package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.onlyloveyd.customview.R;

/**
 * 文 件 名: CustomDotIndicator
 * 创 建 人: 易冬
 * 创建日期: 2017/10/12 16:35
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 *
 * @author Mraz
 */
public class CustomDotIndicator extends LinearLayout {
    /**
     * 自定义属性默认值
     **/
    private final int DEFAULT_DOT_RADIUS = 10;
    private final int DEFAULT_DOT_COLOR = Color.GREEN;

    /**
     * 0 空心 1 实心
     */
    private final int DEFAULT_DOT_FILLORSTROKE = 1;

    /**
     * 如果是空心，需要画笔宽度
     */

    private final int DEFAULT_DOT_STROKE_WIDTH = 2;

    /**
     * 自定义属性
     */
    private int mDotRadius = DEFAULT_DOT_RADIUS;
    private int mDotColor = DEFAULT_DOT_COLOR;
    private int mDotFillOrStroke = DEFAULT_DOT_FILLORSTROKE;
    private int mDotStrokeWidth = DEFAULT_DOT_STROKE_WIDTH;

    /**
     * 指示器画笔
     **/
    private Paint mDotPaint;


    /**
     * ViewPager相关
     */

    private ViewPager mViewPager;
    private final LinearLayout.LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams(0,
            LayoutParams.MATCH_PARENT, 1.0f);

    /**
     * Tab宽度和移动偏移，因为绘制位置 = 起点位置（固定） + Offset(随着ViewPager的滚动变化)
     */
    private float mTabWidth;
    private float mTabOffset;

    /**
     * 点指示器的中心点X坐标
     */
    private float mDotStartPosition;

    /**
     * 设置ViewPager
     *
     * @param viewPager 新的ViewPager
     */
    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        if (viewPager.getAdapter() == null || viewPager.getAdapter().getCount() == 0) {
            throw new IllegalArgumentException();
        }
        notifyViewPagerChanged();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
                mTabOffset = mTabWidth * position + mTabWidth * positionOffset;
                invalidate();
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 添加文字Tab
     *
     * @param index 索引
     * @param title tab标题
     */
    private void addTextTab(final int index, String title) {
        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(index);
            }
        });
        this.addView(tab, index, defaultLayoutParams);
    }

    /**
     * 设置新的ViewPager之后对应的视图内容更新
     */
    private void notifyViewPagerChanged() {
        this.removeAllViews();
        int tabCount = mViewPager.getAdapter().getCount();
        for (int i = 0; i < tabCount; i++) {
            addTextTab(i, mViewPager.getAdapter().getPageTitle(i).toString());
        }
    }

    public CustomDotIndicator(Context context) {
        super(context);
    }

    public CustomDotIndicator(Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构造方法
     *
     * @param context      上下文
     * @param attrs        属性值
     * @param defStyleAttr 默认值
     */
    public CustomDotIndicator(Context context,
            @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray attributes = getContext().obtainStyledAttributes(attrs,
                R.styleable.CustomDotIndicator);

        mDotColor = attributes.getColor(R.styleable.CustomDotIndicator_dotColor, DEFAULT_DOT_COLOR);
        mDotRadius = (int) attributes.getDimension(R.styleable.CustomDotIndicator_dotRadius,
                DEFAULT_DOT_RADIUS);
        mDotFillOrStroke = attributes.getInt(R.styleable.CustomDotIndicator_dotFillOrStroke,
                DEFAULT_DOT_FILLORSTROKE);
        mDotStrokeWidth = (int) attributes.getDimension(
                R.styleable.CustomDotIndicator_dotStrokeWidth, DEFAULT_DOT_STROKE_WIDTH);
        attributes.recycle();
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = w * 1.0f / getChildCount();
        mDotStartPosition = mTabWidth / 2;
    }

    /**
     * 初始化画笔
     */
    private void init() {
        mDotPaint = new Paint();
        mDotPaint.setAntiAlias(true);
        mDotPaint.setColor(mDotColor);
        mDotPaint.setStrokeWidth(mDotStrokeWidth);
        mDotPaint.setStrokeCap(Paint.Cap.ROUND);
        mDotPaint.setStyle(mDotFillOrStroke == 0 ? Paint.Style.STROKE : Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mDotStartPosition + mTabOffset, getHeight() - mDotRadius, mDotRadius,
                mDotPaint);
    }
}
