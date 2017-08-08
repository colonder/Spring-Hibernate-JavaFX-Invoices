package com;

import com.UI.view.RootView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
@Lazy
public class Main extends AbstractJavaFxApplicationSupport {

    @Autowired
    private RootView rootView;

    public static void main(String[] args) {
        launchApp(Main.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //rootLayout.setCenter(mainWindowView.getView());
        stage.setTitle("test");
        stage.setScene(new Scene(rootView.getView()));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}