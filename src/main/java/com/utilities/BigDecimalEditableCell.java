package com.utilities;

import com.entity.Templates;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.BigDecimalStringConverter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.function.UnaryOperator;

public class BigDecimalEditableCell extends TableCell<Templates, BigDecimal> {

    private TextField textField;
    private TextFormatter<BigDecimal> textFormatter;

    public BigDecimalEditableCell() {
        textField = new TextField();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            String newText = c.getControlNewText();

            // always allow deleting all characters:
            if (newText.isEmpty()) {
                return c;
            }

            // otherwise, must have all digits:
            if (!newText.matches("\\d{0,4}[,.]?\\d{0,2}")) {
                return null;
            }

            try {
                BigDecimal value = new BigDecimal(NumberFormat.getNumberInstance().parse(newText).toString());
                if (value.compareTo(BigDecimal.ZERO) < 0) {
                    return null;
                } else {
                    return c;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        };
        BigDecimalStringConverter converter = new BigDecimalStringConverter();

        textFormatter = new TextFormatter<>(converter, BigDecimal.ZERO, filter);
        textField.setTextFormatter(textFormatter);

        textField.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });

        textField.setOnAction(e -> {
            try {
                commitEdit(converter.fromString(NumberFormat
                        .getNumberInstance().parse(textField.getText()).toString()));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });

        textProperty().bind(Bindings
                .when(emptyProperty())
                .then((String) null)
                .otherwise(itemProperty().asString(Locale.getDefault(), "%,.2f")));


        setGraphic(textField);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    protected void updateItem(BigDecimal value, boolean empty) {
        super.updateItem(value, empty);
        if (isEditing()) {
            textField.requestFocus();
            textField.selectAll();
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } else {
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        textFormatter.setValue(getItem());
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.requestFocus();
        textField.selectAll();
    }

    @Override
    public void commitEdit(BigDecimal newValue) {
        super.commitEdit(newValue);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

}
