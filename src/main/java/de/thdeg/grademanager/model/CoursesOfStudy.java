package de.thdeg.grademanager.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "coursesOfStudy")
public class CoursesOfStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "degree")
    private String degree;

    @Column(name = "duration")
    private int duration;

    @Column(name = "fees")
    private int fees;

    @Column(name = "field_of_study")
    private String fieldOfStudy;

    @OneToMany(
            mappedBy = "coursesOfStudy",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Course> courses = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "courses_of_study_id")
    private CoursesOfStudy coursesOfStudy;

    public List<Course> getCourses() {
        return courses;
    }

    /*
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "courses_of_study_id")
    private CoursesOfStudy coursesOfStudy;
    */
    public CoursesOfStudy() {

    }

    public CoursesOfStudy(String name, String degree, int duration, int fees, String fieldOfStudy) {
        this.name = name;
        this.degree = degree;
        this.duration = duration;
        this.fees = fees;
        this.fieldOfStudy = fieldOfStudy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoursesOfStudy that = (CoursesOfStudy) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public void addCourse(Course course) {
        courses.add(course);
        course.setCoursesOfStudy(this);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        course.setCoursesOfStudy(null);
    }

    /**
     * update a given course in the courses-Set.
     *
     * author: Kevin Thaller
     *
     * @param course the object to update.
     */
    public void updateCourse(Course course){
        int index = findCourseID(course);
        courses.set(index, course);
    }

    /**
     * find the index of a course in the courses-set.
     *
     * author: Kevin Thaller
     *
     * @param course the object to find in the set
     * @return the index of the found course in the courses-set or '-1' if the course is not found.
     */
    private int findCourseID(Course course){
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId() == course.getId()){
                return i;
            }
        }
        return -1;
    }
}
