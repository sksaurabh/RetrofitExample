package com.mmadapps.retrofitexample;

import android.app.*;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SecondActivity extends AppCompatActivity {
    String URL="http://52.172.152.230/AVA_Binary/Booking/SaveUserCabBookingDetails";
    String mResult;
    Button button3;
    private ProgressDialog pdLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        intilizationview();
    }

    private void intilizationview() {
        button3= (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncPnrDetails().execute();
            }
        });
    }

    private class AsyncPnrDetails extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            pdLoading = new android.app.ProgressDialog(SecondActivity.this);
            pdLoading.setMessage("please wait...");
            pdLoading.show();
            pdLoading.setCancelable(false);
            pdLoading.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {


            return callPnrService();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pdLoading.cancel();
            if (pdLoading.isShowing()) {
                pdLoading.dismiss();
            }
            if (mResult == null || mResult.length() == 0) {
                Log.e("null result", "null resu//lt");
            } else {

            }
        }
    }

    private boolean callPnrService() {
        Log.e("3", "3");
        boolean isValid = false;

        try {
            String jsonData = createJson();
            Log.e("jsondata", jsonData);
            if (jsonData != null) {
                Log.e("jsondata", jsonData);
                HttpClient client = new DefaultHttpClient();
                String postURL =URL;
                InputStream inputStream = null;
                Log.e("abc", "" + postURL);
                HttpPost httpPost = new HttpPost(postURL);
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setHeader("Accept", "application/json");
                Log.e("jsondatainside", jsonData);
                StringEntity ser = new StringEntity(jsonData, HTTP.UTF_8);
                httpPost.setEntity(ser);
                HttpResponse httpResponse = client.execute(httpPost);
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                Log.d("statusCode", "" + statusCode);
                if (statusCode != 200) {
                    return false;
                }
                inputStream = httpResponse.getEntity().getContent();
                Log.e("inputStream", "" + inputStream);
                String result = null;
                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }
                if (result == null || result.length() == 0) {
                    Log.e("input null", "input null");
                    isValid = false;
                } else {
                    mResult = parseSaveCabResponse(result);
                    Log.e("mResult", mResult);
                    if (mResult.equalsIgnoreCase("Already Scanned.")) {


                        isValid = true;
                        return isValid;
                    } else {
                        isValid = false;
                        return isValid;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }
    public String parseSaveCabResponse(String result) {
        String AdminResponse = "";
        try {
            JSONObject mJsonObject = new JSONObject(result);
            AdminResponse = mJsonObject.getString("ResponseObject");
            Log.e("cancelResponse", AdminResponse + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AdminResponse;
    }
    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }





    private String createJson() {
        String json = "";
        try {
            JSONObject cabsaveObject = new JSONObject();
            cabsaveObject.put("UserId", "167002");
            cabsaveObject.put("CarBookingId", "C4b1937e2a1667e6f-1484130245909");
            cabsaveObject.put("TripId", "T4b1937e2a1667e6f-1484130222419");
            cabsaveObject.put("PickupPlace", "Bengaluru City Junction Railway Station,M.G. Railway Colony, Majestic, Bengaluru, Karnataka 560023, India");
            cabsaveObject.put("PickupDate", "January 12 2017,02:00");
            cabsaveObject.put("DropPlace", "Bangalore,BLR");
            cabsaveObject.put("DropDate", "January 12 2017,02:00");
            cabsaveObject.put("NoOfAdult", "");

            cabsaveObject.put("NoOfChild", "");
            cabsaveObject.put("CarPreference", "");
            cabsaveObject.put("IsCarPooling", "");
            cabsaveObject.put("CarBookingStatus", "Confirmed");
            cabsaveObject.put("CreatedDate", "");
            cabsaveObject.put("PayementId", "");
            cabsaveObject.put("IsDeparture", true);
            cabsaveObject.put("EstimationTime", "");
            cabsaveObject.put("IsInterCity", "");
            cabsaveObject.put("IsIntraCity", "");

            cabsaveObject.put("IsIntraCity", "");
            cabsaveObject.put("PickupLatitude", "12.978348400000002");
            cabsaveObject.put("PickupLongitude", "77.5683983");
            cabsaveObject.put("DropLatitude", "13.200771");
            cabsaveObject.put("DropLongitude", "77.710228");
            json = cabsaveObject.toString();
            Log.e("json", "" + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

}
