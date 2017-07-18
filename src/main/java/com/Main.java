package com;

import com.UI.view.MainWindowView;
import com.UI.view.RootView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
@Lazy
public class Main extends AbstractJavaFxApplicationSupport {

    @Autowired
    private RootView rootView;

    @Autowired
    private MainWindowView mainWindowView;

    public static void main(String[] args) {
        launchApp(Main.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane rootLayout = (BorderPane) rootView.getView();
        rootLayout.setCenter(mainWindowView.getView());
        stage.setTitle("test");
        stage.setScene(new Scene(rootLayout));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}