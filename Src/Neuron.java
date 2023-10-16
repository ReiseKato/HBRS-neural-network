package Src;

public class Neuron {
    private double inputNeuron; //
    private double signal; // das net j aus Folie 14
    double weight;


    public Neuron(double input, double signal) {
        this.inputNeuron = input;
        this.signal = signal;
    }

    public double activationFunction(double input) { // function to determine output signal
        double result;
        result = 0;  // hier die Aktivierungsunktion
        return result;
    }

    public void modificationOfWeight(double newWeight) {
        this.weight = newWeight;
    }
}
