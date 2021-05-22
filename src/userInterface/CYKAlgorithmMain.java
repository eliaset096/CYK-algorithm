package userInterface;

import controller.CYKAlgorithmController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.CYKAlgorithm;

/**
 * Es la entidad que representa el lanzador de la aplicación,
 * @author elias
 */

public class CYKAlgorithmMain extends Application {

    /**
     * Inicializa la GUI
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CYKAlgorithmGUI.fxml"));
        primaryStage.getIcons().add(new Image("resourses/matrix-desktop.png"));
        primaryStage.setFullScreen(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /**
     * Lanza la aplicación
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
