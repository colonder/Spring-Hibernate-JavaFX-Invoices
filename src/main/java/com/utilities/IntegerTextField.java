package com.utilities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class IntegerTextField extends TextField{

    private SimpleIntegerProperty property;

    public IntegerTextField()
    {
        super();
        this.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        property = new SimpleIntegerProperty();
        this.focusedProperty().addListener(observable -> {
            if (!this.getText().isEmpty()) {
                try {
                    this.setValue(Integer.parseInt(this.getText()));
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Conversion error");
                    alert.setHeaderText("An error occurred while entering the value");
                    alert.setContentText("It seems that the you have entered wrong type of the value");
                    alert.showAndWait();
                }
            }
        });
    }

    public Integer getValue()
    {
        return property.getValue();
    }

    public void setValue(Integer val)
    {
        this.property.set(val);
        this.setText(String.valueOf(val));
    }
}
