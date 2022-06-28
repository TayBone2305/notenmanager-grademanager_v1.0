package de.thdeg.grademanager.gui;

import de.thdeg.grademanager.JpaService;
import de.thdeg.grademanager.model.Student;
import de.thdeg.grademanager.model.enumeration.Gender;
import de.thdeg.grademanager.model.enumeration.Semester;
import de.thdeg.grademanager.model.enumeration.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.IOException;

import static de.thdeg.grademanager.gui.PopUpMessageHelper.popUpConfirmationMessage;
import static de.thdeg.grademanager.gui.PopUpMessageHelper.popUpErrorMessage;


public class StudentModificationController
{
    @FXML
    protected ComboBox<Gender> genderComboBox;
    @FXML
    protected ComboBox<Semester> semesterComboBox;
    @FXML
    protected ComboBox<Status> statusComboBox;
    ObservableList<Gender> genderList = FXCollections.observableArrayList(Gender.values());
    ObservableList<Semester> semesterList = FXCollections.observableArrayList(Semester.values());
    ObservableList<Status> statusList = FXCollections.observableArrayList(Status.values());


    @FXML
    protected void initialize()
    {
        // Filling ComboBoxes with values
        // working with FXML
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

        statusComboBox.setItems(statusList);

        /**
         * In the UI, more suitable Strings will be displayed to the user
         * by converting the actual values of the enum to these predefined states
         *
         * author: Tayfun Bönsch
         */
        statusComboBox.setConverter(new StringConverter<Status>()
        {
            @Override
            public String toString(Status status)
            {
                //return Status.getStatus();
                switch(status)
                {
                    case ENROLLED:
                        return "Enrolled";
                    case WITHDRAWAL:
                        return "Withdrawing";
                    case DISMISSAL:
                        return "Dismissed";
                    default:
                        break;
                }

                return null;
            }

            @Override
            public Status fromString(String string)
            {
                //return Status.valueOf(string);
                return null;
            }
        });
    }


    @FXML
    protected Gender gender;
    @FXML
    protected TextField firstName;
    @FXML
    protected TextField lastName;
    @FXML
    protected TextField placeOfResidence;
    @FXML
    protected TextField birthPlace;
    @FXML
    protected TextField officialEmail;
    @FXML
    protected TextField privateEmail;
    @FXML
    protected Status status;
    @FXML
    protected boolean paidFees;
    @FXML
    protected Semester semester;
    @FXML
    protected RadioButton yes;
    @FXML
    protected RadioButton no;

    /**
     * Creates a new student object.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException
    {
        // Checks for blank input fields
        if (genderComboBox.getValue() != null && !firstName.getText().isBlank() && !lastName.getText().isBlank() &&
                !placeOfResidence.getText().isBlank() && !birthPlace.getText().isBlank() &&
                !officialEmail.getText().isBlank() && !privateEmail.getText().isBlank() &&
                statusComboBox.getValue() != null && (yes.isSelected() || no.isSelected()) &&
                semesterComboBox.getValue() != null)
        {
            // Getting values from ComboBoxes and RadioButton
            gender = genderComboBox.getValue();
            paidFees = yes.isSelected();
            status = statusComboBox.getValue();
            semester = semesterComboBox.getValue();

            Student student = new Student(
                    gender.getGender(),
                    firstName.getText(),
                    lastName.getText(),
                    placeOfResidence.getText(),
                    birthPlace.getText(),
                    officialEmail.getText(),
                    privateEmail.getText(),
                    status.getStatus(),
                    paidFees,
                    semester.getSemester()
            );

            // DB: Persisting object
            JpaService jpaService = JpaService.getInstance();
            jpaService.runInTransaction(entityManager -> {entityManager.persist(student); return null;});

            SwitchWindowHelper.switchTo("Main", event);
        }
        else
        {
            popUpErrorMessage("Fehler", "Bitte alle Felder ausfüllen.");
        }
    }


    /**
     * Discards every user input and switches one page back.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void abortAndSwitchToMain(ActionEvent event) throws IOException
    {
        popUpConfirmationMessage("Main", event);
    }
}
