package com.sc.models;

import com.sc.enums.DepartmentsEnum;

public class Company {

    private String name;
    private String address;
    private String city;
    private DepartmentsEnum department;
    private String phoneNumber;
    private String email;
    private String imgPath;

    public Company(String name, String address, String city, DepartmentsEnum department, String phoneNumber, String email) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Company(String name, String address, String city, DepartmentsEnum department, String phoneNumber, String email, String imgPath) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.imgPath = imgPath;
    }

    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public DepartmentsEnum getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentsEnum department) {
        this.department = department;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", department=" + department +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
