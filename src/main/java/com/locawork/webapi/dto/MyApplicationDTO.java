package com.locawork.webapi.dto;

import java.util.Date;

public interface MyApplicationDTO {

    Integer getId();
    Integer getUser_id();
    String getAccount_email();
    String getTitle();
    String getDescription();
    Double getSalary();
    Date getcreated_at();
    Date getUpdated_at();
    Integer getJob_id();
    Double getLongitude();
    Double getLatitude();
}
