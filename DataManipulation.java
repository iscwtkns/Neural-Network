public class DataManipulation {
    private float scaleFactor;
    private float positiveAdjustment;

    public DataManipulation() {}
    public float[][] scaleData(float[] xData, float[] yData) {
        float[] newX = java.util.Arrays.copyOf(xData, xData.length);
        float[] newY = java.util.Arrays.copyOf(yData, yData.length);
        float minimum = newX[0];
        float maximum = newY[0];
        for (float x : newX) {
            if (x < minimum) {
                minimum = x;
            }
            if (x > maximum) {
                maximum = x;
            }
        }
        for (float v : newY) {
            if (v < minimum) {
                minimum = v;
            }
            if (v > maximum) {
                maximum = v;
            }
        }
        if (minimum < 0) {
            positiveAdjustment = -minimum;
            for (int i = 0; i < newX.length; i++) {
                newX[i] += positiveAdjustment;
            }
            for (int i = 0; i < newY.length; i++) {
                newY[i] += positiveAdjustment;
            }
        }
        else {
            positiveAdjustment = 0;
        }
        scaleFactor = maximum + positiveAdjustment;
        for (int i = 0; i < newX.length; i++) {
            newX[i] = newX[i] / scaleFactor;
        }
        for (int i = 0; i < newY.length; i++) {
            newY[i] = newY[i] / scaleFactor;
        }
        return new float[][] {newX, newY};
    }
    public float[][] invertScaling(float[] xData, float[] yData) {
        float[] newX = java.util.Arrays.copyOf(xData,xData.length);
        float[] newY = java.util.Arrays.copyOf(yData,yData.length);
        for (int i = 0; i < newX.length; i++) {
            newX[i] = newX[i] * scaleFactor;
            newX[i] -= positiveAdjustment;
        }
        for (int i = 0; i < newY.length; i++) {
            newY[i] = newY[i] * scaleFactor;
            newY[i] -= positiveAdjustment;
        }
        return new float[][] {newX, newY};
    }
    public float[] scale(float[] data) {
        float[] newData = java.util.Arrays.copyOf(data,data.length);
        for (int i = 0; i <data.length; i++) {
            newData[i] += positiveAdjustment;
            newData[i] = newData[i] / scaleFactor;
        }
        return newData;
    }
    public float[] invertScalingSingular(float[] data) {
        float[] newData = java.util.Arrays.copyOf(data, data.length);
        for (int i = 0; i < data.length; i++) {
            newData[i] = newData[i] * scaleFactor;
            newData[i] -= positiveAdjustment;
        }
        return newData;
    }
}
