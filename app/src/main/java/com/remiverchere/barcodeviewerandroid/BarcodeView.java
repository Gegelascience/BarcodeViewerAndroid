package com.remiverchere.barcodeviewerandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.remiverchere.barcodeviewerandroid.barcodeUtils.BarcodeFormatter;
import com.remiverchere.barcodeviewerandroid.checkEan.EanEnum;


import java.util.Arrays;
import java.util.List;


public class BarcodeView extends View {

    private String ean;
    private EanEnum eanType;
    private final Paint myPaint;
    private BarcodeFormatter myFormatter;
    private List<Integer> listMetaIndex;

    public BarcodeView(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        this.myPaint = new Paint();
        this.setDrawingCacheEnabled(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("test","drawing");

        if (this.ean != null){
            String dataToRender = this.myFormatter.getBarcodeValue(this.ean);
            Log.d("test value", dataToRender);

            float barWidth = 20.0f;

            myPaint.setColor(ContextCompat.getColor(this.getContext(),R.color.black));
            myPaint.setStrokeWidth(barWidth);
            myPaint.setTextSize(100.0f);

            float widthView = this.getWidth();

            int decalageLargeur;
            if (this.eanType == EanEnum.EAN13) {
                decalageLargeur = Math.round((widthView - (7*12 +11)*barWidth)/2);

            } else {
                decalageLargeur = Math.round((widthView - (7*8 +11)*barWidth)/2);
            }
            int decalagepart1 =0;
            int decalagepart2 =4;
            if (this.eanType == EanEnum.EAN13) {
                canvas.drawText(this.ean.substring(0,1),decalageLargeur-100,650,myPaint);
                decalagepart1 = 1;
                decalagepart2 = 7;
            }


            if (dataToRender != null){
                for (int i = 0; i < dataToRender.length(); i++) {
                    if ( dataToRender.charAt(i) == '1'){
                        if (this.listMetaIndex.contains(i)) {
                            canvas.drawLine(decalageLargeur + i*barWidth,400,decalageLargeur + i*20,650,myPaint);
                        } else {
                            canvas.drawLine(decalageLargeur + i*barWidth,400,decalageLargeur + i*20,550,myPaint);
                        }

                    }

                    if (i > 2 && i < this.listMetaIndex.get(3)){
                        int iref = i - 2;
                        if (iref%7 == 3) {
                            int textValue= (int) Math.floor(iref/7.0f);
                            canvas.drawText(this.ean.substring(textValue + decalagepart1,textValue + decalagepart1 +1),decalageLargeur + i*barWidth,650,myPaint);
                        }
                    } else {
                        if (i > this.listMetaIndex.get(7) && i < this.listMetaIndex.get(8)) {
                            int iref = i - this.listMetaIndex.get(7);
                            if (iref%7 == 3) {
                                int textValue= (int) Math.floor(iref/7.0f);
                                canvas.drawText(this.ean.substring(textValue + decalagepart2,textValue + decalagepart2 +1),decalageLargeur + i*barWidth,650,myPaint);
                            }
                        }
                    }

                }
                this.buildDrawingCache();

            }
        }



    }

    public void modifyEanToRender(String eanToRender, EanEnum eanType) {
        this.ean = eanToRender;
        this.eanType = eanType;

        this.myFormatter = new BarcodeFormatter(this.eanType);
        if (eanType == EanEnum.EAN8){
            this.listMetaIndex = Arrays.asList(0,1,2,31,32,33,34,35,64,65,66);

        }else {
            this.listMetaIndex = Arrays.asList(0,1,2,45,46,47,48,49,92,93,94);
        }


        invalidate();
    }

}
