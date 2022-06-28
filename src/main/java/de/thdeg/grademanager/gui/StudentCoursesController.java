package de.thdeg.grademanager.gui;

import de.thdeg.grademanager.JpaService;
import de.thdeg.grademanager.model.Course;
import de.thdeg.grademanager.model.Student;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class StudentCoursesController
{
    @FXML
    protected ListView<Course> coursesListView;


    private static Student student;

    /**
     * Hands over the student in order to get the courses from the object.
     *
     * @param student: student object
     *
     * author: Dennis Toth
     */
    public static void setStudent(Student student)
    {
        StudentCoursesController.student = student;
    }


    @FXML
    protected void initialize()
    {
        if (student.getCoursesOfStudy() != null)
        {
            coursesListView.setItems(FXCollections.observableList(student.getCoursesOfStudy().getCourses()));
        }

        //DB: Initializes the enrollments table
        initEnrollmentsForCoursesOfStudy();
    }


    /**
     * Initializes the enrollments' table for course of study
     *
     * author: Kevin Thaller
     */
    private void initEnrollmentsForCoursesOfStudy()
    {
        JpaService jpaService = JpaService.getInstance();
        List<Course> courses = jpaService.getCoursesForCoursesOfStudyFromDb(student.getCoursesOfStudy());
        Student s = jpaService.getStudentFromDb(student.getId());
        jpaService.runInTransaction(entityManager -> {entityManager.detach(s); return null;});
        s.clearEnrollmentsOldCoursesOfStudy(s.getCoursesOfStudy());
        s.initEnrollments(courses);
        jpaService.runInTransaction(entityManager -> {entityManager.merge(s); return null;});
    }


    /**
     * Lets you allocate grades and a datum for every course.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void onCourseClick(MouseEvent event) throws IOException
    {
        // Hands over the student
        StudentGradesController.setStudent(student);
        // Hands over the course
        StudentGradesController.setCourse(coursesListView.getSelectionModel().getSelectedItem());
        SwitchWindowHelper.switchTo("Student Grades", event);
    }


    /**
     * Switch to student details scene.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void switchToStudentDetails(ActionEvent event) throws IOException
    {
        // Hands over the student
        StudentDetailsController.setStudent(student);
        SwitchWindowHelper.switchTo("Student Details", event);
    }
}
