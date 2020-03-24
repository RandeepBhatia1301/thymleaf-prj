package org.ril.hrss.model.gamification;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "organization.level_master")
public class GamificationLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "level")
    private Integer level;

    @Column(name = "point")
    private Integer point;

    @Column(name = "blog")
    private Integer blog;

    @Column(name = "event")
    private Integer event;

    @Column(name = "poll")
    private Integer poll;

    @Column(name = "quiz")
    private Integer quiz;

    @Column(name = "discussion")
    private Integer discussion;

    @Column(name = "miscellaneous")
    private Integer miscellaneous;

    @Column(name = "is_stage")
    private Integer isStage;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    public Integer getIsStage() {
        return isStage;
    }

    public void setIsStage(Integer isStage) {
        this.isStage = isStage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getBlog() {
        return blog;
    }

    public void setBlog(Integer blog) {
        this.blog = blog;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public Integer getPoll() {
        return poll;
    }

    public void setPoll(Integer poll) {
        this.poll = poll;
    }

    public Integer getQuiz() {
        return quiz;
    }

    public void setQuiz(Integer quiz) {
        this.quiz = quiz;
    }

    public Integer getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Integer discussion) {
        this.discussion = discussion;
    }

    public Integer getMiscellaneous() {
        return miscellaneous;
    }

    public void setMiscellaneous(Integer miscellaneous) {
        this.miscellaneous = miscellaneous;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
