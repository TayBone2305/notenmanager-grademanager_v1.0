package de.thdeg.grademanager.gui;

import de.thdeg.grademanager.JpaService;
import de.thdeg.grademanager.model.CoursesOfStudy;
import de.thdeg.grademanager.model.Student;
import de.thdeg.grademanager.model.enumeration.Gender;
import de.thdeg.grademanager.model.enumeration.Mark;
import de.thdeg.grademanager.model.enumeration.Semester;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.IOException;

import static de.thdeg.grademanager.gui.MainController.coursesOfStudyList;
import static de.thdeg.grademanager.gui.PopUpMessageHelper.popUpErrorMessage;

public class StudentDetailsController
{
    @FXML
    protected ComboBox<CoursesOfStudy> coursesOfStudyComboBox;
    @FXML
    protected ComboBox<Gender> genderComboBox;
    @FXML
    protected ComboBox<Semester> semesterComboBox;
    ObservableList<Gender> genderList = FXCollections.observableArrayList(Gender.values());
    ObservableList<Semester> semesterList = FXCollections.observableArrayList(Semester.values());


    @FXML
    protected TextField firstName;
    @FXML
    protected TextField lastName;
    @FXML
    protected TextField placeOfResidence;
    @FXML
    protected TextField birthPlace;

    @FXML
    protected TextField coursesOfStudy;

    @FXML
    protected TextField studentID;
    @FXML
    protected TextField officialEmail;


    private static Student student;

    /**
     * Hands over the student in order to modify it.
     *
     * @param student: student that needs to be modified
     *
     * author: Dennis Toth
     */
    public static void setStudent(Student student) {
        StudentDetailsController.student = student;
    }


    @FXML
    protected void initialize()
    {
        // Makes every field uneditable
        disableFields(true);

        // Filling ComboBoxes with values
        genderComboBox.setItems(genderList);

        /**
         * In the UI, more suitable Strings will be displayed to the user
         * by converting the actual values of the enum to these predefined states
         *
         * author: Tayfun Bönsch
         */
        genderComboBox.setConverter(new StringConverter<Gender>()
        {
            @Override
            public String toString(Gender gender)
            {
                //return gender.getGender();
                switch(gender)
                {
                    case MALE:
                        return "Male";
                    case FEMALE:
                        return "Female";
                    case OTHER:
                        return "Other";
                    default:
                        break;
                }

                return null;
            }

            @Override
            public Gender fromString(String string)
            {
                //return Gender.valueOf(string);
                return null;
            }
        });

        semesterComboBox.setItems(semesterList);

        /**
         * In the UI, more suitable Strings will be displayed to the user
         * by converting the actual values of the enum to these predefined states
         *
         * author: Tayfun Bönsch
         */
        semesterComboBox.setConverter(new StringConverter<Semester>()
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

        // Filling TextFields and ComboBoxes with data
        if (student != null)
        {
            genderComboBox.setPromptText(student.getGender());
            firstName.setText(student.getFirstName());
            lastName.setText(student.getLastName());
            placeOfResidence.setText(student.getPlaceOfResidence());
            birthPlace.setText(student.getBirthPlace());
            if (student.getCoursesOfStudy() != null)
            {
                coursesOfStudy.setText(student.getCoursesOfStudy().getName());
            }
            semesterComboBox.setPromptText(Integer.toString(student.getSemester()));
            officialEmail.setText(student.getOfficialEmail());
            studentID.setText(Integer.toString(student.getId()));
            coursesOfStudyComboBox.setItems(coursesOfStudyList);
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
        firstName.setDisable(value);
        lastName.setDisable(value);
        placeOfResidence.setDisable(value);
        birthPlace.setDisable(value);
        officialEmail.setDisable(value);

        // Buttons
        saveButton.setDisable(value);
        modifyButton.setDisable(!value);

        // ComboBoxes
        genderComboBox.setDisable(value);
        semesterComboBox.setDisable(value);
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
     * Saves the changes made on student object.
     *
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void onSaveButtonClick() throws IOException
    {
        // Checks for blank input fields
        if (!firstName.getText().isBlank() && !lastName.getText().isBlank() &&
                !placeOfResidence.getText().isBlank() && !birthPlace.getText().isBlank() &&
                !officialEmail.getText().isBlank())
        {
            if(genderComboBox.getValue() != null)
            {
                student.setGender(genderComboBox.getValue().getGender());
            }
            student.setFirstName(firstName.getText());
            student.setLastName(lastName.getText());
            student.setPlaceOfResidence(placeOfResidence.getText());
            student.setBirthPlace(birthPlace.getText());
            if(semesterComboBox.getValue() != null)
            {
                student.setSemester(semesterComboBox.getValue().getSemester());
            }
            student.setOfficialEmail(officialEmail.getText());

            // DB: Merging object
            JpaService jpaService = JpaService.getInstance();
            jpaService.runInTransaction(entityManager -> {entityManager.merge(student); return null;});
            initialize();

            disableFields(true);
        } else {
            popUpErrorMessage("Fehler", "Bitte alle Felder ausfüllen.");
        }
    }


    /**
     * Assigns a course of study to a specific student.
     *
     * @param event: triggers this method
     *
     * author: Kevin Thaller
     */
    @FXML
    protected void assignCoursesOfStudy(ActionEvent event)
    {
        if (student != null && coursesOfStudyComboBox.getValue() != null && event.getSource() == coursesOfStudyComboBox)
        {
            student.setCoursesOfStudy(coursesOfStudyComboBox.getValue());
            // DB: Merging object
            JpaService jpaService = JpaService.getInstance();
            jpaService.runInTransaction(entityManager -> {entityManager.merge(student); return null;});
            initialize();
        }
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
        // Setting static variable to null
        student = null;
        SwitchWindowHelper.switchTo("Main", event);
    }


    /**
     * Switch to student courses scene.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void switchToStudentCourses(ActionEvent event) throws IOException
    {
        // Hands over the student
        StudentCoursesController.setStudent(student);
        SwitchWindowHelper.switchTo("Student Courses", event);
    }


    /**
     * Switch to student stats scene.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void switchToStudentStats(ActionEvent event) throws IOException
    {
        // Hands over the student
        StudentStatsController.setStudent(student);
        SwitchWindowHelper.switchTo("Student Stats", event);

    }


    /**
     * Deletes the selected student.
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
        jpaService.runInTransaction(entityManager -> {student = entityManager.find(Student.class, student.getId());
            entityManager.remove(student); return null;});
        SwitchWindowHelper.switchTo("Main", event);
    }
}
