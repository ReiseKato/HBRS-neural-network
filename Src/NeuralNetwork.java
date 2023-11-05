package Src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class NeuralNetwork {
    static Layer[] layers_t; // array to keep the Neural Network in place
    static TrainingData[] trainingData_t; // array of all the training data given by the user
    static int[] layerConfig; // array for the layer configuration (example: [3,3,4]
    static double totalErrorPrior; // made for optimizing the neural Network, but still not working
    static double totalErrorCurrent; // made for optimizing the neural Network, but still not working
    static List<Double> totalError = new ArrayList<Double>(); // list for keeping all the total Error --> easier to handle as a list than an array, if over 100000 elements



    public static void main(String[] args) {
        int numberOfLayers;
        int[] numberOfNeurons;
        int[] numberOfWeights;
        // paths to needed files
        // USER has to edit these paths to his/her own file path
        String sPath = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\Src\\KW43_weights_trafficlights_classification_simplified.csv"; // Layer, Weight and Bias
        String sPathForTData = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\Src\\KW43_traindata_trafficlights_classification.csv"; // Training Data

        String sPathBestWeights = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\weights.csv"; // best weights
        String sPathKnownTData = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\known_training_data.csv"; // known Training Data
        String sPathUnknownData = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\unknown_data.csv"; // unknown for checking implementation

        boolean printMatirx = true;

        Neuron.setWeightRange(-1, 1);

        // if using whole dataset
        createLayers(sPath);
        getTrainingDataLearnable(sPathForTData);
        weightAndBiasConfig(sPath);

        // if using only specifc datase --> same as whole set
//        createLayers(sPath);
//        getTrainingDataLearnable(sPathKnownTData);
//        weightAndBiasConfig(sPath);

        // if checking with unknown dataset
//        createLayers(sPathBestWeights);
//        getTrainingData(sPathUnknownData);
//        weightAndBiasConfig(sPathBestWeights);


        train(10000, 0.05f); // 10000 iterations for each dataset

        for(int i = 0; i < trainingData_t.length; i++) {
            run(trainingData_t[i].inputData);
            System.out.println("\nInput: " + i);
            for(int j = 0; j < layers_t[layers_t.length - 1].neurons.length; j++) {
                System.out.println(layers_t[layers_t.length - 1].neurons[j].value);
            }
        }

        writeWeightAndBias();
        getAndWriteTotalErrors();
    }



    /**
     * Methods
     * */



    public static void getTrainingData(String path) {
        int numberOfTrainingData = NeuralUtil.getTrainingInputCount(path);
        trainingData_t = new TrainingData[numberOfTrainingData];

        for(int i = 0; i < numberOfTrainingData; i++) {
            trainingData_t[i] = new TrainingData(NeuralUtil.getTrainingInputData(path, layerConfig, i));
        }
    }

    public static void getTrainingDataLearnable(String path) {
        int numberOfTrainingData = NeuralUtil.getTrainingInputCount(path);
        trainingData_t = new TrainingData[numberOfTrainingData];

        for(int i = 0; i < numberOfTrainingData; i++) {
            trainingData_t[i] = new TrainingData(NeuralUtil.getTrainingInputData(path, layerConfig, i),
                    NeuralUtil.getTrainingOutputData(path, layerConfig, i));
        }
    }


    /** method for manually creating Training Data */
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
     *          especially for manually created Training Data
     *          useful, when not having Weight or Bias Data
     */
    public static void createLayers(int numberOfLayers, int[] numberOfWeights, int[] numberOfNeurons) { // parse input data, how many layers, how many weights, how many neurons in each layer
        layers_t = new Layer[numberOfLayers];
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

    /** get weights and bias form csv and initialize Neurons */
    public static void weightAndBiasConfig(String path) {
        for(int i = 1; i < layers_t.length; i++) {
            for(int j = 0; j < layers_t[i].neurons.length; j++) {
                float[] weights;
                float bias;
                float[] weightsAndBias = NeuralUtil.getSpecificWeights(NeuralUtil.readWeightsAndBias(path),
                        layerConfig, j, i);
                weights = Arrays.copyOfRange(weightsAndBias, 0, weightsAndBias.length - 1);
                bias = weightsAndBias[weightsAndBias.length - 1];
                layers_t[i].neurons[j].setWeights(weights);
                layers_t[i].neurons[j].setBias(bias);
            }
        }
    }

    /** method for creating a csv file with the optimal weights and bias for each Neuron */
    public static void writeWeightAndBias() {
        PrintWriter printWriter;

        try {
            File csvFile = new File("weights.csv");
            printWriter = new PrintWriter(csvFile);
            String sLayers = "layers;";
            for(int i = 0; i < layerConfig.length; i++) {
                sLayers += layerConfig[i];
                if(i != layerConfig.length - 1) {
                    sLayers += ";";
                }
            }

            printWriter.println(sLayers);

            for(int i = 1; i < layers_t.length; i++) { // i = 1 because input Layer doesn't have weights
                int num = 0;
                for(int j = 0; j < layers_t[i].neurons[0].weights.length; j++) {
                    String sWeights ="";
                    for (int k = 0; k < layers_t[i].neurons.length; k++) {
                        sWeights += layers_t[i].neurons[k].weights[j] + ";";
                    }
                    printWriter.println(sWeights);
                    num++;
                }
                String sBias = "";
                for(int j = 0; j < layers_t[i].neurons.length; j++) {
                    sBias += layers_t[i].neurons[j].bias + ";";
                }
                printWriter.println(sBias);
                if(i != layers_t.length - 1) {
                    printWriter.println(";;;");
                }
            }

            printWriter.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** basically parse the weights, calculate the values. forward-pass */
    public static void run(float[] input) {
        layers_t[0] = new Layer(input); // create input Layer
        float sum;

        for(int i = 1; i < layers_t.length; i++) { // i: index for layer (i = current layer, i - 1 = layer to get values from)
            for(int j = 0; j < layers_t[i].neurons.length; j++) {
                sum = 0;
                for(int k = 0; k < layers_t[i - 1].neurons.length; k++) {
                    sum += layers_t[i].neurons[j].weights[k]*layers_t[i - 1].neurons[k].value;
                }
                sum += layers_t[i].neurons[j].bias;
                layers_t[i].neurons[j].value = Neuron.SigmoidFunction(sum);
//                 layers_t[i].neurons[j].value = Neuron.ReLu(sum); // ReLu -> not fixed yet
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
        double totalErrorDouble = 0.0f;
        float out = 0.0f;
        float target = 0.0f;
        float deltaOfValue = 0.0f;
        float deriviate = 0.0f;
        float delta;
        float deltaErr = 0.0f; //should be renamed
        float oldWeight;

        // loop for optimizing the output Layer (-> already in output Layer (note for Reise))
        for(int i = 0; i < layers_t[layers_t.length - 1].neurons.length; i++) { // i: indexing for Neurons in output Layer. loop while i < number of neurons in output Layer
            out = layers_t[layers_t.length - 1].neurons[i].value;
            target = __trainingData_t.expectedResult[i];
            totalErrorDouble += 0.5*(out -  target)*(out* - target);
            deltaOfValue = out - target; // get delta of value
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
                }
            }
        }
        totalErrorCurrent = totalErrorDouble;
        totalError.add(totalErrorCurrent);

        updateAllWeights();
    }

    /** calculating the sum over the gradients of the Neurons in a Layer multiplied by the weight */
    public static float gradientSum(int indexCurrentLayer, int indexCurrentNeuron) {
        float sum = 0.0f;
        Layer currentLayer = layers_t[indexCurrentLayer];
        for(int i = 0; i < currentLayer.neurons.length; i++) {
            Neuron currentNeuron = currentLayer.neurons[i];
            sum += currentNeuron.gradient*currentNeuron.weights[indexCurrentNeuron];
        }
        return sum;
    }

    /** updating all the weights of each Neuron --> used in backpropagation */
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

//                if(totalErrorPrior >= totalErrorCurrent && j > 0) {
//                    trainingRate = trainingRate/2;
//                }
//                totalErrorPrior = totalErrorCurrent;
            }
        }
    }

    /** create a csv file named "totalError.csv" to write down the total Error to the expected Output.
     * can be later processed to see the learning curve of the Neural Network by plotting it
     */
    public static Double[] getAndWriteTotalErrors() {
        Double[] arrTotalError = new Double[totalError.size()];
        int i = 0;
        for(Double x : totalError) {
            arrTotalError[i] = x;
            i++;
        }
        PrintWriter printWriter;

        try {
            File csvFile = new File("totalError.csv");
            printWriter = new PrintWriter(csvFile);
            for(double error : arrTotalError) {
                printWriter.print(error + ",");
            }

            printWriter.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return arrTotalError;
    }

}
