package com.klu.model;

public class Student {
    private int studentId;
    private String name;
    private String course;
    private int year;

    // Default Constructor
    public Student() {
    }

    // Constructor for Constructor Injection
    public Student(int studentId, String name, String course, int year) {
        this.studentId = studentId;
        this.name = name;
        this.course = course;
        this.year = year;
    }

    // Setter methods (at least course and year as requested, but providing all for completeness)
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Student [studentId=" + studentId + ", name=" + name + ", course=" + course + ", year=" + year + "]";
    }
}
