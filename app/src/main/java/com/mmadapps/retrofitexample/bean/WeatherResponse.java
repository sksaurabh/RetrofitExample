
package com.mmadapps.retrofitexample.bean;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class WeatherResponse {

    @SerializedName("ResponseObject")
    @Expose
    private ResponseObject responseObject;
    @SerializedName("IsError")
    @Expose
    private Boolean isError;
    @SerializedName("ExceptionObject")
    @Expose
    private Object exceptionObject;

    /**
     * 
     * @return
     *     The responseObject
     */
    public ResponseObject getResponseObject() {
        return responseObject;
    }

    /**
     * 
     * @param responseObject
     *     The ResponseObject
     */
    public void setResponseObject(ResponseObject responseObject) {
        this.responseObject = responseObject;
    }

    /**
     * 
     * @return
     *     The isError
     */
    public Boolean getIsError() {
        return isError;
    }

    /**
     * 
     * @param isError
     *     The IsError
     */
    public void setIsError(Boolean isError) {
        this.isError = isError;
    }

    /**
     * 
     * @return
     *     The exceptionObject
     */
    public Object getExceptionObject() {
        return exceptionObject;
    }

    /**
     * 
     * @param exceptionObject
     *     The ExceptionObject
     */
    public void setExceptionObject(Object exceptionObject) {
        this.exceptionObject = exceptionObject;
    }

}
