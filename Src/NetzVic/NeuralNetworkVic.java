package Src.NetzVic;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Klasse NeuralNetwork
 *
 * @author vkowal2s
 * @version 1.0
 */
public class NeuralNetworkVic {

    NeuronVic[][] network;//Neuronen
    double[][][] weights;//Netztopologie

    NeuronVic[][] biases;
    NeuronVic[] input;//Referenz auf den input Layer
    NeuronVic[] output;//Referenz auf den output Layer
    static int[] layers;
    String[] func;//Funktionen für den jeweiligen Layer

    TrainingDataVic train;

    double LR = 0.5;

    public NeuralNetworkVic() {

    }

    public NeuralNetworkVic(TrainingDataVic obj) {
        this.train = obj;
        String[][] file = train.getFile();
        this.init(file);
        if(file.length == 1) {
            this.initWeightsBiases();
            this.setFunc(train.functions);

        } else {
            this.initWeightsBiases(file);
        }
    }

    /**
     * initialisiere die Neuronen, anhand des Arrays layers
     *
     * @param layers beinhaltet die Anzahl der Neuronen pro Layer
     *               Anzahl der Layer wird implizit abgeleitet
     *               Bsp: {1,2,3} => Network mit 3 Layern
     *               1. Layer: 1 Neuron
     *               2. Layer: 2 Neuronen usw.
     */
    public void init(int[] layers) {
        //initialisiere die erste Dimension mit der Länge des Arrays
        network = new NeuronVic[layers.length][];
        for (int i = 0; i < layers.length; i++) {
            //initialisiere die 2. Dim mit einem Neuronen-Array mit der Anzahl des i-ten Elements von layers
            network[i] = new NeuronVic[layers[i]];
            //initialisiere eine Ebene mit leeren Neuronen
            for (int j = 0; j < network[i].length; j++) {
                network[i][j] = new NeuronVic();
            }
        }

        input = network[0];
        output = network[network.length - 1];
        //speichere das layout in der Klassen variable
        NeuralNetworkVic.layers = layers;

    }

    /**
     * initialisiere die layers mithilfe eines Strings[][] bzw mithilfe der eingelesenen csv
     *
     * @param file Text welche die Layer Konfiguration enthält
     */
    public void init(String[][] file) {
        String[] line = file[0];//instanz auf die erste Zeile
        int[] layout = new int[file[0].length - 1];

        if (line[0].equals("layers")) { // schaue, ob das Stichwort layers an erster Stelle der Zeile steht
            for (int i = 0; i < layout.length; i++) {
                //lese die restichen Werte der ersten Zeile ein und wandle diese in Integer um
                try {
                    layout[i] = Integer.parseInt(line[i + 1]);
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
    public void initWeightsBiases() {
        Random rand = new Random();//Klasse für zufällige Zahl
        int dim = network.length - 1;

        //initialisiere die Dimension
        //1. Dim = Netztiefe -1
        weights = new double[dim][][];
        for (int i = 0; i < dim; i++) {
            //2./3. Dim: Länge des iten Layers und Länge des i+1ten Layers
            weights[i] = new double[network[i].length][network[i + 1].length];
        }
        //initialisiere biases
        biases = new NeuronVic[dim][];
        for (int i = 0; i < dim; i++) {
            biases[i] = new NeuronVic[network[i + 1].length];
        }

        //initialisiere die weights mit random werten zwischen -1, 1
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {
                    weights[i][j][k] = -1 + (2 * rand.nextDouble());
                }
            }
        }

        //initialisiere die biases mit random werten
        for (int i = 0; i < biases.length; i++) {
            for (int j = 0; j < biases[i].length; j++) {
                biases[i][j] = new NeuronVic(1, -1 + (2 * rand.nextDouble()));
            }
        }
    }

    public void updateMyTrainingData(String filename) throws IOException {
        train.updateFile(this.weights, this.biases, this.func);
        NeuralNetworkUtilVic.writeCSV(train.getFile(), filename);
    }

    /**
     * initialisierung der Weights and Biases, anhand der Werte, die in der CSV-Datei stehen
     * CSV-Datei muss vorher mit NeuralNetworkUtil.readCSV() eingelesen werden und als Parameter übergeben werden
     *
     * @param file eingelesene CSV-Datei mit weight Konfiguration
     */
    public void initWeightsBiases(String[][] file) {
        String[] temp;
        //erste Dimension ist jeweils die Netztiefe-1
        weights = new double[network.length - 1][][];
        biases = new NeuronVic[network.length - 1][];
        func = new String[network.length];
        //lese die weight-Konfiguration in eine verkettete Liste ein, schneide die erste Zeile raus
        //wegen der Layer Konfiguration
        LinkedList<String[]> document = new LinkedList<>(Arrays.asList(file).subList(1, file.length));

        //Funktion für die erste Ebene einlesen
        initaFunc(document.remove(), 0);

        //1. Dim: weight-Ebene
        //2. Dim: von Neuron x
        //3. Dim: zu Neuron y

        for (int i = 0; i < weights.length; i++) { // i entspricht der weight-Ebene

            int dimV = network[i].length; // Dimension der Domain
            int dimW = network[i + 1].length;// Dimension der Codomain
            weights[i] = new double[dimV][dimW];// weise der i-ten Weight-Ebene die Dimensionen zu
            for (int j = 0; j < dimV; j++) { //j entspricht dem akt. Index der Domain
                //wandel die akt. Zeile in ein Double[] um und weise sie zur i-ten Ebene mit dem j-ten Element der Domain
                weights[i][j] = NeuralNetworkUtilVic.stringArrayToDouble(document.remove());
            }
            temp = document.remove();//speichere die Zeile mit den biases
            biases[i] = new NeuronVic[temp.length];//erzeuge im Array für die biases an der i-ten stelle ein neuess Neuron[] mit der länge der biases
            biases[i] = NeuralNetworkUtilVic.stringToBiasNeuron(temp);//wandle den String in ein Neuron[] und weise zu


            if (!document.isEmpty()) {// falls noch nicht die letzte Zeile
                initaFunc(document.remove(), i+1);//function einlesen
            }
        }

    }

    public void initaFunc(String[] line, int index) {
        if(line.length == 0) {
            func[index] = "";
        } else {
            func[index] = line[0];
        }
    }


    /**
     * berechnet für einen input vektor den passenden output vektor mithilfe von Matrix Vektor Multiplikation
     *
     * @param dataset enthält den Input Datensatz, idealerweise: Dim(dataset)=Dim(network[0])
     */
    public void compute(double[] dataset) {
        NeuronVic[] v; // Vektor
        double[][] wMatrix; // Matrix

        //setze den input des networks auf den übergebenen datensatz
        for (int i = 0; i < input.length; i++) {
            input[i].value = dataset[i];
            input[i].valbeforecomp = dataset[i];
        }

        // gehe das Netzwerk Vektor für Vektor durch
        for (int i = 0; i < network.length - 1; i++) {
            v = network[i]; // i-ter Vektor
            wMatrix = NeuralNetworkUtilVic.transposeMatrix(weights[i]);// weight Matrix der iten Ebene
            //vorher Matrix transponieren
            // berechne das matrix vektorprodukt aus wMatrix * v
            v = NeuralNetworkUtilVic.matrixVectorMultiplication(wMatrix, v);
            //addiere das Ergebnis mit dem BiasVektor der akt. Ebene
            for (int j = 0; j < v.length; j++) {
                v[j].value += biases[i][j].bias;
            }
            network[i + 1] = v;
            //führe für das Ergebnis aus der M*V, die Aktivierungsfunktion aus für alle Neuronen aus
            for (int j = 0; j < network[i + 1].length; j++) {
                network[i + 1][j].compute(func[i + 1]);
            }
        }
        //output layer aktualisieren
        output = network[network.length - 1];
        // this.saves= save.toArray(new Neuron[0][]);
    }

    public void computeUnknwonData(TrainingDataVic unknown) {
        List<double[]> uninputs = unknown.getInputs();
        System.out.println("Unknown data: ");
        for (int i = 0; i < uninputs.size(); i++) {
            System.out.println("Input: " + i);
            compute(uninputs.get(i));
            printOutput();
        }
    }

    /**
     * bestimme den Gesamt-Fehler für einen Datensatz
     *
     * @param expectedoutput erwarteter output
     * @return gibt den Fehler zurück
     */
    public double determineTotalError(double[] expectedoutput) {
        double sum = 0;
        for (int i = 0; i < expectedoutput.length; i++) {
            // summe von geschätzer output - erwarteter potenziert
            sum += 0.5 * (output[i].value - expectedoutput[i]) * (output[i].value - expectedoutput[i]);
        }
        // summe durch länge teilen
        return sum;
    }

    /**
     * trainiere das Netz mit Backpropagation
     *
     * @param iterations Anzahl der Durchläufe des Datensatzes
     */
    public void train(int iterations) {

        //solange i < Anzahl festgelegter Iterationen
        for (int i = 0; i < iterations; i++) {
            double totalerror = 0;
            //gehe nacheinander alle Datensätze durch
            for (int j = 0; j < train.getListInputSize(); j++) {
                double[] input = train.getInput(j);
                double[] output = train.getOutput(j);
                //forward propagation
                compute(input);
                //calculate the loss
                double loss = determineTotalError(output);

                // wenn nötig passe LR an
               /* if(i == 0) {
                    error.add(j, loss);
                }
                if((loss < error.get(j))) {
                    LR *= 1.1;
                    higher++;
                } else if (loss > error.get(j)) {
                    LR *= 0.5;
                    lower++;
                }
                */
                //backwardpass
                backpropagation(output);
            }
        }
        // nach dem training werte ausgeben
        System.out.println("Trainingdata: ");
        for (int i = 0; i < train.getListInputSize(); i++) {
            compute(train.getInput(i));
            System.out.println("Input: " + i);
            printOutput();
        }
    }

    public void printOutput() {
        for (NeuronVic temp : output) {
            System.out.println(temp.value);
        }
        System.out.println();
    }

    /**
     * bestimme die partiellen Ableitungen für die einzelnen Gewichte und berechne die neuen Gewichte
     * @param expectedoutput erwarteter output des akt. inputs
     */
    public void backpropagation(double[] expectedoutput) {

        double[][][] oldweights = new double[network.length - 1][][];//alte gewichte
        int firsthidden = network.length - 2;
        int lastlayer = network.length - 1;
        double[] delta = new double[output.length];//delta Vektor
        //delta Werte für die Gewichte des Outputslayers
        double[][] out_delta_weights = new double[network[firsthidden].length][network[lastlayer].length];
        double[][] newWeights;
        //Neuron[][] bias_delta = new Neuron[network.length-1][];
        //speichere die alten Gewichte zwischen
        oldweights[network.length - 2] = weights[weights.length - 1];

        for (int i = 0; i < out_delta_weights.length; i++) {
            for (int j = 0; j < out_delta_weights[0].length; j++) {
                //berechne das delta
                out_delta_weights[i][j] = output[j].value - expectedoutput[j];
                out_delta_weights[i][j] *= (output[j].value * (1.0 - output[j].value));
                delta[j] = out_delta_weights[i][j];
                out_delta_weights[i][j] *= network[firsthidden][i].value;
            }
        }
      /*  Neuron[] bias = biases[biases.length-1];
        for (int i = 0; i < bias.length; i++) {
            bias[i].bias -= ((-1) * LR * delta[i]);
        }

       */
        // multipliziere die Delta-Gewichte mit -LR
        NeuralNetworkUtilVic.skalarMatrixMultiplikation((-1) * LR, out_delta_weights);
        //addiere die alten Gewichte mit den Delta-Gewichts-Werten
        newWeights = NeuralNetworkUtilVic.addMatrices(oldweights[oldweights.length - 1], out_delta_weights);
        //überschreibe die alten
        weights[network.length - 2] = newWeights;
        //berechne die Delta-Gewichte für alle restlichen hidden layer
        for (int h = network.length - 2; h > 0; h--) {
            int nexth = h - 1;// index des nächsten hidden layers
            //lege matrix an
            double[][] h_delta_weights = new double[network[nexth].length][network[h].length];
            //speichere alte Gewichte zwischen
            oldweights[h - 1] = weights[h - 1];
            // bias_delta[h-1] = new Neuron[network[h].length];
            // für jedes Element in der Matrix
            for (int i = 0; i < h_delta_weights.length; i++) {
                for (int j = 0; j < h_delta_weights[0].length; j++) {
                    // berechne delta
                    // berechne die Summe von k-ten delta wert mal dem alten Gewicht
                    for (int k = 0; k < delta.length; k++) {
                        h_delta_weights[i][j] += (delta[k] * oldweights[h][j][k]);
                    }
                    // berechne die partielle Ableitung von Ausgangswert nach Aktivierungswert des j-ten Neuron in der h-ten Schicht
                    h_delta_weights[i][j] *= (network[h][j].value * (1.0 - network[h][j].value));
                    //bias_delta[h-1][j] =  new Neuron(1, delta[j]);
                    // multipliziere mit der Aktivierung des i-ten Neurons
                    h_delta_weights[i][j] *= network[h - 1][i].valbeforecomp;
                }
            }
            // multipliziere -LR mal Delta-Gewichtswerte
            NeuralNetworkUtilVic.skalarMatrixMultiplikation((-1) * LR, h_delta_weights);
            // addiere die alten Werte mit den Deltawerten
            newWeights = NeuralNetworkUtilVic.addMatrices(oldweights[h - 1], h_delta_weights);
            // überschreibe
            weights[h - 1] = newWeights;

            /*bias = biases[nexth];
            for (int i = 0; i < bias.length; i++) {
                for (int j = 0; j < network[h].length; j++) {
                    bias[i].bias -= ((-1) * LR * bias_delta[nexth][i].bias);
                }
            }

             */
            //berechne das neue Delta für die nächste versteckte Ebene
            delta = new double[network[nexth].length];
            for (int j = 0; j < network[nexth].length; j++) {
                delta[j] = 0;
                for (int k = 0; k < network[nexth].length; k++) {
                    delta[j] += h_delta_weights[k][j];
                }
                delta[j] *= (network[nexth][j].value * (1.0 - network[nexth][j].value));
            }

       /*     h_bias_delta = new Neuron[biases[h-1].length];
            for (int i = 0; i < h_bias_delta.length; i++) {
                h_bias_delta[i] = new Neuron(1);
                 for (int s = 0; s < delta.length; s++) {
                     h_bias_delta[i].bias += (delta[s] * oldweights[h][i][s]);
                 }
                h_bias_delta[i].bias *= network[h][i].value * (1 - network[h][i].value);
            }
            for (int i = 0; i < h_bias_delta.length; i++) {
                biases[h-1][i].bias -= ((-1) * LR * h_bias_delta[i].bias);
            }
           */
        }
    }

    /**
     * setze ein String[] functions auf die func des NN
     * Struktur von functions:
     * Jeder Index steht für die Aktivierungsfunktion des gesamten i-ten Layers
     * pro Layer eine funktion => könnte man erweitern für jedes Neuron eine andere
     * Bsp: ["","sigmoid", "ReLu"]
     * i=0: die Aktivierungsfunktion ist die id rel
     * i=1: die         "              "  "   Sigmoid Funktion
     * i=2: die         "              "  "  Gleichgerichtete Linearität
     * => noch erweiterbar, sodass jedes Neuron eine eigene Fkt. hat => dann 2D String Feld
     * @param functions Array mit jeweiligen Aktivierungsfunktionen
     */
    public void setFunc(String[] functions) {
        this.func = functions;
    }

    /**
     * @return gibt die biases des NN zurück
     */
    public NeuronVic[][] getBiases() {
        return biases;
    }

    /**
     * @return gibt die weights des NN zurück
     */
    public double[][][] getWeights() {
        return weights;
    }
}
