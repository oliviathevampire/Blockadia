package team.hdt.blockadia.game_engine.client.rendering.fontRendering;

public class SegoeCalculator implements FontVariablesCalculator {

    private static final float[] subOneAntialiasValues = {0.5f, 0.47f, 0.36f, 0.28f, 0.23f, 0.216f, 0.19f, 0.156f, 0.13f, 0.109f, 0.1f};
    private static final float[] subOneEdgeValues = {0.35f, 0.36f, 0.38f, 0.42f, 0.43f, 0.434f, 0.44f, 0.445f, 0.457f, 0.459f, 0.46f};


    public float calculateAntialiasValue(float size) {
        if (size >= 1) {
            size = ((size - 1) / (1f + size / 4f)) + 1f;
            return (0.1f / size);
        } else {
            return lookupInterpolatedValue(subOneAntialiasValues, size);
        }
    }

    public float calculateEdgeValue(float size) {
        if (size >= 1) {
            return ((1f / 300f) * size + (137f / 300f));
        } else {
            return lookupInterpolatedValue(subOneEdgeValues, size);
        }
    }

    private float lookupInterpolatedValue(float[] data, float size) {
        float value = size / 0.1f;
        int firstIndex = (int) value;
        float progress = value - firstIndex;
        float lowerValue = data[firstIndex];
        float higherValue = data[firstIndex + 1];
        return linearInterpolate(lowerValue, higherValue, progress);
    }

    private float linearInterpolate(float lower, float higher, float progress) {
        float dif = higher - lower;
        return lower + (dif * progress);
    }

}
