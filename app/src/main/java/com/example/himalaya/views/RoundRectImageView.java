package com.example.himalaya.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class RoundRectImageView extends AppCompatImageView {

    private float roundRadio = 0.1f;
    private Path mPath;
    public RoundRectImageView(Context context) {
        this(context, null);
    }

    public RoundRectImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPath == null) {
            mPath = new Path();
            mPath.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), roundRadio * getWidth(),
                    roundRadio * getHeight(), Path.Direction.CW);
        }
        canvas.save();
        canvas.clipPath(mPath);
        super.onDraw(canvas);
        canvas.restore();
    }
}
