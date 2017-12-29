package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 文 件 名: CustomCountDownView
 * 创 建 人: 易冬
 * 创建日期: 2017/12/29 08:50
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 * @author Mraz
 */
public class CustomCountDownView extends View {

    private Context mContext;

    private final int  fDefaultRoundRadius = 40;
    private final int fDefaultRoundColor = Color.RED;
    private final int fDefaultCircleWidth = 10;
    private final int fDefaultCircleColor = Color.BLACK;
    private final int fDefaultCountdownTime = 5;


    private int mRoundRadius = fDefaultRoundRadius;
    private int mRoundColor = fDefaultRoundColor;
    private int mCircleWidth = fDefaultCircleWidth;
    private int mCircleColor = fDefaultCircleColor;
    private int mCountdownTime = fDefaultCountdownTime;

    private Paint mRoundPaint;
    private Paint mCirclePaint;


    public CustomCountDownView(Context context) {
        this(context,null);
    }

    public CustomCountDownView(Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        mRoundRadius = dip2px(mContext, fDefaultRoundRadius);
        mCircleWidth = dip2px(mContext, fDefaultCircleWidth);
        initPaint();
    }

    private void initPaint(){
        mRoundPaint = new Paint();
        mRoundPaint.setColor(mRoundColor);
        mRoundPaint.setAntiAlias(true);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mCircleWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = (mCircleWidth + mRoundRadius) *2;
        System.err.println("yidong -- width = " + width);
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        System.err.println("yidong -- getX = " + getX() + " getY = " + getY());
        canvas.drawCircle(getX() + mRoundRadius + mCircleWidth, getY() +mRoundRadius + mCircleWidth, mRoundRadius, mRoundPaint);
        System.err.println("yidong -- getWidth = " + getWidth());
        canvas.drawArc(getX() + mCircleWidth/2, getY() + mCircleWidth/2, getX()+getWidth() - mCircleWidth/2, getY() + getHeight()- mCircleWidth/2, 0f, 180f,false, mCirclePaint);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
