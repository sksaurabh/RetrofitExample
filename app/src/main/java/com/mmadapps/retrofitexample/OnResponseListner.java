package com.mmadapps.retrofitexample;


public interface OnResponseListner<T> {
    void onResponse(T response, WebServices.ApiType URL, boolean isSucces);
}
