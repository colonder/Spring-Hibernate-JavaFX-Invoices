package com.utilities;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.BigDecimalStringConverter;

import java.math.BigDecimal;

public class BigDecimalTextField extends TextField {

    private SimpleObjectProperty<BigDecimal> value;

    public BigDecimalTextField()
    {
        super();
        this.setTextFormatter(new TextFormatter<>(new BigDecimalStringConverter()));
        this.value = new SimpleObjectProperty<>();
    }

    public BigDecimal getValue()
    {
         return this.value.get();
    }

    public void setValue(BigDecimal val)
    {
        this.setText(String.valueOf(val));
        this.value.set(val);
    }
}
