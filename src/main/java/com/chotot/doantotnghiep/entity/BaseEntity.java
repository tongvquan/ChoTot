package com.chotot.doantotnghiep.entity;

import java.util.Date;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createddate", nullable = false, updatable = false)
    @CreatedDate
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createdDate;

    @Column(name = "modifieddate")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    @LastModifiedDate
    private Date modifiedDate;

    @Column(name = "createdby")
    @CreatedBy
    private String createdBy;

    @Column(name = "modifiedby")
    @LastModifiedBy
    private String modifiedBy;

    @PreUpdate
    public void setLastModifiedDate() {
        this.modifiedDate = new Date();
    }
}
