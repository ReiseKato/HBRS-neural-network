package Code;

/**
 * Klasse für ein Neuron
 * @author vkowal2
 * @version 1.0
 */
public class Neuron {

    double value;
    double valbeforecomp;
    double bias; // bei einem reg. Neuron = null

    /**
     * erzeugt ein Neuron mit value = null
     */
    public Neuron() {

    }

    /**
     * erzeuge eine Instanz der Klasse Neuron
     * @param value Wert des Neurons
     */
    public Neuron(double value) {
        this.value = value;
    }

    public Neuron(double value, double bias) {
        this.value = value;
        this.bias = bias;
    }

    /**
     * wendet die Funktion auf den value des Neuron an
     * der Name der Funktion wird als String parameter übergeben
     * Valide Funktionseingaben: 1. "binary" -> binäre Schwellenwertfunktion
     *                          2. "sigmoid" -> logistische Fkt
     *                          3. "ReLu" -> gleichgerichtete Linearität
     *                          4. "tanh" -> tangenshyperbolicus
     *                          5. "" -> id
     * @param function Aktivierungsfunktion
     */
    public void compute(String function) {
        this.valbeforecomp = value;
        switch (function) {
            case "binary" -> binaryThresholdFunc();
            case "sigmoid" -> sigmoidFunc();
            case "ReLu" -> reLuFunc();
            case "tanh" -> tanHyperbolicusFunc();
        }
    }
    //binäre SchwellenwertFunktion: Keyword: binary
    public void binaryThresholdFunc() {
        if (value >= 1.5) {
            value = 1.0;
        } else {
            value = 0.0;
        }
    }

    //Tangenshyperbolicus: Keyword: tanh
    public void tanHyperbolicusFunc() {
        this.value = Math.tanh(value);
    }

    public void derivativeTangensHyperbolicus() {
        this.value = 1 / Math.pow((Math.cosh(value)), 2);
    }

    //logistische Funktion: Keyword: sigmoid
    public void sigmoidFunc() {
        this.value = 1 / (1 + Math.exp((-1) * value));
    }
    public void derivativeSigmoid() {
        value *= Math.exp(value) / Math.pow((Math.exp(value) + 1), 2);
    }

    //Gleichgerichtete Linearität Keyword: ReLu
    public void reLuFunc() {
        if (value < 0) {
            this.value = 0.0;
        }
    }

    public void derivitiveReLu() {
        if(value < 0) {
            this.value = 0;
        } else {
            this.value = 1;
        }
    }

    /**
     *
     * @return gibt den Wert eines Neurons zurück
     */
    public double getValue() {
        return value;
    }

    public double getBias() {
        return bias;
    }
}
