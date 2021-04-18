package com.assignment.justdabao.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtil {
    static int ITEMCOUNT = Integer.MAX_VALUE;

    public static boolean isInternetConnectivityAvailable(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo isConnectivityAvailable = connectivityManager.getActiveNetworkInfo();
            return isConnectivityAvailable != null;
        }
        return true;
    }

    public static boolean isValidString(String s) {
        return (s != null && !s.trim().isEmpty() && !s.equalsIgnoreCase("null"));
    }

    public static boolean isValidEmail(String string) {
        return (isValidString(string) && string.contains("@") && Patterns.EMAIL_ADDRESS.matcher(string).matches());
    }

    public static void writePrefBoolean(Context context, String key, boolean value) {
        try {
            if (context == null)
                return;
            SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE).edit();
            editor.putBoolean(key, value);
            editor.apply();
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean readPrefBoolean(Context context, String key) {
        if (context == null)
            return false;
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static void writePrefString(Context context, String key, String value) {
        try {
            if (context == null)
                return;
            SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(key, value);
            editor.apply();
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readPrefString(Context context, String key) {
        if(context == null) return "";
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(key,"");
    }
    public static void writePrefSet(Context context, String key, Set<String> value) {
        try {
            if (context == null)
                return;
            SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE).edit();
            editor.putStringSet(key, value);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Set<String> readPrefSet(Context context, String key) {
        if(context == null)return null;
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getStringSet(key,null);
    }

    public static boolean isValidPhone(String string) {
        return (isValidString(string) && string.length() <= 10 && Patterns.PHONE.matcher(string).matches());
    }

    public static String getTime() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        dateFormat.setTimeZone(tz);
        return (dateFormat.format(new Date()));
    }
    public static void showToast(Context context, String msg) {
        msg = CommonUtil.isValidString(msg) ? msg : "Something went wrong.";
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static final String setTime(int seconds){
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
        String time = "";
        time = time + day + " D ";
        time = time + hours + " H ";
        time = time + minute + " M";
        return time;
    }

    public static final String setTimeUpes(int seconds){
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
        String time = "";
        time = time + hours + " h ";
        time = time + minute + " m ";
        time = time + second + " s " ;
        return time;
    }



    public static String getTimeinUTC(Calendar calendar){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        dateFormat.setTimeZone(tz);
        return (dateFormat.format(calendar.getTime()));
    }
    public static String getFormattedDate(Date date1, String resultPattern) {
        try {
            if(!isValidString(resultPattern))
                resultPattern = "dd MMM yy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(resultPattern);
            return simpleDateFormat.format(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void hideSoftKeyBoard(Activity activity) {
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && inputMethodManager != null)
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String getFormattedDate(String date, String currentPattern, String resultPattern) {//GENERIC
        try {
            if(date == null || date.equals("null") || date.equals(""))
                return null;
            currentPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";
            if(date.length() < 25){
                currentPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            }
            if(date.length() < 24){
                currentPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS";
            }
            if(date.length() < 21){
                currentPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            }
            if(date.length() < 11){
                currentPattern = "yyyy-MM-dd";
            }
            if(date.startsWith("Date")) return date;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(resultPattern);
            Date date1 = new SimpleDateFormat(currentPattern).parse(date);
            return simpleDateFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getSpecticFormattedDate(String date, String currentPattern, String resultPattern) {//SPECIFIC
        try {
            if(date == null || date.equals("null") || date.equals(""))
                return null;

            if(date.startsWith("Date")) return date;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(resultPattern);
            Date date1 = new SimpleDateFormat(currentPattern).parse(date);
            return simpleDateFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static SharedPreferences getSharedPref(Context context) {
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }


    public static   boolean  isUserLoggedin(Context context){
        return readPrefBoolean(context, Constants.PREF_IS_LOGGED_IN);
        //return false;
    }
    public static void setCollectionPref(Context context, String key, Set<String> set) {
        SharedPreferences.Editor editor = context.getSharedPreferences(key, Context.MODE_PRIVATE).edit();
        Set<String> addedSet = context.getSharedPreferences(key, Context.MODE_PRIVATE).getStringSet(key, null);
        if (addedSet != null && !addedSet.isEmpty()) {
            set.addAll(addedSet);
        }
        editor.clear();
        editor.putStringSet(key, set);
        editor.apply();
        editor.commit();
        Log.d("storesharedPreferences", "" + set);
    }

    public static Set<String> getCollectionPref(Context context, String key) {
        Set<String> set = context.getSharedPreferences(key, Context.MODE_PRIVATE).getStringSet(key, null);
        if (set == null || set.isEmpty()) return null;
        Log.d("retrisharedPreferences", "" + set);
        return set;

    }

    public static String getEncodedImgFromURI(Uri resultUri) {
        File imagefile = new File(resultUri.getPath());
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(imagefile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,60,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    public static String extractYTId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()){
            vId = matcher.group(1);
        }
        return vId;
    }

    public static String getYouTubeId (String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "";
        }
    }





}
