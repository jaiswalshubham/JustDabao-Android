package com.assignment.justdabao.splash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.assignment.justdabao.utils.CommonUtil;
import com.assignment.justdabao.utils.Constants;
import com.assignment.justdabao.MainActivity;
import com.assignment.justdabao.R;
import com.assignment.justdabao.SplashActivity;

public class FeatureScreenFragment extends Fragment {
    View view;
    ImageView forwardImg,splashImg;
    boolean isFirstClick = false,isSecondClick = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feature_screen, container, false);
        CommonUtil.writePrefBoolean(getContext(), Constants.PREF_IS_APP_LAUNCHED,true);
        initUI();
        setListener();
        return  view;
    }

    private void setListener() {
        forwardImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSecondClick){
                    ((SplashActivity)getActivity()).gotoActivity(MainActivity.class,null);
                    return;
                }
                if(!isFirstClick){
                    splashImg.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.splash2));
                    isFirstClick = true;
                }else {
                    splashImg.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.splash3));
                    isSecondClick = true;
                }
            }
        });
    }

    private void initUI() {
        forwardImg = view.findViewById(R.id.forward_img);
        splashImg  = view.findViewById(R.id.splash_img);
    }

}