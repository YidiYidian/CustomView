package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
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
        System.err.println(
                "yidong -- changed = " + changed + " l = " + l + " t = " + t + " r = " + r + " b = "
                        + b);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int gravity = lp.gravity;
            final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;
            final int horizontalGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
            System.err.println(
                    "yidong -- verticalGravity = " + verticalGravity + " horizontalGravity = "
                            + horizontalGravity);
            System.err.println("yidong -- width = " + width + " height = " + height);

            int cl, ct, cr, cb;
            switch (verticalGravity) {
                case Gravity.TOP:
                    ct = t;
                    cb = height;
                    break;
                case Gravity.BOTTOM:
                    ct = b - height;
                    cb = b;
                    break;
                default:
                    ct = t;
                    cb = height;
                    break;
            }
            switch (horizontalGravity) {
                case Gravity.LEFT:
                    cl = l;
                    cr = width;
                    break;
                case Gravity.RIGHT:
                    cl = r - width;
                    cr = r;
                    break;
                default:
                    cl = l;
                    cr = width;
                    break;
            }
            child.layout(cl, ct, cr, cb);
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public static final int UNSPECIFIED_GRAVITY = -1;

        public int gravity = UNSPECIFIED_GRAVITY;

        public LayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
            super(c, attrs);

            final TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CustomViewGroup);
            gravity = a.getInt(R.styleable.CustomViewGroup_custom_gravity, UNSPECIFIED_GRAVITY);
            a.recycle();
        }

        /**
         * Creates a new set of layout parameters with the specified width, height
         * and weight.
         *
         * @param width   the width, either {@link #MATCH_PARENT},
         *                {@link #WRAP_CONTENT} or a fixed size in pixels
         * @param height  the height, either {@link #MATCH_PARENT},
         *                {@link #WRAP_CONTENT} or a fixed size in pixels
         * @param gravity the gravity
         * @see android.view.Gravity
         */
        public LayoutParams(int width, int height, int gravity) {
            super(width, height);
            this.gravity = gravity;
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(@NonNull ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        /**
         * Copy constructor. Clones the width, height, margin values, and
         * gravity of the source.
         *
         * @param source The layout params to copy from.
         */
        public LayoutParams(@NonNull LayoutParams source) {
            super(source);

            this.gravity = source.gravity;
        }
    }

}
