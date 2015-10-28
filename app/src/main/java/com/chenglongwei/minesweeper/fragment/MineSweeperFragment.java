package com.chenglongwei.minesweeper.fragment;

import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.chenglongwei.minesweeper.R;
import com.chenglongwei.minesweeper.dialog.ConfirmDialog;
import com.chenglongwei.minesweeper.model.GameBoard;
import com.chenglongwei.minesweeper.view.GameCell;

import java.util.HashSet;
import java.util.Set;

/**
 * A placeholder fragment containing a simple view.
 */
public class MineSweeperFragment extends Fragment {
    private GameBoard game;
    private GameCell[][] cellButtons;
    // if the cell is 0, we should revel neighbors around it,
    // remember cell 0 positions, and do recursively revel.
    private Set<Point> reveled;
    private LinearLayout ll_mine_field;
    private CellClickListener cellClickListener;
    private String TAG = "MineSweeper";

    public MineSweeperFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_minesweeper, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll_mine_field = (LinearLayout) view.findViewById(R.id.ll_mine_field);
        initGame();
    }

    private void initGame() {
        game = new GameBoard();
        cellButtons = new GameCell[GameBoard.DEFAULT_BOARD_HEIGHT][GameBoard.DEFAULT_BOARD_WIDTH];
        cellClickListener = new CellClickListener();
        reveled = new HashSet<>();
        renderMineField();
    }

    private void renderMineField() {
        //get the screen size to compute cell height
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        //add in some cell padding
        int cellSize = Math.min(screenSize.x, screenSize.y) / (game.getBoardWidth() + 1);
        Log.d(TAG, "cellSize: " + cellSize);

        ll_mine_field.removeAllViews();

        for (int i = 0; i < game.getBoardHeight(); i++) {
            LinearLayout row = new LinearLayout(getActivity());
            row.setHorizontalGravity(LinearLayout.HORIZONTAL);
            for (int j = 0; j < game.getBoardWidth(); j++) {
                cellButtons[i][j] = new GameCell(getActivity());
                cellButtons[i][j].setPositionAndValue(i, j, game.getMineNumberAt(i, j));
                cellButtons[i][j].setLayoutParams(new RelativeLayout.LayoutParams(cellSize, cellSize));
                cellButtons[i][j].setOnClickListener(cellClickListener);
                row.addView(cellButtons[i][j]);
            }
            ll_mine_field.addView(row);
        }
    }

    class CellClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "click: " + v);
            GameCell cell = (GameCell) v;
            //already reveled, do nothing
            if (cell.getReveled()) {
                return;
            }

            cell.setReveled(true);
            int row = cell.getRow();
            int column = cell.getColumn();
            if (game.getMineNumberAt(row, column) == 0) {
                reveled.add(new Point(row, column));
                revelNeighbors(row, column);
            } else if (game.getMineNumberAt(row, column) == GameBoard.MINE) {
                showFailedDialog();
            }
        }
    }

    private void showFailedDialog() {
        ConfirmDialog.show(getActivity(), R.string.game_over_title, R.string.game_over_message,
                R.string.game_over_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //TODO: block the validate
                        revelGameBoard();
                    }
                });
    }

    private void revelGameBoard() {
        for (int i = 0; i < game.getBoardHeight(); i++) {
            for (int j = 0; j < game.getBoardWidth(); j++) {
                cellButtons[i][j].setReveled(true);
            }
        }
    }

    private void revelNeighbors(int row, int column) {
        for (int i = 0; i < GameBoard.directions.length; i++) {
            int r = row + GameBoard.directions[i][0];
            int c = column + GameBoard.directions[i][1];
            if (r >= 0 && r < game.getBoardHeight() && c >= 0 && c < game.getBoardWidth()) {

                cellButtons[r][c].setReveled(true);
                //if number was 0, behaves as if the user has clicked on every cell around it.
                if (game.getMineNumberAt(r, c) == 0 && reveled.add(new Point(r, c))) {
                    revelNeighbors(r, c);
                }
            }
        }
    }
}
