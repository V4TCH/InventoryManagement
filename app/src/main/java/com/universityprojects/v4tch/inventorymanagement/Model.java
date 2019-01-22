package com.universityprojects.v4tch.inventorymanagement;

public class Model {
    private String mId, mTitle, mDesc;

    public Model() {

    }

    Model(String mId, String mTitle, String mDesc) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDesc = mDesc;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId){
        this.mId = mId;
    }

    String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }
}
