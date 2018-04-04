package com.elf.appstore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.elf.appstore.widget.base.BaseMainFragment;
import com.elf.appstore.widget.fragment.AppStoreFragment;
import com.elf.appstore.widget.fragment.FirstFragment;
import com.elf.appstore.widget.fragment.MeFragment;
import com.elf.appstore.widget.fragment.NewsFragment;
import com.elf.appstore.widget.fragment.SearchFragment;
import com.elf.appstore.widget.fragment.SupportActivity;
import com.elf.appstore.widget.fragment.TabSelectedEvent;
import com.elf.appstore.widget.ui.BottomBar;
import com.elf.appstore.widget.ui.BottomBarTab;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private BottomBar mBottomBar;
    private SupportFragment[] mFragments = new SupportFragment[4];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initFragment();
        initViews();
    }

    private void initFragment() {
        SupportFragment firstFragment = findFragment(FirstFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = FirstFragment.newInstance();
            mFragments[SECOND] = NewsFragment.newInstance();
            mFragments[THIRD] = SearchFragment.newInstance();
            mFragments[FOURTH] = MeFragment.newInstance();
            loadMultipleRootFragment(R.id.home_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(NewsFragment.class);
            mFragments[THIRD] = findFragment(SearchFragment.class);
            mFragments[FOURTH] = findFragment(MeFragment.class);
        }
    }

    private void initViews() {
            mBottomBar = (BottomBar) findViewById(R.id.bottomBar);

            mBottomBar.addItem(new BottomBarTab(this, R.drawable.ic_home_white_24dp))
                    .addItem(new BottomBarTab(this, R.drawable.ic_discover_white_24dp))
                    .addItem(new BottomBarTab(this, R.drawable.ic_message_white_24dp))
                    .addItem(new BottomBarTab(this, R.drawable.ic_account_circle_white_24dp));
            mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
                @Override
                public void onTabSelected(int position, int prePosition) {
                    Log.i("xxxx","position = "+position+" , prePosition = "+prePosition);
                    showHideFragment(mFragments[position], mFragments[prePosition]);
                }

                @Override
                public void onTabUnselected(int position) {

                }

                @Override
                public void onTabReselected(int position) {
                    final SupportFragment currentFragment = mFragments[position];
                    int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();
                    Log.i("xxxx","getSupportFragmentManager().getBackStackEntryCount() = "+count);
                    // 如果不在该类别Fragment的主页,则回到主页;
                    if (count > 1) {
                        if (currentFragment instanceof FirstFragment) {
                            currentFragment.popToChild(AppStoreFragment.class, false);
                        } else if (currentFragment instanceof NewsFragment) {
                            currentFragment.popToChild(NewsFragment.class, false);
                        } else if (currentFragment instanceof SearchFragment) {
                            currentFragment.popToChild(SearchFragment.class, false);
                        } else if (currentFragment instanceof MeFragment) {
                            currentFragment.popToChild(MeFragment.class, false);
                        }
                        return;
                    }


                    // 这里推荐使用EventBus来实现 -> 解耦
                    if (count == 1) {
                        // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                        // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                        EventBusActivityScope.getDefault(MainActivity.this).post(new TabSelectedEvent(position));
                    }
                }
            });
    }

    @Override
    public void onBackPressedSupport() {
        Log.i("xxxx","getSupportFragmentManager().getBackStackEntryCount() = "+getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }
}
