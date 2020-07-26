package com.example.himalaya.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.example.himalaya.R;

/**
 * Description :
 * Time : 2020/7/24 15:09
 * Author : zengkun
 */
public class LoadingView extends androidx.appcompat.widget.AppCompatImageView {
    private int rotateDegree = 0;
    private boolean mNeedRotate = false;
    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置图片
        setImageResource(R.mipmap.loading);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mNeedRotate = true;
        post(new Runnable() {
            @Override
            public void run() {
                rotateDegree += 30;
                rotateDegree = rotateDegree <= 360 ? rotateDegree : rotateDegree - 360;
                invalidate();
                if (mNeedRotate) {
                    postDelayed(this, 100);
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mNeedRotate = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //传给父类之前要干的事,让他旋转
        canvas.rotate(rotateDegree, getWidth()/2, getHeight() /2);
        super.onDraw(canvas);
    }
}