package org.ril.hrss.model.reports;

import java.util.Date;

public class PollParticipantsCount {

    private Date date;
    private long total;

    public PollParticipantsCount() {
        super();
    }

    public PollParticipantsCount(Date date, long total) {
        this();
        this.date = date;
        this.total = total;
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

    @Override
    public String toString() {
        return "PollParticipantsCount [ date = " + date + ", total = " + total + "]";
    }
}
