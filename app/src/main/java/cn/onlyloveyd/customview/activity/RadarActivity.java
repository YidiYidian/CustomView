package cn.onlyloveyd.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;

import java.util.ArrayList;
import java.util.List;

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
@SuppressWarnings("ALL")
public class RadarActivity extends AppCompatActivity {

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


        List<CustomRadarChart.RadarEntry> radarEntries = new ArrayList<>();
        radarEntries.add(0, new CustomRadarChart.RadarEntry("发育", 35.3f));
        radarEntries.add(1, new CustomRadarChart.RadarEntry("推进", 43.1f));
        radarEntries.add(2, new CustomRadarChart.RadarEntry("生存", 57.9f));
        radarEntries.add(3, new CustomRadarChart.RadarEntry("输出", 34.3f));
        radarEntries.add(4, new CustomRadarChart.RadarEntry("综合", 41.4f));
        radarEntries.add(5, new CustomRadarChart.RadarEntry("KDA", 47.7f));
        customRadarChart.setRadatEntries(radarEntries);

    }
}
