package Code;

public class Neuron {

    double value;

    /**
     * erzeugt ein Code.Neuron mit der identitäts aktivierungsfunktion
     */
    public Neuron() {

    }

    public Neuron(double value) {
        this.value = value;
    }

    public void compute(String function) {

        if(function.equals("binary")) {
            binaryThresholdFunc();
        } else if (function.equals("sigmoid")) {
            sigmoidFunc();
        } else if (function.equals("ReLu")) {
            reLuFunc();
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

    //Tangenshyperbolicus
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

    public double getValue() {
        return value;
    }
}
