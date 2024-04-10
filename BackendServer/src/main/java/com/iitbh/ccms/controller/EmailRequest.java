package com.iitbh.ccms.controller;

public class EmailRequest {
    private String to;
    private String subject;
    private String text;

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
