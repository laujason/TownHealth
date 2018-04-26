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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ormediagroup.xproject.JsonRespon;
import com.ormediagroup.xproject.PicassoTrustSSL;
import com.ormediagroup.xproject.R;
import com.ormediagroup.xproject.TabContentFragment;
import com.ormediagroup.xproject.ViewPagerAdapter;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by YQ04 on 2018/4/13.
 */

public class HomeFragment extends Fragment{
    private final String packagename = "HomeFragment - ";
    private boolean loading;
    String TownHealthDomain;
    JSONArray adJsonArray;
    ArrayList<JSONObject> topAdJsonArray;
    private ImageView Topad;
    private View view;


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
            Topad = view.findViewById(R.id.homepage_Topad);

            TownHealthDomain = getString(R.string.Townhealth_domain);
            loading = false;
            /** Get TopAd JSONObject**/
            String URL = TownHealthDomain + "app/app-home";

            new JsonRespon(getActivity(),URL, new JsonRespon.onComplete() {
                @Override
                public void onComplete(JSONObject json) {
                    try {
                        adJsonArray = new JSONArray();
                        topAdJsonArray = new ArrayList<JSONObject>();
                        /** Prepare TopAd and Bottom Ad data (From JSONArray to ArrayList<JSONObject>)>**/
                        adJsonArray = json.getJSONArray("ads");
    //                    Log.i("ORM","ads:"+json.toString());
                        for (int i = 0; i < adJsonArray.length(); i++) {
                            topAdJsonArray.add(adJsonArray.getJSONObject(i));
                            Log.i("ORM",  packagename+"Get topad url: " +topAdJsonArray);

                        }
                        initView();

                    }catch (Exception e){
                        Log.e("ORM",  packagename+"Get topad error: " +e.toString());
                    }
                }
            });

            return view;
    }


    private void initView() {
        try {
            Topad.setTag(topAdJsonArray.get(0).getString("url"));
            PicassoTrustSSL.getInstance(getActivity()).load(topAdJsonArray.get(0).getString("img")).into(Topad);
//            Picasso.with(getActivity()).load(topAdJsonArray.get(0).getString("img")).into(Topad);
            Log.i("ORM","imageUrl:"+topAdJsonArray.get(0).getString("img"));
            Topad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),"hello",Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.e("ORM",  packagename+"Homepage Topad load error: " +e.toString());
        }
    }



}
