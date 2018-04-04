package com.elf.appstore.widget.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elf.appstore.R;
import com.elf.appstore.widget.base.BaseMainFragment;

/**
 * Created by antino on 18-4-4.
 */

public class FirstFragment extends BaseMainFragment {
    public static FirstFragment newInstance() {
        Bundle args = new Bundle();
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (findChildFragment(AppStoreFragment.class) == null) {
            loadRootFragment(R.id.first_container, AppStoreFragment.newInstance());
        }
    }
}
