package cn.onlyloveyd.customview.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.onlyloveyd.customview.DemoActivity;
import cn.onlyloveyd.customview.activity.CustomArcProgressBarActivity;
import cn.onlyloveyd.customview.activity.IndicatorActivity;
import cn.onlyloveyd.customview.activity.RadarActivity;

/**
 * 文 件 名: CustomAdapter
 * 创 建 人: 易冬
 * 创建日期: 2017/10/12 16:12
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 *
 * @author Mraz
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.TextViewHolder> {

    private final Context mContext;
    private final ArrayList<Intent> demoList = new ArrayList<>();
    private final ArrayList<String> mTitles = new ArrayList<>();

    public CustomAdapter(Context context) {
        mContext = context;

        mTitles.add("自定义仪表盘进度条");
        Intent intent = new Intent();
        intent.setClass(mContext, CustomArcProgressBarActivity.class);
        demoList.add(intent);

        mTitles.add("自定义ViewPager指示器");
        Intent intent2 = new Intent();
        intent2.setClass(mContext, IndicatorActivity.class);
        demoList.add(intent2);

        mTitles.add("自定义雷达图");
        Intent intent3 = new Intent();
        intent3.setClass(mContext, RadarActivity.class);
        demoList.add(intent3);


    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(20, 20, 0, 20);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new TextViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        final int finalPosition = position;
        ((TextView) holder.itemView).setText(mTitles.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(demoList.get(finalPosition),
                        ActivityOptions.makeSceneTransitionAnimation(
                                (DemoActivity) mContext).toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return demoList.size();
    }

    class TextViewHolder extends RecyclerView.ViewHolder {

        TextViewHolder(View itemView) {
            super(itemView);
        }
    }
}
