package com.assignment.justdabao.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.assignment.justdabao.MainActivity;
import com.assignment.justdabao.R;
import com.assignment.justdabao.SplashActivity;

public class SuperActivity extends AppCompatActivity {

    public void gotoActivity(Class activityClass, Bundle bundle) {
        try {
            Intent intent = new Intent(this, activityClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    public void goToActivityWithoutFinish(Context context, Bundle bundle, Class<?> aClass){
        Intent intentLogin = new Intent(context, aClass);
        if(bundle != null)
            intentLogin.putExtras(bundle);
        startActivity(intentLogin);
    }
    public void gotoFragment(Fragment fragment, Bundle bundle, String backStackTag, Context activity) {
        try {
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(0, 0);
            if (activity instanceof MainActivity) {
                fragmentTransaction.replace(R.id.frame_main, fragment);
            }else if(activity instanceof SplashActivity){
                fragmentTransaction.replace(R.id.frame_splash,fragment);
            }
            if (CommonUtil.isValidString(backStackTag)) {
                fragmentTransaction.addToBackStack(backStackTag);
            }
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
