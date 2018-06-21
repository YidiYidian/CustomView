package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import cn.onlyloveyd.customview.R;
import cn.onlyloveyd.customview.utils.CountDownTimer;


/**
 * @author Mraz
 */
public class CountDownTextView extends AppCompatTextView {
    private static final int DEFAULT_COUNT_DOWN_SECOND = 30;
    private String mPendingText;
    private String mCountDownText;
    private int mCountDownSecond;
    /**
     * 在倒计时期间是否变为不可用状态
     */
    private boolean mEnableChangeable = true;

    protected CountDownFinishListener mFinishListener;
    private TickTimer mTickTimer;
    private boolean isStart;

    public CountDownTextView(Context context) {
        this(context, null);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CountDownTextView, defStyleAttr, 0);
        String pendingText = a.getString(R.styleable.CountDownTextView_pendingText);
        String countDownText = a.getString(R.styleable.CountDownTextView_countDownText);
        int countDownSecond = a.getInt(R.styleable.CountDownTextView_countDownSecond, DEFAULT_COUNT_DOWN_SECOND);
        a.recycle();
        init(pendingText, countDownText, countDownSecond);
    }

    public CountDownTextView(Context context, String pendingText, String countDownText, int countDownSecond) {
        super(context);
        init(pendingText, countDownText, countDownSecond);
    }

    private void init(String pendingText, String countDownText, int countDownSecond) {
        mPendingText = pendingText;
        setText(mPendingText);
        mCountDownText = countDownText == null ? "%ds" : countDownText;
        mCountDownSecond = countDownSecond <= 0 ? DEFAULT_COUNT_DOWN_SECOND : countDownSecond;
    }

    public synchronized void start() {
        if (!isStart) {
            mTickTimer = new TickTimer(mCountDownSecond * 1000, 1000);
            mTickTimer.start();
            onStartCountDown();
        }
    }

    public synchronized void start(int countDownSecond) {
        if (!isStart) {
            mCountDownSecond = countDownSecond <= 0 ? DEFAULT_COUNT_DOWN_SECOND : countDownSecond;
            mTickTimer = new TickTimer(mCountDownSecond * 1000, 1000);
            mTickTimer.start();
            onStartCountDown();
        }
    }

    public synchronized void stop() {
        if (isStart) {
            mTickTimer.cancel();
            onFinishCountDown();
        }
    }

    private void onStartCountDown() {
        isStart = true;
        if (mEnableChangeable) {
            setEnabled(false);
        }
    }

    private void onFinishCountDown() {
        isStart = false;
        if (mEnableChangeable) {
            setEnabled(true);
        }
        setText(mPendingText);
    }

    public boolean isStart() {
        return isStart;
    }

    public void setEnableChangeable(boolean b) {
        this.mEnableChangeable = b;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    private class TickTimer extends CountDownTimer {

        private TickTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long secondUntilFinished = millisUntilFinished / 1000;
            secondUntilFinished = secondUntilFinished < mCountDownSecond ? secondUntilFinished + 1 : secondUntilFinished;
            try {
                setText(String.format(mCountDownText, secondUntilFinished));
            } catch (Exception e) {
                setText(mCountDownText);
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {
            onFinishCountDown();
            if (mFinishListener != null) {
                mFinishListener.onFinish();
            }
        }
    }

    public void setFinishListener(CountDownFinishListener l) {
        this.mFinishListener = l;
    }

    public interface CountDownFinishListener {
        void onFinish();
    }
}
