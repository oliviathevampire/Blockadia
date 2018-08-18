//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package team.hdt.blockadia.game_engine_old.client.rendering;

import com.google.common.collect.Lists;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public final class Monitor {
    private final VirtualScreen virtualScreen;
    private final long monitorPointer;
    private final List<VideoMode> videoModes;
    private VideoMode defaultVideoMode;
    private int virtualPosX;
    private int virtualPosY;

    public Monitor(VirtualScreen p_i47673_1_, long p_i47673_2_) {
        this.virtualScreen = p_i47673_1_;
        this.monitorPointer = p_i47673_2_;
        this.videoModes = Lists.newArrayList();
        this.setup();
    }

    public void setup() {
        this.videoModes.clear();
        Buffer lvt_1_1_ = GLFW.glfwGetVideoModes(this.monitorPointer);

        for(int lvt_2_1_ = 0; lvt_2_1_ < lvt_1_1_.limit(); ++lvt_2_1_) {
            lvt_1_1_.position(lvt_2_1_);
            VideoMode lvt_3_1_ = new VideoMode(lvt_1_1_);
            if (lvt_3_1_.getRedBits() >= 8 && lvt_3_1_.getGreenBits() >= 8 && lvt_3_1_.getBlueBits() >= 8) {
                this.videoModes.add(lvt_3_1_);
            }
        }

        int[] lvt_2_2_ = new int[1];
        int[] lvt_3_2_ = new int[1];
        GLFW.glfwGetMonitorPos(this.monitorPointer, lvt_2_2_, lvt_3_2_);
        this.virtualPosX = lvt_2_2_[0];
        this.virtualPosY = lvt_3_2_[0];
        GLFWVidMode lvt_4_1_ = GLFW.glfwGetVideoMode(this.monitorPointer);
        this.defaultVideoMode = new VideoMode(lvt_4_1_);
    }

    public VideoMode getVideoModeOrDefault(Optional<VideoMode> p_getVideoModeOrDefault_1_) {
        if (p_getVideoModeOrDefault_1_.isPresent()) {
            VideoMode lvt_2_1_ = (VideoMode)p_getVideoModeOrDefault_1_.get();
            Iterator var3 = Lists.reverse(this.videoModes).iterator();

            while(var3.hasNext()) {
                VideoMode lvt_4_1_ = (VideoMode)var3.next();
                if (lvt_4_1_.equals(lvt_2_1_)) {
                    return lvt_4_1_;
                }
            }
        }

        return this.getDefaultVideoMode();
    }

    public int getVideoModeOrDefaultIndex(Optional<VideoMode> p_getVideoModeOrDefaultIndex_1_) {
        if (p_getVideoModeOrDefaultIndex_1_.isPresent()) {
            VideoMode lvt_2_1_ = (VideoMode)p_getVideoModeOrDefaultIndex_1_.get();

            for(int lvt_3_1_ = this.videoModes.size() - 1; lvt_3_1_ >= 0; --lvt_3_1_) {
                if (lvt_2_1_.equals(this.videoModes.get(lvt_3_1_))) {
                    return lvt_3_1_;
                }
            }
        }

        return this.videoModes.indexOf(this.getDefaultVideoMode());
    }

    public VideoMode getDefaultVideoMode() {
        return this.defaultVideoMode;
    }

    public int getVirtualPosX() {
        return this.virtualPosX;
    }

    public int getVirtualPosY() {
        return this.virtualPosY;
    }

    public VideoMode getVideoModeFromIndex(int p_getVideoModeFromIndex_1_) {
        return (VideoMode)this.videoModes.get(p_getVideoModeFromIndex_1_);
    }

    public int getVideoModeCount() {
        return this.videoModes.size();
    }

    public long getMonitorPointer() {
        return this.monitorPointer;
    }

    public String toString() {
        return String.format("Monitor[%s %sx%s %s]", this.monitorPointer, this.virtualPosX, this.virtualPosY, this.defaultVideoMode);
    }
}
