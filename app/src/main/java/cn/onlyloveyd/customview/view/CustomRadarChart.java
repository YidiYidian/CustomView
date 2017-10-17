package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
@SuppressWarnings("ALL")
public class CustomRadarChart extends View {

    /**
     * N 边形，默认为6边形
     */
    private final int DEFAULT_PIECE_NUMBER = 7;

    /**
     * 线条宽度，默认为10px
     */
    private final int DEFAULT_LINE_WIDTH = 4;

    /**
     * 线条颜色,默认为灰色
     */
    private final int DEFAULT_LINE_COLOR = Color.GRAY;

    /**
     * 半径分成N段,默认为4段
     */
    private final int DEFAULT_LINE_SEGMENTS = 4;

    /**
     * 外接圆半径，默认为50px
     */
    private final int DEFAULT_RADIUS = 50;

    /**
     * 文本颜色和文本字体, 默认为黑色，10px
     */
    private final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private final int DEFAULT_TEXT_SIZE = 10;

    /**
     * 覆盖面绘制颜色
     */
    private final int DEFAULT_COVER_COLOR = 0xaacccccc;

    private int mPieceNumber = DEFAULT_PIECE_NUMBER;
    private int mLineWidth = DEFAULT_LINE_WIDTH;
    private int mLineColor = DEFAULT_LINE_COLOR;
    private int mLineSegments = DEFAULT_LINE_SEGMENTS;
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mTextSize = DEFAULT_TEXT_SIZE;
    private int mRadius = DEFAULT_RADIUS;
    private int mCoverColor = DEFAULT_COVER_COLOR;

    private double mAverageAngle = 0;

    private Paint mRadarPaint;
    private Paint mTextPaint;
    private Paint mCoverPaint;
    private Path mCoverPath;

    List<RadarPoints> mRadarPointses = new ArrayList<>();
    List<RadarEntry> mRadarEntries = new ArrayList<>();
    List<PointF> mCoverPoints = new ArrayList<>();

    /**
     * 外接圆中心位置
     */
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
        /**
         * 蜘蛛网Paint初始化
         */
        mRadarPaint = new Paint();
        mRadarPaint.setColor(mLineColor);
        mRadarPaint.setStrokeWidth(mLineWidth);
        mRadarPaint.setAntiAlias(true);
        mRadarPaint.setStyle(Paint.Style.STROKE);

        /**
         * 文字绘制Paint初始化
         */
        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.STROKE);

        /**
         *覆盖面绘制Paint初始化
         */
        mCoverPaint = new Paint();
        mCoverPaint.setColor(mCoverColor);
        mCoverPaint.setAntiAlias(true);
        mCoverPaint.setStyle(Paint.Style.FILL);
        mCoverPath = new Path();

//        mRadarEntries.add(0, new RadarEntry("音乐", 80.0f));
//        mRadarEntries.add(1, new RadarEntry("音乐", 60.0f));
//        mRadarEntries.add(2, new RadarEntry("音乐", 90.0f));
//        mRadarEntries.add(3, new RadarEntry("音乐", 30.0f));
//        mRadarEntries.add(4, new RadarEntry("音乐", 100.0f));
//        mRadarEntries.add(5, new RadarEntry("音乐", 20.0f));
//        mRadarEntries.add(5, new RadarEntry("音乐", 80.0f));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mRadius = Math.min(w / 2, h / 2);
        mPositionX = w / 2;
        mPositionY = h / 2;
        mAverageAngle = 360.0 / mPieceNumber;

        /**
         * 计算每一条轴线上的所有点
         */
        for (int i = 0; i < mPieceNumber; i++) {
            List<PointF> pointFs = new ArrayList<>();
            for (int j = 0; j < mLineSegments; j++) {
                PointF point = new PointF();
                double percent = j * 1.0 / (mLineSegments - 1);
                point.set(getPloyX(mAverageAngle * i, percent),
                        getPloyY(mAverageAngle * i, percent));
                pointFs.add(point);
            }
            RadarPoints radarPoints = new RadarPoints(i, pointFs);
            mRadarPointses.add(radarPoints);
        }

        /**
         * 根据数据集计算覆盖多变形的点
         */
        for (int m = 0; m < mPieceNumber; m++) {
            PointF pointF = new PointF();
            double percent = mRadarEntries.get(m).level / 100.0;
            pointF.set(getPloyX(mAverageAngle * m, percent), getPloyY(mAverageAngle * m, percent));
            mCoverPoints.add(pointF);
        }
    }

    /**
     * 设置数据集
     */
    public void setRadatEntries(List<RadarEntry> entries) {
        this.mRadarEntries = entries;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 绘制中心点
         */
        canvas.drawPoint(mPositionX, mPositionY, mRadarPaint);

        /**
         * 绘制蜘蛛网
         */
        for (int i = 0; i < mLineSegments; i++) {
            for (int j = 0; j < mPieceNumber - 1; j++) {
                canvas.drawLine(mRadarPointses.get(j).getPointFs().get(i).x, mRadarPointses.get(
                        j).getPointFs().get(i).y,
                        mRadarPointses.get(j + 1).getPointFs().get(i).x, mRadarPointses.get(
                                j + 1).getPointFs().get(i).y, mRadarPaint);
            }
            canvas.drawLine(mRadarPointses.get(mPieceNumber - 2).getPointFs().get(i).x,
                    mRadarPointses.get(mPieceNumber - 2).getPointFs().get(i).y,
                    mRadarPointses.get(mPieceNumber - 1).getPointFs().get(i).x, mRadarPointses.get(
                            mPieceNumber - 1).getPointFs().get(i).y, mRadarPaint);

            canvas.drawLine(mRadarPointses.get(mPieceNumber - 1).getPointFs().get(i).x,
                    mRadarPointses.get(mPieceNumber - 1).getPointFs().get(i).y,
                    mRadarPointses.get(0).getPointFs().get(i).x, mRadarPointses.get(
                            0).getPointFs().get(i).y, mRadarPaint);
        }

        /**
         * 绘制轴线
         */
        for (int k = 0; k < mPieceNumber; k++) {
            canvas.drawLine(mRadarPointses.get(k).getPointFs().get(0).x,
                    mRadarPointses.get(k).getPointFs().get(0).y,
                    mRadarPointses.get(k).getPointFs().get(mLineSegments - 1).x, mRadarPointses.get(
                            k).getPointFs().get(mLineSegments - 1).y, mRadarPaint);
        }

        /**
         * 绘制数据
         */
        if (mCoverPoints != null && mCoverPoints.size() == mPieceNumber) {
            mCoverPath.reset();
            mCoverPath.moveTo(mCoverPoints.get(0).x, mCoverPoints.get(0).y);
            for (int i = 1; i < mPieceNumber; i++) {
                System.err.println("yidong -- i = " + i);
                mCoverPath.lineTo(mCoverPoints.get(i).x, mCoverPoints.get(i).y);
            }
            mCoverPath.close();
            canvas.drawPath(mCoverPath, mCoverPaint);
        } else {
            throw new NullPointerException("请先设置数据集");
        }

    }

    public float getPloyX(double angle, double percent) {
        return Float.parseFloat(
                String.valueOf(
                        mPositionX + Math.cos(angle / 360.0 * 2 * Math.PI) * mRadius * percent));
    }

    public float getPloyY(double angle, double percent) {
        return Float.parseFloat(String.valueOf(
                mPositionY + Math.sin(angle / 360.0 * 2 * Math.PI) * mRadius * percent));
    }

    /**
     * 雷达图数据载体
     */
    public static class RadarEntry {
        private String title;
        private Float level;

        public RadarEntry(String title, float level) {
            this.title = title;
            this.level = level;
        }
    }

    /**
     * 每一条线上的所有点集合
     */
    public class RadarPoints {
        int index;
        List<PointF> mPointFs;

        public RadarPoints(int index, List<PointF> pointFs) {
            this.index = index;
            mPointFs = pointFs;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public List<PointF> getPointFs() {
            return mPointFs;
        }

        public void setPointFs(List<PointF> pointFs) {
            mPointFs = pointFs;
        }
    }

}
