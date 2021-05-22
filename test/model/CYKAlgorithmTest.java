package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
class CYKAlgorithmTest {

    // ------ Atributos --------
    HashMap<String, List<String>> pMap;
    ArrayList<String> inputVariables;
    String[][] initialMatrix;
    String[][] resultMatrix;
    String w;
    CYKAlgorithm cykAlgorithm;

    // ------- Setup ------------
    private void setupEscenaryOne(){
        w = "baa";
        initialMatrix = new String[3][2];
        resultMatrix = new String[w.length()][w.length()];
        pMap = new HashMap<>();
        inputVariables = new ArrayList<>();

        inputVariables.add("S");
        inputVariables.add("A");
        inputVariables.add("B");

        initialMatrix[0][0] = "S";
        initialMatrix[1][0] = "A";
        initialMatrix[2][0] = "B";

        initialMatrix[0][1] = "AB|BA";
        initialMatrix[1][1] = "A|a";
        initialMatrix[2][1] = "B|b";

        ArrayList<String> prodS = new ArrayList<>();
        prodS.add("AB");
        prodS.add("BA");

        ArrayList<String> prodA = new ArrayList<>();
        prodA.add("A");
        prodA.add("a");

        ArrayList<String> prodB = new ArrayList<>();
        prodB.add("B");
        prodB.add("b");

        pMap.put(initialMatrix[0][0],prodS);
        pMap.put(initialMatrix[1][0],prodA);
        pMap.put(initialMatrix[2][0],prodB);

        cykAlgorithm = new CYKAlgorithm(w.length(), initialMatrix);
        cykAlgorithm.setInputVariablesList(inputVariables);
        cykAlgorithm.setProductionMap(pMap);

        resultMatrix[0][0] = null;
        resultMatrix[1][0] = "A,B";
        resultMatrix[2][0] = "A,B";

        resultMatrix[0][1] = "A,B";
        resultMatrix[1][1] = "S";
        resultMatrix[2][1] = null;

        resultMatrix[0][2] = "S";
        resultMatrix[1][2] = null;
        resultMatrix[2][2] = null;
    }


    // ---------- Tests ----------
    @Test
    void executeCYKAlgorithm() {
        setupEscenaryOne();

        cykAlgorithm.addElementsToProductionMap();
        cykAlgorithm.addTofirstColumn(w);
        cykAlgorithm.executeCYKAlgorithm(w);
        //printMatrizResult(cykAlgorithm.getProductionResultMatrix());
        //printMatrizResult(resultMatrix);
        //compareMatrix(resultMatrix,cykAlgorithm.getProductionResultMatrix());
    }

    @Test
    void getInputVariablesList() {
        setupEscenaryOne();
        Assertions.assertEquals(inputVariables, cykAlgorithm.getInputVariablesList(), "Las variables no son iguales.");
    }

    @Test
    void setInputVariablesList() {
        setupEscenaryOne();
        cykAlgorithm.setInputVariablesList(new ArrayList<>());
        Assertions.assertNotEquals(inputVariables, cykAlgorithm.getInputVariablesList(), "Las variables son iguales.");
    }

    @Test
    void getInitialProductionMatrix() {
        setupEscenaryOne();
        Assertions.assertEquals(initialMatrix, cykAlgorithm.getInitialProductionMatrix(), "La matriz de producciones  no son las mismas.");
    }

    @Test
    void setInitialProductionMatrix() {
        setupEscenaryOne();
        cykAlgorithm.setInitialProductionMatrix(new String[w.length()][w.length()]);
        Assertions.assertNotEquals(initialMatrix, cykAlgorithm.getInitialProductionMatrix(), "La matriz de producciones son iguales.");
    }

    @Test
    void getProductionMap() {
        setupEscenaryOne();
        Assertions.assertNotNull(cykAlgorithm.getProductionMap(), "La tabla de producciones está vacía.");
    }

    @Test
    void setProductionMap() {
        setupEscenaryOne();
        cykAlgorithm.setProductionMap(null);
        Assertions.assertNull(cykAlgorithm.getProductionMap(), "La table de producciones no está vacía.");
    }

    @Test
    void getProductionResultMatrix() {
        setupEscenaryOne();
        Assertions.assertNotNull(cykAlgorithm.getProductionResultMatrix(), "La matriz final de producciones es diferente a la esperada.");
    }

    @Test
    void setProductionResultMatrix() {
        setupEscenaryOne();
        cykAlgorithm.setProductionResultMatrix(new String[w.length()][w.length()]);
        Assertions.assertNotEquals(resultMatrix, cykAlgorithm.getProductionResultMatrix(), "La matriz final de producciones no es diferente de la esperada.");
    }

    private void printMatrizResult(String[][] matriz) {
        for (int x=0; x < matriz.length; x++) {
            System.out.print("|");
            for (int y=0; y < matriz[x].length; y++) {
                String value = matriz[x][y];
               // System.out.print("|");
                if(value!=null) {
                    System.out.print ("{"+value+"}");
                }else {
                    System.out.print("{} ");
                }
                if (y!=matriz[x].length-1) System.out.print("   ");
            }
            System.out.println("|");
        }
    }

    private void compareMatrix(String[][] matrix1, String[][] matrix2) {
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                //Assertions.assertEquals("La matriz en la posición "+i+" "+j+" es diferentes",matrix1[i][j],matrix2[i][j]);
            }
        }
    }


}