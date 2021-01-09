package com.project.carrepairedgeservice.model;

import java.util.Date;

public class WorkingHours {
    private int id;
    private Date startTimestamp;
    private Date stopTimestamp;

    public WorkingHours() {
    }

    public WorkingHours(Date startTimestamp, Date stopTimestamp) {
        this.startTimestamp = startTimestamp;
        this.stopTimestamp = stopTimestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getStopTimestamp() {
        return stopTimestamp;
    }

    public void setStopTimestamp(Date stopTimestamp) {
        this.stopTimestamp = stopTimestamp;
    }
}
