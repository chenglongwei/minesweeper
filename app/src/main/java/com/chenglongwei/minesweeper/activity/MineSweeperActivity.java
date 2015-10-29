package com.chenglongwei.minesweeper.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chenglongwei.minesweeper.R;
import com.chenglongwei.minesweeper.dialog.CustomizeDialog;
import com.chenglongwei.minesweeper.fragment.MineSweeperFragment;
import com.chenglongwei.minesweeper.model.MineSweeperGame;

public class MineSweeperActivity extends AppCompatActivity {
    private MineSweeperFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragment = (MineSweeperFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_minesweeper, menu);
        MenuItem itemEasy = menu.findItem(R.id.action_easy);
        itemEasy.setTitle(getResources().getString(R.string.easy) + " (" + MineSweeperGame.EASY_HEIGHT + "*"
                + MineSweeperGame.EASY_WIDTH + "  " + MineSweeperGame.EASY_MINES_NUMBER + " mines)");

        MenuItem itemMedium = menu.findItem(R.id.action_medium);
        itemMedium.setTitle(getResources().getString(R.string.medium) + " (" + MineSweeperGame.MEDIUM_HEIGHT + "*"
                + MineSweeperGame.MEDIUM_WIDTH + "  " + MineSweeperGame.MEDIUM_MINES_NUMBER + " mines)");

        MenuItem itemHard = menu.findItem(R.id.action_hard);
        itemHard.setTitle(getResources().getString(R.string.hard) + " (" + MineSweeperGame.HARD_HEIGHT + "*"
                + MineSweeperGame.HARD_WIDTH + "  " + MineSweeperGame.HARD_MINES_NUMBER + " mines)");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_easy:
                fragment.setGameModel(MineSweeperGame.MODEL.LEVEL_EASY);
                return true;
            case R.id.action_medium:
                fragment.setGameModel(MineSweeperGame.MODEL.LEVEL_MEDIUM);
                return true;
            case R.id.action_hard:
                fragment.setGameModel(MineSweeperGame.MODEL.LEVEL_HARD);
                return true;
            case R.id.action_save:
                fragment.saveGame();
                return true;
            case R.id.action_load:
                fragment.loadGame();
                return true;
            case R.id.action_custom:
                showCustomizeDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCustomizeDialog() {
        final CustomizeDialog customizeDialog = new CustomizeDialog(this);
        customizeDialog.setOnCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customizeDialog.dismiss();
            }
        });
        customizeDialog.setOnConfirmClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int row = customizeDialog.getRowNumber();
                int column = customizeDialog.getColumnNumber();
                int mine = customizeDialog.getMineNumber();
                //do some check
                if (mine >= row * mine) {
                    mine = row * mine - 1;
                }
                fragment.setGameModel(row, column, mine);
                customizeDialog.dismiss();
            }
        });
        customizeDialog.show();
    }
}
