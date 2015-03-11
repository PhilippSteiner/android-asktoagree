package com.steiner_consult.models.responses;

import java.util.Date;

/**
 * Created by Philipp on 16.12.14.
 */
public class RegisterResponse extends BaseResponse {

    private Date creationDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
