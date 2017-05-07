package com;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;

@SpringBootApplication
@Lazy
public class Main extends AbstractJavaFxApplicationSupport {

    private String windowTitle = "test";
    private BorderPane rootLayout;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception
    {
        this.stage = stage;
        this.stage.setTitle(windowTitle);
        initRootLayout();
        loadMainWindow();
    }

    private void loadMainWindow()
    {
        try
        {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/UI/view/MainWindowView.fxml"));
            AnchorPane mainView = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(mainView);
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void initRootLayout()
    {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/UI/view/rootView.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launchApp(Main.class, args);
    }
}