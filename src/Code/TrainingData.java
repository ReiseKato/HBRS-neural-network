package Code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TrainingData {

    String[][] file;

    String[][] traindata;
    int[] layoutofFile;

    Double[][][] weightcfg;
    Neuron[][] biases;

    List<Neuron[]> inputs;
    List<Neuron[]> outputs;
    public TrainingData() {

    }

    public void setTrain() {
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        int numofinputs = layoutofFile[0];
        int numofoutputs = layoutofFile[layoutofFile.length-1];

        for (int i = 0; i < traindata.length; i++) {
            String[] a = new String[numofinputs];
            String[] b = new String[numofoutputs];

            System.arraycopy(traindata[i], 0, a, 0, numofinputs);
            System.arraycopy(traindata[i], numofinputs, b, 0,numofoutputs);

            inputs.add(NeuralNetworkUtil.stringToNeuron(a));
            outputs.add(NeuralNetworkUtil.stringToNeuron(b));

        }
    }

    public void addInputOutput(Double[] in, Double[] out) {
        Neuron[] temp1 = new Neuron[in.length];
        Neuron[] temp2 = new Neuron[out.length];
        for (int i = 0; i < in.length; i++) {
            temp1[i] = new Neuron(in[i]);
        }
        for (int i = 0; i < out.length; i++) {
            temp2[i] = new Neuron(out[i]);
        }
        inputs.add(temp1);
        outputs.add(temp2);
    }

    public TrainingData(String[][] doc) {
        this.file = doc;

    }

    public void setTrainData(String[][] traindata) {
        this.traindata = traindata;
    }

    public String[][] getTraindata() {
        return traindata;
    }

    public void determineLayout() {
        String[] line = file[0];
        int[] layout = new int[file[0].length-1];
        if(line[0].equals("layers")) {
            for (int i = 0; i < layout.length; i++) {
                try {
                    layout[i] = Integer.parseInt(line[i+1]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        }
        this.layoutofFile = layout;
    }

    public void determineWeightsBiases() {
        weightcfg = new Double[layoutofFile.length-1][][];
        biases = new Neuron[layoutofFile.length-1][];
        int z = 1;
        for (int i = 0; i < weightcfg.length; i++) {
            Double[] line;
            int dimV = layoutofFile[i];
            int dimW = layoutofFile[i+1];
            weightcfg[i] = new Double[dimV][dimW];
            for (int j = 1; j <= dimV; j++) {
                line = NeuralNetworkUtil.stringArrayToDouble(file[z]);
                weightcfg[i][j-1] = line;
                z++;
            }
            biases[i] = new Neuron[file[z].length];
            biases[i] = NeuralNetworkUtil.stringToNeuron(file[z]);
            z += 2;
        }

    }

    public Double[][][] getWeightcfg() {
        return weightcfg;
    }

    public void setWeightcfg(Double[][][] weightcfg) {
        this.weightcfg = weightcfg;
    }

    public void updateTrainData() {
        String[][] temp = new String[inputs.size()][];
        for (int i = 0; i < temp.length; i++) {
            Neuron[] input = inputs.get(i);
            Neuron[] output = outputs.get(i);
            String[] st = NeuralNetworkUtil.neuronToString(output);
            st[0] = "\t\t" + st[0];
            temp[i] = NeuralNetworkUtil.concatArrays(NeuralNetworkUtil.neuronToString(input),st);

        }
        this.traindata = temp;
    }

    public void updateFile() {
        ArrayList<String[]> text = new ArrayList<>();
        String[] line = new String[layoutofFile.length+1];
        LinkedList<String[][]> wls = new LinkedList<>();
        LinkedList<String[]> bls = new LinkedList<>();


        //erstelle alle weight Matrizen
        for (int i = 0; i < weightcfg.length; i++) {
            String[][] temp = NeuralNetworkUtil.doubleMatrixToStringArray(weightcfg[i]);
            wls.add(temp);
        }

        for (int i = 0; i < biases.length; i++) {
            String[] temp = NeuralNetworkUtil.neuronToString(biases[i]);
            bls.add(temp);
        }


        line[0] = "layers";
        for (int i = 1; i < line.length; i++) {
            line[i] = Integer.toString(layoutofFile[i-1]);
        }
        text.add(line);

        while (!wls.isEmpty() || !bls.isEmpty()) {
            String[][] temp = wls.remove();
            for (int i = 0; i < temp.length; i++) {
                text.add(temp[i]);
            }
            String[] temp2 = bls.remove();
            text.add(temp2);

            text.add(new String[]{});

        }
        if(text.get(text.size()-1).length == 0) {
            text.remove(text.size()-1);
        }

        this.file = text.toArray(new String[0][]);
    }

    public String[][] getFile() {
        return file;
    }

    public void setLayout(int[] layout) {
        this.layoutofFile = layout;
    }

    public void setBiases(Neuron[][] biases) {
        this.biases = biases;
    }

    public int[] getLayout() {
        return layoutofFile;
    }

    public Neuron[][] getBiases() {
        return biases;
    }
}
