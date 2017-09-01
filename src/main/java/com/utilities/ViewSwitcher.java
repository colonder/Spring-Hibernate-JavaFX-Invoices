package com.utilities;

import com.UI.controllers.AbstractInitializableController;
import com.UI.view.AbstractFxmlView;
import com.entity.BaseAbstractEntity;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class ViewSwitcher {
    public static void openView(Button button, AbstractFxmlView view)
    {
        BorderPane parent = (BorderPane) button.getScene().getRoot();
        parent.setCenter(view.getView());
    }

    public static void openView(Button button, AbstractFxmlView view, BaseAbstractEntity entity)
    {
        BorderPane parent = (BorderPane) button.getScene().getRoot();
        AbstractInitializableController controller = (AbstractInitializableController) view.getPresenter();
        controller.initializeFields(entity);
        parent.setCenter(view.getView());
    }
}
