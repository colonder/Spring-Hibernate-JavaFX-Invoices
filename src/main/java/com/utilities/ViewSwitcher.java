package com.utilities;

import com.Main;
import com.UI.controllers.IInitializableFromEntity;
import com.UI.controllers.IRefreshable;
import com.UI.view.AbstractFxmlView;
import com.entity.BaseAbstractEntity;

public class ViewSwitcher {
    public static void openView(AbstractFxmlView view)
    {
        Main.rootPane.setCenter(view.getView());
    }

    public static void openAndInitialize(AbstractFxmlView view, BaseAbstractEntity entity)
    {
        IInitializableFromEntity controller = (IInitializableFromEntity) view.getPresenter();
        controller.initializeFields(entity);
        Main.rootPane.setCenter(view.getView());
    }

    public static void openAndInitialize(AbstractFxmlView view)
    {
        IRefreshable controller = (IRefreshable) view.getPresenter();
        controller.refresh();
        Main.rootPane.setCenter(view.getView());
    }
}
