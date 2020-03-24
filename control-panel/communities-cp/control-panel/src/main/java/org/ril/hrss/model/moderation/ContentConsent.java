package org.ril.hrss.model.moderation;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "content.content_consent")
public class ContentConsent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sub_org_id")
    private Integer subOrgId;

    @Column(name = "content_type_id")
    private Integer contentTypeId;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "approval_level")
    private Integer approvalLevel;

    @Column(name = "approval_moderator_type")
    private Integer approvalModeratorType;

    @Column(name = "publish_status")
    private Integer publishStatus;

    @Column(name = "pending_status")
    private Integer pendingStatus;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "admin_user_id")
    private Long adminUserId;

    @Column(name = "send_by_user_id")
    private Long sendByUserId;

    @Column(name = "moderated_by")
    private Long moderatedBy;

    @Column(name = "update_msg")
    private String updateMessage;

    @Column(name = "reject_msg")
    private String rejectMessage;

    @Column(name = "reject_reason")
    private String rejectReason;

    @Column(name = "created_date")
    private Date createdDate;


    public ContentConsent() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubOrgId() {
        return subOrgId;
    }

    public void setSubOrgId(Integer subOrgId) {
        this.subOrgId = subOrgId;
    }

    public Integer getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(Integer contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getApprovalLevel() {
        return approvalLevel;
    }

    public void setApprovalLevel(Integer approvalLevel) {
        this.approvalLevel = approvalLevel;
    }

    public Integer getApprovalModeratorType() {
        return approvalModeratorType;
    }

    public void setApprovalModeratorType(Integer approvalModeratorType) {
        this.approvalModeratorType = approvalModeratorType;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Integer getPendingStatus() {
        return pendingStatus;
    }

    public void setPendingStatus(Integer pendingStatus) {
        this.pendingStatus = pendingStatus;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public Long getSendByUserId() {
        return sendByUserId;
    }

    public void setSendByUserId(Long sendByUserId) {
        this.sendByUserId = sendByUserId;
    }

    public Long getModeratedBy() {
        return moderatedBy;
    }

    public void setModeratedBy(Long moderatedBy) {
        this.moderatedBy = moderatedBy;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public String getRejectMessage() {
        return rejectMessage;
    }

    public void setRejectMessage(String rejectMessage) {
        this.rejectMessage = rejectMessage;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
