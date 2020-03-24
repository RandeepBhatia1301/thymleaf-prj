package org.ril.hrss.model;

public class CommentTimeBaseCount {

    private Integer total;

    private String commentedAt;

    public CommentTimeBaseCount() {
        super();
    }

    public CommentTimeBaseCount(Integer total, String commentedAt) {
        this();
        this.total = total;
        this.commentedAt = commentedAt;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(String commentedAt) {
        this.commentedAt = commentedAt;
    }

    @Override
    public String toString() {
        return "CommentTimeBaseCount{" +
                "total=" + total +
                ", commentedAt='" + commentedAt + '\'' +
                '}';
    }
}
