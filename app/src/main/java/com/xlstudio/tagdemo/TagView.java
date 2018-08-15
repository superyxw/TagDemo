package com.xlstudio.tagdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TagView extends ViewGroup {

    public static final int DEFAULT_RADIUS = 8;//默认外圆半径
    public static final int DEFAULT_LINES_WIDTH = 1;//默认线宽
    public static final int DEFAULT_TAP_RANGE = 50;//默认线宽

    private int mCenterX;//圆心 X 坐标
    private int mCenterY;//圆心 Y 坐标
    private float mPercentX;
    private float mPercentY;
    private Paint mPaint;
    private Path mPath;

    private GestureDetectorCompat mGestureDetector;

    private TextView tagText;

    private int mRadius;//外圆半径

    private int mLinesWidth;

    private DIRECTION direction;

    private RectF mCenterRect;

    private RectF mTextRect;

    public TagView(Context context) {
        this(context, null);
    }

    public TagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRadius = DEFAULT_RADIUS;
        mLinesWidth = DEFAULT_LINES_WIDTH;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        direction = DIRECTION.RIGHT;
        mPath = new Path();

        mCenterRect = new RectF();
        mTextRect = new RectF();

        tagText = new TextView(context);
        tagText.setText("its test");
        tagText.setTextColor(Color.WHITE);
        tagText.setBackground(context.getDrawable(R.drawable.tag_textview_bg));
        tagText.setPadding(20,10,20,10);

        addView(tagText);

        mGestureDetector = new GestureDetectorCompat(context, new TagOnGestureListener());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        int left = 0;
        int right = 0;
        switch(direction) {
            case LEFT:
                left = mCenterX-50-child.getMeasuredWidth();
                right = mCenterX-50;
                break;
            case RIGHT:
                left = mCenterX+50;
                right = mCenterX+50+child.getMeasuredWidth();
                break;
        }
        child.layout(left, mCenterY-child.getMeasuredHeight()/2, right, mCenterY+child.getMeasuredHeight()/2);
        mTextRect.set(left, mCenterY-child.getMeasuredHeight()/2, right, mCenterY+child.getMeasuredHeight()/2);
    }

    public void setPercent(float percentX, float percentY) {
        mPercentX = percentX;
        mPercentY = percentY;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //园中心默认在左上角 (0,0)
        mCenterX = (int) (getMeasuredWidth() * mPercentX);
        mCenterY = (int) (getMeasuredHeight() * mPercentY);

        mCenterRect.set(mCenterX - DEFAULT_TAP_RANGE, mCenterY - DEFAULT_TAP_RANGE, mCenterX + DEFAULT_TAP_RANGE, mCenterY + DEFAULT_TAP_RANGE);
        checkBound();
    }

    private void checkBound() {
        if (mCenterX < mRadius) {
            mCenterX = mRadius;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#ffffff"));
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);

        mPath.reset();
        mPath.moveTo(mCenterX, mCenterY);
        switch (direction) {
            case LEFT:
                mPath.lineTo(mCenterX - 50, mCenterY);
                break;
            case RIGHT:
                mPath.lineTo(mCenterX + 50, mCenterY);
                break;
        }
        mPaint.setStrokeWidth(mLinesWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPath, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return mGestureDetector.onTouchEvent(event);
    }

    private class TagOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();
            if (mCenterRect.contains(x, y) || mTextRect.contains(x,y)) {
                return true;
            }
            return super.onDown(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();

            if (mCenterRect.contains(x, y) || mTextRect.contains(x,y)) {
                direction = direction.next();
                invalidate();
                requestLayout();
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("wenzi","touch event");
            float currentX = mCenterX - distanceX;
            float currentY = mCenterY - distanceY;
            mPercentX = currentX / getMeasuredWidth();
            mPercentY = currentY / getMeasuredHeight();
            invalidate();
            requestLayout();
            return true;
        }
    }

}
