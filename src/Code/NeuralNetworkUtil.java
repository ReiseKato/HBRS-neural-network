package Code;

import java.io.*;
import java.util.ArrayList;

public class NeuralNetworkUtil {



    /**
     * methode zur Matrix Vector multiplikation
     * mxn * nx1= mx1
     *
     * @param matrix Matrix
     * @param v Vektor
     * @return Produkt von Matrix * Vektor
     */
    public static Neuron[] matrixVectorMultiplication(Double[][] matrix, Neuron[] v) {
        Neuron[] res = new Neuron[matrix.length];// Ergebnis Vektor mit Länge = Anzahl der Zeilen der Matrix
        //matrix[] = Zeilen, matrix = Spalten
        //prüfe ob Anzahl der Spalten = Anzahl Zeilen von Vektor
        //falls die Anzahl der Spalten != die Anzahl der Zeilen des Vektors
        if (matrix[0].length != v.length) {
            System.out.println("n nicht gleich n");
        }


        for (int i = 0; i < matrix.length; i++) {
            res[i] = new Neuron();
            //berechne die Summe von der Multiplikation von jedem Element der it-Zeile mit der j-ten Komponente des Vektors
            for (int j = 0; j < matrix[0].length; j++) {
                res[i].value += matrix[i][j] * v[j].value;
            }
        }
        return res;
    }

    public static Double[][] vectorMatrixMultiplication(Neuron[] v, Double[][] matrix) {
        Double[][] result = null;
        if (1 == matrix.length) {
            result = new Double[v.length][matrix[0].length];

            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {

                    for (Double[] doubles : matrix) {
                        result[i][j] = v[i].value * doubles[j];
                    }
                }
            }
        }

        return result;
    }

    /**
     * Addiere zwei Neuron[] Vektoren Komponentenweise
     * @param v1 erster Vektor
     * @param v2 zweiter Vektor
     * @return v1 + v2
     */
    public static Neuron[] addVectors(Neuron[] v1, Neuron[] v2) {
        Neuron[] res = null;
        //schaue, ob die Anzahl an Komponenten gleich ist
        if(v1.length == v2.length) {
            res = new Neuron[v1.length];//erzeuge ergebnis Vektor
            for (int i = 0; i < v1.length; i++) {
                //addiere Komponentenweise
                res[i] = new Neuron(v1[i].value + v2[i].value);
            }
        }
        return res;
    }

    public static Neuron[] subVectors(Neuron[] v1, Neuron[] v2) {
        Neuron[] res = null;
        //schaue, ob die Anzahl an Komponenten gleich ist
        if(v1.length == v2.length) {
            res = new Neuron[v1.length];//erzeuge ergebnis Vektor
            for (int i = 0; i < v1.length; i++) {
                //subtrahiere Komponentenweise
                res[i] = new Neuron(v1[i].value - v2[i].value);
            }
        }
        return res;
    }

    /**
     * Methode zum Einlesen von einer CSV-Datei in ein String[][]
     * @param csvpath Pfad zur CSV-Datei
     * @return gibt den Text in einem String[][] zurück
     * @throws IOException falls die Datei nicht gelesen werden kann
     */
    public static String[][] readCSV(String csvpath) throws IOException {
        //Klasse zum Einlesen einer csv
        BufferedReader read = null;
        String ln;// eine Linie
        String[] text;
        ArrayList<String[]> doc = new ArrayList<>(); // dyn Array

        try {
            read = new BufferedReader(new FileReader(csvpath));// erzeuge einen BufferedReader
            //solange es noch Zeilen gibt lese ein
            while ((ln = read.readLine()) != null) {
                //teile den String in ein String[] zwischen ;
                text= ln.split(";");
                doc.add(text);//schreibe die Zeile in den Text

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); //falls die datei nicht importiert werden kann
        } finally {
            //schließe den Reader, falls read != null ist
            if (read != null) {
                read.close();
            }
        }
        //konvertiere das dynArray in ein statisches Array
        return doc.toArray(new String[0][]);
    }

    /**
     * Methode zum Schreiben eines Textes(String[][]) in eine CSV-Datei
     * @param text Text der in die CSV geschrieben werden soll
     * @param filename Pfad zur CSV, in welche geschrieben werden soll
     * @throws IOException wird geworfen, falls keine Writer instanz auf basis des Files erzeugt werden kann
     */
    public static void writeCSV(String[][] text, String filename) throws IOException {
        BufferedWriter writer = null;//Klasse zum Schreiben in eine CSV
        try {
            //initialisiere den Writer und fange, wenn nötig exception
            writer = new BufferedWriter(new FileWriter(filename));
            //für jede Zeile aus Text
            for (String[] row : text) {
                //falls die Zeile leer ist
                if(row.length == 0) {
                    writer.write(";;;");
                }
                //für jedes Wort in einer Zeile
                for (int i = 0; i < row.length; i++) {
                    //schreibe i-tes Wort in Datei
                    writer.write(row[i]);
                    //füge nach jedem Wort ein ";" ein, außer es ist das letzte Wort der Zeile
                    if (i < row.length - 1) {
                        writer.write(";");
                    }
                }
                writer.newLine(); // Zeilenumbruch nach jeder Zeile
            }
        } catch (IOException e) { // werfe Exception, falls der Reader nicht Korrekt instanziierbar ist
            e.printStackTrace();
        } finally {
            //falls writer noch nicht geschlossen
            if(writer != null) {
                writer.close();
            }
        }

    }

    /**
     * Methode um ein String[] in ein Double[] umzuwandeln
     * vorausgesetzt: Der String ist ein gültiger Double-Wert
     * @param sar String Array, welches umgewandelt werden soll
     * @return gibt das umgewandelte Array zurück (Double)
     */
    public static Double[] stringArrayToDouble(String[] sar) {
        Double[] ar = new Double[sar.length];//erzeuge Double[] mit größe von sar
        for (int i = 0; i < sar.length; i++) {
            try {
                //versuche String in Double umzuwandeln
                ar[i] = Double.parseDouble(sar[i]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return ar;
    }

    /**
     * Methode zum Zusammenfügen zweier String[]
     * @param ar1 1. String[]
     * @param ar2 2. String[]
     * @return 1. + 2. String[]
     */
    public static String[] concatArrays(String[] ar1, String[] ar2) {
        String[] temp = new String[ar1.length + ar2.length];
        // erzeuge ein neues Feld mit der Länge des Ersten + Zweiten
        System.arraycopy(ar1,0, temp,0, ar1.length);
        //Kopiere das erste Array von pos 0 in Temp zu Position 0, und zwar so viele Strings entsprechend der Länge von ar1
        System.arraycopy(ar2,0, temp, ar1.length, ar2.length);
        //für ar2 das gleiche nur andere Einfüg position
        return temp;
    }

    /**
     * Methode um eine Double[][] Matrix in eine String[][] Matrix zu konvertieren
     * @param matrix Matrix, welche in String[][] umgewandelt werden soll
     * @return umgewandelte Matrix
     */
    public static String[][] doubleMatrixToStringArray(Double[][] matrix) {
        //erzeuge eine Neue String Matrix mit entsprechend der Anzahl der Zeilen und Spalten vom Parameter
        String[][] stringArray = new String[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                //für jedes Element aus der Matrix rufe die toString Methode auf
                stringArray[i][j] = Double.toString(matrix[i][j]);
            }
        }
        return stringArray;
    }

    /**
     * Methode um ein Neuron[] in ein String[] umzuwandeln
     * @param n Neuron Vektor, welcher umgewandelt werden soll
     * @return String[]
     */
    public static String[] neuronToString(Neuron[] n) {

        String[] temp = new String[n.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = Double.toString(n[i].getValue());
        }
        return temp;
    }

    /**
     * Methode um einen String[] in ein Neuron[] umzuwandeln
     * @param st String Feld
     * @return Neuron[]
     */
    public static Neuron[] stringToNeuron(String[] st) {
        Neuron[] temp = new Neuron[st.length];
        //erzeuge Neuron[] mit Länge von Parameter
        for (int i = 0; i < temp.length; i++) {
            try {
                //versuche für jeden String ein Neuron zu erstellen
                temp[i] = new Neuron(Double.parseDouble(st[i]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
        return temp;
    }
    public static Neuron[] stringToBiasNeuron(String[] st) {
        Neuron[] temp = new Neuron[st.length];
        //erzeuge Neuron[] mit Länge von Parameter
        for (int i = 0; i < temp.length; i++) {
            try {
                //versuche für jeden String ein Neuron zu erstellen
                temp[i] = new Neuron(1, Double.parseDouble(st[i]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
        return temp;
    }

    public static Double[][][] addArrays(Double[][][] array1, double[][][] array2) {


        for (int i = 0; i < array1.length; i++) {
            for (int j = 0; j < array1[i].length; j++) {
                for (int k = 0; k < array1[i][j].length; k++) {
                    array1[i][j][k] = array1[i][j][k] + array2[i][j][k];
                }
            }
        }

        return array1;
    }

    public static Double calcAbsValOfVector(Neuron[] vektor) {

        double temp = 0;
        for (Neuron i: vektor) {
            temp += Math.pow(i.getValue(), 2);
        }
        return Math.sqrt(temp);
    }

    public static double[] neuronToDouble(Neuron[] temp) {
        double[] temp2 = new double[temp.length];
        for (int i = 0; i < temp2.length; i++) {
            temp2[i] = temp[i].value;
        }
        return temp2;
    }

    public static Neuron[] doubleToNeuronBias(Double[] temp) {
        Neuron[] res = new Neuron[temp.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = new Neuron(1, temp[i]);
        }
        return res;
    }
    public static Neuron[] doubleToNeuron(Double[] temp) {
        Neuron[] res = new Neuron[temp.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = new Neuron(temp[i]);
        }
        return res;
    }



    public static void skalarMatrixMultiplikation(double skalar, Double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] *= skalar;
            }

        }
    }
    public static Neuron[] vektorMultiplikation(Neuron[] v1, Neuron[] v2) {
        Neuron[] res = null;
        if(v1.length == v2.length) {
            res = new Neuron[v1.length];
            for (int i = 0; i < v1.length; i++) {
                res[i] = new Neuron(v1[i].value * v2[i].value);
            }
        }
        return res;
    }

    public static Double[][] transposeMatrix(Double[][] matrix) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        // Erstellen Sie eine neue Matrix mit vertauschten Zeilen und Spalten
        Double[][] transpose = new Double[numCols][numRows];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }

        return transpose;
    }

    public static Double[][] addMatrices(Double[][] m1, Double[][] m2) {
        Double[][] res = null;
        if (m1.length == m2.length && m1[0].length == m2[0].length) {
            res = new Double[m1.length][m1[0].length];
            for (int i = 0; i < res.length; i++) {
                for (int j = 0; j < res[0].length; j++) {
                    res[i][j] = m1[i][j] + m2[i][j];
                }
            }

        }
        return res;
    }

    public static String[] doubleArrayToString(Double[] ar) {
        String[] temp = new String[ar.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = ar[i].toString();
        }
        return temp;
    }
}



