package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.onlyloveyd.customview.R;

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
    private final int DEFAULT_LINE_COLOR = 0xffd0d6dc;

    /**
     * 半径分成N段,默认为4段，圆心算一段
     */
    private final int DEFAULT_LINE_SEGMENTS = 4;

    /**
     * 外接圆半径，默认为50px
     */
    private final int DEFAULT_RADIUS = 50;

    /**
     * 文本颜色和文本字体, 默认为黑色，10px
     */
    private final int DEFAULT_TEXT_COLOR = 0xff647d91;
    private final int DEFAULT_TEXT_SIZE = 10;

    /**
     * 覆盖面绘制颜色
     */
    private final int DEFAULT_COVER_COLOR = 0x55ced6dc;

    private int mPieceNumber = DEFAULT_PIECE_NUMBER;
    private int mRadius = DEFAULT_RADIUS;

    private int mLineWidth = DEFAULT_LINE_WIDTH;
    private int mLineColor = DEFAULT_LINE_COLOR;
    private int mLineSegments = DEFAULT_LINE_SEGMENTS;
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mTextSize = DEFAULT_TEXT_SIZE;
    private int mCoverColor = DEFAULT_COVER_COLOR;

    private double mAverageAngle = 0;

    private Paint mRadarPaint;
    private TextPaint mTextPaint;
    private Paint mCoverPaint;
    private Path mCoverPath;

    List<RadarPoints> mRadarPointses = new ArrayList<>();
    List<RadarEntry> mRadarEntries = new ArrayList<>();
    List<PointF> mCoverPoints = new ArrayList<>();
    List<PointF> mTextPoints = new ArrayList<>();

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
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.CustomRadarChart);

        mLineWidth = (int) attributes.getDimension(R.styleable.CustomRadarChart_radarLineWidth, DEFAULT_LINE_WIDTH);
        mLineColor = attributes.getColor(R.styleable.CustomRadarChart_radarLineColor, DEFAULT_LINE_COLOR);
        mLineSegments = attributes.getInteger(R.styleable.CustomRadarChart_radarLineSegments, DEFAULT_LINE_SEGMENTS);
        mTextColor = attributes.getColor(R.styleable.CustomRadarChart_radarTextColor, DEFAULT_TEXT_COLOR);

        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                attributes.getInteger(R.styleable.CustomRadarChart_radarTextSize, DEFAULT_TEXT_SIZE), getResources().getDisplayMetrics());
        mCoverColor = (int) attributes.getColor(R.styleable.CustomRadarChart_radarCoverColor, DEFAULT_COVER_COLOR);

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
        mTextPaint = new TextPaint();
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
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPositionX = w / 2;
        mPositionY = h / 2;
        mAverageAngle = 360.0 / mPieceNumber;

        int max = 0;
        for(RadarEntry entry : mRadarEntries) {
            Rect textBound = new Rect();
            mTextPaint.getTextBounds(entry.title, 0, entry.title.length(),
                    textBound);
            max = Math.max(textBound.width(), max);
        }
        mRadius = Math.min(w / 2 - max, h / 2);

        if (mRadarEntries==null || mRadarEntries.size()==0) {
            throw new NullPointerException("请先设置数据集");
        }
        /**
         * 计算每一条轴线上的所有点
         */
        for (int i = 0; i < mPieceNumber; i++) {
            List<PointF> pointFs = new ArrayList<>();
            for (int j = 0; j < mLineSegments; j++) {
                PointF point = new PointF();
                double percent = j * 1.0 / (mLineSegments - 1);
                point.set(getPloygonX(mAverageAngle * i, percent),
                        getPloygonY(mAverageAngle * i, percent));
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
            pointF.set(getPloygonX(mAverageAngle * m, percent),
                    getPloygonY(mAverageAngle * m, percent));
            mCoverPoints.add(pointF);
        }

        /**
         * 设置文字显示位置
         */
        for (int m = 0; m < mPieceNumber; m++) {
            PointF pointF = new PointF();
            String title = mRadarEntries.get(m).title;
            Rect textBound = new Rect();
            mTextPaint.getTextBounds(title, 0, title.length(),
                    textBound);
            float boundx = mRadarPointses.get(m).getPointFs().get(mLineSegments -1).x;
            float boundy = mRadarPointses.get(m).getPointFs().get(mLineSegments -1).y;
            if( boundx > mRadius && boundy <= mRadius) {
                pointF.set(getPloygonX(mAverageAngle * m, 1),
                        getPloygonY(mAverageAngle * m, 1) - textBound.height()*2) ;
            } else if ( boundx <= mRadius && boundy <= mRadius){
                pointF.set(getPloygonX(mAverageAngle * m, 1) - textBound.width(),
                        getPloygonY(mAverageAngle * m, 1) - textBound.height()*2);
            } else if( boundx <= mRadius && boundy > mRadius) {
                pointF.set(getPloygonX(mAverageAngle * m, 1) - textBound.width(),
                        getPloygonY(mAverageAngle * m, 1) );
            } else {
                pointF.set(getPloygonX(mAverageAngle * m, 1),
                        getPloygonY(mAverageAngle * m, 1));
            }
            mTextPoints.add(pointF);
        }
    }

    /**
     * 设置数据集，数据集的index决定位置，顺时针方向，起始角度为0度
     */
    public void setRadatEntries(List<RadarEntry> entries) {
        this.mRadarEntries = entries;
        mPieceNumber = entries.size();
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
                mCoverPath.lineTo(mCoverPoints.get(i).x, mCoverPoints.get(i).y);
            }
            mCoverPath.close();
            canvas.drawPath(mCoverPath, mCoverPaint);
        } else {
            throw new NullPointerException("请先设置数据集");
        }

        /**
         * 绘制文字,使用StaticLayout进行换行文字的绘制
         */
        for (int i = 0; i < mPieceNumber; i++) {
            canvas.save();
            String str= mRadarEntries.get(i).title + "\r\n" + Math.floor(mRadarEntries.get(i).level*10)/10;
            StaticLayout layout = new StaticLayout(str, mTextPaint, 300,
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            canvas.translate(mTextPoints.get(i).x,mTextPoints.get(i).y);
            layout.draw(canvas);
            canvas.restore();
        }

    }

    public float getPloygonX(double angle, double percent) {
        return Float.parseFloat(
                String.valueOf(
                        mPositionX + Math.cos(angle / 360.0 * 2 * Math.PI) * mRadius * percent));
    }

    public float getPloygonY(double angle, double percent) {
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
