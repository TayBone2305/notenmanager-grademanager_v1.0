package de.thdeg.grademanager.gui;

import de.thdeg.grademanager.JpaService;
import de.thdeg.grademanager.model.CoursesOfStudy;
import de.thdeg.grademanager.model.enumeration.Degree;
import de.thdeg.grademanager.model.enumeration.Mark;
import de.thdeg.grademanager.model.enumeration.Semester;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;

import static de.thdeg.grademanager.gui.PopUpMessageHelper.popUpConfirmationMessage;
import static de.thdeg.grademanager.gui.PopUpMessageHelper.popUpErrorMessage;

public class CoursesOfStudyModificationController
{
    @FXML
    protected ComboBox<Degree> degreeComboBox;
    @FXML
    protected ComboBox<Semester> durationComboBox;
    ObservableList<Degree> degreeList = FXCollections.observableArrayList(Degree.values());
    ObservableList<Semester> durationList = FXCollections.observableArrayList(Semester.values());


    @FXML
    protected void initialize()
    {
        // Filling ComboBoxes with values
        //degreeComboBox.getItems().setAll(Degree.values());
        degreeComboBox.setItems(degreeList);

        /**
         * In the UI, more suitable Strings will be displayed to the user
         * by converting the actual values of the enum to these predefined states
         *
         * author: Tayfun Bönsch
         */
        degreeComboBox.setConverter(new StringConverter<Degree>()
        {
            @Override
            public String toString(Degree degree)
            {
                switch(degree)
                {
                    case BACHELOR_OF_ARTS:
                        return "B.A.";
                    case BACHELOR_OF_ENGINEERING:
                        return "B.Eng.";
                    case BACHELOR_OF_SCIENCE:
                        return "B.Sc.";
                    case MASTER_OF_ARTS:
                        return "M.A.";
                    case MASTER_OF_ENGINEERING:
                        return "M.Eng.";
                    case MASTER_OF_SCIENCE:
                        return "M.Sc.";
                    default:
                        break;
                }

                return null;
            }

            @Override
            public Degree fromString(String string)
            {
                return null;
            }
        });

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
    }


    @FXML
    protected TextField name;
    @FXML
    protected Degree degree;
    @FXML
    protected Semester duration;
    @FXML
    protected int fees;
    @FXML
    protected TextField fieldOfStudy;


    /**
     * Creates a new course of study object.
     *
     * @param event: trigger this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException
    {
        // Checks for blank input fields
        if (!name.getText().isBlank() && degreeComboBox.getValue() != null && durationComboBox.getValue() != null &&
                !fieldOfStudy.getText().isBlank())
        {
            // Getting values from ComboBoxes
            degree = degreeComboBox.getValue();
            duration = durationComboBox.getValue();
            // Fees are always the same (= 62€) for every course of study
            fees = 62;

            CoursesOfStudy coursesOfStudy =new CoursesOfStudy(
                    name.getText(),
                    degree.getDegree(),
                    durationComboBox.getValue().getSemester(),
                    fees,
                    fieldOfStudy.getText());

            // DB: Persisting object
            JpaService jpaService = JpaService.getInstance();
            jpaService.runInTransaction(entityManager -> {entityManager.persist(coursesOfStudy); return null;});

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
