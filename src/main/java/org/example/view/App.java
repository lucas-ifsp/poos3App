package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Object controller;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXMLAndController("panelTableProduct"), 670, 400);
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXMLAndController(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(App.class.getResource(fxml + ".fxml").openStream());
        controller = fxmlLoader.getController();
        return parent;
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXMLAndController(fxml));
    }

    public static void main(String[] args) {
        launch();
    }

    public static Object getController() {
        return controller;
    }
}