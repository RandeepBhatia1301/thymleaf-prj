package org.ril.hrss.model;

public class ContentTimeBaseCount {

    private Integer total;

    private String date;

    public ContentTimeBaseCount() {
        super();
    }

    public ContentTimeBaseCount(Integer total, String date) {
        this();
        this.total = total;
        this.date = date;
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
        return "ContentTimeBaseCount{" +
                "total=" + total +
                ", date='" + date + '\'' +
                '}';
    }
}
