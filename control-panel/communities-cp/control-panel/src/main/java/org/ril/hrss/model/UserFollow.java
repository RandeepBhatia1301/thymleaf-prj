package org.ril.hrss.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Index;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Document(collection = "user_follow")
public class UserFollow {

    @Id
    @Field("_id")
    private String id;

    @Indexed
    @Field("sub_org_id")
    @NotNull(message = "subOrgId must not be null")
    @Min(message = "subOrgId must be greater than 0", value = 1)
    private Integer subOrgId;

    @Indexed
    @Field("org_id")
    @NotNull(message = "orgId must not be null")
    @Min(message = "orgId must be greater than 0", value = 1)
    private Integer orgId;

    @Field("status")
    @NotNull(message = "status must not be null")
    @Min(message = "status must not be less than 0", value = 0)
    @Max(message = "status must not be greater than 1", value = 1)
    private Integer status;

    @Indexed
    @Field("user_id")
    @NotNull(message = "userId must not be null")
    @Min(message = "userId must be greater than 0", value = 1)
    private Long userId;

    @Field("follwer_id")
    @NotNull(message = "followerId must not be null")
    @Min(message = "followerId must be greater than 0", value = 1)
    private Long followerId;

    @Field("organization_id")
    private Long organizationId;


    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }
}