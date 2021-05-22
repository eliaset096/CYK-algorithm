package customExceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Entidad que representa una excepción por no haber encontrado contrado una GIC válida.
 * @author elias
 */
public class NoGrammarException extends Exception {

    /**
     * Crea la excepción.
     */
    public NoGrammarException() {
        super("Debe ingresar una GIC en foma GNC. No debe dejar campos vacíos.");
    }

    /**
     * Es el mensaje de la excepción
     */
    public void message() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CLOSE);
        alert.setHeaderText(super.getMessage());
        alert.show();
    }

}
