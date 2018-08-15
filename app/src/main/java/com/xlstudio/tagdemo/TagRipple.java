package com.xlstudio.tagdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class TagRipple extends View{

    private Paint mPaint;

    public TagRipple(Context context) {
        this(context, null);
    }

    public TagRipple(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagRipple(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
    }


}
