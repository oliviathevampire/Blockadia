package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiSliderBarEventValueChanged;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * a slider bar
 */
public class GuiSliderBar extends Gui {
    /**
     * the objects hold
     */
    private final ArrayList<Object> values;

    /**
     * selected index
     */
    private int selectedIndex;

    private String prefix;
    private String suffix;

    public GuiSliderBar() {
        super();
        this.selectedIndex = 0;
        this.values = new ArrayList<Object>();
        this.prefix = "";
        this.suffix = "";
        this.addListener(ON_PRESS_FOCUS_LISTENER);
    }

    /**
     * VALUES HELPER
     */
    public static final Integer[] intRange(int min, int max) {
        return (intRange(min, max, 1));
    }

    public static final Integer[] intRange(int min, int max, int step) {
        int n = (max - min) / step;
        Integer[] values = new Integer[n + 1];
        int i = 0;
        while (min <= max) {
            values[i++] = new Integer(min);
            min += step;
        }
        return (values);
    }

    public static final Float[] floatRange(float min, float max, float step) {
        return (floatRange(min, max, (int) ((max - min) / step)));
    }

    public static final Float[] floatRange(float min, float max, int n) {
        float step = (max - min) / (float) (n - 1);
        Float[] values = new Float[n];
        for (int i = 0; i < n; i++) {
            values[i] = new Float(min + i * step);
        }
        return (values);
    }

    /**
     * add all values to the list
     */
    public final void addValues(Object... objects) {
        for (Object object : objects) {
            this.addValue(object);
        }
    }

    public final void addValuesArray(Object[] objects) {
        for (Object object : objects) {
            this.addValue(object);
        }
    }

    /**
     * add a value to the list
     */
    public final void addValue(Object object) {
        if (this.values.size() == 0) {
            this.select(0);
        }
        this.values.add(object);
    }

    /**
     * remove all values from the list
     */
    public final void removeValues(Object... objects) {
        for (Object object : objects) {
            this.removeValue(object);
        }
    }

    /**
     * remove a value from the list
     */
    public final void removeValue(Object object) {
        this.values.remove(object);
    }

    /**
     * remove all values from the list
     */
    public final void removeValues() {
        this.values.clear();
    }

    /**
     * sort the values
     */
    public final void sort(Comparator<Object> cmp) {
        this.values.sort(cmp);
    }

    /**
     * get all values from the list
     */
    public final ArrayList<Object> getValues() {
        return (this.values);
    }

    public final Object getValue(int index) {
        return (this.values.get(index));
    }

    /**
     * select the value at given index
     */
    public final Object select(int selectedIndex) {
        if (this.values.size() == 0) {
            return (null);
        }
        if (selectedIndex < 0) {
            selectedIndex = 0;
        } else if (selectedIndex >= this.values.size()) {
            selectedIndex = this.values.size() - 1;
        } else if (selectedIndex != this.selectedIndex) {
            int prevIndex = this.selectedIndex;
            this.selectedIndex = selectedIndex;
            super.stackEvent(
                    new GuiSliderBarEventValueChanged<GuiSliderBar>(this, prevIndex, this.getValue(prevIndex)));
        }
        return (this.getSelectedValue());
    }

    public final Object select(Object object) {
        return (this.select(this.values.indexOf(object)));
    }

    /**
     * select the value at given index
     */
    public final Object select(float percent) {
        return (this.select((int) (this.values.size() * percent)));
    }

    /**
     * @see GuiSliderBarValues#select(float)
     */
    public final Object select(double percent) {
        return (this.select((float) percent));
    }

    /**
     * get the selected value
     */
    public final Object getSelectedValue() {
        if (this.selectedIndex < 0 || this.selectedIndex >= this.values.size()) {
            return (null);
        }
        return (this.values.get(this.selectedIndex));
    }

    public final String getPrefix() {
        return (this.prefix);
    }

    /**
     * the prefix to be displayed
     */
    public final void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public final String getSuffix() {
        return (this.suffix);
    }

    /**
     * the suffix to be displayed
     */
    public final void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * get the selected value
     */
    public final int getSelectedIndex() {
        return (this.selectedIndex);
    }

    /**
     * get the percent progression of the selected value
     */
    public final float getPercent() {
        if (this.values.size() == 0) {
            return (0);
        }
        return ((this.selectedIndex + 1) / (float) this.values.size());
    }

    @Override
    protected void onInputUpdate() {
        if (super.isPressed() && super.hasFocus() && super.isEnabled()) {
            this.select(this.getMouseX());
        }
    }
}