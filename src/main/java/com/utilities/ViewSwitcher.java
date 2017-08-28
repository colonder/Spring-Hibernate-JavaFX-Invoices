package com.utilities;

import com.UI.view.AbstractFxmlView;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class ViewSwitcher {
    public static void openView(Button button, AbstractFxmlView view)
    {
        BorderPane parent = (BorderPane) button.getScene().getRoot();
        parent.setCenter(view.getView());
    }
}
