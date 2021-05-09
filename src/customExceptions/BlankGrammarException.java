package customExceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class BlankGrammarException extends Exception{

    public BlankGrammarException(String message){
        super(message);
    }

    public void message(){
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CLOSE);
        alert.setHeaderText(super.getMessage());
        alert.show();
    }

}
