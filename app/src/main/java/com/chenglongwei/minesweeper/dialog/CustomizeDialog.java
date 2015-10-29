package com.chenglongwei.minesweeper.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.chenglongwei.minesweeper.R;

/**
 * Created by chenglongwei on 10/28/15.
 */
public class CustomizeDialog extends Dialog {
    private static final int MIN_SIZE = 4;
    private static final int MAX_WIDTH = 10;
    private static final int MAX_COLUMN = 15;
    private NumberPicker np_row_number;
    private NumberPicker np_column_number;
    private NumberPicker np_mine_number;
    private Button bt_cancel;
    private Button bt_confirm;

    public CustomizeDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_cutomize_level);

        np_row_number = (NumberPicker) findViewById(R.id.np_row_number);
        np_row_number.setMinValue(MIN_SIZE);
        np_row_number.setMaxValue(MAX_COLUMN);
        np_column_number = (NumberPicker) findViewById(R.id.np_column_number);
        np_column_number.setMinValue(MIN_SIZE);
        np_column_number.setMaxValue(MAX_WIDTH);
        np_mine_number = (NumberPicker) findViewById(R.id.np_mine_number);
        np_mine_number.setMinValue(MIN_SIZE);
        np_mine_number.setMaxValue(2 * MAX_WIDTH);

        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_confirm = (Button) findViewById(R.id.bt_confirm);

        setTitle(context.getString(R.string.customize_title));
    }

    public void setOnCancelClickListener(View.OnClickListener listener) {
        bt_cancel.setOnClickListener(listener);
    }

    public void setOnConfirmClickListener(View.OnClickListener listener) {
        bt_confirm.setOnClickListener(listener);
    }

    public int getRowNumber() {
        return np_row_number.getValue();
    }

    public int getColumnNumber() {
        return np_column_number.getValue();
    }

    public int getMineNumber() {
        return np_mine_number.getValue();
    }
}
