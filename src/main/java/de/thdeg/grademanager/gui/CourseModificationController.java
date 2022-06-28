package de.thdeg.grademanager.gui;

import de.thdeg.grademanager.JpaService;
import de.thdeg.grademanager.model.Course;
import de.thdeg.grademanager.model.CoursesOfStudy;
import de.thdeg.grademanager.model.enumeration.*;
import de.thdeg.grademanager.model.enumeration.CourseType;
import de.thdeg.grademanager.model.enumeration.ExamType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.IOException;

import static de.thdeg.grademanager.gui.PopUpMessageHelper.popUpConfirmationMessage;
import static de.thdeg.grademanager.gui.PopUpMessageHelper.popUpErrorMessage;

public class CourseModificationController
{
    @FXML
    protected ComboBox<Semester> semesterComboBox;
    @FXML
    protected ComboBox<CourseType> courseTypeComboBox;
    @FXML
    protected ComboBox<ExamType> examTypeComboBox;
    ObservableList<Semester> bachelorSemesterList = FXCollections.observableArrayList(Semester.values());
    ObservableList<CourseType> courseTypeList = FXCollections.observableArrayList(CourseType.values());
    ObservableList<ExamType> examTypeList = FXCollections.observableArrayList(ExamType.values());


    @FXML
    protected TextField name;
    @FXML
    protected Semester semester;
    @FXML
    protected CourseType courseType;
    @FXML
    protected TextField sws;
    @FXML
    protected TextField ects;
    @FXML
    protected ExamType examType;


    private static CoursesOfStudy coursesOfStudy;

    /**
     * Hands over the course of study in order to link it with the appropriate course.
     *
     * @param coursesOfStudy: course of study that needs to be linked with course
     *
     * author: Dennis Toth
     */
    public static void setCoursesOfStudy(CoursesOfStudy coursesOfStudy)
    {
        CourseModificationController.coursesOfStudy = coursesOfStudy;
    }


    private static boolean newCourse = true;
    private static Course course;

    /**
     * Hands over the course in order to modify it.
     *
     * @param course: course that needs to be modified
     *
     * author: Dennis Toth
     */
    public static void setCourse(Course course)
    {
        CourseModificationController.course = course;

        // In order to modify the course and not creating a new object, this variable needs to be false
        newCourse = false;
    }


    @FXML
    protected void initialize()
    {
        // Filling ComboBoxes with values
        semesterComboBox.setItems(bachelorSemesterList);

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

        courseTypeComboBox.setItems(courseTypeList);

        /**
         * In the UI, more suitable Strings will be displayed to the user
         * by converting the actual values of the enum to these predefined states
         *
         * author: Tayfun Bönsch
         */
        courseTypeComboBox.setConverter(new StringConverter<CourseType>()
        {
            @Override
            public String toString(CourseType courseType)
            {
                switch(courseType)
                {
                    case COMPULSORY:
                        return "FWP";
                    case ELECTIVE:
                        return "AWP";
                    case PLV:
                        return "PLV";
                    case REQUIRED:
                        return "Required";
                    case VOLUNTARY:
                        return "Voluntary";
                    default:
                        break;
                }

                return null;
            }

            @Override
            public CourseType fromString(String string)
            {
                return null;
            }
        });

        examTypeComboBox.setItems(examTypeList);

        /**
         * In the UI, more suitable Strings will be displayed to the user
         * by converting the actual values of the enum to these predefined states
         *
         * author: Tayfun Bönsch
         */
        examTypeComboBox.setConverter(new StringConverter<ExamType>()
        {
            @Override
            public String toString(ExamType examType)
            {
                switch(examType)
                {
                    case BACHELOR_THESIS:
                        return "BA";
                    case INTERNSHIP:
                        return "Pr";
                    case MASTER_THESIS:
                        return "MA";
                    case ORAL:
                        return "mdlP";
                    case PERFORMANCE_RECORD:
                        return "LN";
                    case PROOF_OF_PARTICIPATION:
                        return "TN";
                    case RESEARCH_PROJECT:
                        return "PstA";
                    case WRITTEN:
                        return "schrP";
                    default:
                        break;
                }

                return null;
            }

            @Override
            public ExamType fromString(String string)
            {
                return null;
            }
        });

        // Filling TextFields and ComboBoxes with data
        if (coursesOfStudy != null && course != null && !newCourse)
        {
            name.setText(course.getName());
            semesterComboBox.setPromptText(Integer.toString(course.getSemester()));
            courseTypeComboBox.setPromptText(course.getCourseType());
            sws.setText(Integer.toString(course.getSws()));
            ects.setText(Double.toString(course.getEcts()));
            examTypeComboBox.setPromptText(course.getExamType());
        }
    }


    /**
     * Creates a new course object or modifies the values of an existing course.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException
    {
        if(newCourse)
        {
            // Checks for blank input fields
            if (!name.getText().isBlank() && semesterComboBox.getValue() != null &&
                    courseTypeComboBox.getValue() != null && !sws.getText().isBlank() && !ects.getText().isBlank() &&
                    examTypeComboBox.getValue() != null)
            {

                // Getting values from ComboBoxes
                semester = semesterComboBox.getValue();
                courseType = courseTypeComboBox.getValue();
                examType = examTypeComboBox.getValue();

                // Catching false user input
                try {
                    // DB: Detaching object
                    JpaService jpaService = JpaService.getInstance();
                    coursesOfStudy = jpaService.getCoursesOfStudyFromDb(coursesOfStudy.getId());
                    jpaService.runInTransaction(entityManager -> {entityManager.detach(coursesOfStudy); return null;});

                    coursesOfStudy.addCourse(new Course(
                            name.getText(),
                            semester.getSemester(),
                            courseType.getCourseType(),
                            Integer.parseInt(sws.getText()),
                            Double.parseDouble(ects.getText()),
                            examType.getExamType(),
                            false)); //TODO: Add UI-Element for isCredited

                    // Important: Hibernate throws warning (bug) if this line is not here
                    System.out.println(coursesOfStudy.getCourses());

                    // DB: Merging object
                    jpaService.runInTransaction(entityManager -> {entityManager.merge(coursesOfStudy); return null;});

                    SwitchWindowHelper.switchTo("CoursesOfStudy Details", event);
                }
                catch (NumberFormatException e)
                {
                    popUpErrorMessage("Fehler", "Bitte Zahlen eingeben für SWS und ECTS.");
                }
            }
            else
            {
                popUpErrorMessage("Fehler", "Bitte alle Felder ausfüllen.");
            }
        }
        else
        {
            // Checks for blank input fields
            if(!name.getText().isBlank() && !sws.getText().isBlank() && !ects.getText().isBlank())
            {
                // DB: Detaching object
                JpaService jpaService = JpaService.getInstance();
                CoursesOfStudy cos = jpaService.getCoursesOfStudyFromDb(coursesOfStudy.getId());
                course = jpaService.getCourseFromDb(course.getId());
                jpaService.runInTransaction(entityManager -> {entityManager.detach(cos); return null;});

                try {
                    // Filling TextFields and ComboBoxes with data
                    course.setName(name.getText());
                    if (semesterComboBox.getValue() != null) {
                        semester = semesterComboBox.getValue();
                        course.setSemester(semester.getSemester());
                    }
                    if (courseTypeComboBox.getValue() != null) {
                        courseType = courseTypeComboBox.getValue();
                        course.setCourseType(courseType.getCourseType());
                    }
                    course.setSws(Integer.parseInt(sws.getText()));
                    course.setEcts(Double.parseDouble(ects.getText()));
                    if (examTypeComboBox.getValue() != null) {
                        examType = examTypeComboBox.getValue();
                        course.setExamType(examType.getExamType());
                    }

                    // DB: Merging object
                    cos.updateCourse(course);
                    jpaService.runInTransaction(entityManager -> {
                        entityManager.merge(cos);
                        return null;
                    });
                    initialize();

                    // Static variable needs to be set to true
                    newCourse = true;
                    SwitchWindowHelper.switchTo("CoursesOfStudy Details", event);
                }
                catch (NumberFormatException e)
                {
                    popUpErrorMessage("Fehler", "Bitte Zahlen eingeben für SWS und ECTS.");
                }
            }
            else
            {
                popUpErrorMessage("Fehler", "Bitte alle Felder ausfüllen.");
            }
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
    protected void abortAndSwitchToCoursesOfStudyDetails(ActionEvent event) throws IOException
    {
        // Static variable needs to be set to true
        newCourse = true;

        popUpConfirmationMessage("CoursesOfStudy Details", event);
    }
}