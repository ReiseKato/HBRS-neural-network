package Code;

import java.util.*;

import static Code.TrainingData.inputs;
import static Code.TrainingData.outputs;

/**
 * Klasse NeuralNetwork
 * @author vkowal2s
 * @version 1.0
 */
public class NeuralNetwork {

    Neuron[][] network;//Neuronen
    Double[][][] weights;//Netztopologie

    Neuron[][] biases;
    Neuron[] input;//Referenz auf den input Layer
    public Neuron[] output;//Referenz auf den output Layer
    static int[] layers;
    String[] func;//Funktionen für den jeweiligen Layer

    Double LR =0.5;//10e-8;

   /* public ArrayList<Double> x = new ArrayList<>();
    public ArrayList<Double> lr = new ArrayList<>();

    */

    /**
     * initialisiere die Neuronen, anhand des Arrays layers
     * @param layers beinhaltet die Anzahl der Neuronen pro Layer
     *               Anzahl der Layer wird implizit abgeleitet
     *               Bsp: {1,2,3} => Network mit 3 Layern
     *                               1. Layer: 1 Neuron
     *                               2. Layer: 2 Neuronen usw.
     */
    public void init(int[] layers) {
        //initialisiere die erste Dimension mit der Länge des Arrays
        network = new Neuron[layers.length][];
        for (int i = 0; i < layers.length; i++) {
            //initialisiere die 2. Dim mit einem Neuronen-Array mit der Anzahl des i-ten Elements von layers
            network[i] = new Neuron[layers[i]];
            //initialisiere eine Ebene mit leeren Neuronen
            for (int j = 0; j < network[i].length; j++) {
                network[i][j] = new Neuron();
            }
        }

        input = network[0];
        output = network[network.length-1];
        //speichere das layout in der Klassen variable
        NeuralNetwork.layers = layers;

    }

    /**
     * initialisiere die layers mithilfe eines Strings[][] bzw mithilfe der eingelesenen csv
     * @param file Text welche die Layer Konfiguration enthält
     */
    public void init(String[][] file) {
        String[] line = file[0];//instanz auf die erste Zeile
        int[] layout = new int[file[0].length-1];

        if(line[0].equals("layers")) { // schaue, ob das Stichwort layers an erster Stelle der Zeile steht
            for (int i = 0; i < layout.length; i++) {
                //lese die restichen Werte der ersten Zeile ein und wandle diese in Integer um
                try {
                    layout[i] = Integer.parseInt(line[i+1]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        layers = layout; //setze die ermittelte Konfiguration auf die Klassenvariable
        init(layout);// initialisiere die Neuronen
    }

    /**
     * erzeugt die Netztopologie, jedem weight wird ein zufälliger Double-wert zwischen -1, 1 zugewiesen
     */
    public void initWeights() {
        Random rand = new Random();//Klasse für zufällige Zahl
        int dim = network.length-1;

        //initialisiere die Dimension
        //1. Dim = Netztiefe -1
        weights = new Double[dim][][];
        for (int i = 0; i < dim; i++) {
            //2./3. Dim: Länge des iten Layers und Länge des i+1ten Layers
            weights[i] = new Double[network[i].length][network[i+1].length];
        }
        //initialisiere die weights mit random werten zwischen -1, 1
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {
                    weights[i][j][k] = -1 + (2 * rand.nextDouble());
                }
            }
        }
    }

    /**
     * initialisierung der Weights and Biases, anhand der Werte, die in der CSV-Datei stehen
     * CSV-Datei muss vorher mit NeuralNetworkUtil.readCSV() eingelesen werden und als Parameter übergeben werden
     * @param file eingelesene CSV-Datei mit weight Konfiguration
     */
    public void initWeightsBiases(String[][] file) {
        //erste Dimension ist jeweils die Netztiefe-1
        weights = new Double[network.length-1][][];
        biases = new Neuron[network.length-1][];
        //lese die weight-Konfiguration in eine verkettete Liste ein, schneide die erste Zeile raus
        //wegen der Layer Konfiguration
        LinkedList<String[]> document = new LinkedList<>(Arrays.asList(file).subList(1, file.length));

        //1. Dim: weight-Ebene
        //2. Dim: von Neuron x
        //3. Dim: zu Neuron y

        for (int i = 0; i < weights.length; i++) { // i entspricht der weight-Ebene

            int dimV = network[0].length; // Dimension der Domain
            int dimW = network[i+1].length;// Dimension der Codomain
            weights[i] = new Double[dimV][dimW];// weise der i-ten Weight-Ebene die Dimensionen zu
            for (int j = 0; j < dimV; j++) { //j entspricht dem akt. Index der Domain
                //wandel die akt. Zeile in ein Double[] um und weise sie zur i-ten Ebene mit dem j-ten Element der Domain
                weights[i][j] = NeuralNetworkUtil.stringArrayToDouble(document.remove());
            }
            String[] temp = document.remove();//speichere die Zeile mit den biases
            biases[i] = new Neuron[temp.length];//erzeuge im Array für die biases an der i-ten stelle ein neuess Neuron[] mit der länge der biases
            biases[i] = NeuralNetworkUtil.stringToBiasNeuron(temp);//wandle den String in ein Neuron[] und weise zu
            if (!document.isEmpty()) {// falls noch nicht die letzte Zeile
                document.remove();//leerzeile entfernen
            }
        }

    }


    /**
     * berechnet für einen input vektor den passenden output vektor mithilfe von Matrix Vektor Multiplikation
     * @param dataset enthält den Input Datensatz, idealerweise: Dim(dataset)=Dim(network[0])
     */
    public void compute(Double[] dataset) {
        Neuron[] v; // Vektor
        Double[][] wMatrix; // Matrix

        //setze den input des networks auf den übergebenen datensatz
        for (int i = 0; i < input.length; i++) {
            input[i].value = dataset[i];
        }

        // gehe das Netzwerk Vektor für Vektor durch
        for (int i = 0; i < network.length-1; i++) {
            v = network[i]; // i-ter Vektor
            wMatrix = NeuralNetworkUtil.transposeMatrix(weights[i]);// weight Matrix der iten Ebene
            // berechne das matrix vektorprodukt aus wMatrix * v
            v = NeuralNetworkUtil.matrixVectorMultiplication(wMatrix, v);
            //addiere das Ergebnis mit dem BiasVektor der akt. Ebene
            for (int j = 0; j < v.length; j++) {
                v[j].value += biases[i][j].bias;
            }
            network[i+1] = v;
            //führe für das Ergebnis aus der M*V, die Aktivierungsfunktion aus für alle Neuronen aus
            for (int j = 0; j < network[i+1].length; j++) {
                network[i+1][j].compute(func[i+1]);
            }
        }
        //output layer aktualisieren
        output = network[network.length-1];
       // this.saves= save.toArray(new Neuron[0][]);
    }

    public Double determineLossFunction(Double[] expectedoutput) {
        double sum = 0;
        for (int i = 0; i < expectedoutput.length; i++) {
            sum += Math.pow(output[i].value - expectedoutput[i], 2.0);
        }
        return sum / expectedoutput.length;
    }

    public void train() {
        int higher = 0;
        int lower = 0;
       // ArrayList<Double> totalErrors = new ArrayList<>();
   //     ArrayList<Double> error = new ArrayList<>();
        System.out.println("LR star: " +LR);
        int iterations = 100000;
        for (int i = 0; i < iterations; i++) {
            //double totalerror = 0;
            for (int j = 0; j < inputs.size(); j++) {
                Double[] input = inputs.get(j);
                Double[] output = outputs.get(j);
                //forward propagation
                compute(input);
                //calculate the loss
                Double loss = determineLossFunction(output);
             //   System.out.println("error " + loss);
               // totalerror += loss;
              /*  if(i == 0) {
                    error.add(j, loss);
                }
                if((loss < error.get(j))) {
                    LR *= 1.1;
                    higher++;
                } else if (loss > error.get(j)) {
                    LR *= 0.5;
                    lower++;
                }
                error.set(j, loss);

               */

                backpropagation(output);
            }
           /* if(i == 0|| i == iterations-1) {
                totalErrors.add(totalerror);
            }


            */


        }
    }

    public void backpropagation(Double[] expectedoutput) {
        //backpropagation from output to hidden
            //calculate the delta for each Neuron of the output layer
            Double[][] delta_out = new Double[1][];
            delta_out[0] = calcDeltaOuputError(expectedoutput);
            //nehme den Output Vektor vom ersten HiddenLayer (von Rechts aus)
            //wandle den Vektor in eine Matrix um -> transponieren
            Neuron[] h_output = network[network.length-2];
            // berechne Vektor*Matrix von h_output * delta_out
           Double[][] newWeights = NeuralNetworkUtil.vectorMatrixMultiplication(h_output, delta_out);
           //multipliziere die neuen Weights mit der Lernrate
            NeuralNetworkUtil.skalarMatrixMultiplikation(LR, newWeights);
            //setzte die neuen Weights ein für die letzte ebene
            weights[weights.length-1] = NeuralNetworkUtil.addMatrices(weights[weights.length-1], newWeights);
            //passe biases des hidden layers an
            Neuron[] new_b = NeuralNetworkUtil.doubleToNeuronBias(delta_out[0]);
            for (int i = 0; i < new_b.length; i++) {
                new_b[i].bias *= (-1) * LR;
                biases[biases.length-1][i].bias += new_b[i].bias;
            }
        //backpropagaiton form hidden to input
            Neuron[] delta_Output_Vector = NeuralNetworkUtil.doubleToNeuron(delta_out[0]);
        for (int i = network.length-2; i > 0 ; i--) {
            Neuron[] current_o = network[i];
            for (int j = 0; j < current_o.length; j++) {
                current_o[i].computeDerivative(func[i]);
            }
            //berechne delta werte des hiddenlayers
            Neuron[] delta_h = NeuralNetworkUtil.matrixVectorMultiplication(weights[i], delta_Output_Vector);
            Neuron[] o_next = network[i-1];
            delta_h = NeuralNetworkUtil.vektorMultiplikation(delta_h, current_o);
            Double[][] new_w_h = new Double[1][];
            double[] temp = NeuralNetworkUtil.neuronToDouble(o_next);
            Double[] boxedArray = Arrays.stream(temp).boxed().toArray(Double[]::new);
            new_w_h[0] = boxedArray;
            new_w_h = NeuralNetworkUtil.vectorMatrixMultiplication(delta_h, new_w_h);
            NeuralNetworkUtil.skalarMatrixMultiplikation((-1) * LR, new_w_h);
            weights[i-1] = NeuralNetworkUtil.addMatrices(weights[i-1],new_w_h);
            //biases berechnen
            Neuron[] b_v = new Neuron[delta_h.length];
            for (int j = 0; j < b_v.length; j++) {
                b_v[j] = new Neuron(1, delta_h[j].value);
                b_v[j].bias *= (-1) * LR;
                biases[i-1][j].bias += b_v[j].bias;
            }
          //  Neuron[] b_v = NeuralNetworkUtil.skalarVektorMultiplikation((-1) * LR, delta_h);
            //biases[i-1] = NeuralNetworkUtil.addVectors(biases[i-1], b_v);

        }
    }


    public Double[] calcDeltaOuputError(Double[] expectedoutput) {
        Double[] delta = new Double[expectedoutput.length];
        for (int i = 0; i < delta.length; i++) {
            delta[i] = output[i].value - expectedoutput[i];
        }
        return delta;
    }

  /*  public double[][][] backwardparse(Neuron[] expectedoutput) {
        Double[][] dellMatrix;
        //für die Augabeschicht:
        double[] delK = calcDeltaOuputError(expectedoutput);
        double[][][] delWeights = new double[weights.length][][];

        for (int i = 0; i < output.length; i++) {
            output[i].delta = delK[i];
            output[i].computeDerivative("sigmoid");
        }
        
        // für die hidden layers:
        for (int i = network.length-2; i >= 1; i--) {

            for (int j = 0; j < network[i].length; j++) {
                Neuron temp = network[i][j];
                for (int k = 0; k < network[i+1].length; k++) {
                    temp.delta += (delK[k] * weights[i][j][k]);
                }
                temp.computeDerivative(func[i]);
            }
        }

       for (int l = weights.length-1; l >= 0; l--) {
           delWeights[l] = new double[weights[l].length][];
            for (int i = 0; i < weights[l].length; i++) {
                Neuron[] out = network[l];
                Neuron[] delj = network[l+1];
                delWeights[l][i] = new double[weights[l][i].length];
                for (int j = 0; j < weights[l][i].length ; j++) {
                    delWeights[l][i][j] = LR * out[i].value * delj[j].value;
                }
            }
        }
       return delWeights;

    }

   */

    /**
     * setze ein String[] functions auf die func des NN
     * Struktur von functions:
     *      Jeder Index steht für die Aktivierungsfunktion des gesamten i-ten Layers
     *      pro Layer eine funktion => könnte man erweitern für jedes Neuron eine andere
     *      Bsp: ["","sigmoid", "ReLu"]
     *              i=0: die Aktivierungsfunktion ist die id rel
     *              i=1: die         "              "  "   Sigmoid Funktion
     *              i=2: die         "              "  "  Gleichgerichtete Linearität
     *
     * @param functions Array mit jeweiligen Aktivierungsfunktionen
     */
    public void setFunc(String[] functions) {
        this.func = functions;
    }

    /**
     *
     * @return gibt die biases des NN zurück
     */
    public Neuron[][] getBiases() {
        return biases;
    }

    /**
     *
     * @return gibt die weights des NN zurück
     */
    public Double[][][] getWeights() {
        return weights;
    }
}
