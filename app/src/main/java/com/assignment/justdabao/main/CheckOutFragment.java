package com.assignment.justdabao.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assignment.justdabao.R;
import com.assignment.justdabao.main.adapter.PurchaseAdapter;


public class CheckOutFragment extends Fragment {

    View view;
    RecyclerView purchaseDetailsRecycler;
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
        purchaseDetailsRecycler.setAdapter(new PurchaseAdapter());
    }

    private void initUI() {
        purchaseDetailsRecycler = view.findViewById(R.id.purchase_details_recycler);
        subTotal                = view.findViewById(R.id.sub_total);
        paymentInitiatedText    = view.findViewById(R.id.payment_initiated_txt);
    }
}