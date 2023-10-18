package Code;

public class Neuron {

    String func;
    double value;

    /**
     * erzeugt ein Code.Neuron mit der identitäts aktivierungsfunktion
     */
    public Neuron(){

    }

    public void compute() {
        func();
    }

    public void func() {
        if(value >= 1.5) {
            value = 1.0;
        } else {
            value = 0;
        }
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
