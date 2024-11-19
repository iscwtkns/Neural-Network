public class Layer {
    public Neuron[] neurons;
    public Layer(int numNeurons, int inputsPerNeuron, String activationFunction) {
        this.neurons = new Neuron[numNeurons];
        for (int i = 0; i < numNeurons; i++) {
            Neuron neuron = new Neuron(inputsPerNeuron, activationFunction);
            neurons[i] = neuron;
        }
    }
    public Layer(int numNeurons) {
        this.neurons = new Neuron[numNeurons];
        for (int i = 0; i < numNeurons; i++) {
            float[] weights = new float[numNeurons];
            for (int j = 0; j < weights.length; j++) {
                if (i == j ) {
                    weights[j] = 1;
                }
                else {
                    weights[j] = 0;
                }
            }
            Neuron neuron = new Neuron(weights);
            neurons[i] = neuron;
        }

    }
    public float[] getOutputs(float[] inputs) {
        float[] outputs = new float[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].output(inputs);
        }
        return outputs;
    }
}
