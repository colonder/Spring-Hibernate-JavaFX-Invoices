package com.utilities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class IntegerTextField extends TextField{

    private SimpleIntegerProperty property;

    public IntegerTextField()
    {
        super();
        property = new SimpleIntegerProperty();
        this.textProperty().bindBidirectional(property, new NumberStringConverter());
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
