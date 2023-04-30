package com.remiverchere.barcodeviewerandroid.barcodeUtils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Xml;
import android.view.View;

import com.remiverchere.barcodeviewerandroid.BarcodeView;
import com.remiverchere.barcodeviewerandroid.checkEan.EanEnum;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

public class BarcodeFileSaving {

    public void saveAsSvgFile(EanEnum eanType, String possibleEan) {
        BarcodeFormatter myFormater = new BarcodeFormatter(eanType);
        String dataToRender = myFormater.getBarcodeValue(possibleEan);
        if (dataToRender != null){
            try {
                File svgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"barcode.svg");
                if (svgFile.exists()){
                    boolean isDeleted = svgFile.delete();
                }
                FileOutputStream fos = new FileOutputStream(svgFile);
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

    public void saveAsPngFile(BarcodeView myView) {

        try {
            File pngFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "barcode.png");
            if (pngFile.exists()) {
                boolean isDeleted = pngFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(pngFile);
            Bitmap myBitmap = myView.getDrawingCache();
            myBitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


}
