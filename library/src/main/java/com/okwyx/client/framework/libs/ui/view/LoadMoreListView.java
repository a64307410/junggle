package com.okwyx.client.framework.libs.ui.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.okwyx.client.framework.libs.R;
import com.okwyx.client.framework.libs.display.ImageDisplayer;

/**
 * 作者：Swei on 2017/4/26 10:39<BR/>
 * 邮箱：sweilo@qq.com
 */

public class LoadMoreListView extends ListView {

    //滑动过程中暂停加载图片
    private boolean pauseOnScroll = true;
    //控制猛的滑动界面的时候图片是否加载
    private boolean pauseOnFling = true;

    /**
     * 加载更多监听
     */
    private LoadMoreListener loadMoreListener;

    private LoadMoreScrollListener scrollListener;


    public LoadMoreListView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        super.setOnScrollListener(ImageDisplayer.getPauseOnScrollListener(pauseOnScroll, pauseOnFling, null));
    }

    /**
     * 设置需要加载更多
     * @param loadMoreListener
     */
    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
        this.scrollListener = new LoadMoreScrollListener(this);
        //初始化loadingview
        setOnScrollListener(scrollListener);
    }

    /**
     * 调用加载完成
     * @param hasMore
     */
    public void onLoadMoreComplete(boolean hasMore) {
        if (scrollListener != null) {
            scrollListener.onLoadMoreComplete(hasMore);
        }
    }


    @Override
    public void setOnScrollListener(OnScrollListener l) {
        super.setOnScrollListener(ImageDisplayer.getPauseOnScrollListener(pauseOnScroll, pauseOnFling, l));
    }

    /**
     * 自动加载更多
     */
    public static interface LoadMoreListener {
        public void onLoadMore(int currentPage);
    }

    private static class LoadMoreScrollListener implements OnScrollListener {
        private LoadMoreListView listView;
        private View footView;

        private TextView infoTv;

        private ImageView progressImgView;

        private AnimationDrawable loadingDrawable;


        /**
         * 是否还有更多数据
         */
        private boolean hasMore = true;

        /**
         * 默认为第二页
         */
        private int currentPage = 2;

        private int currentScrollState;


        public LoadMoreScrollListener(LoadMoreListView listView) {
            this.listView = listView;

            LayoutInflater inflater = (LayoutInflater) this.listView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.footView = inflater.inflate(R.layout.market_game_include_lv_footloading, null);
            this.progressImgView = (ImageView) footView.findViewById(R.id.market_game_lvfootloading_imgView);
            this.infoTv = (TextView) footView.findViewById(R.id.market_game_lvfootloading_tv);
            this.loadingDrawable = (AnimationDrawable) progressImgView.getDrawable();

            listView.addFooterView(footView);

        }

        @Override
        public void onScrollStateChanged(AbsListView absListView, int currentScrollState) {
            this.currentScrollState = currentScrollState;
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (visibleItemCount == totalItemCount) {
                progressImgView.setVisibility(View.GONE);
                if (loadingDrawable.isRunning()) {
                    loadingDrawable.stop();
                }
                return;
            }

            boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
            if (hasMore && loadMore && currentScrollState != SCROLL_STATE_IDLE) {
                progressImgView.setVisibility(View.VISIBLE);
                if (!loadingDrawable.isRunning()) {
                    loadingDrawable.start();
                }
                hasMore = false;
                onLoadMore();
            }
        }

        private void onLoadMore() {
            if (listView.loadMoreListener != null) {
                listView.loadMoreListener.onLoadMore(currentPage);
            }
        }

        public void onLoadMoreComplete(boolean hasMore) {
            this.hasMore = hasMore;
            progressImgView.setVisibility(View.GONE);
            if (loadingDrawable.isRunning()) {
                loadingDrawable.stop();
            }

            if (hasMore) {
                currentPage++;
            } else {
                listView.removeFooterView(footView);
            }
        }
    }

}
