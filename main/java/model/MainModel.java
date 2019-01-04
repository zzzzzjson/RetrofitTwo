package model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Map;

import api.RetrofitUtils;
import callback.MyCallBack;

/**
 * 作者：穆佳琪
 * 时间：2019/1/3 9:17.
 */

public class MainModel implements IModel {
    @Override
    public void getData(String url, Map<String, String> param, final Class clazz, final MyCallBack myCallBack) {
//        RetrofitUtils.getInstance().get(url,param).setHttpListener(new RetrofitUtils.HttpListener() {
//            @Override
//            public void onSuccess(String jsonStr) {
//                Object o = new Gson().fromJson(jsonStr, clazz);
//                Log.d("Mainmodel", o.toString());
//                myCallBack.onSuccess(o);
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//        });
        RetrofitUtils.getInstance().get(url, param, new RetrofitUtils.HttpListener() {
            @Override
            public void onSuccess(String jsonStr) {
                Object o = new Gson().fromJson(jsonStr, clazz);
                Log.d("Mainmodel", o.toString());
                myCallBack.onSuccess(o);
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
