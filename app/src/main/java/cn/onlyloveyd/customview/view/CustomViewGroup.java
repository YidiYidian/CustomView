package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 文 件 名: CustomViewGroup
 * 创 建 人: 易冬
 * 创建日期: 2017/10/18 09:50
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 *
 * @author Mraz
 */
public class CustomViewGroup extends ViewGroup {

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int mode = MeasureSpec.EXACTLY;
            int childWidth = widthSize / 4;
            int childHeight = heightSize / 4;
            view.measure(MeasureSpec.makeMeasureSpec(childWidth, mode),
                    MeasureSpec.makeMeasureSpec(childHeight, mode));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        System.err.println(
                "yidong -- changed = " + changed + " l = " + l + " t = " + t + " r = " + r + " b = "
                        + b);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            System.err.println("yidong width = " + width + " height = " + height);
            switch (i) {
                case 0:
                    view.layout(0, 0, width, height);
                    break;
                case 1:
                    view.layout(r - width, 0, r, height);
                    break;
                case 2:
                    view.layout(r - width, b - height, r, b);
                    break;
                case 3:
                    view.layout(0, b - height, width, b);
                    break;
                default:
                    break;
            }
        }
    }
}
