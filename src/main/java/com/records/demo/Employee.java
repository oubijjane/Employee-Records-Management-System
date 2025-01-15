package com.records.demo;

import java.util.Objects;

public class Employee {

    private int employeeId;
    private String firstName;
    private String lastName;
    private String department;
    private String hireDate;
    private String adress;

    public Employee(int employeeId, String firstName, String lastName, String department, String hireDate, String adress) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.hireDate = hireDate;
        this.adress = adress;
    }
    public Employee () {}

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

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(department, employee.department) && Objects.equals(hireDate, employee.hireDate) && Objects.equals(adress, employee.adress);
    }

}
