package com.assignment.justdabao.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assignment.justdabao.MainActivity;
import com.assignment.justdabao.R;
import com.assignment.justdabao.main.models.AddItem;
import com.assignment.justdabao.main.models.AllItem;
import com.assignment.justdabao.main.models.RestaurantsModel;
import com.assignment.justdabao.utils.CommonUtil;
import com.assignment.justdabao.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;


public class CheckInFragment extends Fragment {

    View view;
    ImageView homeImg,quantityIncrease,quantityDecrease;
    TextView title,pickupTime,quantityCount,addToCart,buyItNow,price,totalAmount;
    LinearLayout quantity;
    int quantityAdded = 0;
    RestaurantsModel    restaurantsModel;
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
                restaurantsModel = (RestaurantsModel)bundle.getSerializable("key");
            title.setText(restaurantsModel.getTitle());
            Picasso.get().load(restaurantsModel.getImage_url()).centerCrop().fit().into(homeImg);
            price.setText(restaurantsModel.getAvg_price());
        }
        quantityIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityAdded++;
                quantityCount.setText(quantityAdded+"");
                String value = String.format("%.2f",(quantityAdded * Double.parseDouble(restaurantsModel.getAvg_price())));
                totalAmount.setText(value);
            }
        });

        quantityDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantityAdded == 0)
                    return;
                quantityAdded--;
                quantityCount.setText(quantityAdded+"");
                totalAmount.setText((quantityAdded * Double.parseDouble(restaurantsModel.getAvg_price() )+ ""));
            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllItem allItem = null;

                String addedItem = CommonUtil.readPrefString(getActivity(), Constants.ADDED_ITEM);
                if(addedItem != null && !addedItem.isEmpty()){
                    allItem =  new Gson().fromJson(addedItem, new TypeToken<AllItem>() {
                    }.getType());
                    boolean isAdded = false;
                    for(AddItem addItem: allItem.getAddItemList()){
                        if(addItem.getId().equalsIgnoreCase(restaurantsModel.getId())){
                            addItem.setQuantity(quantityAdded);
                            isAdded = true;
                            break;
                        }
                    }
                    if(!isAdded){
                        AddItem addItem = new AddItem(restaurantsModel.getId(),quantityAdded);
                        allItem.getAddItemList().add(addItem);
                    }
                }else {
                    AddItem addItem = new AddItem(restaurantsModel.getId(),quantityAdded);
                    allItem = new AllItem(new ArrayList<>(Arrays.asList(addItem)));
                }

                CommonUtil.writePrefString(getActivity(), Constants.ADDED_ITEM,new Gson().toJson(allItem));
                FragmentManager fm = getActivity().getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                ((MainActivity)getActivity()).gotoFragment(new HomeFragment(),null,null,getActivity());
                ((MainActivity)getActivity()).updateCartCount();
            }
        });



    }

    private void setListener() {
        buyItNow.setOnClickListener(v -> {
            String addedItem = CommonUtil.readPrefString(getActivity(), Constants.ADDED_ITEM);
            AllItem allItem =  new Gson().fromJson(addedItem, new TypeToken<AllItem>() {
            }.getType());
            if(allItem == null){
                CommonUtil.showToast(getActivity(),"Please add something to place the order.");
                return;
            }
            ((MainActivity)getActivity()).gotoFragment(new CheckOutFragment(),null,"CheckInFragment",getActivity());

        });
    }

    private void initUI() {
        homeImg = view.findViewById(R.id.home_img);
        title   = view.findViewById(R.id.title);
        quantity= view.findViewById(R.id.quantitiy);
        quantityCount= view.findViewById(R.id.quantityCount);
        price   = view.findViewById(R.id.price);
        pickupTime = view.findViewById(R.id.pick_up_time);
        totalAmount = view.findViewById(R.id.total_amount);
        addToCart = view.findViewById(R.id.add_to_cart_btn);
        buyItNow    = view.findViewById(R.id.buy_it_now_btn);
        quantityIncrease = view.findViewById(R.id.quantityIncrease);
        quantityDecrease = view.findViewById(R.id.quantityDecrease);
    }
}