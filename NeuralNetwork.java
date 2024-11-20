import java.util.Arrays;

public class NeuralNetwork {
    public Layer[] layers;
    public float learnStep = (float) 0.0001;
    public String activationFunction;
    public NeuralNetwork(int[] neuronsPerLayer, String activationFunction) {
        this.activationFunction = activationFunction;
        layers = new Layer[neuronsPerLayer.length];
        for (int i = 0; i < layers.length; i++) {
            if (i == 0) {
                layers[i] = new Layer(neuronsPerLayer[i]);
            }
            else if (i == layers.length-1) {
                layers[i] = new Layer(neuronsPerLayer[i], neuronsPerLayer[i-1], "identity");
            }
            else {
                layers[i] = new Layer(neuronsPerLayer[i], neuronsPerLayer[i - 1], activationFunction);
            }
        }
    }
    public float[] propagateInputs(float[] inputs) {
        float[] outputs = java.util.Arrays.copyOf(inputs,inputs.length);
        for (Layer layer : layers) {
            outputs = layer.getOutputs(outputs);
        }
        return outputs;
    }
    public float[][] propagateInputArray(float[][] inputs) {
        float[][] outputs = java.util.Arrays.copyOf(inputs,inputs.length);
        for (int i = 0; i < outputs.length; i++) {
            outputs[i] = propagateInputs(inputs[i]);
        }
        return outputs;
    }
    public float[] propagateInputArray(float[] inputs) {
        float[] outputs = java.util.Arrays.copyOf(inputs,inputs.length);
        for (int i = 0; i < outputs.length; i++) {
            float[] input = new float[] {inputs[i]};
            outputs[i] = propagateInputs(input)[0];
        }
        return outputs;
    }
    public float cost(float[] predictions, float[] trueValues) {
        float cost = 0;
        for (int i = 0; i < predictions.length-1; i++) {
            cost += Math.pow(trueValues[i]-predictions[i],2);
        }
        return cost/predictions.length;
    }
    public void bulkLearn(float[][] trainingData, int loops) {
        for (int i = 0; i < loops; i++) {
            learnFromDataSet(trainingData[0],trainingData[1]);
        }
    }
    public void learnFromDataSet(float[] xTrain, float[] yTrain) {
        DataManipulation dataTools = new DataManipulation();
        float[][] scaledData = dataTools.scaleData(xTrain, yTrain);
        int nSets = (int) xTrain.length/layers[0].neurons.length;
        int inputSetLength = layers[0].neurons.length;
        int outputSetLength = layers[layers.length-1].neurons.length;
        float[][] inputSets = new float[nSets][inputSetLength];
        float[][] outputSets = new float[nSets][outputSetLength];
        for (int i = 0; i < nSets; i ++) {
            inputSets[i] = Arrays.copyOfRange(scaledData[0], i*inputSetLength, (i+1)*inputSetLength);
            outputSets[i] = Arrays.copyOfRange(scaledData[1], i*outputSetLength, (i+1)*outputSetLength);
        }
        for (int i = 0; i < inputSets.length; i++) {
            learnFromDataPoint(inputSets[i],outputSets[i]);
        }
        float[] scaledPredictions = propagateInputArray(scaledData[0]);
        float[] predictions = dataTools.invertScalingSingular(scaledPredictions);
        System.out.println(predictions[8]);
        System.out.println(yTrain[8]);
        float error = MathUtils.MSE(predictions, yTrain);
        System.out.println("MSE:");
        System.out.println(error);

    }
    public void learnFromDataPoint(float[] scaledInput, float[] scaledOutput) {
        /*
        This function learns off a single data point, input array must match size of input neurons,
        output array matches size of output neurons and holds true values
         */
        float[] predictions = propagateInputs(scaledInput);
        defineErrorTerms(predictions, scaledOutput);
        assignDerivatives();
        updateWeightsAndBias();
    }
    public void updateWeightsAndBias() {
        for (int i = 1; i < layers.length; i++) {
            for (int j = 0; j < layers[i].neurons.length; j++) {
                Neuron neuron = layers[i].neurons[j];
                for (int k = 0; k < neuron.weights.length; k++) {
                    neuron.weights[k] = neuron.weights[k] - learnStep*neuron.weightDerivatives[k];
                }
                neuron.bias = neuron.bias - learnStep*neuron.biasDerivative;
            }
        }
    }
    public void assignDerivatives() {
        for (int layerIndex = 1; layerIndex < layers.length; layerIndex++) {
            for (int i = 0; i < layers[layerIndex].neurons.length; i++) {
                Neuron neuron = layers[layerIndex].neurons[i];
                neuron.biasDerivative = neuron.errorTerm;
                for (int j = 0; j < neuron.weights.length; j++) {
                    neuron.weightDerivatives[j] = neuron.errorTerm*layers[layerIndex-1].neurons[j].output;
                }
            }
        }
    }

    public void defineErrorTerms(float[] predictions, float[] actual) {
        defineOutputErrorTerms(predictions, actual);
        int layerIndex = layers.length - 2;
        while (layerIndex > 0) {
            defineErrorTermForLayer(layerIndex);
            layerIndex--;
        }
    }
    public void defineErrorTermForLayer(int layerIndex) {
        Layer definingLayer = layers[layerIndex];
        Layer nextLayer = layers[layerIndex+1];
        for (int i = 0; i < definingLayer.neurons.length; i++) {
            float errorTerm = 0;
            for (int j = 0; j < nextLayer.neurons.length; j++) {
                errorTerm += nextLayer.neurons[j].errorTerm * nextLayer.neurons[j].weights[i];
            }
            definingLayer.neurons[i].errorTerm = errorTerm;
        }
    }
    public void defineOutputErrorTerms(float[] predictions, float[] actual) {
        Layer outputLayer = this.layers[layers.length-1];
        for (int i = 0; i < outputLayer.neurons.length; i++) {
            outputLayer.neurons[i].errorTerm = 2*(predictions[i]-actual[i])/outputLayer.neurons.length;
        }
    }
    public void printWeightsAndBiases() {
        for (int i = 1; i < layers.length; i++) {
            System.out.println("Layer");
            for (int j = 0; j < layers[i].neurons.length; j++) {
                Neuron neuron = layers[i].neurons[j];
                System.out.println("Neuron Bias");
                System.out.println(neuron.bias);
                System.out.println("Neuron Weight");
                for (int k = 0; k < neuron.weights.length; k++) {
                    System.out.println(neuron.weights[k]);
                }
            }
        }
    }

}
