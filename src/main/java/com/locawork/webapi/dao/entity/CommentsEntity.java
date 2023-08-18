package com.locawork.webapi.dao.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class CommentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @TableGenerator(
            name="comments",
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

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public CommentsEntity() {

    }
}


