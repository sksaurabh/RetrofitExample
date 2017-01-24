
package com.mmadapps.retrofitexample.loginbeans;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginResponse {

    @SerializedName("EmailId")
    @Expose
    private String emailId;
    @SerializedName("PlatFormDeviceType")
    @Expose
    private String platFormDeviceType;
    @SerializedName("ProviderId")
    @Expose
    private String providerId;
    @SerializedName("ProviderType")
    @Expose
    private String providerType;
    @SerializedName("UserPassword")
    @Expose
    private String userPassword;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LoginResponse() {
    }

    /**
     * 
     * @param providerType
     * @param emailId
     * @param platFormDeviceType
     * @param userPassword
     * @param providerId
     */
    public LoginResponse(String emailId, String platFormDeviceType, String providerId, String providerType, String userPassword) {
        this.emailId = emailId;
        this.platFormDeviceType = platFormDeviceType;
        this.providerId = providerId;
        this.providerType = providerType;
        this.userPassword = userPassword;
    }

    /**
     * 
     * @return
     *     The emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * 
     * @param emailId
     *     The EmailId
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * 
     * @return
     *     The platFormDeviceType
     */
    public String getPlatFormDeviceType() {
        return platFormDeviceType;
    }

    /**
     * 
     * @param platFormDeviceType
     *     The PlatFormDeviceType
     */
    public void setPlatFormDeviceType(String platFormDeviceType) {
        this.platFormDeviceType = platFormDeviceType;
    }

    /**
     * 
     * @return
     *     The providerId
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * 
     * @param providerId
     *     The ProviderId
     */
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    /**
     * 
     * @return
     *     The providerType
     */
    public String getProviderType() {
        return providerType;
    }

    /**
     * 
     * @param providerType
     *     The ProviderType
     */
    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    /**
     * 
     * @return
     *     The userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * 
     * @param userPassword
     *     The UserPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
