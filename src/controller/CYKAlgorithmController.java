package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CYKAlgorithmController implements Initializable {

    // Atributos
    @FXML private JFXTextField tfStringW;
    @FXML private VBox matrixCYKVBox;
    @FXML private Text txPrintIsGenerate;
    @FXML private VBox vbGrammar;

    // Inicializador
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    // Métodos
    public void addStringWHandler(ActionEvent actionEvent) {
    }

    public void cleanStringWHandler(ActionEvent actionEvent) {
    }

    public void executeCYK(ActionEvent actionEvent) {
    }

    public void addProductionHandler(ActionEvent actionEvent) {
    }

    public void cleanGICHandler(ActionEvent actionEvent) {
    }
}
