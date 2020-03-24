package org.ril.hrss.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document(collection = "feed")
public class Feed {

    @Id
    @Field("_id")
    private String id;

    @Indexed
    @Field("activity_type")
    private String activityType;

    @Field("user_id")
    @Indexed
    private Integer userId;

    @Indexed
    @Field("content_type_id")
    private Integer contentTypeId;

    @Field("content_id")
    private Integer contentId;

    @Field("content_visibility")
    private Integer contentVisibility;

    @Indexed
    @Field("sub_org_id")
    private Integer subOrgId;

    @Indexed
    @Field("category_id")
    private Integer categoryId;

    @Field("score")
    private Integer score;

    @Field("is_closed")
    private Integer isClosed;

    @Field("shared_by")
    private Integer sharedBy;

    @Field("invited_user_id")
    private List<Integer> invitedUserId;

    @Field("action_user_id")
    private List<Integer> actionUserId;

    @Indexed
    @Field("sub_category_id")
    private List<Integer> subCategoryId;

    @Field("tag_id")
    private List<Integer> tagId;

    @Field("accepted_user_id")
    private List<Integer> acceptedUserId;

    @Field("mentioned_user_id")
    private List<Long> mentionedUserId;

    @Field("date")
    private Date date;

    @Field("data")
    private FeedObject feedObject;

    @Field("replied_to_user_id")
    private Integer RepliedToUserId;

    public Feed() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(Integer contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getContentVisibility() {
        return contentVisibility;
    }

    public void setContentVisibility(Integer contentVisibility) {
        this.contentVisibility = contentVisibility;
    }

    public Integer getSubOrgId() {
        return subOrgId;
    }

    public void setSubOrgId(Integer subOrgId) {
        this.subOrgId = subOrgId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Integer isClosed) {
        this.isClosed = isClosed;
    }

    public Integer getSharedBy() {
        return sharedBy;
    }

    public void setSharedBy(Integer sharedBy) {
        this.sharedBy = sharedBy;
    }

    public List<Integer> getInvitedUserId() {
        return invitedUserId;
    }

    public void setInvitedUserId(List<Integer> invitedUserId) {
        this.invitedUserId = invitedUserId;
    }

    public List<Integer> getActionUserId() {
        return actionUserId;
    }

    public void setActionUserId(List<Integer> actionUserId) {
        this.actionUserId = actionUserId;
    }

    public List<Integer> getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(List<Integer> subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public List<Integer> getTagId() {
        return tagId;
    }

    public void setTagId(List<Integer> tagId) {
        this.tagId = tagId;
    }

    public List<Integer> getAcceptedUserId() {
        return acceptedUserId;
    }

    public void setAcceptedUserId(List<Integer> acceptedUserId) {
        this.acceptedUserId = acceptedUserId;
    }

    public List<Long> getMentionedUserId() {
        return mentionedUserId;
    }

    public void setMentionedUserId(List<Long> mentionedUserId) {
        this.mentionedUserId = mentionedUserId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public FeedObject getFeedObject() {
        return feedObject;
    }

    public void setFeedObject(FeedObject feedObject) {
        this.feedObject = feedObject;
    }

    public Integer getRepliedToUserId() {
        return RepliedToUserId;
    }

    public void setRepliedToUserId(Integer repliedToUserId) {
        RepliedToUserId = repliedToUserId;
    }
}
