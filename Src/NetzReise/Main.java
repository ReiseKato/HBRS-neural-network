package Src.NetzReise;

public class Main {
    public static void main(String args[]) {
        String sPath = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\Src\\KW43_weights_trafficlights_classification_simplified.csv"; // Layer, Weight and Bias
        String sPathForTData = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\Src\\KW43_traindata_trafficlights_classification.csv"; // Training Data

        sPathForTData = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\training_data_and_layer_config\\R1_tData.csv";
        sPath = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\training_data_and_layer_config\\R1_layerConfig.csv";

        // Neural Network creation
        NeuralNetworkReise nn = new NeuralNetworkReise(sPath, -1, 1);

        nn.getTrainingDataLearnable(sPathForTData);
//        nn.weightAndBiasConfig(sPath); // manual weights and bias configuration
        nn.train(100000, 0.05f);

        nn.print();
        nn.writeWeightAndBias("layerConfig");
        nn.writeTotalErrors("totalError");
    }
}
