package customExceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class NoGrammarException extends Exception{

    public NoGrammarException(){
        super("Debe ingresar una GIC en foma GNC. No debe dejar campos vacíos.");
    }

    public void message(){
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CLOSE);
        alert.setHeaderText(super.getMessage());
        alert.show();
    }

}
