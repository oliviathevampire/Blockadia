//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package team.hdt.blockadia.game_engine_old.client.rendering;

import com.google.common.collect.Maps;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;
import team.hdt.blockadia.game_engine.core.util.math.Maths;
import team.hdt.blockadia.test.engine.GameEngine;
import team.hdt.blockadia.test.engine.Window;

import java.util.Iterator;
import java.util.Map;

public final class VirtualScreen implements AutoCloseable {
    private final Map<Long, Monitor> monitorMap = Maps.newHashMap();
    private final Map<Long, Window> unusedMap = Maps.newHashMap();
    private final Map<Window, Monitor> windowToMonitorMap = Maps.newHashMap();

    public VirtualScreen(GameEngine gameEngine) {
        GLFW.glfwSetMonitorCallback(this::onMonitorConfigurationChange);
        PointerBuffer lvt_2_1_ = GLFW.glfwGetMonitors();

        for(int lvt_3_1_ = 0; lvt_3_1_ < lvt_2_1_.limit(); ++lvt_3_1_) {
            long lvt_4_1_ = lvt_2_1_.get(lvt_3_1_);
            this.monitorMap.put(lvt_4_1_, new Monitor(this, lvt_4_1_));
        }

    }

    private void onMonitorConfigurationChange(long p_onMonitorConfigurationChange_1_, int p_onMonitorConfigurationChange_3_) {
        if (p_onMonitorConfigurationChange_3_ == 262145) {
            this.monitorMap.put(p_onMonitorConfigurationChange_1_, new Monitor(this, p_onMonitorConfigurationChange_1_));
        } else if (p_onMonitorConfigurationChange_3_ == 262146) {
            this.monitorMap.remove(p_onMonitorConfigurationChange_1_);
        }

    }

    public Monitor getMonitor(long p_getMonitor_1_) {
        return (Monitor)this.monitorMap.get(p_getMonitor_1_);
    }

    public Monitor getWindowMonitor(Window p_getWindowMonitor_1_) {
        long lvt_2_1_ = GLFW.glfwGetWindowMonitor(p_getWindowMonitor_1_.getWindowHandle());
        if (lvt_2_1_ != 0L) {
            return (Monitor)this.monitorMap.get(lvt_2_1_);
        } else {
            Monitor lvt_4_1_ = (Monitor)this.monitorMap.values().iterator().next();
            int lvt_5_1_ = -1;
            int lvt_6_1_ = p_getWindowMonitor_1_.getWindowX();
            int lvt_7_1_ = lvt_6_1_ + p_getWindowMonitor_1_.getWindowWidth();
            int lvt_8_1_ = p_getWindowMonitor_1_.getWindowY();
            int lvt_9_1_ = lvt_8_1_ + p_getWindowMonitor_1_.getWindowHeight();
            Iterator var10 = this.monitorMap.values().iterator();

            while(var10.hasNext()) {
                Monitor lvt_11_1_ = (Monitor)var10.next();
                int lvt_12_1_ = lvt_11_1_.getVirtualPosX();
                int lvt_13_1_ = lvt_12_1_ + lvt_11_1_.getDefaultVideoMode().getWidth();
                int lvt_14_1_ = lvt_11_1_.getVirtualPosY();
                int lvt_15_1_ = lvt_14_1_ + lvt_11_1_.getDefaultVideoMode().getHeight();
                int lvt_16_1_ = Maths.clamp(lvt_6_1_, lvt_12_1_, lvt_13_1_);
                int lvt_17_1_ = Maths.clamp(lvt_7_1_, lvt_12_1_, lvt_13_1_);
                int lvt_18_1_ = Maths.clamp(lvt_8_1_, lvt_14_1_, lvt_15_1_);
                int lvt_19_1_ = Maths.clamp(lvt_9_1_, lvt_14_1_, lvt_15_1_);
                int lvt_20_1_ = Math.max(0, lvt_17_1_ - lvt_16_1_);
                int lvt_21_1_ = Math.max(0, lvt_19_1_ - lvt_18_1_);
                int lvt_22_1_ = lvt_20_1_ * lvt_21_1_;
                if (lvt_22_1_ > lvt_5_1_) {
                    lvt_4_1_ = lvt_11_1_;
                    lvt_5_1_ = lvt_22_1_;
                }
            }

            if (lvt_4_1_ != this.windowToMonitorMap.get(p_getWindowMonitor_1_)) {
                this.windowToMonitorMap.put(p_getWindowMonitor_1_, lvt_4_1_);
            }

            return lvt_4_1_;
        }
    }

    public Window createWindow(GameEngine engine, GameConfiguration.DisplayInformation p_createWindow_1_, String p_createWindow_2_) {
        return new Window(engine, this, p_createWindow_1_, p_createWindow_2_);
    }

    public void close() {
        GLFWMonitorCallback lvt_1_1_ = GLFW.glfwSetMonitorCallback(null);
        if (lvt_1_1_ != null) {
            lvt_1_1_.free();
        }

    }
}
