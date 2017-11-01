package com.lxy.zoom.image.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by a on 2017/11/1.
 */

public class CustomImageView extends AppCompatImageView {

    private float mRadius;
    private Path mPath;
    private Paint mLinePaint;
    private Paint mRadiusPaint;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mRadius = 25f;
        mPath = new Path();
        mLinePaint = new Paint();
        mRadiusPaint = new Paint();

        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.parseColor("#FF0000"));
        mLinePaint.setStrokeWidth(8);

        mRadiusPaint.setStyle(Paint.Style.STROKE);
        mRadiusPaint.setColor(Color.parseColor("#FF0000"));
        mRadiusPaint.setStrokeWidth(4);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        if (!(drawable instanceof BitmapDrawable)) {
            return;
        }
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap == null) {
            return;
        }

        Bitmap b = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        int width = getWidth();
        int height = getHeight();

        canvas.drawColor(Color.parseColor("#00000000"));
        Bitmap roundBitmap = getCroppedBitmap(b, width, height);


        canvas.drawBitmap(roundBitmap, 0, 0, mLinePaint);
        canvas.drawLine(mRadius, 0, width - mRadius, 0, mLinePaint);
        canvas.drawLine(mRadius, height, width - mRadius, height, mLinePaint);
        canvas.drawLine(0, mRadius, 0, height - mRadius, mLinePaint);
        canvas.drawLine(width, mRadius, width, height - mRadius, mLinePaint);

        RectF rectF = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        canvas.drawArc(rectF, -90, 180, false, mRadiusPaint);

        RectF rectF2 = new RectF(width - mRadius, -mRadius, width + mRadius, mRadius);
        canvas.drawArc(rectF2, 90, 180, false, mRadiusPaint);

        RectF rectF3 = new RectF(width - mRadius, height - mRadius, width + mRadius, height + mRadius);
        canvas.drawArc(rectF3, 180, 270, false, mRadiusPaint);

        RectF rectF4 = new RectF(-mRadius, height - mRadius, mRadius, height + mRadius);
        canvas.drawArc(rectF4, 270, 360, false, mRadiusPaint);


    }

    /**
     * 初始Bitmap对象的缩放裁剪过程
     *
     * @param bmp   初始Bitmap对象
     * @param width 圆形图片直径大小
     * @return 返回一个圆形的缩放裁剪过后的Bitmap对象
     */
    public Bitmap getCroppedBitmap(Bitmap bmp, int width, int height) {

        Bitmap sbmp;
        //比较初始Bitmap宽高和给定的圆形直径，判断是否需要缩放裁剪Bitmap对象
        if (bmp.getWidth() != width || bmp.getHeight() != height) {
            sbmp = Bitmap.createScaledBitmap(bmp, width, height, false);
        } else {
            sbmp = bmp;
        }
        Bitmap outputBitmpa = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outputBitmpa);

        final Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);

        final Rect rect = new Rect(0, 0, width, height);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));

        canvas.drawCircle(0, 0, mRadius, paint);
        canvas.drawCircle(0, sbmp.getHeight(), mRadius, paint);
        canvas.drawCircle(sbmp.getWidth(), 0, mRadius, paint);
        canvas.drawCircle(sbmp.getWidth(), sbmp.getHeight(), mRadius, paint);

        //核心部分，设置两张图片的相交模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return outputBitmpa;
    }

}
