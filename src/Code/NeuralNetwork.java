package Code;

import java.util.Random;

public class NeuralNetwork {

    Neuron[][] network;
    Double[][][] weights;
    Neuron[] input;
    Neuron[] output;

    public Neuron[][] getNetwork() {
        return network;
    }

    public void init(int[] layers) {

        network = new Neuron[layers.length][];
        for (int i = 0; i < layers.length; i++) {
            network[i] = new Neuron[layers[i]];
            for (int j = 0; j < network[i].length; j++) {
                network[i][j] = new Neuron();
            }
        }

        input = network[0];
        output = network[network.length-1];

    }

    /**
     * erzeugt die Netztopologie und setzt die Werte auf Zahlen zwischen -1,1
     */
    public void initWeights() {
        Random rand = new Random();
        int dim = network.length-1;

        //intialisiere die Dimension
        //1. Dim = Netztiefe -1
        weights = new Double[dim][][];
        for (int i = 0; i < dim; i++) {
            //2./3.Dim: Länge des iten Layers und Länge des i+1ten Layers
            weights[i] = new Double[network[i].length][network[i+1].length];
        }
        //initialiersiere die weights mit random werten zwischen -1, 1
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {
                    weights[i][j][k] = -1 + (2 * rand.nextDouble());
                }
            }
        }
    }

    /**
     * Methode zum setzen aller weights auf einen bestimmten Wert val
     * @param val Wert auf den alle weights gesetzt werden soll
     */
    public void initWeights(double val) {
        initWeights();
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {
                    weights[i][j][k] = val;
                }
            }
        }
    }

    /**
     * intialisierung der Gewichte mithilfe von csv datei
     * @param csvPath
     */
    public void initWeights(String csvPath) {

    }

    public void compute(double[] dataset) {
        Neuron[] v;
        Double[][] wMatrix;

        setInput(dataset);

        for (int i = 0; i < network.length-1; i++) {
            v = network[i];
            wMatrix = weights[i];
            network[i+1] = matrixVectorMultiplication(wMatrix, v);
            for (int j = 0; j < network[i+1].length; j++) {
                network[i+1][j].compute();
            }
        }
        //output layer aktualisieren
        output = network[network.length-1];
    }

    private void setInput(double[] dataset) {
        for (int i = 0; i < input.length; i++) {
            input[i].value = dataset[i];
        }
    }

    /**
     * methode zur Matrix Vector multiplikation
     * mxn * nx1= mx1   {{1,2,3},
     *                   {2,3,4}}
     * @param matrix
     * @param v
     * @return
     */
    private Neuron[] matrixVectorMultiplication(Double[][] matrix, Neuron[] v) {
        Neuron[] res = new Neuron[matrix[0].length];
        //prüfe ob Anzahl der Spalten = Anzahl Zeilen von Vektor
        if (matrix.length != v.length) {
            System.out.println("n nicht gleich n");
        }


        for (int i = 0; i < matrix[0].length; i++) {
            res[i] = new Neuron();
            for (int j = 0; j < matrix.length ; j++) {
                res[i].value += matrix[j][i] * v[j].value;
            }
        }
        return res;
    }

    public Neuron[] getInput() {
        return input;
    }

    public Neuron[] getOutput() {
        return output;
    }
}
