/**
 * *	This file is part of the project https://github.com/toss-dev/VoxelEngine
 * *
 * *	License is available here: https://raw.githubusercontent.com/toss-dev/VoxelEngine/master/LICENSE.md
 * *
 * *	PEREIRA Romain
 * *                                       4-----7
 * *                                      /|    /|
 * *                                     0-----3 |
 * *                                     | 5___|_6
 * *                                     |/    | /
 * *                                     1-----2
 */

package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector2f;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.animations.GuiAnimation;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.animations.GuiAnimationFadeIn;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.animations.GuiAnimationFadeOut;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiParameter;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.*;

import java.util.*;

/**
 *
 * Main Gui class.
 *
 * A Gui can have parent / children.
 *
 * It has a position, a rotation (relative to the axis poiting into the screen),
 * and a scaling. These value are relative to the Gui parent referential (or to
 * the screen referential if it has no parent)
 *
 * WARNING: When a Gui is created, it is automatically allocated when needed,
 * but it is not de-allocated automatically! if you no longer need a Gui, you
 * should call: {@link #deinitialize()}
 *
 * @author Romain
 *
 */

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class Gui {

    /** default colors */
    public static final Color COLOR_WHITE = new Color(0.9f, 0.9f, 0.9f, 1.0f);
    public static final Color COLOR_BLACK = new Color(0.1f, 0.1f, 0.1f, 1.0f);
    public static final Color COLOR_BLUE = new Color(65 / 255.0f, 105 / 255.0f, 225 / 255.0f, 1.0f);
    public static final Color COLOR_LIGHT_BLUE = Color.scale(null, COLOR_BLUE, 1.5f);
    public static final Color COLOR_RED = new Color(176 / 255.0f, 23 / 255.0f, 31 / 255.0f, 1.0f);
    public static final Color COLOR_DARK_MAGENTA = new Color(139 / 255.0f, 0 / 255.0f, 139 / 255.0f, 1.0f);
    /** mouse states (using bitwise operations) */
    public static final int STATE_INITIALIZED = (1 << 0);
    public static final int STATE_HOVERED = (1 << 1);
    public static final int STATE_FOCUSED = (1 << 2);
    public static final int STATE_LEFT_PRESSED = (1 << 3);
    public static final int STATE_RIGHT_PRESSED = (1 << 4);
    public static final int STATE_SELECTABLE = (1 << 5);
    public static final int STATE_VISIBLE = (1 << 6);
    public static final Comparator<? super Gui> LAYER_COMPARATOR = new Comparator<Gui>() {
        @Override
        public int compare(Gui left, Gui right) {
            return (left.getLayer() - right.getLayer());
        }
    };
    private static final int STATE_ENABLED = (1 << 7);
    private static final int STATE_REQUESTED_FOCUS = (1 << 8);
    protected static final GuiListener<GuiEventPress<Gui>> ON_PRESS_FOCUS_LISTENER = new GuiListener<GuiEventPress<Gui>>() {
        @Override
        public void invoke(GuiEventPress<Gui> event) {
            event.getGui().requestFocus(true);
        }
    };
    protected static final GuiListener<GuiEventPress<Gui>> ON_UNPRESS_FOCUS_LISTENER = new GuiListener<GuiEventPress<Gui>>() {
        @Override
        public void invoke(GuiEventPress<Gui> event) {
            event.getGui().requestFocus(false);
        }
    };
    private static final int STATE_REQUESTED_UNFOCUS = (1 << 9);
    protected static final GuiListener<GuiEventMouseMove<Gui>> ON_HOVERED_FOCUS_LISTENER = new GuiListener<GuiEventMouseMove<Gui>>() {
        @Override
        public void invoke(GuiEventMouseMove<Gui> event) {
            event.getGui().requestFocus(event.getGui().isHovered());
            event.getGui().requestUnfocus(!event.getGui().isHovered());
        }
    };
    private static final int STATE_SELECTED = (1 << 10);
    private static final int STATE_HOVERABLE = (1 << 11);
    private static final int STATE_PARENT_RELATIVE_SIZE = (1 << 12);
    /** the transformation matrix, relative to the parent */
    private final Matrix4f guiToParentChangeOfBasis;
    private final Matrix4f guiToWindowChangeOfBasis;
    private final Matrix4f windowToGuiChangeOfBasis;
    private final Matrix4f guiToGLChangeOfBasis;
    /** this rectangle relative to opengl axis (-1;-1) to (1;1) */
    private final Vector2f boxPos;
    private final Vector2f boxSize;
    private final Vector2f boxCenter;
    private final ArrayList<GuiTask> tasks;
    /** gui custom attributes */
    private HashMap<String, Object> attributes;
    private float boxRot;
    /** transparency value of this gui in [0, 1] */
    private float transparency;
    /** guis of this view */
    private ArrayList<Gui> children;
    private Gui parent;
    private ArrayList<GuiParameter<Gui>> params;
    /** gui animations */
    private ArrayList<GuiAnimation<Gui>> animations;
    /** hashmap: class of 'GuiEvent', and list of GuiListener<GuiEvent> */
    private HashMap<Class<?>, ArrayList<GuiListener<?>>> eventListeners;
    /** gui state */
    private int state;
    private float localAspectRatio = 1.0f;
    private float totalAspectRatio = 1.0f;
    private int layer;

    /** border */
    private float borderWidth;
    private Color borderColor;

    /** the mouse coordinates relatively to the gui basis */
    private float mouseX;
    private float mouseY;
    private float prevMouseX;
    private float prevMouseY;

    public Gui() {
        this.children = null;
        this.guiToParentChangeOfBasis = new Matrix4f();
        this.guiToWindowChangeOfBasis = new Matrix4f();
        this.windowToGuiChangeOfBasis = new Matrix4f();
        this.guiToGLChangeOfBasis = new Matrix4f();
        this.boxPos = new Vector2f();
        this.boxCenter = new Vector2f();
        this.boxSize = new Vector2f();
        this.boxRot = 0.0f;
        this.setTransparency(1);
        this.setBox(0, 0, 1, 1, 0);
        this.params = null;
        this.animations = null;
        this.state = 0;
        this.tasks = new ArrayList<GuiTask>();
        this.setVisible(true);
        this.setPressed(false);
        this.setSelected(false);
        this.setSelectable(false);
        this.setEnabled(true);
        this.setHoverable(true);
        this.setSizeRelativeToParent(true);
        this.requestFocus(false);
        this.focus(false);
    }

    /** a listener to the mouse hovering the gui */
    public void addListener(GuiListener<?> listener) {
        if (listener == null) {
            return;
        }
        if (this.eventListeners == null) {
            this.eventListeners = new HashMap<Class<?>, ArrayList<GuiListener<?>>>();
        }
        ArrayList<GuiListener<?>> lst = this.eventListeners.get(listener.getEventClass());
        if (lst == null) {
            lst = new ArrayList<GuiListener<?>>();
            this.eventListeners.put(listener.getEventClass(), lst);
        }

        lst.add(listener);
    }

    /** a listener to the mouse entering the gui */
    public void removeListener(GuiListener<?> listener) {
        ArrayList<GuiListener<?>> lst = this.eventListeners.get(listener.getEventClass());
        if (lst == null) {
            return;
        }
        lst.remove(listener);
        if (lst.size() == 0) {
            this.eventListeners.remove(listener.getEventClass());
        }
        if (this.eventListeners.size() == 0) {
            this.eventListeners = null;
        }
    }

    /**
     * start an animation
     *
     * @return the animation id
     */
    public int startAnimation(GuiAnimation<? extends Gui> animation) {
        if (this.animations == null) {
            this.animations = new ArrayList<GuiAnimation<Gui>>();
        }
        this.animations.add((GuiAnimation<Gui>) animation);
        ((GuiAnimation<Gui>) animation).restart(this);
        ((GuiAnimation<Gui>) animation).onStart(this);
        return (this.animations.size() - 1);
    }

    /** stop an animation by it id */
    public void stopAnimation(int index) {
        if (this.animations != null && index >= 0 && index < this.animations.size()) {
            this.animations.remove(index);
            if (this.animations.size() == 0) {
                this.animations = null;
            }
        }
    }

    /** stop every animation with the given class */
    public void stopAnimation(Class<?> clazz) {
        if (this.animations == null) {
            return;
        }

        for (int i = 0; i < this.animations.size(); i++) {
            GuiAnimation<?> animation = this.animations.get(i);

            if (animation == null) {
                continue;
            }

            if (animation.getClass().equals(clazz)) {
                this.stopAnimation(i);
            }
        }
    }

    public void setBoxPosition(float x, float y) {
        this.setBoxPosition(x, y, true);
    }

    protected void setBoxPosition(float x, float y, boolean runParameters) {
        this.setBox(x, y, this.getBoxWidth(), this.getBoxHeight(), this.getBoxRotation(), runParameters);
    }

    /** set the gui text center, if using PARAM_CENTER */
    public final void setBoxCenterPosition(float xcenter, float ycenter) {
        this.setBoxCenter(xcenter, ycenter, this.getBoxWidth(), this.getBoxHeight(), this.getBoxRotation());
    }

    public final void setBoxCenter(float xcenter, float ycenter, float width, float height, float rot) {
        this.setBoxCenter(xcenter, ycenter, width, height, rot, true);
    }

    public final void setBoxCenter(float xcenter, float ycenter, float width, float height, float rot,
                                   boolean runParameters) {
        this.setBox(xcenter - width * 0.5f, ycenter - height * 0.5f, width, height, rot, runParameters);
    }

    public void setBoxSize(float width, float height) {
        this.setBoxSize(width, height, true);
    }

    public final void setBoxSize(float width, float height, boolean runParameters) {
        this.setBox(this.getBoxX(), this.getBoxY(), width, height, this.getBoxRotation(), runParameters);
    }

    /**
     * MAIN SETTER FUNCTION.
     *
     * Every setters end up calling this one.
     *
     * It updates the transformation matrix
     *
     * Coordinates and dimension follows (0, 0) (bot left) (1, 1) (top right)
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param rot
     */
    public final void setBox(float x, float y, float width, float height, float rot) {
        this.setBox(x, y, width, height, rot, true);
    }

    public final void setBox(Gui gui) {
        this.setBox(gui.getBoxX(), gui.getBoxY(), gui.getBoxWidth(), gui.getBoxHeight(), gui.getBoxRotation());
    }

    public final void setBox(float x, float y, float width, float height, float rot, boolean runParameters) {

        // positions
        this.boxPos.set(x, y);
        this.boxSize.set(width, height);
        this.boxRot = rot;
        this.boxCenter.set(x + width * 0.5f, y + height * 0.5f);

        // matrices are relative to GL referential
        this.guiToParentChangeOfBasis.setIdentity();
        this.guiToParentChangeOfBasis.translate(this.boxCenter.x, this.boxCenter.y, 0.0f);
        this.guiToParentChangeOfBasis.rotateZ(rot);
        this.guiToParentChangeOfBasis.translate(-this.boxCenter.x, -this.boxCenter.y, 0.0f);
        this.guiToParentChangeOfBasis.translate(x, y, 0.0f);
        this.guiToParentChangeOfBasis.scale(width, height, 1.0f);

        Matrix4f parentTransform = this.parent == null || !this.isSizeRelativeToParent() ? Matrix4f.IDENTITY
                : this.parent.guiToWindowChangeOfBasis;
        this.updateTransformationMatrices(parentTransform);

        // aspect ratio
        float aspectRatio = this.getBoxWidth() / this.getBoxHeight();
        if (aspectRatio != this.localAspectRatio) {
            this.localAspectRatio = aspectRatio;
            float parentAspectRatio = this.getParent() == null ? 1.0f : this.getParent().getTotalAspectRatio();
            this.updateAspectRatio(parentAspectRatio, runParameters);
        }

        if (runParameters) {
            this.runParameters();
        }
    }

    private void updateAspectRatio(float parentAspectRatio, boolean runParameters) {
        float oldAspectRatio = this.totalAspectRatio;
        this.totalAspectRatio = parentAspectRatio * this.localAspectRatio;
        this.stackEvent(new GuiEventAspectRatio(this, oldAspectRatio, this.totalAspectRatio));
        if (this.children != null) {
            for (Gui gui : this.children) {
                gui.updateAspectRatio(this.totalAspectRatio, runParameters);
            }
        }
    }

    /** update transformation matrices */
    private final void updateTransformationMatrices(Matrix4f parentTransform) {

        // combine with parent transformation
        Matrix4f.mul(parentTransform, this.guiToParentChangeOfBasis, this.guiToWindowChangeOfBasis);
        Matrix4f.invert(this.guiToWindowChangeOfBasis, this.windowToGuiChangeOfBasis);
        Matrix4f.mul(MainRenderer.WINDOW_TO_GL_BASIS, this.guiToWindowChangeOfBasis, this.guiToGLChangeOfBasis);

        // finally, set it relative to opengl screen referential
        if (this.children != null) {
            for (Gui child : this.children) {
                child.updateTransformationMatrices(this.guiToWindowChangeOfBasis);
            }
        }
    }

    /**
     * render the gui
     *
     * @param program
     */
    public final void render(GuiRenderer renderer) {

        if (!this.isInitialized()) {
            this.initialize(renderer);
        }

        this.onRender(renderer);
    }

    /** do the rendering of this gui */
    protected void onRender(GuiRenderer guiRenderer) {

    }

    /** initialize the gui */
    public final void initialize(GuiRenderer renderer) {
        if (this.hasState(STATE_INITIALIZED)) {
            return;
        }
        this.setState(STATE_INITIALIZED);
        this.onInitialized(renderer);

        if (this.children != null) {
            for (Gui gui : this.children) {
                gui.initialize(renderer);
            }
        }
    }

    /** initialize the gui: this function is call in opengl main thread */
    protected void onInitialized(GuiRenderer renderer) {

    }

    /** deinitialize the gui */
    public final void deinitialize(GuiRenderer renderer) {

        // remove from parent
        if (this.parent != null) {
            this.parent.removeChild(Gui.this);
        }

        // deinitialize this gui
        if (!this.hasState(STATE_INITIALIZED)) {
            return;
        }
        this.unsetState(STATE_INITIALIZED);
        this.onDeinitialized(renderer);

        // repeat on each child
        if (this.children != null) {
            for (Gui child : this.children) {
                if (!child.hasState(STATE_INITIALIZED)) {
                    continue;
                }

                child.unsetState(STATE_INITIALIZED);
                child.onDeinitialized(renderer);
            }
        }
    }

    /**
     * remove this gui from it parent, and delete it from the heap. Repeated for
     * each child of this gui
     */
    public final void deinitialize() {
        this.deinitialize(null);
    }

    /**
     * @see #deinitialize()
     */
    public final void pop() {
        this.deinitialize(null);
    }

    /** deinitialize the gui: this function is call in opengl main thread */
    protected void onDeinitialized(GuiRenderer renderer) {
    }

    /** stack an event, it listeners will be caled using Gui#unstackEvents() */
    public final void stackEvent(GuiEvent<?> guiEvent) {
        if (this.eventListeners == null) {
            return;
        }

        ArrayList<GuiListener<?>> listeners = this.eventListeners.get(guiEvent.getClass());
        if (listeners == null) {
            return;
        }

        for (GuiListener<?> listener : listeners) {
            listener.stackEvent(guiEvent);
        }
    }

    /** release the stack events and call their listener */
    public final void unstackEvents() {
        if (this.eventListeners == null) {
            return;
        }

        Collection<ArrayList<GuiListener<?>>> allListeners = this.eventListeners.values();

        for (ArrayList<GuiListener<?>> listeners : allListeners) {
            for (GuiListener<?> listener : listeners) {
                listener.invokeEvents();
                listener.unstackEvents();
            }
        }

        this.onInputUpdate();
    }

    /** update animations and gui tasks */
    public final void update() {
        if (!this.isInitialized()) {
            return;
        }
        if (this.tasks.size() > 0) {
            Iterator<GuiTask> tasks = this.tasks.iterator();
            while (tasks.hasNext()) {
                GuiTask task = tasks.next();
                if (task.run()) {
                    tasks.remove();
                }
            }
        }

        this.updateAnimations();
        this.onUpdate();
    }

    protected void onUpdate() {
    }

    /** add a task to be run at the end of the gui update */
    public final void addTask(GuiTask task) {
        this.tasks.add(task);
    }

    private void updateAnimations() {
        if (this.animations != null) {
            int i = 0;
            while (i < this.animations.size()) {
                GuiAnimation<Gui> animation = this.animations.get(i);
                if (animation.update(this)) {
                    animation.onStop(this);
                    this.animations.remove(i);
                    continue;
                }
                ++i;
            }
        }
    }

    /**
     * @return : the mouse X coordinate relatively to the gui basis
     */
    public final float getMouseX() {
        return (this.mouseX);
    }

    /**
     * @return : the mouse Y coordinate relatively to the gui basis
     */
    public final float getMouseY() {
        return (this.mouseY);
    }

    /**
     * @return : the previous mouse X coordinate relatively to the gui basis
     */
    public final float getPrevMouseX() {
        return (this.prevMouseX);
    }

    /**
     * @return : the previous mouse Y coordinate relatively to the gui basis
     */
    public final float getPrevMouseY() {
        return (this.prevMouseY);
    }

    public final boolean isSelectable() {
        return (this.hasState(STATE_SELECTABLE));
    }

    public final void setSelectable(boolean isSelectable) {
        this.setState(STATE_SELECTABLE, isSelectable);
    }

    public final boolean isEnabled() {
        return (this.hasState(STATE_ENABLED));
    }

    /**
     * set to true or false weather you want this gui to be responsive to mouse
     * events
     */
    public final void setEnabled(boolean enabled) {
        this.setState(STATE_ENABLED, enabled);
    }

    public final boolean isVisible() {
        return (this.hasState(STATE_VISIBLE));
    }

    public void setVisible(boolean visible) {
        this.setState(STATE_VISIBLE, visible);
    }

    /** return true if the mouse cursor is inside this gui */
    public final boolean isHovered() {
        return (this.hasState(STATE_HOVERED));
    }

    public final void setHovered(boolean isHovered) {
        this.setState(STATE_HOVERED, isHovered);
    }

    public final boolean isHoverable() {
        return (this.hasState(STATE_HOVERABLE));
    }

    public final void setHoverable(boolean isHoverable) {
        this.setState(STATE_HOVERABLE, isHoverable);
    }

    /** return true if the gui is pressed */
    public final boolean isPressed() {
        return (this.isLeftPressed());
    }

    public final void setPressed(boolean isPressed) {
        this.setLeftPressed(isPressed);
    }

    public final boolean isLeftPressed() {
        return (this.hasState(STATE_LEFT_PRESSED));
    }

    public final void setLeftPressed(boolean isPressed) {
        this.setState(STATE_LEFT_PRESSED, isPressed);
    }

    public final boolean isRightPressed() {
        return (this.hasState(STATE_RIGHT_PRESSED));
    }

    public final void setRightPressed(boolean isPressed) {
        this.setState(STATE_RIGHT_PRESSED, isPressed);
    }

    /** return true if the gui is selected */
    public final boolean isSelected() {
        return (this.hasState(STATE_SELECTED));
    }

    public final void setSelected(boolean isSelected) {
        this.setState(STATE_SELECTED, isSelected);
    }

    public void addParameter(GuiParameter<?> parameter) {
        if (this.params == null) {
            this.params = new ArrayList<GuiParameter<Gui>>();
        }
        this.params.add((GuiParameter<Gui>) parameter);
        ((GuiParameter<Gui>) parameter).run(this);
    }

    public void addParameters(GuiParameter<?>... parameters) {
        for (GuiParameter<?> parameter : parameters) {
            this.addParameter(parameter);
        }
    }

    /**
     * run the parameter to update the gui position and size depending on it
     * parameters You shall not have to call it, as it is call when setting a
     * gui's size and position automatically
     */
    public final void runParameters() {
        if (this.params == null) {
            return;
        }
        for (GuiParameter<Gui> param : this.params) {
            param.run(this);
        }
        if (this.children != null) {
            for (Gui child : this.children) {
                child.runParameters();
            }
        }
    }

    public boolean hasParameter(GuiParameter<?> param) {
        if (this.params == null) {
            return (false);
        }
        return (this.params.contains(param));
    }

    public float getBoxCenterX() {
        return (this.boxCenter.x);
    }

    public float getBoxCenterY() {
        return (this.boxCenter.y);
    }

    public float getBoxX() {
        return (this.boxPos.x);
    }

    /** set the x position, but do not run parameters to adjust it */
    public final void setBoxX(float x) {
        this.setBoxPosition(x, this.boxPos.y);
    }

    public float getBoxY() {
        return (this.boxPos.y);
    }

    /** set the y position, but do not run parameters to adjust it */
    public final void setBoxY(float y) {
        this.setBoxPosition(this.boxPos.x, y);
    }

    public float getBoxWidth() {
        return (this.boxSize.x);
    }

    /** set the width, but do not run parameters to adjust it */
    public void setBoxWidth(float width) {
        this.setBoxSize(width, this.boxSize.y);
    }

    public float getBoxHeight() {
        return (this.boxSize.y);
    }

    /** set the height, but do not run parameters to adjust it */
    public void setBoxHeight(float height) {
        this.setBoxSize(this.boxSize.x, height);
    }

    public final boolean hasFocus() {
        return (this.hasState(STATE_FOCUSED));
    }

    public final void requestFocus(boolean focus) {
        this.setState(STATE_REQUESTED_FOCUS, focus);
    }

    public final void requestUnfocus(boolean unfocus) {
        this.setState(STATE_REQUESTED_UNFOCUS, unfocus);
    }

    public final boolean requestedFocus() {
        return (this.hasState(STATE_REQUESTED_FOCUS));
    }

    public final boolean requestedUnfocus() {
        return (this.hasState(STATE_REQUESTED_UNFOCUS));
    }

    /** DO NOT USE, USE {@link Gui#requestFocus()} */
    public final void focus(boolean hasFocus) {
        this.setState(STATE_FOCUSED, hasFocus);
    }

    public final float getBoxRotation() {
        return (this.boxRot);
    }

    public final void setBoxRotation(float rot) {
        this.setBoxRotation(rot, true);
    }

    public final void setBoxRotation(float rot, boolean runParameters) {
        this.setBox(this.getBoxX(), this.getBoxY(), this.getBoxWidth(), this.getBoxHeight(), rot, runParameters);
    }

    public final Gui getParent() {
        return (this.parent);
    }

    public final Gui getOldestParent() {
        if (this.parent == null) {
            return (null);
        }

        Gui oldest = this.parent;
        while (oldest.getParent() != null) {
            oldest = oldest.getParent();
        }
        return (oldest);
    }

    public final ArrayList<Gui> getChildren() {
        return (this.children);
    }

    /** add a child to this gui */
    public final void addChild(Gui gui) {
        this.addChild(this.children == null ? 0 : this.children.size(), gui);
    }

    public final void addChild(int position, Gui gui) {
        if (gui.parent != null) {
            Logger.get().log(Logger.Level.WARNING, gui, "already have a parent...");
        }
        gui.parent = this;
        if (this.children == null) {
            this.children = new ArrayList<Gui>();
        }
        this.children.add(position, gui);
        gui.updateTransformationMatrices(this.guiToWindowChangeOfBasis);
        gui.updateAspectRatio(this.getTotalAspectRatio(), false);
        gui.setLayer(this.getLayer() + 1);
        this.stackEvent(new GuiEventAddChild<Gui, Gui>(this, gui));
    }

    /** remove a child from this gui */
    public final void removeChild(Gui gui) {
        if (this.children == null) {
            return;
        }
        this.children.remove(gui);
        this.stackEvent(new GuiEventRemoveChild<Gui, Gui>(this, gui));
        if (this.children.size() == 0) {
            this.children = null;
        }
    }

    /** return true if this Gui has been initialized */
    public final boolean isInitialized() {
        return (this.hasState(STATE_INITIALIZED));
    }

    private final boolean hasState(int state) {
        return ((this.state & state) == state);
    }

    private final void setState(int state) {
        this.state = this.state | state;
        this.onStateChanged();
    }

    private final void setState(int state, boolean enabled) {
        if (enabled) {
            this.setState(state);
        } else {
            this.unsetState(state);
        }
    }

    private final void unsetState(int state) {
        this.state = this.state & ~state;
        this.onStateChanged();
    }

    @SuppressWarnings("unused")
    private final void swapState(int state) {
        this.state = this.state ^ state;
        this.onStateChanged();
    }

    protected void onStateChanged() {

    }

    public final Matrix4f getGuiToWindowChangeOfBasis() {
        return (this.guiToWindowChangeOfBasis);
    }

    public final Matrix4f getWindowToGuiChangeOfBasis() {
        return (this.windowToGuiChangeOfBasis);
    }

    public final Matrix4f getGuiToParentChangeOfBasis() {
        return (this.guiToParentChangeOfBasis);
    }

    public final Matrix4f getGuiToGLChangeOfBasis() {
        return (this.guiToGLChangeOfBasis);
    }

    /**
     * @return the aspect ratio of this gui relative to his parent
     */
    public float getLocalAspectRatio() {
        return (this.localAspectRatio);
    }

    /**
     * @return the aspect ratio of this gui relative to the window
     */
    public float getTotalAspectRatio() {
        return (this.totalAspectRatio);
    }

    public void onWindowResized(int width, int height, float aspectRatio) {
        if (this.children != null) {
            for (Gui gui : this.children) {
                gui.onWindowResized(width, height, aspectRatio);
            }
        }
    }

    public final int getLayer() {
        return (this.layer);
    }

    /**
     * gui layer (the greater the layer is, the most this gui will be placed in
     * foreground layers). This layer is relative to the window
     */
    public final void setLayer(int layer) {
        if (this.layer == layer) {
            return;
        }
        if (this.parent == null) {
            Logger.get().log(Logger.Level.WARNING,
                    "Tried to set a Gui layer before it parent was set. This layer will be reset once this Gui will be added to it parent.");
        }
        int inc = layer - this.layer;
        this.layer = layer;
        if (this.getChildren() != null) {
            for (Gui gui : this.getChildren()) {
                gui.setLayer(gui.getLayer() + inc);
            }
        }
    }

    public final void increaseLayer(int inc) {
        this.setLayer(this.layer + inc);
    }

    public final Object getAttribute(String attrID) {
        return (this.attributes == null ? null : this.attributes.get(attrID));
    }

    public final void setAttribute(String attrID, Object attribute) {
        if (this.attributes == null) {
            this.attributes = new HashMap<String, Object>();
            this.attributes.put(attrID, attribute);
        }
    }

    public final void removeAttribute(String attrID) {
        if (this.attributes == null) {
            return;
        }
        this.attributes.remove(attrID);
        if (this.attributes.size() == 0) {
            this.attributes = null;
        }
    }

    public void setMouse(float x, float y) {
        this.prevMouseX = this.mouseX;
        this.prevMouseY = this.mouseY;
        this.mouseX = x;
        this.mouseY = y;
    }

    protected void onInputUpdate() {
    }

    public final float getTransparency() {
        return (this.transparency);
    }

    public final void setTransparency(float transparency) {
        this.transparency = transparency < 0.0f ? 0.0f : transparency > 1.0f ? 1.0f : transparency;
        if (this.children != null) {
            for (Gui gui : this.children) {
                gui.setTransparency(transparency);
            }
        }
    }

    public void fadeOut(double t) {
        this.getParent().startAnimation(new GuiAnimationFadeOut<Gui, Gui>(this, 0.15d));
    }

    public void fadeIn(Gui parent, double t) {
        parent.startAnimation(new GuiAnimationFadeIn<Gui, Gui>(this, 0.15d));
    }

    /**
     * return the topest layer found (tested gui are the 'older' parent found,
     * and it childs)
     */
    public final int getTopestLayer() {
        Gui localParentParent = this.getParent();
        Gui localParent = this;
        while (true) {
            Gui oldLocalParentParent = localParentParent;
            localParentParent = localParent.getParent();
            if (localParentParent == null) {
                break;
            }
            localParent = oldLocalParentParent;
        }
        return (localParent.getTopestLayerUnder());
    }

    /** return the topest layer found (tested gui are 'this', and it childs) */
    public final int getTopestLayerUnder() {
        int topestLayer = this.layer;
        if (this.children != null) {
            for (Gui child : this.children) {
                int childTopestLayer = child.getTopestLayerUnder();
                if (childTopestLayer > topestLayer) {
                    topestLayer = childTopestLayer;
                }
            }
        }
        return (topestLayer);
    }

    /**
     * return true if this gui dimension should be interperted relatively to it
     * parent
     */
    public final boolean isSizeRelativeToParent() {
        return (this.hasState(STATE_PARENT_RELATIVE_SIZE));
    }

    /**
     * set to true if you want this gui dimension to be relative to its parent,
     * false elseway (true by default)
     *
     * @param value
     */
    public final void setSizeRelativeToParent(boolean value) {
        this.setState(STATE_PARENT_RELATIVE_SIZE, value);
        Matrix4f parentTransform = this.parent == null || !this.isSizeRelativeToParent() ? Matrix4f.IDENTITY
                : this.parent.guiToWindowChangeOfBasis;
        this.updateTransformationMatrices(parentTransform);
    }

    /** a task to be run at the end of the gui update */
    public interface GuiTask {
        /** return true if this task should be popped */
        public boolean run();
    }
}
