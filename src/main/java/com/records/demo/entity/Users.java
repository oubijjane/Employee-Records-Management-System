package com.records.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String email;
    @Column
    private String password;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Roles> roles;

    @Column
    private String department;


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


}
