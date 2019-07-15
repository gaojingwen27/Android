package com.bytedance.clockapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.Locale;

public class Clock extends View {

    private final static String TAG = Clock.class.getSimpleName();

    private static final int FULL_ANGLE = 360;

    private static final int CUSTOM_ALPHA = 140;
    private static final int FULL_ALPHA = 255;

    private static final int DEFAULT_PRIMARY_COLOR = Color.WHITE;
    private static final int DEFAULT_SECONDARY_COLOR = Color.LTGRAY;

    private static final float DEFAULT_DEGREE_STROKE_WIDTH = 0.010f;

    public final static int AM = 0;

    private static final int RIGHT_ANGLE = 90;

    private int mWidth, mCenterX, mCenterY, mRadius;

    /**
     * properties
     */
    private int centerInnerColor;
    private int centerOuterColor;

    private int secondsNeedleColor;
    private int hoursNeedleColor;
    private int minutesNeedleColor;

    private int degreesColor;

    private int hoursValuesColor;

    private int numbersColor;

    private boolean mShowAnalog = true;

    private Handler mHandler;
    private Runnable clockRunnable;

    private Rect textBounds = new Rect();
    private Calendar cal;

    private  String[] clockNumbers = {"12","1","2","3","4","5","6","7","8","9","10","11"};

    public Clock(Context context) {
        super(context);
        init(context, null);
    }

    public Clock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Clock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        if (widthWithoutPadding > heightWithoutPadding) {
            size = heightWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }

        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    private void init(Context context, AttributeSet attrs) {

        this.centerInnerColor = Color.LTGRAY;
        this.centerOuterColor = DEFAULT_PRIMARY_COLOR;

        this.secondsNeedleColor = DEFAULT_SECONDARY_COLOR;
        this.hoursNeedleColor = DEFAULT_PRIMARY_COLOR;
        this.minutesNeedleColor = DEFAULT_PRIMARY_COLOR;

        this.degreesColor = DEFAULT_PRIMARY_COLOR;

        this.hoursValuesColor = DEFAULT_PRIMARY_COLOR;

        numbersColor = Color.WHITE;

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        mWidth = getHeight() > getWidth() ? getWidth() : getHeight();

        int halfWidth = mWidth / 2;
        mCenterX = halfWidth;
        mCenterY = halfWidth;
        mRadius = halfWidth;

        if (mShowAnalog) {
            drawDegrees(canvas);
            drawHoursValues(canvas);
            drawNeedles(canvas);
            drawCenter(canvas);
        } else {
            drawNumbers(canvas);
        }

    }

    private void drawDegrees(Canvas canvas) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(mWidth * DEFAULT_DEGREE_STROKE_WIDTH);
        paint.setColor(degreesColor);

        int rPadded = mCenterX - (int) (mWidth * 0.01f);
        int rEnd = mCenterX - (int) (mWidth * 0.05f);

        for (int i = 0; i < FULL_ANGLE; i += 6 /* Step */) {

            if ((i % RIGHT_ANGLE) != 0 && (i % 15) != 0)
                paint.setAlpha(CUSTOM_ALPHA);
            else {
                paint.setAlpha(FULL_ALPHA);
            }

            int startX = (int) (mCenterX + rPadded * Math.cos(Math.toRadians(i)));
            int startY = (int) (mCenterX - rPadded * Math.sin(Math.toRadians(i)));

            int stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(i)));
            int stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(i)));

            canvas.drawLine(startX, startY, stopX, stopY, paint);

        }
    }

    /**
     * @param canvas
     */
    private void drawNumbers(Canvas canvas) {

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(mWidth * 0.2f);
        textPaint.setColor(numbersColor);
        textPaint.setColor(numbersColor);
        textPaint.setAntiAlias(true);

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR)+8;
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int amPm = calendar.get(Calendar.AM_PM);

        String time = String.format("%s:%s:%s%s",
                String.format(Locale.getDefault(), "%02d", hour),
                String.format(Locale.getDefault(), "%02d", minute),
                String.format(Locale.getDefault(), "%02d", second),
                amPm == AM ? "AM" : "PM");

        SpannableStringBuilder spannableString = new SpannableStringBuilder(time);
        spannableString.setSpan(new RelativeSizeSpan(0.3f), spannableString.toString().length() - 2, spannableString.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // se superscript percent

        StaticLayout layout = new StaticLayout(spannableString, textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1, 1, true);
        canvas.translate(mCenterX - layout.getWidth() / 2f, mCenterY - layout.getHeight() / 2f);
        layout.draw(canvas);
    }

    /**
     * Draw Hour Text Values, such as 1 2 3 ...
     *
     * @param canvas
     */
    private void drawHoursValues(Canvas canvas) {
        // Default Color:
        // - hoursValuesColor
        Paint textPaint = new Paint();
        textPaint.setColor(hoursValuesColor);
        textPaint.setTextSize(50);

        canvas.save();
        int preX = getWidth() / 2;
        int preY = 150;
        int x, y;
        int degree = 360 / clockNumbers.length;

        canvas.save();
        for(int i = 0; i < clockNumbers.length; i++){
             String num = clockNumbers[i];
             textPaint.getTextBounds(num, 0, num.length(), textBounds);
             x = (int) (preX - textPaint.measureText(num) / 2);
             y = preY - textBounds.height();
             canvas.drawText(num,x,y,textPaint);
             canvas.rotate(degree,getWidth()/2,getHeight()/2);
        }
        canvas.restore();
    }

    /**
     * Draw hours, minutes needles
     * Draw progress that indicates hours needle disposition.
     *
     * @param canvas
     */
    private void drawNeedles(final Canvas canvas) {
        // Default Color:
        // - secondsNeedleColor
        // - hoursNeedleColor
        // - minutesNeedleColor
        Paint secondsNeedlePaint = new Paint();
        Paint hoursNeedlePaint = new Paint();
        Paint minutesNeedlePaint = new Paint();
        secondsNeedlePaint.setColor(secondsNeedleColor);
        secondsNeedlePaint.setStrokeWidth(5);
        secondsNeedlePaint.setAntiAlias(true);
        secondsNeedlePaint.setStrokeCap(Paint.Cap.ROUND);

        minutesNeedlePaint.setColor(minutesNeedleColor);
        minutesNeedlePaint.setStrokeWidth(10);
        minutesNeedlePaint.setAntiAlias(true);
        minutesNeedlePaint.setStrokeCap(Paint.Cap.ROUND);

        hoursNeedlePaint.setColor(hoursNeedleColor);
        hoursNeedlePaint.setStrokeWidth(12);
        hoursNeedlePaint.setAntiAlias(true);
        hoursNeedlePaint.setStrokeCap(Paint.Cap.ROUND);


        cal = Calendar.getInstance();
        int hour,min,second;
        float hourAngle,minAngle,secAngle;
        hour = cal.get(Calendar.HOUR)+8;//Calendar.HOUR获取的是12小时制，Calendar.HOUR_OF_DAY获取的是24小时制
        min = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);

        hourAngle = (float)hour / 12 * 360 + (float)min / 60 * (360 / 12);//360/12是指每个数字之间的角度
        minAngle = (float)min / 60 * 360 + (float)second / 60 * (360 / 60);
        secAngle = (float)second / 60 * 360;


        canvas.save();
        canvas.rotate(hourAngle,getWidth()/2,getHeight()/2);
        canvas.drawLine(getWidth()/2,getHeight()/2,getWidth()/2,getHeight()/2-100, hoursNeedlePaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(minAngle,getWidth()/2,getHeight()/2);
        canvas.drawLine(getWidth()/2,getHeight()/2,getWidth()/2,getHeight()/2-150, minutesNeedlePaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(secAngle,getWidth()/2,getHeight()/2);
        canvas.drawLine(getWidth()/2,getHeight()/2,getWidth()/2,getHeight()/2-200, secondsNeedlePaint);
        canvas.restore();



    }

    /**
     * Draw Center Dot
     *
     * @param canvas
     */
    private void drawCenter(Canvas canvas) {
        // Default Color:
        // - centerInnerColor
        // - centerOuterColor
        Paint centerInnerPaint = new Paint();
        Paint centerOuterPaint = new Paint();
        centerInnerPaint.setColor(centerInnerColor);
        centerOuterPaint.setColor(centerOuterColor);
        centerOuterPaint.setStrokeWidth(6);

        centerInnerPaint.setStyle(Paint.Style.FILL);
        centerOuterPaint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(getWidth()/2, getHeight()/2, 12, centerInnerPaint);
        canvas.drawCircle(getWidth()/2, getHeight()/2, 15, centerOuterPaint);
    }

    public void setShowAnalog(boolean showAnalog) {
        mShowAnalog = showAnalog;
        invalidate();
    }

    public boolean isShowAnalog() {
        return mShowAnalog;
    }

}