package de.thdeg.grademanager.gui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * This class is used to reduce redundant code for switching gui-windows.
 *
 * author: Kevin Thaller
 */
public class SwitchWindowHelper
{
    /**
     * This method is used to "switchTo" a certain window.
     **
     * @param windowTitle: the string of the new switched to window.
     * @param event: the event which triggers the window change.
     * @throws IOException
     *
     * author: Kevin Thaller
     */
    @FXML
    public static void switchTo(String windowTitle, Event event) throws IOException
    {
        FXMLLoader root = new FXMLLoader(Main.class.getResource(getResourceName(windowTitle)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load(), 1000, 600);
        if (stage != null)
        {
            stage.setTitle(windowTitle);
            stage.setScene(scene);
            stage.show();
        }
    }


    /**
     * Get the JavaFX-Gui-File for a given window-title.
     *
     * @param windowTitle: the string of the new-window.
     * @return the string of the JavaFX-Gui-File for a given window-title.
     * If the window-title isn't found an empty string will be returned.
     *
     * author: Kevin Thaller
     */
    private static String getResourceName(String windowTitle)
    {
        switch (windowTitle)
        {
            case "Main":
                return "main.fxml";
            case "Student Details":
                return "student-details.fxml";
            case "Student Modification":
                return "student-modification.fxml";
            case "CoursesOfStudy Modification":
                return "courses-of-study-modification.fxml";
            case "CoursesOfStudy Details":
                return "courses-of-study-details.fxml";
            case "Course Modification":
                return "course-modification.fxml";
            case "Student Stats":
                return "student-stats.fxml";
            case "Student Courses":
                return "student-courses.fxml";
            case "Student Grades":
                return "student-grades.fxml";
        }
        return "";
    }
}
