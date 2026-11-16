
package com.tka.student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "student_id")
    private int studentId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "course")
    private String course;

    @Column(name = "percentage")
    private double percentage;

    @Column(name = "college")
    private String college;

    public Student() {
    }

    public Student(int studentId, String studentName,
            String course, double percentage, String college) {

        super();
        this.studentId = studentId;
        this.studentName = studentName;
        this.course = course;
        this.percentage = percentage;
        this.college = college;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Override
    public String toString() {
        return "Student [studentId=" + studentId
                + ", studentName=" + studentName
                + ", course=" + course
                + ", percentage=" + percentage
                + ", college=" + college + "]";
    }
}
