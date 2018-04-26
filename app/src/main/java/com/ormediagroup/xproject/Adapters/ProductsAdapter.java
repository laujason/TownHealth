package com.ormediagroup.xproject.Adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ormediagroup.xproject.Beans.ProductBean;
import com.ormediagroup.xproject.DB.DBOpenHelper;
import com.ormediagroup.xproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lau on 2018/4/26.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductHolder> {
    private Context context;
    private List<ProductBean> productBeanList;

    private int TYPE_NORMAL = 2;
    private int TYPE_FOOTER = 1;

    private String DB_NAME = "TownHealth.db";
    private String TB_NAME = "tb_cart";

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
            DBOpenHelper helper = new DBOpenHelper(context,DB_NAME,null,1);
            SQLiteDatabase db = helper.getWritableDatabase();
            
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
