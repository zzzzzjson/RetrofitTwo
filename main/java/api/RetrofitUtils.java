package api;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import contactss.Contacts;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：穆佳琪
 * 时间：2019/1/3 9:06.
 */

public class RetrofitUtils {
    private ApiService myApiService1;

    private RetrofitUtils(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Contacts.BASE_URL)
                .client(okHttpClient)
                .build();
        myApiService1 = retrofit.create(ApiService.class);
    }
    public static RetrofitUtils getInstance() {
        return RetroHolder.retro;
    }

    private static class RetroHolder {
        private static final RetrofitUtils retro = new RetrofitUtils();
    }
    //封装Get方式  这里面采用构造者模式  就是调用这个方法有返回自己本身这个对象
    public RetrofitUtils get(String url, Map<String, String> map) {
        //这个订阅事件（处理网络请求）放生那个线程
        //Schedulers 线程调度器
        myApiService1.get(url, map).subscribeOn(Schedulers.io())//io就是子线程
                //在主线程调用
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return RetrofitUtils.getInstance();
    }

    public void get(String url,Map<String,String> map,final HttpListener listener){
        Observer observer = new Observer<ResponseBody>() {

            @Override
            public void onCompleted() {
                Log.e("onCompleted","onCompleted");
            }
            //网络处理失败
            @Override
            public void onError(Throwable e) {
                Log.e("onError","onError"+e.getMessage());
                if (listener != null) {
                    listener.onError(e.getMessage());
                }
            }
            //网络处理成功
            @Override
            public void onNext(ResponseBody responseBody) {
                Log.d("onNext","onNext");
                if (listener != null) {
                    try {
                        listener.onSuccess(responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        myApiService1.get(url, map).subscribeOn(Schedulers.io())//io就是子线程
                //在主线程调用
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 表单post请求
     */
    /*public RetrofitUtils post(String url, Map<String, Integer> map) {
        if (map == null) {
            map = new HashMap<>();
        }

        myApiService1.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return RetrofitUtils.getInstance();
    }*/
    //重写一个观察者对象
    private Observer observer = new Observer<ResponseBody>() {

        @Override
        public void onCompleted() {
            Log.e("onCompleted","onCompleted");
        }
        //网络处理失败
        @Override
        public void onError(Throwable e) {
            Log.e("onError","onError"+e.getMessage());
            if (httpListener != null) {
                httpListener.onError(e.getMessage());
            }
        }
        //网络处理成功
        @Override
        public void onNext(ResponseBody responseBody) {
            Log.d("onNext","onNext");
            if (httpListener != null) {
                try {
                    httpListener.onSuccess(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    public interface HttpListener {
        void onSuccess(String jsonStr);

        void onError(String error);
    }

    private HttpListener httpListener;

    public void setHttpListener(HttpListener listener) {
        this.httpListener = listener;
    }

}
