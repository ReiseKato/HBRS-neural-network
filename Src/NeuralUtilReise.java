package Src;
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
//        int[] layerConfig = NeuralUtil.getlayerConfig(sPath);
//        int inputLength = layerConfig[0];
//        int outputLength = layerConfig[layerConfig.length - 1];
//        int counter = 0;
//        String line;
//
//        try{
//            BufferedReader br = new BufferedReader(new FileReader(path));
//            counter = 0;
//            while((line = br.readLine()) != null) {
//                counter++;
//            }
//        } catch(FileNotFoundException e) {
//            e.printStackTrace();
//        } catch(IOException e) {
//            e.printStackTrace();
//        }

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
        float[] fInpuData = new float[inputData_t.length];
        int i = 0;
        for(float __num__ : inputData_t) {
            fInpuData[i] = __num__;
            i++;
        }

        return fInpuData;
    }

    /** get the Training Output Data only */
    public static float[] getTrainingOutputData(String path, int[] layerConfig, int index) {
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
}
