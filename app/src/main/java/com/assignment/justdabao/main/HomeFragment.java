package com.assignment.justdabao.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assignment.justdabao.R;
import com.assignment.justdabao.main.adapter.HomeAdapter;


public class HomeFragment extends Fragment {

    View view;
    RecyclerView homeRecycler;
    HomeAdapter homeAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initUI();
        setAdapter();
        return view;
    }

    private void initUI() {
        homeRecycler = view.findViewById(R.id.home_recycler);
    }

    private void setAdapter() {
        homeAdapter = new HomeAdapter(getActivity());
        homeRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        homeRecycler.setAdapter(homeAdapter);
    }
}