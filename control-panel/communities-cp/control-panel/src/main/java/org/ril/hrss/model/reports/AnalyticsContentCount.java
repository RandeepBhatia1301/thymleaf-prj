package org.ril.hrss.model.reports;

public class AnalyticsContentCount {

    private String date;
    private Integer total;

    public AnalyticsContentCount() {
        super();
    }

    public AnalyticsContentCount(String date, Integer total) {
        this();
        this.date = date;
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AnalyticsContentCount [ date = " + date + ", total = " + total + "]";
    }
}
