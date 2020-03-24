package org.ril.hrss.model.reports;

import java.util.Date;

public class QuizParticipantsCount {

    private Date date;
    private long total;

    public QuizParticipantsCount() {
        super();
    }

    public QuizParticipantsCount(Date date, long total) {
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
        return "QuizParticipantsCount [ date = " + date + ", total = " + total + "]";
    }
}
