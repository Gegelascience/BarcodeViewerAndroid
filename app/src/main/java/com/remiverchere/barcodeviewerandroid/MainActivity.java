package com.remiverchere.barcodeviewerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.remiverchere.barcodeviewerandroid.testean.EanEnum;
import com.remiverchere.barcodeviewerandroid.testean.EanValidator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button eanBtn = findViewById(R.id.buttonEan);
        EditText eanInput = findViewById(R.id.editTextEan);
        BarcodeView myBarcodeView = findViewById(R.id.barcodeRendering);
        eanBtn.setOnClickListener(v -> {

            //memo valeurs de tests 3666154117284  12345670

            String eanPossibleValue = eanInput.getText().toString();
            Log.d("test",eanPossibleValue);
            EanValidator validator = new EanValidator(EanEnum.EAN13);
            Log.d("test", String.valueOf(validator.isCorrectEan(eanPossibleValue)));
            if (validator.isCorrectEan(eanPossibleValue)){
                myBarcodeView.modifyEanToRender(eanPossibleValue,EanEnum.EAN13 );
            }


        });
    }
}