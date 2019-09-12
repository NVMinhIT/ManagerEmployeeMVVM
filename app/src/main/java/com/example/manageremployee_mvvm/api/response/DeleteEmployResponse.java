package com.example.manageremployee_mvvm.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteEmployResponse {
    @SerializedName("success")
    @Expose
    private Success success;

    public DeleteEmployResponse() {

    }
    public DeleteEmployResponse(Success success) {
        this.success = success;
    }

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }
}

