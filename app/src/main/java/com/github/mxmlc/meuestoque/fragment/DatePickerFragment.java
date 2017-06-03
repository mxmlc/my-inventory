package com.github.mxmlc.meuestoque.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.github.mxmlc.meuestoque.util.DateUtil;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Fragmento para apresentação da caixa de diálogo de seleção de data.
 *
 * @author mxmlc - Fabiano Ramos dos Santos
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText editText;

    public void setEditText(View v) {
        editText = (EditText) v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * Ao selecionar uma data atualiza o campo em tela.
     *
     * @param view
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String dateStr = new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).toString();
        editText.setText(DateUtil.formatDate(dateStr));
    }

}
