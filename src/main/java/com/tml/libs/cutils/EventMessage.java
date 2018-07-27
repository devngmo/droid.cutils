package com.tml.libs.cutils;

public class EventMessage{
    public String id;
    public String status;

    public EventMessage(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public EventMessage(String id) {
        this.id = id;
        this.status = "ok";
    }
}
