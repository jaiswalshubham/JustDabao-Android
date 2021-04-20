package com.assignment.justdabao.main;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.assignment.justdabao.MainActivity;
import com.assignment.justdabao.R;
import com.assignment.justdabao.main.adapter.PurchaseAdapter;
import com.assignment.justdabao.main.models.AddItem;
import com.assignment.justdabao.main.models.AllItem;
import com.assignment.justdabao.main.models.RestaurantsModel;
import com.assignment.justdabao.utils.CommonUtil;
import com.assignment.justdabao.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class CheckOutFragment extends Fragment {

    View view;
    RecyclerView purchaseDetailsRecycler;
    int quantityOrder;
    TextView subTotal,paymentInitiatedText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_check_out, container, false);
        initUI();
        setAdapter();
        return view;
    }

    private void setAdapter() {
        purchaseDetailsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        String addedItem = CommonUtil.readPrefString(getActivity(), Constants.ADDED_ITEM);
        AllItem allItem =  new Gson().fromJson(addedItem, new TypeToken<AllItem>() {
        }.getType());
        List<RestaurantsModel> addedRestrauntList = new ArrayList<>();
        List<RestaurantsModel> restaurantsModels = new Gson().fromJson(Constants.DUMMY_DATA, new TypeToken<List<RestaurantsModel>>(){}.getType());
        for (RestaurantsModel restaurantsModel: restaurantsModels){
            for (AddItem addItem: allItem.getAddItemList()){
                if(addItem.getId().equalsIgnoreCase(restaurantsModel.getId())){
                    restaurantsModel.setQuantity(addItem.getQuantity());
                    addedRestrauntList.add(restaurantsModel);
                }
            }
        }

        purchaseDetailsRecycler.setAdapter(new PurchaseAdapter(addedRestrauntList));
        double value = 0.0;
        for (RestaurantsModel restaurantsModel:restaurantsModels){
            value +=(restaurantsModel.getQuantity() * Double.parseDouble(restaurantsModel.getAvg_price()));
        }
        subTotal.setText(String.format("Sub Total  %.2f",value));
        paymentInitiatedText.setText(String.format("Check Out & Pay  %.2f",value));

    }

    private void initUI() {
        purchaseDetailsRecycler = view.findViewById(R.id.purchase_details_recycler);
        subTotal                = view.findViewById(R.id.sub_total);
        paymentInitiatedText    = view.findViewById(R.id.payment_initiated_txt);
        paymentInitiatedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.writePrefString(getActivity(),Constants.ADDED_ITEM,null);
                CommonUtil.showToast(getActivity(),"Order placed Successfully.");
                Intent resultIntent = new Intent(getActivity(), MainActivity.class);
                resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                showNotificationMessage(resultIntent);
                ((MainActivity)getActivity()).setCartCountZero();
               ( (MainActivity)getActivity()).gotoFragment(new HomeFragment(),null,null,getActivity());
            }
        });
    }


    public void showNotificationMessage(  Intent intent) {
        try {
            final int icon = R.drawable.app_icon;
            String title = getActivity().getResources().getString(R.string.app_name);
//            HashMap<String, Object> userInfo = new HashMap<>();
//            userInfo.put("contentId", "238472984734-091281038921-2983171283");
//            userInfo.put("notificationType", "library");
//            userInfo.put("externalLink", "https://www.google.com");


            String message = "Order placed successfully.";
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            final PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            getActivity(),
                            0,
                            intent,
                            PendingIntent.FLAG_CANCEL_CURRENT
                    );

            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationManager notificationManager = (NotificationManager) getActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mChannel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_HIGH);
                mChannel.setLightColor(Color.GRAY);
                mChannel.enableLights(true);
                mChannel.setDescription("Desc1");

                if (notificationManager != null) {
                    notificationManager.createNotificationChannel( mChannel );
                }
            }

            RemoteViews contentView = new RemoteViews(getActivity().getPackageName(), R.layout.push_notifications_layout);
            contentView.setTextViewText(R.id.buildText,"Just Dabao \nOrder Placed Successfully.");
            contentView.setImageViewResource(R.id.image, icon);
            contentView.setTextViewText(R.id.title, title);

            Notification mBuilder = new NotificationCompat.Builder(getActivity(), "channel_id")
                    .setSmallIcon(icon)
                    .setContent(contentView)
                    .setContentIntent(resultPendingIntent)
                    .build();
            mBuilder.flags |=Notification.FLAG_AUTO_CANCEL;
            int NOTIFICATION_ID = 0; // Causes to update the same notification over and over again.
            if (notificationManager != null) {
                notificationManager.notify(NOTIFICATION_ID, mBuilder);
            }

        } catch (Exception e) {
          e.printStackTrace();
        }
    }
}