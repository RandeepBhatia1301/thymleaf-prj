package org.ril.hrss.model;

public class ViewTimeBaseCount {

    private Integer total;

    private String viewedAt;

    public ViewTimeBaseCount() {
        super();
    }

    public ViewTimeBaseCount(Integer total, String viewedAt) {
        this();
        this.total = total;
        this.viewedAt = viewedAt;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(String viewedAt) {
        this.viewedAt = viewedAt;
    }

    @Override
    public String toString() {
        return "ViewTimeBaseCount{" +
                "total=" + total +
                ", viewedAt='" + viewedAt + '\'' +
                '}';
    }
}
