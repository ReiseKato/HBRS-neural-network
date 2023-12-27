package Src.Main;

import Src.NetzReise.NeuralNetworkReise;
import Src.NetzReise.NeuralUtilReise;
import Src.NetzVic.NeuralNetworkVic;
import Src.NetzVic.TrainingDataVic;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class  Main {
    public static void main(String args[]) throws IOException {
        // Reises Netz
//        String sPath;
//        String sPathForTData;
//        String Path;
//        String[] function = new String[]{"", "sigmoid", "sigmoid"};
//
//        sPathForTData = "training_data\\R1_tData.csv";
//        sPath = "layer_config\\R1_layerConfig.csv";
//        Path = "training_data_and_layer_config/";

        //Trainingdata
//        TrainingDataVic trainingV1 = new TrainingDataVic(Path + "V1_layerConfig.csv", Path + "V1_tData.csv", 3, 2, function);
//
//        // Neural Network creation
//        //NeuralNetworkReise nnr = new NeuralNetworkReise(sPath, -1, 1);
//
//        NeuralNetworkVic nnv = new NeuralNetworkVic(trainingV1);
//        NeuralNetworkReise nnr = new NeuralNetworkReise(Path + "R5_layerConfig.csv", -1, 1);
//
//        //nnr.getTrainingDataLearnable(sPathForTData);
//        nnr.getTrainingDataLearnable(Path + "R5_tData.csv");
////        nn.weightAndBiasConfig(sPath); // manual weights and bias configuration
//        //time start
//        nnr.train(100000, 0.05f);
//        nnv.train(100000, 0.5);
//        //time end
//
//        nnr.print();
//        nn.writeTotalErrors("totalError");


        // Victors Netz





        // Reises Platz Begin
        // Init Begin
//        String sPathLayerConfig;
//        String sPathTrainingData;
//        String pathToResults = "results\\";
        // Init End

        // CODE 0 Begin
        /** Single Runtime Reise */

//        NeuralNetworkReise nnr = new NeuralNetworkReise(sPath, -1, 1);
//        nnr.getTrainingDataLearnable(sPathForTData);
//        nnr.train(10,0.05f);


//        String[] functionArrV = {"", "sigmoid", "sigmoid", "sigmoid"};
//        sPath = "layer_config\\R1_layerConfig.csv";
//        long[][] runtimeReise = runtimeReise(sPath,sPathForTData,1000,0.05f, 10);
//        for(long[] arrTime : runtimeReise) {
//            System.out.println(Arrays.toString(arrTime));
//        }
//
//        pathToResults += "R1_RuntimeData";
//        writeRuntime(pathToResults, runtimeReise, sPath, "clean Data");

        // check if sum is runtimeReise[0] and [4]

        // CODE 0 End


        // CODE 1 Begin
        /** Bunch Runtime Reise */


//        File dirTraining = new File("training_data");
//        File dirLayer = new File("layer_config");
//        File[] dirTrainingListing = dirTraining.listFiles();
//        File[] dirLayerListing = dirLayer.listFiles();
//        if(dirLayerListing.length != dirTrainingListing.length) {
//            System.out.println("Count of Layer Configs and Training Data do NOT match");
//        } else {
//            for (int i = 0; i < dirTrainingListing.length; i++) {
//                sPathTrainingData = dirTrainingListing[i].getPath();
//                sPathLayerConfig = dirLayerListing[i].getPath();
//                long[][] runtimeR = runtimeReise(sPathLayerConfig, sPathTrainingData, 1000, 0.05f, 10);
////                long[][] runtimeV = runtimeVictor(sPathLayerConfig, sPathTrainingData, 1000, 0.05f, 10); // in dem Fall stimmt die
//                pathToResults = "results\\";
////                pathToResults += sPathTrainingData;
//                Path p = Paths.get(dirTrainingListing[i].getName());
//                String file = pathToResults + p.getFileName().toString();
//                file = file.substring(0, file.indexOf("_") + 1);
//                String fileReise = file + "Runtime_NNR";
//                writeRuntime(fileReise, runtimeR, sPathLayerConfig);
//                String fileVictor = file + "Runtime_NNV";
////                writeRuntime(fileVictor, runtimeV, sPathLayerConfig);
//            }
//        }


        // CODE 1 End

        // CODE 2 Begin
        /** Output Comparison Reise */
//        sPathLayerConfig = "layer_config\\R1_layerConfig.csv";
//        sPathTrainingData = "training_data\\R1_tData.csv";
//        NeuralNetworkReise nnR = new NeuralNetworkReise(sPathLayerConfig, -1, 1);
//        nnR.getTrainingDataLearnable(sPathTrainingData);
//        nnR.train(100000, 0.05f);
//        float[][] outputs = nnR.getOutputs2();
//        for(float[] output_row : outputs) {
//            System.out.println(Arrays.toString(output_row));
//        }
        // CODE 2 End

        // CODE 3 Begin
        /** train while totalError > target totalError */
//        double targetTotalError = 0.2; // define target total Error
//        float trainingRate = 0.05f;
//        File dirTraining = new File("training_data");
//        File dirLayer = new File("layer_config");
//        File[] dirTrainingListing = dirTraining.listFiles();
//        File[] dirLayerListing = dirLayer.listFiles();
//        if(dirLayerListing.length != dirTrainingListing.length) {
//            System.out.println("Count of Layer Configs and Training Data do NOT match");
//        } else {
//            for (int i = 0; i < dirTrainingListing.length; i++) {
//                sPathTrainingData = dirTrainingListing[i].getPath();
//                sPathLayerConfig = dirLayerListing[i].getPath();
//                double[] iterationReise =
//                        getIterationForDeltaGoal("reise", targetTotalError,
//                                sPathLayerConfig, sPathTrainingData, trainingRate);
//                double[] iterationVictor = getIterationForDeltaGoal("vic", targetTotalError,
//                        sPathLayerConfig, sPathTrainingData, trainingRate);
//
//                System.out.println("Training Data: " + dirTrainingListing[i].getName() + "\n"
//                        + "LayerConfig: " + Arrays.toString(NeuralUtilReise.getlayerConfig(sPathLayerConfig)) + "\n"
//                        + "Target Total Error: " + targetTotalError + "\n"
//                        + "Reise total Error and Iteration needed: " + iterationReise[1] + "  -  " + iterationReise[0]
//                        + "Victor total Error and Iteration needed: " + iterationVictor[1] + "  -  " + iterationVictor[0]
//                        + "\n"
//                );
//            }
//        }


        // CODE 3 End

        // CODE 4 Begin
        /** unknown data */
//        String sPathLayer = "layer_config\\R4_layerConfig.csv";
//        String knownData = "known_data\\R4_tData_unknown.csv";
//        String unknownData = "unknown_data\\R4_tData_unknown.csv";
//        NeuralNetworkReise nnR = new NeuralNetworkReise(sPathLayer, -1, 1);
//        nnR.getTrainingDataLearnable(knownData);
//        nnR.train(100000, 0.05f);
//        double[] totalErrorsUnknownDataReise = nnR.passWithExpectedOutput(unknownData);
//        System.out.println(Arrays.toString(totalErrorsUnknownDataReise));

        /** make new data with noise from already existing data */
//        File dirTraining = new File("training_data");
//        File dirLayer = new File("layer_config");
//        String targetDir = "training_data_noise\\";
//        File[] dirTrainingListing = dirTraining.listFiles();
//        File[] dirLayerListing = dirLayer.listFiles();
//        if(dirLayerListing.length != dirTrainingListing.length) {
//            System.out.println("Count of Layer Configs and Training Data do NOT match");
//        } else {
//            for (int i = 0; i < dirTrainingListing.length; i++) {
//                sPathTrainingData = dirTrainingListing[i].getPath();
//                sPathLayerConfig = dirLayerListing[i].getPath();
//                String filename = dirTrainingListing[i].getName();
//                filename = targetDir + "noise_" + filename;
//                Float[][] newData = NeuralUtilReise.makeTrainingData(sPathTrainingData, sPathLayerConfig);
//                NeuralUtilReise.writeTrainData(newData, filename);
//            }
//        }



        // CODE 4 End

        // CODE 5 Begin
        /** Auswertung auf unbekannten Daten */
//        String sPathLayer = "";
//        String knownData = "";
//        String unknownData = "";
//
//        File dirTrainingKnown = new File("training_data_known");
//        File dirTrainingUnknown = new File("training_data_unknown");
//        File dirLayer = new File("layer_config");
//        File[] dirTrainingKnownListing = dirTrainingKnown.listFiles();
//        File[] dirTrainingUnknownListing = dirTrainingUnknown.listFiles();
//        File[] dirLayerListing = dirLayer.listFiles();
//        if(dirLayerListing.length != dirTrainingKnownListing.length
//                && dirTrainingKnownListing.length != dirTrainingUnknownListing.length) {
//            System.out.println("Count of Layer Configs and Training Data do NOT match");
//        } else {
//            for (int i = 0; i < dirTrainingKnownListing.length; i++) {
//                knownData = dirTrainingKnownListing[i].getPath();
//                unknownData = dirTrainingUnknownListing[i].getPath();
//                String unknownDataFilename = dirTrainingUnknownListing[i].getName();
//                sPathLayerConfig = dirLayerListing[i].getPath();
//                NeuralNetworkReise nnR = new NeuralNetworkReise(sPathLayerConfig, -1, 1);
//                nnR.getTrainingDataLearnable(knownData);
//                nnR.train(1000, 0.05f);
//                double[] totalErrorsUnknownDataReise = nnR.passWithExpectedOutput(unknownData);
//                System.out.println(unknownDataFilename);
//                System.out.println(Arrays.toString(totalErrorsUnknownDataReise));
//            }
//        }

        // CODE 5 End

        // CODE Final Begin
        /**
         * evaluation of runtime
         *      --> all results in dir "results_runtime"
         * */
        // CONSTANT Begin
        int ITERATION_NETWORK_TRAINING = 10;
        float LEARNING_RATE = 0.05f;
        // CONSTANT End

        // Init Begin
        String sPathResultsDir = "results\\";
        String sPathLayerConfig;
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
        File[] dirTrainingCleanListing;
        File[] dirTrainingNoiseListing;
        File[] dirTrainingMergedListing;
        File[] dirTrainingKnownListing;
        File[] dirTrainingUnknownListing;
        File[] dirLayerListing;
        // Init End

        // evaluation of runtime on all usable data
//        int runtimeIteration = 3;
//        dirTrainingClean = new File("training_data");
//        dirTrainingNoise = new File("training_data_noise");
//        dirTrainingMerge = new File("training_data_merge_clean_noise");
//        dirLayer = new File("layer_config");
//        dirTrainingCleanListing = dirTrainingClean.listFiles();
//        dirTrainingNoiseListing = dirTrainingNoise.listFiles();
//        dirTrainingMergedListing = dirTrainingMerge.listFiles();
//        dirLayerListing = dirLayer.listFiles();
//        if(dirLayerListing.length != dirTrainingCleanListing.length
//                || dirLayerListing.length != dirTrainingNoiseListing.length
//                || dirLayerListing.length != dirTrainingMergedListing.length) {
//            System.out.println("Count of Layer Configs and Training Data do NOT match");
//        } else {
//            for (int i = 0; i < dirTrainingCleanListing.length; i++) {
//                sPathTrainingDataClean = dirTrainingCleanListing[i].getPath();
//                sPathTrainingDataNoise = dirTrainingNoiseListing[i].getPath();
//                sPathTrainingDataMerged = dirTrainingMergedListing[i].getPath();
//                sPathLayerConfig = dirLayerListing[i].getPath();
//                long[][] runtimeCleanR = runtimeReise(sPathLayerConfig, sPathTrainingDataClean,
//                        ITERATION_NETWORK_TRAINING, LEARNING_RATE, runtimeIteration);
////                long[][] runtimeCleanV = runtimeVictor(sPathLayerConfig, sPathTrainingDataClean,
////                        ITERATION_NETWORK_TRAINING, LEARNING_RATE, runtimeIteration);
////                sPathResultsDir = "results\\";
//                // write clean data results
//                sDataType = "clean_data";
////                String sPathResult = sPathResultsDir + sPathTrainingDataClean;
//                String sTrainData = dirTrainingCleanListing[i].getName();
////                String file = sPathResult + p.getFileName().toString();
//                String file = sTrainData.substring(0, sTrainData.indexOf("_") + 1);
////                file = file.substring(0, file.indexOf("_") + 1);
//                String filenameReise = sPathResultsDir + file + "Runtime_NNR_" + sDataType;
//                writeRuntime(filenameReise, runtimeCleanR, sPathLayerConfig, sDataType);
////                String filenameVictor = file + "Runtime_NNV_" + sDataType;
////                writeRuntime(filenameVictor, runtimeCleanV, sPathLayerConfig, sDataType);
//                filenameReise = "";
////                filenameVictor = "";
//                // write noise data results
//                sDataType = "noise_data";
//                long[][] runtimeNoiseR = runtimeReise(sPathLayerConfig, sPathTrainingDataNoise,
//                        ITERATION_NETWORK_TRAINING, LEARNING_RATE, runtimeIteration);
////                long[][] runtimeNoiseV = runtimeVictor(sPathLayerConfig, sPathTrainingDataNoise,
////                        ITERATION_NETWORK_TRAINING, LEARNING_RATE, runtimeIteration);
////                sPathResult = sPathResultsDir + sPathTrainingDataClean;
////                p = Paths.get(dirTrainingCleanListing[i].getName());
////                file = sPathResult + p.getFileName().toString();
//                sTrainData = dirTrainingCleanListing[i].getName();
//                file = sTrainData.substring(0, sTrainData.indexOf("_") + 1);
////                file = file.substring(0, file.indexOf("_") + 1);
//                filenameReise = sPathResultsDir + file + "Runtime_NNR_" + sDataType;
//                writeRuntime(filenameReise, runtimeNoiseR, sPathLayerConfig, sDataType);
////                filenameVictor = file + "Runtime_NNV_" + sDataType;
////                writeRuntime(filenameVictor, runtimeNoiseV, sPathLayerConfig, sDataType);
//                filenameReise = "";
////                filenameVictor = "";
//                // write merged data results
//                sDataType = "merge_data";
//                long[][] runtimeMergeR = runtimeReise(sPathLayerConfig, sPathTrainingDataMerged,
//                        ITERATION_NETWORK_TRAINING, LEARNING_RATE, runtimeIteration);
////                long[][] runtimeMergeV = runtimeVictor(sPathLayerConfig, sPathTrainingDataMerged,
////                        ITERATION_NETWORK_TRAINING, LEARNING_RATE, runtimeIteration);
////                sPathResult = sPathResultsDir + sPathTrainingDataClean;
////                p = Paths.get(dirTrainingCleanListing[i].getName());
////                file = sPathResult + p.getFileName().toString();
////                file = file.substring(0, file.indexOf("_") + 1);
//                sTrainData = dirTrainingCleanListing[i].getName();
//                file = sTrainData.substring(0, sTrainData.indexOf("_") + 1);
//                filenameReise = sPathResultsDir + file + "Runtime_NNR_" + sDataType;
//                writeRuntime(filenameReise, runtimeMergeR, sPathLayerConfig, sDataType);
////                filenameVictor = file + "Runtime_NNV_" + sDataType;
////                writeRuntime(filenameVictor, runtimeMergeV, sPathLayerConfig, sDataType);
//            }
//        }

        // evaluation of Comparison on known and unknown data
//        dirTrainingMerge = new File("training_data_merge_clean_noise");
//        dirTrainingKnown = new File("training_data_known");
//        dirTrainingUnknown = new File("training_data_unknown");
//        dirLayer = new File("layer_config");
//        dirTrainingMergedListing = dirTrainingMerge.listFiles();
//        dirTrainingKnownListing = dirTrainingKnown.listFiles();
//        dirTrainingUnknownListing = dirTrainingUnknown.listFiles();
//        dirLayerListing = dirLayer.listFiles();
//
//        if(dirLayerListing.length != dirTrainingKnownListing.length
//                && dirTrainingKnownListing.length != dirTrainingUnknownListing.length) {
//            System.out.println("Count of Layer Configs and Training Data do NOT match");
//        } else {
//            for (int i = 0; i < dirTrainingKnownListing.length; i++) {
//                sPathTrainingDataMerged = dirTrainingMergedListing[i].getPath();
//                sPathTrainingDataKnown = dirTrainingKnownListing[i].getPath();
//                sPathTrainingDataUnknown = dirTrainingUnknownListing[i].getPath();
//                sPathLayerConfig = dirLayerListing[i].getPath();
//                String unknownDataFilename = dirTrainingUnknownListing[i].getName();
//                NeuralNetworkReise nnR_MergeData = new NeuralNetworkReise(sPathLayerConfig, -1, 1);
//                NeuralNetworkReise nnR_knownData = new NeuralNetworkReise(sPathLayerConfig, -1, 1);
//                nnR_MergeData.getTrainingDataLearnable(sPathTrainingDataMerged);
//                nnR_knownData.getTrainingDataLearnable(sPathTrainingDataKnown);
//                nnR_knownData.train(ITERATION_NETWORK_TRAINING, LEARNING_RATE);
//                double[] totalErrorsUnknownDataReise_wholeTrained =
//                        nnR_MergeData.passWithExpectedOutput(sPathTrainingDataUnknown);
//                double[] totalErrorsUnknownDataReise_partialTrained =
//                        nnR_knownData.passWithExpectedOutput(sPathTrainingDataUnknown);
////                System.out.println(unknownDataFilename);
////                System.out.println("clean data train\n" + Arrays.toString(totalErrorsUnknownDataReise_cleanTrained));
////                System.out.println("partial data train\n" + Arrays.toString(totalErrorsUnknownDataReise_knownTrained));
//                sDataType = "whole_training_data";
//                String sResultFilename = unknownDataFilename.substring(0, unknownDataFilename.indexOf("_") + 1);
////                file = file.substring(0, file.indexOf("_") + 1);
//                filename = sPathResultsDir + sResultFilename + "Unknown_NNR_" + sDataType;
//                int[] iLayerConfig = NeuralUtilReise.getlayerConfig(sPathLayerConfig);
//                String slayerConfig = "layers: " + Arrays.toString(iLayerConfig);
//                writeTotalErrorToCSV(filename, totalErrorsUnknownDataReise_wholeTrained,
//                        slayerConfig + " " + sDataType);
//                sDataType = "partial_training_data";
//                sResultFilename = unknownDataFilename.substring(0, unknownDataFilename.indexOf("_") + 1);
//                filename = sPathResultsDir + sResultFilename + "Unknown_NNR_" + sDataType;
//                writeTotalErrorToCSV(filename, totalErrorsUnknownDataReise_partialTrained,
//                        slayerConfig + " " + sDataType);
//            }
//        }

        // evaluation on needed iteration until min. desired total error is reached
//        double targetTotalError = 0.2; // define target total Error
//        String[] trainAndLayer = new String[2];
//        dirTrainingClean = new File("training_data");
//        dirTrainingNoise = new File("training_data_noise");
//        dirTrainingMerge = new File("training_data_merge_clean_noise");
//        dirLayer = new File("layer_config");
//        dirTrainingCleanListing = dirTrainingClean.listFiles();
//        dirTrainingNoiseListing = dirTrainingNoise.listFiles();
//        dirTrainingMergedListing = dirTrainingMerge.listFiles();
//        dirLayerListing = dirLayer.listFiles();
//        if(dirLayerListing.length != dirTrainingCleanListing.length
//                || dirLayerListing.length != dirTrainingNoiseListing.length
//                || dirLayerListing.length != dirTrainingMergedListing.length) {
//            System.out.println("Count of Layer Configs and Training Data do NOT match");
//        } else {
//            for (int i = 0; i < dirTrainingCleanListing.length; i++) {
//                sPathTrainingDataClean = dirTrainingCleanListing[i].getPath();
//                sPathTrainingDataNoise = dirTrainingNoiseListing[i].getPath();
//                sPathTrainingDataMerged = dirTrainingMergedListing[i].getPath();
//                sPathLayerConfig = dirLayerListing[i].getPath();
//                String name = dirTrainingCleanListing[i].getName();
//                double[] iterationReise =
//                        getIterationForDeltaGoal("reise", targetTotalError,
//                                sPathLayerConfig, sPathTrainingDataClean, LEARNING_RATE);
//                double[] iterationVictor = getIterationForDeltaGoal("vic", targetTotalError,
//                        sPathLayerConfig, sPathTrainingDataClean, LEARNING_RATE);
//                PrintWriter printWriter;
//                double[] allIterationData = {targetTotalError,
//                        iterationReise[0], iterationReise[1], iterationVictor[0], iterationVictor[1]};
//                trainAndLayer[0] = dirTrainingCleanListing[i].getName();
//                trainAndLayer[1] = Arrays.toString(NeuralUtilReise.getlayerConfig(sPathLayerConfig));
//                sDataType = "clean_data";
//                filename = name.substring(0, name.indexOf("_") + 1);
//                filename = sPathResultsDir + filename + "Goal_total_error_" + sDataType;
//                writeIterationDataToCSV(filename, allIterationData, trainAndLayer);
////                System.out.println("Training Data: " + dirTrainingCleanListing[i].getName() + "\n"
////                        + "LayerConfig: " + Arrays.toString(NeuralUtilReise.getlayerConfig(sPathLayerConfig)) + "\n"
////                        + "Target Total Error: " + targetTotalError + "\n"
////                        + "Reise total Error and Iteration needed: " + iterationReise[1] + "  -  " + iterationReise[0]
////                        + "\nVictor total Error and Iteration needed: " + iterationVictor[1] + "  -  " + iterationVictor[0]
////                        + "\n"
////                );
//                iterationReise =
//                        getIterationForDeltaGoal("reise", targetTotalError,
//                                sPathLayerConfig, sPathTrainingDataNoise, LEARNING_RATE);
//                iterationVictor = getIterationForDeltaGoal("vic", targetTotalError,
//                        sPathLayerConfig, sPathTrainingDataNoise, LEARNING_RATE);
//                allIterationData[1] = iterationReise[0];
//                allIterationData[2] = iterationReise[1];
//                allIterationData[3] = iterationVictor[0];
//                allIterationData[4] = iterationVictor[1];
//                sDataType = "noise_data";
//                trainAndLayer[0] = dirTrainingNoiseListing[i].getName();
//                filename = name.substring(0, name.indexOf("_") + 1);
//                filename = sPathResultsDir + filename + "Goal_total_error_" + sDataType;
//                writeIterationDataToCSV(filename, allIterationData, trainAndLayer);
//                iterationReise =
//                        getIterationForDeltaGoal("reise", targetTotalError,
//                                sPathLayerConfig, sPathTrainingDataMerged, LEARNING_RATE);
//                iterationVictor = getIterationForDeltaGoal("vic", targetTotalError,
//                        sPathLayerConfig, sPathTrainingDataMerged, LEARNING_RATE);
//                allIterationData[1] = iterationReise[0];
//                allIterationData[2] = iterationReise[1];
//                allIterationData[3] = iterationVictor[0];
//                allIterationData[4] = iterationVictor[1];
//                sDataType = "merged_data";
//                trainAndLayer[0] = dirTrainingMergedListing[i].getName();
//                filename = name.substring(0, name.indexOf("_") + 1);
//                filename = sPathResultsDir + filename + "Goal_total_error_" + sDataType;
//                writeIterationDataToCSV(filename, allIterationData, trainAndLayer);
//
////                System.out.println("Training Data: " + dirTrainingCleanListing[i].getName() + "\n"
////                        + "LayerConfig: " + Arrays.toString(NeuralUtilReise.getlayerConfig(sPathLayerConfig)) + "\n"
////                        + "Target Total Error: " + targetTotalError + "\n"
////                        + "Reise total Error and Iteration needed: " + iterationReise[1] + "  -  " + iterationReise[0]
////                        + "Victor total Error and Iteration needed: " + iterationVictor[1] + "  -  " + iterationVictor[0]
////                        + "\n"
////                );
//            }
//        }

        // evaluation on different topologies


        // CODE Final End


        // Reises Platz End



        // Vicotrs Platz Begin


        // Victors Platz End
        // compareTotalError();

        
        // Philips Platz Begin
        // Nach Absprache beschlossen, dass Philip keinen Code schreibt
        // Philips Platz End


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
        String[] func = getSigmoidFuncOnly(pathLayerConfig);
        int[] layerConfig = NeuralUtilReise.getlayerConfig(pathLayerConfig);
        int inlen = layerConfig[0];
        int outlen = layerConfig[layerConfig.length - 1];
        long timesV[][] = new long[runtimeIteration][4];
        for(int i = 0; i < runtimeIteration; i++) {
            long start = System.nanoTime(); // time start
            TrainingDataVic tdV = new TrainingDataVic(pathLayerConfig, pathTrainingData,inlen, outlen, func);
            timesV[i][1] = System.nanoTime(); // time getting Training Data
            NeuralNetworkVic nnV = new NeuralNetworkVic(tdV);
            timesV[i][0] = System.nanoTime(); // time layer config
            nnV.train(trainingIteration, learningRate);
            timesV[i][2] = System.nanoTime(); // time training the model
            timesV[i][3] = timesV[i][2]; // time stop


            timesV[i][3] = timesV[i][3] - start; // final whole runtime of model
            timesV[i][2] = timesV[i][2] - timesV[i][1]; // final training time
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

    public static void writeRuntime(String filename, long[][] arrTimes, String pathLayerConfig, String note) {
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

    public static void writeTotalErrorToCSV(String filename, double[] arr, String note) {
        PrintWriter printWriter;
        try {
            if (filename.charAt(filename.length() - 1) != 'v') {
                filename += ".csv";
            }
            File csvFile = new File(filename);
            printWriter = new PrintWriter(csvFile);
            printWriter.println(note);
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
        double[] resultData = new double[2];
        double totalError;
        if(networkType.toLowerCase().contains("reise")) {
            NeuralNetworkReise nnR = new NeuralNetworkReise(pathLayerConfig, -1, 1);
            nnR.getTrainingDataLearnable(pathTrainingData);
            trainIterations = 0;
            do {
                nnR.train(1, trainingRate);
                ++trainIterations;
                double[] totalErrors = nnR.passWithExpectedOutput(pathTrainingData);
                totalError = totalErrors[totalErrors.length - 1];
            }
            while(totalError > deltaGoal);
            resultData[1] = totalError;
            resultData[0] = trainIterations;
        }
//        else {
//            String[] func = getSigmoidFuncOnly(pathLayerConfig);
//            int[] layerConfig = NeuralUtilReise.getlayerConfig(pathLayerConfig);
//            int inlen = layerConfig[0];
//            int outlen = layerConfig[layerConfig.length - 1];
//            try {
//                TrainingDataVic tdV = new TrainingDataVic(pathLayerConfig, pathTrainingData, inlen, outlen, func);
//                NeuralNetworkVic nnV = new NeuralNetworkVic(tdV);
//                do {
//                    nnV.train(1, trainingRate);
//                    ++trainIterations;
//                }
//                while(nnV.getCurrentTotalError() > deltaGoal);
//                resultData[1] = nnV.getCurrentTotalError();
//                resultData[0] = trainIterations;
//            } catch(IOException e) {
//                System.out.println(e);
//            }
//        }

        return resultData;
    }

    public static void writeIterationDataToCSV(String filename, double[] iterationData, String[]  trainAndlayerData) {
        PrintWriter printWriter;
        try {
            File csvFile = new File(filename);
            printWriter = new PrintWriter(csvFile);
            printWriter.println("Goal total Error evaluation");
            printWriter.println("Training Data: " + trainAndlayerData[0]);
            printWriter.println("LayerConfig: " + trainAndlayerData[1]);
            printWriter.println("Target Total Error: " + iterationData[0]);
            printWriter.println("Reise total Error and Iteration needed: " + iterationData[2] + "  -  " + iterationData[1]);
            printWriter.println("Victor total Error and Iteration needed: " + iterationData[4] + "  -  " + iterationData[3]);
            printWriter.close();
        } catch(FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public static void compareTotalError() throws IOException {
        String PathLay = "layer_config/";
        String PathTra = "training_data/";
        String[] f = new String[]{"", "sigmoid", "sigmoid"};

        NeuralNetworkReise nreise;
        NeuralNetworkVic nvic;

        TrainingDataVic v10 = new TrainingDataVic(PathLay + "V10_layerConfig.csv", PathTra + "V10_tData.csv", 5,6, f);
        //V10, R1,R5,R2,R3,
        //
        //initialisiere die trainingssätze
        nreise = new NeuralNetworkReise(PathLay + "V10_layerConfig.csv", -1 , 1);
        nvic = new NeuralNetworkVic(v10);
        nreise.getTrainingDataLearnable(PathTra +"V10_tData.csv");

        nreise.train(10000, 0.05f);
        nreise.writeTotalErrors("python/totalErrorReise.csv");

        nvic.train(10000, 0.05);
        nvic.writeTotalErrors("python/totalErrorVic.csv");
    }
}
