package team.hdt.blockadia.game_engine.common.world.dayNightCycle;

import team.hdt.blockadia.game_engine.util.toolbox.Colour;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ColourCycle {

    private final Colour nightColour;
    private final Colour dayColour;
    private final Colour dawnColour;
    private final Colour duskColour;

    private final float midnightEnd;
    private final float middayStart;
    private final float middayEnd;

    private final Map<Float, Colour> colours;

    public ColourCycle(Colour nightColour, Colour dayColour, Colour dawnColour, Colour duskColour, float midnightEnd,
                       float middayStart, float middayEnd) {
        this.nightColour = nightColour;
        this.dayColour = dayColour;
        this.dawnColour = dawnColour;
        this.duskColour = duskColour;
        this.midnightEnd = midnightEnd / 24f;
        this.middayStart = middayStart / 24f;
        this.middayEnd = middayEnd / 24f;
        this.colours = create();
    }

    public Colour getColour(float time) {
        Entry<Float, Colour> prev = null;
        Entry<Float, Colour> next = null;
        Iterator<Entry<Float, Colour>> frames = colours.entrySet().iterator();
        while (frames.hasNext()) {
            Entry<Float, Colour> frame = frames.next();
            if (frame.getKey() > time) {
                next = frame;
                break;
            }
            prev = frame;
        }
        float blend = (time - prev.getKey()) / (next.getKey() - prev.getKey());
        return Colour.interpolateColours(prev.getValue(), next.getValue(), blend, null);
    }

    private Map<Float, Colour> create() {
        Map<Float, Colour> map = new LinkedHashMap<Float, Colour>();
        map.put(0f, nightColour);
        map.put(midnightEnd, nightColour);
        map.put(midnightEnd + (middayStart - midnightEnd) * 0.5f, dawnColour);
        map.put(middayStart, dayColour);
        map.put(middayEnd, dayColour);
        map.put(middayEnd + (1 - middayEnd) * 0.5f, duskColour);
        map.put(1f, nightColour);
        return map;
    }

}
