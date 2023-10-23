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


    public void readWeightsAndBias(String path) {
        String line;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

            while(bufferedReader.readLine() != null) {
                line = bufferedReader.readLine();
                System.out.println(line);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Path path = Paths.get("KW43_weights_trafficlights_classification_simplified.csv");
        String sPath = "S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\Src\\KW43_weights_trafficlights_classification_simplified.csv";
        String line;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(sPath));
            while(bufferedReader.readLine() != null) {
                line = bufferedReader.readLine();
                System.out.println(line);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
