package com.example.android.entity;

import java.util.Date;

public class Student {
    private String uuid;

    private String username;

    private String password;

    private String name;

    private String number;

    private String studentClass;

    private String phone;

    private Date attendanceTime;

    private Date date;

    private Integer status;

    public Student(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Student() {
    }

    public Student(String uuid, String username, String password, String name, String number, String studentClass, String phone) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.name = name;
        this.number = number;
        this.studentClass = studentClass;
        this.phone = phone;
    }

    public Student(String name, String number, String studentClass, String phone) {
        this.name = name;
        this.number = number;
        this.studentClass = studentClass;
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(Date attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
