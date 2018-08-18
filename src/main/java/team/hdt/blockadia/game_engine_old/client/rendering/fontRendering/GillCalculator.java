package team.hdt.blockadia.game_engine_old.client.rendering.fontRendering;

public class GillCalculator implements FontVariablesCalculator {

    private static final float[] subOneAntialiasValues = {0.5f, 0.47f, 0.36f, 0.21f, 0.17f, 0.14f, 0.1297f, 0.126f, 0.0894f, 0.102f, 0.098f};
    private static final float[] subOneEdgeValues = {0.35f, 0.36f, 0.38f, 0.42f, 0.43f, 0.444f, 0.448f, 0.443f, 0.465f, 0.451f, 0.452f};

//	private float antialias = 0.102f;
//	private float edge = 0.451f;

//	public void update(){
//		MyKeyboard k = MyKeyboard.getKeyboard();
//		if(k.isKeyDown(Keyboard.KEY_I)){
//			antialias += DisplayManager.getDeltaSeconds() * 0.1f;
//		}else if(k.isKeyDown(Keyboard.KEY_K)){
//			antialias -= DisplayManager.getDeltaSeconds() * 0.1;
//		}
//		if(k.isKeyDown(Keyboard.KEY_U)){
//			edge += DisplayManager.getDeltaSeconds() * 0.02f;
//		}else if(k.isKeyDown(Keyboard.KEY_J)){
//			edge -= DisplayManager.getDeltaSeconds() * 0.02;
//		}
//		System.out.println(antialias + " , " + edge);
//	}

    public float calculateAntialiasValue(float size) {
        //return antialias;
        if (size >= 1) {
            size = ((size - 1) / (1f + size / 4f)) + 1f;
            return (0.1f / size);
        } else {
            return lookupInterpolatedValue(subOneAntialiasValues, size);
        }
    }

    public float calculateEdgeValue(float size) {
        //return edge;
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
