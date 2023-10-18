package Tests;

import Code.NeuralNetwork;
import Code.Neuron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.NoopProcessorErrorHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NNTest {
    NeuralNetwork network;

    @BeforeEach
    void init() {
        network = new NeuralNetwork();
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
        network.initWeights(1.0);
        network.compute(data);
        network.getInput();
        network.getOutput();
    }
    @Test
    void testNeuron() {
        int[] ar = {2,1};
        double[] data = {0,0};
        network.init(ar);
        network.initWeights(1.0);
        network.compute(data);
        network.getInput();
        network.getOutput();


    }
    @Test
    void testMatrixVector() {
        Double[][] matrix = {{1.0, 2.0, 3.0},
                             {4.0, 5.0, 6.0}};
        double z = 7.0;
        Neuron[] v = new Neuron[3];
        for (int i = 0; i < v.length; i++) {
            v[i] = new Neuron();
            v[i].setValue(z);
            z++;
        }

    //    Neuron[] res = network.matrixVectorMultiplication(matrix, v);

     //   assertTrue(res[0].getValue()== 50);
   //     assertTrue(res[1].getValue()== 122);

        Double[][] matrix2 = {{1.0,1.0}};
        Neuron[] v2 = new Neuron[2];
        for (int i = 0; i < v2.length; i++) {
            v2[i] = new Neuron();
            v2[i].setValue(0);
        }
     //   res = network.matrixVectorMultiplication(matrix2, v2);

      //
        //  assertTrue(res[0].getValue()== 0);
    }
}
