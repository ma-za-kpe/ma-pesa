
package com.maku.easydata.models.serverresponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SosResponse2 {

    @SerializedName("SosResult")
    @Expose
    private List<SosResult> sosResult = null;

    public List<SosResult> getSosResult() {
        return sosResult;
    }

    public void setSosResult(List<SosResult> sosResult) {
        this.sosResult = sosResult;
    }

}
