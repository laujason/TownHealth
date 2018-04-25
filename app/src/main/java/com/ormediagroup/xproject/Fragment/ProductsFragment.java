package com.ormediagroup.xproject.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ormediagroup.xproject.Beans.CategoryBean;
import com.ormediagroup.xproject.Beans.ProductBean;
import com.ormediagroup.xproject.JsonRespon;
import com.ormediagroup.xproject.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lau on 2018/4/13.
 */

public class ProductsFragment extends Fragment {

    private RecyclerView mContentRv;
    private LinearLayout mCategories;
    private TextView mTitle;
    private ProductsAdapter adapter;
    private ProgressBar progressBar;

    private String ProductURL = "https://thhealthmgt.com/app/app-get-products/";
    private String CategoryURL = "https://thhealthmgt.com/app/app-get-categories/";
    private int initCategoryId;
    private String initCategoryTitle;
    private int currentPage = 1;
    private int currentCategoryId;
    private int limit = 10;
    private List<ProductBean> productBeanList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        mContentRv = view.findViewById(R.id.rv_shopping);
        mCategories = view.findViewById(R.id.ll_categories);
        mTitle = view.findViewById(R.id.tv_title);
        progressBar = view.findViewById(R.id.progressBar);
        mTitle.setText("");
        currentPage = 1;
        initView();
        return view;
    }

    private void initView() {
        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar.setVisibility(View.VISIBLE);
        new JsonRespon(getContext(), CategoryURL, new JsonRespon.onComplete() {
            @Override
            public void onComplete(JSONObject json) {
                final List<CategoryBean> categoryBeanList = new ArrayList<>();
                try {
                    JSONArray categoriesArray = json.getJSONArray("productCat");
                    Log.i("ORM", "lx-test-json: " + categoriesArray.toString());
                    CategoryBean categoryBean;
                    for (int i = 0; i < categoriesArray.length(); i++) {
                        categoryBean = new CategoryBean();
                        JSONObject jsonObject = categoriesArray.getJSONObject(i);
                        categoryBean.Id = jsonObject.getInt("ID");
                        categoryBean.name = jsonObject.getString("name");
                        categoryBeanList.add(categoryBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                initCategoryId = categoryBeanList.get(categoryBeanList.size() - 1).Id;
                initCategoryTitle = categoryBeanList.get(categoryBeanList.size() - 1).name;
                for (int i = categoryBeanList.size() - 1; i >= 0; i--) {
                    TextView textView = new TextView(getContext());
                    textView.setText(categoryBeanList.get(i).name);
                    textView.setTextColor(Color.rgb(88, 44, 131));
                    textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                    textView.setPadding(10, 5, 10, 5);
                    final int finalI = i;
                    final int cateId = categoryBeanList.get(i).Id;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            currentCategoryId = cateId;
                            currentPage = 1;
                            mTitle.setText(categoryBeanList.get(finalI).name);
                            progressBar.setVisibility(View.VISIBLE);
                            productBeanList.clear();
                            getProductsByCategory(ProductURL + "?catcode=" + currentCategoryId);
                        }
                    });
                    mCategories.addView(textView);
                }
                mTitle.setText(initCategoryTitle);
                getProductsByCategory(ProductURL + "?catcode=" + initCategoryId);

            }
        });
    }

    private void getProductsByCategory(final String url) {
        productBeanList = new ArrayList<>();
        new JsonRespon(getContext(), url, new JsonRespon.onComplete() {
            @Override
            public void onComplete(JSONObject json) {
                Log.i("ORM", "lx-test-json: " + json.toString());
                try {
                    JSONArray productsArray = json.getJSONArray("products");
                    ProductBean productBean;
                    for (int i = 0; i < productsArray.length(); i++) {
                        productBean = new ProductBean();
                        JSONObject jsonObject = productsArray.getJSONObject(i);
                        JSONArray imgarr = jsonObject.getJSONArray("image");
                        productBean.productImg = imgarr.getString(0);
                        productBean.productId = jsonObject.getInt("ID");
                        productBean.productName = jsonObject.getString("name");
                        productBean.productPrice = jsonObject.getInt("price");
                        productBean.productRegprice = jsonObject.getInt("regprice");
                        productBean.productDesc = jsonObject.getString("desc");
                        productBeanList.add(productBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
                Log.i("ORM", "onComplete lx-size 1: " + Math.ceil(productBeanList.size() / 2.0));
                Log.i("ORM", "onComplete lx-size 2: " + (int) Math.ceil(productBeanList.size() / 2.0));
                Log.i("ORM", "onComplete lx-size 3: " + productBeanList.size());
                int totalPages = (int) Math.ceil(productBeanList.size() / (double) limit);

                // 分页
                if (getActivity() != null) {
                    LinearLayout pages = new LinearLayout(getContext());
                    pages.setOrientation(LinearLayout.HORIZONTAL);
                    pages.setGravity(Gravity.CENTER);
                    pages.setPadding(10, 10, 10, 10);
                    pages.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    if (currentPage <= totalPages && currentPage > 1) {
                        TextView textView = new TextView(getContext());
                        textView.setText("←");
                        textView.setPadding(10, 10, 10, 10);
                        textView.setTextColor(Color.rgb(88, 44, 131));
                        textView.setBackgroundResource(R.drawable.shape_pagebtn);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                currentPage = currentPage - 1;
                                productBeanList.clear();
                                progressBar.setVisibility(View.VISIBLE);
                                getProductsByCategory(url);
                            }
                        });
                        pages.addView(textView);
                    }
                    for (int i = 1; i <= totalPages; i++) {
                        final TextView textView = new TextView(getContext());
                        textView.setText("" + i);
                        textView.setPadding(10, 10, 10, 10);
                        textView.setTextColor(Color.rgb(88, 44, 131));
                        textView.setWidth(60);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        textView.setBackgroundResource(R.drawable.shape_pagebtn);
                        final int finalI = i;
                        if (i == currentPage) {
                            textView.setTextColor(Color.rgb(204, 204, 204));
                        } else {
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    currentPage = finalI;
                                    productBeanList.clear();
                                    progressBar.setVisibility(View.VISIBLE);
                                    getProductsByCategory(url);
                                }
                            });
                        }
                        if (i == currentPage && currentPage == totalPages) {
                            textView.setBackgroundResource(R.drawable.shape_pagebtn_right);
                        }
                        pages.addView(textView);
                    }
                    if (currentPage < totalPages && currentPage > 0) {
                        TextView textView = new TextView(getContext());
                        textView.setText("→");
                        textView.setPadding(10, 10, 10, 10);
                        textView.setTextColor(Color.rgb(88, 44, 131));
                        textView.setBackgroundResource(R.drawable.shape_pagebtn_right);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                currentPage = currentPage + 1;
                                productBeanList.clear();
                                progressBar.setVisibility(View.VISIBLE);
                                getProductsByCategory(url);
                            }
                        });
                        pages.addView(textView);
                    }

                    Log.i("ORM", "onComplete: lx-totalpages:" + totalPages);
                    getProductsByPage(currentPage, limit, productBeanList, pages);
                }
            }
        });
    }

    private void getProductsByPage(int page, int limit, List<ProductBean> list, View footerView) {
        int offset = (page - 1) * limit;
        final List<ProductBean> mList = new ArrayList<>();
        int last = (offset + limit > list.size() - 1) ? (list.size() - 1) : (offset + limit);
        for (int i = offset; i < last; i++) {
            mList.add(list.get(i));
        }
        adapter = new ProductsAdapter(getContext(), mList);
        adapter.setFooterView(footerView);
        mContentRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new ProductsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i("ORM", "lx-item" + mList.get(position).productName);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment f = new ProductDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("ID",mList.get(position).productId);
                f.setArguments(bundle);
                ft.replace(R.id.frameLayout, f, "details");
                ft.addToBackStack("details");
                ft.commit();
            }
        });
    }

    private static class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductHolder> {
        private Context context;
        private List<ProductBean> productBeanList;

        private int TYPE_NORMAL = 2;
        private int TYPE_FOOTER = 1;

        private View mFooterView;

        private OnItemClickListener onItemClickListener;

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public ProductsAdapter(Context context, List<ProductBean> productBeanList) {
            this.context = context;
            this.productBeanList = productBeanList;
        }

        public void setFooterView(View mFooterView) {
            this.mFooterView = mFooterView;
            notifyItemInserted(getItemCount() - 1);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mFooterView != null && viewType == TYPE_FOOTER) {
                return new ProductHolder(mFooterView);
            }
            View view = LayoutInflater.from(context).inflate(R.layout.item_shopping_list, parent, false);
            ProductHolder holder = new ProductHolder(view);
            final RelativeLayout shopping_item_left = view.findViewById(R.id.shopping_item_left);
            shopping_item_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick((Integer) shopping_item_left.getTag());
                    }
                }
            });
            final RelativeLayout shopping_item_right = view.findViewById(R.id.shopping_item_right);
            shopping_item_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick((Integer) shopping_item_right.getTag());
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ProductHolder holder, int position) {
            if (getItemViewType(position) == TYPE_FOOTER) {
                return;
            }
            Picasso.with(context).load(productBeanList.get(position * 2).productImg.toString()).into(holder.shopping_item_image);
            holder.shopping_item_name.setText(productBeanList.get(position * 2).productName);
            holder.shopping_item_price.setText("$ " + (double) productBeanList.get(position * 2).productPrice);
            holder.addtoCartOnClickListener1.setPosition(position * 2, holder.shopping_item_addtocart, holder.shopping_item_tocart);
            holder.jumptoCartOnClickListener1.setPosition(position * 2);
            holder.shopping_item_left.setTag(position * 2);
            Log.i("ORM", "onBindViewHolder: lx-position:" + position);
            Log.i("ORM", "onBindViewHolder: lx-size:" + productBeanList.size());

            if ((position * 2 + 1) > (productBeanList.size() - 1)) {
                holder.shopping_item_image2.setVisibility(View.GONE);
                holder.shopping_item_name2.setVisibility(View.GONE);
                holder.shopping_item_price2.setVisibility(View.GONE);
                holder.shopping_item_addtocart2.setVisibility(View.GONE);
            } else {
                holder.shopping_item_image2.setVisibility(View.VISIBLE);
                holder.shopping_item_name2.setVisibility(View.VISIBLE);
                holder.shopping_item_price2.setVisibility(View.VISIBLE);
                holder.shopping_item_addtocart2.setVisibility(View.VISIBLE);
                Picasso.with(context).load(productBeanList.get(position * 2 + 1).productImg.toString()).into(holder.shopping_item_image2);
                holder.shopping_item_name2.setText(productBeanList.get(position * 2 + 1).productName);
                holder.shopping_item_price2.setText("$ " + (double) productBeanList.get(position * 2 + 1).productPrice);
                holder.addtoCartOnClickListener2.setPosition(position * 2 + 1, holder.shopping_item_addtocart2, holder.shopping_item_tocart2);
                holder.jumptoCartOnClickListener2.setPosition(position * 2 + 1);
                holder.shopping_item_right.setTag(position * 2 + 1);
            }
//            holder.itemView.setTag(position);
            //0  0 1
            //1  2 3
            //2  4 5
            //3  6 7
            //4  8 9
        }

        @Override
        public int getItemCount() {
            if (mFooterView != null) {
                return (int) Math.ceil(productBeanList.size() / 2.0) + 1;
            }
            return (int) Math.ceil(productBeanList.size() / 2.0);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getItemCount() - 1) {
                return TYPE_FOOTER;
            }
            return TYPE_NORMAL;
        }

        public class ProductHolder extends RecyclerView.ViewHolder {

            private RelativeLayout shopping_item_left, shopping_item_right;
            private ImageView shopping_item_image, shopping_item_image2;
            private TextView shopping_item_name, shopping_item_name2;
            private TextView shopping_item_price, shopping_item_price2;
            private Button shopping_item_addtocart, shopping_item_addtocart2;
            private TextView shopping_item_tocart, shopping_item_tocart2;
            private AddtoCartOnClickListener addtoCartOnClickListener1;
            private AddtoCartOnClickListener addtoCartOnClickListener2;
            private JumptoCartOnClickListener jumptoCartOnClickListener1;
            private JumptoCartOnClickListener jumptoCartOnClickListener2;

            public ProductHolder(View itemView) {
                super(itemView);
                if (itemView == mFooterView) {
                    return;
                }
                shopping_item_image = itemView.findViewById(R.id.shopping_item_image);
                shopping_item_name = itemView.findViewById(R.id.shopping_item_name);
                shopping_item_price = itemView.findViewById(R.id.shopping_item_price);
                shopping_item_addtocart = itemView.findViewById(R.id.shopping_item_addtocart);
                shopping_item_tocart = itemView.findViewById(R.id.shopping_item_tocart);
                addtoCartOnClickListener1 = new AddtoCartOnClickListener();
                shopping_item_addtocart.setOnClickListener(addtoCartOnClickListener1);
                jumptoCartOnClickListener1 = new JumptoCartOnClickListener();
                shopping_item_tocart.setOnClickListener(jumptoCartOnClickListener1);
                shopping_item_left = itemView.findViewById(R.id.shopping_item_left);

                shopping_item_image2 = itemView.findViewById(R.id.shopping_item_image2);
                shopping_item_name2 = itemView.findViewById(R.id.shopping_item_name2);
                shopping_item_price2 = itemView.findViewById(R.id.shopping_item_price2);
                shopping_item_addtocart2 = itemView.findViewById(R.id.shopping_item_addtocart2);
                shopping_item_tocart2 = itemView.findViewById(R.id.shopping_item_tocart2);
                addtoCartOnClickListener2 = new AddtoCartOnClickListener();
                shopping_item_addtocart2.setOnClickListener(addtoCartOnClickListener2);
                jumptoCartOnClickListener2 = new JumptoCartOnClickListener();
                shopping_item_tocart2.setOnClickListener(jumptoCartOnClickListener2);
                shopping_item_right = itemView.findViewById(R.id.shopping_item_right);
            }
        }

        class AddtoCartOnClickListener implements View.OnClickListener {
            private int position;
            private TextView addToCart;
            private TextView toCart;

            public void setPosition(int position, TextView addToCart, TextView toCart) {
                this.position = position;
                this.toCart = toCart;
                this.addToCart = addToCart;
            }

            @Override
            public void onClick(View view) {
                Log.i("ORM", "lx-onClick: " + position);
                toCart.setVisibility(View.VISIBLE);
                addToCart.setTextColor(Color.LTGRAY);
                addToCart.setText("加到購物車 √");
            }
        }

        class JumptoCartOnClickListener implements View.OnClickListener {
            private int position;

            public void setPosition(int position) {
                this.position = position;
            }

            @Override
            public void onClick(View view) {
                Log.i("ORM", "lx-onClick: " + position);
            }
        }
    }
}
