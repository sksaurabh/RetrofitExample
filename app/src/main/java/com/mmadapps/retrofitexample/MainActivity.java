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
import android.widget.Toast;

import com.mmadapps.retrofitexample.bean.GetCabBookindDetails;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button2;
    String URL = "http://52.172.152.230/AVA_Binary/Booking/GetUserAllCabBookingDetails/?userId=166858";
    private ProgressDialog pdLoading;
    String[] mResult;
    List<GetCabBookindDetails> myDealsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intilizationView();

    }

    private void intilizationView() {
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new AsyncPnrDetails().execute();
            }
        });
    }




    private class AsyncPnrDetails extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            pdLoading = new android.app.ProgressDialog(MainActivity.this);
            pdLoading.setMessage("please wait...");
            pdLoading.show();
            pdLoading.setCancelable(false);
            pdLoading.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {


            return  callService();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pdLoading.cancel();
            if (pdLoading.isShowing()) {
                pdLoading.dismiss();
            }
          if(result==null){

            } else {



            }
        }
    }





    public String getObjectvalue(JSONObject listObj, String keyOfObject) {
        try {
            try {
                String mKeyValue = listObj.getString(keyOfObject).toString();
                if (mKeyValue.equals("null")) {
                } else {
                    return mKeyValue;
                }
            } catch (NullPointerException nullExp) {
            }
        } catch (JSONException e) {
        }
        return "";
    }

    private Boolean callService() {
        try {
            Log.e("resultchannel===>", "Service called");
            String resultOutparam = getResponseFromService(URL);
            Log.e("GetConnectorListback", resultOutparam);
            if (resultOutparam == null && resultOutparam.length() == 0) {
                return false;
            } else {
                //isValid=false;
                Log.e("resultchannel===>", resultOutparam);
               myDealsList= parseUserCabsList(resultOutparam);

                if (myDealsList == null || myDealsList.size() == 0) {
                    return false;
                } else {
                    Log.e("resultchanne2===>", "Service called");
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e("saurabh", e.getMessage());
            return false;
        }
    }

    public List<GetCabBookindDetails> parseUserCabsList(String mOutputParam) {
        List<GetCabBookindDetails> mUserCabsList = null;
        try {
            JSONObject userAllCabs = new JSONObject(mOutputParam);
            String IsError = userAllCabs.getString("IsError");
            if (IsError.equalsIgnoreCase("true")) {
            } else {
                mUserCabsList = new ArrayList<GetCabBookindDetails>();
                if (userAllCabs == null || userAllCabs.length() == 0) {
                } else {
                    JSONArray responseArray = userAllCabs.getJSONArray("ResponseObject");
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject cabObject = responseArray.getJSONObject(i);
                        GetCabBookindDetails mCabDetail = new GetCabBookindDetails();
                        mCabDetail.setmTripId(getObjectvalue(cabObject, "TripId"));
                        mCabDetail.setmCabBookingId(getObjectvalue(cabObject, "CabBookingId"));
                        mCabDetail.setmPickupLocation(getObjectvalue(cabObject, "PickupPlace"));
                        mCabDetail.setmDropLocation(getObjectvalue(cabObject, "DropPlace"));
                        mCabDetail.setmIsDeparture(getObjectvalue(cabObject, "IsDeparture"));
                        mCabDetail.setmDateTime(getObjectvalue(cabObject, "PickupDate"));
                        String IsCancelled = cabObject.getString("IsCancelled");
                        if (IsCancelled.equalsIgnoreCase("false")) {
                            mCabDetail.setmIsActive("true");
                        } else {
                            mCabDetail.setmIsActive("False");
                        }
                        mCabDetail.setmPickUpLatitude(getObjectvalue(cabObject, "PickupLatitude"));
                        mCabDetail.setmPickUpLongitude(getObjectvalue(cabObject, "PickupLongitude"));
                        mCabDetail.setmDropLatitude(getObjectvalue(cabObject, "DropLatitude"));
                        mCabDetail.setmDropLongitude(getObjectvalue(cabObject, "DropLongitude"));
                        mCabDetail.setmEstimatedTime(getObjectvalue(cabObject, "EstimationTime"));
                        mCabDetail.setmIsInterCity(getObjectvalue(cabObject, "IsInterCity"));
                        mCabDetail.setmIsIntraCity(getObjectvalue(cabObject, "IsIntraCity"));

                        mUserCabsList.add(mCabDetail);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mUserCabsList;
    }


    public String getResponseFromService(String url) {
        String finalResult = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        if (httpclient ==null) {
        } else {
            HttpResponse response;
            try {
                if (httpget == null) {
                } else {
                    response = httpclient.execute(httpget);
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 404) {
                        return null;
                    }
                    HttpEntity entity = response.getEntity();
                    StringBuffer out = new StringBuffer();
                    byte[] b = EntityUtils.toByteArray(entity);
                    out.append(new String(b, 0, b.length));
                    finalResult = out.toString();
                }

            } catch (ClientProtocolException e) {
                Log.e("REST", "There was a protocol based error", e);
            } catch (IOException e) {
                Log.e("REST", "There was an IO Stream related error", e);
            }
        }

        return finalResult;
    }

}
