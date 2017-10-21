package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import cn.onlyloveyd.customview.R;

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

        /**
         * 每个子View的大小都设置成相同大小
         */
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int mode = MeasureSpec.EXACTLY;
            int childWidth = widthSize / 4;
            int childHeight = heightSize / 4;
            child.measure(MeasureSpec.makeMeasureSpec(childWidth, mode),
                    MeasureSpec.makeMeasureSpec(childHeight, mode));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int gravity = lp.gravity;
            switch (gravity) {
                case LayoutParams.TOPLEFT_GRAVITY:
                    child.layout(l, t, width, height);
                    break;
                case LayoutParams.TOPRIGHT_GRAVITY:
                    child.layout(r - width, t, r, height);
                    break;
                case LayoutParams.BOTTOMLEFT_GRAVITY:
                    child.layout(l, b - height, width, b);
                    break;
                case LayoutParams.BOTTOMRIGHT_GRAVITY:
                    child.layout(r - width, b - height, r, b);
                    break;
                case LayoutParams.CENTER_GRAVITY:
                    child.layout((r - width) / 2, (b - height) / 2, (r + width) / 2,
                            (b + height) / 2);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
    }


    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public static final int UNSPECIFIED_GRAVITY = -1;
        public static final int TOPLEFT_GRAVITY = 1;
        public static final int TOPRIGHT_GRAVITY = 2;
        public static final int BOTTOMLEFT_GRAVITY = 3;
        public static final int BOTTOMRIGHT_GRAVITY = 4;
        public static final int CENTER_GRAVITY = 5;

        public int gravity = UNSPECIFIED_GRAVITY;

        public LayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
            super(c, attrs);

            final TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CustomViewGroup);
            gravity = a.getInt(R.styleable.CustomViewGroup_custom_gravity, UNSPECIFIED_GRAVITY);
            a.recycle();
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height);
            this.gravity = gravity;
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams source) {
            super(source);
            this.gravity = TOPLEFT_GRAVITY;
        }

        public LayoutParams(@NonNull LayoutParams source) {
            super(source);

            this.gravity = source.gravity;
        }
    }

}
