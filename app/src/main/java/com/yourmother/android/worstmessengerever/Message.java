package com.yourmother.android.worstmessengerever;

public class Message {

    private String mUserUid;
    private String mUsername;
    private String mText;
    private long mDate;

    public Message(String userUid, String username, String text, long date) {
        mUserUid = userUid;
        mUsername = username;
        mText = text;
        mDate = date;
    }

    public Message() {
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public String getUserUid() {
        return mUserUid;
    }

    public void setUserUid(String userUid) {
        mUserUid = userUid;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }
}