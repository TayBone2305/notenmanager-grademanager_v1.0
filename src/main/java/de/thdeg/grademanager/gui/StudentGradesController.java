package de.thdeg.grademanager.gui;

import de.thdeg.grademanager.JpaService;
import de.thdeg.grademanager.model.Course;
import de.thdeg.grademanager.model.Enrollment;
import de.thdeg.grademanager.model.Student;
import de.thdeg.grademanager.model.enumeration.Mark;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import static de.thdeg.grademanager.gui.PopUpMessageHelper.popUpErrorMessage;

public class StudentGradesController
{
    @FXML
    protected ComboBox<Mark> markComboBox;
    ObservableList<Mark> markList = FXCollections.observableArrayList(Mark.values());


    protected Mark mark;
    protected LocalDate date;
    @FXML
    protected DatePicker dateBox;


    private static Student student;

    /**
     * Hands over the student in order to assign grades.
     *
     * @param student: student object
     *
     * author Dennis Toth
     */
    public static void setStudent(Student student)
    {
        StudentGradesController.student = student;
    }


    private static Course course;

    /**
     * Hands over the course in order to assign grades.
     *
     * @param course: course object
     *
     * author: Dennis Toth
     */
    public static void setCourse(Course course)
    {
        StudentGradesController.course = course;
    }


    @FXML
    protected void initialize()
    {
        // Filling ComboBox with values
        markComboBox.setItems(markList);

        /**
         * In the UI, more suitable Strings will be displayed to the user
         * by converting the actual values of the enum to these predefined states
         *
         * author: Tayfun Bönsch
         */
        markComboBox.setConverter(new StringConverter<Mark>()
        {
            @Override
            public String toString(Mark mark)
            {
                switch(mark)
                {
                    case EINS_NULL:
                        return "1.0";
                    case EINS_DREI:
                        return "1.3";
                    case EINS_SIEBEN:
                        return "1.7";
                    case ZWEI_NULL:
                        return "2.0";
                    case ZWEI_DREI:
                        return "2.3";
                    case ZWEI_SIEBEN:
                        return "2.7";
                    case DREI_NULL:
                        return "3.0";
                    case DREI_DREI:
                        return "3.3";
                    case DREI_SIEBEN:
                        return "3.7";
                    case VIER_NULL:
                        return "4.0";
                    case FUENF_NULL:
                        return "5.0";
                    case SECHS_NULL:
                        return "6.0";
                    case SIEBEN_NULL:
                        return "7.0";
                    default:
                        break;
                }

                return null;
            }

            @Override
            public Mark fromString(String string)
            {
                return null;
            }
        });

        // Getting enrollments of specific student
        Set<Enrollment> enrollments = student.getEnrollments();
        for (Enrollment e: enrollments)
        {
            if(course.getId() == e.getCourse().getId())
            {
                if(e.getGrade() != null)
                {
                    markComboBox.setValue(Mark.getValue(e.getGrade()));
                }
                if (e.getEnrollmentDate() != null)
                {
                    dateBox.setValue(e.getEnrollmentDate());
                }
            }
        }
    }


    /**
     * Saves the changes made on course grades.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException
    {
        if(markComboBox.getValue() != null && dateBox.getValue() != null)
        {
            mark = markComboBox.getValue();
            date = dateBox.getValue();
            for (Enrollment e : student.getEnrollments())
            {
                if (course.getId() == e.getCourse().getId())
                {
                    e.setGrade(mark.getMark());
                    e.setEnrollmentDate(date);

                    // DB: Merging object
                    JpaService jpaService = JpaService.getInstance();
                    jpaService.runInTransaction(entityManager -> {entityManager.merge(e); return null;});
                    initialize();
                    break;
                }
            }
            SwitchWindowHelper.switchTo("Student Courses", event);
        }
        else
        {
            popUpErrorMessage("Fehler", "Bitte alle Felder ausfüllen.");
        }
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
        SwitchWindowHelper.switchTo("Student Courses", event);
    }
}
