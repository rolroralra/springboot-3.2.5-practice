package com.example.project.repository.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity extends BaseTimeEntity {
    @Column(updatable = false, length = 30)
    @CreatedBy
    protected String insertId;

    @Column(length = 30)
    @LastModifiedBy
    protected String updateId;
}
