package com.chenglongwei.minesweeper.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.chenglongwei.minesweeper.Config;
import com.chenglongwei.minesweeper.model.MineSweeperGame;
import com.google.gson.Gson;

/**
 * Created by chenglongwei on 10/28/15.
 */
public class SharePreferenceUtil {

    private static final String share_key = "shared_key_game_data";
    private static SharedPreferences sharedPref;

    public static void saveGameModel(Context context, MineSweeperGame game) {
        if (game == null) {
            return;
        }
        if (sharedPref == null) {
            sharedPref = context.getSharedPreferences(share_key, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = sharedPref.edit();
        String gameJson = new Gson().toJson(game, MineSweeperGame.class);
        Log.d(Config.TAG, gameJson);
        edit.putString(Config.key_game_model, gameJson);
        edit.apply();
    }

    public static MineSweeperGame getGameModel(Context context) {
        if (sharedPref == null) {
            sharedPref = context.getSharedPreferences(share_key, Context.MODE_PRIVATE);
        }
        String json = sharedPref.getString(Config.key_game_model, "");
        if (!TextUtils.isEmpty(json)) {
            return new Gson().fromJson(json, MineSweeperGame.class);
        }
        return null;
    }

    public static void saveGameState(Context context, boolean[][] reveledCells) {
        if (reveledCells == null || reveledCells.length == 0 || reveledCells[0].length == 0) {
            return;
        }
        if (sharedPref == null) {
            sharedPref = context.getSharedPreferences(share_key, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = sharedPref.edit();

        String cellsJson = new Gson().toJson(reveledCells, boolean[][].class);
        Log.d(Config.TAG, cellsJson);
        edit.putString(Config.key_game_state, cellsJson);
        edit.apply();
    }

    public static boolean[][] getGameState(Context context) {
        if (sharedPref == null) {
            sharedPref = context.getSharedPreferences(share_key, Context.MODE_PRIVATE);
        }
        String json = sharedPref.getString(Config.key_game_state, "");
        if (!TextUtils.isEmpty(json)) {
            return new Gson().fromJson(json, boolean[][].class);

        }
        return null;
    }

    public static void saveValidateCheatButtonState(Context context, boolean enabled) {
        if (sharedPref == null) {
            sharedPref = context.getSharedPreferences(share_key, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putBoolean(Config.key_validate_cheat_button_state, enabled);
        edit.apply();
    }

    public static boolean getValidateCheatButtonState(Context context) {
        if (sharedPref == null) {
            sharedPref = context.getSharedPreferences(share_key, Context.MODE_PRIVATE);
        }

        return sharedPref.getBoolean(Config.key_validate_cheat_button_state, true);
    }
}
