/**
package Src.Tests;


import Src.NetzVic.NeuralNetworkUtilVic;
import Src.NetzVic.NeuralNetworkVic;
import Src.NetzVic.TrainingDataVic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class NNVic_Test {
    static final String sPath = "training_data_and_layer_config/";
    NeuralNetworkVic network;

    String[][] weighttxt, weight2, weight3;
    String[][] traintxt, train2, train3;

    TrainingDataVic t, t2, t3;

    @BeforeEach
    void init() throws IOException {
        network = new NeuralNetworkVic();
        weighttxt = NeuralNetworkUtilVic.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv");
        traintxt = NeuralNetworkUtilVic.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/traindata_trafficlights.csv");
        train2 = NeuralNetworkUtilVic.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/example1train.csv");
        weight2 = NeuralNetworkUtilVic.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/examplecfg1.csv");
        weight3 = NeuralNetworkUtilVic.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/example2_w.csv");
        train3 = NeuralNetworkUtilVic.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/example2_t.csv");

        t = new TrainingDataVic(weighttxt, traintxt);
        t2 = new TrainingDataVic(weight2, train2);
        t3 = new TrainingDataVic(weight3, train3);
    }

    @Test
    void testCSV() throws IOException {

        NeuralNetworkUtilVic.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv");
        new TrainingDataVic(weighttxt, traintxt);


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
    void testNeuralNetworkVicWithBiases() {

        network.init(weighttxt);
        network.initWeightsBiases(weighttxt);
        network.setFunc(new String[]{"", "sigmoid", ""});
        t.initInputsOutputs();
        network.train(10000, 0.5);
        network.compute(new double[] {0.23, 0.30, 0.78});
        network.printOutput();
        network.compute(new double[]{1,0,0});
        network.printOutput();
    }

    @Test
    void test() throws IOException {

        network.init(weighttxt);
        //network.initWeightsBiases(weighttxt);
      //  t.updateFile(network.getWeights(), network.getBiases());
        String[][] strings = t.getFile();
        NeuralNetworkUtilVic.writeCSV(strings, "/Users/victor/IdeaProjects/Projektseminar/test.csv");
    }

    @Test
    void testTrain() throws IOException {
        double[] i = {0.69, 0.69, 0.69};
        double[] o = {1.0, 1.0, 1.0, 1.0};
        network.init(weighttxt);
        t.initInputsOutputs();
        t.addInputOutput(i, o);
       // t.updateTrainData();
        NeuralNetworkUtilVic.writeCSV(t.getTraindata(), "/Users/victor/IdeaProjects/Projektseminar/test2.csv");

    }

    @Test
    void testMatrixaddition() {
        double[][] m1 = {{1.0, 1.0}, {1.0, 1.0}};
        double[][] m2 = {{2.0, 1.0}, {4.0, 5.0}};
        NeuralNetworkUtilVic.addMatrices(m1, m2);
    }


    @Test
    void testEasy() {
        String[] func = {"sigmoid", "sigmoid", "sigmoid"};
        network.init(weight2);
        network.setFunc(func);
        t2.initInputsOutputs();
        network.initWeightsBiases(weight2);
        network.train(1000, 0.5);
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
        network.train(10000, 0.5);
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
        network.train(100000, 0.5);


    }
    @Test
    void testKorrektheit() throws IOException {
        String[][] part1 = NeuralNetworkUtilVic.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/Korrektheitstrain.csv");
        TrainingDataVic correct = new TrainingDataVic(weighttxt, part1);
        network.init(weighttxt);
        network.initWeightsBiases(weighttxt);
        network.setFunc(new String[]{"", "sigmoid", "sigmoid"});
        correct.initInputsOutputs();
        network.train(10000, 0.5);

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
        TrainingDataVic known = new TrainingDataVic("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv",
                "/Users/victor/IdeaProjects/Projektseminar/src/Korrektheitstrain.csv",
                    3, 4, func);
        TrainingDataVic unknown = new TrainingDataVic(null, "/Users/victor/IdeaProjects/Projektseminar/src/Korrektheitstrain2.csv", 3, 4);
        
        NeuralNetworkVic mNN = new NeuralNetworkVic(known);
        mNN.train(10000, 0.5);
        mNN.computeUnknwonData(unknown);
    }

    @Test
    void testTraining1() throws IOException {
        String[] func = new String[]{"", "sigmoid", "sigmoid"};
        TrainingDataVic train1 = new TrainingDataVic(sPath + "V1_layerConfig.csv",
                sPath + "V1_tData.csv",
                3, 2, func);
        TrainingDataVic train1UnKnown = new TrainingDataVic( null,
                sPath + "V1_tData_unkn.csv",
                3, 2);

        NeuralNetworkVic NN = new NeuralNetworkVic(train1);
        // NN.updateMyTrainingData("V2_layerConfig.csv");
        NN.train(10000, 0.5);
        NN.computeUnknwonData(train1UnKnown);

    }
    @Test
    void testTraining2() throws IOException {
        String[] func = new String[]{"", "sigmoid", "sigmoid", "sigmoid"};
        TrainingDataVic train2 = new TrainingDataVic(sPath + "V2_layerConfig.csv",
                sPath + "V2_tData.csv",
                3, 6, func);
        TrainingDataVic train2UnKnown = new TrainingDataVic( null,
                sPath + "V2_tData_unkn.csv",
                3, 6);

        NeuralNetworkVic NN = new NeuralNetworkVic(train2);
       // NN.updateMyTrainingData("V2_layerConfig.csv");
        NN.train(10000, 0.5);
        NN.computeUnknwonData(train2UnKnown);

    }

    @Test
    void testTraining3() throws IOException {
        String[] func = new String[]{"", "sigmoid", "sigmoid"};
        TrainingDataVic train3 = new TrainingDataVic(sPath + "V3_layerConfig.csv",
                sPath + "V3_tData.csv",
                3, 7, func);
        TrainingDataVic train3UnKnown = new TrainingDataVic( null,
                sPath + "V3_tData_unkn.csv",
                3, 7);

        NeuralNetworkVic NN = new NeuralNetworkVic(train3);
        //NN.updateMyTrainingData("V3_layerConfig.csv");
        NN.train(10000, 0.5);
        NN.computeUnknwonData(train3UnKnown);

    }

    @Test
    void testTraining4() throws IOException {
        String[] func = new String[]{"", "sigmoid", "sigmoid"};
        TrainingDataVic train4 = new TrainingDataVic(sPath + "V4_layerConfig.csv",
                sPath + "V4_tData.csv",
                3, 8, func);
        TrainingDataVic train4UnKnown = new TrainingDataVic( null,
                sPath + "V4_tData_unkn.csv",
                3, 8);

        NeuralNetworkVic NN = new NeuralNetworkVic(train4);
        // NN.updateMyTrainingData("V4_layerConfig.csv");
        NN.train(10000, 0.5);
        NN.computeUnknwonData(train4UnKnown);

    }
}

*/
