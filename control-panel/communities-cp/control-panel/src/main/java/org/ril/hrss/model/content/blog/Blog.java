package org.ril.hrss.model.content.blog;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "content.blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "blog_type")
    private Integer blogType;

    @Column(name = "sub_org_id")
    private Integer subOrgId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "thumbnail_image")
    private String thumbnailImage;

    @Column(name = "is_thumbnail_upload")
    private Integer isThumbnailUpload;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "reject_reason")
    private String rejectReason;

    @Column(name = "category_id")
    private Long categoryId;


    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
    private List<BlogSubCategory> blogSubCategories;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getBlogType() {
        return blogType;
    }

    public void setBlogType(Integer blogType) {
        this.blogType = blogType;
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

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public Integer getIsThumbnailUpload() {
        return isThumbnailUpload;
    }

    public void setIsThumbnailUpload(Integer isThumbnailUpload) {
        this.isThumbnailUpload = isThumbnailUpload;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSubOrgId() {
        return subOrgId;
    }

    public void setSubOrgId(Integer subOrgId) {
        this.subOrgId = subOrgId;
    }

    public List<BlogSubCategory> getBlogSubCategories() {
        return blogSubCategories;
    }

    public void setBlogSubCategories(List<BlogSubCategory> blogSubCategories) {
        this.blogSubCategories = blogSubCategories;
    }
}