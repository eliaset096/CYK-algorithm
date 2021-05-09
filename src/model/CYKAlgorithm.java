package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CYKAlgorithm {

    // Atributos
    private ArrayList<String> inputVariablesList;
    private String[][] initialProductionMatrix;
    private HashMap<String, List<String>> productionMap;
    private String[][] productionResultMatrix;

    // Constructores
    public CYKAlgorithm (){}

    public CYKAlgorithm (int sizeResultMatrix, String[][] productionMatrix ){
        this.initialProductionMatrix = productionMatrix;
        this.productionResultMatrix = new String[sizeResultMatrix][sizeResultMatrix];
        this.inputVariablesList = new ArrayList<>();
        this.productionMap = new HashMap<>();
    }


    // Métodos

    public void executeCYKAlgorithm(String stringW) {
        for (int j = 2; j <= stringW.length(); j++) {

            for (int i = 1; i <= (stringW.length()-j+1); i++) {
                List<String> nTuplas = new ArrayList<>();
                for (int k = 1; k <= j-1; k++) {
                    //X (i,j) -> X(i,k) X(i+k),(j-k)
                    //Elements to use the method called cartesian product
                    String[] partitions1 = productionResultMatrix[i-1][k-1].split(",");
                    String[] partitions2 = productionResultMatrix[(i-1)+(k)][(j-1)-(k)].split(",");
                    // Result the method cartesian product
                    String[] resultCartesianProduct = cartesianProduct(partitions1,partitions2);
                    nTuplas.addAll(Arrays.asList(resultCartesianProduct));
                }
                //Values to generate
                productionResultMatrix[i-1][j-1] = findVariableGeneratesString(nTuplas);
            }
        }
    }

    public String[] cartesianProduct(String[] partitions1, String[] partitions2) {
        int size = partitions1.length * partitions2.length;
        String[] combinations = new String[size];
        int counter = 0;
        for (int i = 0; i < partitions1.length; i++) {
            for (int j = 0; j < partitions2.length; j++) {
                combinations[counter] = partitions1[i]+""+partitions2[j];
                counter++;
            }
        }
        return combinations;
    }


    public String findVariableGeneratesString(List<String> nTuplas) {
        List<String> resultTupla = new ArrayList<String>();
        String result = "";
        for (int i = 0; i < nTuplas.size(); i++) {
            for (int j = 0; j < inputVariablesList.size(); j++) {
                String key = inputVariablesList.get(j);
                if(productionMap.get(key).contains(nTuplas.get(i))) {
                    if(!resultTupla.contains(key)) {
                        resultTupla.add(key);
                        result += key+",";
                    }
                }
            }
        }
        //Para quitar la coma
        //Ejemplo: si es "B," entonces el resultado seria "B"
        if(result != "" && result.charAt(result.length()-1) == ',') {
            result = result.substring(0,result.length()-1);
        }
        return result;
    }


    public void addElementsToProductionMap() {
        String producerVariable = "";
        inputVariablesList = new ArrayList<String>();
        for (int i = 0; i < initialProductionMatrix.length; i++) {
            List<String> productions = new ArrayList<String>();
            for (int j = 0; j < initialProductionMatrix[i].length; j++) {
                if(j == 0) {
                    inputVariablesList.add(initialProductionMatrix[i][j]);
                    producerVariable = initialProductionMatrix[i][j];
                }else {
                    String[] productionsArray = initialProductionMatrix[i][j].split("\\|");
                    productions = Arrays.asList(productionsArray);
                }
            }
            //addValueToMapAux(producerVariable,productions);
            productionMap.put(producerVariable, productions);
        }
    }


    public boolean containsString() {
        String[] checkValueInitial = productionResultMatrix[0][productionResultMatrix[0].length-1].split(",");
        boolean found = false;
        for (int i = 0; i < checkValueInitial.length && !found; i++) {
            if(checkValueInitial[i].equals(inputVariablesList.get(0))) {
                found = true;
            }
        }
        return found;
    }


    public void addTofirstColumn(String w){
        for (int j = 1; j <= w.length(); j++) {
            String value = "";
            for (int k = 0; k < inputVariablesList.size(); k++) {
                String subCadena = Character.toString(w.charAt(j-1));
                String key = inputVariablesList.get(k);
                if(productionMap.get(key).contains(subCadena)) {
                    value += key+",";
                }
            }
            // Para quitar la coma
            //Ejemplo: si es "B," entonces el resultado seria "B"
            if(value != "" && value.charAt(value.length()-1) == ',') {
                value = value.substring(0,value.length()-1);
            }
            productionResultMatrix[j-1][0] = value;
        }
    }











    public ArrayList<String> getInputVariablesList() {
        return inputVariablesList;
    }

    public void setInputVariablesList(ArrayList<String> inputVariablesList) {
        this.inputVariablesList = inputVariablesList;
    }

    public String[][] getInitialProductionMatrix() {
        return initialProductionMatrix;
    }

    public void setInitialProductionMatrix(String[][] initialProductionMatrix) {
        this.initialProductionMatrix = initialProductionMatrix;
    }

    public HashMap<String, List<String>> getProductionMap() {
        return productionMap;
    }

    public void setProductionMap(HashMap<String, List<String>> productionMap) {
        this.productionMap = productionMap;
    }

    public String[][] getProductionResultMatrix() {
        return productionResultMatrix;
    }

    public void setProductionResultMatrix(String[][] productionResultMatrix) {
        this.productionResultMatrix = productionResultMatrix;
    }

}
