package com.zhy.sample_okhttp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;


/**
 * @author kevin
 * @date 2018/7/24 00:00
 * <p>
 * RemoteView 不支持自定义的View 为了实现在notification 中显示自定义的进度条样式
 * 故将其转化成bitmap用imageview来显示，本来是将自定义的进度条转化为bitmap的工具类
 * </p>
 */
public class ProgressBarBitmapUtils {

    private Paint completePaint;
    private Paint unCompletePaint;

    private Paint progressPaint;
    private Paint precentMaskPaint;
    private String progressText;
    private boolean showText = true;

    private RectF rectF = new RectF();
    private Rect rect = new Rect();
    private int process = 50;

    private int width;
    private int heigth;
    private int strokeWidth;
    private Context context;


    public ProgressBarBitmapUtils(Context context) {

        this.context = context;

        strokeWidth = ScreenUtil.dip2px(context, 3);
        completePaint = new Paint();
        completePaint.setAntiAlias(true);
        completePaint.setColor(Color.WHITE);
        completePaint.setStyle(Paint.Style.STROKE);
        completePaint.setStrokeWidth(strokeWidth);

        unCompletePaint = new Paint();
        unCompletePaint.setAntiAlias(true);
        unCompletePaint.setColor(Color.parseColor("#66ffffff"));
        unCompletePaint.setStyle(Paint.Style.STROKE);
        unCompletePaint.setStrokeWidth(strokeWidth);


        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setTextAlign(Paint.Align.LEFT);
        progressPaint.setColor(Color.WHITE);
        progressPaint.setTextSize(ScreenUtil.dip2px(context, 18));

        precentMaskPaint = new Paint();
        precentMaskPaint.setColor(Color.WHITE);
        precentMaskPaint.setAntiAlias(true);
        precentMaskPaint.setTextSize(ScreenUtil.dip2px(context, 10));


        heigth = width = ScreenUtil.dip2px(context, 48);
        progressText = String.valueOf(process);


    }

    public synchronized void setProgress(int progress) {
        this.process = progress;
        progressText = String.valueOf(progress);

    }

    private int getFontWidth(Paint paint, String text) {
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.width();
    }

    private int getFontHeight(Paint paint, String text) {

        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    public Bitmap getBitmap() {

        Bitmap mbmpTest = Bitmap.createBitmap(width, heigth, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mbmpTest);
        rectF.set(strokeWidth, strokeWidth, width - strokeWidth, heigth - strokeWidth);

        if (process > 100) {
            process = 100;
        }

        float p = 360 * process / 100.0f;

        canvas.drawArc(rectF, 270, p, false, completePaint);
        canvas.drawArc(rectF, 270 + p, 360 - p, false, unCompletePaint);

        if (showText && progressText != null) {

            float progressWidth = getFontWidth(progressPaint, progressText);
            float progressHeight = getFontHeight(progressPaint, progressText);
            float maskWidth = getFontWidth(precentMaskPaint, "%");
            float maskPadding = ScreenUtil.dip2px(context, 2);

            canvas.drawText(progressText, width / 2 - progressWidth / 2 - maskWidth / 2 - maskPadding / 2, heigth / 2 + progressHeight / 2, progressPaint);
            canvas.drawText("%", heigth / 2 + progressWidth / 2 - maskWidth / 2 + maskPadding / 2, heigth / 2 + progressHeight / 2, precentMaskPaint);
        }

        return mbmpTest;


    }


}
