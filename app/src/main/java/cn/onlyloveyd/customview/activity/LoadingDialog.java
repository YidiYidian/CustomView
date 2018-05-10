package cn.onlyloveyd.customview.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import cn.onlyloveyd.customview.R;

/**
 * 加载数据不确定进度对话框
 *
 * @author Created by jiangyujiang on 2017/5/4.
 */

public class LoadingDialog extends Dialog {
    private TextView contentTv;
    private String content;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.NoFrameDialog);
    }

    public LoadingDialog(@NonNull Context context, boolean cancelable) {
        super(context, R.style.NoFrameDialog);
        setCancelable(cancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        contentTv = findViewById(R.id.content);
        if (!TextUtils.isEmpty(content)) {
            contentTv.setText(content);
        }
    }

    public void setContent(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        this.content = content;
        if (contentTv != null) {
            contentTv.setText(content);
        }
    }

    public static LoadingDialog show(Context context, boolean cancelable) {
        LoadingDialog dialog = new LoadingDialog(context);
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
    }
}
