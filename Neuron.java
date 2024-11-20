public class Neuron {
    public float[] weights;
    public float bias;
    public float errorTerm;
    public String activationFunction;
    public float output;
    public float biasDerivative;
    public float[] weightDerivatives;
    public Neuron(int nInputs, String activationFunction) {
        weights = new float[nInputs];
        weightDerivatives = new float[nInputs];
        bias = (float) (-1 + 2*Math.random());
        for (int i = 0; i < nInputs; i++) {
            weights[i] = (float) Math.random();
        }
        this.activationFunction = activationFunction;
    }
    public Neuron(float[] weights) {
        bias = 0;
        this.weights = weights;
        this.activationFunction = "identity";
    }
    public float output(float[] inputs) {
        this.output = ActivationFunction(MathUtils.dotProduct(weights, inputs) + bias);
        return this.output;
    }
    private float ActivationFunction(float n) {
        switch (activationFunction) {
            case "sigmoid" -> {
                return MathUtils.sigmoid(n);
            }
            case "identity" -> {
                return n;
            }
            case "relu" -> {
                if (n > 0) {
                    return n;
                } else {
                    return 0;
                }
            }
            default -> throw new IllegalArgumentException("Unknown activation function: " + activationFunction);
        }
    }
}
