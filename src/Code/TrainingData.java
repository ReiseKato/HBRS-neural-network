package Code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasse TrainingData
 * @author vkowal2s
 * @version 1.0
 */
public class TrainingData {

    String[][] file; // layer+weight Konfiguration
    String[][] traindata;// Trainingssätze: input   (expected)output
    static List<Neuron[]> inputs;
    static List<Neuron[]> outputs;
    public TrainingData() {

    }

    /**
     * Erzeuge ein Objekt der Klasse TrainingData
     * @param doc Weight Konfiguration
     */
    public TrainingData(String[][] doc) {
        this.file = doc;
    }

    /**
     * Erzeuge ein Objekt der Klasse TrainingData
     * @param doc Weight Konfiguration
     * @param traindata Input/Output sets
     */
    public TrainingData(String[][] doc, String[][] traindata) {
        new TrainingData(doc);
        this.traindata = traindata;

    }

    /**
     * Methode um die Inputs und Outputs in eine Liste zu schreiben
     * ermöglicht schnellen Zugriff und Erweiterung
     */
    public void initInputsOutputs() {
        inputs = new ArrayList<>();// Zwei Listen jew. für input und output
        outputs = new ArrayList<>();

        int numofinputs = NeuralNetwork.layers[0];
        int numofoutputs = NeuralNetwork.layers[NeuralNetwork.layers.length-1];

        for (String[] traindatum : traindata) {
            // gehe jede Zeile durch
            // zwei temp String variablen um pro zeile in und output einzulesen
            String[] a = new String[numofinputs];
            String[] b = new String[numofoutputs];
            //Kopiere input und output jew. in String[] entsprechend der Länge der in - und outputs
            System.arraycopy(traindatum, 0, a, 0, numofinputs);
            System.arraycopy(traindatum, numofinputs, b, 0, numofoutputs);
            // füge die eingelesen Werte in die entsprechende Liste hinzu
            inputs.add(NeuralNetworkUtil.stringToNeuron(a));
            outputs.add(NeuralNetworkUtil.stringToNeuron(b));

        }
    }

    /**
     * Methode um einen neuen Datensatz in die Liste aufzunehmen
     * @param in Input Vektor
     * @param out Output Vektor
     */
    public void addInputOutput(Double[] in, Double[] out) {
        //erzeuge ein Neuron[] aus den in and out Double[]
        Neuron[] temp1 = new Neuron[in.length];
        Neuron[] temp2 = new Neuron[out.length];
        for (int i = 0; i < in.length; i++) {
            temp1[i] = new Neuron(in[i]);
        }
        for (int i = 0; i < out.length; i++) {
            temp2[i] = new Neuron(out[i]);
        }
        //füge sie in die Liste hinzu
        inputs.add(temp1);
        outputs.add(temp2);
    }

    /**
     * Methode um die Trainingssätze zu aktualisieren im String[][]
     */
    public void updateTrainData() {
        String[][] temp = new String[inputs.size()][];
        //neue String[][] anlegen mit der Anzahl an akt Datensätzen in der Liste
        //für jede Zeile
        for (int i = 0; i < temp.length; i++) {
            Neuron[] input = inputs.get(i);// i-ter input
            Neuron[] output = outputs.get(i);// passender i-ter output
            String[] st = NeuralNetworkUtil.neuronToString(output);
            // wandel den Input Neuron[] in ein String[] um
            st[0] = "\t\t" + st[0];
            temp[i] = NeuralNetworkUtil.concatArrays(NeuralNetworkUtil.neuronToString(input),st);
            // füge zum letzten El zwei taps hinzu
            // wandel den Output Neuron[] in ein String[]
            // füge beide Strings[] zusammen und weise sie der akt Zeile zu

        }
        this.traindata = temp;
    }

    /**
     * Methode um die Weights und Layer Konfiguration im Strin[][] zu aktualisieren
     * @param weights akt weight Konfiguration
     * @param biases akt bias Konfiguration
     */
    public void updateFile(Double[][][] weights, Neuron[][] biases) {
        ArrayList<String[]> text = new ArrayList<>();
        // erzeug temporäres dyn Array
        String[] line = new String[NeuralNetwork.layers.length + 1];
        // Zeile für die layer Konfiguration
        // jew. eine Queue für Weights and Biases
        // damit nacheinander abgearbeitet werden kann
        LinkedList<String[][]> wls = new LinkedList<>();
        LinkedList<String[]> bls = new LinkedList<>();


        //erstelle alle weight Matrizen und füge sie zur entsprechenden Liste hinzu
        for (Double[][] weight : weights) {
            //convert to string
            String[][] temp = NeuralNetworkUtil.doubleMatrixToStringArray(weight);
            wls.add(temp);
        }
        // erstelle alle bias Vektoren und füge sie zur Liste hinzu
        for (Neuron[] biase : biases) {
            //convert to string
            String[] temp = NeuralNetworkUtil.neuronToString(biase);
            bls.add(temp);
        }

        // generate Zeile mit Layer Konfiguration
        line[0] = "layers";
        for (int i = 1; i < line.length; i++) {
            line[i] = Integer.toString(NeuralNetwork.layers[i-1]);
        }
        text.add(line);

        //Solange eine der beiden liste El besitzt
        while (!wls.isEmpty() || !bls.isEmpty()) {
            // entferne Weight Ebene
            String[][] temp = wls.remove();
            Collections.addAll(text, temp);
            // füge sie zum text hinzu
            // entferne bias Vektor
            String[] temp2 = bls.remove();
            text.add(temp2);
            // schreibe sie in die nächste Zeile
            text.add(new String[]{});
            //LeereZeile
        }
        //entferne die letzte Zeile des Textes
        if(text.get(text.size()-1).length == 0) {
            text.remove(text.size()-1);
        }
        // wandle das dyn. Array in ein statisches Array um
        this.file = text.toArray(new String[0][]);
    }

    /**
     *
     * @return gibt die Weight-Layer Konfiguration in Form String[][] zurück
     */
    public String[][] getFile() {
        return file;
    }

    /**
     *
     * @return gibt die In/output Datensätze in Form String[][] zurück
     */
    public String[][] getTraindata() {
        return traindata;
    }

    /**
     * Weise Trainingdata eine neue Layer/weight Konfiguration in Form von String[][] zu
     * @param file Weight Layers config
     */
    public void setFile(String[][] file) {
        this.file = file;
    }

    /**
     * Weise ein String[][] mit in und output Datensätzen zu
     * @param traindata In and Output values
     */
    public void setTraindata(String[][] traindata) {
        this.traindata = traindata;
    }
}
