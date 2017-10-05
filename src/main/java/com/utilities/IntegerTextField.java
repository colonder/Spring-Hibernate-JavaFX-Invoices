package com.utilities;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class IntegerTextField extends TextField{

    public IntegerTextField()
    {
        super();
        this.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
    }

    public int getValue()
    {
        return Integer.parseInt(this.getText());
    }
}
