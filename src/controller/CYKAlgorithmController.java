package controller;

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

public class CYKAlgorithmController implements Initializable {

    // Constantes
    public static final  String LAMBDA = "λ";

    // Atributos
    @FXML
    private JFXTextField tfStringW;
    @FXML
    private VBox vbMatrixCYK;
    @FXML
    private JFXTextField tfPrintIsGenerate;
    @FXML
    private VBox vbGrammar;

    private CYKAlgorithm cykAlgorithm;


    // Inicializador
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         createNewRow();
    }


    // Métodos

    @FXML
    public void newStringWHandler(ActionEvent actionEvent) {
        tfStringW.clear();
        vbMatrixCYK.getChildren().clear();
        this.cykAlgorithm = null;
    }

    @FXML
    public void cleanAllHandler(ActionEvent actionEvent) {
        vbGrammar.getChildren().clear();
        tfStringW.clear();
        vbMatrixCYK.getChildren().clear();
        this.cykAlgorithm = null;

        createNewRow();
    }

    @FXML
    public void executeCYK(ActionEvent actionEvent) {

        try {
            checkGrammar();
            checkTextString();

            vbMatrixCYK.getChildren().clear();

            this.cykAlgorithm = new CYKAlgorithm(tfStringW.getText().length(), convertGrammarToMatrix());
            this.cykAlgorithm.addElementsToProductionMap();
            this.cykAlgorithm.addTofirstColumn(tfStringW.getText());
            this.cykAlgorithm.executeCYKAlgorithm(tfStringW.getText());

            showMessageResultCYK();
            generateMatrix();

            //CYK_Tab.setDisable(false);
            //tabPane.getSelectionModel().select(CYK_Tab);

        } catch (StringWBlankException textString) {
            textString.message();
        } catch (NoGrammarException grammar) {
            grammar.message();
        }


    }

    @FXML
    public void addProductionHandler(ActionEvent actionEvent) {
        createNewRow();
    }




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



    private void checkTextString() throws StringWBlankException {
        System.out.println(" la cadena es: " +tfStringW.getText());
        if (tfStringW.getText().isEmpty() || tfStringW.getText().equals(" ") || tfStringW.getText()==null){
            throw new StringWBlankException();
        }
    }


    private void showMessageResultCYK() {

       // JFXTextField textField = new JFXTextField();
       // vbMatrixCYK.getChildren().add(textField);

        tfPrintIsGenerate.setAlignment(Pos.CENTER);
        tfPrintIsGenerate.setEditable(false);
        tfPrintIsGenerate.setPrefSize(698, 30d);

        tfPrintIsGenerate.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        tfPrintIsGenerate.setFont(Font.font("System", FontWeight.BOLD, 14));

        if (this.cykAlgorithm.containsString()) {
            tfPrintIsGenerate.setText("La cadena ingresada es generada por la GIC");
            tfPrintIsGenerate.setBackground(new Background(new BackgroundFill(Color.valueOf("#36b351"), CornerRadii.EMPTY, Insets.EMPTY)));

        } else {
            tfPrintIsGenerate.setText("La cadena ingresada no es generada por la GIC");
            tfPrintIsGenerate.setBackground(new Background(new BackgroundFill(Color.valueOf("#FF5555"), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }


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



    private void createNewRow() {

        HBox hBox = new HBox();
        vbGrammar.getChildren().add(hBox);

        hBox.prefWidth(685d);
        hBox.prefHeight(50d);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(3d);

        JFXTextField textField1 = new JFXTextField();
        hBox.getChildren().add(textField1);
        textField1.setPrefSize(80d, 30d);
        textField1.setAlignment(Pos.CENTER);
        textField1.setFocusColor(Color.RED);
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
        textField3.setPrefSize(505d, 30d);
        textField3.setAlignment(Pos.CENTER_LEFT);
        textField3.setFocusColor(Color.RED);
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
