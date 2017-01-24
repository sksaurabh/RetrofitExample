package com.mmadapps.retrofitexample;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.mmadapps.retrofitexample.bean.WeatherResponse;
import com.mmadapps.retrofitexample.loginbeans.LoginResponse;

public class WeatherActivity extends Activity implements OnResponseListner {
    WeatherResponse mWeatherResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        String Input="Begusarai";
        WebServices<WeatherResponse> webServicess = new WebServices<>(WeatherActivity.this);
        webServicess.getDtailsforweather(WebServices.TravaService,webServicess.apiTypeVariable,Input);
        Button button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebServices<LoginResponse> webServicess = new WebServices<>(WeatherActivity.this);
                webServicess.loginUser(WebServices.TravaService, WebServices.ApiType.TravaLoginUser, new LoginResponse("saurabh11@mmsdapps.com","ANDROID", "", "1", "Hai@123"));
            }
        });
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces) {
        if (URL == WebServices.ApiType.GetWeatherInfoByGoeidOrCityName) {
            if (isSucces) {
                mWeatherResponse = (WeatherResponse) response;
                String str = new Gson().toJson(mWeatherResponse);
                Log.e("ganga", new Gson().toJson(mWeatherResponse));
            }
        }
    }

}
