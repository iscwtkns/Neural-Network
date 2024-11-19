import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class Visualisation extends Application {
    private static NeuralNetwork network;

    @Override
    public void start(Stage primaryStage) {
        if (network == null) {
            throw new IllegalStateException("Neural Network is not defined");
        }
        // Set up the canvas for drawing
        Canvas canvas = new Canvas(800, 600); // Set canvas size
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Visualize the network
        visualizeNetwork(gc);

        // Wrap the canvas inside a Group (a Parent subclass)
        Group root = new Group(canvas); // You can also use other layout managers like StackPane or VBox

        // Set up the scene with the Group
        Scene scene = new Scene(root, 800, 600); // You can adjust the size of the scene as needed
        primaryStage.setTitle("Neural Network Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void visualizeNetwork(GraphicsContext gc) {
        int layerCount = network.layers.length;
        int radius = 20; // Radius for the neurons' circles
        int layerSpacing = 100; // Horizontal space between layers
        int neuronSpacing = 40; // Vertical space between neurons in the same layer

        // Iterate through layers to draw neurons
        for (int layerIndex = 0; layerIndex < layerCount; layerIndex++) {
            int neuronCount = network.layers[layerIndex].neurons.length;

            // Draw the neurons (as circles)
            for (int neuronIndex = 0; neuronIndex < neuronCount; neuronIndex++) {
                float bias = network.layers[layerIndex].neurons[neuronIndex].bias;

                // Calculate fill ratio based on the bias
                double fillRatio = (bias + 1) / 2; // Convert bias to a value between 0 (no fill) and 1 (full fill)

                // Set fill color based on fill ratio
                gc.setFill(new Color(0, fillRatio, 0, 1)); // Green fill with varying opacity

                // Draw the filled part of the circle (based on bias)
                gc.fillArc(layerIndex * layerSpacing + 50, neuronIndex * neuronSpacing + 50, radius * 2, radius * 2,
                        90, (int)(360 * fillRatio), ArcType.ROUND);

                // Draw the hollow outline of the circle
                gc.setStroke(Color.GREEN);
                gc.setLineWidth(2); // Line width for the outline
                gc.strokeOval(layerIndex * layerSpacing + 50, neuronIndex * neuronSpacing + 50, radius * 2, radius * 2);
            }
        }

        // Draw the connections between neurons in consecutive layers
        for (int layerIndex = 0; layerIndex < layerCount - 1; layerIndex++) {
            int currentLayerNeuronCount = network.layers[layerIndex].neurons.length;
            int nextLayerNeuronCount = network.layers[layerIndex + 1].neurons.length;

            for (int currentNeuronIndex = 0; currentNeuronIndex < currentLayerNeuronCount; currentNeuronIndex++) {
                for (int nextNeuronIndex = 0; nextNeuronIndex < nextLayerNeuronCount; nextNeuronIndex++) {
                    // Get the weight of the connection from the current neuron to the next layer's neuron
                    float weight = network.layers[layerIndex + 1].neurons[nextNeuronIndex].weights[currentNeuronIndex];

                    // Set line opacity based on weight (normalized between 0 and 1)
                    double opacity = Math.abs(weight); // Absolute value of weight for opacity
                    gc.setStroke(new Color(0, 0, 0, opacity)); // Black line with opacity

                    // Draw line from current neuron to next neuron
                    gc.strokeLine(
                            layerIndex * layerSpacing + radius + 50,
                            currentNeuronIndex * neuronSpacing + radius + 50,
                            (layerIndex + 1) * layerSpacing + radius + 50,
                            nextNeuronIndex * neuronSpacing + radius + 50
                    );
                }
            }
        }
    }

    public static void visualize(NeuralNetwork n) {
        network = n;
        launch();  // Launch the JavaFX application
    }
}