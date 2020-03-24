package org.ril.hrss.model.category_hierarchy;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user.category_subscription")
public class CategorySubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_hierarchy_id")
    private Integer categoryHierarchyId;

    @Column(name = "organization_id")
    private Integer organizationId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_admin")
    private Integer isAdmin;

    @Column(name = "is_default")
    private Integer isDefault;

    private Integer status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "sub_org_id")
    private Integer subOrgId;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "grand_parent_id")
    private Integer grandParentId;

    @Column(name = "admin_role_id")
    private Integer adminRoleId;

    @Column(name = "sme_role_id")
    private Integer smeRoleId;

    @Column(name = "is_sme")
    private Integer isSME;

    @Column(name = "is_active")
    private Integer isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCategoryHierarchyId() {
        return categoryHierarchyId;
    }

    public void setCategoryHierarchyId(Integer categoryHierarchyId) {
        this.categoryHierarchyId = categoryHierarchyId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getSubOrgId() {
        return subOrgId;
    }

    public void setSubOrgId(Integer subOrgId) {
        this.subOrgId = subOrgId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getGrandParentId() {
        return grandParentId;
    }

    public void setGrandParentId(Integer grandParentId) {
        this.grandParentId = grandParentId;
    }

    public Integer getIsSME() {
        return isSME;
    }

    public void setIsSME(Integer isSME) {
        this.isSME = isSME;
    }

    public Integer getAdminRoleId() {
        return adminRoleId;
    }

    public void setAdminRoleId(Integer adminRoleId) {
        this.adminRoleId = adminRoleId;
    }

    public Integer getSmeRoleId() {
        return smeRoleId;
    }

    public void setSmeRoleId(Integer smeRoleId) {
        this.smeRoleId = smeRoleId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "CategorySubscription{" +
                "id=" + id +
                ", categoryHierarchyId=" + categoryHierarchyId +
                ", organizationId=" + organizationId +
                ", userId=" + userId +
                ", isAdmin=" + isAdmin +
                ", isDefault=" + isDefault +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", subOrgId=" + subOrgId +
                ", parentId=" + parentId +
                ", grandParentId=" + grandParentId +
                ", adminRoleId=" + adminRoleId +
                ", smeRoleId=" + smeRoleId +
                ", isSME=" + isSME +
                '}';
    }
}


