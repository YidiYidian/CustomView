package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import cn.onlyloveyd.customview.R;

/**
 * 加载更多RecyclerView
 *
 * @author Created by jiangyujiang on 2017/8/23.
 */

public class LoadMoreRecyclerView extends RecyclerView {
    private WrapperAdapter mWrapperAdapter;
    private OnLoadMoreListener mLoadMoreListener;
    private boolean mLoading;
    private boolean mFinish;

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(mScrollListener);
    }

    private OnScrollListener mScrollListener = new OnScrollListener() {
        private int mScrollDx, mScrollDy;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            int scrollDx = 0;
            int scrollDy = 0;
            if (newState == SCROLL_STATE_IDLE) {
                scrollDx = mScrollDx;
                scrollDy = mScrollDy;
                mScrollDx = 0;
                mScrollDy = 0;
            }
            if (newState != SCROLL_STATE_IDLE || mWrapperAdapter == null || mWrapperAdapter.getItemCount() <= 0
                    || mLoading || mFinish || mLoadMoreListener == null) {
                return;
            }
            if (scrollDx <= 0 && scrollDy <= 0) {
                // 有滚动距离才执行加载更多
                return;
            }
            boolean isLast = false;
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager glm = (GridLayoutManager) layoutManager;
                isLast = glm.findLastVisibleItemPosition() >= layoutManager.getItemCount() - 1;
            } else if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager llm = (LinearLayoutManager) layoutManager;
                isLast = llm.findLastVisibleItemPosition() >= layoutManager.getItemCount() - 1;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager sglm = (StaggeredGridLayoutManager) layoutManager;
                int[] into = new int[sglm.getSpanCount()];
                sglm.findLastVisibleItemPositions(into);
                int last = into[0];
                for (int value : into) {
                    if (value > last) {
                        last = value;
                    }
                }
                isLast = last >= layoutManager.getItemCount() - 1;
            }
            if (isLast) {
                onLoadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            mScrollDx += dx;
            mScrollDy += dy;
        }
    };

    private void onLoadMore() {
        mLoading = true;
        mWrapperAdapter.mLoadMoreViewHolder.resultTv.setVisibility(GONE);
        mWrapperAdapter.mLoadMoreViewHolder.loadingView.setVisibility(VISIBLE);
        if (mLoadMoreListener != null) {
            mLoadMoreListener.onLoadMore();
        }
    }

    /**
     * 数据加载完成时，外部必须主动调用该方法结束本次加载
     *
     * @param isSuccess 数据是否加载成功
     */
    public void loadMoreComplete(boolean isSuccess) {
        if (mLoading) {
            mLoading = false;
            if (mWrapperAdapter != null) {
                if (isSuccess) {
                    mWrapperAdapter.mLoadMoreViewHolder.resultTv.setText(R.string.load_more_success);
                } else {
                    mWrapperAdapter.mLoadMoreViewHolder.resultTv.setText(R.string.load_more_fail);
                }
                mWrapperAdapter.mLoadMoreViewHolder.loadingView.setVisibility(GONE);
                mWrapperAdapter.mLoadMoreViewHolder.resultTv.setVisibility(VISIBLE);
            }
        }
    }

    /**
     * 所有数据加载完成，如果没有更多数据，外部必须主动调用该方法
     */
    public void finish() {
        if (!mFinish) {
            mFinish = true;
            mLoading = false;
            if (mWrapperAdapter != null) {
                mWrapperAdapter.mLoadMoreViewHolder.loadingView.setVisibility(GONE);
                mWrapperAdapter.mLoadMoreViewHolder.resultTv.setText(R.string.load_more_finish);
                mWrapperAdapter.mLoadMoreViewHolder.resultTv.setVisibility(VISIBLE);
            }
        }
    }

    public void resetStatus() {
        mFinish = false;
        mLoading = false;
        if (mWrapperAdapter != null) {
            mWrapperAdapter.mLoadMoreViewHolder.loadingView.setVisibility(GONE);
            mWrapperAdapter.mLoadMoreViewHolder.resultTv.setText(R.string.load_more_success);
            mWrapperAdapter.mLoadMoreViewHolder.resultTv.setVisibility(VISIBLE);
        }
    }

    public boolean isLoading() {
        return mLoading;
    }

    public boolean isFinish() {
        return mFinish;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (mWrapperAdapter != null) {
            mWrapperAdapter.mAdapter.unregisterAdapterDataObserver(mDataObserver);
            mWrapperAdapter = null;
        }
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mDataObserver);
            mWrapperAdapter = new WrapperAdapter(adapter);
        }
        super.setAdapter(mWrapperAdapter);
    }

    public Adapter getOriginAdapter() {
        return mWrapperAdapter == null ? null : mWrapperAdapter.mAdapter;
    }

    @Override
    public void clearOnScrollListeners() {
        super.clearOnScrollListeners();
        addOnScrollListener(mScrollListener);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            mWrapperAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapperAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapperAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapperAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapperAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapperAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    private class WrapperAdapter extends Adapter {
        private static final int LOAD_MORE_TYPE = -1;
        private Adapter mAdapter;
        private LoadMoreViewHolder mLoadMoreViewHolder;

        private WrapperAdapter(Adapter adapter) {
            this.mAdapter = adapter;
            View loadMoreView = LayoutInflater.from(getContext()).inflate(R.layout.view_load_more, LoadMoreRecyclerView.this, false);
            mLoadMoreViewHolder = new LoadMoreViewHolder(loadMoreView);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == LOAD_MORE_TYPE) {
                return mLoadMoreViewHolder;
            } else {
                return mAdapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (!(holder instanceof LoadMoreViewHolder)) {
                //noinspection unchecked
                mAdapter.onBindViewHolder(holder, position);
            }
        }

        @Override
        public int getItemCount() {
            int originalCount = mAdapter.getItemCount();
            return originalCount <= 0 ? 0 : originalCount + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getItemCount() - 1) {
                return LOAD_MORE_TYPE;
            } else {
                int type = mAdapter.getItemViewType(position);
                if (type == LOAD_MORE_TYPE) {
                    throw new IllegalArgumentException("getItemViewType()返回值不能等于" + LOAD_MORE_TYPE);
                }
                return type;
            }
        }

        @Override
        public long getItemId(int position) {
            if (getItemViewType(position) == LOAD_MORE_TYPE) {
                return super.getItemId(position);
            } else {
                return mAdapter.getItemId(position);
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onBindViewHolder(ViewHolder holder, int position, List payloads) {
            if (!(holder instanceof LoadMoreViewHolder)) {
                mAdapter.onBindViewHolder(holder, position, payloads);
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onViewRecycled(ViewHolder holder) {
            if (holder instanceof LoadMoreViewHolder) {
                super.onViewRecycled(holder);
            } else {
                mAdapter.onViewRecycled(holder);
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean onFailedToRecycleView(ViewHolder holder) {
            if (holder instanceof LoadMoreViewHolder) {
                return super.onFailedToRecycleView(holder);
            } else {
                return mAdapter.onFailedToRecycleView(holder);
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            if (holder instanceof LoadMoreViewHolder) {
                ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                    ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
                }
            } else {
                mAdapter.onViewAttachedToWindow(holder);
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            if (holder instanceof LoadMoreViewHolder) {
                super.onViewDetachedFromWindow(holder);
            } else {
                mAdapter.onViewDetachedFromWindow(holder);
            }
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                final GridLayoutManager.SpanSizeLookup originalSizeLookup = gridLayoutManager.getSpanSizeLookup();
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (getItemViewType(position) == LOAD_MORE_TYPE) {
                            return gridLayoutManager.getSpanCount();
                        } else if (originalSizeLookup != null) {
                            return originalSizeLookup.getSpanSize(position);
                        }
                        return 1;
                    }
                });
            }
            mAdapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            mAdapter.onDetachedFromRecyclerView(recyclerView);
        }

        private class LoadMoreViewHolder extends ViewHolder {
            private View loadingView;
            private TextView resultTv;

            private LoadMoreViewHolder(View itemView) {
                super(itemView);
                loadingView = itemView.findViewById(R.id.load_more_loading);
                resultTv = itemView.findViewById(R.id.load_more_result);
                resultTv.setOnClickListener(v -> {
                    if (v == resultTv && !isLoading() && !isFinish()) {
                        onLoadMore();
                    }
                });
            }
        }
    }
}
