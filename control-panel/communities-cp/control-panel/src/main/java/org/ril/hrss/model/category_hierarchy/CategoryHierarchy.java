package org.ril.hrss.model.category_hierarchy;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;
import org.ril.hrss.model.SubOrg;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "organization.category_hierarchy")
public class CategoryHierarchy {

    @Transient
    @Embedded
    ImageUploadJson imageUploadJson;
    @Transient
    String parentName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "image_path")
    private String imagePath;

    /*   @Column(name = "category_type_id")
       private Integer categoryTypeId;*/
    @Column(name = "description")
    private String description;
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "is_private")
    private Integer isPrivate;
    @Column(name = "grand_parent_id")
    private Integer grandParentId;
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "organization_id")
    private Integer organizationId;
    @Column(name = "status")
    private Integer status;
    @Column(name = "title_acronym")
    private String titleAcronym;
    @Column(name = "member_count")
    private Integer memberCount;
    @Column(name = "child_hierarchy_count")
    private Integer subcategoryCount;
    @Column(name = "poll_count")
    private Integer pollCount;
    @Column(name = "event_count")
    private Integer eventCount;
    @Column(name = "discussion_count")
    private Integer discussionCount;
    @Column(name = "blog_count")
    private Integer blogCount;
    @Column(name = "is_hybrid")
    private Integer isHybrid;
    @Column(name = "sub_org_id")
    private Integer suborgId;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "is_approval_required")
    private Integer isApprovalRequired;
    @Column(name = "content_approval")
    private String contentApproval;
    @ManyToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "organization.suborg_category_hierarchy", joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "sub_org_id", referencedColumnName = "id"))
    private List<SubOrg> subOrgs;
    @OneToOne
    @JoinColumn(name = "category_type_id", referencedColumnName = "id")
    private CategoryType categoryType;

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public ImageUploadJson getImageUploadJson() {
        return imageUploadJson;
    }

    public void setImageUploadJson(ImageUploadJson imageUploadJson) {
        this.imageUploadJson = imageUploadJson;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }


    public Integer getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Integer isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Integer getGrandParentId() {
        return grandParentId;
    }

    public void setGrandParentId(Integer grandParentId) {
        this.grandParentId = grandParentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitleAcronym() {
        return titleAcronym;
    }

    public void setTitleAcronym(String titleAcronym) {
        this.titleAcronym = titleAcronym;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getSubcategoryCount() {
        return subcategoryCount;
    }

    public void setSubcategoryCount(Integer subcategoryCount) {
        this.subcategoryCount = subcategoryCount;
    }

    public Integer getPollCount() {
        return pollCount;
    }

    public void setPollCount(Integer pollCount) {
        this.pollCount = pollCount;
    }

    public Integer getEventCount() {
        return eventCount;
    }

    public void setEventCount(Integer eventCount) {
        this.eventCount = eventCount;
    }

    public Integer getDiscussionCount() {
        return discussionCount;
    }

    public void setDiscussionCount(Integer discussionCount) {
        this.discussionCount = discussionCount;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
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

    public Integer getIsApprovalRequired() {
        return isApprovalRequired;
    }

    public void setIsApprovalRequired(Integer isApprovalRequired) {
        this.isApprovalRequired = isApprovalRequired;
    }

    public String getContentApproval() {
        return contentApproval;
    }

    public void setContentApproval(String contentApproval) {
        this.contentApproval = contentApproval;
    }

    public List<SubOrg> getSubOrgs() {
        return subOrgs;
    }

    public void setSubOrgs(List<SubOrg> subOrgs) {
        this.subOrgs = subOrgs;
    }

    public Integer getIsHybrid() {
        return isHybrid;
    }

    public void setIsHybrid(Integer isHybrid) {
        this.isHybrid = isHybrid;
    }

    public Integer getSuborgId() {
        return suborgId;
    }

    public void setSuborgId(Integer suborgId) {
        this.suborgId = suborgId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return "CategoryHierarchy{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", description='" + description + '\'' +
                ", createdBy=" + createdBy +
                ", isPrivate=" + isPrivate +
                ", grandParentId=" + grandParentId +
                ", parentId=" + parentId +
                ", organizationId=" + organizationId +
                ", status=" + status +
                ", titleAcronym='" + titleAcronym + '\'' +
                ", memberCount=" + memberCount +
                ", subcategoryCount=" + subcategoryCount +
                ", pollCount=" + pollCount +
                ", eventCount=" + eventCount +
                ", discussionCount=" + discussionCount +
                ", blogCount=" + blogCount +
                ", isHybrid=" + isHybrid +
                ", suborgId=" + suborgId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isApprovalRequired=" + isApprovalRequired +
                ", contentApproval='" + contentApproval + '\'' +
                ", subOrgs=" + subOrgs +
                ", imageUploadJson=" + imageUploadJson +
                ", categoryType=" + categoryType +
                ", parentName='" + parentName + '\'' +
                '}';
    }
}
