package com.assignment.justdabao.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assignment.justdabao.MainActivity;
import com.assignment.justdabao.R;
import com.assignment.justdabao.main.models.RestaurantsModel;


public class CheckInFragment extends Fragment {

    View view;
    ImageView homeImg;
    TextView title,pickupTime,addToCart,buyItNow;
    LinearLayout quantity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_check_in, container, false);
        initUI();
        getDataFromBundle();
        setListener();
        return view;
    }

    private void getDataFromBundle() {
        Bundle bundle = getArguments();
        if(bundle != null){
            RestaurantsModel    restaurantsModel = (RestaurantsModel)bundle.getSerializable("key");
            
        }


    }

    private void setListener() {
        buyItNow.setOnClickListener(v -> ((MainActivity)getActivity()).gotoFragment(new CheckOutFragment(),null,"CheckInFragment",getActivity()));
    }

    private void initUI() {
        homeImg = view.findViewById(R.id.home_img);
        title   = view.findViewById(R.id.title);
        quantity= view.findViewById(R.id.quantitiy);
        pickupTime = view.findViewById(R.id.pick_up_time);
        addToCart = view.findViewById(R.id.add_to_cart_btn);
        buyItNow    = view.findViewById(R.id.buy_it_now_btn);
    }
}