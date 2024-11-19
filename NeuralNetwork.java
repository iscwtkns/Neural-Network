public class NeuralNetwork {
    public Layer[] layers;

    public NeuralNetwork(int[] neuronsPerLayer, String activationFunction) {
        layers = new Layer[neuronsPerLayer.length];
        for (int i = 0; i < layers.length; i++) {
            if (i == 0) {
                layers[i] = new Layer(neuronsPerLayer[i]);
            } else {
                layers[i] = new Layer(neuronsPerLayer[i], neuronsPerLayer[i - 1], activationFunction);
            }
        }
    }

    public float[] propagateInputs(float[] inputs) {
        float[] outputs = inputs;
        for (int i = 0; i < layers.length; i++) {
            outputs = layers[i].getOutputs(outputs);
        }
        return outputs;
    }

}
