package com;

import com.UI.view.HomeView;
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

    @Autowired private RootView rootView;
    @Autowired private HomeView homeView;

    public static void main(String[] args) {
        launchApp(Main.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Simple invoices");
        BorderPane pane = (BorderPane) rootView.getView();
        pane.setCenter(homeView.getView());
        stage.setScene(new Scene(pane));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}