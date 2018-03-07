package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.font;

import net.thegaminghuskymc.sandboxgame.engine.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FontFile {

    // charset:
    //
    // ABCDEFGHIJKLMNOPQRSTUVWXYZ
    // abcdefghijklmnopqrstuvwxyz
    // ��������������
    // 1234567890
    // "!`?'.,;:()[]{}<>|/@\^$-%+=#_&~*

    public static final int FONT_WIDTH = 512;
    public static final int FONT_HEIGHT = 512;
    public static final char CURSOR_CHAR = (int) 128;
    /**
     * default line height so every fonts have the same line heights
     */
    private static final int LINE_PER_SCREEN = 16; // number of line per screen
    // for a size of 1.0f
    public static final float LINEHEIGHT = 1 / ((float) LINE_PER_SCREEN - 1) * 2;
    private static final int PAD_TOP = 0;
    private static final int PAD_LEFT = 1;
    private static final int PAD_BOT = 2;
    private static final int PAD_RIGHT = 3;
    private static final int DESIRED_PADDING = 6;
    private static final FontChar DEFAULT_FONT_CHAR = new FontChar();
    private static final char CURSOR_CHAR_APPEARENCE = '|';
    private int[] _padding;
    private int _padding_width;
    private int _padding_height;

    private FontChar[] _chars;
    private int _fontsize;
    private int line_height;

    public FontFile(String filepath) {
        // + 1 for the cursor char (copy of '|' but with no offset
        this._chars = new FontChar[128 + 1];

        File file = new File(filepath);
        if (!file.exists()) {
            Logger.get().log(Logger.Level.WARNING, "Font file doesnt exists: " + filepath);
            return;
        }

        try {
            this.parseFile(file);
            this._chars[CURSOR_CHAR] = this._chars[CURSOR_CHAR_APPEARENCE].clone();
            this._chars[CURSOR_CHAR].xadvance = 0;
            this._chars[CURSOR_CHAR].xoffset = 0;
        } catch (Exception e) {
            Logger.get().log(Logger.Level.WARNING, "Wrong FontFile formatting: " + filepath, e.getLocalizedMessage());
        }
    }

    private void parseFile(File file) throws Exception {
        Map<String, String> values = new HashMap<String, String>();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        this.parseLine(values, reader);
        this._fontsize = Integer.parseInt(values.get("size"));
        String[] pads = values.get("padding").split(",");
        this._padding = new int[4];
        for (int i = 0; i < pads.length; i++) {
            this._padding[i] = Integer.parseInt(pads[i]);
        }
        this._padding_width = this._padding[PAD_LEFT] + this._padding[PAD_RIGHT];
        this._padding_height = this._padding[PAD_TOP] + this._padding[PAD_BOT];

        this.parseLine(values, reader);
        this.line_height = Integer.parseInt(values.get("lineHeight")) - this._padding_height;

        while (this.parseLine(values, reader)) {
            try {
                int id = Integer.parseInt(values.get("id"));
                int uvx = Integer.parseInt(values.get("x"));
                int uvy = Integer.parseInt(values.get("y"));
                int width = Integer.parseInt(values.get("width")) - this._padding_width + 2 * DESIRED_PADDING;
                int height = Integer.parseInt(values.get("height")) - this._padding_height + 2 * DESIRED_PADDING;
                int xoffset = Integer.parseInt(values.get("xoffset")) + this._padding[PAD_LEFT] + DESIRED_PADDING;
                int yoffset = Integer.parseInt(values.get("yoffset")) + this._padding[PAD_TOP] + DESIRED_PADDING;
                int xadvance = Integer.parseInt(values.get("xadvance")) - this._padding_width;

                FontChar fchar = new FontChar();
                fchar.ascii = id;
                fchar.uvx = (uvx + this._padding[PAD_LEFT] - DESIRED_PADDING) / (float) FONT_WIDTH;
                fchar.uvy = (uvy + this._padding[PAD_TOP] - DESIRED_PADDING) / (float) FONT_HEIGHT;
                fchar.width = width;
                fchar.height = height;
                fchar.uvwidth = width / (float) FONT_WIDTH;
                fchar.uvheight = height / (float) FONT_HEIGHT;
                fchar.xoffset = xoffset;
                fchar.yoffset = yoffset;
                fchar.xadvance = xadvance;

                this._chars[id] = fchar;
            } catch (Exception e) {
                continue;
            }
        }
    }

    private boolean parseLine(Map<String, String> values, BufferedReader reader) throws IOException {
        values.clear();
        String line = reader.readLine();

        if (line == null) {
            return (false);
        }
        String[] elements = line.split(" ");
        for (String element : elements) {
            String[] pair = element.split("=");
            if (pair.length != 2) {
                continue;
            }
            values.put(pair[0], pair[1]);
        }
        return (true);
    }

    public FontChar getCharData(char c) {

        if ((int) c < 0 || (int) c >= this._chars.length) {
            return (DEFAULT_FONT_CHAR);
        }

        FontChar ch = this._chars[(int) c];
        if (ch == null) {
            return (DEFAULT_FONT_CHAR);
        }
        return (ch);
    }

    public float getFontSize() {
        return (this._fontsize);
    }

    public final float getLineHeight() {
        return (this.line_height);
    }
}