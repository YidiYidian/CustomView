package cn.onlyloveyd.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.onlyloveyd.customview.R;
import cn.onlyloveyd.customview.view.CountDownTextView;


/**
 * 文 件 名: CustomCountDownActivity
 * 创 建 人: 易冬
 * 创建日期: 2017/12/29 16:48
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 *
 * @author Mraz
 */
public class CustomCountDownActivity extends AppCompatActivity {

    @BindView(R.id.cdtv)
    CountDownTextView cdtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_count_down);
        ButterKnife.bind(this);

        cdtv.start();
    }
}
