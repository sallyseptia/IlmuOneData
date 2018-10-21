package com.example.asus.ilmuonedata.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.ilmuonedata.Interface.ItemClickListener;
import com.example.asus.ilmuonedata.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public TextView product_name;
    public ImageView product_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ProductViewHolder(View itemView) {
        super(itemView);
        product_name = (TextView)itemView.findViewById(R.id.info_text);
        product_image = (ImageView)itemView.findViewById(R.id.product_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
