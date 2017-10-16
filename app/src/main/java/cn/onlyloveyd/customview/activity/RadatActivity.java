package cn.onlyloveyd.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.onlyloveyd.customview.R;
import cn.onlyloveyd.customview.view.CustomRadarChart;

/**
 * 文 件 名: RadatActivity
 * 创 建 人: 易冬
 * 创建日期: 2017/10/16 21:48
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 *
 * @author Mraz
 */
public class RadatActivity extends AppCompatActivity {

    @BindView(R.id.customRadarChart)
    CustomRadarChart customRadarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radat);
        ButterKnife.bind(this);

        Slide slide = new Slide();
        slide.setDuration(200);
        getWindow().setEnterTransition(slide);


        System.err.println("yidong -- X = " + Math.sin(0/ 360.0 * 2 * Math.PI));

    }
}
