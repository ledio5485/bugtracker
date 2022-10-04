package com.bugtracker.persistence;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BUG")
@Data
public class BugEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
    private String title;
    private String description;
    private Status status;
    private UUID projectId;
    private UUID assignee;
    @CreatedDate
    private long createdDate;
    @LastModifiedDate
    private long lastModifiedDate;
}
