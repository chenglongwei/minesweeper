package com.chenglongwei.minesweeper.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.chenglongwei.minesweeper.R;
import com.chenglongwei.minesweeper.fragment.MineSweeperFragment;
import com.chenglongwei.minesweeper.model.GameBoard;

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
        itemEasy.setTitle(getResources().getString(R.string.easy) + " (" + GameBoard.EASY_HEIGHT + " * "
                + GameBoard.EASY_WIDTH + " " + GameBoard.EASY_MINES_NUMBER + ")");

        MenuItem itemMedium = menu.findItem(R.id.action_medium);
        itemMedium.setTitle(getResources().getString(R.string.medium) + " (" + GameBoard.MEDIUM_HEIGHT + " * "
                + GameBoard.MEDIUM_WIDTH + " " + GameBoard.MEDIUM_MINES_NUMBER + ")");

        MenuItem itemHard = menu.findItem(R.id.action_hard);
        itemHard.setTitle(getResources().getString(R.string.hard) + " (" + GameBoard.HARD_HEIGHT + " * "
                + GameBoard.HARD_WIDTH + " " + GameBoard.HARD_MINES_NUMBER + ")");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_easy:
                fragment.setGameModel(GameBoard.MODEL.LEVEL_EASY);
                return true;
            case R.id.action_medium:
                fragment.setGameModel(GameBoard.MODEL.LEVEL_MEDIUM);
                return true;
            case R.id.action_hard:
                fragment.setGameModel(GameBoard.MODEL.LEVEL_HARD);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
