package org.ril.hrss.model.reports;

public class EventValueBasedActionsCount {

    private Integer userAction;
    private long total;

    public EventValueBasedActionsCount() {
        super();
    }

    public EventValueBasedActionsCount(Integer userAction, long total) {
        this();
        this.total = total;
        this.userAction = userAction;
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
        return "PollParticipantsCount [ total = " + total + ", userAction = " + userAction + "]";
    }
}
