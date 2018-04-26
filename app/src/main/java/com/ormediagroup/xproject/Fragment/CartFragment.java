package com.ormediagroup.xproject.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ormediagroup.xproject.R;

/**
 * Created by Lau on 2018/4/25.
 */

public class CartFragment extends Fragment {
    private RecyclerView cart_list;
    private TextView cart_subTotal;
    private TextView cart_fees;
    private TextView cart_total;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_simple_list_1,null);
        /*View view = inflater.inflate(R.layout.fragment_cart,null);
        cart_list = view.findViewById(R.id.cart_list);
        cart_subTotal=view.findViewById(R.id.cart_subTotal);
        cart_fees=view.findViewById(R.id.cart_fees);
        cart_total=view.findViewById(R.id.cart_total);
        initView();*/
        return view;
    }

    private void initView() {
        
    }
}
