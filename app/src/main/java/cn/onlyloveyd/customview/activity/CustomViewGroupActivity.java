package cn.onlyloveyd.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.onlyloveyd.customview.R;

/**
 * 文 件 名: CustomViewGroupActivity
 * 创 建 人: 易冬
 * 创建日期: 2017/10/10 16:48
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 *
 * @author Mraz
 */
public class CustomViewGroupActivity extends AppCompatActivity {

    @BindView(R.id.tv_topleft)
    TextView mTvTopleft;
    @BindView(R.id.tv_topright)
    TextView mTvTopright;
    @BindView(R.id.tv_bottomleft)
    TextView mTvBottomleft;
    @BindView(R.id.tv_bottomright)
    TextView mTvBottomright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_group);
        ButterKnife.bind(this);
    }
}
