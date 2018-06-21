package cn.onlyloveyd.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.onlyloveyd.customview.R;
import cn.onlyloveyd.customview.view.LoadingDialog;
import cn.onlyloveyd.customview.view.PromptDialog;

/**
 * 文 件 名: LoadingActivity
 * 创 建 人: 易冬
 * 创建日期: 2018/05/10 16:48
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 *
 * @author Mraz
 */
public class LoadingActivity extends AppCompatActivity {


    @BindView(R.id.bt_loading)
    Button btLoading;
    @BindView(R.id.bt_prompt)
    Button btPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);

        btLoading.setOnClickListener(v -> LoadingDialog.show(LoadingActivity.this, true));

        btPrompt.setOnClickListener(v -> PromptDialog.show(LoadingActivity.this, "测试"));

    }
}
