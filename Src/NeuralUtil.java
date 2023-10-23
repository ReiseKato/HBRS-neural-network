package Src;
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class NeuralUtil {

    /** create a random float number between float min and float max */
    public static float RandomFloatNum(float min, float max) {
        float temp = (float)Math.random();
        float num = min + (float)Math.random() * (max - min);
        if(temp < 0.5) {
            return num;
        } else {
            return -num;
        }
    }

    /**have to see if this really makes sense
     * probably better in class "NeuralNetwork" because I need to use the Layers created there*/
    public static float gradientSum(float gradient, int indexCurrentLayer, int indexCurrentNeuron) {
        float sum = 0.0f;

        Layer curentLayer;

        return sum;
    }


    public static Float[][] readWeightsAndBias(String path) {
        int[] arrLayer = NeuralUtil.getlayerConfig(path);
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


    public static void main(String[] args) {
        Path path = Paths.get("KW43_weights_trafficlights_classification_simplified.csv");
        String sPath = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\Src\\KW43_weights_trafficlights_classification_simplified.csv"; // Desktop
        //String sPath = "/Users/samuraireise/Documents/HBRS/neural network/HBRS-neural-network/Src/KW43_weights_trafficlights_classification_simplified.csv"; // Mac

//        int[] arrLayer = NeuralUtil.getlayerConfig(sPath);
//        int sum = 0;
//        for(int i = 1; i < arrLayer.length; i++) {
//            sum += arrLayer[i] + 1;
//        }
//        //sum += arrLayer.length - 2;
//        int maxNeurons = Arrays.stream(arrLayer).max().getAsInt();
//
//        String line;
//        String layerConfig;
//        String data[];
//        String weightConfig[][] = new String[sum][maxNeurons];
//        try{
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(sPath));
//            layerConfig = bufferedReader.readLine();
//            System.out.println(layerConfig);
//            int i = 0;
//            while((line = bufferedReader.readLine()) != null) {
//                //line = bufferedReader.readLine();
//                data = line.split(";");
//                System.out.println(Arrays.toString(data));
//                weightConfig[i] = data;
//                i++;
//            }
//        } catch(FileNotFoundException e) {
//            e.printStackTrace();
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("\n\n");
//
//        for(String[] row : weightConfig) {
//            System.out.println(Arrays.toString(row));
//        }
//        System.out.println(sum);
//
//        System.out.println("\n\n");

//        float[][] fWeightConfig = new float[weightConfig.length][weightConfig[0].length];
//        for(int i = 0; i < weightConfig[0].length; i++) {
//            for(int j = 0; j < weightConfig.length; j++) {
//                fWeightConfig[i][j] = Float.parseFloat(weightConfig[j][i]);
//            }
//        }
//
//        for(float[] row : fWeightConfig) {
//            System.out.println(Arrays.toString(row));
//        }

        Float[][] weightsAndBias = NeuralUtil.readWeightsAndBias(sPath);
        for(Float[] row : weightsAndBias) {
            System.out.println(Arrays.toString(row));
        }


    }

}
