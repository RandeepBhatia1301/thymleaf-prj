package org.ril.hrss.model.reports;

public class TopPolls {

    private Long pollId;
    private Long count;
    private String quesText;

    public TopPolls() {
        super();
    }

    public TopPolls(Long pollId, Long count) {
        this();
        this.pollId = pollId;
        this.count = count;
    }

    public TopPolls(Long pollId, Long count, String quesText) {
        this(pollId, count);
        this.quesText = quesText;
    }

    public String getQuesText() {
        return quesText;
    }

    public void setQuesText(String quesText) {
        this.quesText = quesText;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TopPolls [ pollId = " + pollId + ", quesText = " + quesText + ", count = " + count + "]";
    }
}
