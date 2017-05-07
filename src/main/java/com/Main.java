package com;

import com.UI.ProjectsView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
@Lazy
public class Main extends AbstractJavaFxApplicationSupport {

    private String windowTitle = "test";

    @Autowired
    ProjectsView projectsView;

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle(windowTitle);
        stage.setScene(new Scene(projectsView.getView()));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launchApp(Main.class, args);
    }
}