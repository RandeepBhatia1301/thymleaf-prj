package org.ril.hrss.model.reports;

import java.util.Date;

public class EventUserActionsCount {

    private Date date;
    private Integer userAction;
    private long total;

    public EventUserActionsCount() {
        super();
    }

    public EventUserActionsCount(Date date, Integer userAction, long total) {
        this();
        this.date = date;
        this.total = total;
        this.userAction = userAction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }


    public Integer getUserAction() {
        return userAction;
    }

    public void setUserAction(Integer userAction) {
        this.userAction = userAction;
    }

    @Override
    public String toString() {
        return "PollParticipantsCount [ date = " + date + ", total = " + total + ", userAction = " + userAction + "]";
    }
}
