package edu.itagd.tgnamo.istodayagooddayto.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomAlertView extends View {
    Paint paint;

    public CustomAlertView(Context context) {
        super(context);
        paint = new Paint();
    }

    public CustomAlertView(Context context, AttributeSet attrs) {
        super(context, attrs); paint = new Paint();
    }

    public CustomAlertView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    public CustomAlertView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        paint = new Paint();
    }

    /**
     * Draws a red circle on a canvas to be used as an alert
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(getWidth()/2, getHeight()/2, 15, paint);
    }
}