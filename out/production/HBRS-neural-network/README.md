<h1>2023 WS - Projekt-Seminar: Lernen in natürlichen und künstlichen neuronalen Netzen</h1>

README to Reises' implementation of a Neural Network.


<h3>Neuron</h3>

  - public Neuron(float[] weights, float bias)
        - constructor for the hidden and output Neurons
        - set the first weights and bias of the Neuron here
        
  - public Neuron(float value)
        - constructor for the input Neuron only
        - the input Neuron has no weights nor a bias
        - each inpout Neron has only one value
    
&nbsp;
&nbsp;

  -  void setWeights(float[] weightManual)
        - use this method to set the weights o the Neuron manually

  -  void setBias(float bias)
        - use this method to set the bias of the Neuron manually

  -  void updateWeights()
        - use this method to update your weights during backpropagation

  -  static float SigmoidFunction(float num)
        - activation function for each Neuron
        - it currently is the only one woring properly (tested in full automated mode)

  -  static float ReLu(float num)
        - ReLu, the activation function, which I am still working on
        - using this function has led to suboptimal results during testing

  -   static void setWeightRange(float min, float max)
        - method to set the weight range for automated processing of the weights from start to finish
  
  
  <h3>Layer</h3>
  
  -  public Layer(int numberOfWeights, int numberOfNeurons)
        - constructor for the automated Layers (only use this, if you want to have your neural Network to be learnable)
        - currently used in both modes as it turned out to be more useful and less complicated  -->  all needed Parameters are now in Neuron Class
  -   public Layer(float input[])
        - constructor for the input Layer as it doesn't need weights
        - number of needed Neurons will be calculated by the length of input array

&nbsp;
&nbsp;
  
  -  void readCsvWeights(String path)
        - method to read the correct weights for each Neuron from a csv file
        - currently not in use, as I found it easier to use the method from a helper Class (NeuralUtil)

<h3>Training Data</h3>
this class is completly optional, but I found it easier to use a dedicated class for just the training data. With this class I simply managed to still have a good overview of the code and be able to manage the training data easily.

  -  public TrainingData(float inputData[], float expectedResult[])
        - constructor for the learnable model of this neural Network

  -  public TrainingData(float inputData[])
        - constructor for the completly manual nueral Network
        - this model doesn't need an expected result as it isn't learnable and won't compare the actual results with the expected ones


<h3>NueralUtil</h3>
Not initiable helper class. 

  -  public static float RandomFloatNum(float min, float max)
        - method to get a random float number
        - helpful for creating a random bias and random weights for the very first initialisation
          
  -  public static float gradientSum(float gradient, int indexCurrentLayer, int indexCurrentNeuron)
        - if this is still here, I completly forgot to remove this from the code, just ignore this method

  -  public static Float[][] readWeightsAndBias(String path)
        - read the weights and bias for the Neurons from a csv file (specify path)
        - the output is a 2d matrix of Floats the use of the objects Float over float is due to BufferedReader returning Strings. 
          To convert these arrays of Strings I mapped them to a Float 2d matrix. This step requires Float.
          
  -  public static int[] getlayerConfig(String path)
        - specify path of the csv file
        - as the layer configuration is the very first line in our provided csv files, I just read the first line and used the important configuration numbers

  -  public static float[] getSpecificWeights(Float[][] weightsAndBias, int[] layerConfig, int neuronNumber, int layerNumber)
        - method for reading the specifc weights and bias for aech Neuron
        - to get the right configuration, you have to hand over the right index of your Neuron of your Neuron in your right Layer
        - the implementation of this method is quite weird as I have build the neural Network without taking the dataset into consideration.
          Next time I should definetly take the layout of the dataset into consideration

  -  public static int getTrainingInputCount(String path)
        - specify the path of the trainig data csv file
        - it just counts how many inputs the Neural Network will get
        - this way I can specify the amount of trainingData objects I need for this dataset
  -  public static float[] getTrainingInputData(String path, int[] layerConfig, int index)
        - get each input data line by line
        - layerConfig is needed to determine what is the input data and what is the expected result dataset
        - only get the input data --> possible by only taking the amount of input Neurons I have read from the layer configuration
        - index is needed to specify which line of dataset I currently am interested in and want to be returned

  -  public static void main(String[] args)
        - had to test each method in a safe environment before using them in the actual neural Network --> saves time and headache

<h3>NeuralNetwork</h3>

  -  public static void getTrainingData(String path)
        - specify the correct path to the trainind data dataset (csv) and it will take care of getting the right dataset for the trainingData objects

  -  public static void getTrainingData()
        - if you want to manually create trainig data, you can still do this inside this method
        - look at the examples which have been commented out for further details

  -  public static void createLayers(int numberOfLayers, int[] numberOfWeights, int[] numberOfNeurons)
        - create the Layers without having to parse the right weights nor bias
        - just parse how many layers, how many weights the Neurons (array) will get and how many Neurons aech Layer will have as an array
        - the input Layer has to be excluded expect for "int numberOfLayers"
        - layout might change over time to make it more efficient (was first idea and hasn't changed since)
        - example: you want a Layer configuration of 2, 8, 12, 20, 6
                - int numberOfLayers: 5
                - int[] numberOfWeights: {2, 8, 12, 20}
                - int[] numberOfNeurons: {8, 12, 20, 6}
  -  public static void createLayers(String path)
        - specify the path to the layer configuration file (csv)
        - everything else will be taken care by the helper methods explained in "NeuralUtil"
  
  -  public static void run(float[] input)
        - parse the input data here to calculate the output result of the Neural Network
        - it iterates through each Layer, inside each Layer, it iterates thorugh each Neuron inside that Layer to get their weights and iterates through each Neuron inside the Layer before to get their values
        - after it's done iterating though the Neruons of the Layer before, the value of the current Neuorn will be calculated by the activation function

  -  public static void backpropagation(TrainingData __trainingData_t, float trainingRate)
        - method to make the Neural Network learnable
        - I followed a step-by-step guide by Matt Mazur (https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/)
        - it did help me quite a lot to make the backpropagation work
        - to be honest I still don't quite understand how it works and have mostly just implemented the functions he used to manually calculate the backprpagation phase
        - especially the gradients are still somewhat weird

  -  public static float gradientSum(int indexCurrentLayer, int indexCurrentNeuron)
        - it sums up all the gradients from the current Layer (parameter) multiplied by the weight of the current Neuron (parameter)
        - method can't be explained without going into further detials of backpropagation
          
  -  public static void updateAllWeights()
        - all the new weights calculated in the backpropagation method have been stored inside an array of the associated Neuron
        - these weights now need to be parsed as the new current weight
        - iterate through each Layer and each Nueron inside that Layer to update their weights
          
  -  public static void train(int countOfTraining, float trainingRate)
        - parse how often it should iterate/use backpropagationfor each training dataset
        - parse how high the training rate (learning rate) should be  -->  too high: fast training, but can also lead to suboptimal conclusions; too low: can get stuck during training and doesn't really update the weights enough (new values are too close to the old values --> like running the same Network over and over without changing anything)
