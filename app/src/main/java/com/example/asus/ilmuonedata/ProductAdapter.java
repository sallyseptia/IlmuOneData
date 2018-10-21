package com.example.asus.ilmuonedata;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.ilmuonedata.Interface.ItemClickListener;
import com.example.asus.ilmuonedata.Model.Product;
import com.example.asus.ilmuonedata.ViewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{

    List<Product> listProduct = new ArrayList<>();
    Context context;
    ItemClickListener clickListener;

    // data is passed into the constructor
    ProductAdapter(Context context, List<Product> listProduct, ItemClickListener clickListener) {
        this.context = context;
        this.listProduct = listProduct;
        this.clickListener = clickListener;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(v);
        viewHolder.setItemClickListener(clickListener);
        return viewHolder;
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = listProduct.get(position);

        holder.product_name.setText(product.getName());
        Picasso.with(context).load(product.getImage()).into(holder.product_image);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return listProduct.size();
    }

}