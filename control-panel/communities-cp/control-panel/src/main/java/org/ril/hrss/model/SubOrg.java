package org.ril.hrss.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.ril.hrss.model.auth.AdminUser;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "organization.sub_org_master")
public class SubOrg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "org_id")
    private Integer orgId;

    @Column(name = "abbrevation")
    private String abbrevation;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "logo_image")
    private String logoImage;

    @Column(name = "disjoin_default_communities")
    private Integer disjoinDefaultCommunities;

    @Column(name = "status")
    private Integer status;

    @Column(name = "sso_url")
    private String ssoUrl;

    @Column(name = "sso_key")
    private String ssoKey;

    @Column(name = "sso_type")
    private String ssoType;

    @Column(name = "app_name")
    private String appName;

    @Column(name = "app_url")
    private String appUrl;

    @Column(name = "api_url")
    private String apiUrl;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;


    @Column(name = "created_by")
    private Integer createdBy;

    @OneToMany(mappedBy = "subOrgId", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AdminUser> adminUser;

    @Transient
    private String registeredAdminName;

    @Transient
    private String registeredAdminEmail;

  /*  @Column(name = "ga_code")
    private String gaCode;*/

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbrevation() {
        return abbrevation;
    }

    public void setAbbrevation(String abbrevation) {
        this.abbrevation = abbrevation;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public Integer getDisjoinDefaultCommunities() {
        return disjoinDefaultCommunities;
    }

    public void setDisjoinDefaultCommunities(Integer disjoinDefaultCommunities) {
        this.disjoinDefaultCommunities = disjoinDefaultCommunities;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSsoUrl() {
        return ssoUrl;
    }

    public void setSsoUrl(String ssoUrl) {
        this.ssoUrl = ssoUrl;
    }

    public String getSsoKey() {
        return ssoKey;
    }

    public void setSsoKey(String ssoKey) {
        this.ssoKey = ssoKey;
    }

    public String getSsoType() {
        return ssoType;
    }

    public void setSsoType(String ssoType) {
        this.ssoType = ssoType;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public List<AdminUser> getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(List<AdminUser> adminUser) {
        this.adminUser = adminUser;
    }

    public String getRegisteredAdminName() {
        return registeredAdminName;
    }

    public void setRegisteredAdminName(String registeredAdminName) {
        this.registeredAdminName = registeredAdminName;
    }

    public String getRegisteredAdminEmail() {
        return registeredAdminEmail;
    }

    public void setRegisteredAdminEmail(String registeredAdminEmail) {
        this.registeredAdminEmail = registeredAdminEmail;
    }

   /* public String getGaCode() {
        return gaCode;
    }

    public void setGaCode(String gaCode) {
        this.gaCode = gaCode;
    }*/
}


