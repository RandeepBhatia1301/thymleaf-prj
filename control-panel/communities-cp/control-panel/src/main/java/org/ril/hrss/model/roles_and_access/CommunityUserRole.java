package org.ril.hrss.model.roles_and_access;

import javax.persistence.*;

@Entity
@Table(name = "user.community_user_role")
public class CommunityUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "community_role_id")
    private Integer communityRoleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCommunityRoleId() {
        return communityRoleId;
    }

    public void setCommunityRoleId(Integer communityRoleId) {
        this.communityRoleId = communityRoleId;
    }
}
