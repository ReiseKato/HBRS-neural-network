package Tests;

import Code.NeuralNetwork;
import Code.NeuralNetworkUtil;
import Code.Neuron;
import Code.TrainingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class NNTest {
    NeuralNetwork network;

    @BeforeEach
    void init() {
        network = new NeuralNetwork();
    }

    @Test
    void testCSV() throws IOException {

        String[][] s =NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv");
        TrainingData t = new TrainingData(s);
        t.determineLayout();
        t.determineWeightsBiases();
    }

    @Test
    void testbig() {
        int[] test = {1600,64,64,30};
        int arraySize = 1600;
        double[] randomArray = new double[arraySize];
        for (int i = 0; i < arraySize; i++) {
            randomArray[i] = Math.random(); // Erzeugt einen zufälligen Double-Wert zwischen 0 (einschließlich) und 1 (ausschließlich)
        }


        network.init(test);
        network.initWeights();
        network.compute(randomArray);
    }
    @Test
    void testNeuro2() {
        int[] ar = {3,2,3};
        double[] data = {0.8, 0.7, 0.6};
        network.init(ar);
        network.compute(data);
    }
    @Test
    void testNeuron() {
        int[] ar = {2,1};
        double[] data = {0,0};
        network.init(ar);
        network.compute(data);



    }
    @Test
    void testMatrixVector() {
        Double[][] matrix = {{1.0, 2.0, 3.0},
                             {4.0, 5.0, 6.0}};
        double z = 7.0;
        Neuron[] v = new Neuron[3];
        for (int i = 0; i < v.length; i++) {
            v[i] = new Neuron();
            //v[i].setValue(z);
            z++;
        }

    //    Neuron[] res = network.matrixVectorMultiplication(matrix, v);

     //   assertTrue(res[0].getValue()== 50);
   //     assertTrue(res[1].getValue()== 122);

        Double[][] matrix2 = {{1.0,1.0}};
        Neuron[] v2 = new Neuron[2];
        for (int i = 0; i < v2.length; i++) {
            v2[i] = new Neuron();
         //   v2[i].setValue(0);
        }
     //   res = network.matrixVectorMultiplication(matrix2, v2);

      //
        //  assertTrue(res[0].getValue()== 0);
    }


    @Test
    void testNeuralNetworkWithBiases() throws IOException {
        String[][] s =NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv");
        TrainingData t = new TrainingData(s);
        t.determineLayout();
        t.determineWeightsBiases();
        network.init(t.getLayout());
        network.initWeights(t.getWeightcfg());
        network.initBiases(t.getBiases());
        network.setFunc(new String[] {"","sigmoid",""});
        network.compute(new double[]{1.0, 0, 0});
    }

    @Test
    void test() throws IOException {
        String[][] s =NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv");
        TrainingData t = new TrainingData(s);
        t.determineLayout();
        t.determineWeightsBiases();
        t.updateFile();
        String[][] strings = t.getFile();
        NeuralNetworkUtil.writeCSV(strings,"/Users/victor/IdeaProjects/Projektseminar/test.csv" );
    }

    @Test
    void testTrain() throws IOException {
        String[][] st =NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv");

        String[][] s = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/traindata_trafficlights.csv");
        TrainingData t = new TrainingData(st);
        t.determineLayout();
        t.setTrainData(s);
        t.setTrain();
        Double[] i = {0.69,0.69,0.69};
        Double[] o = {1.0,1.0,1.0,1.0};
        t.addInputOutput(i, o);
        t.updateTrainData();
        NeuralNetworkUtil.writeCSV(t.getTraindata(), "/Users/victor/IdeaProjects/Projektseminar/test2.csv");

    }
}
