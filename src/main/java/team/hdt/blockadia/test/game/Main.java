package team.hdt.blockadia.test.game;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.hdt.blockadia.game_engine_old.client.rendering.GameConfiguration;
import team.hdt.blockadia.test.engine.GameEngine;

import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.List;
import java.util.Optional;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        OptionParser lvt_1_1_ = new OptionParser();
        lvt_1_1_.allowsUnrecognizedOptions();
        lvt_1_1_.accepts("demo");
        lvt_1_1_.accepts("fullscreen");
        lvt_1_1_.accepts("checkGlErrors");
        OptionSpec<String> lvt_2_1_ = lvt_1_1_.accepts("server").withRequiredArg();
        OptionSpec<Integer> lvt_3_1_ = lvt_1_1_.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565);
        OptionSpec<File> lvt_4_1_ = lvt_1_1_.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."));
        OptionSpec<File> lvt_5_1_ = lvt_1_1_.accepts("assetsDir").withRequiredArg().ofType(File.class);
        OptionSpec<File> lvt_6_1_ = lvt_1_1_.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        OptionSpec<String> lvt_7_1_ = lvt_1_1_.accepts("proxyHost").withRequiredArg();
        OptionSpec<Integer> lvt_8_1_ = lvt_1_1_.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
        OptionSpec<String> lvt_9_1_ = lvt_1_1_.accepts("proxyUser").withRequiredArg();
        OptionSpec<String> lvt_10_1_ = lvt_1_1_.accepts("proxyPass").withRequiredArg();
        OptionSpec<String> lvt_14_1_ = lvt_1_1_.accepts("version").withRequiredArg().required();
        OptionSpec<Integer> lvt_15_1_ = lvt_1_1_.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854);
        OptionSpec<Integer> lvt_16_1_ = lvt_1_1_.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480);
        OptionSpec<Integer> lvt_17_1_ = lvt_1_1_.accepts("fullscreenWidth").withRequiredArg().ofType(Integer.class);
        OptionSpec<Integer> lvt_18_1_ = lvt_1_1_.accepts("fullscreenHeight").withRequiredArg().ofType(Integer.class);
        OptionSpec<String> lvt_21_1_ = lvt_1_1_.accepts("assetIndex").withRequiredArg();
        OptionSpec<String> lvt_23_1_ = lvt_1_1_.accepts("versionType").withRequiredArg().defaultsTo("release");
        OptionSpec<String> lvt_24_1_ = lvt_1_1_.nonOptions();
        OptionSet lvt_25_1_ = lvt_1_1_.parse(args);
        List<String> lvt_26_1_ = lvt_25_1_.valuesOf(lvt_24_1_);
        if (!lvt_26_1_.isEmpty()) {
            System.out.println("Completely ignored arguments: " + lvt_26_1_);
        }

        String lvt_27_1_ = func_206236_a(lvt_25_1_, lvt_7_1_);
        Proxy lvt_28_1_ = Proxy.NO_PROXY;
        if (lvt_27_1_ != null) {
            try {
                lvt_28_1_ = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(lvt_27_1_, func_206236_a(lvt_25_1_, lvt_8_1_)));
            } catch (Exception var52) {
            }
        }

        final String lvt_29_1_ = func_206236_a(lvt_25_1_, lvt_9_1_);
        final String lvt_30_1_ = func_206236_a(lvt_25_1_, lvt_10_1_);
        if (!lvt_28_1_.equals(Proxy.NO_PROXY) && isNotEmpty(lvt_29_1_) && isNotEmpty(lvt_30_1_)) {
            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(lvt_29_1_, lvt_30_1_.toCharArray());
                }
            });
        }

        int lvt_31_1_ = func_206236_a(lvt_25_1_, lvt_15_1_);
        int lvt_32_1_ = func_206236_a(lvt_25_1_, lvt_16_1_);
        Optional<Integer> lvt_33_1_ = Optional.ofNullable(func_206236_a(lvt_25_1_, lvt_17_1_));
        Optional<Integer> lvt_34_1_ = Optional.ofNullable(func_206236_a(lvt_25_1_, lvt_18_1_));
        boolean lvt_35_1_ = lvt_25_1_.has("fullscreen");
        boolean lvt_36_1_ = lvt_25_1_.has("demo");
        String lvt_37_1_ = func_206236_a(lvt_25_1_, lvt_14_1_);
        String lvt_41_1_ = func_206236_a(lvt_25_1_, lvt_23_1_);
        File lvt_42_1_ = func_206236_a(lvt_25_1_, lvt_4_1_);
        File lvt_43_1_ = lvt_25_1_.has(lvt_5_1_) ? func_206236_a(lvt_25_1_, lvt_5_1_) : new File(lvt_42_1_, "assets/");
        File lvt_44_1_ = lvt_25_1_.has(lvt_6_1_) ? func_206236_a(lvt_25_1_, lvt_6_1_) : new File(lvt_42_1_, "resourcepacks/");
        String lvt_46_1_ = lvt_25_1_.has(lvt_21_1_) ? lvt_21_1_.value(lvt_25_1_) : null;
        String lvt_47_1_ = func_206236_a(lvt_25_1_, lvt_2_1_);
        Integer lvt_48_1_ = func_206236_a(lvt_25_1_, lvt_3_1_);
        GameConfiguration lvt_50_1_ = new GameConfiguration(new GameConfiguration.DisplayInformation(lvt_31_1_, lvt_32_1_, lvt_33_1_, lvt_34_1_, lvt_35_1_), new GameConfiguration.FolderInformation(lvt_42_1_, lvt_44_1_, lvt_43_1_, lvt_46_1_), new GameConfiguration.GameInformation(lvt_36_1_, lvt_37_1_, lvt_41_1_), new GameConfiguration.ServerInformation(lvt_47_1_, lvt_48_1_));
        Thread.currentThread().setName("Client thread");
        (new GameEngine(lvt_50_1_)).run();
    }

    private static <T> T func_206236_a(OptionSet p_206236_0_, OptionSpec<T> p_206236_1_) {
        try {
            return p_206236_0_.valueOf(p_206236_1_);
        } catch (Throwable var5) {
            if (p_206236_1_ instanceof ArgumentAcceptingOptionSpec) {
                ArgumentAcceptingOptionSpec<T> lvt_3_1_ = (ArgumentAcceptingOptionSpec)p_206236_1_;
                List<T> lvt_4_1_ = lvt_3_1_.defaultValues();
                if (!lvt_4_1_.isEmpty()) {
                    return lvt_4_1_.get(0);
                }
            }

            throw var5;
        }
    }

    private static boolean isNotEmpty(String p_isNotEmpty_0_) {
        return p_isNotEmpty_0_ != null && !p_isNotEmpty_0_.isEmpty();
    }

    static {
        System.setProperty("java.awt.headless", "true");
    }

}