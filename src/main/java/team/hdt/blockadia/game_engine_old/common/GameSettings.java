//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package team.hdt.blockadia.game_engine_old.common;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.hdt.blockadia.game_engine_old.client.rendering.VideoMode;
import team.hdt.blockadia.game_engine_old.client.resourceProcessing.I18n;
import team.hdt.blockadia.game_engine_old.common.util.math.Maths;
import team.hdt.blockadia.test.engine.GameEngine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GameSettings {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new Gson();
    private static final String[] PARTICLES = new String[]{"options.particles.all", "options.particles.decreased", "options.particles.minimal"};
    private static final String[] AMBIENT_OCCLUSIONS = new String[]{"options.ao.off", "options.ao.min", "options.ao.max"};
    private static final String[] CLOUDS_TYPES = new String[]{"options.off", "options.clouds.fast", "options.clouds.fancy"};
    private static final String[] ATTACK_INDICATORS = new String[]{"options.off", "options.attack.crosshair", "options.attack.hotbar"};
    public double mouseSensitivity = 0.5D;
    public boolean invertMouse;
    public int renderDistanceChunks = -1;
    public boolean viewBobbing = true;
    public boolean fboEnable = true;
    public int limitFramerate = 120;
    public int clouds = 2;
    public boolean fancyGraphics = true;
    public int ambientOcclusion = 2;
    public List<String> resourcePacks = Lists.newArrayList();
    public List<String> incompatibleResourcePacks = Lists.newArrayList();
    public boolean chatColours;
    public boolean chatLinks;
    public boolean chatLinksPrompt;
    public double chatOpacity;
    public boolean snooperEnabled;
    public boolean fullScreen;
    public boolean enableVsync;
    public boolean useVbo;
    public boolean reducedDebugInfo;
    public boolean hideServerAddress;
    public boolean advancedItemTooltips;
    public boolean pauseOnLostFocus;
    public boolean touchscreen;
    public int overrideWidth;
    public int overrideHeight;
    public boolean heldItemTooltips;
    public double chatScale;
    public double chatWidth;
    public double chatHeightUnfocused;
    public double chatHeightFocused;
    public int mipmapLevels;
    public boolean useNativeTransport;
    public boolean entityShadows;
    public int attackIndicator;
    public boolean enableWeakAttacks;
    public boolean showSubtitles;
    public boolean realmsNotifications;
    public boolean autoJump;
    public boolean autoSuggestions;
    public int biomeBlendRadius;
    public double mouseWheelSensitivity;
    public int glDebugVerbosity;
    private File optionsFile;
    public String lastServer;
    public double fovSetting;
    public double gammaSetting;
    public float saturation;
    public int guiScale;
    public int particleSetting;
    public int narrator;
    public String language;
    public boolean forceUnicodeFont;
    public GameEngine ge;

    public String gameTitle;

    public GameSettings(GameEngine p_i46326_1_, File p_i46326_2_) {
        this.chatColours = true;
        this.chatLinks = true;
        this.chatLinksPrompt = true;
        this.chatOpacity = 1.0D;
        this.snooperEnabled = true;
        this.enableVsync = true;
        this.useVbo = true;
        this.pauseOnLostFocus = true;
        this.heldItemTooltips = true;
        this.chatScale = 1.0D;
        this.chatWidth = 1.0D;
        this.chatHeightUnfocused = 0.44366195797920227D;
        this.chatHeightFocused = 1.0D;
        this.mipmapLevels = 4;
        this.useNativeTransport = true;
        this.ge = p_i46326_1_;
        this.entityShadows = true;
        this.attackIndicator = 1;
        this.realmsNotifications = true;
        this.autoJump = true;
        this.autoSuggestions = true;
        this.biomeBlendRadius = 2;
        this.mouseWheelSensitivity = 1.0D;
        this.glDebugVerbosity = 1;
        this.lastServer = "";
        this.fovSetting = 70.0D;
        this.language = "en_us";
        this.optionsFile = new File(p_i46326_2_, "options.txt");
        if (Runtime.getRuntime().maxMemory() >= 1000000000L) {
            GameSettings.Options.RENDER_DISTANCE.setValueMax(32.0F);
        } else {
            GameSettings.Options.RENDER_DISTANCE.setValueMax(16.0F);
        }

        this.renderDistanceChunks = 8;
        this.loadOptions();
    }

    public GameSettings() {
        this.chatColours = true;
        this.chatLinks = true;
        this.chatLinksPrompt = true;
        this.chatOpacity = 1.0D;
        this.snooperEnabled = true;
        this.enableVsync = true;
        this.useVbo = true;
        this.pauseOnLostFocus = true;
        this.heldItemTooltips = true;
        this.chatScale = 1.0D;
        this.chatWidth = 1.0D;
        this.chatHeightUnfocused = 0.44366195797920227D;
        this.chatHeightFocused = 1.0D;
        this.mipmapLevels = 4;
        this.useNativeTransport = true;
        this.entityShadows = true;
        this.attackIndicator = 1;
        this.realmsNotifications = true;
        this.autoJump = true;
        this.autoSuggestions = true;
        this.biomeBlendRadius = 2;
        this.mouseWheelSensitivity = 1.0D;
        this.glDebugVerbosity = 1;
        this.lastServer = "";
        this.fovSetting = 70.0D;
        this.language = "en_us";
    }

    public void setOptionFloatValue(GameSettings.Options p_setOptionFloatValue_1_, double p_setOptionFloatValue_2_) {
        if (p_setOptionFloatValue_1_ == GameSettings.Options.SENSITIVITY) {
            this.mouseSensitivity = p_setOptionFloatValue_2_;
        }

        if (p_setOptionFloatValue_1_ == GameSettings.Options.FOV) {
            this.fovSetting = p_setOptionFloatValue_2_;
        }

        if (p_setOptionFloatValue_1_ == GameSettings.Options.GAMMA) {
            this.gammaSetting = p_setOptionFloatValue_2_;
        }

        if (p_setOptionFloatValue_1_ == GameSettings.Options.FRAMERATE_LIMIT) {
            this.limitFramerate = (int)p_setOptionFloatValue_2_;
        }

        if (p_setOptionFloatValue_1_ == GameSettings.Options.MOUSE_WHEEL_SENSITIVITY) {
            this.mouseWheelSensitivity = p_setOptionFloatValue_2_;
        }

    }

    public void setOptionValue(GameSettings.Options p_setOptionValue_1_, int p_setOptionValue_2_) {
        if (p_setOptionValue_1_ == GameSettings.Options.RENDER_DISTANCE) {
            this.setOptionFloatValue(p_setOptionValue_1_, Maths.clamp((double)(this.renderDistanceChunks + p_setOptionValue_2_), p_setOptionValue_1_.getValueMin(), p_setOptionValue_1_.getValueMax()));
        }

        if (p_setOptionValue_1_ == GameSettings.Options.INVERT_MOUSE) {
            this.invertMouse = !this.invertMouse;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.PARTICLES) {
            this.particleSetting = (this.particleSetting + p_setOptionValue_2_) % 3;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.VIEW_BOBBING) {
            this.viewBobbing = !this.viewBobbing;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.RENDER_CLOUDS) {
            this.clouds = (this.clouds + p_setOptionValue_2_) % 3;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.FBO_ENABLE) {
            this.fboEnable = !this.fboEnable;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.CHAT_COLOR) {
            this.chatColours = !this.chatColours;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.CHAT_LINKS) {
            this.chatLinks = !this.chatLinks;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.CHAT_LINKS_PROMPT) {
            this.chatLinksPrompt = !this.chatLinksPrompt;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.SNOOPER_ENABLED) {
            this.snooperEnabled = !this.snooperEnabled;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.TOUCHSCREEN) {
            this.touchscreen = !this.touchscreen;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.REDUCED_DEBUG_INFO) {
            this.reducedDebugInfo = !this.reducedDebugInfo;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.ENTITY_SHADOWS) {
            this.entityShadows = !this.entityShadows;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.ATTACK_INDICATOR) {
            this.attackIndicator = (this.attackIndicator + p_setOptionValue_2_) % 3;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.SHOW_SUBTITLES) {
            this.showSubtitles = !this.showSubtitles;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.REALMS_NOTIFICATIONS) {
            this.realmsNotifications = !this.realmsNotifications;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.AUTO_JUMP) {
            this.autoJump = !this.autoJump;
        }

        if (p_setOptionValue_1_ == GameSettings.Options.AUTO_SUGGESTIONS) {
            this.autoSuggestions = !this.autoSuggestions;
        }

        this.saveOptions();
    }

    public double getOptionFloatValue(GameSettings.Options p_getOptionFloatValue_1_) {
        if (p_getOptionFloatValue_1_ == GameSettings.Options.BIOME_BLEND_RADIUS) {
            return (double)this.biomeBlendRadius;
        } else if (p_getOptionFloatValue_1_ == GameSettings.Options.FOV) {
            return this.fovSetting;
        } else if (p_getOptionFloatValue_1_ == GameSettings.Options.GAMMA) {
            return this.gammaSetting;
        } else if (p_getOptionFloatValue_1_ == GameSettings.Options.SATURATION) {
            return (double)this.saturation;
        } else if (p_getOptionFloatValue_1_ == GameSettings.Options.SENSITIVITY) {
            return this.mouseSensitivity;
        } else if (p_getOptionFloatValue_1_ == GameSettings.Options.CHAT_OPACITY) {
            return this.chatOpacity;
        } else if (p_getOptionFloatValue_1_ == GameSettings.Options.CHAT_HEIGHT_FOCUSED) {
            return this.chatHeightFocused;
        } else if (p_getOptionFloatValue_1_ == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED) {
            return this.chatHeightUnfocused;
        } else if (p_getOptionFloatValue_1_ == GameSettings.Options.CHAT_SCALE) {
            return this.chatScale;
        } else if (p_getOptionFloatValue_1_ == GameSettings.Options.CHAT_WIDTH) {
            return this.chatWidth;
        } else if (p_getOptionFloatValue_1_ == GameSettings.Options.FRAMERATE_LIMIT) {
            return (double)this.limitFramerate;
        } else if (p_getOptionFloatValue_1_ == GameSettings.Options.MIPMAP_LEVELS) {
            return (double)this.mipmapLevels;
        } else if (p_getOptionFloatValue_1_ == GameSettings.Options.RENDER_DISTANCE) {
            return (double)this.renderDistanceChunks;
        } else {
            return p_getOptionFloatValue_1_ == GameSettings.Options.MOUSE_WHEEL_SENSITIVITY ? this.mouseWheelSensitivity : 0.0D;
        }
    }

    private static String getTranslation(String[] p_getTranslation_0_, int p_getTranslation_1_) {
        if (p_getTranslation_1_ < 0 || p_getTranslation_1_ >= p_getTranslation_0_.length) {
            p_getTranslation_1_ = 0;
        }

        return I18n.format(p_getTranslation_0_[p_getTranslation_1_]);
    }

    public String getKeyBinding(GameSettings.Options p_getKeyBinding_1_) {
        String lvt_2_1_ = I18n.format(p_getKeyBinding_1_.getTranslation()) + ": ";
        if (p_getKeyBinding_1_.isFloat()) {
            double lvt_3_1_ = this.getOptionFloatValue(p_getKeyBinding_1_);
            double lvt_5_1_ = p_getKeyBinding_1_.func_198008_a(lvt_3_1_);
            if (p_getKeyBinding_1_ == GameSettings.Options.SENSITIVITY) {
                if (lvt_5_1_ == 0.0D) {
                    return lvt_2_1_ + I18n.format("options.sensitivity.min");
                } else {
                    return lvt_5_1_ == 1.0D ? lvt_2_1_ + I18n.format("options.sensitivity.max") : lvt_2_1_ + (int)(lvt_5_1_ * 200.0D) + "%";
                }
            } else if (p_getKeyBinding_1_ == GameSettings.Options.BIOME_BLEND_RADIUS) {
                if (lvt_5_1_ == 0.0D) {
                    return lvt_2_1_ + I18n.format("options.off");
                } else {
                    int lvt_7_1_ = this.biomeBlendRadius * 2 + 1;
                    return lvt_2_1_ + lvt_7_1_ + "x" + lvt_7_1_;
                }
            } else if (p_getKeyBinding_1_ == GameSettings.Options.FOV) {
                if (lvt_3_1_ == 70.0D) {
                    return lvt_2_1_ + I18n.format("options.fov.min");
                } else {
                    return lvt_3_1_ == 110.0D ? lvt_2_1_ + I18n.format("options.fov.max") : lvt_2_1_ + (int)lvt_3_1_;
                }
            } else if (p_getKeyBinding_1_ == GameSettings.Options.FRAMERATE_LIMIT) {
                return lvt_3_1_ == p_getKeyBinding_1_.valueMax ? lvt_2_1_ + I18n.format("options.framerateLimit.max") : lvt_2_1_ + I18n.format("options.framerate", (int)lvt_3_1_);
            } else if (p_getKeyBinding_1_ == GameSettings.Options.RENDER_CLOUDS) {
                return lvt_3_1_ == p_getKeyBinding_1_.valueMin ? lvt_2_1_ + I18n.format("options.cloudHeight.min") : lvt_2_1_ + ((int)lvt_3_1_ + 128);
            } else if (p_getKeyBinding_1_ == GameSettings.Options.GAMMA) {
                if (lvt_5_1_ == 0.0D) {
                    return lvt_2_1_ + I18n.format("options.gamma.min");
                } else {
                    return lvt_5_1_ == 1.0D ? lvt_2_1_ + I18n.format("options.gamma.max") : lvt_2_1_ + "+" + (int)(lvt_5_1_ * 100.0D) + "%";
                }
            } else if (p_getKeyBinding_1_ == GameSettings.Options.SATURATION) {
                return lvt_2_1_ + (int)(lvt_5_1_ * 400.0D) + "%";
            } else if (p_getKeyBinding_1_ == GameSettings.Options.CHAT_OPACITY) {
                return lvt_2_1_ + (int)(lvt_5_1_ * 90.0D + 10.0D) + "%";
            } else if (p_getKeyBinding_1_ == GameSettings.Options.RENDER_DISTANCE) {
                return lvt_2_1_ + I18n.format("options.chunks", (int)lvt_3_1_);
            } else if (p_getKeyBinding_1_ == GameSettings.Options.MOUSE_WHEEL_SENSITIVITY) {
                return lvt_5_1_ == 1.0D ? lvt_2_1_ + I18n.format("options.mouseWheelSensitivity.default") : lvt_2_1_ + "+" + (int)lvt_5_1_ + "." + (int)(lvt_5_1_ * 10.0D) % 10;
            } else if (p_getKeyBinding_1_ == GameSettings.Options.MIPMAP_LEVELS) {
                return lvt_3_1_ == 0.0D ? lvt_2_1_ + I18n.format("options.off") : lvt_2_1_ + (int)lvt_3_1_;
            }/* else if (p_getKeyBinding_1_ == GameSettings.Options.FULLSCREEN_RESOLUTION) {
                return lvt_3_1_ == 0.0D ? lvt_2_1_ + I18n.format("options.fullscreen.current", new Object[0]) : lvt_2_1_ + this.ge.mainWindow.getVideoModeString((int)lvt_3_1_ - 1);
            }*/ else {
                return lvt_5_1_ == 0.0D ? lvt_2_1_ + I18n.format("options.off") : lvt_2_1_ + (int)(lvt_5_1_ * 100.0D) + "%";
            }
        }/* else if (p_getKeyBinding_1_.isBoolean()) {
            boolean lvt_3_2_ = this.getOptionOrdinalValue(p_getKeyBinding_1_);
            return lvt_3_2_ ? lvt_2_1_ + I18n.format("options.on") : lvt_2_1_ + I18n.format("options.off");
        }*/ else if (p_getKeyBinding_1_ == GameSettings.Options.GUI_SCALE) {
            return lvt_2_1_ + (this.guiScale == 0 ? I18n.format("options.guiScale.auto") : this.guiScale);
        } else if (p_getKeyBinding_1_ == GameSettings.Options.PARTICLES) {
            return lvt_2_1_ + getTranslation(PARTICLES, this.particleSetting);
        } else if (p_getKeyBinding_1_ == GameSettings.Options.AMBIENT_OCCLUSION) {
            return lvt_2_1_ + getTranslation(AMBIENT_OCCLUSIONS, this.ambientOcclusion);
        } else if (p_getKeyBinding_1_ == GameSettings.Options.RENDER_CLOUDS) {
            return lvt_2_1_ + getTranslation(CLOUDS_TYPES, this.clouds);
        } else if (p_getKeyBinding_1_ == GameSettings.Options.GRAPHICS) {
            if (this.fancyGraphics) {
                return lvt_2_1_ + I18n.format("options.graphics.fancy");
            } else {
                String lvt_3_3_ = "options.graphics.fast";
                return lvt_2_1_ + I18n.format("options.graphics.fast");
            }
        } else if (p_getKeyBinding_1_ == GameSettings.Options.ATTACK_INDICATOR) {
            return lvt_2_1_ + getTranslation(ATTACK_INDICATORS, this.attackIndicator);
        } else {
            return lvt_2_1_;
        }
    }

    public void loadOptions() {
        /*try {
            if (!this.optionsFile.exists()) {
                return;
            }

            List<String> lvt_1_1_ = IOUtils.readLines(new FileInputStream(this.optionsFile));
            NBTTagCompound lvt_2_1_ = new NBTTagCompound();
            Iterator<String> var3 = lvt_1_1_.iterator();

            String lvt_4_2_;
            while(var3.hasNext()) {
                lvt_4_2_ = var3.next();

                try {
                    Iterator<String> lvt_5_1_ = COLON_SPLITTER.omitEmptyStrings().limit(2).split(lvt_4_2_).iterator();
                    lvt_2_1_.setString((String)lvt_5_1_.next(), (String)lvt_5_1_.next());
                } catch (Exception var10) {
                    LOGGER.warn("Skipping bad option: {}", lvt_4_2_);
                }
            }

            lvt_2_1_ = this.dataFix(lvt_2_1_);
            var3 = lvt_2_1_.getKeySet().iterator();

            while(var3.hasNext()) {
                lvt_4_2_ = var3.next();
                String lvt_5_3_ = lvt_2_1_.getString(lvt_4_2_);

                try {
                    if ("mouseSensitivity".equals(lvt_4_2_)) {
                        this.mouseSensitivity = (double)this.parseFloat(lvt_5_3_);
                    }

                    if ("fov".equals(lvt_4_2_)) {
                        this.fovSetting = (double)(this.parseFloat(lvt_5_3_) * 40.0F + 70.0F);
                    }

                    if ("gamma".equals(lvt_4_2_)) {
                        this.gammaSetting = (double)this.parseFloat(lvt_5_3_);
                    }

                    if ("saturation".equals(lvt_4_2_)) {
                        this.saturation = this.parseFloat(lvt_5_3_);
                    }

                    if ("invertYMouse".equals(lvt_4_2_)) {
                        this.invertMouse = "true".equals(lvt_5_3_);
                    }

                    if ("renderDistance".equals(lvt_4_2_)) {
                        this.renderDistanceChunks = Integer.parseInt(lvt_5_3_);
                    }

                    if ("guiScale".equals(lvt_4_2_)) {
                        this.guiScale = Integer.parseInt(lvt_5_3_);
                    }

                    if ("particles".equals(lvt_4_2_)) {
                        this.particleSetting = Integer.parseInt(lvt_5_3_);
                    }

                    if ("bobView".equals(lvt_4_2_)) {
                        this.viewBobbing = "true".equals(lvt_5_3_);
                    }

                    if ("maxFps".equals(lvt_4_2_)) {
                        this.limitFramerate = Integer.parseInt(lvt_5_3_);
                    }

                    if ("fboEnable".equals(lvt_4_2_)) {
                        this.fboEnable = "true".equals(lvt_5_3_);
                    }

                    if ("fancyGraphics".equals(lvt_4_2_)) {
                        this.fancyGraphics = "true".equals(lvt_5_3_);
                    }

                    if ("ao".equals(lvt_4_2_)) {
                        if ("true".equals(lvt_5_3_)) {
                            this.ambientOcclusion = 2;
                        } else if ("false".equals(lvt_5_3_)) {
                            this.ambientOcclusion = 0;
                        } else {
                            this.ambientOcclusion = Integer.parseInt(lvt_5_3_);
                        }
                    }

                    if ("renderClouds".equals(lvt_4_2_)) {
                        if ("true".equals(lvt_5_3_)) {
                            this.clouds = 2;
                        } else if ("false".equals(lvt_5_3_)) {
                            this.clouds = 0;
                        } else if ("fast".equals(lvt_5_3_)) {
                            this.clouds = 1;
                        }
                    }

                    if ("attackIndicator".equals(lvt_4_2_)) {
                        if ("0".equals(lvt_5_3_)) {
                            this.attackIndicator = 0;
                        } else if ("1".equals(lvt_5_3_)) {
                            this.attackIndicator = 1;
                        } else if ("2".equals(lvt_5_3_)) {
                            this.attackIndicator = 2;
                        }
                    }

                    if ("resourcePacks".equals(lvt_4_2_)) {
                        this.resourcePacks = JsonUtils.gsonDeserialize(GSON, lvt_5_3_, TYPE_LIST_STRING);
                        if (this.resourcePacks == null) {
                            this.resourcePacks = Lists.newArrayList();
                        }
                    }

                    if ("incompatibleResourcePacks".equals(lvt_4_2_)) {
                        this.incompatibleResourcePacks = JsonUtils.gsonDeserialize(GSON, lvt_5_3_, TYPE_LIST_STRING);
                        if (this.incompatibleResourcePacks == null) {
                            this.incompatibleResourcePacks = Lists.newArrayList();
                        }
                    }

                    if ("lastServer".equals(lvt_4_2_)) {
                        this.lastServer = lvt_5_3_;
                    }

                    if ("lang".equals(lvt_4_2_)) {
                        this.language = lvt_5_3_;
                    }

                    if ("chatColors".equals(lvt_4_2_)) {
                        this.chatColours = "true".equals(lvt_5_3_);
                    }

                    if ("chatLinks".equals(lvt_4_2_)) {
                        this.chatLinks = "true".equals(lvt_5_3_);
                    }

                    if ("chatLinksPrompt".equals(lvt_4_2_)) {
                        this.chatLinksPrompt = "true".equals(lvt_5_3_);
                    }

                    if ("chatOpacity".equals(lvt_4_2_)) {
                        this.chatOpacity = (double)this.parseFloat(lvt_5_3_);
                    }

                    if ("snooperEnabled".equals(lvt_4_2_)) {
                        this.snooperEnabled = "true".equals(lvt_5_3_);
                    }

                    if ("fullscreen".equals(lvt_4_2_)) {
                        this.fullScreen = "true".equals(lvt_5_3_);
                    }

                    if ("fullscreenResolution".equals(lvt_4_2_)) {
                        this.fullscreenResolution = lvt_5_3_;
                    }

                    if ("enableVsync".equals(lvt_4_2_)) {
                        this.enableVsync = "true".equals(lvt_5_3_);
                    }

                    if ("useVbo".equals(lvt_4_2_)) {
                        this.useVbo = "true".equals(lvt_5_3_);
                    }

                    if ("hideServerAddress".equals(lvt_4_2_)) {
                        this.hideServerAddress = "true".equals(lvt_5_3_);
                    }

                    if ("advancedItemTooltips".equals(lvt_4_2_)) {
                        this.advancedItemTooltips = "true".equals(lvt_5_3_);
                    }

                    if ("pauseOnLostFocus".equals(lvt_4_2_)) {
                        this.pauseOnLostFocus = "true".equals(lvt_5_3_);
                    }

                    if ("touchscreen".equals(lvt_4_2_)) {
                        this.touchscreen = "true".equals(lvt_5_3_);
                    }

                    if ("overrideHeight".equals(lvt_4_2_)) {
                        this.overrideHeight = Integer.parseInt(lvt_5_3_);
                    }

                    if ("overrideWidth".equals(lvt_4_2_)) {
                        this.overrideWidth = Integer.parseInt(lvt_5_3_);
                    }

                    if ("heldItemTooltips".equals(lvt_4_2_)) {
                        this.heldItemTooltips = "true".equals(lvt_5_3_);
                    }

                    if ("chatHeightFocused".equals(lvt_4_2_)) {
                        this.chatHeightFocused = (double)this.parseFloat(lvt_5_3_);
                    }

                    if ("chatHeightUnfocused".equals(lvt_4_2_)) {
                        this.chatHeightUnfocused = (double)this.parseFloat(lvt_5_3_);
                    }

                    if ("chatScale".equals(lvt_4_2_)) {
                        this.chatScale = (double)this.parseFloat(lvt_5_3_);
                    }

                    if ("chatWidth".equals(lvt_4_2_)) {
                        this.chatWidth = (double)this.parseFloat(lvt_5_3_);
                    }

                    if ("mipmapLevels".equals(lvt_4_2_)) {
                        this.mipmapLevels = Integer.parseInt(lvt_5_3_);
                    }

                    if ("forceUnicodeFont".equals(lvt_4_2_)) {
                        this.forceUnicodeFont = "true".equals(lvt_5_3_);
                    }

                    if ("reducedDebugInfo".equals(lvt_4_2_)) {
                        this.reducedDebugInfo = "true".equals(lvt_5_3_);
                    }

                    if ("useNativeTransport".equals(lvt_4_2_)) {
                        this.useNativeTransport = "true".equals(lvt_5_3_);
                    }

                    if ("entityShadows".equals(lvt_4_2_)) {
                        this.entityShadows = "true".equals(lvt_5_3_);
                    }

                    if ("mainHand".equals(lvt_4_2_)) {
                        this.mainHand = "left".equals(lvt_5_3_) ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
                    }

                    if ("showSubtitles".equals(lvt_4_2_)) {
                        this.showSubtitles = "true".equals(lvt_5_3_);
                    }

                    if ("realmsNotifications".equals(lvt_4_2_)) {
                        this.realmsNotifications = "true".equals(lvt_5_3_);
                    }

                    if ("enableWeakAttacks".equals(lvt_4_2_)) {
                        this.enableWeakAttacks = "true".equals(lvt_5_3_);
                    }

                    if ("autoJump".equals(lvt_4_2_)) {
                        this.autoJump = "true".equals(lvt_5_3_);
                    }

                    if ("narrator".equals(lvt_4_2_)) {
                        this.narrator = Integer.parseInt(lvt_5_3_);
                    }

                    if ("autoSuggestions".equals(lvt_4_2_)) {
                        this.autoSuggestions = "true".equals(lvt_5_3_);
                    }

                    if ("biomeBlendRadius".equals(lvt_4_2_)) {
                        this.biomeBlendRadius = Integer.parseInt(lvt_5_3_);
                    }

                    if ("mouseWheelSensitivity".equals(lvt_4_2_)) {
                        this.mouseWheelSensitivity = (double)this.parseFloat(lvt_5_3_);
                    }

                    if ("glDebugVerbosity".equals(lvt_4_2_)) {
                        this.glDebugVerbosity = Integer.parseInt(lvt_5_3_);
                    }

                    KeyBinding[] var6 = this.keyBindings;
                    int var7 = var6.length;

                    int var8;
                    for(var8 = 0; var8 < var7; ++var8) {
                        KeyBinding lvt_9_1_ = var6[var8];
                        if (lvt_4_2_.equals("key_" + lvt_9_1_.getKeyDescription())) {
                            lvt_9_1_.func_197979_b(InputMappings.getInputByName(lvt_5_3_));
                        }
                    }

                    SoundCategory[] var14 = SoundCategory.values();
                    var7 = var14.length;

                    for(var8 = 0; var8 < var7; ++var8) {
                        SoundCategory lvt_9_2_ = var14[var8];
                        if (lvt_4_2_.equals("soundCategory_" + lvt_9_2_.getName())) {
                            this.soundLevels.put(lvt_9_2_, this.parseFloat(lvt_5_3_));
                        }
                    }

                    EnumPlayerModelParts[] var15 = EnumPlayerModelParts.values();
                    var7 = var15.length;

                    for(var8 = 0; var8 < var7; ++var8) {
                        EnumPlayerModelParts lvt_9_3_ = var15[var8];
                        if (lvt_4_2_.equals("modelPart_" + lvt_9_3_.getPartName())) {
                            this.setModelPartEnabled(lvt_9_3_, "true".equals(lvt_5_3_));
                        }
                    }
                } catch (Exception var11) {
                    LOGGER.warn("Skipping bad option: {}:{}", lvt_4_2_, lvt_5_3_);
                }
            }

            KeyBinding.resetKeyBindingArrayAndHash();
        } catch (Exception var12) {
            LOGGER.error("Failed to load options", var12);
        }*/

    }

    private float parseFloat(String p_parseFloat_1_) {
        if ("true".equals(p_parseFloat_1_)) {
            return 1.0F;
        } else {
            return "false".equals(p_parseFloat_1_) ? 0.0F : Float.parseFloat(p_parseFloat_1_);
        }
    }

    public void saveOptions() {
        PrintWriter lvt_1_1_ = null;

        try {
            lvt_1_1_ = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFile), StandardCharsets.UTF_8));
            lvt_1_1_.println("version:1519");
            lvt_1_1_.println("invertYMouse:" + this.invertMouse);
            lvt_1_1_.println("mouseSensitivity:" + this.mouseSensitivity);
            lvt_1_1_.println("fov:" + (this.fovSetting - 70.0D) / 40.0D);
            lvt_1_1_.println("gamma:" + this.gammaSetting);
            lvt_1_1_.println("saturation:" + this.saturation);
            lvt_1_1_.println("renderDistance:" + this.renderDistanceChunks);
            lvt_1_1_.println("guiScale:" + this.guiScale);
            lvt_1_1_.println("particles:" + this.particleSetting);
            lvt_1_1_.println("bobView:" + this.viewBobbing);
            lvt_1_1_.println("maxFps:" + this.limitFramerate);
            lvt_1_1_.println("fboEnable:" + this.fboEnable);
            lvt_1_1_.println("fancyGraphics:" + this.fancyGraphics);
            lvt_1_1_.println("ao:" + this.ambientOcclusion);
            lvt_1_1_.println("biomeBlendRadius:" + this.biomeBlendRadius);
            switch(this.clouds) {
            case 0:
                lvt_1_1_.println("renderClouds:false");
                break;
            case 1:
                lvt_1_1_.println("renderClouds:fast");
                break;
            case 2:
                lvt_1_1_.println("renderClouds:true");
            }

            lvt_1_1_.println("resourcePacks:" + GSON.toJson(this.resourcePacks));
            lvt_1_1_.println("incompatibleResourcePacks:" + GSON.toJson(this.incompatibleResourcePacks));
            lvt_1_1_.println("lastServer:" + this.lastServer);
            lvt_1_1_.println("lang:" + this.language);
            lvt_1_1_.println("chatColors:" + this.chatColours);
            lvt_1_1_.println("chatLinks:" + this.chatLinks);
            lvt_1_1_.println("chatLinksPrompt:" + this.chatLinksPrompt);
            lvt_1_1_.println("chatOpacity:" + this.chatOpacity);
            lvt_1_1_.println("snooperEnabled:" + this.snooperEnabled);
            lvt_1_1_.println("fullscreen:" + this.fullScreen);
            if (this.ge.mainWindow.getVideoMode().isPresent()) {
                lvt_1_1_.println("fullscreenResolution:" + ((VideoMode)this.ge.mainWindow.getVideoMode().get()).getSettingsString());
            }

            lvt_1_1_.println("enableVsync:" + this.enableVsync);
            lvt_1_1_.println("useVbo:" + this.useVbo);
            lvt_1_1_.println("hideServerAddress:" + this.hideServerAddress);
            lvt_1_1_.println("advancedItemTooltips:" + this.advancedItemTooltips);
            lvt_1_1_.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
            lvt_1_1_.println("touchscreen:" + this.touchscreen);
            lvt_1_1_.println("overrideWidth:" + this.overrideWidth);
            lvt_1_1_.println("overrideHeight:" + this.overrideHeight);
            lvt_1_1_.println("heldItemTooltips:" + this.heldItemTooltips);
            lvt_1_1_.println("chatHeightFocused:" + this.chatHeightFocused);
            lvt_1_1_.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
            lvt_1_1_.println("chatScale:" + this.chatScale);
            lvt_1_1_.println("chatWidth:" + this.chatWidth);
            lvt_1_1_.println("mipmapLevels:" + this.mipmapLevels);
            lvt_1_1_.println("forceUnicodeFont:" + this.forceUnicodeFont);
            lvt_1_1_.println("reducedDebugInfo:" + this.reducedDebugInfo);
            lvt_1_1_.println("useNativeTransport:" + this.useNativeTransport);
            lvt_1_1_.println("entityShadows:" + this.entityShadows);
            lvt_1_1_.println("attackIndicator:" + this.attackIndicator);
            lvt_1_1_.println("showSubtitles:" + this.showSubtitles);
            lvt_1_1_.println("realmsNotifications:" + this.realmsNotifications);
            lvt_1_1_.println("enableWeakAttacks:" + this.enableWeakAttacks);
            lvt_1_1_.println("autoJump:" + this.autoJump);
            lvt_1_1_.println("narrator:" + this.narrator);
            lvt_1_1_.println("autoSuggestions:" + this.autoSuggestions);
            lvt_1_1_.println("mouseWheelSensitivity:" + this.mouseWheelSensitivity);
            lvt_1_1_.println("glDebugVerbosity:" + this.glDebugVerbosity);
        } catch (Exception var9) {
            LOGGER.error("Failed to save options", var9);
        } finally {
            IOUtils.closeQuietly(lvt_1_1_);
        }
    }

    public int shouldRenderClouds() {
        return this.renderDistanceChunks >= 4 ? this.clouds : 0;
    }

    public boolean isUsingNativeTransport() {
        return this.useNativeTransport;
    }

    public enum Options {
        INVERT_MOUSE("options.invertMouse", false, true),
        SENSITIVITY("options.sensitivity", true, false),
        FOV("options.fov", true, false, 30.0D, 110.0D, 1.0F),
        GAMMA("options.gamma", true, false),
        SATURATION("options.saturation", true, false),
        RENDER_DISTANCE("options.renderDistance", true, false, 2.0D, 16.0D, 1.0F),
        VIEW_BOBBING("options.viewBobbing", false, true),
        FRAMERATE_LIMIT("options.framerateLimit", true, false, 10.0D, 260.0D, 10.0F),
        FBO_ENABLE("options.fboEnable", false, true),
        RENDER_CLOUDS("options.renderClouds", false, false),
        GRAPHICS("options.graphics", false, false),
        AMBIENT_OCCLUSION("options.ao", false, false),
        GUI_SCALE("options.guiScale", false, false),
        PARTICLES("options.particles", false, false),
        CHAT_VISIBILITY("options.chat.visibility", false, false),
        CHAT_COLOR("options.chat.color", false, true),
        CHAT_LINKS("options.chat.links", false, true),
        CHAT_OPACITY("options.chat.opacity", true, false),
        CHAT_LINKS_PROMPT("options.chat.links.prompt", false, true),
        SNOOPER_ENABLED("options.snooper", false, true),
        FULLSCREEN_RESOLUTION("options.fullscreen.resolution", true, false, 0.0D, 0.0D, 1.0F),
        USE_FULLSCREEN("options.fullscreen", false, true),
        ENABLE_VSYNC("options.vsync", false, true),
        USE_VBO("options.vbo", false, true),
        TOUCHSCREEN("options.touchscreen", false, true),
        CHAT_SCALE("options.chat.scale", true, false),
        CHAT_WIDTH("options.chat.width", true, false),
        CHAT_HEIGHT_FOCUSED("options.chat.height.focused", true, false),
        CHAT_HEIGHT_UNFOCUSED("options.chat.height.unfocused", true, false),
        MIPMAP_LEVELS("options.mipmapLevels", true, false, 0.0D, 4.0D, 1.0F),
        FORCE_UNICODE_FONT("options.forceUnicodeFont", false, true),
        REDUCED_DEBUG_INFO("options.reducedDebugInfo", false, true),
        ENTITY_SHADOWS("options.entityShadows", false, true),
        MAIN_HAND("options.mainHand", false, false),
        ATTACK_INDICATOR("options.attackIndicator", false, false),
        ENABLE_WEAK_ATTACKS("options.enableWeakAttacks", false, true),
        SHOW_SUBTITLES("options.showSubtitles", false, true),
        REALMS_NOTIFICATIONS("options.realmsNotifications", false, true),
        AUTO_JUMP("options.autoJump", false, true),
        NARRATOR("options.narrator", false, false),
        AUTO_SUGGESTIONS("options.autoSuggestCommands", false, true),
        BIOME_BLEND_RADIUS("options.biomeBlendRadius", true, false, 0.0D, 7.0D, 1.0F),
        MOUSE_WHEEL_SENSITIVITY("options.mouseWheelSensitivity", true, false, 1.0D, 10.0D, 0.5F);

        private final boolean isFloat;
        private final boolean isBoolean;
        private final String translation;
        private final float valueStep;
        private double valueMin;
        private double valueMax;

        public static GameSettings.Options byOrdinal(int p_byOrdinal_0_) {
            GameSettings.Options[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                GameSettings.Options lvt_4_1_ = var1[var3];
                if (lvt_4_1_.getOrdinal() == p_byOrdinal_0_) {
                    return lvt_4_1_;
                }
            }

            return null;
        }

        Options(String p_i1015_3_, boolean p_i1015_4_, boolean p_i1015_5_) {
            this(p_i1015_3_, p_i1015_4_, p_i1015_5_, 0.0D, 1.0D, 0.0F);
        }

        Options(String p_i47986_3_, boolean p_i47986_4_, boolean p_i47986_5_, double p_i47986_6_, double p_i47986_8_, float p_i47986_10_) {
            this.translation = p_i47986_3_;
            this.isFloat = p_i47986_4_;
            this.isBoolean = p_i47986_5_;
            this.valueMin = p_i47986_6_;
            this.valueMax = p_i47986_8_;
            this.valueStep = p_i47986_10_;
        }

        public boolean isFloat() {
            return this.isFloat;
        }

        public boolean isBoolean() {
            return this.isBoolean;
        }

        public int getOrdinal() {
            return this.ordinal();
        }

        public String getTranslation() {
            return this.translation;
        }

        public double getValueMin() {
            return this.valueMin;
        }

        public double getValueMax() {
            return this.valueMax;
        }

        public void setValueMax(float p_setValueMax_1_) {
            this.valueMax = (double)p_setValueMax_1_;
        }

        public double func_198008_a(double p_198008_1_) {
            return Maths.clamp((this.func_198011_c(p_198008_1_) - this.valueMin) / (this.valueMax - this.valueMin), 0.0D, 1.0D);
        }

        public double func_198004_b(double p_198004_1_) {
            return this.func_198011_c(this.valueMin + (this.valueMax - this.valueMin) * Maths.clamp(p_198004_1_, 0.0D, 1.0D));
        }

        public double func_198011_c(double p_198011_1_) {
            p_198011_1_ = this.func_198006_d(p_198011_1_);
            return Maths.clamp(p_198011_1_, this.valueMin, this.valueMax);
        }

        private double func_198006_d(double p_198006_1_) {
            if (this.valueStep > 0.0F) {
                p_198006_1_ = (double)(this.valueStep * (float)Math.round(p_198006_1_ / (double)this.valueStep));
            }

            return p_198006_1_;
        }
    }
}
