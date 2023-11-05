package Src;

import java.io.*;
import java.util.*;



public class Layer {
    public Neuron neurons[]; // array to keep all the Neurons of a Layer in place

    /** for hidden layer and output layer (automated) */
    public Layer(int numberOfWeights, int numberOfNeurons) {
        this.neurons = new Neuron[numberOfNeurons];

        for (int i = 0; i < numberOfNeurons; i++) {
            float[] weights = new float[numberOfWeights];
            for (int j = 0; j < numberOfWeights; j++) {
                weights[j] = NeuralUtil.RandomFloatNum(Neuron.minWeight, Neuron.maxWeight);
            }
            neurons[i] = new Neuron(weights, NeuralUtil.RandomFloatNum(0, 1)); // get random number for bias between 0 and 1
        }
    }

    /** for hidden layer and output layer (manual) */
    public Layer(int numberOfWeights, int numberOfNeurons, int num) {
        this.neurons = new Neuron[numberOfNeurons];

        for (int i = 0; i < numberOfNeurons; i++) {
            float[] weights = new float[numberOfWeights];
//            for (int j = 0; j < numberOfWeights; j++) {
//                // weights[j] = NeuralUtil.RandomFloatNum(Neuron.minWeight, Neuron.maxWeight);
//            }
            neurons[i] = new Neuron(weights, NeuralUtil.RandomFloatNum(0, 1)); // get random number for bias between 0 and 1
        }
    }

    /** for input layer -> input[] is TrainingData.inputData */
    public Layer(float input[]) {
        this.neurons = new Neuron[input.length];
        for(int i = 0; i < input.length; i++) {
            this.neurons[i] = new Neuron(input[i]);
        }
    }


}
