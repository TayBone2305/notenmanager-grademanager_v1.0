package de.thdeg.grademanager.gui;

import de.thdeg.grademanager.JpaService;
import de.thdeg.grademanager.model.CoursesOfStudy;
import de.thdeg.grademanager.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class MainController
{
    @FXML
    protected ComboBox<CoursesOfStudy> coursesOfStudyComboBox;
    @FXML
    protected ComboBox<Student> studentComboBox;
    static public ObservableList<CoursesOfStudy> coursesOfStudyList = FXCollections.observableArrayList();
    static public ObservableList<Student> studentList = FXCollections.observableArrayList();


    @FXML
    protected void initialize()
    {
        JpaService jpaService = JpaService.getInstance();
        coursesOfStudyList.setAll(jpaService.getCoursesOfStudyFromDb());
        studentList.setAll(jpaService.getStudentsFromDb());

        if (!coursesOfStudyList.isEmpty())
        {
            coursesOfStudyComboBox.setItems(coursesOfStudyList);
        }
        if (!studentList.isEmpty())
        {
            studentComboBox.setItems(studentList);
        }
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
        StudentDetailsController.setStudent(studentComboBox.getValue());
        SwitchWindowHelper.switchTo("Student Details", event);
    }


    /**
     * Switch to student modification scene.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void switchToStudentModification(ActionEvent event) throws IOException
    {
        SwitchWindowHelper.switchTo("Student Modification", event);
    }


    /**
     * Switch to course of study details scene.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void switchToCoursesOfStudyDetails(ActionEvent event) throws IOException
    {
        // Hands over the course of study
        CoursesOfStudyDetailsController.setCoursesOfStudy(coursesOfStudyComboBox.getValue());
        SwitchWindowHelper.switchTo("CoursesOfStudy Details", event);
    }


    /**
     * Switch to course of study modification scene.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void switchToCoursesOfStudyModification(ActionEvent event) throws IOException
    {
        SwitchWindowHelper.switchTo("CoursesOfStudy Modification", event);
    }
}
