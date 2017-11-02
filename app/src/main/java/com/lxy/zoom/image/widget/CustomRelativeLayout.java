package com.lxy.zoom.image.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by a on 2017/11/2.
 */

public class CustomRelativeLayout extends RelativeLayout {

    private Paint mPaint;
    private Paint mRadiusPaint;
    private float mRadius;
    private Path mPath;
    private RectF rectF;

    public CustomRelativeLayout(Context context) {
        this(context, null);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        rectF = new RectF();
        mPath = new Path();
        mPath.setFillType(Path.FillType.EVEN_ODD);
        mRadius = 50f;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);

        mRadiusPaint = new Paint();
        mRadiusPaint.setColor(Color.parseColor("#ff0000"));
        mRadiusPaint.setStyle(Paint.Style.STROKE);
        mRadiusPaint.setStrokeWidth(10);

        //如果你继承的是ViewGroup,注意此行,否则draw方法是不会回调的;
        setWillNotDraw(false);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        rectF.set(0f, 0f, getMeasuredWidth(), getMeasuredHeight());
        setRoundPath();

    }

    private void setRoundPath() {
        //添加一个圆角矩形到path中, 如果要实现任意形状的View, 只需要手动添加path就行
        mPath.addCircle(0, 0, mRadius, Path.Direction.CCW);
        mPath.addCircle(getWidth(), 0, mRadius, Path.Direction.CCW);
        mPath.addCircle(getWidth(), getHeight(), mRadius, Path.Direction.CCW);
        mPath.addCircle(0, getHeight(), mRadius, Path.Direction.CCW);

        mPath.addRect(rectF, Path.Direction.CW);
    }

    @Override
    public void draw(Canvas canvas) {
        if (mRadius > 0f) {
            canvas.clipPath(mPath);
            int width = getWidth();
            int height = getHeight();
            drawCorner(canvas, width, height);
        }
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //边框
        canvas.drawLine(0, 0, getWidth(), 0, mPaint);
        canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), mPaint);
        canvas.drawLine(getWidth(), getHeight(), 0, getHeight(), mPaint);
        canvas.drawLine(0, getHeight(), 0, 0, mPaint);

    }

    //画四个角
    public void drawCorner(Canvas canvas, int width, int height) {
        RectF rectF = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        canvas.drawArc(rectF, -90, 180, false, mRadiusPaint);

        RectF rectF2 = new RectF(width - mRadius, -mRadius, width + mRadius, mRadius);
        canvas.drawArc(rectF2, 90, 180, false, mRadiusPaint);

        RectF rectF3 = new RectF(width - mRadius, height - mRadius, width + mRadius, height + mRadius);
        canvas.drawArc(rectF3, 180, 270, false, mRadiusPaint);

        RectF rectF4 = new RectF(-mRadius, height - mRadius, mRadius, height + mRadius);
        canvas.drawArc(rectF4, 270, 360, false, mRadiusPaint);
    }

}
