package com.steiner_consult.models.responses;

/**
 * Created by Philipp on 05.02.15.
 */
public class BaseResponse {

    protected int id;
    protected String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
