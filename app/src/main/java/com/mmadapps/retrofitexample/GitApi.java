package com.mmadapps.retrofitexample;

import com.mmadapps.retrofitexample.bean.WeatherResponse;
import com.mmadapps.retrofitexample.loginbeans.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by saurabh.kumar on 11/25/2016.
 */

public interface GitApi {

    @GET("GetWeatherInfoByGoeidOrCityName")
    Call<WeatherResponse> getweatherDetails(@Query("goeid") String goeid,@Query("cityname") String cityname);

    @POST("User/TravaLoginUser")
    Call<LoginResponse> TravaLoginUser(@Body LoginResponse json);
}
