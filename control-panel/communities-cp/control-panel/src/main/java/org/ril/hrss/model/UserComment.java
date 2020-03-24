package org.ril.hrss.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "user_comments")
public class UserComment {
    @Id
    @Field("_id")
    private String id;

    @Indexed
    @Field("content_type_id")
    @Min(message = "contentTypeId must be greater than 0", value = 1)
    @NotNull(message = "contentTypeId must not be null")
    private Integer contentTypeId;

    @Indexed
    @Field("sub_org_id")
    @Min(message = "subOrgId must be greater than 0", value = 1)
    @NotNull(message = "subOrgId must not be null")
    private Integer subOrgId;

    @Indexed
    @Field("content_id")
    @NotNull(message = "contentId must not be null")
    private Integer contentId;

    @Field("parent_id")
    @NotNull(message = "parentId must not be null")
    private String parentId;

    @Field("commented_at")
    private Date commentedAt;

    @Field("user_id")
    @Min(message = "userId must be greater than 0", value = 1)
    @NotNull(message = "userId must not be null")
    private Integer userId;

    @Field("content_owner_id")
    @Min(message = "contentOwnerId must be greater than 0", value = 1)
    @NotNull(message = "contentOwnerId must not be null")
    private Integer contentOwnerId;

    @Field("likes_count")
    private Integer likesCount;

    @Field("comment_count")
    private Integer commentCount;

    @Field("user_likes")
    private Map<Integer, String> userLikes;

    @Field("comment")
    @NotNull(message = "comment must not be null")
    private String comment;

    @Field("parent_comment")
    private String parentComment;

    @Field("mentioned_user_id")
    private List<Long> mentionedUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(Integer contentTypeId) {
        this.contentTypeId = contentTypeId;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Date getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(Date commentedAt) {
        this.commentedAt = commentedAt;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Map<Integer, String> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(Map<Integer, String> userLikes) {
        this.userLikes = userLikes;
    }

    public String getParentComment() {
        return parentComment;
    }

    public void setParentComment(String parentComment) {
        this.parentComment = parentComment;
    }

    public Integer getContentOwnerId() {
        return contentOwnerId;
    }

    public void setContentOwnerId(Integer contentOwnerId) {
        this.contentOwnerId = contentOwnerId;
    }

    public List<Long> getMentionedUserId() {
        return mentionedUserId;
    }

    public void setMentionedUserId(List<Long> mentionedUserId) {
        this.mentionedUserId = mentionedUserId;
    }
}
