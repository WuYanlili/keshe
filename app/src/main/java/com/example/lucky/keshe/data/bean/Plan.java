package com.example.lucky.keshe.data.bean;

import java.util.Date;

/**
 * Created by lucky on 2018/9/7.
 */

public class Plan {
    private int id;
    private String name;
    private String content;
    private Date start_time;
    private Date during_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getDuring_time() {
        return during_time;
    }

    public void setDuring_time(Date during_time) {
        this.during_time = during_time;
    }
}
