package com.ormediagroup.xproject;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabTl;
    private ViewPager mContentVp,viewPager;


    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;

    private String[] mTabTitles_top = new String []{"推荐","热点","体育","健康","娱乐","军事","科技"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTabTl =  findViewById(R.id.tl_tab);
        mContentVp =  findViewById(R.id.vp_content);
        viewPager = findViewById(R.id.vp_content_bottom);
        initContent();
        initTab();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bnv_menu);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_wechat:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.item_contacts:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.item_discover:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.item_me:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setupViewPager(viewPager);

    }

    private void initTab(){
        mTabTl.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabTl.setTabTextColors(ContextCompat.getColor(this, R.color.gray), ContextCompat.getColor(this, R.color.white));
        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white));
        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(mContentVp);
    }

    private void initContent() {
        tabIndicators = new ArrayList<>();
        /*for (int i = 0; i < 8; i++) {
            tabIndicators.add("Tab " + i);
        }*/
        for (int i = 0; i < mTabTitles_top.length; i++) {
            tabIndicators.add(mTabTitles_top[i]);
        }
        tabFragments = new ArrayList<>();
        for (String s : tabIndicators) {
            tabFragments.add(TabContentFragment.newInstance(s));  //内容
        }
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(BaseFragment.newInstance("新闻"));
        adapter.addFragment(BaseFragment.newInstance("图书"));
        adapter.addFragment(BaseFragment.newInstance("发现"));
        adapter.addFragment(BaseFragment.newInstance("更多"));
        viewPager.setAdapter(adapter);
    }

    private class ContentPagerAdapter extends FragmentPagerAdapter{

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }
}
