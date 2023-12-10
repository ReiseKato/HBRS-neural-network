package Src;

public class TrainingDataReise {
    float inputData[]; // array because the input data will be a Vector (x input leads to y output)
    float expectedResult[]; // array because we have a Vector of expected results

    /** constructor for trainable kNN */
    public TrainingDataReise(float inputData[], float expectedResult[]) {
        this.inputData = inputData;
        this.expectedResult = expectedResult;
    }

    /** constructor for just forward pass */
    public TrainingDataReise(float inputData[]) {
        this.inputData = inputData;
    }
}
