package de.thdeg.grademanager.gui;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;


/**
 * This class is used to reduce redundant code for popup messages.
 *
 * author: Dennis Toth
 */
public class PopUpMessageHelper {
    /**
     * Creates error popup message for better usability.
     *
     * @param title: title of popup message
     * @param description: description of popup message
     *
     * author: Dennis Toth
     */
    @FXML
    public static void popUpErrorMessage(String title, String description)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(description);
        alert.showAndWait();
    }


    /**
     * Creates confirmation popup message for better usability.
     *
     * @param scene: scene to switch to
     * @param event: triggers this method
     * @throws IOException
     *
     * author: Dennis Toth
     */
    @FXML
    public static void popUpConfirmationMessage(String scene, Event event)
            throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bestätigung erforderlich");
        alert.setHeaderText("Daten wurden nicht gespeichert. Änderungen verwerfen?");
        ButtonType buttonTypeOne = new ButtonType("Ja");
        ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeOne)
        {
            SwitchWindowHelper.switchTo(scene, event);
        }
    }
}
