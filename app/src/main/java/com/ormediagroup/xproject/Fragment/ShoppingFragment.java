package com.ormediagroup.xproject.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ormediagroup.xproject.R;

/**
 * Created by Lau on 2018/4/25.
 */

public class ShoppingFragment extends Fragment{
    private FrameLayout frameLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping,null);
        frameLayout = view.findViewById(R.id.frameLayout);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = new ProductsFragment();
        ft.replace(R.id.frameLayout,f,"shopping");
        //ft.addToBackStack("shopping");
        ft.commit();
        return view;
    }


}
