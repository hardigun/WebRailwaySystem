package com.tsystems.webrailwaysystem.entities;

import com.tsystems.webrailwaysystem.enums.EMessageType;

/**
 * Date: 20.10.13
 */
public class Message {

    private String text;
    private EMessageType type;

    public Message(String text, EMessageType type) {
        this.setText(text);
        this.setType(type);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EMessageType getType() {
        return type;
    }

    public void setType(EMessageType type) {
        this.type = type;
    }

}
