package Src;

import java.io.*;
import java.util.*;



public class Layer {
    public Neuron neurons[];

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
    public Layer(int numberOfWeights, int numberOfNeurons, float[][] weights2d, float bias) {
        this.neurons = new Neuron[numberOfNeurons];

        for (int i = 0; i < numberOfNeurons; i++) {
            float[] weights = new float[numberOfWeights];
            for (int j = 0; j < numberOfWeights; j++) {
                // weights[j] = NeuralUtil.RandomFloatNum(Neuron.minWeight, Neuron.maxWeight);
            }
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

    /** read csv to get Neuron Weight and Bias Config */
    public void readCsvWeights(String path) {
        //String sPath = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\Src\\KW43_weights_trafficlights_classification_simplified.csv";
        String sPath = "/Users/samuraireise/Documents/HBRS/neural network/HBRS-neural-network/Src/KW43_weights_trafficlights_classification_simplified.csv";
        String line;
        String layerConfig;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path)); // for testing: might use sPath
            layerConfig = bufferedReader.readLine();
            System.out.println(layerConfig);
            while((line = bufferedReader.readLine()) != null) {
                //line = bufferedReader.readLine();
                System.out.println(line);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
