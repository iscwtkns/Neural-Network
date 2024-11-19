public class DataManipulation {
    private float scaleFactor;
    private float positiveAdjustment;

    public DataManipulation() {}
    public float[][] scaleData(float[] xData, float[] yData) {
        float minimum = xData[0];
        float maximum = xData[0];
        for (int i = 0; i < xData.length; i++) {
            if (xData[i] < minimum) {
                minimum = xData[i];
            }
            if (xData[i] > maximum) {
                maximum = xData[i];
            }
        }
        for (int i = 0; i < yData.length; i++) {
            if (yData[i] < minimum) {
                minimum = yData[i];
            }
            if (yData[i] > maximum) {
                maximum = yData[i];
            }
        }
        if (minimum < 0) {
            positiveAdjustment = -minimum;
            for (int i = 0; i < xData.length; i++) {
                xData[i] += positiveAdjustment;
            }
            for (int i = 0; i < yData.length; i++) {
                yData[i] += positiveAdjustment;
            }
        }
        else {
            positiveAdjustment = 0;
        }
        scaleFactor = maximum + positiveAdjustment;
        for (int i = 0; i < xData.length; i++) {
            xData[i] = xData[i] / scaleFactor;
        }
        for (int i = 0; i < yData.length; i++) {
            yData[i] = yData[i] / scaleFactor;
        }
        return new float[][] {xData,yData};
    }
    public float[][] invertScaling(float[] xData, float[] yData) {
        for (int i = 0; i < xData.length; i++) {
            xData[i] = xData[i] * scaleFactor;
            xData[i] -= positiveAdjustment;
        }
        for (int i = 0; i < yData.length; i++) {
            yData[i] = yData[i] * scaleFactor;
            yData[i] -= positiveAdjustment;
        }
        return new float[][] {xData, yData};
    }
}
