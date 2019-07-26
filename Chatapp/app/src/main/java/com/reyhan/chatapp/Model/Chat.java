package com.reyhan.chatapp.Model;

import java.util.HashMap;
import java.util.Map;

public class Chat {

    private String sender;
    private String receiver;
    private String message;
    private String type;
    private boolean isseen;
    private long timestamp;

    public Chat(String sender, String receiver, String message, boolean isseen, long timestamp, String type) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isseen = isseen;
        this.timestamp = timestamp;
        this.type = type;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }
}
