
public class Main {
    public static NeuralNetwork mainNetwork;
    public static void main(String[] args) {
        int[] neuronsPerLayer = {1,3,1};
        String activationFunction = "relu";
        mainNetwork = new NeuralNetwork(neuronsPerLayer, activationFunction);
        float[] trainX = new float[1000];
        float[] trainY = new float[1000];
        for (float i = 0; i < 1000; i++) {
            trainX[(int) i] = i/50;
            trainY[(int) i] = (float) Math.pow(i/500,2);
        }
        float[][] trainingData = new float[][] {trainX, trainY};
        mainNetwork.bulkLearn(trainingData,10);
        Visualisation.visualize(mainNetwork);

    }
}
