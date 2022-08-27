package com.remiverchere.barcodeviewerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.remiverchere.barcodeviewerandroid.testean.EanEnum;

public class BarcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        BarcodeView myBarcodeView = findViewById(R.id.barcodeRendering);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String eanPossibleValue = extras.getString("ean");
            myBarcodeView.modifyEanToRender(eanPossibleValue, EanEnum.EAN13 );
        }

    }
}