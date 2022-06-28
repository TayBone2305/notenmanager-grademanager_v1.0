package de.thdeg.grademanager.gui;

import de.thdeg.grademanager.JpaService;
import de.thdeg.grademanager.model.Course;
import de.thdeg.grademanager.model.CoursesOfStudy;
import de.thdeg.grademanager.model.enumeration.Semester;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.io.IOException;

import static de.thdeg.grademanager.gui.PopUpMessageHelper.popUpErrorMessage;

public class CoursesOfStudyDetailsController
{
    @FXML
    protected ComboBox<Semester> durationComboBox;
    ObservableList<Semester> durationList = FXCollections.observableArrayList(Semester.values());


    @FXML
    protected TextField name;
    @FXML
    protected TextField fieldOfStudy;
    @FXML
    protected TextField fees;
    @FXML
    protected ListView<Course> courseListView;
    static public ObservableList<Course> courseList = FXCollections.observableArrayList();


    private static CoursesOfStudy coursesOfStudy;

    /**
     * Hands over the course of study in order to get the attributes from the object.
     *
     * @param coursesOfStudy: course of study which attributes need to be shown
     *
     * author: Dennis Toth
     */
    public static void setCoursesOfStudy(CoursesOfStudy coursesOfStudy)
    {
        CoursesOfStudyDetailsController.coursesOfStudy = coursesOfStudy;
    }


    @FXML
    protected void initialize()
    {
        // Makes every field uneditable
        disableFields(true);

        // Filling ComboBox with values
        durationComboBox.setItems(durationList);

        /**
         * In the UI, more suitable Strings will be displayed to the user
         * by converting the actual values of the enum to these predefined states
         *
         * author: Tayfun Bönsch
         */
        durationComboBox.setConverter(new StringConverter<Semester>()
        {
            @Override
            public String toString(Semester semester)
            {
                switch(semester)
                {
                    case FIRST:
                        return "1";
                    case SECOND:
                        return "2";
                    case THIRD:
                        return "3";
                    case FOURTH:
                        return "4";
                    case FIFTH:
                        return "5";
                    case SIXTH:
                        return "6";
                    case SEVENTH:
                        return "7";
                    case EIGHTH:
                        return "8";
                    case NINTH:
                        return "9";
                    case TENTH:
                        return "10";
                    case ELEVENTH:
                        return "11";
                    case TWELFTH:
                        return "12";
                    case THIRTEENTH:
                        return "13";
                    case FOURTEENTH:
                        return "14";
                    default:
                        break;
                }

                return null;
            }

            @Override
            public Semester fromString(String string)
            {
                return null;
            }
        });

        // Filling TextFields, ComboBoxes and ListView with data
        if (coursesOfStudy != null)
        {
            name.setText(coursesOfStudy.getName());
            durationComboBox.setPromptText(Integer.toString(coursesOfStudy.getDuration()));
            fieldOfStudy.setText(coursesOfStudy.getFieldOfStudy());
            fees.setText(Integer.toString(coursesOfStudy.getFees()));

            JpaService jpaService = JpaService.getInstance();
            courseList.setAll(jpaService.getCoursesForCoursesOfStudyFromDb(coursesOfStudy));
            if (!courseList.isEmpty())
            {
                courseListView.setItems(courseList);
            }
        }
    }


    /**
     * Disables/Enables every field in order to show data or to modify data.
     *
     * @param value: Disables/Enables fields
     *
     * author: Dennis Toth
     */
    protected void disableFields(boolean value)
    {
        // Variables
        name.setDisable(value);
        fieldOfStudy.setDisable(value);
        fees.setDisable(true);

        // Buttons
        saveButton.setDisable(value);
        modifyButton.setDisable(!value);

        // ComboBoxes
        durationComboBox.setDisable(value);
    }


    @FXML
    protected Button modifyButton;
    @FXML
    protected void onModifyButtonClick()
    {
        disableFields(false);
    }


    @FXML
    protected Button saveButton;

    /**
     * Saves the changes made on CoursesOfStudy object.
     *
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void onSaveButtonClick() throws IOException
    {
        // Checks for blank input fields
        if (!name.getText().isBlank() && !fieldOfStudy.getText().isBlank())
        {
            coursesOfStudy.setName(name.getText());
            if(durationComboBox.getValue() != null)
            {
                coursesOfStudy.setDuration(durationComboBox.getValue().getSemester());
            }
            coursesOfStudy.setFieldOfStudy(fieldOfStudy.getText());

            // DB: Merging object
            JpaService jpaService = JpaService.getInstance();
            jpaService.runInTransaction(entityManager -> {entityManager.merge(coursesOfStudy); return null;});
            initialize();

            disableFields(true);
        }
        else
        {
            popUpErrorMessage("Fehler", "Bitte alle Felder ausfüllen.");
        }
    }


    /**
     * Lets you modify the course object.
     *
     * @param event: trigger this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void onCourseClick(MouseEvent event) throws IOException
    {
        // Hands over the course of study in order to link it with the appropriate course
        CourseModificationController.setCoursesOfStudy(coursesOfStudy);
        // Hands over the course that needs to be modified
        CourseModificationController.setCourse(courseListView.getSelectionModel().getSelectedItem());
        SwitchWindowHelper.switchTo("Course Modification", event);
    }


    /**
     * Switch to main scene.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void switchToMain(ActionEvent event) throws IOException
    {
        SwitchWindowHelper.switchTo("Main", event);
    }


    /**
     * Switch to course modification scene.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void switchToCourseModification(ActionEvent event) throws IOException
    {
        // Hands over the course of study
        CourseModificationController.setCoursesOfStudy(coursesOfStudy);
        SwitchWindowHelper.switchTo("Course Modification", event);
    }


    /**
     * Deletes the selected course of study.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void onRemoveButtonClick(ActionEvent event) throws IOException
    {
        // DB: removing object
        JpaService jpaService = JpaService.getInstance();
        jpaService.runInTransaction(entityManager -> {coursesOfStudy = entityManager.find(CoursesOfStudy.class,
                coursesOfStudy.getId()); entityManager.remove(coursesOfStudy); return null;});
        SwitchWindowHelper.switchTo("Main", event);
    }
}
