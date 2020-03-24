package org.ril.hrss.model.reports;

public class EventGraphPoints {

    private String date;
    private Integer postCount;
    private Integer commentCount;
    private Integer reactionsCount;
    private Integer goingCount;
    private Integer notGoingCount;
    private Integer interestedCount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getReactionsCount() {
        return reactionsCount;
    }

    public void setReactionsCount(Integer reactionsCount) {
        this.reactionsCount = reactionsCount;
    }

    public Integer getGoingCount() {
        return goingCount;
    }

    public void setGoingCount(Integer goingCount) {
        this.goingCount = goingCount;
    }

    public Integer getNotGoingCount() {
        return notGoingCount;
    }

    public void setNotGoingCount(Integer notGoingCount) {
        this.notGoingCount = notGoingCount;
    }

    public Integer getInterestedCount() {
        return interestedCount;
    }

    public void setInterestedCount(Integer interestedCount) {
        this.interestedCount = interestedCount;
    }

    @Override
    public String toString() {
        return "PollGraphPoints [ date = " + date + ", postCount = " + postCount + ", commentCount = " + commentCount + ", reactionsCount = " + reactionsCount +
                ", goingCount = " + goingCount + ", notGoingCount = " + notGoingCount + ", interestedCount = " + interestedCount + "]";
    }
}
