package org.ril.hrss.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;
import org.ril.hrss.model.auth.AdminUser;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "organization.org_master")
public class Org {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviations")
    private String abbreviations;

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

    @Column(name = "app_name")
    private String appName;

    @Column(name = "app_url")
    private String appUrl;

    @Column(name = "api_url")
    private String apiUrl;

    @Column(name = "is_sso_enabled")
    private Integer isSsoEnabled;

    @Column(name = "can_create_coummunity")
    private Integer can_create_coummunity;

    @Column(name = "community_limit")
    private Integer community_limit;

    @Column(name = "can_create_sub_organization")
    private Integer can_create_sub_organization;

    @Column(name = "sub_organization_limit")
    private Integer sub_organization_limit;

    @Column(name = "level_limit")
    private Integer level_limit;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "is_approved")
    private Integer is_approved;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "org", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AdminUser> adminUser;

    @Transient
    private String registeredAdminName;

    @Transient
    private String registeredAdminEmail;

    @Column(name = "sso_url")
    private String ssoUrl;

    @Column(name = "sso_key")
    private String ssoKey;

    @Column(name = "sso_type")
    private String ssoType;

  /*  @Column(name = "ga_code")
    private String gaCode;

    @Column(name = "is_analytics_enabled")
    private Integer isAnalyticsEnabled;
*/
   /* @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
>>>>>>> a0a2836d5cfa4f4a74b880a2a82da037cbc6db3c
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "organization.org_content_type", joinColumns = @JoinColumn(name = "org_id", updatable = false,referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "content_type_id", referencedColumnName = "id",updatable = false))
    private List<Content> contents;*/

 /*   @OneToMany(fetch = FetchType.EAGER,mappedBy = "org", cascade = {CascadeType.MERGE})
    private Set<OrgContent> orgContents = new HashSet<>();*/

    @ManyToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "organization.org_module", joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id"))
    private List<Module> modules;
/*
    @ManyToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "organization.org_language", joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "id"))
    private List<Language> language;*/

   /* @ManyToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "organization.org_product_hierarchy", joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private List<ProductHierarchy> productHierarchies;*/

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

    public String getAbbreviations() {
        return abbreviations;
    }

    public void setAbbreviations(String abbreviations) {
        this.abbreviations = abbreviations;
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

    public Integer getIsSsoEnabled() {
        return isSsoEnabled;
    }

    public void setIsSsoEnabled(Integer isSsoEnabled) {
        this.isSsoEnabled = isSsoEnabled;
    }

    public Integer getCan_create_coummunity() {
        return can_create_coummunity;
    }

    public void setCan_create_coummunity(Integer can_create_coummunity) {
        this.can_create_coummunity = can_create_coummunity;
    }

    public Integer getCommunity_limit() {
        return community_limit;
    }

    public void setCommunity_limit(Integer community_limit) {
        this.community_limit = community_limit;
    }

    public Integer getCan_create_sub_organization() {
        return can_create_sub_organization;
    }

    public void setCan_create_sub_organization(Integer can_create_sub_organization) {
        this.can_create_sub_organization = can_create_sub_organization;
    }

    public Integer getSub_organization_limit() {
        return sub_organization_limit;
    }

    public void setSub_organization_limit(Integer sub_organization_limit) {
        this.sub_organization_limit = sub_organization_limit;
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

    public Integer getIs_approved() {
        return is_approved;
    }

    public void setIs_approved(Integer is_approved) {
        this.is_approved = is_approved;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getLevel_limit() {
        return level_limit;
    }

    public void setLevel_limit(Integer level_limit) {
        this.level_limit = level_limit;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
    /*
    public List<Language> getLanguage() {
        return language;
    }

    public void setLanguage(List<Language> language) {
        this.language = language;
    }*/

   /* public List<ProductHierarchy> getProductHierarchies() {
        return productHierarchies;
    }

    public void setProductHierarchies(List<ProductHierarchy> productHierarchies) {
        this.productHierarchies = productHierarchies;
    }*/
/*
    public Set<OrgContent> getOrgContents() {
        return orgContents;
    }

    public void setOrgContents(Set<OrgContent> orgContents) {
        this.orgContents = orgContents;
    }*/

}


