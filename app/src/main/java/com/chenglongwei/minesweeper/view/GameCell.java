package com.chenglongwei.minesweeper.view;

import android.content.Context;
import android.widget.Button;

import com.chenglongwei.minesweeper.R;
import com.chenglongwei.minesweeper.model.MineSweeperGame;

/**
 * Created by chenglongwei on 10/27/15.
 */
public class GameCell extends Button {
    private int row;
    private int column;
    private int mineNumber;
    private boolean reveled;
    private String mineSymbol = "*";

    public GameCell(Context context) {
        super(context);
        setBackgroundResource(R.drawable.mine_hidden);
    }

    public void setPositionAndValue(int row, int column, int mineNumber) {
        this.row = row;
        this.column = column;
        this.mineNumber = mineNumber;
    }

    public void setReveled(boolean reveled) {
        this.reveled = reveled;
        if (reveled) {
            if (mineNumber == MineSweeperGame.MINE) {
                setText(mineSymbol);
                setBackgroundResource(R.drawable.mine_hitten);
            } else {
                setText(mineNumber == 0 ? "" : String.valueOf(mineNumber));
                setBackgroundResource(R.drawable.mine_reveled);
            }
        } else {
            setBackgroundResource(R.drawable.mine_hidden);
        }
    }

    public void cheatAndRevealMine() {
        if (mineNumber == MineSweeperGame.MINE) {
            setText(mineSymbol);
        }
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getMineNumber() {
        return mineNumber;
    }

    public void setMineNumber(int mineNumber) {
        this.mineNumber = mineNumber;
    }

    public boolean isReveled() {
        return reveled;
    }
}
