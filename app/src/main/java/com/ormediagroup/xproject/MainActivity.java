package com.ormediagroup.xproject;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;

import com.ormediagroup.xproject.Fragment.ContactFragment;
import com.ormediagroup.xproject.Fragment.DiscoverFragment;
import com.ormediagroup.xproject.Fragment.HomeFragment;
import com.ormediagroup.xproject.Fragment.ShareFragment;
import com.ormediagroup.xproject.Fragment.UserFragment;


public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.vp_content_bottom);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bnv_menu);
        initView();

    }

    private void initView() {
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.item_contacts:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.item_share:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.item_discover:
                                viewPager.setCurrentItem(3);
                                break;
                            case R.id.item_me:
                                viewPager.setCurrentItem(4);
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //viewPager.setCurrentItem(0);

        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new ContactFragment());
        adapter.addFragment(new ShareFragment());
        adapter.addFragment(new DiscoverFragment());
        adapter.addFragment(new UserFragment());

        viewPager.setAdapter(adapter);
    }

}
