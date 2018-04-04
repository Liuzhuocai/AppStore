package com.elf.appstore.widget.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.elf.appstore.MainActivity;
import com.elf.appstore.R;
import com.elf.appstore.adapter.AppStoreRecycleViewAdapter;
import com.elf.appstore.adapter.base.CommonViewHolder;
import com.elf.appstore.http.DataResponse;
import com.elf.appstore.http.HttpHelper;
import com.elf.appstore.http.RequetError;
import com.elf.appstore.model.BannerItem;
import com.elf.appstore.model.BaseItem;
import com.elf.appstore.model.CategoryItem;
import com.elf.appstore.model.NetAppItem;
import com.elf.appstore.model.entities.BannerInfo;
import com.elf.appstore.model.entities.HomeCategoryData;
import com.elf.appstore.widget.base.BaseMainFragment;
import com.elf.appstore.widget.ui.ErrorLayout;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * Created by liuzuocai on 18-3-28.
 */

public class AppStoreFragment extends SupportFragment implements CommonViewHolder.OnItemCommonClickListener{
    private LRecyclerView mRecycleView;
    private ErrorLayout mErrorLayout;
    private LRecyclerViewAdapter mLAdapter;
    private AppStoreRecycleViewAdapter mAdapter;
    private int PAGE_SIZE = 3;
    private Handler uiHandler = new Handler();
    int mCurrentPage = 0;
    int pageCount = 10;

    public static AppStoreFragment newInstance() {
        Bundle args = new Bundle();
        AppStoreFragment fragment = new AppStoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBusActivityScope.getDefault(_mActivity).register(this);
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }
    public void initView(View view) {
        mRecycleView = view.findViewById(R.id.app_store_recyleview);
        mErrorLayout = view.findViewById(R.id.error_layout);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(manager);
        mAdapter = new AppStoreRecycleViewAdapter(this);
        mLAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecycleView.setAdapter(mLAdapter);
        mRecycleView.setOnRefreshListener(refresh);
        mRecycleView.setLoadMoreEnabled(true);
        mRecycleView.setOnLoadMoreListener(loadmore);
    }

    public void initData() {
        /*if (requestDataIfViewCreated()) {
            mErrorLayout.setErrorType(ErrorLayout.NETWORK_LOADING);
            refreshData();
        } else {
            mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
        }


        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPage = 0;
                mErrorLayout.setErrorType(ErrorLayout.NETWORK_LOADING);
                refreshData();
            }
        });*/
        mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
        List<BaseItem> items = new ArrayList<BaseItem>();
        for(int i = 0;i<17;i++){
            if(i==0){
                BannerItem itemBanner = new BannerItem();
                items.add(itemBanner);
                List<BannerInfo> bannerinfos = new ArrayList<>();
                List<String> urls = new ArrayList<String>();
                for(int j=0;j<6;j++){
                    BannerInfo info = new BannerInfo().test(j);
                    bannerinfos.add(info);
                    urls.add(info.getBannerUrl());
                }
                itemBanner.setBanners(bannerinfos);
            }else if(i%4==1){
                CategoryItem item = new CategoryItem();
                item.tittle ="类型"+i/4;
                items.add(item);
            }else{
                NetAppItem item = new NetAppItem();
                item.appItemInfo.setAppName("应用　"+i);
                item.appItemInfo.setAppMemo("fuck app");
                items.add(item);
            }
        }

        for(BaseItem item:items){
            Log.i("xxxx","initDate = "+ item.getItemType());
        }
        mAdapter.setData(items);
    }

    protected boolean requestDataIfViewCreated() {
        return true;
    }

    private void getHomeCategory() {
        HttpHelper.getInstance(AppStoreFragment.this.getContext()).getHomeCategory(AppStoreFragment.this.getContext(), 0,
                PAGE_SIZE, new DataResponse<HomeCategoryData>() {
                    @Override
                    public void onResponse(HomeCategoryData value) {

                    }

                    @Override
                    public void onErrorResponse(RequetError error) {
                        mErrorLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                mErrorLayout.setErrorType(ErrorLayout.NETWORK_ERROR);
                            }
                        });
                        setSwipeRefreshLoadedState();
                    }
                });
    }

    private void refreshData() {
        HttpHelper.getInstance(this.getContext()).getBannar(this.getContext(), new DataResponse<BannerItem>() {
            @Override
            public void onResponse(BannerItem value) {
                if (value.isEmpty()) {
                    setErrorLayoutState(ErrorLayout.NODATA_ENABLE_CLICK);
                } else {
                    List<BaseItem> list = new ArrayList<>();
                    list.add(value);
                    mAdapter.setData(list);
                    getHomeCategory();
                }
            }

            @Override
            public void onErrorResponse(RequetError error) {
                Log.i("xxxx", "init data : " + error);
                setErrorLayoutState(ErrorLayout.NETWORK_ERROR);
                setSwipeRefreshLoadedState();
            }
        });
    }

    public int getLayoutId() {
        return R.layout.fragment_appstore;
    }


    //Refresh
    OnRefreshListener refresh = new OnRefreshListener() {
        @Override
        public void onRefresh() {
            Log.i("xxxx", "onRefresh ");
            refreshData();
        }
    };
    //Load more
    OnLoadMoreListener loadmore = new OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            Log.i("xxxx", "hasMoreData ? " + hasMoreData() + " ,mCurrentPage = " + mCurrentPage);
            /*
            if (hasMoreData()) {
                List<BaseItem> items = new ArrayList<BaseItem>();
                for (int i = 0; i < 4; i++) {
                    if (i % 4 == 1) {
                        CategoryItem item = new CategoryItem();
                        item.tittle = "类型" + i / 4;
                        items.add(item);
                    } else {
                        NetAppItem item = new NetAppItem();
                        item.appItemInfo.setAppName("应用　" + i);
                        items.add(item);
                    }
                }
                mAdapter.addAll(items);
                mCurrentPage++;
                setSwipeRefreshLoadedState();
            } else {
                mRecycleView.setLoadMoreEnabled(true);
                mRecycleView.setNoMore(true);
            }
            */
            loadMore();


        }
    };

    private void loadMore() {
        HttpHelper.getInstance(AppStoreFragment.this.getContext()).getHomeCategory(AppStoreFragment.this.getContext(), mCurrentPage++,
                PAGE_SIZE, new DataResponse<HomeCategoryData>() {
                    @Override
                    public void onResponse(HomeCategoryData value) {
                        if (value.isEmpty()) {
                            setErrorLayoutState(ErrorLayout.NODATA_ENABLE_CLICK);
                        } else {
                            mAdapter.addAll(value.toBaseItem());
                        }
                        setSwipeRefreshLoadedState();
                    }

                    @Override
                    public void onErrorResponse(RequetError error) {
                        mCurrentPage--;

                        setSwipeRefreshLoadedState(-1);
                    }
                });
    }

    private void setErrorLayoutState(int state) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                mErrorLayout.setErrorType(state);
            }
        });
    }
    protected void setSwipeRefreshLoadedState(int state){
        if(state==0){
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (null != mRecycleView) {
                        mRecycleView.refreshComplete(PAGE_SIZE);
                    }
                }
            });
        }else{
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    mRecycleView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                        @Override
                        public void reload() {
                            loadMore();
                        }
                    });
                }
            });
        }
    }

    //刷新上拉下拉状态
    protected void setSwipeRefreshLoadedState() {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                if (null != mRecycleView) {
                    mRecycleView.refreshComplete(PAGE_SIZE);
                }
            }
        });
    }

    private boolean hasMoreData() {
        return mCurrentPage < pageCount;
    }
    /**
     * 选择tab事件
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainActivity.FIRST) return;
        /*
        if (mInAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }
        */
    }

    @Override
    public void onBannerClickListener(BannerInfo info) {
        Toast.makeText(this.getContext(),"Banner is clicked."+info,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMoreClickListener(CategoryItem info) {
       start(CategoryListFragment.newInstance(info));
    }

    @Override
    public void onDownloadClick(NetAppItem info) {

    }

    @Override
    public void onItemClickListener(NetAppItem position) {

    }

    @Override
    public void onItemLongClickListener(int position) {

    }
}
