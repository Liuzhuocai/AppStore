package com.elf.appstore.widget.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.elf.appstore.R;
import com.elf.appstore.adapter.CategoryListAdpater;
import com.elf.appstore.model.BannerItem;
import com.elf.appstore.model.BaseItem;
import com.elf.appstore.model.CategoryItem;
import com.elf.appstore.model.NetAppItem;
import com.elf.appstore.model.entities.AppItemInfo;
import com.elf.appstore.model.entities.BannerInfo;
import com.elf.appstore.widget.base.BaseBackFragment;
import com.elf.appstore.widget.ui.ErrorLayout;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antino on 18-4-3.
 */

public class CategoryListFragment extends BaseBackFragment implements AdapterView.OnItemClickListener{
    public static String CATEGORY_INFO="CATEGORY_INFO";
    private LRecyclerView lRecyclerView;
    private LRecyclerViewAdapter lAdapter;
    private CategoryListAdpater mAdapter;
    private ErrorLayout mErrorLayout;

    private static final String ARG_ITEM = "arg_item";

    private CategoryItem mAbaultInfo;

    public static CategoryListFragment newInstance(CategoryItem item) {
        Bundle args = new Bundle();
        args.putParcelable(CATEGORY_INFO,item);
        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAbaultInfo = getArguments().getParcelable(CATEGORY_INFO);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        refreshData();
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        lRecyclerView = view.findViewById(R.id.app_list_recyleview);
        mErrorLayout = view.findViewById(R.id.error_layout);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lRecyclerView.setLayoutManager(manager);
        mAdapter = new CategoryListAdpater<AppItemInfo>();
        lAdapter = new LRecyclerViewAdapter(mAdapter);
        lRecyclerView.setAdapter(lAdapter);
        lRecyclerView.setOnRefreshListener(refresh);
        lRecyclerView.setOnLoadMoreListener(loadmore);
        lRecyclerView.setLoadMoreEnabled(true);
        mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_categrory_list;
    }
    //Refresh
    OnRefreshListener refresh = new OnRefreshListener() {
        @Override
        public void onRefresh() {
            Log.i("xxxx", "onRefresh ");
            refreshData();
        }
    };
    OnLoadMoreListener loadmore = new OnLoadMoreListener() {
        @Override
        public void onLoadMore() {

        }
    };

    private void refreshData() {
        List<BaseItem> items = new ArrayList<BaseItem>();
        for(int i = 0;i<17;i++){
                NetAppItem item = new NetAppItem();
                item.appItemInfo.setAppName("应用　"+i);
                item.appItemInfo.setAppMemo("fuck app");
                items.add(item);
        }
        mAdapter.setData(items);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO:antino
        //Detail of app
    }
}
