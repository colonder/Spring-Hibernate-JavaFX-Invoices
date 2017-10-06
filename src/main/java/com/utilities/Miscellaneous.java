package com.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

public class Miscellaneous {

    public static BigDecimal ONE_HUNDREDTH = new BigDecimal(0.01);

    public static void showConstraintAlert()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred while saving object to the database");
        alert.setContentText("It seems that the object with identical data already exist in the database.");
        alert.showAndWait();
    }

    public static String getTextFromControl(TextField textField)
    {
        if (textField.getText().isEmpty())
            return null;
        return textField.getText();
    }
}
