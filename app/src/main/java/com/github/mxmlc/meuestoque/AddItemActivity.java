package com.github.mxmlc.meuestoque;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mxmlc.meuestoque.fragment.DatePickerFragment;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void callBarcodeScanner(View v) {
        Intent intent = new Intent(AddItemActivity.this, BarcodeScannerActivity.class);
        startActivity(intent);
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setEditText(v);
        fragment.show(getFragmentManager(), "datePicker");
    }

}
