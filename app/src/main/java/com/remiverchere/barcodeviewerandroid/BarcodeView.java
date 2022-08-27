package com.remiverchere.barcodeviewerandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.remiverchere.barcodeviewerandroid.testean.EanEnum;


import java.util.Arrays;
import java.util.List;


public class BarcodeView extends View {

    private String ean;
    private EanEnum eanType;
    private final Paint myPaint;

    private final static List<String> setA = Arrays.asList(
            "0001101", "0011001", "0010011","0111101","0100011","0110001","0101111","0111011","0110111","0001011");

    private final static List<String> setB = Arrays.asList(
            "0100111", "0110011", "0011011","0100001","0011101","0111001","0000101","0010001","0001001","0010111");

    private final static List<String> setC = Arrays.asList(
            "1110010", "1100110", "1101100","1000010","1011100","1001110","1010000","1000100","1001000","1110100");

    public BarcodeView(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        this.myPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("test","drawing");

        if (this.ean != null){
            String dataToRender = getBarcodeValue();

            myPaint.setColor(getResources().getColor(R.color.black));
            myPaint.setStrokeWidth(30.0f);

            int decalageLargeur = 100;

            if (dataToRender != null){
                for (int i = 0; i < dataToRender.length(); i++) {
                    if ( dataToRender.charAt(i) == '1'){
                        canvas.drawLine(400,decalageLargeur + i*30,550,decalageLargeur + i*30,myPaint);
                    }

                }
            }
        }



    }

    public void modifyEanToRender(String eanToRender, EanEnum eanType) {
        this.ean = eanToRender;
        this.eanType = eanType;
        invalidate();
    }

    private String getBarcodeValue() {

        if( this.eanType == EanEnum.EAN13){

            String firstPart = this.ean.substring(1,7);
            String seconPart = this.ean.substring(7);

            String prefix = this.ean.substring(0,1);

            String barcodeValue = "101";

            for (int i = 0; i < firstPart.length(); i++) {
                List<String> setFound = this.getSetToApply(i,prefix);

                barcodeValue = barcodeValue + setFound.get(Integer.parseInt(firstPart.substring(i,i+1)));
            }


            barcodeValue = barcodeValue +"01010";

            for (int i = 0; i < seconPart.length(); i++) {
                barcodeValue = barcodeValue + setC.get(Integer.parseInt(firstPart.substring(i,i+1)));
            }


            barcodeValue = barcodeValue +"101";

            return barcodeValue;

        } else if (this.eanType == EanEnum.EAN8) {

            String firstPart = this.ean.substring(0,4);
            String seconPart = this.ean.substring(4);

            String barcodeValue = "101";

            for (int i = 0; i < seconPart.length(); i++) {
                barcodeValue = barcodeValue + setA.get(Integer.parseInt(firstPart.substring(i,i+1)));
            }

            barcodeValue = barcodeValue +"01010";

            for (int i = 0; i < seconPart.length(); i++) {
                barcodeValue = barcodeValue + setC.get(Integer.parseInt(firstPart.substring(i,i+1)));
            }


            barcodeValue = barcodeValue +"101";

            return barcodeValue;
        } else {
            return null;
        }


    }

    private List<String> getSetToApply(int index, String prefix) {
        List<String> setFound;

        if (index == 0) {
            setFound =  setA;
        } else {
            switch (prefix){
                case "0":
                    setFound =  setA;
                    break;
                case "1":
                    if (index == 1 || index == 3) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "2":
                    if (index == 1 || index == 4) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "3":
                    if (index == 1 || index == 5) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "4":
                    if (index == 2 || index == 3) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "5":
                    if (index == 4 || index == 3) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "6":
                    if (index == 4 || index == 5) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "7":
                    if (index == 2 || index == 4) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "8":
                    if (index == 2 || index == 5) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "9":
                    if (index == 5 || index == 3) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                default:
                    setFound =  null;
                    break;
            }
        }

        return setFound;
    }
}
