package com.utilities;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.BigDecimalStringConverter;

import java.math.BigDecimal;

public class BigDecimalTextField extends TextField {
    public BigDecimalTextField()
    {
        super();
        this.setTextFormatter(new TextFormatter<>(new BigDecimalStringConverter()));
    }

    public BigDecimal getValue()
    {
        return new BigDecimal(this.getText());
    }
}
