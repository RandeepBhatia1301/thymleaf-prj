package org.ril.hrss.model.reports;

public class QuizGraphPoints {

    private String date;
    private Integer postCount;
    private Integer commentCount;
    private Integer participantCount;

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

    public Integer getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(Integer participantCount) {
        this.participantCount = participantCount;
    }

    @Override
    public String toString() {
        return "PollGraphPoints [ date = " + date + ", postCount = " + postCount + ", commentCount = " + commentCount + ", participantCount = " + participantCount + "]";
    }
}
