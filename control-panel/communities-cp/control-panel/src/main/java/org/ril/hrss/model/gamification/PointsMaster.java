package org.ril.hrss.model.gamification;

import javax.persistence.*;

@Entity
@Table(name = "organization.points_master")
public class PointsMaster {

    @Transient
    String categoryName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "activity")
    private String activity;
    @Column(name = "c1")
    private Integer c1;
    @Column(name = "c2")
    private Integer c2;
    @Column(name = "c3")
    private Integer c3;
    @Column(name = "c4")
    private Integer c4;
    @Column(name = "c5")
    private Integer c5;
    @Column(name = "score")
    private Integer score;
    @Column(name = "points")
    private Integer points;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "code")
    private String code;
    @Column(name = "activity_message")
    private String activityMessage;
    @Column(name = "base_multiplier")
    private Integer baseMultiplier;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Integer getC1() {
        return c1;
    }

    public void setC1(Integer c1) {
        this.c1 = c1;
    }

    public Integer getC2() {
        return c2;
    }

    public void setC2(Integer c2) {
        this.c2 = c2;
    }

    public Integer getC3() {
        return c3;
    }

    public void setC3(Integer c3) {
        this.c3 = c3;
    }

    public Integer getC4() {
        return c4;
    }

    public void setC4(Integer c4) {
        this.c4 = c4;
    }

    public Integer getC5() {
        return c5;
    }

    public void setC5(Integer c5) {
        this.c5 = c5;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getActivityMessage() {
        return activityMessage;
    }

    public void setActivityMessage(String activityMessage) {
        this.activityMessage = activityMessage;
    }

    public Integer getBaseMultiplier() {
        return baseMultiplier;
    }

    public void setBaseMultiplier(Integer baseMultiplier) {
        this.baseMultiplier = baseMultiplier;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
