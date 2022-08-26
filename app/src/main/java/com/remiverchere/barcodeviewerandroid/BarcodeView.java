package com.remiverchere.barcodeviewerandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.remiverchere.barcodeviewerandroid.testean.EanEnum;

public class BarcodeView extends View {

    private String ean;
    private EanEnum eanType;

    public BarcodeView(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("test","drawing");
        Paint myPaint = new Paint();
        myPaint.setColor(getResources().getColor(R.color.black));
        myPaint.setStrokeWidth(10.0f);
        this.setBackgroundColor(getResources().getColor(R.color.light_blue_400));

        canvas.drawLine(0,0,100,100,myPaint);

    }

    public void modifyEanToRender(String eanToRender, EanEnum eanType) {
        this.ean = eanToRender;
        this.eanType = eanType;
        invalidate();
    }
}
