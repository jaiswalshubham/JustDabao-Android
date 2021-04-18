package com.assignment.justdabao;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.assignment.justdabao.splash.FeatureScreenFragment;
import com.assignment.justdabao.splash.SplashFragment;
import com.assignment.justdabao.utils.CommonUtil;
import com.assignment.justdabao.utils.Constants;
import com.assignment.justdabao.utils.SuperActivity;

public class SplashActivity extends SuperActivity {

    Handler splashHandler;
    Runnable splashRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        gotoFragment(new SplashFragment(), null, null, SplashActivity.this);
        startSplashTimer();
    }

    private void startSplashTimer() {
        try {
            splashHandler = new Handler(Looper.myLooper());
            splashRunnable = this::goToNextScreen;
            splashHandler.postDelayed(splashRunnable, Constants.SPLASH_DURATION_IN_MS);
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    private void goToNextScreen() {
        try {
            boolean first = CommonUtil.readPrefBoolean(this, Constants.PREF_IS_APP_LAUNCHED);
            if (!first){
                //  gotoFragment(new CoachSreenFragment(), null, null, SplashActivity.this);
                gotoFragment(new FeatureScreenFragment(), null, null, SplashActivity.this);
            } else {
                    gotoActivity(MainActivity.class,null);
            }
            splashHandler.removeCallbacks(splashRunnable);
        } catch (Exception e) {
           e.printStackTrace();
        }

    }
}