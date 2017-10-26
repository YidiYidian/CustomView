package cn.onlyloveyd.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 文 件 名: CustomCheckView
 * 创 建 人: 易冬
 * 创建日期: 2017/10/24 16:31
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 *
 * @author Mraz
 */
public class CustomSwitchView extends View {
    /**
     * 开关圆点颜色
     */
    private final int SWITCH_DOT_COLOR = 0xffffffff;

    /**
     * 关闭状态下的背景颜色
     */
    private final int OFF_BACKGROUND_COLOR = 0xffe2e2e2;

    /**
     * 打开状态下的背景颜色
     */
    private final int ON_BACKGROUND_COLOR = 0xff007dff;

    /**
     * 边界和开关圆点的间距
     */
    private final int BOUND_DOT_GAP = 8;

    /**
     * 是否打开
     */
    private boolean isOn = false;

    /**
     * 圆点的半径和坐标位置
     */
    private int mRadius;

    private int startX;
    private int endX;
    private float centerX;
    private float centerY;
    private RectF mRectF;

    /**
     * 开关的画笔
     */
    private Paint mSwitchPaint;

    public CustomSwitchView(Context context) {
        this(context, null);
    }

    public CustomSwitchView(Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 打开或者关闭
     *
     * @param on true 打开， false 关闭
     */
    public void setOn(boolean on) {
        isOn = on;
        ValueAnimator animator;
        if (on) {
            animator = ValueAnimator.ofFloat(centerX, endX);
        } else {
            animator = ValueAnimator.ofFloat(centerX, startX);
        }
        animator.setDuration(200);
        animator.setRepeatCount(0);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                centerX = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    private void init() {
        mSwitchPaint = new Paint();
        mSwitchPaint.setColor(OFF_BACKGROUND_COLOR);
        mSwitchPaint.setStrokeWidth(10f);
        mSwitchPaint.setStrokeCap(Paint.Cap.ROUND);
        mSwitchPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF = new RectF(getLeft(), getTop(), getRight(), getBottom());
        mRadius = h / 2 - BOUND_DOT_GAP;
        startX = getLeft() + mRadius + BOUND_DOT_GAP / 2;
        endX = getRight() - mRadius - BOUND_DOT_GAP / 2;
        centerX = startX;
        centerY = (getTop() + getBottom()) / 2.0f;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isOn) {
            mSwitchPaint.setColor(ON_BACKGROUND_COLOR);
        } else {
            mSwitchPaint.setColor(OFF_BACKGROUND_COLOR);
        }
        canvas.drawRoundRect(mRectF, mRadius, mRadius, mSwitchPaint);
        mSwitchPaint.setColor(SWITCH_DOT_COLOR);
        canvas.drawCircle(centerX, centerY, mRadius, mSwitchPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                setOn(!isOn);
                break;
            case MotionEvent.ACTION_MOVE: {
                float x = event.getX();
                if (x < endX && x > startX) {
                    centerX = x;
                    invalidate();
                }
            }
            break;
            default:
                break;
        }
        return true;
    }
}
