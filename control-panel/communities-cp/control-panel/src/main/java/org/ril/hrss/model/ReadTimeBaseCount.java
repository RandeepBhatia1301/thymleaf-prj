package org.ril.hrss.model;

public class ReadTimeBaseCount {

    private Integer total;

    private String readAt;

    public ReadTimeBaseCount() {
        super();
    }

    public ReadTimeBaseCount(Integer total, String readAt) {
        this();
        this.total = total;
        this.readAt = readAt;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getReadAt() {
        return readAt;
    }

    public void setReadAt(String readAt) {
        this.readAt = readAt;
    }

    @Override
    public String toString() {
        return "ReadTimeBaseCount{" +
                "total=" + total +
                ", readAt='" + readAt + '\'' +
                '}';
    }
}
