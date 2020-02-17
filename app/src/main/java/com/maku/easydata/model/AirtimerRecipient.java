
package com.maku.easydata.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirtimerRecipient {

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
