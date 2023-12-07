package Tests;

import Code.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class NNTest {
    NeuralNetwork network;

    String[][] weighttxt, weight2, weight3;
    String[][] traintxt, train2, train3;

    TrainingData t, t2, t3;

    @BeforeEach
    void init() throws IOException {
        network = new NeuralNetwork();
        weighttxt = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv");
        traintxt = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/traindata_trafficlights.csv");
        train2 = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/example1train.csv");
        weight2 = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/examplecfg1.csv");
        weight3 = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/example2_w.csv");
        train3 = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/example2_t.csv");

        t = new TrainingData(weighttxt, traintxt);
        t2 = new TrainingData(weight2, train2);
        t3 = new TrainingData(weight3, train3);
    }

    @Test
    void testCSV() throws IOException {

        NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv");
        new TrainingData(weighttxt, traintxt);


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
        network.initWeightsBiases();
        network.compute(randomArray);
    }

    @Test
    void testNeuro2() {
        int[] ar = {3, 2, 3};
        double[] data = {0.8, 0.7, 0.6};
        network.init(ar);
        network.compute(data);
    }

    @Test
    void testNeuron() {
        int[] ar = {2, 1};
        double[] data = {0.0, 0.0};
        network.init(ar);
        network.compute(data);


    }


    @Test
    void testNeuralNetworkWithBiases() {

        network.init(weighttxt);
        network.initWeightsBiases(weighttxt);
        network.setFunc(new String[]{"", "sigmoid", ""});
        t.initInputsOutputs();
        network.train(50000);
        network.compute(new double[] {0.23, 0.30, 0.78});
        network.printOutput();
        network.compute(new double[]{1,0,0});
        network.printOutput();
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
        double[] i = {0.69, 0.69, 0.69};
        double[] o = {1.0, 1.0, 1.0, 1.0};
        network.init(weighttxt);
        t.initInputsOutputs();
        t.addInputOutput(i, o);
        t.updateTrainData();
        NeuralNetworkUtil.writeCSV(t.getTraindata(), "/Users/victor/IdeaProjects/Projektseminar/test2.csv");

    }

    @Test
    void testMatrixaddition() {
        double[][] m1 = {{1.0, 1.0}, {1.0, 1.0}};
        double[][] m2 = {{2.0, 1.0}, {4.0, 5.0}};
        NeuralNetworkUtil.addMatrices(m1, m2);
    }


    @Test
    void testEasy() {
        String[] func = {"sigmoid", "sigmoid", "sigmoid"};
        network.init(weight2);
        network.setFunc(func);
        t2.initInputsOutputs();
        network.initWeightsBiases(weight2);
        network.train(1000);
        network.compute(new double[]{0.05, 0.10});
        network.printOutput();

    }


    @Test
    void testNewTry() {
        String[] func = {"", "sigmoid", "sigmoid"};
        network.init(weight3);
        network.setFunc(func);
        t2.initInputsOutputs();
        network.initWeightsBiases(weight3);
        network.train(10000);
        network.compute(new double[] {0, 0.2, 0.62, 1, 0.62});
        network.printOutput();

    }

    @Test
    void test3() {
        String[] func = {"sigmoid", "sigmoid", "sigmoid", "sigmoid"};
        network.init(new int[]{3, 3, 3, 4});
        network.setFunc(func);
        t.initInputsOutputs();
        network.initWeightsBiases();
        network.train(100000);


    }
    @Test
    void testKorrektheit() throws IOException {
        String[][] part1 = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/Korrektheitstrain.csv");
        TrainingData correct = new TrainingData(weighttxt, part1);
        network.init(weighttxt);
        network.initWeightsBiases(weighttxt);
        network.setFunc(new String[]{"", "sigmoid", "sigmoid"});
        correct.initInputsOutputs();
        network.train(1000000);

        network.compute(new double[] {0.99,0.1,0});//1;0;0;0
        network.printOutput();
        network.compute(new double[] {1.1,0,0.01});//1;0;0;0
        network.printOutput();

        network.compute(new double[] {1.1,0.9,0});//0;1;0;0
        network.printOutput();
        network.compute(new double[] {1,1,0.1});//0;1;0;0
        network.printOutput();

        network.compute(new double[] {0,0.1,1.1});//0;0;1;0
        network.printOutput();
        network.compute(new double[] {0.1,0,1});//0;0;1;0
        network.printOutput();
        
        network.compute(new double[] {0.01,1.1,0.1});//0;0;0;1
        network.printOutput();
        network.compute(new double[] {0,0.99,-0.01});//0;0;0;1
        network.printOutput();

        network.compute(new double[] {0.0,0.48,0.45});
        network.printOutput();

    }

    @Test
    void quickTrain() throws IOException {
        String[] func = new String[]{"", "sigmoid", "sigmoid"};
        TrainingData known = new TrainingData("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv",
                "/Users/victor/IdeaProjects/Projektseminar/src/Korrektheitstrain.csv",
                    3, 4, func);
        TrainingData unknown = new TrainingData(null, "/Users/victor/IdeaProjects/Projektseminar/src/Korrektheitstrain2.csv", 3, 4);
        
        NeuralNetwork mNN = new NeuralNetwork(known);
        mNN.train(10000);
        mNN.computeUnknwonData(unknown);
    }
}
