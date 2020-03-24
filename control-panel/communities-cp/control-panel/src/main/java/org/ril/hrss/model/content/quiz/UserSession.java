package org.ril.hrss.model.content.quiz;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "content.user_session")
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(name = "quiz_id")
    private Long quizId;

    private Integer totalQuestion;

    private Integer correctAnswer;

    private Integer takenTime;

    private Double gainMarks;

    @Column(name = "submit_time_date")
    private Date submitTimeDate;

    public UserSession() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Integer getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(Integer totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Integer getTakenTime() {
        return takenTime;
    }

    public void setTakenTime(Integer takenTime) {
        this.takenTime = takenTime;
    }

    public Double getGainMarks() {
        return gainMarks;
    }

    public void setGainMarks(Double gainMarks) {
        this.gainMarks = gainMarks;
    }

    public Date getSubmitTimeDate() {
        return submitTimeDate;
    }

    public void setSubmitTimeDate(Date submitTimeDate) {
        this.submitTimeDate = submitTimeDate;
    }
}
