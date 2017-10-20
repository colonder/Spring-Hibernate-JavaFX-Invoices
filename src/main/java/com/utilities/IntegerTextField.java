package com.utilities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.NumberStringConverter;

import java.util.regex.Pattern;

public class IntegerTextField extends TextField{

    private SimpleIntegerProperty property;

    public IntegerTextField()
    {
        super();
        property = new SimpleIntegerProperty();
        this.textProperty().bindBidirectional(property, new NumberStringConverter());
        Pattern pattern = Pattern.compile("\\d*");
        this.setTextFormatter(new TextFormatter<>(change -> pattern.matcher(change.getControlNewText())
                .matches() ? change : null));
    }

    public int getValue()
    {
        return property.getValue();
    }

    public void setValue(int val)
    {
        try {
            this.property.set(val);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Conversion error");
            alert.setHeaderText("An error occurred while entering the value");
            alert.setContentText("It seems that the you have entered wrong type of the value");
            alert.showAndWait();
        }
    }

    public SimpleIntegerProperty propertyProperty() {
        return property;
    }
}
