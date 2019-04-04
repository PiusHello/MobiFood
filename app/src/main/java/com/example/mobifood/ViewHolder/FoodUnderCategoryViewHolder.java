package com.example.mobifood.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobifood.Interface.ItemClickListener;
import com.example.mobifood.R;

public class FoodUnderCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView FoodUnderCategoryName;
    public TextView FoodUnderCategoryDescription;
    public TextView FoodUnderCategoryPrice;
    public ImageView FoodUnderCategoryImage;
    public ItemClickListener listener;


    public FoodUnderCategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        FoodUnderCategoryName = (TextView) itemView.findViewById(R.id.foodUnderCategoryName);
        FoodUnderCategoryDescription = (TextView) itemView.findViewById(R.id.foodUnderCategoryDescription);
        FoodUnderCategoryPrice = (TextView) itemView.findViewById(R.id.foodUnderCategoryPrice);
        FoodUnderCategoryImage = (ImageView) itemView.findViewById(R.id.foodUnderCategoryImage);
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
