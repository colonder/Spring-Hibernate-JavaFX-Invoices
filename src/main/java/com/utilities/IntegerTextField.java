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

    public Integer getValue()
    {
        if (this.getText().isEmpty())
            return null;

        return Integer.parseInt(this.getText());
    }
}
