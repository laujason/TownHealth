package com.ormediagroup.xproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by YQ04 on 2018/4/11.
 */

public class TabContentFragment extends Fragment{
    private static final String EXTRA_CONTENT = "content";
    private RecyclerView mContentRv;
    public static TabContentFragment newInstance(String content){
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        TabContentFragment tabContentFragment = new TabContentFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_tab_content, null);
//        ((TextView)contentView.findViewById(R.id.tv_content)).setText(getArguments().getString(EXTRA_CONTENT));
        mContentRv =  contentView.findViewById(R.id.rv_content);
        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContentRv.setAdapter(new ContentAdapter());
        return contentView;
    }

    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder>{

        @Override
        public ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_simple_list_1, parent, false));
        }

        @Override
        public void onBindViewHolder(ContentAdapter.ContentHolder holder, int position) {
            Date date = new Date();
            holder.itemTv_title.setText("Item "+new DecimalFormat("00").format(position));
            holder.itemTv_content.setText("许多离我们而去的美好事物，其实未曾远逝，而是以另一种风姿呈现在我们生命里，这便是永恒。");
            holder.itemTv_time.setText("2018/4/12");
        }

        @Override
        public int getItemCount() {
            return 36;
        }

        class ContentHolder extends RecyclerView.ViewHolder{

            private TextView itemTv_title,itemTv_content,itemTv_time;

            public ContentHolder(View itemView) {
                super(itemView);
                itemTv_title = itemView.findViewById(R.id.tv_title);
                itemTv_content = itemView.findViewById(R.id.tv_content);
                itemTv_time = itemView.findViewById(R.id.tv_time);
            }
        }

    }
}
