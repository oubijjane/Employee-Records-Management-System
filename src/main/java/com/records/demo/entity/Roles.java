package com.records.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Audited
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    @Getter(AccessLevel.NONE)
    private Users user;

    @Column
    private String email;

    @Column()
    private String role;

    @Column
    @CreatedBy
    private String createdBy;

    @Column
    @CreatedDate
    private LocalDateTime created;

    @Column
    @LastModifiedBy
    private String modifiedBy;

    @Column
    @LastModifiedDate
    private LocalDateTime modified;

    public Roles(Users user, String role) {

        this.role = role;
        this.user = user;
        this.email = user.getEmail();
    }

}
