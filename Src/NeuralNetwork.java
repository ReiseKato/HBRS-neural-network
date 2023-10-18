package Src;

public class NeuralNetwork {
    static Layer[] layers_t;
    static TrainingData[] trainingData_t;



    public static void main(String[] args) {
        int numberOfLayers = 3;
        int[] numberOfWeights = {2, 6}; // first after input one gets two weights
        int[] numberOfNeurons = {6, 1}; // first after input hast 6 neurons
        Neuron.setWeightRange(-1, 1);

        getTrainingData();

        createLayers(numberOfLayers, numberOfWeights, numberOfNeurons);

        train(1000000, 0.5f);
        for(int i = 0; i < trainingData_t.length; i++) {
            run(trainingData_t[i].inputData);
            System.out.println(layers_t[layers_t.length - 1].neurons[0].value);
        }
    }



    public static void getTrainingData() {
        // code to generate Training Data
        float[] input1 = new float[] {0, 0}; // Expect 0
        float[] input2 = new float[] {0, 1}; // Expect 1
        float[] input3 = new float[] {1, 0}; // Expect 1
        float[] input4 = new float[] {1, 1}; // Expect 1

        float[] expectedOutput1 = new float[] {0};
        float[] expectedOutput2 = new float[] {1};
        float[] expectedOutput3 = new float[] {1};
        float[] expectedOutput4 = new float[] {1};

        // My changes (using an array for the data sets)
        trainingData_t = new TrainingData[4];
        trainingData_t[0] = new TrainingData(input1, expectedOutput1);
        trainingData_t[1] = new TrainingData(input2, expectedOutput2);
        trainingData_t[2] = new TrainingData(input3, expectedOutput3);
        trainingData_t[3] = new TrainingData(input4, expectedOutput4);
    }

    public static void createLayers(int numberOfLayers, int[] numberOfWeights, int[] numberOfNeurons) { // parse input data, how many layers, how many weights, how many neurons in each layer
        layers_t = new Layer[numberOfLayers];

        // layers_t[0] = new Layer(input); // very first Layer aka input Layer (commented because makes no sense to parse data here. just create Layers in function)
        for(int i = 1; i < numberOfLayers; i++) { //create all hidden Layers and output Layer
            layers_t[i] = new Layer(numberOfWeights[i - 1], numberOfNeurons[i - 1]);
        }
    }

    public static void run(float[] input) {
        layers_t[0] = new Layer(input);
        float sum;

        for(int i = 1; i < layers_t.length; i++) { // i: index for layer (i = current layer, i - 1 = layer to get values from)
            for(int j = 0; j < layers_t[i].neurons.length; j++) {
                sum = 0;
                for(int k = 0; k < layers_t[i - 1].neurons.length; k++) {
                    sum += layers_t[i].neurons[j].weights[k]*layers_t[i - 1].neurons[k].value;
                }
                sum += layers_t[i].neurons[j].bias;
                layers_t[i].neurons[j].value = Neuron.ReLu(sum);
            }
        }
    }

    /** training rate:  optional. can be used to determine how fast the model should train.
     *                  too low -> can get stuck | too high ->  can cause the model to converge too quickly to a suboptimal solution */
    public static void backpropagation(TrainingData __trainingData_t, float trainingRate) {
        float out = 0.0f;
        float target = 0.0f;
        float errorTotal = 0.0f;
        float deltaOfValue = 0.0f;
        float deriviate = 0.0f;
        float deltaRule = 0.0f; //should be renamed
        float oldWeight;

        // loop for optimizing the output Layer (-> already in output Layer (note for Reise))
        for(int i = 0; i < layers_t[layers_t.length - 1].neurons.length; i++) { // i: represents Neuron in output Layer. loop while i < number of neurons in output Layer
            out = layers_t[layers_t.length - 1].neurons[i].value;
            target = __trainingData_t.expectedResult[i];
            errorTotal += 0.5*(out -  target)*(out* - target);
            deltaOfValue = out - target; // output - target (M. Mazur)
            deriviate = out*(1 - out);

            for(int j = 0; j < layers_t[layers_t.length - 1].neurons[i].weights.length; j++) { // j: represents Neurons of ex. 1 Layer before output Layer. iterate as often as values each Neuron in output Layer gets
                deltaRule = deltaOfValue*deriviate*layers_t[layers_t.length - 2].neurons[j].value;
                oldWeight = layers_t[layers_t.length - 1].neurons[i].weights[j];
                layers_t[layers_t.length - 1].neurons[i].newWeights[j] = oldWeight - trainingRate*deltaRule;
                // layers_t[layers_t.length - 1].neurons[i].weights[j] = oldWeight - trainingRate*deltaRule;
            }
        }

        float gradientSum;
        float delta;
        // loop for optimizing the hidden Layers -> has to go backwards
        for(int i = layers_t.length - 2; i > 0; i--) { // i; indexing for Layers
            //out = layers_t[i].neurons[]
            for(int j = 0; j < layers_t[i].neurons.length; j++) { // indexing for Neurons
                gradientSum = gradientSum(i + 1, j); // i+1: needs the outputs of the Layer before
                out = layers_t[i].neurons[j].value;
                delta = gradientSum*out*(1 - out); // delta of Error to Weight
                layers_t[i].neurons[j].gradient = delta; // just assuming it because I need to update the gradient and that's the best value I can find
                // loop for updating all weights for each Neuron
                for(int k = 0; k < layers_t[i].neurons[j].weights.length; k++) { // k: indexing for weights
                    layers_t[i].neurons[j].newWeights[k] = layers_t[i].neurons[j].weights[k] - trainingRate*delta;
                    // layers_t[i].neurons[j].weights[k] = layers_t[i].neurons[j].weights[k] - trainingRate*delta;
                }
            }
        }

        updateAllWeights();
    }

    public static float gradientSum(int indexCurrentLayer, int indexCurrentNeuron) { // maybe float gradient has to be given as Parameter -> test
        float sum = 0.0f;
        Layer currentLayer = layers_t[indexCurrentLayer];
        for(int i = 0; i < currentLayer.neurons.length; i++) {
            Neuron currentNeuron = currentLayer.neurons[i];
            sum += currentNeuron.gradient*currentNeuron.weights[indexCurrentNeuron]; // tried a bunch of different values -> weights seems to work so far
        }
        return sum;
    }

    public static void updateAllWeights() {
        for(int i = 0; i < layers_t.length; i++) { // i: indexing for Layers
            for(int j = 0; j < layers_t[i].neurons.length; j++) {
                layers_t[i].neurons[j].updateWeights();
            }
        }
    }

    public static void train(int countOfTraining, float trainingRate) {
        for(int i = 0; i < countOfTraining; i++) {
            for(int j = 0; j < trainingData_t.length; j++) {
                run(trainingData_t[j].inputData);
                backpropagation(trainingData_t[j], trainingRate);
            }
        }
    }
}
