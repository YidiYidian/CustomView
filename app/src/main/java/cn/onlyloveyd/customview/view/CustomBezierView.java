package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: CustomBezierView
 * 创 建 人: 易冬
 * 创建日期: 2017/10/20 10:52
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 *
 * @author Mraz
 */
public class CustomBezierView extends View {
    List<PointF> mDataPoints;
    List<PointF> mControlPoints;
    private int DEFAULT_BEZIER_COLOR = 0xFFFF4081;
    /**
     * 绘制Bezier曲线画笔
     */
    private Paint mBezierPaint;
    /**
     * Bezier曲线Path
     */
    private Path mBezierPath;
    private int mSengmentNumbers = 4;

    private int oppo = 1;
    private int BASE_LINE = 250;
    private int mStartY = -1;
    private int direction = 1;


    private int mBezierColor = DEFAULT_BEZIER_COLOR;

    public CustomBezierView(Context context) {
        this(context, null);
    }

    public CustomBezierView(Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 画笔等初始化
     */
    private void init() {
        mBezierPaint = new Paint();
        mBezierPaint.setAntiAlias(true);
        mBezierPaint.setStyle(Paint.Style.STROKE);
        mBezierPaint.setStrokeWidth(4.0f);
        mBezierPaint.setColor(mBezierColor);

        mBezierPath = new Path();

        mDataPoints = new ArrayList<>();
        mControlPoints = new ArrayList<>();
        mStartY = 50;

        this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //2.开启子线程对绘制用到的数据进行修改
                new DrawThread();
                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });

    }

    public void setStartY(int startY) {
        this.mStartY = startY;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        System.err.println("yidong -- onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.err.println("yidong -- onDraw");
        super.onDraw(canvas);

        if (mStartY > BASE_LINE) {
            oppo = 1;
        } else {
            oppo = -1;
        }

        for (int i = 0; i < mSengmentNumbers * 2 + 1; i++) {
            if (i % 2 == 0) {
                mDataPoints.add(new PointF(i * getWidth() / (2 * mSengmentNumbers), mStartY));
            } else {
                mControlPoints.add(
                        new PointF(i * getWidth() / (2 * mSengmentNumbers), mStartY - oppo * 100));
                oppo = -oppo;
            }
        }

        mBezierPath.reset();
        mBezierPath.moveTo(mDataPoints.get(0).x, mDataPoints.get(0).y);
        for (int i = 0; i < mSengmentNumbers; i++) {
            mBezierPath.quadTo(mControlPoints.get(i).x, mControlPoints.get(i).y,
                    mDataPoints.get(i + 1).x, mDataPoints.get(i + 1).y);
        }
        canvas.drawPath(mBezierPath, mBezierPaint);
    }

    public class DrawThread implements Runnable {
        //2.开启子线程,并通过绘制监听实时更新绘制数据
        private final Thread mDrawThread;
        int count;
        private int statek;

        public DrawThread() {
            mDrawThread = new Thread(this);
            mDrawThread.start();
        }

        @Override
        public void run() {
            while (true) {
                switch (statek) {
                    case 0://给一点点缓冲的时间
                        try {
                            Thread.sleep(200);
                            statek = 1;
                        } catch (InterruptedException e) {

                        }
                        break;
                    case 1:
                        try {//更新显示的数据
                            Thread.sleep(20);
                            setStartY(mStartY);
                            if (mStartY > 550) {
                                direction = -direction;
                            }
                            if (mStartY < 50) {
                                direction = -direction;
                            }
                            mStartY += 100 * direction;
                            count++;
                            postInvalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
                if (count >= 100) {//满足该条件就结束循环
                    break;
                }
            }

        }
    }
}
