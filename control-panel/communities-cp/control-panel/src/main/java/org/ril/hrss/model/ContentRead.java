package org.ril.hrss.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "content_read")
public class ContentRead {

    @Id
    @Field("_id")
    private String id;

    @Indexed
    @Field("content_owner_id")
    @NotNull(message = "contentOwnerId must not be null")
    @Min(message = "contentOwnerId must be greater than 0", value = 1)
    private Long contentOwnerId;

    @Indexed
    @Field("content_viewer_id")
    @NotNull(message = "contentViewerId must not be null")
    @Min(message = "contentViewerId must be greater than 0", value = 1)
    private Long contentViewerId;

    @Indexed
    @Field("sub_org_id")
    @NotNull(message = "subOrgId must not be null")
    @Min(message = "subOrgId must be greater than 0", value = 1)
    private Integer subOrgId;

    @Field("read_percentage")
    @NotNull(message = "read percentage must not be null")
    @Min(message = "read percentage must not be less than 0", value = 0)
    @Max(message = "read percentage must not be greater than 100", value = 100)
    private Integer readPercentage;

    @Indexed
    @Field("content_type_id")
    @NotNull(message = "contentTypeId must not be null")
    @Min(message = "contentTypeId must be greater than 0", value = 1)
    private Integer contentTypeId;

    @Indexed
    @Field("content_id")
    @NotNull(message = "contentId must not be null")
    @Min(message = "contentId must be greater than 0", value = 1)
    private Integer contentId;

    @Field("read_at")
    private Date readAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getContentOwnerId() {
        return contentOwnerId;
    }

    public void setContentOwnerId(Long contentOwnerId) {
        this.contentOwnerId = contentOwnerId;
    }

    public Long getContentViewerId() {
        return contentViewerId;
    }

    public void setContentViewerId(Long contentViewerId) {
        this.contentViewerId = contentViewerId;
    }

    public Integer getSubOrgId() {
        return subOrgId;
    }

    public void setSubOrgId(Integer subOrgId) {
        this.subOrgId = subOrgId;
    }

    public Integer getReadPercentage() {
        return readPercentage;
    }

    public void setReadPercentage(Integer readPercentage) {
        this.readPercentage = readPercentage;
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

    public Date getReadAt() {
        return readAt;
    }

    public void setReadAt(Date readAt) {
        this.readAt = readAt;
    }
}
