package Src;

public class Neuron {
    static float minWeight, maxWeight; // range of the weights
    float value; // each Neuron has exactly one value, which will be parsed to the Neurons in the next Layer except for the output Layer -> value = result
    float[] weights; // array because each neuron will be given as many weights as values from the previous layer
    float[] newWeights;
    float bias; // each Neuron has a specific bias which will be generated randomly by the Helper Class
    float gradient; // measures the change in all weights with regard to the change in error -> should be stored in each Neuron as each Neuron has its own output


    /** for hidden and output Layer Neurons */
    public Neuron(float[] weights, float bias) {
        this.weights = weights;
        this.bias = bias;
        this.newWeights = this.weights; // this.weights is just temp. doing this, so I can store values in arr newWeights (can't store anything if it's null)
        this.gradient = 0;
    }

    /** for input Neuron */
    public Neuron(float value) {
        this.weights = null;
        this.bias = -1;
        this.value = value;
        // this.newWeights = this.weights; // shouldn't initialize it twice hahaha
        this.gradient = 0;
    }

    /** function to determine output signal (still in devPhase, cause not understanding it well) */
    public static float ReLu(float num) {
        if (num <= 0) {
            return 0;
        }
        return num;
    }


    /** update all the weights. relevant for machine learning*/
    public void updateWeights() { // doesn't need parameter as we are saving all new weights inside the class
        this.weights = newWeights;
    }

    /** set the weight range all Neurons will get. has to be set before anything else */
    public static void setWeightRange(float min, float max) {
        minWeight = min;
        maxWeight = max;
    }
}
