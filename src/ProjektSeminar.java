

class test {

    public static void main(String[] args) {
        NeuralNetwork network = new NeuralNetwork();// Initialisierung
        network.setWeights(1);// Gewicht vom OutputLayer setzen
        network.calcOutput();// Output berechnen am besten im Debugger anschauen
    }
}

class NeuralNetwork {
    Layer[] network;
    Layer input;
    Layer output;

    //Beispiel erzeugung für Folie 24 Tabelle letzte Zeile Input: 1,1 Output: 2
    NeuralNetwork() {// Anzahl an Layers
        Layer in = new Layer(2);
        Layer out = new Layer(1);
        input = in;
        output = out;
        network = new Layer[2];
        network[0] = in;
        network[1] = out;
    }
    //setzt die weight attribute auf den Parameter ab dem 1. Index, weil 0 ist ja input
    void setWeights(double value) {
        for (int i = 1; i < network.length; i++) {

            Neuron[] temp = network[i].neurons;

            for (int j = 0; j < temp.length ; j++) {
                temp[j].weigth = value;
            }
        }
    }

    // berechne den output // ist so halbwegs allgemein formuliert in dem beispiel gibt es aber nur ein output Neuron
    void calcOutput() {
        for (int i = 1; i < network.length; i++) {

            Neuron[] temp = network[i].neurons;

            for (int j = 0; j < temp.length; j++) {
                temp[j].value = calcNextValue(network[i-1], temp[j].weigth);
            }
        }
    }
    //Berechne die Summe von einem Array von Neuronen multipliziert mit dem Gewicht
    double calcNextValue(Layer layer, double weight) {
        double sum = 0;
        for (int i = 0; i < layer.neurons.length; i++) {

            sum += layer.neurons[i].value * weight;

        }
        return sum;
    }
}

class Layer{
    Neuron[] neurons;

    //Intialisierung eines Layers
    Layer(int n) {// Anzahl an Neuronen
        neurons = new Neuron[n];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron();
        }
    }
}


class Neuron{
    double value;
    double weigth;
    Neuron() {// Um das Beispiel aus den Folien zu reproduzieren nur temporär
        value = 1;
        weigth = 0;
    }
}
