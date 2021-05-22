package customExceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Entidad que representa una excepción por una cadena w vacía.
 * @author elias
 */
public class StringWBlankException extends Exception {

    /**
     * Crea la excepción.
     */
    public StringWBlankException() {
        super("Debe ingresar una cadena w válida.");
    }

    /**
     * Es el mensaje de la excepción.
     */
    public void message() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CLOSE);
        alert.setHeaderText(super.getMessage());
        alert.show();
    }

}
