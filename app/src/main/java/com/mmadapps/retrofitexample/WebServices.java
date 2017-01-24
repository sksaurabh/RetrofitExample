package com.mmadapps.retrofitexample;

import android.app.Activity;
import android.app.IntentService;

import android.content.Context;
import android.util.Log;

import com.mmadapps.retrofitexample.loginbeans.LoginResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by saurabh.kumar on 11/25/2016.
 */

public class WebServices<T> {
    private T t;
    Call<T> call = null;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
    ApiType apiTypeVariable;
    Context context;
    OnResponseListner<T> onResponseListner;
    android.app.ProgressDialog pdLoading;
    private static OkHttpClient.Builder builder;
    public enum ApiType {
        GetWeatherInfoByGoeidOrCityName,TravaLoginUser
    }
    public static String TravaService = "http://52.172.152.230/AVA_Binary/";

    public WebServices(OnResponseListner<T> onResponseListner) {
        this.onResponseListner = onResponseListner;
        if (onResponseListner instanceof Activity) {
            this.context = (Context) onResponseListner;
        } else if (onResponseListner instanceof IntentService) {
            this.context = (Context) onResponseListner;
        } else if (onResponseListner instanceof android.app.DialogFragment) {
            android.app.DialogFragment dialogFragment = (android.app.DialogFragment) onResponseListner;
            this.context = dialogFragment.getActivity();
        } else {
            android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) onResponseListner;
            this.context = fragment.getActivity();
        }
        builder = getHttpClient();
    }

    public OkHttpClient.Builder getHttpClient() {
        if (builder == null) {
            HttpLoggingInterceptor weather = new HttpLoggingInterceptor();
            weather.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(weather);
            return httpClient;
        }
        return builder;
    }

    //get details of the roles
    public void getDtailsforweather(String api, ApiType apiTypes, String city) {
        apiTypeVariable = apiTypes;
        pdLoading = new com.mmadapps.retrofitexample.ProgressDialog().getInstance(context);
        if (pdLoading.isShowing()) {
            pdLoading.cancel();
        }
        pdLoading.show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
        GitApi gi = retrofit.create(GitApi.class);

        call = (Call<T>) gi.getweatherDetails("","GetWeatherInfoByGoeidOrCityName/"+city);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                t = response.body();
                if (pdLoading.isShowing())
                    pdLoading.cancel();

                onResponseListner.onResponse(t, apiTypeVariable, true);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.e(apiTypeVariable.toString(), t.getMessage() + ".");
                if (pdLoading.isShowing())
                    pdLoading.cancel();
                onResponseListner.onResponse(null, apiTypeVariable, false);
            }

        });
    }

    public void loginUser(String api, ApiType apiTypes, LoginResponse mUserProfile) {
        apiTypeVariable = apiTypes;
        pdLoading = ProgressDialog.getInstance(context);
        try{
            if (pdLoading.isShowing())
                pdLoading.cancel();
        }catch (Exception e){
            e.printStackTrace();
        }
        pdLoading.show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
        GitApi gi = retrofit.create(GitApi.class);
        call = (Call<T>) gi.TravaLoginUser(mUserProfile);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                t = (T) response.body();
                try{
                    if (pdLoading.isShowing())
                        pdLoading.cancel();
                }catch (Exception e){
                    e.printStackTrace();
                }
                onResponseListner.onResponse(t, apiTypeVariable, true);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.e(apiTypeVariable.toString(), t.getMessage() + ".");
                try{
                    if (pdLoading.isShowing())
                        pdLoading.cancel();
                }catch (Exception e){
                    e.printStackTrace();
                }
                onResponseListner.onResponse(null, apiTypeVariable, false);
            }
        });
    }

}
