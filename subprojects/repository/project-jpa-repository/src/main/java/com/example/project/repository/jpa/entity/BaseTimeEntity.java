package com.example.project.repository.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseTimeEntity {
    @Column(updatable = false)
    @CreatedDate
    protected LocalDateTime insertAt;

    @LastModifiedDate
    protected LocalDateTime updateAt;
}
