
package com.maku.easydata.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendAirtime {

    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("numSent")
    @Expose
    private Integer numSent;
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

    public Integer getNumSent() {
        return numSent;
    }

    public void setNumSent(Integer numSent) {
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

}
