package com.remiverchere.barcodeviewerandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.remiverchere.barcodeviewerandroid.checkEan.EanEnum;
import com.remiverchere.barcodeviewerandroid.checkEan.EanValidator;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button eanBtn = findViewById(R.id.buttonEan);
        EditText eanInput = findViewById(R.id.editTextEan);

        eanBtn.setOnClickListener(v -> {

            //memo valeurs de tests 3666154117284  12345670

            String eanPossibleValue = eanInput.getText().toString();
            Log.d("test",eanPossibleValue);
            EanValidator validator13 = new EanValidator(EanEnum.EAN13);
            EanValidator validator8 = new EanValidator(EanEnum.EAN8);
            Log.d("test", String.valueOf(validator13.isCorrectEan(eanPossibleValue)));
            if (validator13.isCorrectEan(eanPossibleValue)){
                Intent goBarcodeActivity = new Intent(MainActivity.this, BarcodeActivity.class);
                goBarcodeActivity.putExtra("ean",eanPossibleValue);
                goBarcodeActivity.putExtra("eanType",EanEnum.EAN13);
                startActivity(goBarcodeActivity);

            } else if (validator8.isCorrectEan(eanPossibleValue)) {
                Intent goBarcodeActivity = new Intent(MainActivity.this, BarcodeActivity.class);
                goBarcodeActivity.putExtra("ean",eanPossibleValue);
                goBarcodeActivity.putExtra("eanType",EanEnum.EAN8);
                startActivity(goBarcodeActivity);
            }


        });
    }

}