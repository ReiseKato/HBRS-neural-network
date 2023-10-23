package Code;

import java.io.*;
import java.util.ArrayList;

public class NeuralNetworkUtil {



    /**
     * methode zur Matrix Vector multiplikation
     * mxn * nx1= mx1   {{1,2,3},
     * {2,3,4}}
     *
     * @param matrix
     * @param v
     * @return
     */
    public static Neuron[] matrixVectorMultiplication(Double[][] matrix, Neuron[] v) {
        Neuron[] res = new Neuron[matrix[0].length];

        //prüfe ob Anzahl der Spalten = Anzahl Zeilen von Vektor
        if (matrix.length != v.length) {
            System.out.println("n nicht gleich n");
        }

        for (int i = 0; i < matrix[0].length; i++) {
            res[i] = new Neuron();
            for (int j = 0; j < matrix.length; j++) {
                res[i].value += matrix[j][i] * v[j].value;
            }
        }
        return res;
    }

    public static Neuron[] addVectors(Neuron[] v1, Neuron[] v2) {
        Neuron[] res = null;
        if(v1.length == v2.length) {
            res = new Neuron[v1.length];
            for (int i = 0; i < v1.length; i++) {
                res[i] = new Neuron(v1[i].value + v2[i].value);
            }
        }
        return res;
    }

    public static String[][] readCSV(String csvpath) throws IOException {
        //Klasse zum einlesen einer csv
        BufferedReader read = null;
        String ln = "";// eine Linie
        String[] text = null;
        ArrayList<String[]> doc = new ArrayList<>();

        try {
            read = new BufferedReader(new FileReader(csvpath));

            for (int i = 0; (ln = read.readLine()) != null; i++) {
                text= ln.split(";");
                doc.add(text);

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); //falls die datei nicht importiert werden kann
        } finally {
            //schließe den Reader
            if (read != null) {
                read.close();
            }
        }
        return doc.toArray(new String[0][]);
    }

    public static void writeCSV(String[][] text, String filename) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            for (String[] row : text) {
                if(row.length == 0) {
                    writer.write(";;;");
                }
                for (int i = 0; i < row.length; i++) {
                    writer.write(row[i]);

                    if (i < row.length - 1) {
                        writer.write(";");
                    }
                }
                writer.newLine(); // Zeilenumbruch nach jeder Zeile
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(writer != null) {
                writer.close();
            }
        }

    }



    public static Double[] stringArrayToDouble(String[] sar) {
        Double[] ar = new Double[sar.length];
        for (int i = 0; i < sar.length; i++) {
            try {
                ar[i] = Double.parseDouble(sar[i]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return ar;
    }
    public static String[] concatArrays(String[] ar1, String[] ar2) {
        String[] temp = new String[ar1.length + ar2.length];
        System.arraycopy(ar1,0, temp,0, ar1.length);
        System.arraycopy(ar2,0, temp, ar1.length, ar2.length);
        return temp;
    }
    public static String[][] doubleMatrixToStringArray(Double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        String[][] stringArray = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                stringArray[i][j] = Double.toString(matrix[i][j]); // Formatierung auf 2 Dezimalstellen anpassen
            }
        }

        return stringArray;
    }

    public static String[] neuronToString(Neuron[] n) {

        String[] temp = new String[n.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = Double.toString(n[i].getValue());
        }
        return temp;
    }

    public static Neuron[] stringToNeuron(String[] st) {
        Neuron[] temp = new Neuron[st.length];
        for (int i = 0; i < temp.length; i++) {
            try {
                temp[i] = new Neuron(Double.parseDouble(st[i]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
        return temp;
    }
}



