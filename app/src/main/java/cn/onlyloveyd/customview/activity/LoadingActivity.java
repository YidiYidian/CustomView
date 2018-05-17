package cn.onlyloveyd.customview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.onlyloveyd.customview.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        LoadingDialog.show(this, true);
    }
}
