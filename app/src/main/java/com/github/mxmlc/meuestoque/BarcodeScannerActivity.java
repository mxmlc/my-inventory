package com.github.mxmlc.meuestoque;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Atividade para realizar a leitura de código de barras.
 *
 * @author mxmlc - Fabiano Ramos dos Santos
 */
public class BarcodeScannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);
        setTitle(R.string.scan_barcode);
    }

}
