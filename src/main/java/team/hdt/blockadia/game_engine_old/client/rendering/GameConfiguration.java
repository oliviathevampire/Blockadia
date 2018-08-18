package team.hdt.blockadia.game_engine_old.client.rendering;

import team.hdt.blockadia.game_engine_old.common.util.interfaces.Nullable;

import java.io.File;
import java.util.Optional;

public class GameConfiguration {
    public final GameConfiguration.DisplayInformation displayInfo;
    public final GameConfiguration.FolderInformation folderInfo;
    public final GameConfiguration.GameInformation gameInfo;
    public final GameConfiguration.ServerInformation serverInfo;

    public GameConfiguration(GameConfiguration.DisplayInformation displayInfo, GameConfiguration.FolderInformation folderInfo, GameConfiguration.GameInformation gameInfo, GameConfiguration.ServerInformation serverInfo) {
        this.displayInfo = displayInfo;
        this.folderInfo = folderInfo;
        this.gameInfo = gameInfo;
        this.serverInfo = serverInfo;
    }

    public static class ServerInformation {
        public final String serverName;
        public final int serverPort;

        public ServerInformation(String serverName, int serverPort) {
            this.serverName = serverName;
            this.serverPort = serverPort;
        }
    }

    public static class FolderInformation {
        public final File gameDir;
        public final File resourcePacksDir;
        public final File assetsDir;
        public final String assetIndex;

        public FolderInformation(File gameDir, File resourcePacksDir, File assetsDir, @Nullable String assetsIndex) {
            this.gameDir = gameDir;
            this.resourcePacksDir = resourcePacksDir;
            this.assetsDir = assetsDir;
            this.assetIndex = assetsIndex;
        }

    }

    public static class DisplayInformation {
        public final int width;
        public final int height;
        public final Optional<Integer> fullscreenWidth;
        public final Optional<Integer> fullscreenHeight;
        public final boolean fullscreen;

        public DisplayInformation(int width, int height, Optional<Integer> fullscreenWidth, Optional<Integer> fullscreenHeight, boolean fullscreen) {
            this.width = width;
            this.height = height;
            this.fullscreenWidth = fullscreenWidth;
            this.fullscreenHeight = fullscreenHeight;
            this.fullscreen = fullscreen;
        }
    }

    public static class GameInformation {
        public final boolean isDemo;
        public final String version;
        public final String versionType;

        public GameInformation(boolean p_i46801_1_, String p_i46801_2_, String p_i46801_3_) {
            this.isDemo = p_i46801_1_;
            this.version = p_i46801_2_;
            this.versionType = p_i46801_3_;
        }
    }
}
