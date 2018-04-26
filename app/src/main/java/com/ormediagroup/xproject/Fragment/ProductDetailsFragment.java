package com.ormediagroup.xproject.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ormediagroup.xproject.JsonRespon;
import com.ormediagroup.xproject.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeoutException;

/**
 * Created by Lau on 2018/4/25.
 */

public class ProductDetailsFragment extends Fragment {
    private ImageView shopping_item_image;
    private TextView shopping_item_name;
    private TextView shopping_item_price;
    private Button shopping_item_addtocart;
    private TextView shopping_item_desc;
    private Handler mHandler = new Handler();

    private String URL = "https://thhealthmgt.com/app/app-get-products/";
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private TextView shopping_item_reduce;
    private TextView shopping_item_plus;
    private EditText shopping_item_count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details,null);
        shopping_item_image = view.findViewById(R.id.shopping_item_image);
        shopping_item_name = view.findViewById(R.id.shopping_item_name);
        shopping_item_price = view.findViewById(R.id.shopping_item_price);
        shopping_item_addtocart = view.findViewById(R.id.shopping_item_addtocart);
        shopping_item_desc = view.findViewById(R.id.shopping_item_desc);
        progressBar = view.findViewById(R.id.progressBar);
        scrollView = view.findViewById(R.id.scrollView);
        scrollView.setVisibility(View.GONE);
        shopping_item_reduce = view.findViewById(R.id.shopping_item_reduce);
        shopping_item_plus = view.findViewById(R.id.shopping_item_plus);
        shopping_item_count = view.findViewById(R.id.shopping_item_count);
        int id = getArguments().getInt("ID");
        Toast.makeText(getContext(),"id="+id,Toast.LENGTH_SHORT).show();
        new JsonRespon(getContext(), URL+"?id="+id, new JsonRespon.onComplete() {
            @Override
            public void onComplete(JSONObject json) {
                try {
                    scrollView.setVisibility(View.VISIBLE);
                    JSONArray productArr = json.getJSONArray("products");
                    Log.i("ORM", "onComplete lx-product: "+productArr.toString());
                    final JSONObject product = productArr.getJSONObject(0);
                    JSONArray imgArr = product.getJSONArray("image");
                    Picasso.with(getContext()).load(imgArr.getString(0)).into(shopping_item_image);
                    shopping_item_name.setText(product.getString("name"));
                    shopping_item_price.setText("$"+(double)product.getInt("price"));
                    new Thread() {
                        @Override
                        public void run() {
                            Spanned res = null;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                try {
                                    res = Html.fromHtml(product.getString("desc"), 1, new Html.ImageGetter() {
                                        @Override
                                        public Drawable getDrawable(final String s) {

                                            java.net.URL imgUrl;
                                            Drawable drawable = null;
                                            try {
                                                imgUrl = new URL(s);
                                                drawable = Drawable.createFromStream(imgUrl.openStream(), "");
                                                drawable.setBounds(0, 0,1000 , 1000);
                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            return drawable;
                                        }
                                    }, null);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            final Spanned finalRes = res;
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    shopping_item_desc.setText(finalRes);
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    }.start();

                    shopping_item_desc.setMovementMethod(LinkMovementMethod.getInstance());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                shopping_item_reduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int num = Integer.parseInt(shopping_item_count.getText().toString());
                        if (num > 1) {
                            shopping_item_count.setText("" + (num - 1));
                        } else {
                            shopping_item_count.setText("" + 1);
                        }
                    }
                });
                shopping_item_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int num = Integer.parseInt(shopping_item_count.getText().toString()) + 1;
                        shopping_item_count.setText("" + num);
                    }
                });
            }
        });
        return view;
    }
}
