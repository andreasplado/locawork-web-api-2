package com.locawork.webapi.model;

import javax.persistence.Column;
import java.util.Date;

public class UserSettings {

    private int userId;

    private String fullname;

    private String email;

    private Double radius;

    private String viewByDefault;

    private boolean askPermissionsBeforeDeletingAJob;

    private boolean showInformationOnStartup;

    private String currency;

    private String role;

    private boolean isBiometric;

    private String contact;

    private String customerId;

    private Date createdAt;

    private Date updatedAt;

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public String getViewByDefault() {
        return viewByDefault;
    }

    public void setViewByDefault(String viewByDefault) {
        this.viewByDefault = viewByDefault;
    }

    public boolean isAskPermissionsBeforeDeletingAJob() {
        return askPermissionsBeforeDeletingAJob;
    }

    public void setAskPermissionsBeforeDeletingAJob(boolean askPermissionsBeforeDeletingAJob) {
        this.askPermissionsBeforeDeletingAJob = askPermissionsBeforeDeletingAJob;
    }

    public boolean isShowInformationOnStartup() {
        return showInformationOnStartup;
    }

    public void setShowInformationOnStartup(boolean showInformationOnStartup) {
        this.showInformationOnStartup = showInformationOnStartup;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isBiometric() {
        return isBiometric;
    }

    public void setBiometric(boolean biometric) {
        isBiometric = biometric;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
