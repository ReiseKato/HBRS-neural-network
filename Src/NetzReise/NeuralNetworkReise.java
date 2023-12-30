package Src.NetzReise;

import Src.NetzVic.NeuralNetworkUtilVic;
import Src.NetzVic.TrainingDataVic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class NeuralNetworkReise {
    LayerReise[] layers_t; // array to keep the Neural Network in place
    TrainingDataReise[] trainingData_t; // array of all the training data given by the user
    int[] iLayerConfig; // array for the layer configuration (example: [3,3,4]
    double dTotalErrorPrior; // made for optimizing the neural Network, but still not working
    double dTotalErrorCurrent; // made for optimizing the neural Network, but still not working
    double dCurrentMeanTotalError;
    double dSumTotalError;
    List<Double> totalErrorReise = new ArrayList<Double>(); // list for keeping all the total Error --> easier to handle as a list than an array, if over 100000 elements



//    public void main(String[] args) {
//        int numberOfLayers;
//        int[] numberOfNeurons;
//        int[] numberOfWeights;
//        // paths to needed files
//        // USER has to edit these paths to his/her own file path
//        String sPath = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\Src\\KW43_weights_trafficlights_classification_simplified.csv"; // Layer, Weight and Bias
//        String sPathForTData = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\Src\\KW43_traindata_trafficlights_classification.csv"; // Training Data
//
//        String sPathBestWeights = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\weights.csv"; // best weights
//        String sPathKnownTData = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\known_training_data.csv"; // known Training Data
//        String sPathUnknownData = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\unknown_data.csv"; // unknown for checking implementation
//
//        boolean printMatirx = true;
//
//        NeuronReise.setWeightRange(-1, 1);
//
//        // if using whole dataset
//        createLayers(sPath);
//        getTrainingDataLearnable(sPathForTData);
//        weightAndBiasConfig(sPath);
//
//        // if using only specifc datase --> same as whole set
////        createLayers(sPath);
////        getTrainingDataLearnable(sPathKnownTData);
////        weightAndBiasConfig(sPath);
//
//        // if checking with unknown dataset
////        createLayers(sPathBestWeights);
////        getTrainingData(sPathUnknownData);
////        weightAndBiasConfig(sPathBestWeights);
//
//
//        train(10000, 0.05f); // 10000 iterations for each dataset
//
//        for(int i = 0; i < trainingData_t.length; i++) {
//            run(trainingData_t[i].inputData);
//            System.out.println("\nInput: " + i);
//            for(int j = 0; j < layers_t[layers_t.length - 1].neurons.length; j++) {
//                System.out.println(layers_t[layers_t.length - 1].neurons[j].fValue);
//            }
//        }
//
//        writeWeightAndBias("weights");
//        writeTotalErrors("totalError");
//    }


    /**
     * Constructor
     */

    public NeuralNetworkReise(String layerPath, int weightRangeMin, int weightRangeMax) {
        NeuronReise.setWeightRange(weightRangeMin, weightRangeMax);
        createLayersAutoEfficient(layerPath);
    }





    /**
     * Methods
     * */


    /**
     * not learnable NN
     * */
    public void getInputData(String path) {
        int numberOfTrainingData = NeuralUtilReise.getTrainingInputCount(path);
        trainingData_t = new TrainingDataReise[numberOfTrainingData];

        for(int i = 0; i < numberOfTrainingData; i++) {
            trainingData_t[i] = new TrainingDataReise(NeuralUtilReise.getTrainingInputData(path, iLayerConfig, i));
        }
    }

    /**
     * learnable NN
     * */
    public void getTrainingDataLearnable(String path) {
        int numberOfTrainingData = NeuralUtilReise.getTrainingInputCount(path);
        trainingData_t = new TrainingDataReise[numberOfTrainingData];

        for(int i = 0; i < numberOfTrainingData; i++) {
            trainingData_t[i] = new TrainingDataReise(NeuralUtilReise.getTrainingInputData(path, iLayerConfig, i),
                    NeuralUtilReise.getTrainingOutputData(path, iLayerConfig, i));
        }
    }

    /**
     * quite useful for trainable kNN because I don't have to initialize all weights manually
     *          --> just use random floats :)
     *          especially for manually created Training Data
     *          useful, when not having Weight or Bias Data
     */
    public void createLayers(int numberOfLayers, int[] numberOfWeights, int[] numberOfNeurons) { // parse input data, how many layers, how many weights, how many neurons in each layer
        layers_t = new LayerReise[numberOfLayers];
        for (int i = 1; i < numberOfLayers; i++) { //create all hidden Layers and output Layer
            layers_t[i] = new LayerReise(numberOfWeights[i - 1], numberOfNeurons[i - 1]);
        }
    }

    /** manual NN creation */
    public void createLayers(String path) {
        iLayerConfig = NeuralUtilReise.getlayerConfig(path);
        int[] numberOfWeights = Arrays.copyOfRange(iLayerConfig, 0, iLayerConfig.length - 1);
        int[] numberOfNeurons = Arrays.copyOfRange(iLayerConfig, 1, iLayerConfig.length);
        int numberOfLayers = iLayerConfig.length;
        layers_t = new LayerReise[iLayerConfig.length];

        for(int i = 1; i < numberOfLayers; i++) {
            layers_t[i] = new LayerReise(numberOfWeights[i - 1], numberOfNeurons[i - 1]);
        }
    }

    /** automatic NN creation */
    public void createLayersAuto(String path) {
        iLayerConfig = NeuralUtilReise.getlayerConfig(path);
        int numberOfWeights;
        int numberOfNeurons;
        int numberOfLayers = iLayerConfig.length;
        layers_t = new LayerReise[iLayerConfig.length];

        for(int i = 1; i < numberOfLayers; i++) {
            numberOfWeights = iLayerConfig[i - 1];
            numberOfNeurons = iLayerConfig[i];
            layers_t[i] = new LayerReise(numberOfWeights, numberOfNeurons);
        }
    }

    /**
     * more storage efficient method for "createLayersAuto"
     * */
    public void createLayersAutoEfficient(String path) {
        iLayerConfig = NeuralUtilReise.getlayerConfig(path);
        layers_t = new LayerReise[iLayerConfig.length];
        for(int i = 1; i < iLayerConfig.length; i++) {
            layers_t[i] = new LayerReise(iLayerConfig[i - 1], iLayerConfig[i]);
        }
    }

    /** get weights and bias from csv and initialize Neurons */
    public void weightAndBiasConfig(String path) {
        Float[][] weightsAndBiasFloat = NeuralUtilReise.readWeightsAndBias(path);
        for(int i = 1; i < layers_t.length; i++) {
            for(int j = 0; j < layers_t[i].neurons.length; j++) {
                float[] weights;
                float bias;
                float[] weightsAndBias = NeuralUtilReise.getSpecificWeights(weightsAndBiasFloat,
                        iLayerConfig, j, i);
                weights = Arrays.copyOfRange(weightsAndBias, 0, weightsAndBias.length - 1);
                bias = weightsAndBias[weightsAndBias.length - 1];
                layers_t[i].neurons[j].setWeights(weights);
                layers_t[i].neurons[j].setBias(bias);
            }
        }
    }

    /** method for creating a csv file with the optimal weights and bias for each Neuron */
    public void writeWeightAndBias(String fileName) {
        PrintWriter printWriter;
        if(fileName.charAt(fileName.length() - 1) != 'v') {
            fileName += ".csv";
        }

        try {
            File csvFile = new File(fileName);
            printWriter = new PrintWriter(csvFile);
            String sLayers = "layers;";
            for(int i = 0; i < iLayerConfig.length; i++) {
                sLayers += iLayerConfig[i];
                if(i != iLayerConfig.length - 1) {
                    sLayers += ";";
                }
            }

            printWriter.println(sLayers);

            for(int i = 1; i < layers_t.length; i++) { // i = 1 because input Layer doesn't have weights
                int num = 0;
                for(int j = 0; j < layers_t[i].neurons[0].fWeights.length; j++) {
                    String sWeights ="";
                    for (int k = 0; k < layers_t[i].neurons.length; k++) {
                        sWeights += layers_t[i].neurons[k].fWeights[j] + ";";
                    }
                    printWriter.println(sWeights);
                    num++;
                }
                String sBias = "";
                for(int j = 0; j < layers_t[i].neurons.length; j++) {
                    sBias += layers_t[i].neurons[j].fBias + ";";
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
    public void run(float[] input) {
        layers_t[0] = new LayerReise(input); // create input Layer
        float sum;

        for(int i = 1; i < layers_t.length; i++) { // i: index for layer (i = current layer, i - 1 = layer to get values from)
            for(int j = 0; j < layers_t[i].neurons.length; j++) {
                sum = 0;
                for(int k = 0; k < layers_t[i - 1].neurons.length; k++) {
                    sum += layers_t[i].neurons[j].fWeights[k]*layers_t[i - 1].neurons[k].fValue;
                }
                sum += layers_t[i].neurons[j].fBias;
                layers_t[i].neurons[j].fValue = NeuronReise.SigmoidFunction(sum);
//                 layers_t[i].neurons[j].value = Neuron.ReLu(sum); // ReLu -> not fixed yet
//                if(i != layers_t.length - 2) {
//                    layers_t[i].neurons[j].value = Neuron.SigmoidFunction(sum);
//                } else {
//                    layers_t[i].neurons[j].value = sum;
//                }
            }
        }
    }

    /** training rate:  can be used to determine how fast the model should train.
     *                  too low -> can get stuck | too high ->  can cause the model to converge too quickly to a suboptimal solution
     */
    public void backpropagation(TrainingDataReise __trainingData_t, float trainingRate) {
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
            out = layers_t[layers_t.length - 1].neurons[i].fValue;
            target = __trainingData_t.expectedResult[i];
            totalErrorDouble += 0.5*(out -  target)*(out - target);
            deltaOfValue = out - target; // get delta of value
            deriviate = out*(1 - out);
            delta = deriviate*deltaOfValue;
            layers_t[layers_t.length - 1].neurons[i].fGradient = delta;
            for(int j = 0; j < layers_t[layers_t.length - 1].neurons[i].fWeights.length; j++) { // j: indexing for Neurons of exactly 1 Layer before output Layer. iterate as often as values each Neuron in output Layer gets
                deltaErr = delta*layers_t[layers_t.length - 2].neurons[j].fValue;
                oldWeight = layers_t[layers_t.length - 1].neurons[i].fWeights[j];
                layers_t[layers_t.length - 1].neurons[i].fNewWeights[j] = oldWeight - trainingRate*deltaErr;
                // layers_t[layers_t.length - 1].neurons[i].weights[j] = oldWeight - trainingRate*deltaErr;
            }
        }

        float gradientSum;
        // loop for optimizing the hidden Layers -> has to go backwards
        for(int i = layers_t.length - 2; i > 0; i--) { // i; indexing for Layers
            //out = layers_t[i].neurons[]
            for(int j = 0; j < layers_t[i].neurons.length; j++) { // indexing for Neurons
                gradientSum = gradientSumEfficient(i + 1, j); // i+1: needs the outputs of the Layer before
                out = layers_t[i].neurons[j].fValue;
                delta = gradientSum*(out*(1 - out)); // delta of Error to Weight
                layers_t[i].neurons[j].fGradient = delta; // just assuming it because I need to update the gradient and that's the best value I can find
                // loop for updating all weights for each Neuron
                for(int k = 0; k < layers_t[i].neurons[j].fWeights.length; k++) { // k: indexing for weights
                    /*if training via output, use code below till block comment*/
                    deltaErr = layers_t[i - 1].neurons[k].fValue *delta;
                    layers_t[i].neurons[j].fNewWeights[k] = layers_t[i].neurons[j].fWeights[k] - trainingRate*deltaErr;

                    /*if it needs to train via weight, use the upper code below*/
                    // layers_t[i].neurons[j].newWeights[k] = layers_t[i].neurons[j].weights[k] - trainingRate*delta;
                }
            }
        }
        dTotalErrorCurrent = totalErrorDouble;
        dSumTotalError += totalErrorDouble;
        totalErrorReise.add(dTotalErrorCurrent);
        dCurrentMeanTotalError = dSumTotalError/totalErrorReise.size();

        updateAllWeights();
    }

    /** calculating the sum over the gradients of the Neurons in a Layer multiplied by the weight */
    public float gradientSum(int indexCurrentLayer, int indexCurrentNeuron) {
        float sum = 0.0f;
        LayerReise currentLayer = layers_t[indexCurrentLayer];
        for(int i = 0; i < currentLayer.neurons.length; i++) {
            NeuronReise currentNeuron = currentLayer.neurons[i];
            sum += currentNeuron.fGradient *currentNeuron.fWeights[indexCurrentNeuron];
        }
        return sum;
    }

    /** more storage efficient "gradientSum" */
    public float gradientSumEfficient(int indexCurrentLayer, int indexCurrentNeuron) {
        float sum = 0.0f;
//        LayerReise currentLayer = layers_t[indexCurrentLayer];
        for(int i = 0; i < layers_t[indexCurrentLayer].neurons.length; i++) {
            //NeuronReise currentNeuron = layers_t[indexCurrentLayer].neurons[i];
            sum += layers_t[indexCurrentLayer].neurons[i].fGradient *
                    layers_t[indexCurrentLayer].neurons[i].fWeights[indexCurrentNeuron];
        }
        return sum;
    }

    /** updating all the weights of each Neuron --> used in backpropagation */
    public void updateAllWeights() {
        for(int i = 0; i < layers_t.length; i++) { // i: indexing for Layers
            for(int j = 0; j < layers_t[i].neurons.length; j++) {
                layers_t[i].neurons[j].updateWeights();
            }
        }
    }

    /** calculate the appropriate output for each input */
    public void train(int countOfTraining, float trainingRate) {
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

    public float[][] getOutputs() {
        float[][] outputs = new float[trainingData_t[0].expectedResult.length][trainingData_t.length];
        for(int i = 0; i < trainingData_t.length; i++) {
            run(trainingData_t[i].inputData);
            for(int j = 0; j < layers_t[layers_t.length - 1].neurons.length; j++) {
                outputs[j][i] = layers_t[layers_t.length - 1].neurons[j].fValue;
            }
        }
        return outputs;
    }

    public float[][] getOutputs2() {
        float[][] outputs = new float[trainingData_t.length][trainingData_t[0].expectedResult.length];
        for(int i = 0; i < trainingData_t.length; i++) {
            run(trainingData_t[i].inputData);
            for(int j = 0; j < layers_t[layers_t.length - 1].neurons.length; j++) {
                outputs[i][j] = layers_t[layers_t.length - 1].neurons[j].fValue;
            }
        }
        return outputs;
    }

    public double getdTotalErrorCurrent() {
        return dTotalErrorCurrent;
    }

    public double getTotalErrorMean() {
        return dCurrentMeanTotalError;
    }

    /**
     * print all
     */
    public void print() {
        System.out.println("Reise's Netz: ");
        for(int i = 0; i < trainingData_t.length; i++) {
            run(trainingData_t[i].inputData);
            System.out.println("\nInput: " + i);
            for(int j = 0; j < layers_t[layers_t.length - 1].neurons.length; j++) {
                System.out.println(layers_t[layers_t.length - 1].neurons[j].fValue);
            }
        }
    }

    public void computeUnknownData(float[][] unknown, String filename) throws IOException {

        String[][] ans = new String[unknown.length][];
        for (int i = 0; i < unknown.length; i++) {
            getOutputVektor(unknown[i]);
            ans[i] = NeuralUtilReise.LayerToString(layers_t[layers_t.length-1]);
        }

        NeuralNetworkUtilVic.writeCSV(ans, "python/unknownOutputs/"+ filename + ".csv");
    }

    public float[] getOutputVektor(float[] input) {
        run(input);
        float[] output = new float[layers_t[layers_t.length-1].neurons.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = layers_t[layers_t.length-1].neurons[i].fValue;
        }
        return output;
    }

    public void pass(String inputData) {
        int numberOfTrainingData = NeuralUtilReise.getTrainingInputCount(inputData);
        TrainingDataReise[] tr = new TrainingDataReise[numberOfTrainingData];

        for(int i = 0; i < numberOfTrainingData; i++) {
            tr[i] = new TrainingDataReise(NeuralUtilReise.getTrainingInputData(inputData, iLayerConfig, i));
        }
        for(int i = 0; i < tr.length; i++) {
            run(tr[i].inputData);
            for(int j = 0; j < layers_t[layers_t.length - 1].neurons.length; j++) {
                System.out.println(layers_t[layers_t.length - 1].neurons[j].fValue);
            }
        }
    }

    public double[] passWithExpectedOutput(String tData)  {
        int numberOfTrainingData = NeuralUtilReise.getTrainingInputCount(tData);
//        double[][][] result = new double[numberOfTrainingData][][];
        double[] totalErrors = new double[numberOfTrainingData + 1];
        TrainingDataReise[] tr = new TrainingDataReise[numberOfTrainingData];
        double totalErrorDouble = 0;
        double totalErrorSum = 0;
        double meanTotalError = 999.99;

        for(int i = 0; i < numberOfTrainingData; i++) {
            tr[i] = new TrainingDataReise(NeuralUtilReise.getTrainingInputData(tData, iLayerConfig, i),
                    NeuralUtilReise.getTrainingOutputData(tData, iLayerConfig, i));
        }
        for(int i = 0; i < tr.length; i++) {
            run(tr[i].inputData);
            totalErrorDouble = 0;
            for(int j = 0; j < layers_t[layers_t.length - 1].neurons.length; j++) {
//                System.out.println("\ngot: " + layers_t[layers_t.length - 1].neurons[j].fValue
//                        + " -- expected: " + tr[i].expectedResult[j]);
                double out = layers_t[layers_t.length - 1].neurons[j].fValue;
                double target = tr[i].expectedResult[j];
                totalErrorDouble += 0.5*(out -  target)*(out - target);
            }
//            System.out.println("total Error: " + totalErrorDouble + "\n");
            totalErrors[i] = totalErrorDouble;
            totalErrorSum +=  totalErrorDouble;
        }
        if(totalErrorDouble != 0) {
            meanTotalError = totalErrorSum/numberOfTrainingData;
        }
        totalErrors[numberOfTrainingData] = meanTotalError;
        return totalErrors;
    }

    /** create a csv file named "totalError.csv" to write down the total Error to the expected Output.
     * can be later processed to see the learning curve of the Neural Network by plotting it
     */
    public Double[] getTotalErrors() {
        Double[] arrTotalError = new Double[totalErrorReise.size()];
        int i = 0;
        for(Double x : totalErrorReise) {
            arrTotalError[i] = x;
            i++;
        }
        return arrTotalError;
    }

    public Double[] writeTotalErrors(String fileName) {
        Double[] arrTotalError = new Double[totalErrorReise.size()];
        int i = 0;
        for(Double x : totalErrorReise) {
            arrTotalError[i] = x;
            i++;
        }
        PrintWriter printWriter;

        if(fileName.charAt(fileName.length() - 1) != 'v') {
            fileName += ".csv";
        }
        try {
            File csvFile = new File(fileName);
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
