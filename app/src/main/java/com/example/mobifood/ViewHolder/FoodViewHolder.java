package com.example.mobifood.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobifood.Interface.ItemClickListener;
import com.example.mobifood.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView FoodName,FoodDescription;
    public ImageView FoodImage;
    public ItemClickListener listener;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        FoodName = (TextView) itemView.findViewById(R.id.food_category_name);
        FoodDescription = (TextView) itemView.findViewById(R.id.categoryDescription);
        FoodImage = (ImageView) itemView.findViewById(R.id.food_category_image);
    }

    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener =listener;
    }

    @Override
    public void onClick(View v)
    {
        listener.onClick(v,getAdapterPosition(),false);
    }
}
