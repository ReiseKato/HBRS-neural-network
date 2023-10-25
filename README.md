<h1>2023 WS - Projekt-Seminar: Lernen in natürlichen und künstlichen neuronalen Netzen</h1>

README zu Reises Implemention eines künstlichen neuronalen Netzwerks.


<h3>Neuron</h3>
  - public Neuron(float[] weights, float bias)
        - constructor for the hidden and output Neurons
        - set the first weights and bias of the Neuron here
        
  - public Neuron(float value)
        - constructor for the input Neuron only
        - the input Neuron has no weights nor a bias
        - each inpout Neron has only one value


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

  
  -  void readCsvWeights(String path)
        - method to read the correct weights for each Neuron from a csv file
        - currently not in use, as I found it easier to use the method from a helper Class (NeuralUtil)
