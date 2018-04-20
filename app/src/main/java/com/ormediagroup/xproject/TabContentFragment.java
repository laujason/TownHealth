package com.ormediagroup.xproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YQ04 on 2018/4/11.
 */

public class TabContentFragment extends Fragment{
      private static final String EXTRA_CONTENT = "content";
//    private RecyclerView mContentRv;
//    private List<String> imageUrl = new ArrayList<>();

    public View contentView;

    public static TabContentFragment newInstance(String content){
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        TabContentFragment tabContentFragment = new TabContentFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.item_simple_list_1, container, false);
        return contentView;
    }

    /*private void initView() {
        try {
            Topad.setTag(topAdJsonArray.get(0).getString("url"));
            PicassoTrustSSL.getInstance(getActivity()).load(topAdJsonArray.get(0).getString("img")).into(Topad);
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
    }*/


}
