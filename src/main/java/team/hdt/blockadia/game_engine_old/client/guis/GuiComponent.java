package team.hdt.blockadia.game_engine_old.client.guis;

import team.hdt.blockadia.game_engine.client.MainExtras;
import team.hdt.blockadia.game_engine_old.client.rendering.fontRendering.Text;
import team.hdt.blockadia.game_engine_old.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An component of a GUI. Implementations of this range from a whole GUI frame
 * to a single button. All components can have sub-components, and the parent
 * component has the responsibility of keeping the sub-components updated.
 *
 * @author Karl
 */
public abstract class GuiComponent {

    private Vectors2f position = new Vectors2f();
    private Vectors2f scale = new Vectors2f();

    private Vectors2f relativePosition = new Vectors2f();
    private Vectors2f relativeScale = new Vectors2f();
    private GuiComponent parent;

    private boolean visible = true;

    private int[] clippingBounds;

    private List<GuiComponent> childComponents = new ArrayList<GuiComponent>();
    private Map<Text, Vectors3f> componentTexts = new HashMap<Text, Vectors3f>();

    private List<GuiComponent> componentsToRemove = new ArrayList<GuiComponent>();
    private List<GuiComponent> componentsToAdd = new ArrayList<GuiComponent>();

    private List<Text> textsToRemove = new ArrayList<Text>();

    private boolean initialized = false;
    private boolean hasFocus = false;

    private boolean useCenteringX = false;
    private boolean useCenteringY = false;
    private boolean usePreferredAspectFixedX = false;
    private boolean usePreferredAspectFixedY = false;
    private float preferredAspect = 1;

    private boolean specificLevelSet = false;
    private int level = 0;
    private int preferredPixelSize = 1;

    /**
     * Determines whether the component (and all sub-components) should be
     * visible or not. Non-visible components are not updated.
     *
     * @param visible - whether the component should be visible or not.
     */
    public void show(boolean visible) {
        this.visible = visible;
    }

    /**
     * Add a sub-component to this component.
     *
     * @param component - the sub=component.
     * @param relX      - the x position of the top-left corner of the child
     *                  component, in relation to this component (0 is the left edge
     *                  of this component, 1 is the right edge).
     * @param relY      - the y position of the top-left corner of the child
     *                  component, in relation to this component (0 is the top edge of
     *                  this component, 1 is the bottom edge).
     * @param relScaleX - the x scale of the child component, in relation to the x
     *                  scale of this component.
     * @param relScaleY - the y scale of the child component, in relation to the y
     *                  scale of this component.
     */
    public void addComponent(GuiComponent component, float relX, float relY, float relScaleX, float relScaleY) {
        component.relativePosition.set(relX, relY);
        component.relativeScale.set(relScaleX, relScaleY);
        component.parent = this;
        componentsToAdd.add(component);
    }

    public void addComponentX(GuiComponent component, float relX, float relY, float relScaleX) {
        component.usePreferredAspectFixedX = true;
        component.relativePosition.set(relX, relY);
        component.relativeScale.x = relScaleX;
        componentsToAdd.add(component);
        component.parent = this;
    }

    public void addComponentY(GuiComponent component, float relX, float relY, float relScaleY) {
        component.usePreferredAspectFixedY = true;
        component.relativePosition.set(relX, relY);
        component.relativeScale.y = relScaleY;
        componentsToAdd.add(component);
        component.parent = this;
    }

    public void addPixelComp(GuiComponent component, float relX, float relY) {
        if (!initialized) {
            System.err.println("UI Component must be initialized before adding PP component!");
        }
        float width = component.preferredPixelSize / (scale.x * MainExtras.WIDTH);
        float height = component.preferredPixelSize / (scale.y * MainExtras.HEIGHT);
        addComponent(component, relX, relY, width, height);
    }

    public void addPixelCompCenterX(GuiComponent component, float centerX, float relY) {
        if (!initialized) {
            System.err.println("UI Component must be initialized before adding PP component!");
        }
        float width = component.preferredPixelSize / (scale.x * MainExtras.WIDTH);
        float height = component.preferredPixelSize / (scale.y * MainExtras.HEIGHT);
        addComponent(component, centerX - (width * 0.5f), relY, width, height);
    }

    public void addPixelCompCenterY(GuiComponent component, float relX, float centerY) {
        if (!initialized) {
            System.err.println("UI Component must be initialized before adding PP component!");
        }
        float width = component.preferredPixelSize / (scale.x * MainExtras.WIDTH);
        float height = component.preferredPixelSize / (scale.y * MainExtras.HEIGHT);
        addComponent(component, relX, centerY - (height * 0.5f), width, height);
    }

    public void addPixelCompCenter(GuiComponent component, float centerX, float centerY) {
        if (!initialized) {
            System.err.println("UI Component must be initialized before adding PP component!");
        }
        float width = component.preferredPixelSize / (scale.x * MainExtras.WIDTH);
        float height = component.preferredPixelSize / (scale.y * MainExtras.HEIGHT);
        addComponent(component, centerX - (width * 0.5f), centerY - (height * 0.5f), width, height);
    }

    public void addCenteredComponentX(GuiComponent component, float centerX, float relY, float relScaleY) {
        component.usePreferredAspectFixedY = true;
        component.useCenteringX = true;
        component.relativePosition.set(centerX, relY);
        component.relativeScale.y = relScaleY;
        componentsToAdd.add(component);
        component.parent = this;
    }

    public void addCenteredComponent(GuiComponent component, float centerX, float centerY, float relScaleX) {
        component.usePreferredAspectFixedX = true;
        component.useCenteringX = true;
        component.useCenteringY = true;
        component.relativePosition.set(centerX, centerY);
        component.relativeScale.x = relScaleX;
        componentsToAdd.add(component);
        component.parent = this;
    }

    public void addCenteredComponentY(GuiComponent component, float centerY, float relX, float relScaleX) {
        component.usePreferredAspectFixedX = true;
        component.useCenteringY = true;
        component.relativePosition.set(relX, centerY);
        component.relativeScale.x = relScaleX;
        componentsToAdd.add(component);
        component.parent = this;
    }

    public void addCenteredComponentYScaleY(GuiComponent component, float centerY, float relX, float relScaleY) {
        component.usePreferredAspectFixedY = true;
        component.useCenteringY = true;
        component.relativePosition.set(relX, centerY);
        component.relativeScale.y = relScaleY;
        componentsToAdd.add(component);
        component.parent = this;
    }

    public void setPreferredPixelSize(int size) {
        this.preferredPixelSize = size;
    }

    public void setFocus(boolean focus) {
        this.hasFocus = focus;
        setChildrenFocus();
    }

    private void setChildrenFocus() {
        for (GuiComponent child : childComponents) {
            child.hasFocus = this.hasFocus;
            child.setChildrenRenderLevel();
        }
    }

    public void setRenderLevel(int level) {
        this.level = level;
        this.specificLevelSet = true;
        setChildrenRenderLevel();
    }

    /**
     * Indicates that a child component should be removed from this component
     * and deleted.
     *
     * @param component - the child component to be removed.
     */
    public void removeComponent(GuiComponent component) {
        componentsToRemove.add(component);
    }

    public void clear() {
        componentsToRemove.addAll(childComponents);
        deleteTexts();
    }

    /**
     * Adds some text to the component.
     *
     * @param text         - the text to be added.
     * @param relX         - the x position of the left edge of the text, relative to
     *                     this component's size and position (0 = far left edge, 1 = far
     *                     right).
     * @param relY         - the y position of the top edge of the text, relative to this
     *                     component's size and position (0 = top edge, 1 = bottom edge).
     * @param relLineWidth - the width of the line of text, relative to the width of the
     *                     component.
     */
    public void addText(Text text, float relX, float relY, float relLineWidth) {
        Vectors3f relativePosition = new Vectors3f(relX, relY, relLineWidth);
        text.setParentInfo(this, relativePosition);
        text.setClippingBounds(clippingBounds);
        componentTexts.put(text, relativePosition);
        if (initialized) {
            setTextScreenSpacePosition(text, relativePosition);
        }
    }

    protected boolean isInitialized() {
        return initialized;
    }

    /**
     * @return {@code true} if this component isn't currently hidden.
     */
    public boolean isShown() {
        return visible;
    }

    /**
     * Removes some text from the component and deletes the text.
     *
     * @param text - the text currently in the component that needs to be
     *             removed.
     */
    public void deleteText(Text text) {
        textsToRemove.add(text);
    }

    /**
     * @return The x position of the top-left corner of the component, relative
     * to the parent component.
     */
    public float getRelativeX() {
        return relativePosition.x;
    }

    public void setRelativeX(float x) {
        if (relativePosition.x != x) {
            relativePosition.x = x;
            updateScreenSpacePosition();
        }
    }

    public float getRelativeY() {
        return relativePosition.y;
    }

    public void setRelativeY(float y) {
        if (relativePosition.y != y) {
            relativePosition.y = y;
            updateScreenSpacePosition();
        }
    }

    public float getRelativeScaleY() {
        return relativeScale.y;
    }

    public void setRelativeScaleY(float relScaleY) {
        if (relativeScale.y != relScaleY) {
            this.relativeScale.y = relScaleY;
            updateScreenSpacePosition();
        }
    }

    public float getRelativeScaleX() {
        return relativeScale.x;
    }

    public void setRelativeScaleX(float relScaleX) {
        if (relativeScale.x != relScaleX) {
            this.relativeScale.x = relScaleX;
            updateScreenSpacePosition();
        }
    }

    public void setRelativeScale(float relScaleX, float relScaleY) {
        if (relativeScale.x != relScaleX || relativeScale.y != relScaleY) {
            this.relativeScale.x = relScaleX;
            this.relativeScale.y = relScaleY;
            updateScreenSpacePosition();
        }
    }

    /**
     * Removes this component from its parent, and deletes it. This component
     * cannot be reused after this method has been called. For something less
     * permanent use {@link #show(boolean)}.
     */
    public void remove() {
        parent.removeComponent(this);
    }

    public void increaseRelativePosition(float dX, float dY) {
        relativePosition.x += dX;
        relativePosition.y += dY;
        updateScreenSpacePosition();
    }

    public float getRelativeAspectRatio() {
        return relativeScale.x / relativeScale.y;
    }

    public void setRelativePosition(float x, float y) {
        if (relativePosition.x != x || relativePosition.y != y) {
            relativePosition.x = x;
            relativePosition.y = y;
            updateScreenSpacePosition();
        }
    }

    public void setPreferredAspectRatio(float ratio) {
        this.preferredAspect = ratio;
    }

    public void updateTextAbsPos(Text text) {
        setTextScreenSpacePosition(text, componentTexts.get(text));
    }

    /**
     * @return The screen-space x and y scales of the component.
     */
    public Vectors2f getScale() {
        return scale;
    }

    public float getPixelHeight() {
        return scale.y * MainExtras.HEIGHT;
    }

    public float getPixelWidth() {
        return scale.x * MainExtras.WIDTH;
    }

    /**
     * @return {@code true} if the mouse cursor is currently over this
     * component.
     */
    public final boolean isMouseOver() {
        if (!GuiMaster.isInFocus(this)) {
            return false;
        }
        return false;//isMouseOverFocusIrrelevant();
    }

   public boolean isMouseOverFocusIrrelevant() {
        if (!GuiMaster.isMouseInteractionEnabled()) {
            return false;
        }
        if (MainExtras.getMouseX() >= position.x && MainExtras.getMouseX() <= position.x + scale.x) {
            if (MainExtras.getMouseY() >= position.y && MainExtras.getMouseY() <= position.y + scale.y) {
                return true;
            }
        }
        return false;
    }

    protected boolean inFocus() {
        return hasFocus;
    }

    public int getLevel() {
        return level;
    }

    public float getRelativeMouseX() {
        return (float) ((MainExtras.getMouseX() - position.x) / scale.x);
    }

    public float getRelativeMouseY() {
        return (float) ((MainExtras.getMouseY() - position.y) / scale.y);
    }

    protected void setClippingBounds(float x, float y, float width, float height) {
        int xPixels = Math.round(x * MainExtras.WIDTH);
        int yPixels = MainExtras.HEIGHT - Math.round((y + height) * MainExtras.HEIGHT);
        int widthPixels = Math.round(width * MainExtras.WIDTH);
        int heightPixels = Math.round(height * MainExtras.HEIGHT);
        if (clippingBounds == null) {
            int[] bounds = new int[]{xPixels, yPixels, widthPixels, heightPixels};
            setChildrenClippingBounds(bounds);
        } else {
            clippingBounds[0] = xPixels;
            clippingBounds[1] = yPixels;
            clippingBounds[2] = widthPixels;
            clippingBounds[3] = heightPixels;
        }
    }

    protected void setTextureClippingBounds(int[] bounds) {
        // not a fan of this, but would have to rework the whole GUI system to
        // fix :/
        // Next time I write a GUI system I'll know better!
    }

    protected float pixelsToRelativeX(float pixels) {
        float pixelsWide = (float) MainExtras.WIDTH * scale.x;
        return pixels / pixelsWide;
    }

    protected float pixelsToRelativeY(float pixels) {
        float pixelsHigh = (float) MainExtras.HEIGHT * scale.y;
        return pixels / pixelsHigh;
    }

    /**
     * @return The screen-space position of the top-left corner of the
     * component.
     */
    protected Vectors2f getPosition() {
        return position;
    }

    /**
     * Only for use in the GuiScreenContainer!
     */
    protected void forceInitialization(float absX, float absY, float absScaleX, float absScaleY) {
        position.x = absX;
        position.y = absY;
        scale.x = absScaleX;
        scale.y = absScaleY;
        this.initialized = true;
    }

    /**
     * Updates the component and all of its child-components, as well as filling
     * the list of {@link GuiTexture}s with any textures that this component or
     * any of its sub-components need to be rendered. It also removes any sub
     * components that have indicated that they need to be removed.
     *
     * @param data - the list of {@link GuiTexture}s to be rendered.
     */
    protected final void update(GuiRenderData data) {
        if (!visible) {
            return;
        }
        // addNewChildren();
        updateSelf();
        updateTexts();
        getGuiTextures(data);
        addTextsToRenderBatch(data);
        addNewChildren();
        removeOldComponents();
        for (GuiComponent childComponent : childComponents) {
            childComponent.update(data);
        }
    }

    protected List<GuiComponent> getComponents() {
        return childComponents;
    }

    protected float getRelativeHeightCoords(float relativeWidth) {
        relativeWidth *= ((float) MainExtras.WIDTH / (float) MainExtras.HEIGHT);
        relativeWidth *= scale.x / scale.y;
        return relativeWidth;
    }

    protected float getRelativeWidthCoords(float relativeHeight) {
        relativeHeight /= ((float) MainExtras.WIDTH / (float) MainExtras.HEIGHT);
        relativeHeight /= scale.x / scale.y;
        return relativeHeight;
    }

    /**
     * Can be overridden if necessary - is called once after the first time that
     * the component is added to the parent, after its positions and scales have
     * been calculated.
     */
    protected void init() {
    }

    /**
     * Calculates the screen space position of the component based on their
     * relative position and the screen-space position of their parent.
     */
    protected void updateScreenSpacePosition() {
        calculateAbsPositionAndScale();
        updateGuiTexturePositions(position, scale);
        for (GuiComponent component : childComponents) {
            component.updateScreenSpacePosition();
        }
        for (Text text : componentTexts.keySet()) {
            setTextScreenSpacePosition(text, componentTexts.get(text));
        }
    }

    protected abstract void updateGuiTexturePositions(Vectors2f position, Vectors2f scale);

    /**
     * Updates the functionality of this particular component.
     */
    protected abstract void updateSelf();

    /**
     * Adds any of this component's {@link GuiTexture}s.
     *
     * @param data - the list of GUI textures that are going to be rendered. This
     *             method adds any necessary {@link GuiTexture}s from this
     *             component to that list.
     */
    protected abstract void getGuiTextures(GuiRenderData data);

    /**
     * Deletes the component and all sub-components. Mainly just deletes the
     * texts VAOs from memory.
     */
    protected void delete() {
        deleteTexts();
        for (GuiComponent component : componentsToRemove) {
            component.delete();
        }
        for (GuiComponent component : componentsToAdd) {
            component.delete();
        }
        for (GuiComponent component : childComponents) {
            component.delete();
        }
    }

    private void setChildrenClippingBounds(int[] bounds) {
        this.clippingBounds = bounds;
        setTextureClippingBounds(bounds);
        for (Text text : componentTexts.keySet()) {
            text.setClippingBounds(clippingBounds);
        }
        for (GuiComponent child : childComponents) {
            child.setChildrenClippingBounds(clippingBounds);
        }
    }

    private void setChildrenRenderLevel() {
        for (GuiComponent child : childComponents) {
            if (!child.specificLevelSet) {
                child.level = this.level;
                child.setChildrenRenderLevel();
            }
        }
    }

    /**
     * Adds the texts from this component into the map of texts for rendering
     * this frame. There is a list of texts for each font being used.
     *
     * @param data - The render data.
     */
    private void addTextsToRenderBatch(GuiRenderData data) {
        for (Text text : componentTexts.keySet()) {
            data.addText(level, text);
        }
    }

    private void deleteTexts() {
        for (Text text : componentTexts.keySet()) {
            text.deleteFromMemory();
        }
        componentTexts.clear();
    }

    /**
     * Calculate the screen-space position and screen-space line width of a text
     * based on their relative position and the screen space position of this
     * component.
     *
     * @param text             - the text whose screen-space position needs to be set.
     * @param relativePosition - the position of the text relative to this component's
     *                         top-left corner. The z component is the line width, specified
     *                         relative to the width of this component.
     */
    private void setTextScreenSpacePosition(Text text, Vectors3f relativePosition) {
        float x = position.x + (scale.x * relativePosition.x);
        float y = position.y + (scale.y * relativePosition.y);
        float lineWidth = relativePosition.z * scale.x;
        text.initialise(x, y, lineWidth);
    }

    /**
     * Update the component's texts.
     */
    private void updateTexts() {
        for (Text text : textsToRemove) {
            componentTexts.remove(text);
            text.deleteFromMemory();
        }
        textsToRemove.clear();
        for (Text text : componentTexts.keySet()) {
            text.update(MainExtras.getDeltaSeconds());
        }
    }

    private void removeOldComponents() {
        while (!componentsToRemove.isEmpty()) {
            GuiComponent component = componentsToRemove.remove(0);
            childComponents.remove(component);
            component.delete();
        }
    }

    private void addNewChildren() {
        int index = 0;
        while (index < componentsToAdd.size()) {
            GuiComponent component = componentsToAdd.get(index++);
            childComponents.add(component);
            component.clippingBounds = this.clippingBounds;
            component.hasFocus = this.hasFocus;
            if (!component.specificLevelSet) {
                component.level = this.level;
            }
            component.updateScreenSpacePosition();
            component.init();
            component.setTextureClippingBounds(clippingBounds);
            component.addNewChildren();
        }
        componentsToAdd.clear();
    }

    private void calculateAbsPositionAndScale() {
        position.x = parent.position.x + (parent.scale.x * relativePosition.x);
        position.y = parent.position.y + (parent.scale.y * relativePosition.y);
        scale.x = relativeScale.x * parent.scale.x;
        scale.y = relativeScale.y * parent.scale.y;
        if (usePreferredAspectFixedY) {
            scale.x = convertToScreenWidthCoords(scale.y);
            relativeScale.x = scale.x / parent.scale.x;
            usePreferredAspectFixedY = false;
        } else if (usePreferredAspectFixedX) {
            scale.y = convertToScreenHeightCoords(scale.x);
            relativeScale.y = scale.y / parent.scale.y;
            usePreferredAspectFixedX = false;
        }
        if (useCenteringX) {
            position.x = position.x - scale.x / 2f;
            relativePosition.x = (position.x - parent.position.x) / parent.scale.x;
            useCenteringX = false;
        }
        if (useCenteringY) {
            position.y = position.y - scale.y / 2f;
            relativePosition.y = (position.y - parent.position.y) / parent.scale.y;
            useCenteringY = false;
        }
        initialized = true;
    }

    private float convertToScreenWidthCoords(float heightCoord) {
        float out = (heightCoord * 16)/9;
        return out;
    }

    private float convertToScreenHeightCoords(float widthCoord) {
        float out = (widthCoord * 9)/16;
        return out;
    }

}
