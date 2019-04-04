package com.example.mobifood;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobifood.Model.FoodUnderCategoryModel;
import com.example.mobifood.ViewHolder.FoodUnderCategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class FoodUnderCategory extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    DatabaseReference myDatabase;

    String categoryID;
    Query query;
    String foodKey = null;

    FirebaseRecyclerAdapter<FoodUnderCategory, FoodUnderCategoryViewHolder> adapter;

    private TextView FoodUnderCategoryName;
    private TextView FoodUnderCategoryDescription;
    private TextView FoodUnderCategoryPrice;
    private Button FoodUnderCategoryButton;
    private ImageView FoodUnderCategoryImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_under_category);


        categoryID = getIntent().getStringExtra("CategoryID");
        Log.v("foodkeylist",categoryID);

        myDatabase = FirebaseDatabase.getInstance().getReference("Food_Stuffs");
        query = myDatabase.orderByChild("MenuID").equalTo(categoryID);

        foodKey = getIntent().getStringExtra("FoodID");
        myDatabase = FirebaseDatabase.getInstance().getReference().child("Food_Stuffs");


        FoodUnderCategoryName = (TextView) findViewById(R.id.foodUnderCategoryName);
        FoodUnderCategoryDescription = (TextView) findViewById(R.id.foodUnderCategoryDescription);
        FoodUnderCategoryPrice = (TextView) findViewById(R.id.foodUnderCategoryPrice);
        FoodUnderCategoryButton = (Button) findViewById(R.id.foodUnderCategoryButton);
        FoodUnderCategoryImage = (ImageView) findViewById(R.id.foodUnderCategoryImage);

        recyclerView = (RecyclerView) findViewById(R.id.foodUnderCategoryRecycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<FoodUnderCategoryModel> options =
        new FirebaseRecyclerOptions.Builder<FoodUnderCategoryModel>()
        .setQuery(query,FoodUnderCategoryModel.class)
        .build();

        FirebaseRecyclerAdapter<FoodUnderCategoryModel,FoodUnderCategoryViewHolder> adapter =
                new FirebaseRecyclerAdapter<FoodUnderCategoryModel, FoodUnderCategoryViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FoodUnderCategoryViewHolder holder, int position, @NonNull FoodUnderCategoryModel model) {

                        holder.FoodUnderCategoryName.setText(model.getName());
                        holder.FoodUnderCategoryDescription.setText(model.getDescription());
                        holder.FoodUnderCategoryPrice.setText(model.getPrice());
                        Picasso.get().load(model.getImage()).into(holder.FoodUnderCategoryImage);

                    }

                    @NonNull
                    @Override
                    public FoodUnderCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_under_category,viewGroup,false);
                        FoodUnderCategoryViewHolder holder = new FoodUnderCategoryViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
