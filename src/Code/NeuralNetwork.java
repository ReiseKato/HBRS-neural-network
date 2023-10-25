package Code;

import java.util.*;

/**
 * Klasse NeuralNetwork
 * @author vkowal2s
 * @version 1.0
 */
public class NeuralNetwork {

    Neuron[][] network;//Neuronen
    Double[][][] weights;//Netztopologie

    Neuron[][] biases;
    Neuron[] input;//Referenz auf den input Layer
    Neuron[] output;//Referenz auf den output Layer
    static int[] layers;
    String[] func;//Funktionen für den jeweiligen Layer

    /**
     * initialisiere die Neuronen, anhand des Arrays layers
     * @param layers beinhaltet die Anzahl der Neuronen pro Layer
     *               Anzahl der Layer wird implizit abgeleitet
     *               Bsp: {1,2,3} => Network mit 3 Layern
     *                               1. Layer: 1 Neuron
     *                               2. Layer: 2 Neuronen usw.
     */
    public void init(int[] layers) {
        //initialisiere die erste Dimension mit der Länge des Arrays
        network = new Neuron[layers.length][];
        for (int i = 0; i < layers.length; i++) {
            //initialisiere die 2. Dim mit einem Neuronen-Array mit der Anzahl des i-ten Elements von layers
            network[i] = new Neuron[layers[i]];
            //initialisiere eine Ebene mit leeren Neuronen
            for (int j = 0; j < network[i].length; j++) {
                network[i][j] = new Neuron();
            }
        }

        input = network[0];
        output = network[network.length-1];
        //speichere das layout in der Klassen variable
        NeuralNetwork.layers = layers;

    }

    /**
     * initialisiere die layers mithilfe eines Strings[][] bzw mithilfe der eingelesenen csv
     * @param file Text welche die Layer Konfiguration enthält
     */
    public void init(String[][] file) {
        String[] line = file[0];//instanz auf die erste Zeile
        int[] layout = new int[file[0].length-1];

        if(line[0].equals("layers")) { // schaue, ob das Stichwort layers an erster Stelle der Zeile steht
            for (int i = 0; i < layout.length; i++) {
                //lese die restichen Werte der ersten Zeile ein und wandle diese in Integer um
                try {
                    layout[i] = Integer.parseInt(line[i+1]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        layers = layout; //setze die ermittelte Konfiguration auf die Klassenvariable
        init(layout);// initialisiere die Neuronen
    }

    /**
     * erzeugt die Netztopologie, jedem weight wird ein zufälliger Double-wert zwischen -1, 1 zugewiesen
     */
    public void initWeights() {
        Random rand = new Random();//Klasse für zufällige Zahl
        int dim = network.length-1;

        //initialisiere die Dimension
        //1. Dim = Netztiefe -1
        weights = new Double[dim][][];
        for (int i = 0; i < dim; i++) {
            //2./3. Dim: Länge des iten Layers und Länge des i+1ten Layers
            weights[i] = new Double[network[i].length][network[i+1].length];
        }
        //initialisiere die weights mit random werten zwischen -1, 1
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {
                    weights[i][j][k] = -1 + (2 * rand.nextDouble());
                }
            }
        }
    }

    /**
     * initialisierung der Weights and Biases, anhand der Werte, die in der CSV-Datei stehen
     * CSV-Datei muss vorher mit NeuralNetworkUtil.readCSV() eingelesen werden und als Parameter übergeben werden
     * @param file eingelesene CSV-Datei mit weight Konfiguration
     */
    public void initWeightsBiases(String[][] file) {
        //erste Dimension ist jeweils die Netztiefe-1
        weights = new Double[network.length-1][][];
        biases = new Neuron[network.length-1][];
        //lese die weight-Konfiguration in eine verkettete Liste ein, schneide die erste Zeile raus
        //wegen der Layer Konfiguration
        LinkedList<String[]> document = new LinkedList<>(Arrays.asList(file).subList(1, file.length));

        //1. Dim: weight-Ebene
        //2. Dim: von Neuron x
        //3. Dim: zu Neuron y

        for (int i = 0; i < weights.length; i++) { // i entspricht der weight-Ebene

            int dimV = network[0].length; // Dimension der Domain
            int dimW = network[i+1].length;// Dimension der Codomain
            weights[i] = new Double[dimV][dimW];// weise der i-ten Weight-Ebene die Dimensionen zu
            for (int j = 0; j < dimV; j++) { //j entspricht dem akt. Index der Domain
                //wandel die akt. Zeile in ein Double[] um und weise sie zur i-ten Ebene mit dem j-ten Element der Domain
                weights[i][j] = NeuralNetworkUtil.stringArrayToDouble(document.remove());
            }
            String[] temp = document.remove();//speichere die Zeile mit den biases
            biases[i] = new Neuron[temp.length];//erzeuge im Array für die biases an der i-ten stelle ein neuess Neuron[] mit der länge der biases
            biases[i] = NeuralNetworkUtil.stringToNeuron(temp);//wandle den String in ein Neuron[] und weise zu
            if (!document.isEmpty()) {// falls noch nicht die letzte Zeile
                document.remove();//leerzeile entfernen
            }
        }

    }

    /**
     * berechnet für einen input vektor den passenden output vektor mithilfe von Matrix Vektor Multiplikation
     * @param dataset enthält den Input Datensatz, idealerweise: Dim(dataset)=Dim(network[0])
     */
    public void compute(double[] dataset) {
        Neuron[] v; // Vektor
        Double[][] wMatrix; // Matrix

        //setze den input des networks auf den übergebenen datensatz
        for (int i = 0; i < input.length; i++) {
            input[i].value = dataset[i];
        }

        // gehe das Netzwerk Vektor für Vektor durch
        for (int i = 0; i < network.length-1; i++) {
            v = network[i]; // i-ter Vektor
            wMatrix = weights[i];// weight Matrix der iten Ebene
            // berechne das matrix vektorprodukt aus wMatrix * v
            v = NeuralNetworkUtil.matrixVectorMultiplication(wMatrix, v);
            //addiere das Ergebnis mit dem BiasVektor der akt. Ebene
            network[i+1] = NeuralNetworkUtil.addVectors(v, biases[i]);
            //führe für das Ergebnis aus der M*V, die Aktivierungsfunktion aus für alle Neuronen aus
            for (int j = 0; j < network[i+1].length; j++) {
                network[i+1][j].compute(func[i+1]);
            }
        }
        //output layer aktualisieren
        output = network[network.length-1];
    }

    /**
     * setze ein String[] functions auf die func des NN
     * Struktur von functions:
     *      Jeder Index steht für die Aktivierungsfunktion des gesamten i-ten Layers
     *      pro Layer eine funktion => könnte man erweitern für jedes Neuron eine andere
     *      Bsp: ["","sigmoid", "ReLu"]
     *              i=0: die Aktivierungsfunktion ist die id rel
     *              i=1: die         "              "  "   Sigmoid Funktion
     *              i=2: die         "              "  "  Gleichgerichtete Linearität
     *
     * @param functions Array mit jeweiligen Aktivierungsfunktionen
     */
    public void setFunc(String[] functions) {
        this.func = functions;
    }

    /**
     *
     * @return gibt die biases des NN zurück
     */
    public Neuron[][] getBiases() {
        return biases;
    }

    /**
     *
     * @return gibt die weights des NN zurück
     */
    public Double[][][] getWeights() {
        return weights;
    }
}
