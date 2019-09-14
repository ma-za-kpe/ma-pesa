
package com.maku.easydata.models.serverrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SosRequest {

    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("nickName")
    @Expose
    private String nickName;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("network")
    @Expose
    private String network;
    @SerializedName("date")
    @Expose
    private String date;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
