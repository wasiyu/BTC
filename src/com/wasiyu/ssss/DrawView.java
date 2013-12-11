package com.wasiyu.ssss;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by ttpod on 13-12-11.
 */
public class DrawView extends View {
    Paint mRedPaint;
    Paint mGreenPaint;
    private Paint mBluePaint;
    private final float OFFSET_OF_LINE = 25;
    private final float OFFSET_TO_LINE = 5;
    private float mWidth;
    private float mHeight;

    public DrawView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mBluePaint = new Paint();
        mBluePaint.setColor(Color.BLUE);
        mBluePaint.setStyle(Paint.Style.STROKE);
        mRedPaint = new Paint();
        mRedPaint.setColor(Color.RED);
        mRedPaint.setStyle(Paint.Style.FILL);
        mGreenPaint = new Paint();
        mGreenPaint.setColor(Color.GREEN);
        mGreenPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth() / 2 - 50;
        int rowHeight = 60;
        int centerX = getWidth() / 2;

        int centerOffset = 5;
        int initStartY = 50;
        int startY = initStartY;
        int rectHeight = rowHeight - centerOffset * 2;

        for (int nIndex = 0; nIndex < 5; nIndex++) {
            canvas.drawRect(centerX - width, startY, centerX + width, startY + rowHeight, mBluePaint);
            canvas.drawLine(centerX, startY, centerX, startY + rowHeight, mBluePaint);

            // 左边的，往前画
            float leftWidth = (float) (Math.random() * (width - centerOffset));
            canvas.drawRect(centerX - centerOffset - leftWidth
                            , startY + centerOffset
                            , centerX - centerOffset
                            , startY + centerOffset + rectHeight
                            , mRedPaint);

            // 右边的，往后画
            float rightWidth = (float) (Math.random() * (width - centerOffset));
            canvas.drawRect(centerX + centerOffset
                            , startY + centerOffset
                            , centerX + centerOffset + rightWidth
                            , startY + centerOffset + rectHeight
                            , mGreenPaint);

            startY = initStartY + nIndex * rowHeight;
        }
    }
}
