package com.lxy.zoom.image.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by lxy on 2017/10/10.
 */

public class ZoomImageView extends AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    private boolean mIsFirst = true;
    private float mScale = 1.0f;
    public float mMaxScale;
    public float mMinScale;
    private int mViewWidth;
    private int mViewHeight;

    private Matrix mMatrix;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
        mMatrix = new Matrix();

        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        setOnTouchListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //
        System.out.println("img========onAttachedToWindow");
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //
        System.out.println("img========onDetachedFromWindow");
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        //在这里获取图片的大小
        if (mIsFirst) {
            //获取控件的宽高
            mViewWidth = getMeasuredWidth();
            mViewHeight = getMeasuredHeight();

            Drawable img = getDrawable();
            if (img == null) {
                //未设置src
                return;
            }
            //获取图片的宽高
            int imgWidth = img.getIntrinsicWidth();
            int imgHeight = img.getIntrinsicHeight();

            //计算缩放比例

            if (imgWidth > mViewWidth && imgHeight < mViewHeight) {
                //图片宽 -- 缩小宽
                mScale = mViewWidth * 1.0f / imgWidth;
            }

            if (imgHeight > mViewHeight && imgWidth < mViewWidth) {
                //图片高 -- 缩小高
                mScale = mViewHeight * 1.0f / imgHeight;

            }

            if (imgWidth > mViewWidth && imgHeight > mViewHeight) {
                //图片宽高均大于控件宽高，缩小长边
                mScale = Math.min(mViewWidth * 1.0f / imgWidth, mViewHeight * 1.0f / imgHeight);
            }

            if (imgWidth < mViewWidth && imgHeight < mViewHeight) {
                //图片宽高均小于控件宽高，拉伸长边
                mScale = Math.min(mViewWidth * 1.0f / imgWidth, mViewHeight * 1.0f / imgHeight);
            }

            System.out.println("img======scale==" + mScale);
            mMaxScale = mScale * 3;
            mMinScale = mScale / 2;

            int dy = mViewHeight / 2 - imgHeight / 2;
            int dx = mViewWidth / 2 - imgWidth / 2;

            System.out.println("img======dx==" + dx);
            System.out.println("img======dy==" + dy);
            System.out.println("img======viewWidth==" + mViewWidth);

            //设置初始缩放和平移
            mMatrix.postTranslate(dx, dy);
            mMatrix.postScale(mScale, mScale, mViewWidth / 2, mViewHeight / 2);//缩放比例和中心点
            setImageMatrix(mMatrix);

            mIsFirst = false;
        }

    }

    //获取当前缩放值
    public float getCurrentScale() {

        float[] values = new float[9];
        mMatrix.getValues(values);

        return values[Matrix.MSCALE_X];
    }

    //缩放过程中的位置矫正
    public void centerFix() {

        RectF rct = getMatrixRectf();
        int dx = 0;
        int dy = 0;

        if (rct.left < 0) {

        }

        if (rct.right > 0) {

        }


    }

    //获取缩放过程中图片的 宽高  rectf
    public RectF getMatrixRectf() {

        RectF rectF = new RectF();

        Drawable drawable = getDrawable();
        if (drawable != null) {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mMatrix.mapRect(rectF);
        }


        return rectF;

    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        if (getDrawable() == null) {
            return true;
        }
        //获取缩放的值
        float scaleFactor = detector.getScaleFactor();
        System.out.println("img======scaleValue==" + scaleFactor);
        //获取触控坐标
        detector.getFocusX();

        // 控制缩放范围
        float currentScale = getCurrentScale();
        if ((currentScale < mMaxScale && scaleFactor > 1.0f) || (currentScale > mMinScale && scaleFactor < 1.0f)) {
            //放大或者缩小
            if (currentScale * scaleFactor < mMinScale) {
                currentScale = mMinScale;
            }
            if (currentScale * scaleFactor > mMaxScale) {
                currentScale = mMaxScale;
            }

            // mMatrix.postScale(scaleFactor, scaleFactor, mViewWidth / 2, mViewHeight / 2);//缩放比例和中心点
            mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            centerFix();
            setImageMatrix(mMatrix);
        }


        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        //这里返回true scale和end才会执行
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);

        //多点触控


        return true;
    }
}
