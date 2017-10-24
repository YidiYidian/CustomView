package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

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
     * 是否打开
     */
    private boolean isOn = false;

    /**
     * 圆点的半径和坐标位置
     */
    private int mRadius;
    private int centerX, centerY;

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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void init() {
        mSwitchPaint = new Paint();
        mSwitchPaint.setColor(OFF_BACKGROUND_COLOR);
        mSwitchPaint.setStrokeWidth(20f);
        mSwitchPaint.setStrokeCap(Paint.Cap.ROUND);

    }
}
