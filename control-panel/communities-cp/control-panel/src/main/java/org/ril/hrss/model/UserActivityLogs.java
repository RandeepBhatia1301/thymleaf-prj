package org.ril.hrss.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document(collection = "user_activity_logs")
public class UserActivityLogs {

    @Id
    @Field("_id")
    private String id;

    @Indexed
    @Field("sub_org_id")
    private Integer subOrgId;

    @Field("user_id")
    @Indexed
    private Integer userId;

    @Indexed
    @Field("activity_code")
    private String activityCode;

    @Field("activity_time")
    private Date activityTime;

    @Indexed
    @Field("category_id")
    private Integer categoryId;

    @Field("sub_category_id")
    private List subCategoryId;

    @Field("content_type_id")
    private Integer contentTypeId;

    @Field("content_type")
    private Integer contentType;

    @Field("content_id")
    private Long contentId;

    @Field("is_points")
    private Integer isPoints;

    @Field("is_badges")
    private Integer isBadges;

    @Field("is_journey")
    private Integer isJourney;

    @Field("journey_level")
    private Integer journeyLevel;

    @Field("is_appearance")
    private Integer isAppearance;

    @Field("appearance_mode")
    private String appearanceMode;

    @Field("appearance_category")
    private Integer appearanceCategory;

    @Field("appearance_level")
    private Integer appearanceLevel;

    @Indexed
    @Field("leaderboard_activity_code")
    private Integer leaderboardActivityCode;

    @Field("activity_score")
    private Integer activityScore;

    @Indexed
    @Field("badges_id")
    private Integer badgesId;

    @Field("badge_level")
    private Integer badgeLevel;

    @Field("badge_message")
    private String badgeMessage;

    @Field("badge_by")
    private Integer badgeBy;

    @Field("followed_user_id")
    private Integer followedUserId;

    public UserActivityLogs() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSubOrgId() {
        return subOrgId;
    }

    public void setSubOrgId(Integer subOrgId) {
        this.subOrgId = subOrgId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public Date getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(Date activityTime) {
        this.activityTime = activityTime;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public List getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(List subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Integer getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(Integer contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Integer getIsPoints() {
        return isPoints;
    }

    public void setIsPoints(Integer isPoints) {
        this.isPoints = isPoints;
    }

    public Integer getIsBadges() {
        return isBadges;
    }

    public void setIsBadges(Integer isBadges) {
        this.isBadges = isBadges;
    }

    public Integer getIsJourney() {
        return isJourney;
    }

    public void setIsJourney(Integer isJourney) {
        this.isJourney = isJourney;
    }

    public Integer getJourneyLevel() {
        return journeyLevel;
    }

    public void setJourneyLevel(Integer journeyLevel) {
        this.journeyLevel = journeyLevel;
    }

    public Integer getIsAppearance() {
        return isAppearance;
    }

    public void setIsAppearance(Integer isAppearance) {
        this.isAppearance = isAppearance;
    }

    public String getAppearanceMode() {
        return appearanceMode;
    }

    public void setAppearanceMode(String appearanceMode) {
        this.appearanceMode = appearanceMode;
    }

    public Integer getAppearanceCategory() {
        return appearanceCategory;
    }

    public void setAppearanceCategory(Integer appearanceCategory) {
        this.appearanceCategory = appearanceCategory;
    }

    public Integer getAppearanceLevel() {
        return appearanceLevel;
    }

    public void setAppearanceLevel(Integer appearanceLevel) {
        this.appearanceLevel = appearanceLevel;
    }

    public Integer getLeaderboardActivityCode() {
        return leaderboardActivityCode;
    }

    public void setLeaderboardActivityCode(Integer leaderboardActivityCode) {
        this.leaderboardActivityCode = leaderboardActivityCode;
    }

    public Integer getActivityScore() {
        return activityScore;
    }

    public void setActivityScore(Integer activityScore) {
        this.activityScore = activityScore;
    }

    public Integer getBadgesId() {
        return badgesId;
    }

    public void setBadgesId(Integer badgesId) {
        this.badgesId = badgesId;
    }

    public Integer getBadgeLevel() {
        return badgeLevel;
    }

    public void setBadgeLevel(Integer badgeLevel) {
        this.badgeLevel = badgeLevel;
    }

    public String getBadgeMessage() {
        return badgeMessage;
    }

    public void setBadgeMessage(String badgeMessage) {
        this.badgeMessage = badgeMessage;
    }

    public Integer getBadgeBy() {
        return badgeBy;
    }

    public void setBadgeBy(Integer badgeBy) {
        this.badgeBy = badgeBy;
    }

    public Integer getFollowedUserId() {
        return followedUserId;
    }

    public void setFollowedUserId(Integer followedUserId) {
        this.followedUserId = followedUserId;
    }
}
