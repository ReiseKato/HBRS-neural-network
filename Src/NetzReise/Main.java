package Src.NetzReise;

public class Main {
    public static void main(String args[]) {
        String sPath;
        String sPathForTData;

        sPathForTData = "training_data_and_layer_config\\R1_tData.csv";
        sPath = "training_data_and_layer_config\\R1_layerConfig.csv";

        long time[] = new long[2];
        time[0] = System.nanoTime();
        // Neural Network creation
        NeuralNetworkReise nn = new NeuralNetworkReise(sPath, -1, 1);

        nn.getTrainingDataLearnable(sPathForTData);
//        nn.weightAndBiasConfig(sPath); // manual weights and bias configuration
        //time start
        nn.train(100000, 0.05f);
        //time end
        time[1] = System.nanoTime();

        nn.print();
        System.out.println(time[1] - time[0]);
//        nn.writeTotalErrors("totalError");
    }
}
