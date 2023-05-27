package com.locawork.webapi.dao.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "settings")
public class SettingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @TableGenerator(
            name="job",
            table="GENERATOR_TABLE",
            pkColumnName = "key",
            valueColumnName = "next",
            pkColumnValue="course",
            allocationSize=30
    )
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "radius")
    private Double radius;

    @Column(name = "view_by_default")
    private String viewByDefault;

    @Column(name = "ask_permissions_before_deleting_a_job")
    private boolean askPermissionsBeforeDeletingAJob;

    @Column(name = "show_information_on_startup")
    private boolean showInformationOnStartup;

    @Column(name = "is_biometric")
    private boolean isBiometric;

    @Column(name = "member_role")
    private String memberRole;

    @Column(name = "member_start_time")
    private Date memberStartTime;

    @Column(name = "currency")
    private String currency;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;




    public SettingsEntity() {

    }

    public SettingsEntity(String title, String description, Double salary, Double longitude, Double latitude) {
        this.setCreatedAt(new Date());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
        if (this.updatedAt == null) updatedAt = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
    }

    @PreRemove
    protected void preRemove() {
        this.updatedAt = new Date();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getRadius() {
        return radius;
    }

    public String getViewByDefault() {
        return viewByDefault;
    }

    public void setViewByDefault(String viewByDefault) {
        this.viewByDefault = viewByDefault;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Boolean getAskPermissionsBeforeDeletingAJob() {
        return askPermissionsBeforeDeletingAJob;
    }

    public void setAskPermissionsBeforeDeletingAJob(Boolean askPermissionsBeforeDeletingAJob) {
        this.askPermissionsBeforeDeletingAJob = askPermissionsBeforeDeletingAJob;
    }

    public Boolean getShowInformationOnStartup() {
        return showInformationOnStartup;
    }

    public void setShowInformationOnStartup(Boolean showInformationOnStartup) {
        this.showInformationOnStartup = showInformationOnStartup;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isBiometric() {
        return isBiometric;
    }

    public void setBiometric(boolean biometric) {
        isBiometric = biometric;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }

    public Date getMemberStartTime() {
        return memberStartTime;
    }

    public void setMemberStartTime(Date memberStartTime) {
        this.memberStartTime = memberStartTime;
    }
}