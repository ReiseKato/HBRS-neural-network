package Src;


public class LayerReise {
    public NeuronReise neurons[]; // array to keep all the Neurons of a Layer in place

    /** for hidden layer and output layer (automated) */
    public LayerReise(int numberOfWeights, int numberOfNeurons) {
        this.neurons = new NeuronReise[numberOfNeurons];

        for (int i = 0; i < numberOfNeurons; i++) {
            float[] weights = new float[numberOfWeights];
            for (int j = 0; j < numberOfWeights; j++) {
                weights[j] = NeuralUtilReise.RandomFloatNum(NeuronReise.fMinWeight, NeuronReise.fMaxWeight);
            }
            neurons[i] = new NeuronReise(weights, NeuralUtilReise.RandomFloatNum(0, 1)); // get random number for bias between 0 and 1
        }
    }

    /** for hidden layer and output layer (manual) */
    public LayerReise(int numberOfWeights, int numberOfNeurons, int num) {
        this.neurons = new NeuronReise[numberOfNeurons];

        for (int i = 0; i < numberOfNeurons; i++) {
            float[] weights = new float[numberOfWeights];
//            for (int j = 0; j < numberOfWeights; j++) {
//                // weights[j] = NeuralUtil.RandomFloatNum(Neuron.minWeight, Neuron.maxWeight);
//            }
            neurons[i] = new NeuronReise(weights, NeuralUtilReise.RandomFloatNum(0, 1)); // get random number for bias between 0 and 1
        }
    }

    /** for input layer -> input[] is TrainingData.inputData */
    public LayerReise(float input[]) {
        this.neurons = new NeuronReise[input.length];
        for(int i = 0; i < input.length; i++) {
            this.neurons[i] = new NeuronReise(input[i]);
        }
    }


}
