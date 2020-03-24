package org.ril.hrss.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "content_view")
public class ContentView {

    @Id
    @Field("_id")
    private String id;

    @Indexed
    @Field("sub_org_id")
    @Min(message = "subOrgId must be greater than 0", value = 1)
    @NotNull(message = "subOrgId must not be null")
    private Integer subOrgId;

    @Indexed
    @Field("content_type_id")
    @Min(message = "contentTypeId must be greater than 0", value = 1)
    @NotNull(message = "contentTypeId must not be null")
    private Integer contentTypeId;

    @Indexed
    @Field("content_id")
    @Min(message = "contentId must be greater than 0", value = 1)
    @NotNull(message = "contentId must not be null")
    private Integer contentId;

    @NotNull(message = "contentOwnerId must not be null")
    @Min(message = "contentOwnerId must be greater than 0", value = 1)
    @Field("content_owner_id")
    @Indexed
    private Integer contentOwnerId;

    @NotNull(message = "contentViewedById must not be null")
    @Min(message = "contentViewedById must be greater than 0", value = 1)
    @Field("content_viewed_by_id")
    @Indexed
    private Integer contentViewedById;

    @Field("viewed_at")
    private Date viewedAt;

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

    public Integer getContentOwnerId() {
        return contentOwnerId;
    }

    public void setContentOwnerId(Integer contentOwnerId) {
        this.contentOwnerId = contentOwnerId;
    }

    public Integer getContentViewedById() {
        return contentViewedById;
    }

    public void setContentViewedById(Integer contentViewedById) {
        this.contentViewedById = contentViewedById;
    }

    public Date getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(Date viewedAt) {
        this.viewedAt = viewedAt;
    }
}