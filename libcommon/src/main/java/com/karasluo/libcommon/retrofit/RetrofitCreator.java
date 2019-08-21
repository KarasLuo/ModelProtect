package com.karasluo.libcommon.retrofit;

import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import org.json.JSONException;

import java.io.File;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hongliang Luo on 2018/8/9.
 **/
public class RetrofitCreator {
    final static private String TAG="RetrofitCreator";

//    static private RetrofitCreator instance;
    private static final long DEFAULT_TIMEOUT=6000;

    private RetrofitCreator(){

    }

//    public static RetrofitCreator getInstance(){
//        if(instance==null){
//            synchronized (RetrofitCreator.class){
//                if(instance==null){
//                    instance=new RetrofitCreator();
//                }
//            }
//        }
//        Log.i(TAG,"instance="+instance);
//        return instance;
//    }

    /**
     * 配置okHttp
     * @return builder
     */
    private static OkHttpClient.Builder getOkHttpClientBuilder(){
        final HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        printHttpInterceptorMsg(message);
                    }
                });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File cacheFile=new File("","Cache");
        Cache cache=new Cache(cacheFile,1024*1024*20);//设置20M缓存
        return new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT,TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .cache(cache);
    }

    /**
     * 打印http拦截的信息
     * @param message msg
     */
    private static void printHttpInterceptorMsg(String message){
        //打印拦截http信息
        try{
            Log.w(TAG,
                    "-----------HttpLoggingInterceptor-----------");
            String body = URLDecoder.decode(message, "utf-8");
            int logPartCount=(body.length()/3000)+1;
            int endIndex;
            for (int i=0;i<logPartCount;i++){
                if(i==logPartCount-1){
                    endIndex=body.length();
                }else {
                    endIndex=(i+1)*3000;
                }
                Log.w(TAG,body.substring(i*3000,endIndex));
            }
            Log.w(TAG,
                    "-------------------end---------------------");
        }catch (Exception e){
            Log.e(TAG,"解析拦截器信息出错");
            String eString=e.getMessage();
            int logPartCount=(eString.length()/3000)+1;
            int endIndex;
            for (int i=0;i<logPartCount;i++){
                if(i==logPartCount-1){
                    endIndex=eString.length();
                }else {
                    endIndex=(i+1)*3000;
                }
                Log.e(TAG,eString.substring(i*3000,endIndex));
            }
        }
    }

    /**
     * 配置retrofit
     * @return Retrofit
     */
    public static Retrofit createRetrofit(String baseUrl) {
        OkHttpClient okHttpClient = getOkHttpClientBuilder().build();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 异常处理
     * 一般由服务器返回异常，这里处理无法连接服务器和服务器没有返回的异常
     * @param e 抛出的异常
     */
    public static void processNetError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {     //   HTTP错误
            Log.e(TAG,"HTTP错误");
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException
                ||e instanceof NoRouteToHostException
                ||e instanceof SocketException) {   //   连接错误
            Log.e(TAG,"连接错误");
        } else if (e instanceof SocketTimeoutException
                ||e instanceof TimeoutException
                ||e instanceof InterruptedIOException) {   //  连接超时
            Log.e(TAG,"连接超时");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                ||e instanceof MalformedJsonException) {   //  解析错误
            Log.e(TAG,"解析错误");
        }else {
            Log.e(TAG,"其它错误");
        }
    }
}

