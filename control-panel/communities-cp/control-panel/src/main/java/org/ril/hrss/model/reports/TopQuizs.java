package org.ril.hrss.model.reports;

public class TopQuizs {

    private Long quizId;
    private Long count;
    private String quesText;

    public TopQuizs() {
        super();
    }

    public TopQuizs(Long quizId, Long count) {
        this();
        this.quizId = quizId;
        this.count = count;
    }

    public TopQuizs(Long pollId, Long count, String quesText) {
        this(pollId, count);
        this.quesText = quesText;
    }

    public String getQuesText() {
        return quesText;
    }

    public void setQuesText(String quesText) {
        this.quesText = quesText;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TopQuizs [ quizId = " + quizId + ", quesText = " + quesText + ", count = " + count + "]";
    }
}
