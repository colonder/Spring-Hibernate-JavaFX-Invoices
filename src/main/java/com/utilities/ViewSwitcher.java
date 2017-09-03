package com.utilities;

import com.UI.controllers.IInitializableFromEntity;
import com.UI.controllers.IRefreshable;
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

    public static void openAndInitialize(Button button, AbstractFxmlView view, BaseAbstractEntity entity)
    {
        BorderPane parent = (BorderPane) button.getScene().getRoot();
        IInitializableFromEntity controller = (IInitializableFromEntity) view.getPresenter();
        controller.initializeFields(entity);
        parent.setCenter(view.getView());
    }

    public static void openAndInitialize(Button button, AbstractFxmlView view)
    {
        BorderPane parent = (BorderPane) button.getScene().getRoot();
        IRefreshable controller = (IRefreshable) view.getPresenter();
        controller.refresh();
        parent.setCenter(view.getView());
    }
}
