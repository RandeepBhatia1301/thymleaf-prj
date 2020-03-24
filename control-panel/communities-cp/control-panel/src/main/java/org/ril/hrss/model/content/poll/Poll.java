package org.ril.hrss.model.content.poll;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "content.poll")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "sub_org_id")
    private Long subOrgId;

    @Column(name = "poll_category")
    private Integer pollCategory;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "result_type")
    private Integer resultType;

    @Column(name = "poll_type")
    private Integer pollType;

    @Column(name = "status")
    private Integer status;

    @Transient
    private Integer contentTypeId;

    @Column(name = "ques_text")
    private String quesText;

    @Column(name = "ques_img")
    private String quesImage;

    @Column(name = "reject_message")
    private String rejectMessage;

    @Column(name = "reject_reason")
    private String rejectReason;

    @Column(name = "update_message")
    private String updateMessage;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "start_date_time")
    private Date startDateTime;

    @Column(name = "end_date_time")
    private Date endDateTime;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<PollCategoryHierarchy> pollCategoryHierarchyList;

    @Transient
    private Boolean firstContent;

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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getSubOrgId() {
        return subOrgId;
    }

    public void setSubOrgId(Long subOrgId) {
        this.subOrgId = subOrgId;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    public Integer getPollType() {
        return pollType;
    }

    public void setPollType(Integer pollType) {
        this.pollType = pollType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPollCategory() {
        return pollCategory;
    }

    public void setPollCategory(Integer pollCategory) {
        this.pollCategory = pollCategory;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getQuesText() {
        return quesText;
    }

    public void setQuesText(String quesText) {
        this.quesText = quesText;
    }

    public String getQuesImage() {
        return quesImage;
    }

    public void setQuesImage(String quesImage) {
        this.quesImage = quesImage;
    }

    public String getRejectMessage() {
        return rejectMessage;
    }

    public void setRejectMessage(String rejectMessage) {
        this.rejectMessage = rejectMessage;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<PollCategoryHierarchy> getPollCategoryHierarchyList() {
        return pollCategoryHierarchyList;
    }

    public void setPollCategoryHierarchyList(List<PollCategoryHierarchy> pollCategoryHierarchyList) {
        this.pollCategoryHierarchyList = pollCategoryHierarchyList;
    }

    public Boolean getFirstContent() {
        return firstContent;
    }

    public void setFirstContent(Boolean firstContent) {
        this.firstContent = firstContent;
    }
}
