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
import java.util.List;

import cn.onlyloveyd.customview.DemoActivity;
import cn.onlyloveyd.customview.activity.CustomArcProgressBarActivity;
import cn.onlyloveyd.customview.activity.CustomCleanableEditTextActivity;
import cn.onlyloveyd.customview.activity.CustomCountDownActivity;
import cn.onlyloveyd.customview.activity.CustomSwitchActivity;
import cn.onlyloveyd.customview.activity.CustomViewGroupActivity;
import cn.onlyloveyd.customview.activity.IndicatorActivity;
import cn.onlyloveyd.customview.activity.LoadingActivity;
import cn.onlyloveyd.customview.activity.RadarActivity;
import cn.onlyloveyd.customview.activity.RefreshAndLoadMoreActivity;

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
    class AdapterItem {
        private String title;
        private Intent intent;

        AdapterItem(String title, Intent intent) {
            this.title = title;
            this.intent = intent;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Intent getIntent() {
            return intent;
        }

        public void setIntent(Intent intent) {
            this.intent = intent;
        }
    }

    private final Context mContext;
    List<AdapterItem> mContentList = new ArrayList<AdapterItem>();

    public CustomAdapter(Context context) {
        mContext = context;
        mContentList.add(new AdapterItem("自定义仪表盘进度条", new Intent(context, CustomArcProgressBarActivity.class)));
        mContentList.add(new AdapterItem("自定义ViewPager指示器", new Intent(context, IndicatorActivity.class)));
        mContentList.add(new AdapterItem("自定义雷达图", new Intent(context, RadarActivity.class)));
        mContentList.add(new AdapterItem("自定义ViewGroup", new Intent(context, CustomViewGroupActivity.class)));
        mContentList.add(new AdapterItem("自定义开关", new Intent(context, CustomSwitchActivity.class)));
        mContentList.add(new AdapterItem("可删除编辑框", new Intent(context, CustomCleanableEditTextActivity.class)));
        mContentList.add(new AdapterItem("倒计时", new Intent(context, CustomCountDownActivity.class)));
        mContentList.add(new AdapterItem("加载对话框", new Intent(context, LoadingActivity.class)));
        mContentList.add(new AdapterItem("下拉刷新加载更多", new Intent(context, RefreshAndLoadMoreActivity.class)));
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
        ((TextView) holder.itemView).setText(mContentList.get(position).getTitle());
        holder.itemView.setOnClickListener(v -> mContext.startActivity(mContentList.get(position).getIntent(),
                ActivityOptions.makeSceneTransitionAnimation(
                        (DemoActivity) mContext).toBundle()));
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    class TextViewHolder extends RecyclerView.ViewHolder {

        TextViewHolder(View itemView) {
            super(itemView);
        }
    }
}
