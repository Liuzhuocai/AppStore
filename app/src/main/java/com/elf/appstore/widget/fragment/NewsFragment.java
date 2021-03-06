package com.elf.appstore.widget.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elf.appstore.R;
import com.elf.appstore.widget.base.BaseMainFragment;

/**
 * Created by liuzuocai on 18-3-28.
 */

public class NewsFragment extends BaseMainFragment {
    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

}
