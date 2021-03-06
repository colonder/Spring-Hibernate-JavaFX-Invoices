package com;

import com.UI.SceneManager;
import com.UI.SpringFXMLLoader;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ResourceBundle;

@Configuration
public class AppJavaConfig {

    @Autowired
    @Lazy
    SpringFXMLLoader springFXMLLoader;

    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("Bundle");
    }

    @Bean
    @Lazy //Stage only created after Spring context bootstap
    public SceneManager sceneManager(Stage stage) {
        return new SceneManager(springFXMLLoader, stage);
    }

}
