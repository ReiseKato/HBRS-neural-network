package Code;

/**
 * Klasse für ein Neuron
 * @author vkowal2
 * @version 1.0
 */
public class Neuron {

    double value;

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

        if(function.equals("binary")) {
            binaryThresholdFunc();
        } else if (function.equals("sigmoid")) {
            sigmoidFunc();
        } else if (function.equals("ReLu")) {
            reLuFunc();
        } else if (function.equals("tanh")) {
            tanHyperbolicusFunc();
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

    //logistische Funktion: Keyword: sigmoid
    public void sigmoidFunc() {
        this.value = 1 / (1 + Math.exp((-1) * value));
    }

    //Gleichgerichtete Linearität Keyword: ReLu
    public void reLuFunc() {
        if (value < 0) {
            this.value = 0.0;
        }
    }

    /**
     *
     * @return gibt den Wert eines Neurons zurück
     */
    public double getValue() {
        return value;
    }
}