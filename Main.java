import javax.xml.crypto.Data;

public class Main {
    public static NeuralNetwork mainNetwork;
    public static void main(String[] args) {
        int[] neuronsPerLayer = {1,3,1};
        String activationFunction = "sigmoid";
        mainNetwork = new NeuralNetwork(neuronsPerLayer, activationFunction);
        float[] input = {(float) 0.5};
        float[] output = mainNetwork.propagateInputs(input);
        System.out.println(output[0]);
        //Visualisation.visualize(mainNetwork);
        float[] trainX = new float[]{-1,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        float[] trainY = new float[]{1,0,1,4,9,16,25,36,49,64,81,100,121,144,169,196,225,256,289,361,400};
        DataManipulation dataTools = new DataManipulation();
        float[][] scaledData = dataTools.scaleData(trainX, trainY);
        System.out.println(scaledData[1][10]);
        float[][] original = dataTools.invertScaling(scaledData[0],scaledData[1]);
        System.out.println(original[0][0]);
    }
}
