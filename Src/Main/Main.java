package Src.Main;

import Src.NetzReise.NeuralNetworkReise;
import Src.NetzVic.NeuralNetworkVic;
import Src.NetzVic.TrainingDataVic;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        // Reises Netz
        String sPath;
        String sPathForTData;
        String Path;
        String[] function = new String[]{"", "sigmoid", "sigmoid"};

        sPathForTData = "training_data_and_layer_config\\R5_tData.csv";
        sPath = "training_data_and_layer_config\\R5_layerConfig.csv";
        Path = "training_data_and_layer_config/";

        //Trainingdata
        TrainingDataVic trainingV1 = new TrainingDataVic(Path + "V1_layerConfig.csv", Path + "V1_tData.csv", 3, 2, function);

        // Neural Network creation
        //NeuralNetworkReise nnr = new NeuralNetworkReise(sPath, -1, 1);

        NeuralNetworkVic nnv = new NeuralNetworkVic(trainingV1);NeuralNetworkReise nnr = new NeuralNetworkReise(Path + "R5_layerConfig.csv", -1, 1);

        //nnr.getTrainingDataLearnable(sPathForTData);
        nnr.getTrainingDataLearnable(Path + "R5_tData.csv");
//        nn.weightAndBiasConfig(sPath); // manual weights and bias configuration
        //time start
        nnr.train(100000, 0.05f);
        nnv.train(100000, 0.5);
        //time end

        nnr.print();
//        nn.writeTotalErrors("totalError");


        // Victors Netz





        // Reises Platz Begin
        // Reises Platz End



        // Vicotrs Platz Begin
        //Victors Platz End


        
        // Philips Platz Begin
        // Philips Platz End


    }

    public void compareTrainingTime() throws IOException {
        String Path = "training_data_and_layer_config/";
        String[] f = new String[]{"", "sigmoid", "sigmoid"};

        NeuralNetworkReise nreise = new NeuralNetworkReise(Path + "R1_layerConfig.csv", -1 , 1);
        NeuralNetworkVic nvic = new NeuralNetworkVic();


        TrainingDataVic v10 = new TrainingDataVic(Path + "V10_layerConfig.csv", Path + "V10_tData.csv", 5,6, f);
        //V10, R1,R5,R2,R3,
        //
        //initialisiere die trainingssätze


    }
}
