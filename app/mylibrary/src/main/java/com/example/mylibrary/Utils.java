package com.example.mylibrary;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentNavigator;

import com.google.android.material.transition.MaterialFadeThrough;
import com.google.android.material.transition.platform.MaterialArcMotion;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialFade;
import com.google.android.material.transition.platform.MaterialSharedAxis;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

public class Utils {
//    jcenter()
//    maven { url "https://jitpack.io" }
//    implementation 'nl.joery.animatedbottombar:library:1.0.9'
//    implementation 'com.jaeger.statusbarutil:library:1.5.1'
//    implementation 'com.github.GrenderG:Toasty:1.5.0'
//    implementation 'com.wang.avi:library:2.1.3'
//    implementation 'androidx.lifecycle:lifecycle-service:2.3.0-alpha02'
//    implementation "androidx.paging:paging-runtime:2.1.2"
//    def room_version = "2.3.0"
//    implementation 'tech.liujin:network-state-manager:1.0.1'
//    implementation "androidx.room:room-runtime:$room_version"
//    annotationProcessor "androidx.room:room-compiler:$room_version"
//    implementation 'com.youth.banner:banner:1.4.9'
//    implementation 'com.gongwen:marqueelibrary:1.1.3'
//    implementation 'com.facebook.shimmer:shimmer:0.5.0'
//    implementation 'com.github.zhpanvip:BannerViewPager:3.5.0'
//    implementation 'com.github.bumptech.glide:glide:4.12.0'
//    implementation 'com.mzlion:easy-okhttp:1.1.0'
//    implementation 'com.github.theonlyanil:Rebound-RecyclerView-Android:0.3'
//    implementation 'com.google.code.gson:gson:2.8.6'
//    implementation 'com.android.volley:volley:1.1.1'
//    implementation 'com.jcodecraeer:xrecyclerview:1.6.0'
//    implementation 'com.stone.vega.library:VegaLayoutManager:1.0.6'
//    implementation 'io.github.everythingme:overscroll-decor-android:1.1.1'
//    implementation 'com.ramotion.paperonboarding:paper-onboarding:1.1.3'
//    implementation 'com.roughike:bottom-bar:2.3.1'
//    implementation 'com.airbnb.android:lottie:3.7.0'
//    implementation 'com.daimajia.androidanimations:library:2.4@aar'
//    implementation 'com.github.f0ris.sweetalert:library:1.5.1'

    //将json文件变成字符串
    public static String getJson(String fileName, Context context) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    //获取AndroidID
    public static String getAndroidID(Context context) {
        try {
            String androidId = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            return androidId;
        } catch (Exception e) {
            Log.d("tag", "getandroidID: ");
        }
        return "";
    }

    //获取通讯录返回hushmap
    public static HashMap get_phone(Context context) throws JSONException {
        Cursor cursor = null;
        HashMap hashMap = new HashMap();

        cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {

                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                hashMap.put(displayName, number);

            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return hashMap;
    }
    //设置状态栏文字是否为黑色
    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
    //判断是否有网络
    public static boolean isNetworkAvailable(Context context) {

        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isAvailable();
    }
    private static NetworkInfo getNetworkInfo(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
    //随机数
    public static int sj(int min1,int max1){
        int max=max1;
        int min=min1;
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }

    //保留两位小数
    public static double nextDouble(final double min, final double max) throws Exception {



        DecimalFormat df = new DecimalFormat("#.00");
        if (max < min) {

            throw new Exception("min < max");

        }

        if (min == max) {

            return min;

        }

        return  Double.parseDouble(df.format(min + ((max - min) * new Random().nextDouble())));
    }


    public static void hideBottomUIMenu(Window window) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = window.getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = window.getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    //判断字符串是否是整数
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


    //沉浸式状态栏
    public static void cjs(Window window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Window window=getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            //隐藏虚拟导航栏
            visibility |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(visibility);

        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }



    //material动画view中视图变换
    public static void bianhua(View startView, View endview, ViewGroup container){
        MaterialContainerTransform materialContainerTransform = new MaterialContainerTransform();
        materialContainerTransform.setStartView(startView);
        materialContainerTransform.setEndView(endview);
        materialContainerTransform.setDuration(2000);

        materialContainerTransform.setPathMotion(new MaterialArcMotion());
        materialContainerTransform.setScrimColor(Color.TRANSPARENT);

        materialContainerTransform.addTarget(endview);
        materialContainerTransform.setFadeMode(MaterialContainerTransform.FADE_MODE_THROUGH);
        materialContainerTransform.setFadeProgressThresholds(new MaterialContainerTransform.ProgressThresholds(0f, 1f));
        TransitionManager.beginDelayedTransition(container, materialContainerTransform);

        startView.setVisibility(View.GONE);

        endview.setVisibility(View.VISIBLE);
    }


    //共享轴视图动画
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void gxz(int fangxiang,boolean shifouqianjin,ViewGroup container,View startview,View endview){
        MaterialSharedAxis materialSharedAxis = new MaterialSharedAxis( fangxiang, shifouqianjin);
        TransitionManager.beginDelayedTransition(container, materialSharedAxis);
        startview.setVisibility(View.GONE);
        endview.setVisibility(View.VISIBLE);
    }

    //共享轴fragment动画
    public static void gxzFragmentAnimation(Fragment fragment, boolean jin, int jinfx, boolean tui, int tuifx){
        MaterialSharedAxis reenterTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            reenterTransition = new MaterialSharedAxis(tuifx,tui);


        }
        fragment.setReenterTransition(reenterTransition);

        MaterialSharedAxis exitTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            exitTransition = new MaterialSharedAxis(jinfx,jin);

        }
        fragment.setExitTransition(exitTransition);

    }

    //淡入淡出动画fragment
    public static void gddh(Fragment fragment, boolean sfjr){
        MaterialFadeThrough materialFadeThrough = new MaterialFadeThrough();


        if (sfjr) {
            fragment.setReenterTransition(materialFadeThrough);
        } else {
            fragment.setExitTransition(materialFadeThrough);
        }

    }

    //淡入淡出动画视图

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void gddhst(ViewGroup c,View start,View end){
        MaterialFadeThrough materialFadeThrough = new MaterialFadeThrough();
        androidx.transition.TransitionManager.beginDelayedTransition(c,materialFadeThrough);
        start.setVisibility(View.GONE);
        end.setVisibility(View.VISIBLE);
    }
    //视图淡入淡出
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void viewdan(View view,ViewGroup container){
        MaterialFade materialFade = new MaterialFade();
        materialFade.setDuration(300);
        TransitionManager.beginDelayedTransition(container, materialFade);
        view.setVisibility(View.GONE);
        //应该也可已设置显示dialog
    }


    //material动画获取过度动画的extras
    //fragment中oncreate里写这句
    //setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
    public static FragmentNavigator.Extras getextras(View view,String name){
        return new FragmentNavigator.Extras.Builder()
                .addSharedElement(view, name)
                .build();

    }

}
