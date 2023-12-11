package Src.Main;

import Src.NetzReise.NeuralNetworkReise;

public class Main {
    public static void main(String args[]) {
        // Reises Netz
        String sPath;
        String sPathForTData;

        sPathForTData = "training_data_and_layer_config\\R5_tData.csv";
        sPath = "training_data_and_layer_config\\R5_layerConfig.csv";

        // Neural Network creation
        NeuralNetworkReise nn = new NeuralNetworkReise(sPath, -1, 1);

        nn.getTrainingDataLearnable(sPathForTData);
//        nn.weightAndBiasConfig(sPath); // manual weights and bias configuration
        //time start
        nn.train(100000, 0.05f);
        //time end

        nn.print();
//        nn.writeTotalErrors("totalError");


        // Victors Netz
        





    }
}
