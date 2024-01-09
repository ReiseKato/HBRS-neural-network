package Src.Main;

import Src.NetzReise.LayerReise;
import Src.NetzReise.NeuralNetworkReise;
import Src.NetzReise.NeuralUtilReise;
import Src.NetzVic.NeuralNetworkUtilVic;
import Src.NetzVic.NeuralNetworkVic;
import Src.NetzVic.TrainingDataVic;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * - all results in directory "results" (Reise)
 *      --> runtime results: "results\\runtime"
 *      --> unknown data: "results\\behaving_on_unknown_data"
 *      --> needed iterations for a target total error: "results\\target_total_error"
 *
 * - all plot results in directory "python" (Victor)
 *      --> "python\\plots"
 *
 * - all layer configurations in directory "layer_config" or "layer_config_2"
 *
 * - clean datasets containing only 0 and 1 in directory "training_data"
 * - datasets with noise containing decimal numbers in directory "training_data_noise"
 * - datasets containing both types of data, referred as "whole data" in the following code, in directory
 *      "training_data_merge_clean_noise"
 * - datasets containing around 85% of the whole dataset (known_data) in directory "training_data_known"
 * - datasets containing around 15% of the whole dataset (partial_data) in directory "training_data_unknown"
 *
 * 1. all clean datasets have been created using logical statements in Microsoft Excel (MIN and MAX Function)
 * 2. all datasets containing decimal numbers have been converted from the clean dataset by using the method
 *      "NeuralUtilReise.makeTrainingData" which uses random numbers to subtract or add to the already existing input
 *      data without touching the output data. --> the output data therefore remains clean
 * */

public class  Main {
    public static void main(String args[]) throws IOException {
        // Reise CODE Begin
        // CONSTANT Begin

        int ITERATION_NETWORK_TRAINING = 100; // define how many training iterations
        float LEARNING_RATE = 0.05f; // define learning rate
        double TARGET_TOTAL_ERROR = 0.05; // define target total Error
        int runtimeIteration = 20; // define how often the method has to calculate the runtime
        String CLEAN_DATASET = "clean_dataset";
        String NOISE_DATASET = "noise_dataset";
        String WHOLE_DATASET = "whole_dataset";
        String PARTIAL_DATASET = "partial_dataset";
        String UNKNOWN_DATASET = "unknown_dataset";
        String LAYER_ALTERNATIVE = "_layer_alternative";

        // CONSTANT End

        // Init Begin
        String sPathTargetDir;
        String sPathTargetDirRuntime;
        String sPathTargetDirUnknown;
        String sPathTargetDirTargetTotalError;
        String sPathLayerConfig;
        String sPathLayerConfigAlternative;
        String sPathTrainingDataClean;
        String sPathTrainingDataNoise;
        String sPathTrainingDataMerged;
        String sPathTrainingDataKnown;
        String sPathTrainingDataUnknown;
        String filename;
        String sDataType;
        File dirTrainingClean;
        File dirTrainingNoise;
        File dirTrainingMerge;
        File dirTrainingKnown;
        File dirTrainingUnknown;
        File dirLayer;
        File dirLayerAlternative;
        File[] dirTrainingCleanListing;
        File[] dirTrainingNoiseListing;
        File[] dirTrainingMergedListing;
        File[] dirTrainingKnownListing;
        File[] dirTrainingUnknownListing;
        File[] dirLayerListing;
        File[] dirLayerAlternativeListing;
        // Init End

        // Stats Code Begin

        sPathTargetDir = "results\\";
        sPathTargetDirRuntime = sPathTargetDir + "runtime\\";
        sPathTargetDirTargetTotalError = sPathTargetDir + "target_total_error\\";
        sPathTargetDirUnknown = sPathTargetDir + "behaving_on_unknown_data\\";
        dirTrainingClean = new File("training_data");
        dirTrainingNoise = new File("training_data_noise");
        dirTrainingMerge = new File("training_data_merge_clean_noise");
        dirTrainingKnown = new File("training_data_known");
        dirTrainingUnknown = new File("training_data_unknown");
        dirLayer = new File("layer_config");
        dirLayerAlternative = new File("layer_config_2");
        dirTrainingCleanListing = dirTrainingClean.listFiles();
        dirTrainingNoiseListing = dirTrainingNoise.listFiles();
        dirTrainingMergedListing = dirTrainingMerge.listFiles();
        dirTrainingKnownListing = dirTrainingKnown.listFiles();
        dirTrainingUnknownListing = dirTrainingUnknown.listFiles();
        dirLayerListing = dirLayer.listFiles();
        dirLayerAlternativeListing = dirLayerAlternative.listFiles();

        Arrays.sort(dirTrainingCleanListing);
        Arrays.sort(dirTrainingNoiseListing);
        Arrays.sort(dirTrainingMergedListing);
        Arrays.sort(dirTrainingKnownListing);
        Arrays.sort(dirTrainingUnknownListing);
        Arrays.sort(dirLayerListing);
        Arrays.sort(dirLayerAlternativeListing);

        if(dirLayerListing.length != dirTrainingCleanListing.length
                || dirLayerListing.length != dirTrainingNoiseListing.length
                || dirLayerListing.length != dirTrainingMergedListing.length
                || dirLayerListing.length != dirTrainingKnownListing.length
                || dirLayerListing.length != dirTrainingUnknownListing.length
                || dirLayerListing.length != dirLayerAlternativeListing.length) {
            System.out.println("Count of Layer Configs and Training Data do NOT match");
        } else {
            for (int i = 0; i < dirTrainingCleanListing.length; i++) {
                sPathTrainingDataClean = dirTrainingCleanListing[i].getPath();
                sPathTrainingDataNoise = dirTrainingNoiseListing[i].getPath();
                sPathTrainingDataMerged = dirTrainingMergedListing[i].getPath();
                sPathTrainingDataKnown = dirTrainingKnownListing[i].getPath();
                sPathTrainingDataUnknown = dirTrainingUnknownListing[i].getPath();
                sPathLayerConfig = dirLayerListing[i].getPath();
                sPathLayerConfigAlternative = dirLayerAlternativeListing[i].getPath();
                System.out.println(i);
                System.out.println(dirLayerListing[i].getName());
                // runtime
                ITERATION_NETWORK_TRAINING = 100;
                runtimeComparison(sPathLayerConfig, sPathTrainingDataClean, sPathTargetDirRuntime,
                        ITERATION_NETWORK_TRAINING, LEARNING_RATE, runtimeIteration, CLEAN_DATASET);
                System.out.println("runtime clean done");
                runtimeComparison(sPathLayerConfig, sPathTrainingDataNoise, sPathTargetDirRuntime,
                        ITERATION_NETWORK_TRAINING, LEARNING_RATE, runtimeIteration, NOISE_DATASET);
                System.out.println("runtime noise done");
                runtimeComparison(sPathLayerConfig, sPathTrainingDataMerged, sPathTargetDirRuntime,
                        ITERATION_NETWORK_TRAINING, LEARNING_RATE, runtimeIteration, WHOLE_DATASET);
                System.out.println("runtime whole done");
                // runtime on alternative Layer configuration
                runtimeComparison(sPathLayerConfigAlternative, sPathTrainingDataClean, sPathTargetDirRuntime,
                        ITERATION_NETWORK_TRAINING, LEARNING_RATE, runtimeIteration,
                        CLEAN_DATASET + LAYER_ALTERNATIVE);
                System.out.println("runtime clean 2 done");
                runtimeComparison(sPathLayerConfigAlternative, sPathTrainingDataNoise, sPathTargetDirRuntime,
                        ITERATION_NETWORK_TRAINING, LEARNING_RATE, runtimeIteration,
                        NOISE_DATASET + LAYER_ALTERNATIVE);
                System.out.println("runtime noise 2 done");
                runtimeComparison(sPathLayerConfigAlternative, sPathTrainingDataMerged, sPathTargetDirRuntime,
                        ITERATION_NETWORK_TRAINING, LEARNING_RATE, runtimeIteration,
                        WHOLE_DATASET + LAYER_ALTERNATIVE);
                System.out.println("runtime whole 2 done");
                System.gc();
                // unknown data
                ITERATION_NETWORK_TRAINING = 100000;
                unknownDataComparison(sPathLayerConfig, sPathTrainingDataMerged, sPathTrainingDataUnknown,
                        sPathTargetDirUnknown, ITERATION_NETWORK_TRAINING, LEARNING_RATE, WHOLE_DATASET);
                System.out.println("unknown data whole done");
                System.gc();
                unknownDataComparison(sPathLayerConfig, sPathTrainingDataKnown, sPathTrainingDataUnknown,
                        sPathTargetDirUnknown, ITERATION_NETWORK_TRAINING, LEARNING_RATE, PARTIAL_DATASET);
                System.out.println("unknown data partial done");
                System.gc();
                // unknown data on alternative Layer configuration
                unknownDataComparison(sPathLayerConfigAlternative, sPathTrainingDataMerged, sPathTrainingDataUnknown,
                        sPathTargetDirUnknown, ITERATION_NETWORK_TRAINING, LEARNING_RATE,
                        WHOLE_DATASET + LAYER_ALTERNATIVE);
                System.out.println("unknown data whole 2 done");
                System.gc();
                unknownDataComparison(sPathLayerConfigAlternative, sPathTrainingDataKnown, sPathTrainingDataUnknown,
                        sPathTargetDirUnknown, ITERATION_NETWORK_TRAINING, LEARNING_RATE,
                        PARTIAL_DATASET + LAYER_ALTERNATIVE);
                System.out.println("unknown data partial 2 done");
                System.gc();
                // target total Error
                requiredIterationComparison(sPathLayerConfig, sPathTrainingDataClean, TARGET_TOTAL_ERROR,
                        sPathTargetDirTargetTotalError, LEARNING_RATE, CLEAN_DATASET);
                System.out.println("required iteration clean done");
                System.gc();
                requiredIterationComparison(sPathLayerConfig, sPathTrainingDataNoise, TARGET_TOTAL_ERROR,
                        sPathTargetDirTargetTotalError, LEARNING_RATE, NOISE_DATASET);
                System.out.println("required iteration noise done");
                System.gc();
                requiredIterationComparison(sPathLayerConfig, sPathTrainingDataMerged, TARGET_TOTAL_ERROR,
                        sPathTargetDirTargetTotalError, LEARNING_RATE, WHOLE_DATASET);
                System.out.println("required iteration whole done");
                System.gc();
                // target total Error on alternative Layer configuration
                requiredIterationComparison(sPathLayerConfigAlternative, sPathTrainingDataClean, TARGET_TOTAL_ERROR,
                        sPathTargetDirTargetTotalError, LEARNING_RATE, CLEAN_DATASET + LAYER_ALTERNATIVE);
                System.out.println("required iteration clean 2 done");
                System.gc();
                requiredIterationComparison(sPathLayerConfigAlternative, sPathTrainingDataNoise, TARGET_TOTAL_ERROR,
                        sPathTargetDirTargetTotalError, LEARNING_RATE, NOISE_DATASET + LAYER_ALTERNATIVE);
                System.out.println("required iteration noise 2 done");
                System.gc();
                requiredIterationComparison(sPathLayerConfigAlternative, sPathTrainingDataMerged, TARGET_TOTAL_ERROR,
                        sPathTargetDirTargetTotalError, LEARNING_RATE, WHOLE_DATASET + LAYER_ALTERNATIVE);
                System.out.println("required iteration whole 2 done");
                System.gc();
            }
        }

        // Stats Code End

        // Reise CODE End





        // Vicotrs Platz Begin


        // Victors Platz End
       // compareTotalError();
       // learnrates();
        //learnratesUnknowndata();

        
        // Philips Platz Begin
        // Nach Absprache beschlossen, dass Philip keinen Code schreibt
        // Philips Platz End


    }

    // Reise Method Begin

    public static void runtimeComparison(String pathLayerConfig, String pathTrainingData, String targetDir,
                                         int trainingIteration, float learningRate, int runtimeIteration,
                                         String dataType) throws IOException {
//        try {
        long[][] runtimeR = runtimeReise(pathLayerConfig, pathTrainingData,
                trainingIteration, learningRate, runtimeIteration);
        long[][] runtimeV = runtimeVictor(pathLayerConfig, pathTrainingData,
                trainingIteration, learningRate, runtimeIteration);
        long[][] runtimeC = getComparisonReiseMinusVictor(runtimeR, runtimeV);
//        double[][] runtimeD = getComparisonReiseDividedVictor(runtimeR, runtimeV);

        File name_t = new File(pathLayerConfig);
        String sName = name_t.getName();
        String filenameR = sName.substring(0, sName.indexOf("_") + 1) + "Runtime_NNR_" + dataType;
        filenameR = targetDir + filenameR;
        String filenameV = sName.substring(0, sName.indexOf("_") + 1) + "Runtime_NNV_" + dataType;
        filenameV = targetDir + filenameV;
        String filenameC = sName.substring(0, sName.indexOf("_") + 1) + "Runtime_Compared_" + dataType;
        filenameC = targetDir + filenameC;
//        String filenameD = sName.substring(0, sName.indexOf("_") + 1) + "Runtime_Compared_Division_" + dataType;
//        filenameD = targetDir + filenameD;
        writeRuntime(filenameR, runtimeR, pathLayerConfig, trainingIteration, learningRate, dataType);
        writeRuntime(filenameV, runtimeV, pathLayerConfig, trainingIteration, learningRate, dataType);
        writeRuntime(filenameC, runtimeC, pathLayerConfig, trainingIteration, learningRate, dataType);
//        writeRuntimeDouble(filenameD, runtimeD, pathLayerConfig, trainingIteration, learningRate, dataType);
//        }
//        catch(IOException e) {
//            System.out.println(e);
//        }
    }

    public static void unknownDataComparison(String pathLayerConfig, String pathTrainingData,
                                             String pathUnkownTrainingData, String targetDir, int trainingIteration,
                                             float learningRate, String dataType) {
        try {
            NeuralNetworkReise nnR = new NeuralNetworkReise(pathLayerConfig, -1, 1);
            nnR.getTrainingDataLearnable(pathTrainingData);
            nnR.train(trainingIteration, learningRate);
            TrainingDataVic tdv = new TrainingDataVic(pathLayerConfig, pathTrainingData);
            NeuralNetworkVic nnV = new NeuralNetworkVic(tdv);
            nnV.train(trainingIteration, learningRate);
            double[] totalErrorsR = nnR.passWithExpectedOutput(pathUnkownTrainingData);
            TrainingDataVic unknownTDV = new TrainingDataVic(pathLayerConfig, pathUnkownTrainingData);
            double[] totalErrorsV = nnV.computeUnknownData(unknownTDV);
            double[] totalErrorsCompared = new double[totalErrorsR.length];
            for(int i = 0; i < totalErrorsR.length; i++) {
                totalErrorsCompared[i] = totalErrorsR[i] - totalErrorsV[i];
            }
            File name_t = new File(pathLayerConfig);
            String sName = name_t.getName();
            sName = sName.substring(0, sName.indexOf("_") + 1);
            String filenameR = targetDir + sName + "Unknown_NNR_" + dataType;
            int[] iLayerConfig = NeuralUtilReise.getlayerConfig(pathLayerConfig);
            String slayerConfig = "layers: " + Arrays.toString(iLayerConfig);
            writeTotalErrorToCSV(filenameR, totalErrorsR, trainingIteration, learningRate, slayerConfig + " "
                    + dataType);
            String filenameV = targetDir + sName + "Unknown_NNV_" + dataType;
            writeTotalErrorToCSV(filenameV, totalErrorsV, trainingIteration, learningRate,slayerConfig + " "
                    + dataType);
            String filenameC = targetDir + sName + "Compared_NNR_MINUS_NNV_" + dataType;
            writeTotalErrorToCSV(filenameC, totalErrorsCompared, trainingIteration, learningRate,slayerConfig
                    + " " + dataType);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void requiredIterationComparison(String pathLayerConfig, String pathTrainingData,
                                                   double targetTotalError, String targetDir, float learningRate,
                                                   String dataType) {
        String filename = "";
        String[] trainAndLayer = new String[2];
        double[] iterationReise =
                getIterationForDeltaGoal("reise", targetTotalError,
                        pathLayerConfig, pathTrainingData, learningRate);
        double[] iterationVictor = getIterationForDeltaGoal("vic", targetTotalError,
                pathLayerConfig, pathTrainingData, learningRate);
        double[] allInformation = new double[iterationReise.length + iterationVictor.length + 1];
        File fileTraining = new File(pathTrainingData);
        File file = new File(pathLayerConfig);
        trainAndLayer[0] = fileTraining.getName();
        trainAndLayer[1] = Arrays.toString(NeuralUtilReise.getlayerConfig(pathLayerConfig));
        allInformation[0] = targetTotalError;
        allInformation[1] = iterationReise[0];
        allInformation[2] = iterationReise[1];
        allInformation[3] = iterationReise[2];
        allInformation[4] = iterationVictor[0];
        allInformation[5] = iterationVictor[1];
        allInformation[6] = iterationVictor[2];
        filename = file.getName().substring(0, file.getName().indexOf("_") + 1) + "required_iteration_" + dataType;
        filename = targetDir + filename;
        writeIterationDataToCSV(filename, allInformation, trainAndLayer, learningRate);
    }


    public static long[][] runtimeReise(String pathLayerConfig, String pathTrainingData,
                                         int trainingIteration, float learningRate, int runtimeIteration) {
        long timesR[][] = new long[runtimeIteration][4];
        for(int i = 0; i < runtimeIteration; i++) {
            long start = System.nanoTime(); // time start
            NeuralNetworkReise nnR = new NeuralNetworkReise(pathLayerConfig, -1, 1);
            timesR[i][0] = System.nanoTime(); // time Layer config
            nnR.getTrainingDataLearnable(pathTrainingData);
            timesR[i][1] = System.nanoTime(); // time getting Training Data
            nnR.train(trainingIteration, learningRate);
            timesR[i][2] = System.nanoTime(); // time training the model
            timesR[i][3] = timesR[i][2]; // time stop


            timesR[i][3] = timesR[i][3] - start; // final whole runtime of model
            timesR[i][2] = timesR[i][2] - timesR[i][1]; // final training time
            timesR[i][1] = timesR[i][1] - timesR[i][0]; // final getting training data time
            timesR[i][0] = timesR[i][0] - start; // final layer config time

            if(timesR[i][2] + timesR[i][1] + timesR[i][0] != timesR[i][3]) {
                long error[] = {Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE};
                timesR[i] = error;
            }
        }

        return timesR;
    }

    public static long[][] runtimeVictor(String pathLayerConfig, String pathTrainingData,
                                         int trainingIteration, float learningRate,
                                         int runtimeIteration) throws IOException {
//        String[] func = getSigmoidFuncOnly(pathLayerConfig);
        int[] layerConfig = NeuralUtilReise.getlayerConfig(pathLayerConfig);
        int inlen = layerConfig[0];
        int outlen = layerConfig[layerConfig.length - 1];
        long timesV[][] = new long[runtimeIteration][4];
        for(int i = 0; i < runtimeIteration; i++) {
            long start = System.nanoTime(); // time start
            TrainingDataVic tdV = new TrainingDataVic(pathLayerConfig, pathTrainingData);
            timesV[i][1] = System.nanoTime(); // time getting Training Data
            NeuralNetworkVic nnV = new NeuralNetworkVic(tdV);
            timesV[i][0] = System.nanoTime(); // time layer config
            nnV.train(trainingIteration, learningRate);
            timesV[i][2] = System.nanoTime(); // time training the model
            timesV[i][3] = timesV[i][2]; // time stop


            timesV[i][3] = timesV[i][3] - start; // final whole runtime of model
            timesV[i][2] = timesV[i][2] - timesV[i][0]; // final training time
            timesV[i][0] = timesV[i][0] - timesV[i][1]; // final layer config time
            timesV[i][1] = timesV[i][1] - start; // final getting training data time

            if(timesV[i][2] + timesV[i][1] + timesV[i][0] != timesV[i][3]) {
                long error[] = {Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE};
                timesV[i] = error;
            }
        }

        return timesV;
    }

    public static long[][] getComparisonReiseMinusVictor(long[][] resultNNR, long[][] resultNNV)
            throws IndexOutOfBoundsException {
        if(resultNNR.length != resultNNV.length) {
            throw new IndexOutOfBoundsException("result data are not same length");
        }
        long[][] compared2D = new long[resultNNR.length][resultNNR[0].length];
        for(int i = 0; i < resultNNR.length; i++) {
            long[] arrNNR = resultNNR[i];
            long[] arrNNV = resultNNV[i];
            if(arrNNR.length != arrNNV.length) {
                throw new IndexOutOfBoundsException("column length do not match");
            }
//            long[] compared1D = new long[arrNNR.length];
            for(int j = 0; j < arrNNR.length; j++) {
                compared2D[i][j] = arrNNR[j] - arrNNV[j];
            }
        }
        return compared2D;
    }

    public static double[][] getComparisonReiseDividedVictor(long[][] resultNNR, long[][] resultNNV)
            throws IndexOutOfBoundsException {
        if(resultNNR.length != resultNNV.length) {
            throw new IndexOutOfBoundsException("result data are not same length");
        }
        double[][] compared2D = new double[resultNNR.length][resultNNR[0].length];
        for(int i = 0; i < resultNNR.length; i++) {
            long[] arrNNR = resultNNR[i];
            long[] arrNNV = resultNNV[i];
            if(arrNNR.length != arrNNV.length) {
                throw new IndexOutOfBoundsException("column length do not match");
            }
//            long[] compared1D = new long[arrNNR.length];
            for(int j = 0; j < arrNNR.length; j++) {
                if(arrNNR[j] != 0 || arrNNV[j] != 0) {
                    compared2D[i][j] = (double)(arrNNR[j] / arrNNV[j]);
                }
                else {
                    compared2D[i][j] = Long.MAX_VALUE;
                }
            }
        }
        return compared2D;
    }

    public static void writeRuntime(String filename, long[][] arrTimes, String pathLayerConfig, int trainingIteration,
                                    float learningRate, String note) {
        PrintWriter printWriter;
        try {
            if(filename.charAt(filename.length() - 1) != 'v') {
                filename += ".csv";
            }
            File csvFile = new File(filename);
            printWriter = new PrintWriter(csvFile);
            if(arrTimes[0].length != 4) {
                printWriter.println("incorrect array");
            } else {
                String sLayerConfig = "";
                try{
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(pathLayerConfig));
                    sLayerConfig = bufferedReader.readLine();
                } catch(FileNotFoundException e) {
                    e.printStackTrace();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                printWriter.println(sLayerConfig);
                printWriter.println("Training Iteration: " + trainingIteration);
                printWriter.println("Learning Rate: " + learningRate + "\n");
                printWriter.println("Layer;Data;Train;Runtime  -  " + note);
                int i = 0;
                for (long timeArr[] : arrTimes) {
                    for(long time : timeArr) {
                        if (i != arrTimes[0].length - 1) {
                            printWriter.print(time + ";");
                        } else {
                            printWriter.print(time);
                        }
                        ++i;
                    }
                    i = 0;
                    printWriter.println();
                }
            }
            printWriter.println();

            // mean
            printWriter.println("Mean");
            long means[] = new long[4];

            for(int i = 0; i < arrTimes[0].length; i++) {
                for(int j = 0; j < arrTimes.length; j++) {
                    switch (i) {
                        case 0:
                            means[0] += arrTimes[j][i];
                            break;
                        case 1:
                            means[1] += arrTimes[j][i];
                            break;
                        case 2:
                            means[2] += arrTimes[j][i];
                            break;
                        case 3:
                            means[3] += arrTimes[j][i];
                            break;
                        default:
                            printWriter.println("error with mean" + i);
                            break;
                    }
                }
            }
            for(int i = 0; i < means.length; i++) {
                means[i] = means[i]/arrTimes.length;
                if(i != means.length - 1) {
                    printWriter.print(means[i] + ";");
                } else {
                    printWriter.print(means[i]);
                }
            }

//            // standard deviation
//            printWriter.println("standard deviation");
//            long std[] = new long[4];
//            for(int i = 0; i < arrTimes[0].length; i++) {
//                for(int j = 0; j < arrTimes.length; j++) {
//                    switch (i) {
//                        case 0:
//                            std[0] += Math.pow(arrTimes[j][i] - means[0], 2);
//                            break;
//                        case 1:
//                            std[1] += Math.pow(arrTimes[j][i] - means[1], 2);
//                            break;
//                        case 2:
//                            std[2] += Math.pow(arrTimes[j][i] - means[2], 2);
//                            break;
//                        case 3:
//                            std[3] += Math.pow(arrTimes[j][i] - means[3], 2);
//                            break;
//                        default:
//                            printWriter.println("error with std" + i);
//                            break;
//                    }
//                }
//            }
//            for(int i = 0; i < std.length; i++) {
//                std[i] = std[i]/arrTimes.length;
//                if(i != std.length - 1) {
//                    printWriter.print(std[i] + ";");
//                } else {
//                    printWriter.print(std[i]);
//                }
//            }

            printWriter.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeRuntimeDouble(String filename, double[][] arrTimes, String pathLayerConfig, int trainingIteration,
                                    float learningRate, String note) {
        PrintWriter printWriter;
        try {
            if(filename.charAt(filename.length() - 1) != 'v') {
                filename += ".csv";
            }
            File csvFile = new File(filename);
            printWriter = new PrintWriter(csvFile);
            if(arrTimes[0].length != 4) {
                printWriter.println("incorrect array");
            } else {
                String sLayerConfig = "";
                try{
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(pathLayerConfig));
                    sLayerConfig = bufferedReader.readLine();
                } catch(FileNotFoundException e) {
                    e.printStackTrace();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                printWriter.println(sLayerConfig);
                printWriter.println("Training Iteration: " + trainingIteration);
                printWriter.println("Learning Rate: " + learningRate + "\n");
                printWriter.println("Layer;Data;Train;Runtime  -  " + note);
                int i = 0;
                for (double timeArr[] : arrTimes) {
                    for(double time : timeArr) {
                        if (i != arrTimes[0].length - 1) {
                            printWriter.print(time + ";");
                        } else {
                            printWriter.print(time);
                        }
                        ++i;
                    }
                    i = 0;
                    printWriter.println();
                }
            }
            printWriter.println();

            // mean
            printWriter.println("Mean");
            long means[] = new long[4];

            for(int i = 0; i < arrTimes[0].length; i++) {
                for(int j = 0; j < arrTimes.length; j++) {
                    switch (i) {
                        case 0:
                            means[0] += arrTimes[j][i];
                            break;
                        case 1:
                            means[1] += arrTimes[j][i];
                            break;
                        case 2:
                            means[2] += arrTimes[j][i];
                            break;
                        case 3:
                            means[3] += arrTimes[j][i];
                            break;
                        default:
                            printWriter.println("error with mean" + i);
                            break;
                    }
                }
            }
            for(int i = 0; i < means.length; i++) {
                means[i] = means[i]/arrTimes.length;
                if(i != means.length - 1) {
                    printWriter.print(means[i] + ";");
                } else {
                    printWriter.print(means[i]);
                }
            }

//            // standard deviation
//            printWriter.println("standard deviation");
//            long std[] = new long[4];
//            for(int i = 0; i < arrTimes[0].length; i++) {
//                for(int j = 0; j < arrTimes.length; j++) {
//                    switch (i) {
//                        case 0:
//                            std[0] += Math.pow(arrTimes[j][i] - means[0], 2);
//                            break;
//                        case 1:
//                            std[1] += Math.pow(arrTimes[j][i] - means[1], 2);
//                            break;
//                        case 2:
//                            std[2] += Math.pow(arrTimes[j][i] - means[2], 2);
//                            break;
//                        case 3:
//                            std[3] += Math.pow(arrTimes[j][i] - means[3], 2);
//                            break;
//                        default:
//                            printWriter.println("error with std" + i);
//                            break;
//                    }
//                }
//            }
//            for(int i = 0; i < std.length; i++) {
//                std[i] = std[i]/arrTimes.length;
//                if(i != std.length - 1) {
//                    printWriter.print(std[i] + ";");
//                } else {
//                    printWriter.print(std[i]);
//                }
//            }

            printWriter.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static long mean(long[] arr) {
        long mean = 0;
        for(long num : arr) {
            mean += num;
        }
        mean /= arr.length;

        return mean;
    }

    public static String[] getSigmoidFuncOnly(String path) {
        int[] layerConfig = NeuralUtilReise.getlayerConfig(path);
        String[] func = new String[layerConfig.length];
        int i = 1;
        func[0] = "";
        while(i < (layerConfig.length)) {
            func[i] = "sigmoid";
            i++;
        }
        return func;
    }

    public static void writeTotalErrorToCSV(String filename, double[] arr, int trainingIteration, float learningRate,
                                            String note) {
        PrintWriter printWriter;
        try {
            if (filename.charAt(filename.length() - 1) != 'v') {
                filename += ".csv";
            }
            File csvFile = new File(filename);
            printWriter = new PrintWriter(csvFile);
            printWriter.println(note);
            printWriter.println("Training Iteration: " + trainingIteration);
            printWriter.println("Learning Rate: " + learningRate + "\n");
            printWriter.println("Total Error for each data:");
            for(int i = 0; i < arr.length - 1; i++) {
                if(i != arr.length - 2) {
                    printWriter.print(arr[i] + ";");
                } else {
                    printWriter.println(arr[i]);
                }
            }
            printWriter.println("\nMean of total Error: " + arr[arr.length - 1]);
            printWriter.close();
        } catch(FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public static double[] getIterationForDeltaGoal(String networkType, double deltaGoal, String pathLayerConfig,
                                               String pathTrainingData, float trainingRate) {
        int trainIterations = 0;
        double[] resultData = new double[3];
        double totalError;
        if(networkType.toLowerCase().contains("reise")) {
            NeuralNetworkReise nnR = new NeuralNetworkReise(pathLayerConfig, -1, 1);
            nnR.getTrainingDataLearnable(pathTrainingData);
            trainIterations = 0;
            resultData[2] = System.nanoTime();
            do {
                nnR.train(1, trainingRate);
                ++trainIterations;
                double[] totalErrors = nnR.passWithExpectedOutput(pathTrainingData);
                totalError = totalErrors[totalErrors.length - 1];
                if(trainIterations > 100000) {
                    trainIterations = 999999;
                    break;
                }
            }
            while(totalError > deltaGoal);
            resultData[2] = System.nanoTime() - resultData[2];
            resultData[1] = totalError;
            resultData[0] = trainIterations;
        }
        else {
            String[] func = getSigmoidFuncOnly(pathLayerConfig);
            int[] layerConfig = NeuralUtilReise.getlayerConfig(pathLayerConfig);
            int inlen = layerConfig[0];
            int outlen = layerConfig[layerConfig.length - 1];
            try {
                TrainingDataVic tdV = new TrainingDataVic(pathLayerConfig, pathTrainingData, inlen, outlen, func);
                NeuralNetworkVic nnV = new NeuralNetworkVic(tdV);
                resultData[2] = System.nanoTime();
                do {
                    nnV.train(1, trainingRate);
                    ++trainIterations;
                    if(trainIterations > 100000) {
                        trainIterations = 999999;
                        break;
                    }
                }
                while(nnV.getCurrentTotalError() > deltaGoal);
                resultData[2] = System.nanoTime() - resultData[2];
                resultData[1] = nnV.getCurrentTotalError();
                resultData[0] = trainIterations;
            } catch(IOException e) {
                System.out.println(e);
            }
        }

        return resultData;
    }

    public static void writeIterationDataToCSV(String filename, double[] iterationData, String[]  trainAndlayerData,
                                               float learningRate) {
        PrintWriter printWriter;
        try {
            File csvFile = new File(filename);
            printWriter = new PrintWriter(csvFile);
            printWriter.println("Goal total Error evaluation");
            printWriter.println("Training Data: " + trainAndlayerData[0]);
            printWriter.println("LayerConfig: " + trainAndlayerData[1]);
            printWriter.println("Learning Rate: " + learningRate);
            printWriter.println("Target Total Error: " + iterationData[0] + "\n");
            printWriter.println("Reise total Error, Iteration and Time needed: " + iterationData[2] + "  -  "
                    + iterationData[1] + "  -  " + iterationData[3] + " (time)");
            printWriter.println("Victor total Error, Iteration and Time needed: " + iterationData[5] + "  -  "
                    + iterationData[4] + "  -  " + iterationData[6] + " (time)");
            double compError = iterationData[2] - iterationData[5];
            double compIter = iterationData[1] - iterationData[4];
            double compTime = iterationData[3] - iterationData[6];
            printWriter.println("Comparison; Reise MINUS Victor: " + compError + "  -  "
                    + compIter + "  -  " + compTime + " (time)");
            printWriter.close();
        } catch(FileNotFoundException e) {
            System.out.println(e);
        }
    }

    // Reise Method End

    public static void compareTotalError() throws IOException {
        String PathLay = "layer_config/";
        String PathTra = "training_data/";
        String prefix = "V";
//        String[] f = new String[]{"", "sigmoid", "sigmoid"};
        File training_data = new File("training_data");
        File layer_config = new File("layer_config");

        File[] dirTrainingData = training_data.listFiles(pathname -> pathname.getName().startsWith(prefix));
        File[] dirLayerConfig = layer_config.listFiles(pathname -> pathname.getName().startsWith(prefix));

        Arrays.sort(dirTrainingData);
        Arrays.sort(dirLayerConfig);

        NeuralNetworkReise nreise;
        NeuralNetworkVic nvic;
        TrainingDataVic tdata;
        //V10, R1,R5,R2,R3,
        //
        //initialisiere die trainingssätze
//        nvic = new NeuralNetworkVic(tdata);
//        nreise = new NeuralNetworkReise(PathLay + "V10_layerConfig.csv", -1 , 1);
//        nreise.getTrainingDataLearnable(PathTra +"V10_tData.csv");
//        nreise.weightAndBiasConfig(PathLay + "V10_layerConfig.csv");
//
//        nreise.train(10000, 0.05f);
//        nreise.writeTotalErrors("python/totalErrorReise.csv");
//
//        nvic.train(100000, 0.05);
//
//        nreise.print();
//        nvic.writeTotalErrors("python/totalErrorVic.csv");
        // für jedes File aus der trainingsdatei
        System.out.println("Starting with evaluating Training data ...");
        for (int i = 1; i < 2; i++) {

            String tag = dirLayerConfig[i].getName();
            tag = tag.substring(0, 3);

            tdata = new TrainingDataVic(PathLay + dirLayerConfig[i].getName(),
                    PathTra + dirTrainingData[i].getName());
            nvic = new NeuralNetworkVic(tdata);
            nreise = new NeuralNetworkReise(PathLay + dirLayerConfig[i].getName(),
                    -1, 1);
            nreise.getTrainingDataLearnable(PathTra + dirTrainingData[i].getName());
//
            nreise.train(100000, 10e6f);
            nreise.writeTotalErrors("python/totalErrorReise_" + tag);
            System.out.println("Finished Training Reise for " + tag);


            nvic.train(100000, 0.05f);
            nvic.writeTotalErrors("python/totalErrorVic_" + tag + ".csv");
            System.out.println("Finished Training Victor for " + tag);
        }

    }

    public static void learnrates() throws IOException {

        String PathLay = "layer_config/V9_layerConfig.csv";
        String PathTra = "training_data_known/V9_tData_known.csv";

        float[] LRs = {
                2.5f,
                1.5f,
                1.0f,
                0.5f,
                0.333f,
                0.25f,
                0.1f,
                0.01f,
                0.001f,
                0.00001f

        };


        NeuralNetworkReise nreise;
        NeuralNetworkVic nvic;
        TrainingDataVic tdata = new TrainingDataVic(PathLay, PathTra);

        System.out.println("Starting training with different LRs ...");
        System.out.println("Used Training: V9");

        for (float LR: LRs) {
            nvic = new NeuralNetworkVic(tdata);

            nreise = new NeuralNetworkReise(PathLay, -1 , 1);
            nreise.getTrainingDataLearnable(PathTra);


            nreise.train(10000, LR);
            nreise.writeTotalErrors("python/learn/totalErrR_" + LR);
            System.out.println("Finished Training Reise with " + LR);

            float[] out = nreise.getOutputVektor(new float[] {1, 1, 1, 1 ,1 , 1, 1});
            nreise.print();

            nvic.train(10000, LR);
            nvic.writeTotalErrors("python/learn/totalErrV_" + LR + ".csv");
            System.out.println("Finished Training Victor with " + LR);


        }

    }


    public static void learnratesUnknowndata() throws IOException {

        String PathLay = "layer_config/V2_layerConfig.csv";
        String PathTra = "training_data_known/V2_tData_known.csv";

        float[] LRs = {
                2.5f,
                1.5f,
                1.0f,
                0.5f,
                0.333f,
                0.25f,
                0.1f,
                0.01f,
                0.001f,
                0.00001f

        };

        float[][] unknown = {
                {0,0,1},
                {0,0,0},
                {1.0732638f,0.97688174f,1.0207669f},
                {1.0947442f,1.0906969f,0.026270742f}
        };


        NeuralNetworkReise nreise;
        NeuralNetworkVic nvic;
        TrainingDataVic tdata = new TrainingDataVic(PathLay, PathTra);
        TrainingDataVic tdataUnknown = new TrainingDataVic(PathLay,
                "training_data_unknown/V2_tData_unknown.csv");

        System.out.println("Starting training with different LRs ...");
        System.out.println("Used Training: V2");

        for (float LR: LRs) {
            nvic = new NeuralNetworkVic(tdata);

            nreise = new NeuralNetworkReise(PathLay, -1 , 1);
            nreise.getTrainingDataLearnable(PathTra);


            nreise.train(10000, LR);
            System.out.println("Finished Training Reise with " + LR);

            nreise.computeUnknownData(unknown, "outputReise" + LR);




//            nvic.train(10000, LR);
//            System.out.println("Finished Training Victor with " + LR);
//
//            nvic.computeUnknownData(tdataUnknown, "outputVic"+ LR);


        }

    }
}
