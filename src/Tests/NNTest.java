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

    String[][] weighttxt;
    String[][] traintxt;

    TrainingData t;
    @BeforeEach
    void init() throws IOException {
        network = new NeuralNetwork();
        weighttxt =NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv");
        traintxt = NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/traindata_trafficlights.csv");
        t = new TrainingData(weighttxt, traintxt);
    }

    @Test
    void testCSV() throws IOException {

        String[][] s =NeuralNetworkUtil.readCSV("/Users/victor/IdeaProjects/Projektseminar/src/weight_trafficlights.csv");
        TrainingData t = new TrainingData(weighttxt, traintxt);

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
        int[] ar = {2, 1};
        double[] data = {0, 0};
        network.init(ar);
        network.compute(data);


    }


    @Test
    void testNeuralNetworkWithBiases() throws IOException {

        network.init(weighttxt);
        network.initWeightsBiases(weighttxt);
        network.setFunc(new String[] {"","sigmoid",""});
        t.initInputsOutputs();
        network.train();

    }

    @Test
    void test() throws IOException {

        network.init(weighttxt);
        network.initWeightsBiases(weighttxt);
        t.updateFile(network.getWeights(), network.getBiases());
        String[][] strings = t.getFile();
        NeuralNetworkUtil.writeCSV(strings,"/Users/victor/IdeaProjects/Projektseminar/test.csv" );
    }

    @Test
    void testTrain() throws IOException {
        Double[] i = {0.69,0.69,0.69};
        Double[] o = {1.0,1.0,1.0,1.0};
        network.init(weighttxt);
        t.initInputsOutputs();
        t.addInputOutput(i, o);
        t.updateTrainData();
        NeuralNetworkUtil.writeCSV(t.getTraindata(), "/Users/victor/IdeaProjects/Projektseminar/test2.csv");

    }
}
