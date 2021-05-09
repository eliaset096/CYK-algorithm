package customExceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class StringWBlankException extends Exception{

    public StringWBlankException(String message){
        super(message);
    }

    public void message(){
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CLOSE);
        alert.setHeaderText(super.getMessage());
        alert.show();
    }

}
