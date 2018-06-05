package cn.onlyloveyd.customview.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import cn.onlyloveyd.customview.R;
import cn.onlyloveyd.customview.adapter.TextAdapter;
import cn.onlyloveyd.customview.databinding.ActivityRefreshLoadmoreLayoutBinding;
import cn.onlyloveyd.customview.view.LoadMoreRecyclerView;

/**
 * Class Description
 *
 * @author yidong (yidong@gz.csg.cn)
 * @date 2018/6/5 09:23
 */
public class RefreshAndLoadMoreActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    int currentIndex = 0;
    ActivityRefreshLoadmoreLayoutBinding binding;
    ArrayList<String> mListData;
    TextAdapter mTextAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refresh_loadmore_layout);

        mListData = new ArrayList<>();
        mTextAdapter = new TextAdapter(mListData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.lmrvContent.setLayoutManager(linearLayoutManager);
        binding.lmrvContent.setAdapter(mTextAdapter);

        binding.lmrvContent.setOnLoadMoreListener(() -> {
            currentIndex += 1;
            doRefresh(currentIndex);
        });

        binding.refresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        currentIndex = 0;
        doRefresh(currentIndex);
    }

    private void doRefresh(int index) {
        if (binding.refresh.isRefreshing()) {
            mListData.clear();
        }
        for (int i = index*10; i < (index + 1) * 10; i++) {
            mListData.add(String.valueOf(i));
        }
        mTextAdapter.notifyDataSetChanged();
        endRefreshAndLoad();
    }

    private void endRefreshAndLoad(){
        if(binding.refresh.isRefreshing()) {
            binding.refresh.setRefreshing(false);
        }
        if(binding.lmrvContent.isLoading()) {
            binding.lmrvContent.loadMoreComplete(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (binding.refresh.isRefreshing()) {
            binding.refresh.setRefreshing(false);
        }
        if (binding.lmrvContent.isLoading()) {
            binding.lmrvContent.loadMoreComplete(true);
        }
    }
}
