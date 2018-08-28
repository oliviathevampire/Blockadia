package team.hdt.blockadia.game_engine_old.client.rendering.fontRendering;

import team.hdt.blockadia.game_engine.client.MainExtras;
import team.hdt.blockadia.game_engine_old.client.guis.GuiComponent;
import team.hdt.blockadia.game_engine_old.common.Loader;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors3f;
import team.hdt.blockadia.game_engine_old.util.toolbox.Colour;
import team.hdt.blockadia.game_engine_old.util.visualFxDrivers.ColourBounceDriver;
import team.hdt.blockadia.game_engine_old.util.visualFxDrivers.ConstantDriver;
import team.hdt.blockadia.game_engine_old.util.visualFxDrivers.ValueDriver;

public class Text {

    private static final float BOLD_FACTOR = 0.017f;
    private static final float SCREEN_FACTOR = MainExtras.HEIGHT / 720f;
    private final float fontSize;
    private final FontType fontType;
    private final boolean centerText;
    private final boolean rightAlignText;
    private final boolean justify;
    private final boolean indent;
    private String textString;
    private int textMesh;
    private int vertexCount;
    private float lineMaxSize;
    private int numberOfLines;
    private float originalWidth;

    private int[] scissorTestInfo = null;

    private ValueDriver alphaDriver = new ConstantDriver(1);
    private ValueDriver scaleDriver = new ConstantDriver(1);
    private ValueDriver glowDriver = new ConstantDriver(0);
    private ValueDriver borderDriver = new ConstantDriver(0);
    private ColourBounceDriver colourDriver = null;
    private Colour colour = new Colour(0f, 0f, 0f);
    private Colour borderColour = new Colour(1f, 1f, 1f);

    private boolean solidBorder = false;
    private boolean glowBorder = false;

    private float effectScale = 1;
    private float parentScaleFactor = 1;
    private float currentX;
    private float currentY;
    private float currentAlpha;
    private float glowSize = 0;
    private float borderSize = 0;
    private float originalLineWidth;
    private Vectors3f relativePosition;
    private GuiComponent parent;

    private float originalHeight;
    private float actualTextWidth;
    private boolean empty = false;

    private boolean expandCenter = true;

    private boolean loaded = false;

    protected Text(String text, FontType font, float fontSize, boolean centered, boolean rightAligned, boolean justified, boolean indent) {
        this.textString = text;
        this.fontSize = fontSize;
        this.fontType = font;
        this.justify = justified;
        this.centerText = centered;
        this.rightAlignText = rightAligned;
        this.indent = indent;
        empty = textString.length() == 0;
        if (empty) {
            textString = ".";
        }
    }

    public static TextBuilder newText(String text) {
        return new TextBuilder(text);
    }

    public FontType getFontType() {
        return fontType;
    }

    public void initialise(float absX, float absY, float maxXLength) {
        // FIXME rather set values in the drivers, don't create new drivers each
        // frame
        this.currentX = absX;
        this.currentY = absY;
        this.lineMaxSize = maxXLength;
        if (!loaded) {
            this.originalLineWidth = maxXLength;
            fontType.loadText(this);
            loaded = true;
            if (empty) {
                this.actualTextWidth = 0;
            }
        } else {
            parentScaleFactor = maxXLength / originalLineWidth;
        }
    }

    public void setParentInfo(GuiComponent parent, Vectors3f relPos) {
        this.relativePosition = relPos;
        this.parent = parent;
    }

    public void expandCenter(boolean center) {
        this.expandCenter = center;
    }

    public void setAbsPosition(float x, float y) {
        this.currentX = x;
        this.currentY = y;
    }

    public void setAbsX(float x) {
        this.currentX = x;
    }

    public boolean isJustified() {
        return justify;
    }

    public void setScaleDriver(ValueDriver scaleDriver) {
        this.scaleDriver = scaleDriver;
    }

    public float getRelativeX() {
        return relativePosition.x;
    }

    public void setRelativeX(float x) {
        this.relativePosition.x = x;
        parent.updateTextAbsPos(this);
    }

    public float getRelativeY() {
        return relativePosition.y;
    }

    public void setRelativeY(float y) {
        this.relativePosition.y = y;
        parent.updateTextAbsPos(this);
    }

    public void increaseRelativeX(float dX) {
        this.relativePosition.x += dX;
        parent.updateTextAbsPos(this);
    }

    public void setBorder(ValueDriver driver) {
        this.borderDriver = driver;
        solidBorder = true;
        glowBorder = false;
    }

    public void makeBold() {
        setBorder(new ConstantDriver(fontSize * BOLD_FACTOR));
        setOutlineColour(colour);
    }

    public void setGlowing(ValueDriver driver) {
        solidBorder = false;
        glowBorder = true;
        this.glowDriver = driver;
    }

    public void removeBorder() {
        solidBorder = false;
        glowBorder = false;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setAlphaDriver(ValueDriver driver) {
        this.alphaDriver = driver;
    }

    public void setText(String newText) {
        if (newText.equals(textString)) {
            return;
        }
        this.textString = newText;
        if (loaded) {
            deleteFromMemory();
            empty = newText.length() == 0;
            if (empty) {
                this.actualTextWidth = 0;
            } else {
                fontType.loadText(this);
            }
        }
    }

    public void deleteFromMemory() {
        if (!empty) {
            Loader.deleteVaoFromCache(textMesh);
        }
    }

    public void setColourDriver(Colour peak, Colour end, float length) {
        this.colourDriver = new ColourBounceDriver(colour, peak, end, length);
    }

    public void setOutlineColour(Colour colour) {
        borderColour.setColour(colour);
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public float getScale() {
        return effectScale * parentScaleFactor;
    }

    public int getNumberOfLines() {
        return numberOfLines;
    }

    protected void setNumberOfLines(int number) {
        this.numberOfLines = number;
    }

    public float getHeight() {
        return (float) (originalHeight * getScale());
    }

    public float getActualWidth() {
        return (float) (actualTextWidth * getScale());
    }

    public float getBorderSize() {
        return borderSize;
    }

    public float getGlowSize() {
        if (solidBorder) {
            return calculateAntialiasSize();
        } else if (glowBorder) {
            return glowSize;
        } else {
            return 0;
        }
    }

    public float getCurrentWidth() {
        return originalWidth * effectScale;
    }

    public void update(float delta) {
        effectScale = scaleDriver.update(delta);
        currentAlpha = alphaDriver.update(delta);
        glowSize = glowDriver.update(delta);
        borderSize = borderDriver.update(delta);
        if (colourDriver != null) {
            colourDriver.update(delta);
        }
    }

    public String getTextString() {
        return textString;
    }

    public int[] getClippingBounds() {
        return scissorTestInfo;
    }

    public void setClippingBounds(int[] clippingBounds) {
        this.scissorTestInfo = clippingBounds;
    }

    protected float calculateAntialiasSize() {
        float size = fontSize * getScale() * SCREEN_FACTOR;
        return fontType.getCalculator().calculateAntialiasValue(size);
    }

    protected float calculateEdgeStart() {
        float size = fontSize * getScale() * SCREEN_FACTOR;
        return fontType.getCalculator().calculateEdgeValue(size);
    }

    protected float getTransparency() {
        return currentAlpha;
    }

    public float getCurrentX() {
        return currentX;
    }

    public float getCurrentEffectScale() {
        return effectScale;
    }

    protected Vectors2f getPosition() {
        float scaleFactor = (effectScale - 1f) / 2f;
        float xChange = expandCenter ? scaleFactor * originalWidth : 0;
        float yChange = scaleFactor * (float) TextLoader.LINE_HEIGHT * fontSize * numberOfLines * 1f;
        return new Vectors2f(currentX - xChange, currentY - yChange);
    }

    protected float getTotalBorderSize() {
        if (solidBorder) {
            if (borderSize == 0) {
                return 0;
            } else {
                return calculateEdgeStart() + borderSize;
            }
        } else if (glowBorder) {
            return calculateEdgeStart();
        } else {
            return 0;
        }
    }

    protected int getMesh() {
        return textMesh;
    }

    protected int getVertexCount() {
        return this.vertexCount;
    }

    protected float getFontSize() {
        return fontSize;
    }

    protected Colour getBorderColour() {
        return borderColour;
    }

    protected void setMeshInfo(int vao, int verticesCount, float width, float height) {
        this.textMesh = vao;
        this.vertexCount = verticesCount;
        this.actualTextWidth = width;
        this.originalHeight = height;
    }

    protected void setOriginalWidth(float width) {
        this.originalWidth = width;
    }

    protected boolean isCentered() {
        return centerText;
    }

    protected boolean isIndented() {
        return indent;
    }

    protected boolean isRightAligned() {
        return rightAlignText;
    }

    protected float getMaxLineSize() {
        return originalLineWidth;
    }

}
