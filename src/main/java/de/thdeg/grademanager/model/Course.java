package de.thdeg.grademanager.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "course")
public class Course
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "semester")
    private int semester;

    @Column(name = "course_type")
    private String courseType;

    @Column(name = "sws")
    private int sws;

    @Column(name = "ects")
    private double ects;

    @Column(name = "exam_type")
    private String examType;

    @Column(name = "is_credited")
    private Boolean isCredited;

    @ManyToOne(fetch = FetchType.LAZY)
    private CoursesOfStudy coursesOfStudy;

    public Course()
    {

    }

    public Course(String name, int semester, String courseType, int sws, double ects, String examType, Boolean isCredited)
    {
        this.name = name;
        this.semester = semester;
        this.courseType = courseType;
        this.sws = sws;
        this.ects = ects;
        this.examType = examType;
        this.isCredited = isCredited;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getSemester()
    {
        return semester;
    }

    public void setSemester(int semester)
    {
        this.semester = semester;
    }

    public String getCourseType()
    {
        return courseType;
    }

    public void setCourseType(String courseType)
    {
        this.courseType = courseType;
    }

    public int getSws()
    {
        return sws;
    }

    public void setSws(int sws)
    {
        this.sws = sws;
    }

    public double getEcts()
    {
        return ects;
    }

    public void setEcts(double ects)
    {
        this.ects = ects;
    }

    public String getExamType()
    {
        return examType;
    }

    public void setExamType(String examType)
    {
        this.examType = examType;
    }

    public Boolean isCredited()
    {
        return isCredited;
    }

    public void setCredited(Boolean credited)
    {
        isCredited = credited;
    }

    public void setCoursesOfStudy(CoursesOfStudy coursesOfStudy)
    {
        this.coursesOfStudy = coursesOfStudy;
    }
}
