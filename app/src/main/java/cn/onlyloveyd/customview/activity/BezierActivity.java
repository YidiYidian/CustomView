package cn.onlyloveyd.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.onlyloveyd.customview.R;
import cn.onlyloveyd.customview.view.CustomBezierView;

/**
 * 文 件 名: BezierActivity
 * 创 建 人: 易冬
 * 创建日期: 2017/10/20 16:48
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 *
 * @author Mraz
 */
@SuppressWarnings("ALL")
public class BezierActivity extends AppCompatActivity {

    @BindView(R.id.custom_bezier)
    CustomBezierView mCustomBezier;

    int startY = 150;
    int direction = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        ButterKnife.bind(this);


//        for (int i = 0; i < 100; i++) {
//            System.err.println("yidong -- recycler i = " + i);
//            System.err.println("yidong -- startY i = " + startY);
//            mCustomBezier.setStartY(startY);
//            mCustomBezier.invalidate();
//            if(startY > 350) {
//                direction = -direction;
//            }
//            if(startY <150) {
//                direction = -direction;
//            }
//            startY += 10*direction;
//        }

        mCustomBezier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
