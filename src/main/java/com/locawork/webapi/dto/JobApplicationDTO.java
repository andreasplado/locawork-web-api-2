package com.locawork.webapi.dto;

import java.util.Date;

public interface JobApplicationDTO {

    Integer getId();
    Integer getUser_id();
    String getEmail();
    String getTitle();
    String getDescription();
    Double getSalary();
    Double getLatitude();
    Double getLongitude();
    Date getcreated_at();
    Date getUpdated_at();
    Integer getJob_id();
    String getContact();
}
