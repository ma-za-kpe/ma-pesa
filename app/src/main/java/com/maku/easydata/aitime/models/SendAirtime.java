
package com.maku.easydata.aitime.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Response;

public class SendAirtime {

    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("numSent")
    @Expose
    private int numSent;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("totalDiscount")
    @Expose
    private String totalDiscount;
    @SerializedName("responses")
    @Expose
    private List<Response> responses = null;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getNumSent() {
        return numSent;
    }

    public void setNumSent(int numSent) {
        this.numSent = numSent;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    @Override
    public String toString() {
        return "SendAirtime{" +
                "errorMessage='" + errorMessage + '\'' +
                ", numSent=" + numSent +
                ", totalAmount='" + totalAmount + '\'' +
                ", totalDiscount='" + totalDiscount + '\'' +
                ", responses=" + responses +
                '}';
    }
}
