package com.reyhan.chatapp.Model;

import java.util.Map;

public class Message {
    String message;
    String username;
    String key;
    //Map<String, String> timestamp;
    Long timestamp;

    public Message() {
    }

    //public Message(String message, String username, Map<String,String> timestamp) {
    public Message(String message, String username, Long timestamp){
        this.message = message;
        this.username = username;
        //this.key = key;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    //public Map<String, String> getTimestamp(){
    public Long getTimestamp(){
        return timestamp;
    }

    //public void setTimestamp(Map<String,String> timestamp){
    public void setTimestamp(Long timestamp){
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{"+
                "message='"+ message + '\''+
                ", username='" + username + '\''+
                ", key='" + key + '\'' +
                '}';
    }
}
