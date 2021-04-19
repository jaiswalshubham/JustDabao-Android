package com.assignment.justdabao.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.justdabao.MainActivity;
import com.assignment.justdabao.R;
import com.assignment.justdabao.main.CheckInFragment;
import com.assignment.justdabao.main.models.RestaurantsModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    List<RestaurantsModel> restaurantsModels = new ArrayList<>();

    public HomeAdapter(Context context) {
        this.context = context;
    }

    public HomeAdapter(Context context, List<RestaurantsModel> restaurantsModels) {
        this.context = context;
        this.restaurantsModels = restaurantsModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater != null ? inflater.inflate(R.layout.layout_home, parent, false) : null;
        return new HomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.homeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("key",(Serializable) restaurantsModels.get(position));
                ((MainActivity)context).gotoFragment(new CheckInFragment(),bundle,"HomeFragment",context);
            }
        });
        holder.price.setText("Rs. " + restaurantsModels.get(position).getAvg_price());
        holder.pickupType.setText(restaurantsModels.get(position).getPickUpType());
        holder.productType.setText(restaurantsModels.get(position).getTitle());
        Picasso.get().load(restaurantsModels.get(position).getImage_url()).placeholder(R.drawable.img_icecream_1).fit().centerCrop().into(holder.homeImg, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("TAG", "success");
            }

            @Override
            public void onError(Exception e) {
                Log.e("TAG", e.getLocalizedMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout homeCard;
        ImageView homeImg;
        TextView productType,pickupType,price,distanceFromLocaiton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeCard = itemView.findViewById(R.id.home_card_rl);
            homeImg  = itemView.findViewById(R.id.home_img);
            productType = itemView.findViewById(R.id.product_type);
            pickupType  = itemView.findViewById(R.id.pickup_type);
            price    = itemView.findViewById(R.id.price);
            distanceFromLocaiton    = itemView.findViewById(R.id.distance_from_location);
        }
    }
}
