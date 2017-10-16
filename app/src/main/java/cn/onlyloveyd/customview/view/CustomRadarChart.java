package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: CustomRadarChart
 * 创 建 人: 易冬
 * 创建日期: 2017/10/16 15:13
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 *
 * @author Mraz
 */
public class CustomRadarChart extends View {
    private final int DEFAULT_PIECE_NUMBER = 6;
    private final int DEFAULT_LINE_WIDTH = 10;
    private final int DEFAULT_RAIUS = 50;
    private final int DEFAULT_LINE_COLOR = Color.GRAY;
    private final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private final int DEFAULT_TEXT_SIZE = 10;

    private final int DEFAULT_LINE_SEGMENTS = 4;

    private int mPieceNumber = DEFAULT_PIECE_NUMBER;
    private int mLineWidth = DEFAULT_LINE_WIDTH;
    private int mLineColor = DEFAULT_LINE_COLOR;
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mTextSize = DEFAULT_TEXT_SIZE;
    private int mRadius = DEFAULT_RAIUS;

    private Paint mRadarPaint;
    private Paint mTextPaint;
    private Path mRadarPath;

    List<String> mTitles = new ArrayList<>();
    List<Integer> mDatas = new ArrayList<>();
    List<PointF> mPoints = new ArrayList<>();

    private int mPositionX = 0;
    private int mPositionY = 0;

    public CustomRadarChart(Context context) {
        this(context, null);
    }

    public CustomRadarChart(Context context,
                            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRadarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRadarPaint = new Paint();
        mRadarPaint.setColor(mLineColor);
        mRadarPaint.setStrokeCap(Paint.Cap.ROUND);
        mRadarPaint.setStrokeWidth(mLineWidth);
        mRadarPaint.setAntiAlias(true);
        mRadarPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mRadarPaint.setColor(mTextColor);
        mRadarPaint.setStrokeCap(Paint.Cap.ROUND);
        mRadarPaint.setTextSize(mTextSize);
        mRadarPaint.setAntiAlias(true);
        mRadarPaint.setStyle(Paint.Style.STROKE);

        mRadarPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mRadius = Math.min(w / 2, h / 2);
        mPositionX = mRadius;
        mPositionY = mRadius;

        System.err.println("yidong -- mPositionX = " + mPositionX + " mPositionY = " + mPositionY);

        double pieceAngle = 360.0/mPieceNumber;

        for(int i=0; i<mPieceNumber;i++) {
            PointF point = new PointF();
            point.set(getPloyX(pieceAngle*i), getPloyY(pieceAngle*i));
            mPoints.add(point);
            System.err.println("yidong -- mPoints = " + mPoints);
        }

    }

    /**
     * 设置雷达图的文字说明
     *
     * @param titles 标题
     */
    public void setTitles(List<String> titles) {
        this.mTitles = titles;
    }

    /**
     * 设置每一项的数值
     *
     * @param data 数值
     */
    public void setData(List<Integer> data) {
        this.mDatas = data;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mRadarPath.reset();
        mRadarPath.moveTo(mPoints.get(0).x, mPoints.get(0).y);

        for(int i=1; i<mPieceNumber ; i++) {
            mRadarPath.lineTo(mPoints.get(i).x, mPoints.get(i).y);
        }
        mRadarPath.close();

        canvas.drawPath(mRadarPath, mRadarPaint);


    }

    public int getPloyX(double angle) {
        System.err.println("yidong -- angle = " + angle);
        return (int) (mPositionX + Math.cos(angle / 360.0 * 2 * Math.PI) * mRadius);
    }

    public int getPloyY(double angle) {
        return (int) (mPositionY + Math.sin(angle / 360.0 * 2 * Math.PI) * mRadius);
    }
}
