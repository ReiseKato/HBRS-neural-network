package Src.Main;

import Src.NetzReise.NeuralNetworkReise;
import Src.NetzReise.TrainingDataReise;

public class MainTest {
    public static void main(String[] args) {
        long start = System.nanoTime();
        NeuralNetworkReise nnR = new NeuralNetworkReise("layer_config\\R3_layerConfig.csv", -1, 1);
        nnR.getTrainingDataLearnable("training_data_merge_clean_noise\\R3_tData.csv_merge.csv");

        int trainIterations = 100000;
        float learningRate = 0.05f;
        nnR.train(trainIterations, learningRate);
        long end = System.nanoTime();
        System.out.println("trained");
        System.out.println("Time required: " + (end - start));
    }
}
