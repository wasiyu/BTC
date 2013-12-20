package com.wasiyu.ssss;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ttpod on 13-12-11.
 */
public class DrawView extends View {
    Paint mRedPaint;
    Paint mGreenPaint;
    Paint mTextPaint;
    private Paint mBluePaint;
    private static final int MAX_LENGTH = 30;
    private float mRedValue[] = new float[MAX_LENGTH];
    private float mGreenValue[] = new float[MAX_LENGTH];
    private boolean flag = true;

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defaultSize = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        setMeasuredDimension(defaultSize, 2000);
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

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        int width = getWidth() / 2 - 50;
        if (flag) {
            flag = false;
            for (int nIndex = 0; nIndex < MAX_LENGTH; ++nIndex) {
                mRedValue[nIndex] = (float) (Math.random() * width);
                mGreenValue[nIndex] = (float) (Math.random() * width);
            }
        }
        int rowHeight = 60;
        int centerX = getWidth() / 2;

        int centerOffset = 5;
        int initStartY = 50;
        int startY = initStartY;
        int rectHeight = rowHeight - centerOffset * 2;

        for (int nIndex = 0; nIndex < MAX_LENGTH; nIndex++, startY = initStartY + nIndex * rowHeight) {

            canvas.drawRect(centerX - width, startY, centerX + width, startY + rowHeight, mBluePaint);
            canvas.drawLine(centerX, startY, centerX, startY + rowHeight, mBluePaint);

            // 左边的，往前画
            canvas.drawRect(centerX - centerOffset - mRedValue[nIndex]
                            , startY + centerOffset
                            , centerX - centerOffset
                            , startY + centerOffset + rectHeight
                            , mRedPaint);
            String left = String.format("%.1f", mRedValue[nIndex]);
            canvas.drawText(left
                    , centerX - width + 2
                    , startY + centerOffset + 40
                    , mTextPaint);

            // 右边的，往后画
            canvas.drawRect(centerX + centerOffset
                    , startY + centerOffset
                    , centerX + centerOffset + mGreenValue[nIndex]
                    , startY + centerOffset + rectHeight
                    , mGreenPaint);

            String right = String.format("%.1f", mGreenValue[nIndex]);
            float textWidth = mTextPaint.measureText(right);
            canvas.drawText(right
                    , centerX + width - 2 - textWidth
                    , startY + centerOffset + 40
                    , mTextPaint);
        }
    }
}
