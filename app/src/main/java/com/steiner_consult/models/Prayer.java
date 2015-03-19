package com.steiner_consult.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Philipp on 29.01.15.
 */
public class Prayer {

    private int id;
    private String title;
    private String text;
    private Date creationDate;
    private Date updateDate;
    private boolean status;
    private String testimony;
    private long creator;
    private boolean agreed;
    private boolean privacy;
    private ArrayList<AppUser> sharedwith = new ArrayList<>();
    private ArrayList<AppUser> agreedwith = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTestimony() {
        return testimony;
    }

    public void setTestimony(String testimony) {
        this.testimony = testimony;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public boolean isAgreed() {
        return agreed;
    }

    public void setAgreed(boolean agreed) {
        this.agreed = agreed;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public ArrayList<AppUser> getSharedwith() {
        return sharedwith;
    }

    public void setSharedwith(ArrayList<AppUser> sharedwith) {
        this.sharedwith = sharedwith;
    }

    public ArrayList<AppUser> getAgreedwith() {
        return agreedwith;
    }

    public void setAgreedwith(ArrayList<AppUser> agreedwith) {
        this.agreedwith = agreedwith;
    }
}
