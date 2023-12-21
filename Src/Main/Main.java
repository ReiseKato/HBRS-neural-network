package Src.Main;

import Src.NetzReise.NeuralNetworkReise;
import Src.NetzVic.NeuralNetworkVic;
import Src.NetzVic.TrainingDataVic;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
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
        // Declaration Begin
        String sPathLayerConfig;
        String sPathTrainingData;
        String pathToResults = "results\\";
        // Declaration End

        // CODE 0 Begin
        // Single Runtime Reise

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
//        writeRuntime(pathToResults, runtimeReise, sPath);

        // check if sum is runtimeReise[0] and [4]

        // CODE 0 End


        // CODE 1 Begin
        // Bunch Runtime Reise
        File dirTraining = new File("training_data");
        File dirLayer = new File("layer_config");
        File[] dirTrainingListing = dirTraining.listFiles();
        File[] dirLayerListing = dirLayer.listFiles();
        if(dirLayerListing.length != dirTrainingListing.length) {
            System.out.println("Layer Configs and Training Data are not same!");
        } else {
            for (int i = 0; i < dirTrainingListing.length; i++) {
                sPathTrainingData = dirTrainingListing[i].getPath();
                sPathLayerConfig = dirLayerListing[i].getPath();
                long[][] runtime = runtimeReise(sPathLayerConfig, sPathTrainingData, 1000, 0.05f, 10);
                //long[] runtime2 = runtimeVictor(sPathLayerConfig, sPathTrainingData, 1000, 0.05f, 3,8, new String[]{"", "sigmoid", "sigmoid"}); // indem Fall stimmt die
                pathToResults = "results\\";
//                pathToResults += sPathTrainingData;
                Path p = Paths.get(dirTrainingListing[i].getName());
                String file = pathToResults + p.getFileName().toString();
                file = file.substring(0, file.indexOf("_") + 1);
                file += "Runtime";
                writeRuntime(file, runtime, sPathLayerConfig);
            }
        }








        // CODE 1 End

        // CODE 2 Begin
        // Output Comparison Reise
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




        // Reises Platz End



        // Vicotrs Platz Begin


        // Victors Platz End
       // compareTotalError();

        
        // Philips Platz Begin
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

    public static void writeRuntime(String filename, long[][] arrTimes, String pathLayerConfig) {
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
                printWriter.println("Layer;Data;Train;Runtime");
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

    public static long[] runtimeVictor(String pathLayerConfig, String pathTrainingData,
                                       int trainingIteration, float learningRate,
                                       int inlen, int outlen, String[] func) throws IOException {
        long timesV[] = new long[4];
        long start = System.nanoTime(); // time start
        TrainingDataVic tdV = new TrainingDataVic(pathLayerConfig, pathTrainingData,inlen, outlen, func);
        timesV[1] = System.nanoTime(); // time getting training data
        NeuralNetworkVic nnV = new NeuralNetworkVic(tdV);
        timesV[0] = System.nanoTime(); // time layer config
        nnV.train(trainingIteration, learningRate);
        timesV[2] = System.nanoTime(); // time train
        timesV[3] = timesV[2]; // time stop

        timesV[3] = timesV[3] - start; // final whole runtime of model
        timesV[2] = timesV[2] - timesV[1]; // final training time
        timesV[1] = timesV[1] - timesV[0]; // final getting training data time
        timesV[0] = timesV[0] - start; // final layer config time

        return timesV;
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
