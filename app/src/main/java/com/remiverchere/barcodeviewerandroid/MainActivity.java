package com.remiverchere.barcodeviewerandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.util.Xml;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.remiverchere.barcodeviewerandroid.checkEan.EanEnum;
import com.remiverchere.barcodeviewerandroid.checkEan.EanValidator;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

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