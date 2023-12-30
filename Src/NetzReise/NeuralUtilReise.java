package Src.NetzReise;
import Src.NetzVic.NeuronVic;

import java.io.*;
import java.util.*;

public class NeuralUtilReise {
    static int counter;

    /** create a random float number between float min and float max
     *  --> used to randomly generate weights and biases
     */
    public static float RandomFloatNum(float min, float max) {
        float temp = (float)Math.random();
        float num = min + (float)Math.random() * (max - min);
        if(temp < 0.5) {
            return num;
        } else {
            return -num;
        }
    }

    /** read weight and Bias form a csv and return it as a Float[][] */
    public static Float[][] readWeightsAndBias(String path) {
        int[] arrLayer = NeuralUtilReise.getlayerConfig(path);
        int sum = 0;
        for(int i = 1; i < arrLayer.length; i++) {
            sum += arrLayer[i] + 1;
        }
        //sum += arrLayer.length - 2;
        int maxNeurons = Arrays.stream(arrLayer).max().getAsInt();

        String line;
        String layerConfig;
        String data[];
        String weightConfig[][] = new String[sum][maxNeurons];
        Float[][] fWeightConfig = new Float[sum][maxNeurons];
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            layerConfig = bufferedReader.readLine();
            // System.out.println(layerConfig);
            int i = 0;
            while((line = bufferedReader.readLine()) != null) {
                //line = bufferedReader.readLine();
                data = line.split(";");
                // System.out.println(Arrays.toString(data));
                weightConfig[i] = data;
                fWeightConfig[i] = Arrays.stream(data).map(Float::valueOf).toArray(Float[]::new);
                i++;
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

//        float[][] fWeightConfig = new float[weightConfig.length][weightConfig[0].length];
//        for(int i = 0; i < weightConfig.length; i++) {
//            for(int j = 0; j < weightConfig[i].length; j++) {
//                fWeightConfig[i][j] = Float.parseFloat(weightConfig[i][j]);
//            }
//        }

//        float[][] fWeightConfig;
//        fWeightConfig = Arrays.stream(weightConfig).map(Float::valueOf).toArray(Float[]::new);

        return fWeightConfig;
    }

    /** get Layer configuration and parse it as array of int */
    public static int[] getlayerConfig(String path) {
        String sLayerConfig;
        String[] arrLayerConfig = null;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            sLayerConfig = bufferedReader.readLine();
            arrLayerConfig = sLayerConfig.split(";");
            // System.out.println(sLayerConfig);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        int[] iLayerConfig = new int[arrLayerConfig.length - 1];
        for(int i = 1; i < arrLayerConfig.length; i++) {
            iLayerConfig[i - 1] = Integer.valueOf(arrLayerConfig[i]);
        }

        return iLayerConfig;
    }

    /** get the specific Weights and Bias for each Neuron by parsing the index of the Neuron and Layer */
    public static float[] getSpecificWeights(Float[][] weightsAndBias, int[] layerConfig,
                                             int neuronNumber, int layerNumber) {
        float[] arr = new float[layerConfig[layerNumber - 1] + 1];
        int layerCountInCsv = 0;
        if(layerNumber != 1) {
            for(int i = 2; i < layerNumber + 1; i++) {
                layerCountInCsv = layerConfig[i] + 1;
            }
//             layerCountInCsv = layerConfig[layerNumber] + 1;
        }
        for(int i = 0; i <= layerConfig[layerNumber - 1]; i++) {
            arr[i] = weightsAndBias[layerCountInCsv + i][neuronNumber];
        }

        return arr;
    }

    /** get Input Data count
     *      find out how many Inputs each dataset has
      */
    public static int getTrainingInputCount(String path) {
//        int counter = 0;
        String line;

        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            counter = 0;
            while((line = br.readLine()) != null) {
                counter++;
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return counter;
    }

    /** get the Training Input Data only */
    public static float[] getTrainingInputData(String path, int[] layerConfig, int index) {
        String line;
        String[] data;
        String[][] training = new String[counter][];
        Float[][] fTraining = new Float[counter][];
        int inputLength = layerConfig[0];

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            // System.out.println(layerConfig);
            int i = 0;
            while((line = bufferedReader.readLine()) != null) {
                //line = bufferedReader.readLine();
                data = line.split(";");
                // System.out.println(Arrays.toString(data));
                training[i] = data;
                fTraining[i] = Arrays.stream(data).map(Float::valueOf).toArray(Float[]::new);
                i++;
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

//        for(Float[] row : fTraining) {
//            System.out.println(Arrays.toString(row));
//        }

        Float[] inputData_t;
        inputData_t = Arrays.copyOfRange(fTraining[index], 0, inputLength);
//        for(int i = 0; i < counter; i++) {
//            inputData_t = Arrays.copyOfRange(fTraining[i], 0, inputLength);
//            System.out.println(Arrays.toString(inputData_t));
//        }
//        Random r = new Random();
//        float fMIN = 0.0000000001f;
//        float fMAX = 0.1f;
        float[] fInpuData = new float[inputData_t.length];
        int z = 0;
        for(float __num__ : inputData_t) {
            fInpuData[z] = __num__;
            ++z;
        }

        return fInpuData;
    }

    /** get the Training Output Data only */
    public static float[] getTrainingOutputData(String path, int[] layerConfig, int index) {
//        getTrainingInputCount(path);
        String line;
        String[] data;
        String[][] training = new String[counter][];
        Float[][] fTraining = new Float[counter][];
        int inputLength = layerConfig[0];
        int outputLength = layerConfig[layerConfig.length - 1];

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            // System.out.println(layerConfig);
            int i = 0;
            while((line = bufferedReader.readLine()) != null) {
                //line = bufferedReader.readLine();
                data = line.split(";");
                // System.out.println(Arrays.toString(data));
                training[i] = data;
                fTraining[i] = Arrays.stream(data).map(Float::valueOf).toArray(Float[]::new);
                i++;
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

//        for(Float[] row : fTraining) {
//            System.out.println(Arrays.toString(row));
//        }

        Float[] inputData_t;
        inputData_t = Arrays.copyOfRange(fTraining[index], inputLength, inputLength + outputLength);
//        for(int i = 0; i < counter; i++) {
//            inputData_t = Arrays.copyOfRange(fTraining[i], 0, inputLength);
//            System.out.println(Arrays.toString(inputData_t));
//        }
        float[] fInputData = new float[inputData_t.length];
        int i = 0;
        for(float __num__ : inputData_t) {
            fInputData[i] = __num__;
            i++;
        }

        return fInputData;
    }

    public static Float[][] makeTrainingData(String trainData, String layerConfig) {
        int count = getTrainingInputCount(trainData);
        String line;
        String[] data;
//        String[][] training = new String[count*2][];
        Float[][] fTraining = new Float[count][];
        Float[][] fTrainingNew = new Float[count][];
        int[] iLayerConfig = getlayerConfig(layerConfig);
        int inputLength = iLayerConfig[0];
        int outputLength = iLayerConfig[iLayerConfig.length - 1];

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(trainData));
            // System.out.println(layerConfig);
            int i = 0;
            while((line = bufferedReader.readLine()) != null) {
                //line = bufferedReader.readLine();
                data = line.split(";");
                // System.out.println(Arrays.toString(data));
//                training[i] = data;
                fTraining[i] = Arrays.stream(data).map(Float::valueOf).toArray(Float[]::new);
                i++;
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        int indexForOriginalData = 0;

        for(int i = 0; i < count; i++) {
            Float[] inputData_t;
            Float[] outputData_t;
//            int indexForOriginalData = 0;
//            if(i%2 == 1) {
//                indexForOriginalData++;
//            }
            inputData_t = Arrays.copyOfRange(fTraining[indexForOriginalData], 0, inputLength);
            outputData_t = Arrays.copyOfRange(fTraining[indexForOriginalData], inputLength, inputLength + outputLength);
            indexForOriginalData++;
            Random r = new Random();
            float fMIN = 0.0000000001f;
            float fMAX = 0.1f;
            Float[] fInputDataRand = new Float[inputData_t.length];
            int j = 0;
            for(int z = 0; z < inputData_t.length; z++) {
                float __num__ = inputData_t[z];
                float randNum = fMIN + r.nextFloat() * (fMAX - fMIN);
                if(__num__ == 0) {
                    __num__ += randNum;
                } else {
                    if(Math.random() < 0.5) {
                        __num__ -= randNum;
                    } else {
                        __num__ += randNum;
                    }
                }
                fInputDataRand[j] = __num__;
                j++;
            }
            Float[] fData = new Float[inputData_t.length + outputData_t.length];
            int k = 0;
            for(Float __randInput__ : fInputDataRand) {
                fData[k] = __randInput__;
                ++k;
            }
            for(Float __output__ : outputData_t) {
                fData[k] = __output__;
                ++k;
            }
            fTrainingNew[i] = fData;
        }

        return fTrainingNew;
    }

    public static Float[][] mergeTrainData(String dataOne, String dataTwo, String layerConfig, int mergeNum) {
        int count = getTrainingInputCount(dataOne) + getTrainingInputCount(dataTwo);
        String lineClean;
        String lineNoise;
        String[] dataClean;
        String[] dataNoise;
        Float[][] newTraining_t = new Float[count][];
        int[] iLayerConfig = getlayerConfig(layerConfig);
        int inputLength = iLayerConfig[0];
        int outputLength = iLayerConfig[iLayerConfig.length - 1];

        try{
            BufferedReader bufferedReaderOne = new BufferedReader(new FileReader(dataOne));
            BufferedReader bufferedReaderTwo = new BufferedReader(new FileReader(dataTwo));
            // System.out.println(layerConfig);
            int i = 0;
            if(mergeNum == 0) {
                while ((lineClean = bufferedReaderOne.readLine()) != null) {
                    dataClean = lineClean.split(";");
                    newTraining_t[i] = Arrays.stream(dataClean).map(Float::valueOf).toArray(Float[]::new);
//                    System.out.println(lineClean);
                    i++;
                    lineNoise = bufferedReaderTwo.readLine();
                    dataNoise = lineNoise.split(";");
                    newTraining_t[i] = Arrays.stream(dataNoise).map(Float::valueOf).toArray(Float[]::new);
//                    System.out.println(lineNoise);
                    i++;
                }
            } else {
                while ((lineClean = bufferedReaderOne.readLine()) != null) {
                    dataClean = lineClean.split(";");
                    newTraining_t[i] = Arrays.stream(dataClean).map(Float::valueOf).toArray(Float[]::new);
                    i++;
                }
                while ((lineNoise = bufferedReaderTwo.readLine()) != null) {
                    dataNoise = lineNoise.split(";");
                    newTraining_t[i] = Arrays.stream(dataNoise).map(Float::valueOf).toArray(Float[]::new);
                    i++;
                }
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return newTraining_t;
    }

    public static void writeTrainData(Float[][] trainingData, String filename) {
        if(filename.charAt(filename.length() - 1) != 'v') {
            filename += ".csv";
        }
        PrintWriter printWriter;
        try {
            File csvFile = new File(filename);
            printWriter = new PrintWriter(csvFile);
            for(Float[] columnData : trainingData) {
                printWriter.println(arrToStringForCSV(columnData));
            }

            printWriter.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String arrToStringForCSV(Float[] arr) {
        String res = "";
        for(int i = 0; i < arr.length; i++) {
            res += arr[i];
            if(i != arr.length - 1) {
                res += ";";
            }
        }
        return res;
    }


    public static String[] LayerToString(LayerReise l) {

        String[] temp = new String[l.neurons.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = Float.toString(l.neurons[i].fValue);

        }
        return temp;
    }
}
