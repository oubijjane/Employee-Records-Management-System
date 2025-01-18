package com.records.demo.entity;

import com.records.demo.audit.AuditInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
import java.util.Objects;


@Setter
@Getter
@Entity
@Audited
@EntityListeners(AuditingEntityListener.class)
public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int employeeId;

    @Column
    @NotBlank(message = "required")
    private String firstName;

    @Column
    @NotBlank(message = "required")
    private String lastName;

    @Column
    @NotBlank(message = "required")
    private String department;

    @Column
    @NotBlank(message = "required")
    private String hireDate;

    @Column
    private String PhoneNumber;

    @Column
    @Email(message = "enter a valid email")
    @NotBlank(message = "required")
    private String email;

    @Column
    private String adress;

    @Column
    private String jobTitle;

    @Column
    private String status;

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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(department, employee.department) && Objects.equals(hireDate, employee.hireDate) && Objects.equals(PhoneNumber, employee.PhoneNumber) && Objects.equals(email, employee.email) && Objects.equals(adress, employee.adress);
    }

}
