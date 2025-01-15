package com.records.demo.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String department;

    @Column
    private String hireDate;

    @Column
    private String PhoneNumber;

    @Column
    private String email;

    @Column
    private String adress;

    public Employee(int employeeId, String firstName, String lastName, String department, String hireDate, String phoneNumber, String email, String adress) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.hireDate = hireDate;
        this.PhoneNumber = phoneNumber;
        this.email = email;
        this.adress = adress;
    }

    public Employee () {}

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    public String getHireDate() {
        return hireDate;
    }

    public String getAdress() {
        return adress;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(department, employee.department) && Objects.equals(hireDate, employee.hireDate) && Objects.equals(PhoneNumber, employee.PhoneNumber) && Objects.equals(email, employee.email) && Objects.equals(adress, employee.adress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName, department, hireDate, PhoneNumber, email, adress);
    }
}
