package com.locawork.webapi.dao.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "job_applications")
public class JobApplicationEntity {

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

    @Column(name = "job_id")
    private Integer jobId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "is_approved")
    private boolean isApproved;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public JobApplicationEntity() {

    }

    public JobApplicationEntity(String title, String description, Double salary, Double longitude, Double latitude) {
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

    public Integer getJob() {
        return jobId;
    }

    public void setJob(Integer jobId) {
        this.jobId = jobId;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

