package com.ormediagroup.xproject;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ormediagroup.xproject.Fragment.CartFragment;
import com.ormediagroup.xproject.Fragment.LoginFragment;
import com.ormediagroup.xproject.Fragment.DiscoverFragment;
import com.ormediagroup.xproject.Fragment.HomeFragment;
import com.ormediagroup.xproject.Fragment.ProductDetailsFragment;
import com.ormediagroup.xproject.Fragment.ProductsFragment;
import com.ormediagroup.xproject.Fragment.QuestionnaireFragment;
import com.ormediagroup.xproject.Fragment.ShoppingFragment;


public class MainActivity extends AppCompatActivity{


    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;
    private boolean mIsExit;
    private ImageView Topbar_myaccount,Topbar_shopcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.vp_content_bottom);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bnv_menu);
        Topbar_myaccount = findViewById(R.id.Topbar_myaccount);
        Topbar_shopcart = findViewById(R.id.Topbar_shopcart);

        initView();

    }

    private void initView() {

        //需改
        Topbar_myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
        adapter.addFragment(new LoginFragment());
        adapter.addFragment(new ShoppingFragment());
//        adapter.addFragment(new ProductsFragment());
        adapter.addFragment(new QuestionnaireFragment());
//        adapter.addFragment(new UserFragment());
        adapter.addFragment(new CartFragment());
        viewPager.setAdapter(adapter);
    }


    /*
     * 双击退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount()==0){
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (mIsExit) {
                    this.finish();
                } else {
                    Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                    mIsExit = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mIsExit = false;
                        }
                    }, 2000);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
