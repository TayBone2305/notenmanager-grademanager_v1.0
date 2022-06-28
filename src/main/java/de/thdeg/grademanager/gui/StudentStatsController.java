package de.thdeg.grademanager.gui;

import de.thdeg.grademanager.PdfService;
import de.thdeg.grademanager.model.Enrollment;
import de.thdeg.grademanager.model.EnrollmentProperty;
import de.thdeg.grademanager.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class StudentStatsController
{
    private static final String[] colors = {"#57bf08", "#fc0303", "#860061"};

    @FXML
    protected Text headLine;
    @FXML
    protected TableView<EnrollmentProperty> enrollmentTableView;
    @FXML
    protected TableColumn<EnrollmentProperty, String> courseColumn;
    @FXML
    protected TableColumn<EnrollmentProperty, Double> gradeColumn;
    @FXML
    protected TextField studentId;
    @FXML
    protected TextField courseOfStudy;
    @FXML
    protected TextField semester;
    @FXML
    protected PieChart statsDiagram;
    @FXML
    protected Label passedExams;
    @FXML
    protected Label failedExams;
    @FXML
    protected Label openExams;
    @FXML
    protected TextField average;
    @FXML
    protected TextField median;


    private static Student student;

    /**
     * Hands over the student in order to generate the statistics.
     * @param student: student object
     *
     * author: Kevin Thaller
     */
    public static void setStudent(Student student)
    {
        StudentStatsController.student = student;
    }


    @FXML
    protected void initialize()
    {
        if (student != null)
        {
            headLine.setText("Notenblatt " + student.getFirstName() + " " + student.getLastName());
            initEnrollmentTable();
            initPieChart();
            courseOfStudy.setText(student.getCoursesOfStudy().getName());
            semester.setText(Integer.toString(student.getSemester()));
            studentId.setText(Integer.toString(student.getId()));
            try {
                average.setText(Double.toString(student.calculateAverage()));
                median.setText(Integer.toString(student.calculateMedian()));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    /**
     * Initializes the enrollment tableview with the students enrollments.
     *
     * author: Kevin Thaller
     */
    private void initEnrollmentTable()
    {
        ObservableList<EnrollmentProperty> enrollmentObservableList = FXCollections.observableArrayList();
        for (Enrollment enrollment : student.getEnrollments())
        {
            enrollmentObservableList.add(new EnrollmentProperty(enrollment));

        }
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseProp"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("gradeProp"));
        enrollmentTableView.getItems().addAll(enrollmentObservableList);
    }


    /**
     * Initializes the piechart with the students exam values as well as colors the labels below the piechart.
     *
     * author: Kevin Thaller
     */
    private void initPieChart()
    {
        Label[] labels = {passedExams, failedExams, openExams};
        int[] examValues = student.getExamValues();

        ObservableList<PieChart.Data> examStatusPieChart = FXCollections.observableArrayList(
                new PieChart.Data("Bestanden", examValues[0]),
                new PieChart.Data("Offen", examValues[1]),
                new PieChart.Data("Durchgefallen", examValues[2])
        );

        statsDiagram.setData(examStatusPieChart);
        for (int i = 0; i < examStatusPieChart.size(); i++)
        {
            examStatusPieChart.get(i).getNode().setStyle("-fx-pie-color: " + colors[i] + ";");
            labels[i].setTextFill(Paint.valueOf(colors[i]));
            labels[i].setText(labels[i].getText() + " " + examValues[i]);
        }
    }


    /**
     * This method is called when the user clicks on the pdf-export-button.
     * It opens a directory-chooser to select the directory where a pdf-file of the students attributes should be
     * stored.
     *
     * author: Kevin Thaller
     *
     */
    @FXML
    protected void savePdf()
    {
        Stage primaryStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        if (selectedDirectory != null)
        {
            try {
                PdfService.writePDF(selectedDirectory.getAbsolutePath(), student);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    /**
     * This method is used to navigate to the main-window.
     *
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Kevin Thaller
     */
    @FXML
    protected void switchToMain(ActionEvent event) throws IOException
    {
        SwitchWindowHelper.switchTo("Main", event);
    }
}
