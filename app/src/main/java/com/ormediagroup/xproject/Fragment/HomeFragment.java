package com.ormediagroup.xproject.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ormediagroup.xproject.R;
import com.ormediagroup.xproject.TabContentFragment;
import com.ormediagroup.xproject.ViewPagerAdapter;


import java.util.ArrayList;
import java.util.List;



/**
 * Created by YQ04 on 2018/4/13.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{

    private TabLayout tablayout ;
    private ViewPager tabViewpager;
    private View view;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private String[] mTabTitles_top = new String []{"推荐","Tap1","Tap2","Tap3","Tap4","Tap5","Tap6","Tap7","Tap8"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            if (view != null) {
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null) {
                    parent.removeView(view);
                }
                return view;
            }

            view  = inflater.inflate(R.layout.fragment_home,container,false);
            tablayout = view.findViewById(R.id.tablayout);
            tabViewpager =  view.findViewById(R.id.tab_viewpager);
            initTab(view);
            initView(view);
            return view;
    }

    private void initTab(View view) {
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.gray), ContextCompat.getColor(getActivity(), R.color.white));
        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.white));
        ViewCompat.setElevation(tablayout, 10);
        tablayout.setupWithViewPager(tabViewpager);

    }

    private void initView(View view) {
        tabIndicators = new ArrayList<>();
        for (int i = 0; i < mTabTitles_top.length; i++) {
            tabIndicators.add(mTabTitles_top[i]);
        }
        tabFragments = new ArrayList<>();
        /*for (String s : tabIndicators) {
            tabFragments.add(TabContentFragment.newInstance(s));  //内容
        }*/
        tabFragments.add(new HomePageFragment());
        for (int i = 1; i < tabIndicators.size(); i++) {
            TabContentFragment fragment = TabContentFragment.newInstance(tabIndicators.get(i));
            tabFragments.add(fragment);
        }
        contentAdapter = new ContentPagerAdapter(getActivity().getSupportFragmentManager());
        tabViewpager.setAdapter(contentAdapter);

        /*setupViewPager(tabViewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager())
        tabViewpager.addFragment(new HomePageFragment());*/
    }

    /*private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new HomePageFragment());
        viewPager.setAdapter(adapter);
    }*/



    @Override
    public void onClick(View v) {

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
