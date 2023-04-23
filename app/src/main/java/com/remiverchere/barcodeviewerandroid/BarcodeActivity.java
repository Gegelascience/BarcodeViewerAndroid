package com.remiverchere.barcodeviewerandroid;

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
import android.widget.Toast;

import com.remiverchere.barcodeviewerandroid.barcodeUtils.BarcodeFormatter;
import com.remiverchere.barcodeviewerandroid.checkEan.EanEnum;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

public class BarcodeActivity extends AppCompatActivity {
    boolean canWriteFile = true;
    private static final int STORAGE_PERMISSION_CODE = 100;
    String possibleEan;
    EanEnum eantype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        BarcodeView myBarcodeView = findViewById(R.id.barcodeRendering);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            this.possibleEan = extras.getString("ean");
            this.eantype = (EanEnum)extras.get("eanType");
            myBarcodeView.modifyEanToRender(this.possibleEan, this.eantype );


            if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                canWriteFile = false;
                Log.w("test", String.valueOf(canWriteFile));
            }
            if (canWriteFile){
                if (checkPermission()) {
                    Log.d("test", "onClick: Permissions already granted...");
                    writeSvgFile();
                } else {
                    Log.d("test", "onClick: Permissions was not granted, request...");
                    requestPermission();
                }
            }
        }

    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    private void writeSvgFile() {
        BarcodeFormatter myFormater = new BarcodeFormatter(this.eantype);
        String dataToRender = myFormater.getBarcodeValue(this.possibleEan);
        if (dataToRender != null){
            try {
                File xmlFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"barcode.xml");
                if (xmlFile.exists()){
                    boolean isDeleted = xmlFile.delete();
                }
                FileOutputStream fos = new FileOutputStream(xmlFile);
                XmlSerializer xmlSerializer = Xml.newSerializer();
                StringWriter writer = new StringWriter();
                xmlSerializer.setOutput(writer);
                xmlSerializer.startDocument("UTF-8",true);
                xmlSerializer.startTag(null,"svg");
                xmlSerializer.attribute(null, "xmlns","http://www.w3.org/2000/svg");
                xmlSerializer.attribute(null, "version","1.1");
                xmlSerializer.attribute(null, "baseProfile","full");
                xmlSerializer.attribute(null, "width","700");
                xmlSerializer.attribute(null, "height","200");

                xmlSerializer.startTag(null,"g");
                xmlSerializer.attribute(null, "stroke","black");
                int index = 10;
                for (int i = 0; i < dataToRender.length(); i++) {
                    if (dataToRender.charAt(i) == '1'){
                        xmlSerializer.startTag(null,"line");
                        xmlSerializer.attribute(null, "stroke-width","4");
                        xmlSerializer.attribute(null, "y1","10");
                        xmlSerializer.attribute(null, "x1",String.valueOf((index)));
                        xmlSerializer.attribute(null, "y2","50");
                        xmlSerializer.attribute(null, "x2",String.valueOf((index)));

                        xmlSerializer.endTag(null,"line");
                    }

                    index = index + 4;
                }



                xmlSerializer.endTag(null,"g");
                xmlSerializer.endTag(null,"svg");

                xmlSerializer.endDocument();
                xmlSerializer.flush();

                String dataWrite = writer.toString();
                fos.write(dataWrite.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android is 11(R) or above
            try {
                Log.d("test", "requestPermission: try");

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                storageActivityResultLauncher.launch(intent);
            }
            catch (Exception e){
                Log.e("test", "requestPermission: catch", e);
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                storageActivityResultLauncher.launch(intent);
            }
        }
        else {
            //Android is below 11(R)
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE
            );
        }
    }

    private ActivityResultLauncher<Intent> storageActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Log.d("test", "onActivityResult: ");
                //here we will handle the result of our intent
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                    //Android is 11(R) or above
                    if (Environment.isExternalStorageManager()){
                        //Manage External Storage Permission is granted
                        Log.d("test", "onActivityResult: Manage External Storage Permission is granted");
                        writeSvgFile();
                    }
                    else{
                        //Manage External Storage Permission is denied
                        Log.d("test", "onActivityResult: Manage External Storage Permission is denied");
                        Toast.makeText(BarcodeActivity.this, "Manage External Storage Permission is denied", Toast.LENGTH_SHORT).show();
                    }
                }

            }
    );

    public boolean checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android is 11(R) or above
            return Environment.isExternalStorageManager();
        }
        else{
            //Android is below 11(R)
            int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
        }
    }

    /*Handle permission request results*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0){
                //check each permission if granted or not
                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (write && read){
                    //External Storage permissions granted
                    Log.d("test", "onRequestPermissionsResult: External Storage permissions granted");
                    writeSvgFile();
                }
                else{
                    //External Storage permission denied
                    Log.d("test", "onRequestPermissionsResult: External Storage permission denied");
                    Toast.makeText(this, "External Storage permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}