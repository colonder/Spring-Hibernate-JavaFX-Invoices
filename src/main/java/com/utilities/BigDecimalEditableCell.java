package com.utilities;

import com.entity.Templates;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.BigDecimalStringConverter;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

public class BigDecimalEditableCell extends TableCell<Templates, BigDecimal> {

    private BigDecimalTextField textField;
    private TextFormatter<BigDecimal> textFormatter;

    public BigDecimalEditableCell() {
        textField = new BigDecimalTextField();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            String newText = c.getControlNewText();

            // always allow deleting all characters:
            if (newText.isEmpty()) {
                return c;
            }

            // otherwise, must have all digits:
            if (!newText.matches("\\d{0,4}\\.?\\d{0,2}")) {
                return null;
            }

            // check range:
            BigDecimal value = new BigDecimal(newText);
            if (value.compareTo(BigDecimal.ZERO) == -1) {
                return null;
            } else {
                return c;
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

        textField.setOnAction(e -> commitEdit(converter.fromString(textField.getText())));

        textProperty().bind(Bindings
                .when(emptyProperty())
                .then((String) null)
                .otherwise(itemProperty().asString()));

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
