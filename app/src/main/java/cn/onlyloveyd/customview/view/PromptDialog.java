package cn.onlyloveyd.customview.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import cn.onlyloveyd.customview.R;
import cn.onlyloveyd.customview.databinding.DialogPromptBinding;


public class PromptDialog extends Dialog {
    private static final String DEFAULT_ENSURE_TEXT = "确定";
    private static final String DEFAULT_CANCEL_TEXT = "取消";

    private DialogPromptBinding mPromptBinding;
    private String mContentText;
    private String mEnsureText;
    private View.OnClickListener mEnsureListener;
    private String mCancelText;
    private View.OnClickListener mCancelListener;

    public static PromptDialog show(Context context, String content) {
        return show(context, content, DEFAULT_ENSURE_TEXT, null, DEFAULT_CANCEL_TEXT, null);
    }

    public static PromptDialog show(Context context, String content, String ensure, View.OnClickListener ensureListener) {
        return show(context, content, ensure, ensureListener, null, null);
    }

    public static PromptDialog show(Context context, String content, String ensure, View.OnClickListener ensureListener,
                                    String cancel, View.OnClickListener cancelListener) {
        PromptDialog promptDialog = new PromptDialog(context, content, true, ensure, ensureListener,
                cancel, cancelListener);
        promptDialog.show();
        return promptDialog;
    }

    public PromptDialog(Context context, String content) {
        this(context, content, true, DEFAULT_ENSURE_TEXT, null, DEFAULT_CANCEL_TEXT, null);
    }

    public PromptDialog(@NonNull Context context, String content, String ensure, String cancel) {
        this(context, content, true, ensure, null, cancel, null);
    }

    public PromptDialog(@NonNull Context context, String content, boolean cancelable, String ensure, View.OnClickListener ensureListener,
                        String cancel, View.OnClickListener cancelListener) {
        super(context, R.style.NoFrameDialog);
        this.mContentText = content;
        this.mEnsureText = ensure;
        this.mEnsureListener = ensureListener;
        this.mCancelText = cancel;
        this.mCancelListener = cancelListener;
        setCancelable(cancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPromptBinding = DialogPromptBinding.inflate(LayoutInflater.from(getContext()));
        setContentView(mPromptBinding.getRoot());
        mPromptBinding.promptContent.setText(mContentText);

        if (TextUtils.isEmpty(mEnsureText)) {
            mPromptBinding.promptEnsure.setVisibility(View.GONE);
            mPromptBinding.promptDivider.setVisibility(View.GONE);
        } else {
            mPromptBinding.promptEnsure.setText(mEnsureText);
        }
        if (TextUtils.isEmpty(mCancelText)) {
            mPromptBinding.promptCancel.setVisibility(View.GONE);
            mPromptBinding.promptDivider.setVisibility(View.GONE);
        } else {
            mPromptBinding.promptCancel.setText(mCancelText);
        }

        mPromptBinding.promptEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mEnsureListener != null) {
                    mEnsureListener.onClick(v);
                }
            }
        });

        mPromptBinding.promptCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mCancelListener != null) {
                    mCancelListener.onClick(v);
                }
            }
        });
    }

    public void setContent(String content) {
        this.mContentText = content;
        if (mPromptBinding != null) {
            mPromptBinding.promptContent.setText(mContentText);
        }
    }
}
