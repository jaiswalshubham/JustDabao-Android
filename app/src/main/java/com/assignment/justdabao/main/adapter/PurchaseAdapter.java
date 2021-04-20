package com.assignment.justdabao.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.justdabao.R;
import com.assignment.justdabao.main.models.RestaurantsModel;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {

    private  List<RestaurantsModel> restaurantsModels;


    public PurchaseAdapter(List<RestaurantsModel> restaurantsModels) {

        this.restaurantsModels = restaurantsModels;
    }

    @NonNull
    @Override
    public PurchaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater != null ? inflater.inflate(R.layout.layout_purchase, parent, false) : null;
        return new PurchaseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseAdapter.ViewHolder holder, int position) {
        String value = String.format("%.2f",(restaurantsModels.get(position).getQuantity() * Double.parseDouble(restaurantsModels.get(position).getAvg_price())));
        holder.priceTotal.setText(value);
        holder.title.setText(restaurantsModels.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return restaurantsModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,priceTotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            priceTotal = itemView.findViewById(R.id.price);
        }
    }
}
