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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chenglongwei.minesweeper.R;
import com.chenglongwei.minesweeper.dialog.ConfirmDialog;
import com.chenglongwei.minesweeper.model.GameBoard;
import com.chenglongwei.minesweeper.view.GameCell;

import java.util.HashSet;
import java.util.Set;

/**
 * A placeholder fragment containing a simple view.
 */
public class MineSweeperFragment extends Fragment implements View.OnClickListener {
    private Button bt_new;
    private Button bt_validate;
    private Button bt_cheat;
    private LinearLayout ll_mine_field;

    private GameBoard game;
    private GameCell[][] cellButtons;
    // if the cell is 0, we should revel neighbors around it,
    // remember cell 0 positions, and do recursively revel.
    private Set<Point> reveled;
    private CellClickListener cellClickListener;

    private GameBoard.MODEL model = GameBoard.MODEL.LEVEL_MEDIUM;
    private String TAG = "MineSweeper";

    public MineSweeperFragment() {
    }

    //game levels
    public void setGameModel(GameBoard.MODEL model) {
        this.model = model;
        initGame();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_minesweeper, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initGame();
    }

    private void initView(View view) {
        ll_mine_field = (LinearLayout) view.findViewById(R.id.ll_mine_field);
        bt_new = (Button) view.findViewById(R.id.bt_new);
        bt_validate = (Button) view.findViewById(R.id.bt_validate);
        bt_cheat = (Button) view.findViewById(R.id.bt_cheat);

        bt_new.setOnClickListener(this);
        bt_validate.setOnClickListener(this);
        bt_cheat.setOnClickListener(this);
    }

    private void initGame() {
        game = new GameBoard(model);
        cellButtons = new GameCell[game.getBoardHeight()][game.getBoardWidth()];
        cellClickListener = new CellClickListener();
        reveled = new HashSet<>();
        enableValidAndCheat(true);
        renderMineField();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_new:
                initGame();
                break;
            case R.id.bt_validate:
                if (checkGame()) {
                    showSuccessDialog();
                } else {
                    showFailedDialog();
                }
                break;
            case R.id.bt_cheat:
                cheatGame();
                break;
        }
    }

    private void enableValidAndCheat(boolean enable) {
        bt_validate.setEnabled(enable);
        bt_cheat.setEnabled(enable);
        if (enable) {
            bt_validate.setBackgroundResource(R.drawable.button_bg);
            bt_cheat.setBackgroundResource(R.drawable.button_bg);
        } else {
            bt_validate.setBackgroundResource(R.drawable.button_disable_bg);
            bt_cheat.setBackgroundResource(R.drawable.button_disable_bg);
        }
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
                showGameOverDialog();
            }
        }
    }

    private void showGameOverDialog() {
        ConfirmDialog.show(getActivity(), R.string.game_over_title, R.string.game_over_message,
                R.string.game_over_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        enableValidAndCheat(false);
                        revelGameBoard();
                    }
                });
    }

    private void showSuccessDialog() {
        ConfirmDialog.show(getActivity(), R.string.game_success_title, R.string.game_success_message,
                R.string.game_success_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        enableValidAndCheat(false);
                    }
                });
    }

    private void showFailedDialog() {
        ConfirmDialog.show(getActivity(), R.string.game_fail_title, R.string.game_fail_message,
                R.string.game_fail_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        enableValidAndCheat(false);
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

    //to revel the mines
    private void cheatGame() {
        for (int i = 0; i < game.getBoardHeight(); i++) {
            for (int j = 0; j < game.getBoardWidth(); j++) {
                cellButtons[i][j].cheatAndRevealMine();
            }
        }
    }

    private boolean checkGame() {
        for (int i = 0; i < game.getBoardHeight(); i++) {
            for (int j = 0; j < game.getBoardWidth(); j++) {
                if (!cellButtons[i][j].getReveled() && game.getMineNumberAt(i, j) != GameBoard.MINE) {
                    return false;
                }
            }
        }
        return true;
    }
}
