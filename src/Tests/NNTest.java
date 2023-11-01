package Tests;

import Code.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class NNTest {
    NeuralNetwork network;

    String[][] weighttxt, weight2;
    String[][] traintxt, train2;

    TrainingData t;

    @BeforeEach
    void init() throws IOException {
        network = new NeuralNetwork();
        weighttxt = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv");
        traintxt = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/traindata_trafficlights.csv");
        train2 = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/example1train.csv");
        weight2 = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/examplecfg1.csv");
        //t = new TrainingData(weighttxt, traintxt);
        t = new TrainingData(weight2, train2);

    }

    @Test
    void testCSV() throws IOException {

        String[][] s = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv");
        TrainingData t = new TrainingData(weighttxt, traintxt);


    }

    @Test
    void testbig() {
        int[] test = {1600, 64, 64, 30};
        int arraySize = 1600;
        double[] randomArray = new double[arraySize];
        for (int i = 0; i < arraySize; i++) {
            randomArray[i] = Math.random(); // Erzeugt einen zufälligen Double-Wert zwischen 0 (einschließlich) und 1 (ausschließlich)
        }


        network.init(test);
        network.initWeights();
        //network.compute(randomArray);
    }

    @Test
    void testNeuro2() {
        int[] ar = {3, 2, 3};
        Double[] data = {0.8, 0.7, 0.6};
        network.init(ar);
        network.compute(data);
    }

    @Test
    void testNeuron() {
        int[] ar = {2, 1};
        Double[] data = {0.0, 0.0};
        network.init(ar);
        network.compute(data);


    }


    @Test
    void testNeuralNetworkWithBiases() throws IOException, InterruptedException {

        network.init(weighttxt);
        network.initWeightsBiases(weighttxt);
        network.setFunc(new String[]{"", "sigmoid", ""});
        t.initInputsOutputs();
        network.train();
        //ArrayList<Double> er = network.train();
        //     System.out.println(er.size());
        //   System.out.println(er.get(0));
        // System.out.println(er.get(er.size()-1));

        //      network.compute(new double[]{1,0,0});

     /*   Neuron[] n = network.output;
        for (int i = 0; i < n.length; i++) {
            System.out.println(n[i].getValue());
        }

      */


    }

    @Test
    void test() throws IOException {

        network.init(weighttxt);
        network.initWeightsBiases(weighttxt);
        t.updateFile(network.getWeights(), network.getBiases());
        String[][] strings = t.getFile();
        NeuralNetworkUtil.writeCSV(strings, "/Users/victor/IdeaProjects/Projektseminar/test.csv");
    }

    @Test
    void testTrain() throws IOException {
        Double[] i = {0.69, 0.69, 0.69};
        Double[] o = {1.0, 1.0, 1.0, 1.0};
        network.init(weighttxt);
        t.initInputsOutputs();
        t.addInputOutput(i, o);
        t.updateTrainData();
        NeuralNetworkUtil.writeCSV(t.getTraindata(), "/Users/victor/IdeaProjects/Projektseminar/test2.csv");

    }

    @Test
    void testPropa() {
        Neuron[] temp = new Neuron[3];
        temp[0] = new Neuron(-0.33);
        temp[1] = new Neuron(0.53);
        temp[2] = new Neuron(0.52);

        Double[][] ma = new Double[1][4];
        ma[0][0] = 0.10;
        ma[0][1] = 0.62;
        ma[0][2] = 0.21;
        ma[0][3] = 0.62;
        Double[][] test = NeuralNetworkUtil.vectorMatrixMultiplication(temp, ma);
        //     int i = test.length;
        Double[][] res = NeuralNetworkUtil.transposeMatrix(test);

    }

    @Test
    void testMatrixaddition() {
        Double[][] m1 = {{1.0, 1.0}, {1.0, 1.0}};
        Double[][] m2 = {{2.0, 1.0}, {4.0, 5.0}};
        Double[][] res = NeuralNetworkUtil.addMatrices(m1, m2);
    }


    @Test
    void tesMattMazur() {
        String[] func = {"", "sigmoid", "sigmoid"};
        network.init(weight2);
        network.setFunc(func);
        t.initInputsOutputs();
        network.initWeightsBiases(weight2);
        network.train();
        network.compute(new Double[]{0.05, 0.10});

    }
}
