package org.ril.hrss.model.gamification;

import javax.persistence.*;

@Entity
@Table(name = "organization.badge_master")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*  @ManyToOne
      @JoinColumn(name = "org_id")
      private Org org;
  */
    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "level")
    private String level;

    @Column(name = "is_automatic")
    private Integer isAutomatic;

    @Column(name = "is_peer_to_peer")
    private Integer isPeerToPeer;

    @Column(name = "is_admin_to_peer")
    private Integer isAdminToPeer;

    @Column(name = "popup_message")
    private String popupMsg;

    @Column(name = "dashboard_badge")
    private String dashboardBadge;

    @Column(name = "dashboard_next_level")
    private String dashboardNextLevel;

    @Column(name = "dashboard_on_completion")
    private String dashboardOnCompletion;

    @Transient
    @Embedded
    private Level l;

    @Column(name = "code")
    private String code;

    @Column(name = "journey_message")
    private String journeyMessage;

    public String getJourneyMessage() {
        return journeyMessage;
    }

    public void setJourneyMessage(String journeyMessage) {
        this.journeyMessage = journeyMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getIsAutomatic() {
        return isAutomatic;
    }

    public void setIsAutomatic(Integer isAutomatic) {
        this.isAutomatic = isAutomatic;
    }

    public Integer getIsPeerToPeer() {
        return isPeerToPeer;
    }

    public void setIsPeerToPeer(Integer isPeerToPeer) {
        this.isPeerToPeer = isPeerToPeer;
    }

    public Integer getIsAdminToPeer() {
        return isAdminToPeer;
    }

    public void setIsAdminToPeer(Integer isAdminToPeer) {
        this.isAdminToPeer = isAdminToPeer;
    }

    public String getPopupMsg() {
        return popupMsg;
    }

    public void setPopupMsg(String popupMsg) {
        this.popupMsg = popupMsg;
    }

    public String getDashboardBadge() {
        return dashboardBadge;
    }

    public void setDashboardBadge(String dashboardBadge) {
        this.dashboardBadge = dashboardBadge;
    }

    public String getDashboardNextLevel() {
        return dashboardNextLevel;
    }

    public void setDashboardNextLevel(String dashboardNextLevel) {
        this.dashboardNextLevel = dashboardNextLevel;
    }

    public String getDashboardOnCompletion() {
        return dashboardOnCompletion;
    }

    public void setDashboardOnCompletion(String dashboardOnCompletion) {
        this.dashboardOnCompletion = dashboardOnCompletion;
    }

    public Level getL() {
        return l;
    }

    public void setL(Level l) {
        this.l = l;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
