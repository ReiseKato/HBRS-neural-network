package Src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class NeuralNetwork {
    static Layer[] layers_t;
    static TrainingData[] trainingData_t;
    static int[] layerConfig;



    public static void main(String[] args) {
        int numberOfLayers;
        int[] numberOfNeurons;
        int[] numberOfWeights;
        String sPath = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\Src\\KW43_weights_trafficlights_classification_simplified.csv"; // Layer, Weight and Bias
        String sPathForTData = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\Src\\KW43_traindata_trafficlights_classification.csv"; // Training Data

        boolean printMatirx = true;

        // for triple logical statement
//        numberOfLayers = 6;
//        numberOfNeurons = new int[]{20, 30, 20, 5, 1}; // first after input hast 6 neurons (input Layer is not included)
//        numberOfWeights = new int[]{8, 20, 30, 20, 5}; // first after input one gets two weights

//        numberOfLayers = 3;
//        numberOfNeurons = new int[]{8, 1};
//        numberOfWeights = new int[]{2, 8};


        Neuron.setWeightRange(-1, 1);


        // createLayers(numberOfLayers, numberOfWeights, numberOfNeurons);
        createLayers(sPath);
        getTrainingData(sPathForTData);



        for(int i = 1; i < layers_t.length; i++) {
            for(int j = 0; j < layers_t[i].neurons.length; j++) {
                float[] weights;
                float bias;
                float[] weightsAndBias = NeuralUtil.getSpecificWeights(NeuralUtil.readWeightsAndBias(sPath),
                        layerConfig, j, i);
                weights = Arrays.copyOfRange(weightsAndBias, 0, weightsAndBias.length - 1);
                bias = weightsAndBias[weightsAndBias.length - 1];
                layers_t[i].neurons[j].setWeights(weights);
                layers_t[i].neurons[j].setBias(bias);
            }
        }



        for(int i = 0; i < trainingData_t.length; i++) {
            run(trainingData_t[i].inputData);
            System.out.println("\nInput: " + i);
            for(int j = 0; j < layers_t[layers_t.length - 1].neurons.length; j++) {
                System.out.println(layers_t[layers_t.length - 1].neurons[j].value);
            }
        }


        // train(10000000, 0.05f);


//        for(int i = 1; i < layers_t.length; i++) {
//            System.out.println("\nLayer: " + i);
//            for(int j = 0; j < layers_t[i].neurons.length; j++) {
//                System.out.println("\nNeuron: " + j);
//                for(int k = 0; k < layers_t[i].neurons[j].weights.length; k++) {
//                    System.out.println(layers_t[i].neurons[j].weights[k]);
//                }
//            }
//        }


//        if(printMatirx==true) {
//            int sumOfNeurons = 0;
//            int[] layerConfigMat = NeuralUtil.getlayerConfig(sPath);
//            for(int i = 0; i < layerConfigMat.length; i++) {
//                sumOfNeurons += layerConfigMat[i] + 1;
//            }
//            float[][] weightsAndBiasMatrix = new float[sumOfNeurons][Arrays.stream(layerConfigMat).max().getAsInt()];
//
//            for(int i = 0; i < layers_t.length; i++) {
//                for(int j = 0; j < layers_t[i].neurons.length; j++) {
//                    for(int k = 0; k < layers_t[i].neurons[k].weights.length; k++) {
//                        weightsAndBiasMatrix[j][k] = layers_t[i].neurons[k].weights[j];
//                    }
//                }
//            }
//
//            for(float[] weight : weightsAndBiasMatrix) {
//                System.out.println(Arrays.toString(weight));
//            }
//        }

        PrintWriter printWriter;
//        float[][] weights = {{0.2f, 0.5f, 0.7f},
//                {0.8f, 0.9f, 0.0f}};

        try {
            File csvFile = new File("weights.csv");
            printWriter = new PrintWriter(csvFile);

            for(int i = 1; i < layers_t.length; i++) { // i = 1 because input Layer doesn't have weights
                printWriter.println("\nLayer: " + i);
                for(int j = 0; j < layers_t[i].neurons.length; j++) {
                    printWriter.println("Neuron: " + j);
                    printWriter.println(Arrays.toString(layers_t[i].neurons[j].weights) + ";" +
                            layers_t[i].neurons[j].bias);
                }
            }

            printWriter.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

    }



    public static void getTrainingData(String path) {
        int numberOfTrainingData = NeuralUtil.getTrainingInputCount(path);
        trainingData_t = new TrainingData[numberOfTrainingData];

        for(int i = 0; i < numberOfTrainingData; i++) {
            trainingData_t[i] = new TrainingData(NeuralUtil.getTrainingInputData(path, layerConfig, i));
        }
    }


    public static void getTrainingData() {
//        float[] input0 = new float[] {1, 0, 0}; // expect {1, 0, 0, 0}
//        float[] input1 = new float[] {0.8f, 0.0f, 0.1f}; // expect {1, 0, 0, 0}
//
//        float[] expectedResult0 = new float[] {1, 0, 0, 0};
//        float[] expectedResult1 = new float[] {1, 0, 0, 0};
//
//        trainingData_t = new TrainingData[2];
//        trainingData_t[0] = new TrainingData(input0, expectedResult0);
//        trainingData_t[1] = new TrainingData(input1, expectedResult1);

//        float[] input0 = new float[] {0, 0}; // expect 0
//        float[] input1 = new float[] {0, 1}; // expect 1
//        float[] input2 = new float[] {1, 0}; // expect 1
//        float[] input3 = new float[] {1, 1}; // expect 1
//
//        float[] expectedResult0 = new float[] {0};
//        float[] expectedResult1 = new float[] {1};
//        float[] expectedResult2 = new float[] {1};
//        float[] expectedResult3 = new float[] {1};
//
//        // initialize Training Data
//        trainingData_t = new TrainingData[4];
//        trainingData_t[0] = new TrainingData(input0, expectedResult0);
//        trainingData_t[1] = new TrainingData(input1, expectedResult1);
//        trainingData_t[2] = new TrainingData(input2, expectedResult2);
//        trainingData_t[3] = new TrainingData(input3, expectedResult3);

        // still having problems with triple logical statement
//        float[] input0 = new float[] {0, 0, 0}; // expect 0
//        float[] input1 = new float[] {0, 0, 1}; // expect 1
//        float[] input2 = new float[] {0, 1, 0}; // expect 0
//        float[] input3 = new float[] {0, 1, 1}; // expect 1
//        float[] input4 = new float[] {1, 0, 0}; // expect 0
//        float[] input5 = new float[] {1, 0, 1}; // expect 1
//        float[] input6 = new float[] {1, 1, 0}; // expect 1
//        float[] input7 = new float[] {1, 1, 1}; // expect 1
//
//        float[] expectedResult0 = new float[] {0};
//        float[] expectedResult1 = new float[] {1};
//        float[] expectedResult2 = new float[] {0};
//        float[] expectedResult3 = new float[] {1};
//        float[] expectedResult4 = new float[] {0};
//        float[] expectedResult5 = new float[] {1};
//        float[] expectedResult6 = new float[] {1};
//        float[] expectedResult7 = new float[] {1};
//
//        trainingData_t = new TrainingData[8];
//        trainingData_t[0] = new TrainingData(input0, expectedResult0);
//        trainingData_t[1] = new TrainingData(input1, expectedResult1);
//        trainingData_t[2] = new TrainingData(input2, expectedResult2);
//        trainingData_t[3] = new TrainingData(input3, expectedResult3);
//        trainingData_t[4] = new TrainingData(input4, expectedResult4);
//        trainingData_t[5] = new TrainingData(input5, expectedResult5);
//        trainingData_t[6] = new TrainingData(input6, expectedResult6);
//        trainingData_t[7] = new TrainingData(input7, expectedResult7);
    }

    /**
     * quite useful for trainable kNN because I don't have to initialize all weights manually
     *          --> just use random floats :)
     */
    public static void createLayers(int numberOfLayers, int[] numberOfWeights, int[] numberOfNeurons) { // parse input data, how many layers, how many weights, how many neurons in each layer
        layers_t = new Layer[numberOfLayers];

        // layers_t[0] = new Layer(input); // very first Layer aka input Layer (commented because makes no sense to parse data here. just create Layers in function)
        for(int i = 1; i < numberOfLayers; i++) { //create all hidden Layers and output Layer
            layers_t[i] = new Layer(numberOfWeights[i - 1], numberOfNeurons[i - 1]);
        }
    }

    /** manual NN creation */
    public static void createLayers(String path) {
        layerConfig = NeuralUtil.getlayerConfig(path);
        int[] numberOfWeights = Arrays.copyOfRange(layerConfig, 0, layerConfig.length - 1);
        int[] numberOfNeurons = Arrays.copyOfRange(layerConfig, 1, layerConfig.length);
        int numberOfLayers = layerConfig.length;
        layers_t = new Layer[layerConfig.length];

        for(int i = 1; i < numberOfLayers; i++) {
            layers_t[i] = new Layer(numberOfWeights[i - 1], numberOfNeurons[i - 1]);
        }
    }

    /** basically parse the weights, calculate the values. voila! */
    public static void run(float[] input) {
        layers_t[0] = new Layer(input);
        float sum;

        for(int i = 1; i < layers_t.length; i++) { // i: index for layer (i = current layer, i - 1 = layer to get values from)
            for(int j = 0; j < layers_t[i].neurons.length; j++) {
                sum = 0;
                for(int k = 0; k < layers_t[i - 1].neurons.length; k++) {
                    sum += layers_t[i].neurons[j].weights[k]*layers_t[i - 1].neurons[k].value;
                }
                sum += layers_t[i].neurons[j].bias;
                layers_t[i].neurons[j].value = Neuron.SigmoidFunction(sum);
                // layers_t[i].neurons[j].value = Neuron.ReLu(sum); // ReLu -> not fixed yet
//                if(i != layers_t.length - 2) {
//                    layers_t[i].neurons[j].value = Neuron.SigmoidFunction(sum);
//                } else {
//                    layers_t[i].neurons[j].value = sum;
//                }
            }
        }
    }

    /** training rate:  optional. can be used to determine how fast the model should train.
     *                  too low -> can get stuck | too high ->  can cause the model to converge too quickly to a suboptimal solution
     */
    public static void backpropagation(TrainingData __trainingData_t, float trainingRate) {
        float out = 0.0f;
        float target = 0.0f;
        float errorTotal = 0.0f;
        float deltaOfValue = 0.0f;
        float deriviate = 0.0f;
        float delta;
        float deltaErr = 0.0f; //should be renamed
        float oldWeight;

        // loop for optimizing the output Layer (-> already in output Layer (note for Reise))
        for(int i = 0; i < layers_t[layers_t.length - 1].neurons.length; i++) { // i: indexing for Neurons in output Layer. loop while i < number of neurons in output Layer
            out = layers_t[layers_t.length - 1].neurons[i].value;
            target = __trainingData_t.expectedResult[i];
            // errorTotal += 0.5*(out -  target)*(out* - target);
            deltaOfValue = out - target; // output - target (M. Mazur)
            deriviate = out*(1 - out);
            delta = deriviate*deltaOfValue;
            layers_t[layers_t.length - 1].neurons[i].gradient = delta;
            for(int j = 0; j < layers_t[layers_t.length - 1].neurons[i].weights.length; j++) { // j: indexing for Neurons of exactly 1 Layer before output Layer. iterate as often as values each Neuron in output Layer gets
                deltaErr = delta*layers_t[layers_t.length - 2].neurons[j].value;
                oldWeight = layers_t[layers_t.length - 1].neurons[i].weights[j];
                layers_t[layers_t.length - 1].neurons[i].newWeights[j] = oldWeight - trainingRate*deltaErr;
                // layers_t[layers_t.length - 1].neurons[i].weights[j] = oldWeight - trainingRate*deltaErr;
            }
        }

        float gradientSum;
        // loop for optimizing the hidden Layers -> has to go backwards
        for(int i = layers_t.length - 2; i > 0; i--) { // i; indexing for Layers
            //out = layers_t[i].neurons[]
            for(int j = 0; j < layers_t[i].neurons.length; j++) { // indexing for Neurons
                gradientSum = gradientSum(i + 1, j); // i+1: needs the outputs of the Layer before
                out = layers_t[i].neurons[j].value;
                delta = gradientSum*(out*(1 - out)); // delta of Error to Weight
                layers_t[i].neurons[j].gradient = delta; // just assuming it because I need to update the gradient and that's the best value I can find
                // loop for updating all weights for each Neuron
                for(int k = 0; k < layers_t[i].neurons[j].weights.length; k++) { // k: indexing for weights
                    /*if training via output, use code below till block comment*/
                    deltaErr = layers_t[i - 1].neurons[k].value*delta;
                    layers_t[i].neurons[j].newWeights[k] = layers_t[i].neurons[j].weights[k] - trainingRate*deltaErr;

                    /*if it needs to train via weight, use the upper code below*/
                    // layers_t[i].neurons[j].newWeights[k] = layers_t[i].neurons[j].weights[k] - trainingRate*delta;
                    // layers_t[i].neurons[j].weights[k] = layers_t[i].neurons[j].weights[k] - trainingRate*delta;
                }
            }
        }

        updateAllWeights();
    }

    public static float gradientSum(int indexCurrentLayer, int indexCurrentNeuron) { // maybe float gradient has to be given as Parameter -> test
        float sum = 0.0f;
        Layer currentLayer = layers_t[indexCurrentLayer];
        for(int i = 0; i < currentLayer.neurons.length; i++) {
            Neuron currentNeuron = currentLayer.neurons[i];
            sum += currentNeuron.gradient*currentNeuron.weights[indexCurrentNeuron]; // tried a bunch of different values -> weights seems to work so far
        }
        return sum;
    }

    public static void updateAllWeights() {
        for(int i = 0; i < layers_t.length; i++) { // i: indexing for Layers
            for(int j = 0; j < layers_t[i].neurons.length; j++) {
                layers_t[i].neurons[j].updateWeights();
            }
        }
    }

    /** calculate the appropriate output for each input */
    public static void train(int countOfTraining, float trainingRate) {
        for(int i = 0; i < countOfTraining; i++) {
            for(int j = 0; j < trainingData_t.length; j++) {
                run(trainingData_t[j].inputData);
                backpropagation(trainingData_t[j], trainingRate);
            }
        }
    }
}
