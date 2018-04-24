package com.ormediagroup.xproject.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by YQ04 on 2018/4/20.
 */

public class HomePageFragment extends Fragment{
    private final String packagename = "HomePageFragment - ";
    private boolean loading;
    String TownHealthDomain;
    JSONArray adJsonArray;
    ArrayList<JSONObject> topAdJsonArray;
    private ImageView Topad;
    public View contentView;

    public static HomePageFragment newInstance(String content){
        Bundle arguments = new Bundle();
//        arguments.putString(EXTRA_CONTENT, content);
        HomePageFragment homepageFragment = new HomePageFragment();
        homepageFragment.setArguments(arguments);
        return homepageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.home_item_view, container, false);
        Topad = contentView.findViewById(R.id.homepage_Topad);

        if(loading){
            initView();
        }
//        initView();
        loading= true;
        return contentView;
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
