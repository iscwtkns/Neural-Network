public class Neuron {
    public float[] weights;
    public float bias;
    private String activationFunction;
    public Neuron(int nInputs, String activationFunction) {
        weights = new float[nInputs];
        bias = (float) (-1 + 2*Math.random());
        for (int i = 0; i < nInputs; i++) {
            weights[i] = (float) Math.random();
        }
        this.activationFunction = activationFunction;
    }
    public Neuron(float[] weights) {
        bias = 0;
        this.weights = weights;
    }
    public float output(float[] inputs) {
        return ActivationFunction(MathUtils.dotProduct(weights, inputs) + bias);
    }
    private float ActivationFunction(float n) {
        switch (activationFunction) {
            case "sigmoid":
                return MathUtils.sigmoid(n);
            case "identity":
                return n;
            default:
                throw new IllegalArgumentException("Unknown activation function: " + activationFunction);
        }
    }
}
