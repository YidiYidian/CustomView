package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 文 件 名: CleanEditText
 * 创 建 人: 易冬
 * 创建日期: 2017/12/25 08:50
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 * @author Mraz
 */
public class CustomCleanableEditText extends AppCompatEditText implements TextWatcher, View.OnFocusChangeListener {
    /**
     * 左右两侧图片资源
     */
    private Drawable left, right;
    /**
     * 是否获取焦点，默认没有焦点
     */
    private boolean hasFocus = false;

    public CustomCleanableEditText(Context context) {
        this(context, null);
    }

    public CustomCleanableEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public CustomCleanableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWidgets();
    }

    /**
     * 初始化各组件
     */
    private void initWidgets() {
        try {
            left = getCompoundDrawables()[0];
            right = getCompoundDrawables()[2];
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        try {
            setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
            addListeners();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加事件监听
     */
    private void addListeners() {
        try {
            setOnFocusChangeListener(this);
            addTextChangedListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (hasFocus) {
            if (TextUtils.isEmpty(s)) {
                setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
            } else {
                if (null == right) {
                    right = getCompoundDrawables()[2];
                }
                setCompoundDrawablesWithIntrinsicBounds(left, null, right, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    int x = (int) event.getX();
                    if ((getWidth() - x) <= getCompoundPaddingRight()) {
                        if (!TextUtils.isEmpty(getText().toString())) {
                            setText("");
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        try {
            this.hasFocus = hasFocus;
            String msg=getText().toString();

            if(hasFocus){
                if(msg.equalsIgnoreCase("")){
                    setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                }else{
                    setCompoundDrawablesWithIntrinsicBounds(left, null, right, null);
                }
            } else {
                setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
