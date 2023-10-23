package Code;

import java.util.Random;

public class NeuralNetwork {

    Neuron[][] network;
    Double[][][] weights;

    Neuron[][] biases;
    Neuron[] input;
    Neuron[] output;

    String[] func;
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
     * intialisierung der Gewichte mithilfe von csv datei
     * @param
     */
    public void initWeights(Double[][][] weights) {
            this.weights = weights;
    }

    public void initBiases(Neuron[][] biases) {
        this.biases = biases;
    }

    public void compute(double[] dataset) {
        Neuron[] v;
        Double[][] wMatrix;

        for (int i = 0; i < input.length; i++) {
            input[i].value = dataset[i];
        }

        for (int i = 0; i < network.length-1; i++) {
            v = network[i];
            wMatrix = weights[i];
            v = NeuralNetworkUtil.matrixVectorMultiplication(wMatrix, v);
            network[i+1] = NeuralNetworkUtil.addVectors(v, biases[i]);
            for (int j = 0; j < network[i+1].length; j++) {
                network[i+1][j].compute(func[i+1]);
            }
        }
        //output layer aktualisieren
        output = network[network.length-1];
    }

    public void setFunc(String[] functions) {
        this.func = functions;
    }
}
