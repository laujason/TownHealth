package com.ormediagroup.xproject.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ormediagroup.xproject.R;


/**
 * Created by YQ04 on 2018/4/24.
 */

public class MyAccountFragment extends Fragment {
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myaccount,container,false);
        recyclerView = view.findViewById(R.id.rv_myaccount);
        return view;
    }
}
