package org.ril.hrss.model.moderation;


import javax.persistence.*;

@Entity
@Table(name = "organization.hierarchy_moderation")
public class HierarchyModeration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "org_id")
    private Integer orgId;

    @Column(name = "suborg_id")
    private Integer suborgId;

    @Column(name = "catergory_hierarchy_id")
    private Integer catergoryHierarchyId;

    @Column(name = "can_join_category")
    private Integer canJoinCategory;

    @Column(name = "moderated_by")
    private Integer moderatedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getCatergoryHierarchyId() {
        return catergoryHierarchyId;
    }

    public void setCatergoryHierarchyId(Integer catergoryHierarchyId) {
        this.catergoryHierarchyId = catergoryHierarchyId;
    }

    public Integer getCanJoinCategory() {
        return canJoinCategory;
    }

    public void setCanJoinCategory(Integer canJoinCategory) {
        this.canJoinCategory = canJoinCategory;
    }

    public Integer getModeratedBy() {
        return moderatedBy;
    }

    public void setModeratedBy(Integer moderatedBy) {
        this.moderatedBy = moderatedBy;
    }

    public Integer getSuborgId() {
        return suborgId;
    }

    public void setSuborgId(Integer suborgId) {
        this.suborgId = suborgId;
    }
}
