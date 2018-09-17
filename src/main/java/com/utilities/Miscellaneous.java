package com.utilities;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Miscellaneous {

    public static <T> Callback<TableColumn<T, BigDecimal>, TableCell<T, BigDecimal>> getCellFactory() {
        return new Callback<TableColumn<T, BigDecimal>, TableCell<T, BigDecimal>>() {
            @Override
            public TableCell<T, BigDecimal> call(final TableColumn<T, BigDecimal> param) {
                return new TableCell<T, BigDecimal>() {
                    @Override
                    public void updateItem(BigDecimal v, boolean empty) {
                        super.updateItem(v, empty);
                        if (empty || v == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setText(NumberFormat.getNumberInstance().format(v));
                        }
                    }
                };
            }
        };
    }
}
