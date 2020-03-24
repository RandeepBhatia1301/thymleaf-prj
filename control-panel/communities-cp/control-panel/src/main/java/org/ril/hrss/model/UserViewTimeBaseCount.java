package org.ril.hrss.model;

public class UserViewTimeBaseCount {

    private Integer total;

    private String viewedAt;

    public UserViewTimeBaseCount() {
        super();
    }

    public UserViewTimeBaseCount(Integer total, String viewedAt) {
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
        return "UserViewTimeBaseCount{" +
                "total=" + total +
                ", viewedAt='" + viewedAt + '\'' +
                '}';
    }
}
