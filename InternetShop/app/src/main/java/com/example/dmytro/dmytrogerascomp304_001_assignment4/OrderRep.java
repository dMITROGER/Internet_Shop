package com.example.dmytro.dmytrogerascomp304_001_assignment4;

public class OrderRep {
    private int employeeId;
    private String password;
    private String firstName;
    private String lastName;
    private String userName;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public OrderRep() {
    }



    public OrderRep(int employeeId, String userName, String password, String firstName, String lastName) {
        this.employeeId = employeeId;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }



}
