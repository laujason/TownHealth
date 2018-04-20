package com.ormediagroup.xproject.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ormediagroup.xproject.R;

/**
 * Created by YQ04 on 2018/4/13.
 */

public class ShoppingFragment extends Fragment {

    private RecyclerView mContentRv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
        mContentRv = view.findViewById(R.id.rv_shopping);
        initView();
        return view;
    }

    private void initView() {
        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContentRv.setAdapter(new ContentAdapter());
    }

    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {

        @Override
        public ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_shopping_list, parent, false));
        }

        @Override
        public void onBindViewHolder(ContentAdapter.ContentHolder holder, int position) {
//            Date date = new Date();
//            holder.itemTv_title.setText("Item "+new DecimalFormat("00").format(position));
//            holder.itemTv_content.setText("许多离我们而去的美好事物，其实未曾远逝，而是以另一种风姿呈现在我们生命里，这便是永恒。");
//            holder.itemTv_time.setText("2018/4/12");
//            holder.topAdViewPager.setBackgroundResource(R.mipmap.ic_launcher);
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ContentHolder extends RecyclerView.ViewHolder {

            private TextView itemTv_title, itemTv_content, itemTv_time;

            public ContentHolder(View itemView) {
                super(itemView);
//                itemTv_title = itemView.findViewById(R.id.tv_title);
//                itemTv_content = itemView.findViewById(R.id.tv_content);
//                itemTv_time = itemView.findViewById(R.id.tv_time);
            }
        }
    }
}
