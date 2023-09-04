package com.locawork.webapi.dao.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "jobs")
public class JobEntity {

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

    @Column(name = "title")
    private String title;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "description")
    private String description;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "payroll")
    private String payroll;

    @Column(name = "hours_to_work")
    private Double hoursToWork;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "created_at")
    private Date createdAt;


    @Column(name = "is_done")
    private boolean isDone;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;


    @Column(name = "applyer_id")
    private Integer applyerId;

    public JobEntity() {

    }

    public JobEntity(String title, String description, Double salary, Double longitude, Double latitude, String payroll, Double hoursToWork, Integer userId) {
        this.setTitle(title);
        this.setDescription(description);
        this.setSalary(salary);
        this.setLongitude(longitude);
        this.setLatitude(latitude);
        this.setUserId(userId);
        this.setPayroll(payroll);
        this.setHoursToWork(hoursToWork);
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

    public Double getLatitude() {
        return latitude;
    }

    private void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    private void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public Double getSalary() {
        return salary;
    }

    private void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getApplyerId() {
        return applyerId;
    }

    public void setApplyerId(Integer applyerId) {
        this.applyerId = applyerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getPayroll() {
        return payroll;
    }

    public void setPayroll(String payroll) {
        this.payroll = payroll;
    }

    public Double getHoursToWork() {
        return hoursToWork;
    }

    public void setHoursToWork(Double hoursToWork) {
        this.hoursToWork = hoursToWork;
    }
}

