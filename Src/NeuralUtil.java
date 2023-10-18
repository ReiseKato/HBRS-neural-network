package Src;

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

}
