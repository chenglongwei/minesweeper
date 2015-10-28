package com.chenglongwei.minesweeper.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;

/**
 * Created by chenglongwei on 10/28/15.
 */
public class ConfirmDialog {
    public static void show(Context context, CharSequence title, CharSequence message,
                            CharSequence button, DialogInterface.OnClickListener listener) {
        Dialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(button, listener)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void show(Context context, @StringRes int titleId, @StringRes int messageId,
                            @StringRes int buttonId, DialogInterface.OnClickListener listener) {
        Dialog dialog = new AlertDialog.Builder(context)
                .setTitle(titleId)
                .setMessage(messageId)
                .setNeutralButton(buttonId, listener)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
