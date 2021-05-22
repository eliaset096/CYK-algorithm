package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Es la Entidad que representa el algoritmo CYK.
 * Representa el modelo.
 * @author elias
 */
public class CYKAlgorithm {

    // Atributos

    /**
     * Representa las variables de las producciones.
     */
    private ArrayList<String> inputVariablesList;
    /**
     * Es la matriz inicial con las producciones ingresadas.
     */
    private String[][] initialProductionMatrix;
    /**
     * Es la tabla con las priducciones ingresadas.
     */
    private HashMap<String, List<String>> productionMap;
    /**
     * Es la matriz final que contiene el resultado de ejecutar el algoritmo.
     */
    private String[][] productionResultMatrix;

    // Constructores

    /**
     * Constructor vacío
     */
    public CYKAlgorithm() {
    }

    /**
     * Inicializa el algoritmo CYK.
     * @param sizeResultMatrix - Es un entero con el tamaño de la matriz de resultado.
     * @param productionMatrix - Es una matriz con las producciones iniciales
     */
    public CYKAlgorithm(int sizeResultMatrix, String[][] productionMatrix) {
        this.initialProductionMatrix = productionMatrix;
        this.productionResultMatrix = new String[sizeResultMatrix][sizeResultMatrix];
        this.inputVariablesList = new ArrayList<>();
        this.productionMap = new HashMap<>();
    }


    // Métodos

    /**
     * Es el método que realiza el algoritmo CYK. Parte de una GIC en forma FNC
     * @param stringW - Es la cadena para comprobar si es generada por la GIC.
     */
    public void executeCYKAlgorithm(String stringW) {
        for (int j = 2; j <= stringW.length(); j++) {

            for (int i = 1; i <= (stringW.length() - j + 1); i++) {
                List<String> nTuplas = new ArrayList<>();
                for (int k = 1; k <= j - 1; k++) {

                    String[] partitions1 = productionResultMatrix[i - 1][k - 1].split(",");
                    String[] partitions2 = productionResultMatrix[(i - 1) + (k)][(j - 1) - (k)].split(",");

                    String[] resultCartesianProduct = cartesianProduct(partitions1, partitions2);
                    nTuplas.addAll(Arrays.asList(resultCartesianProduct));
                }
                //Values to generate
                productionResultMatrix[i - 1][j - 1] = findVariableGeneratesString(nTuplas);
            }
        }
    }

    /**
     * Realiza el producto cartesiono entre los diferentes conbinaciones de
     * las producciones.
     * @param partitions1 - Es una producción x
     * @param partitions2 - Es una prodicción y
     * @return combinations - Un arreglo con el producto cartesiano
     */
    public String[] cartesianProduct(String[] partitions1, String[] partitions2) {
        int size = partitions1.length * partitions2.length;
        String[] combinations = new String[size];
        int counter = 0;
        for (int i = 0; i < partitions1.length; i++) {
            for (int j = 0; j < partitions2.length; j++) {
                combinations[counter] = partitions1[i] + "" + partitions2[j];
                counter++;
            }
        }
        return combinations;
    }


    /**
     * Comprueba qué variables generan una cadena w
     * @param nTuplas - Una lista de terminales y variables
     * @return result - Es una cadena con las variables generadoras.
     */
    public String findVariableGeneratesString(List<String> nTuplas) {
        List<String> resultTupla = new ArrayList<String>();
        String result = "";
        for (int i = 0; i < nTuplas.size(); i++) {
            for (int j = 0; j < inputVariablesList.size(); j++) {
                String key = inputVariablesList.get(j);
                if (productionMap.get(key).contains(nTuplas.get(i))) {
                    if (!resultTupla.contains(key)) {
                        resultTupla.add(key);
                        result += key + ",";
                    }
                }
            }
        }
        if (result != "" && result.charAt(result.length() - 1) == ',') {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     *  Agrega una nueva producción a la tabla de producciones.
     */
    public void addElementsToProductionMap() {
        String producerVariable = "";
        inputVariablesList = new ArrayList<String>();
        for (int i = 0; i < initialProductionMatrix.length; i++) {
            List<String> productions = new ArrayList<String>();
            for (int j = 0; j < initialProductionMatrix[i].length; j++) {
                if (j == 0) {
                    inputVariablesList.add(initialProductionMatrix[i][j]);
                    producerVariable = initialProductionMatrix[i][j];
                } else {
                    String[] productionsArray = initialProductionMatrix[i][j].split("\\|");
                    productions = Arrays.asList(productionsArray);
                }
            }
            productionMap.put(producerVariable, productions);
        }
    }

    /**
     * Verifica si una cadena w se construye por la lista de variables y sus producciones.
     * @return found - Indica si está contenida si o no.
     */
    public boolean containsString() {
        String[] checkValueInitial = productionResultMatrix[0][productionResultMatrix[0].length - 1].split(",");
        boolean found = false;
        for (int i = 0; i < checkValueInitial.length && !found; i++) {
            if (checkValueInitial[i].equals(inputVariablesList.get(0))) {
                found = true;
            }
        }
        return found;
    }

    /**
     *  Agrega a la matrix de resultados final la primero columna.
     * @param w - Es una cadena w.
     */
    public void addTofirstColumn(String w) {
        for (int j = 1; j <= w.length(); j++) {
            String value = "";
            for (int k = 0; k < inputVariablesList.size(); k++) {
                String subCadena = Character.toString(w.charAt(j - 1));
                String key = inputVariablesList.get(k);
                if (productionMap.get(key).contains(subCadena)) {
                    value += key + ",";
                }
            }
            if (value != "" && value.charAt(value.length() - 1) == ',') {
                value = value.substring(0, value.length() - 1);
            }
            productionResultMatrix[j - 1][0] = value;
        }
    }

    /**
     * Devuelve la lista de variables iniciales.
     * @return - inputVariablesList
     */
    public ArrayList<String> getInputVariablesList() {
        return inputVariablesList;
    }

    /**
     * Cambia la lista de variables iniciales.
     * @param inputVariablesList - la nueva lista de variables iniciales.
     */
    public void setInputVariablesList(ArrayList<String> inputVariablesList) {
        this.inputVariablesList = inputVariablesList;
    }

    /**
     * Devuelve la matriz de producciones iniciales.
     * @return - initialProductionMatrix
     */
    public String[][] getInitialProductionMatrix() {
        return initialProductionMatrix;
    }

    /**
     * Cambia la matriz de producciones iniciales.
     * @param initialProductionMatrix - La nueva matriz de producciones iniciales.
     */
    public void setInitialProductionMatrix(String[][] initialProductionMatrix) {
        this.initialProductionMatrix = initialProductionMatrix;
    }

    /**
     * Devuelve la tabla de producciones.
     * @return - productionMap
     */
    public HashMap<String, List<String>> getProductionMap() {
        return productionMap;
    }

    /**
     * Cambia la tabla de producciones.
     * @param productionMap - Es na nueva tabla de producciones.
     */
    public void setProductionMap(HashMap<String, List<String>> productionMap) {
        this.productionMap = productionMap;
    }

    /**
     * Devuelve la matriz final con el resultado de la ejecución del algoritmo.
     * @return
     */
    public String[][] getProductionResultMatrix() {
        return productionResultMatrix;
    }

    /**
     * Cambia la matriz final con el resultado de la ejecución del algoritmo.
     * @param productionResultMatrix - Es la nueva matriz final con el resultado de la ejecución del algoritmo.
     */
    public void setProductionResultMatrix(String[][] productionResultMatrix) {
        this.productionResultMatrix = productionResultMatrix;
    }

}
