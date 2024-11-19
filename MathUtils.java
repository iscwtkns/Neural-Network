public class MathUtils {
    public static float dotProduct(float[] a, float[] b) {
        float result = 0;
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vectors must have the same length");
        }
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }
    public static float sigmoid(float a) {
        return (float) (1/(1+Math.exp(-a)));
    }
}