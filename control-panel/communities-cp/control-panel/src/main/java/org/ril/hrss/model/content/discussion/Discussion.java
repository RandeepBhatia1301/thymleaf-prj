package org.ril.hrss.model.content.discussion;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "content.discussion")
public class Discussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_date")
    private Date createdAt;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "sub_org_id")
    private Long subOrgId;


    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL)
    private List<DiscussionSubCategory> discussionSubCategories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getSubOrgId() {
        return subOrgId;
    }

    public void setSubOrgId(Long subOrgId) {
        this.subOrgId = subOrgId;
    }

    public List<DiscussionSubCategory> getDiscussionSubCategories() {
        return discussionSubCategories;
    }

    public void setDiscussionSubCategories(List<DiscussionSubCategory> discussionSubCategories) {
        this.discussionSubCategories = discussionSubCategories;
    }
}