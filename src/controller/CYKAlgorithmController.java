package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import customExceptions.NoGrammarException;
import customExceptions.StringWBlankException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.CYKAlgorithm;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Es la entidad que representa el controlador de la aplicación.
 * Realiza el enlace entre el modelo y la GUI
 * @author elias
 */
public class CYKAlgorithmController implements Initializable {

    // -------- Constantes -----------------------

    /**
     * Indica una terminal vacía.
     */
    public static final String LAMBDA = "λ";

    // --------- Atributos -----------------------

    /**
     * Caja de texto para la cadena w.
     */
    @FXML private JFXTextField tfStringW;
    /**
     * Panel para pintar la matriz final del algoritmo.
     */
    @FXML private VBox vbMatrixCYK;
    /**
     * Caja de texto para mostrar el resultado del algoritmo.
     */
    @FXML private JFXTextField tfPrintIsGenerate;
    /**
     * Conjunto de cajas de texto para ingresar variables y sus producciones.
     */
    @FXML private VBox vbGrammar;
    /**
     * Botón para agregar nuevas producciones.
     */
    @FXML private JFXButton btAddProduction;
    /**
     * Botón para cambiar la cadena w.
     */
    @FXML private JFXButton btNewStringW;
    /**
     * Botón para limpiar la interfaz gráfica.
     */
    @FXML private JFXButton btCleanAll;
    /**
     * Botón para ejecutar el algoritmo
     */
    @FXML
    private JFXButton btExecuteCYK;

    /**
     * Es la relación hacia el modelo.
     */
    private CYKAlgorithm cykAlgorithm;


    // ----------- Inicializador -----------------

    /**
     * Inicializa los valores o componentes necesarios.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createNewProduction();
        btNewStringW.setDisable(true);
        //btCleanAll.setDisable(true);
        //btExecuteCYK.setDisable(true);
    }


    // ---------------- Métodos -----------------

    /**
     * Controla el botón para ingresar una nueva cadena.
     * @param actionEvent - El evento producido por el botón.
     */
    @FXML
    public void newStringWHandler(ActionEvent actionEvent) {
        tfStringW.clear();
        vbMatrixCYK.getChildren().clear();
        this.cykAlgorithm = null;
        tfPrintIsGenerate.clear();
        tfPrintIsGenerate.setOpacity(1);
        tfPrintIsGenerate.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        tfPrintIsGenerate.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    /**
     * Controla el botón para limpiar la toda la aplicación.
     * @param actionEvent - El evento producido por el botón.
     */
    @FXML
    public void cleanAllHandler(ActionEvent actionEvent) {
        btAddProduction.setDisable(false);
        btNewStringW.setDisable(true);
        vbGrammar.getChildren().clear();
        tfStringW.clear();
        vbMatrixCYK.getChildren().clear();
        this.cykAlgorithm = null;
        tfPrintIsGenerate.clear();
        tfPrintIsGenerate.setOpacity(1);
        tfPrintIsGenerate.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        tfPrintIsGenerate.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        createNewProduction();
    }

    /**
     * Controla el botón para ejecutar el algoritmo CYK.
     * @param actionEvent - El evento producido por el botón.
     */
    @FXML
    public void executeCYK(ActionEvent actionEvent) {
        try {
            btAddProduction.setDisable(true);
            btNewStringW.setDisable(false);
            checkGrammar();
            checkTextString();
            vbMatrixCYK.getChildren().clear();
            this.cykAlgorithm = new CYKAlgorithm(tfStringW.getText().length(), convertGrammarToMatrix());
            this.cykAlgorithm.addElementsToProductionMap();
            this.cykAlgorithm.addTofirstColumn(tfStringW.getText());
            this.cykAlgorithm.executeCYKAlgorithm(tfStringW.getText());
            showMessageResultCYK();
            generateMatrix();
        } catch (StringWBlankException textString) {
            textString.message();
        } catch (NoGrammarException grammar) {
            grammar.message();
        }
    }

    /**
     * Controla el botón de agregar una nueva producción.
     * @param actionEvent - El evento producido por el botón.
     */
    @FXML
    public void addProductionHandler(ActionEvent actionEvent) {
        createNewProduction();
    }

    /**
     * Verifica si la gramática independiente de contexto (GIC) es válida.
     * @throws NoGrammarException - Excepción que se lanza si GIC en inválida.
     */
    private void checkGrammar() throws NoGrammarException {
        for (int i = 0; i < vbGrammar.getChildren().size(); i++) {
            HBox hBox = (HBox) vbGrammar.getChildren().get(i);
            JFXTextField textField1 = (JFXTextField) hBox.getChildren().get(0);
            JFXTextField textField2 = (JFXTextField) hBox.getChildren().get(2);
            if (textField1.getText().equals("") || textField2.getText().equals("")) {
                throw new NoGrammarException();
            }
        }
    }

    /**
     * Verifica si la cadena w es válida
     * @throws StringWBlankException - Excepción cuando la cadena w es inválida.
     */
    private void checkTextString() throws StringWBlankException {
        //System.out.println(" la cadena es: " + tfStringW.getText());
        if (tfStringW.getText().isEmpty() || tfStringW.getText().equals(" ") || tfStringW.getText() == null) {
            throw new StringWBlankException();
        }
    }

    /**
     * Muestra el resultado de la ejecución del algoritmo CYK.
     */
    private void showMessageResultCYK() {

        tfPrintIsGenerate.setAlignment(Pos.CENTER);
        tfPrintIsGenerate.setEditable(false);
        tfPrintIsGenerate.setPrefSize(698, 30d);

        tfPrintIsGenerate.setBorder(new Border(new BorderStroke(Color.BROWN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        tfPrintIsGenerate.setFont(Font.font("System", FontWeight.BOLD, 15));

        if (this.cykAlgorithm.containsString()) {
            tfPrintIsGenerate.setText("La cadena ingresada " + tfStringW.getText() + " es generada por la GIC");
            tfPrintIsGenerate.setBackground(new Background(new BackgroundFill(Color.valueOf("#36b351"), CornerRadii.EMPTY, Insets.EMPTY)));

        } else {
            tfPrintIsGenerate.setText("La cadena ingresada " +tfStringW.getText() + " no es generada por la GIC");
            tfPrintIsGenerate.setBackground(new Background(new BackgroundFill(Color.valueOf("#FF5555"), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    /**
     * Muestra la matriz final con el resultado del algoritmo.
     */
    private void generateMatrix() {
        HBox hBox = new HBox();
        VBox vBox = new VBox();

        hBox.setSpacing(3d);
        hBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(hBox);

        Label label = new Label();
        label.setPrefHeight(30);
        label.setPrefWidth(40);
        hBox.getChildren().add(label);

        for (int i = 0; i < tfStringW.getText().length(); i++) {
            Label label2 = new Label();
            label2.setAlignment(Pos.CENTER);
            label2.setPrefHeight(30);
            label2.setPrefWidth(40);

            label2.setText("j= " + (i + 1));
            hBox.getChildren().add(label2);
        }
        printMatrix(vBox);
    }

    /**
     * Pinta la matriz de resultado
     * @param vBox
     */
    private void printMatrix(VBox vBox) {
        GridPane gpTableCYK = new GridPane();
        String[][] matrixResult = this.cykAlgorithm.getProductionResultMatrix();

        gpTableCYK.setHgap(3);
        gpTableCYK.setVgap(3);
        gpTableCYK.setAlignment(Pos.CENTER);

        for (int i = 0; i < matrixResult.length; i++) {
            Label label = new Label("i= " + (i + 1));
            label.setAlignment(Pos.CENTER);
            label.setPrefHeight(30);
            label.setPrefWidth(40);
            gpTableCYK.add(label, 0, (i + 1));
            for (int j = 0; j < matrixResult[0].length; j++) {
                Label label2 = new Label();
                label2.setAlignment(Pos.CENTER);
                label2.setPrefHeight(30);
                label2.setPrefWidth(40);
                label2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                if (matrixResult[i][j] != null) {
                    label2.setText("{" + matrixResult[i][j] + "}");
                } else {
                    label2.setText("");
                }
                gpTableCYK.add(label2, (j + 1), (i + 1));
            }
        }
        vBox.getChildren().add(gpTableCYK);
        this.vbMatrixCYK.getChildren().add(vBox);
    }

    /**
     * Convierte la gramática a la matriz.
     * @return grammar - La matriz con la gramática.
     */
    private String[][] convertGrammarToMatrix() {
        String[][] grammar = new String[vbGrammar.getChildren().size()][2];

        for (int i = 0; i < vbGrammar.getChildren().size(); i++) {
            HBox hBox = (HBox) vbGrammar.getChildren().get(i);

            JFXTextField textField1 = (JFXTextField) hBox.getChildren().get(0);
            JFXTextField textField2 = (JFXTextField) hBox.getChildren().get(2);

            grammar[i][0] = textField1.getText();
            grammar[i][1] = textField2.getText();
        }
        return grammar;
    }

    /**
     * Crea una nueva producción.
     */
    private void createNewProduction() {
        HBox hBox = new HBox();
        vbGrammar.getChildren().add(hBox);
        hBox.prefWidth(600d);
        hBox.prefHeight(50d);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(3d);

        JFXTextField textField1 = new JFXTextField();
        hBox.getChildren().add(textField1);
        textField1.setPrefSize(80d, 30d);
        textField1.setAlignment(Pos.CENTER);
        textField1.setFocusColor(Color.GREEN);
        textField1.setUnFocusColor(Color.WHITE);
        textField1.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        textField1.setFont(Font.font("System", FontWeight.BOLD, 14d));

        JFXTextField textField2 = new JFXTextField();
        hBox.getChildren().add(textField2);
        textField2.setPrefSize(80d, 30d);
        textField2.setAlignment(Pos.CENTER);
        textField2.setFocusColor(Color.WHITE);
        textField2.setUnFocusColor(Color.WHITE);
        textField2.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        textField2.setFont(Font.font("System", FontWeight.BOLD, 14d));
        textField2.setText("----->");
        textField2.setEditable(false);

        JFXTextField textField3 = new JFXTextField();
        hBox.getChildren().add(textField3);
        textField3.setPrefSize(450d, 30d);
        textField3.setAlignment(Pos.CENTER_LEFT);
        textField3.setFocusColor(Color.GREEN);
        textField3.setUnFocusColor(Color.WHITE);
        textField3.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        textField3.setFont(Font.font("System", 14d));

        textField1.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !textField1.getText().equals("")) {
                textField3.setText(textField3.getText() + LAMBDA);
            }
        });
    }


}
