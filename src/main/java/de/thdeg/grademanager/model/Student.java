package de.thdeg.grademanager.model;

import de.thdeg.grademanager.interfaces.Calculable;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "student")
public class Student extends UniversityMember implements Calculable {
    @Column(name = "status")
    private String status;

    @Column(name = "paid_fees")
    private boolean paidFees;

    @Column(name = "semester")
    private int semester;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true) // cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Enrollment> enrollments;

    @OneToOne(fetch = FetchType.LAZY)
    private CoursesOfStudy coursesOfStudy;

    public Student() {

    }

    public Student(String gender, String firstName, String lastName, String placeOfResidence, String birthPlace,
                   String officialEmail, String privateEmail, String status,
                   boolean paidFess, int semester) {
        super(gender, firstName, lastName, placeOfResidence, birthPlace,
                officialEmail, privateEmail);
        this.status = status;
        this.paidFees = paidFess;
        this.semester = semester;
    }

    /**
     * implementation of the calculable-interface.
     * this method calculates the average of the students grades.
     * <p>
     * author: Kevin Thaller
     *
     * @return the calculated average of the students grades.
     */
    @Override
    public double calculateAverage() {
        double average = 0.0;
        int counter = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getGrade() != null) {
                average += enrollment.getGrade();
                counter++;
            }
        }
        return average / counter;
    }

    /**
     * implementation of the calculable-interface.
     * this method calculates the median of the students grades.
     * <p>
     * author: Kevin Thaller
     *
     * @return the calculated median of the students grades.
     */
    @Override
    public int calculateMedian() {
        List<Double> markList = new ArrayList<>();
        int median = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getGrade() != null) {
                markList.add(enrollment.getGrade());
            }
        }
        Collections.sort(markList);
        median = markList.get(markList.size() / 2).intValue();
        if (markList.size() % 2 == 0)
            median = ((median + markList.get(markList.size() / 2 - 1).intValue()) / 2);
        return median;
    }

    @Override
    public String toString() {
        return getId() + " " + getFirstName() + " " + getLastName();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isPaidFees() {
        return paidFees;
    }

    public void setPaidFees(boolean paidFees) {
        this.paidFees = paidFees;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setCoursesOfStudy(CoursesOfStudy coursesOfStudy) {
        this.coursesOfStudy = coursesOfStudy;
    }

    public CoursesOfStudy getCoursesOfStudy() {
        return coursesOfStudy;
    }

    /**
     * create new enrollments for a given list of courses.
     * these enrollments are later added to the enrollments-set.
     * <p>
     * author: Kevin Thaller
     *
     * @param courses a list of courses which should be turned into new enrollments.
     */
    public void initEnrollments(List<Course> courses) {
        for (Course course : courses) {
            enrollments.add(new Enrollment(this, course));
        }
    }

    /**
     * get the enrollments of this student.
     * <p>
     * author: Kevin Thaller
     *
     * @return the set of enrollments.
     */
    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * delete old enrollments from the set of enrollments.
     * this is used when the courses-of-study of a student changes and the old enrollments are no longer needed.
     * <p>
     * author: Kevin Thaller
     *
     * @param coursesOfStudy the object for comparing if an enrollment should be deleted or should be stored.
     */
    public void clearEnrollmentsOldCoursesOfStudy(CoursesOfStudy coursesOfStudy) {
        List<Enrollment> enrollmentsToRemove = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            boolean courseToRemove = true;
            for (int i = 0; i < coursesOfStudy.getCourses().size(); i++) {
                if (enrollment.course.getId() == coursesOfStudy.getCourses().get(i).getId()) {
                    courseToRemove = false;
                    break;
                }
            }
            if (courseToRemove) {
                enrollmentsToRemove.add(enrollment);
            }
        }
        enrollments.removeAll(enrollmentsToRemove);
    }

    /*
    public void rateStudent(Course course, Mark mark) {
        Enrollment temp = new Enrollment(this, course);
        enrollments.remove(temp);
        temp.setEnrollmentDate(LocalDateTime.now());
        temp.setGrade(mark.getMark());
    }

     */

    /**
     * get the count of passed, failed and open-exams.
     * <p>
     * author: Kevin Thaller
     *
     * @return an int-array which stores the count of passed, failed and open-exams.
     */
    public int[] getExamValues() {
        int passedExams = 0;
        int failedExams = 0;
        int openExams = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getGrade() == null) {
                openExams++;
            } else if (enrollment.getGrade() > 4.0) {
                failedExams++;
            } else {
                passedExams++;
            }
        }
        return new int[]{passedExams, failedExams, openExams};
    }
}