//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package team.hdt.blockadia.game_engine_old.common.util;

import com.google.common.collect.Iterators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.hdt.blockadia.game_engine_old.common.util.interfaces.Nullable;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {
    public static LongSupplier nanoTimeSupplier = System::nanoTime;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Pattern RESERVED_WINDOWS_NAMES = Pattern.compile(".*\\.|(?:CON|PRN|AUX|NUL|COM1|COM2|COM3|COM4|COM5|COM6|COM7|COM8|COM9|LPT1|LPT2|LPT3|LPT4|LPT5|LPT6|LPT7|LPT8|LPT9)(?:\\..*)?", 2);

    public static <K, V> Collector<Entry<? extends K, ? extends V>, ?, Map<K, V>> toMapCollector() {
        return Collectors.toMap(Entry::getKey, Entry::getValue);
    }

    public static long milliTime() {
        return nanoTime() / 1000000L;
    }

    public static long nanoTime() {
        return nanoTimeSupplier.getAsLong();
    }

    public static long millisecondsSinceEpoch() {
        return Instant.now().toEpochMilli();
    }

    public static Util.EnumOS getOSType() {
        String lvt_0_1_ = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (lvt_0_1_.contains("win")) {
            return Util.EnumOS.WINDOWS;
        } else if (lvt_0_1_.contains("mac")) {
            return Util.EnumOS.OSX;
        } else if (lvt_0_1_.contains("solaris")) {
            return Util.EnumOS.SOLARIS;
        } else if (lvt_0_1_.contains("sunos")) {
            return Util.EnumOS.SOLARIS;
        } else if (lvt_0_1_.contains("linux")) {
            return Util.EnumOS.LINUX;
        } else {
            return lvt_0_1_.contains("unix") ? Util.EnumOS.LINUX : Util.EnumOS.UNKNOWN;
        }
    }

    public static Stream<String> getJvmFlags() {
        RuntimeMXBean lvt_0_1_ = ManagementFactory.getRuntimeMXBean();
        return lvt_0_1_.getInputArguments().stream().filter((p_211566_0_) -> {
            return p_211566_0_.startsWith("-X");
        });
    }

    public static boolean func_209537_a(Path p_209537_0_) {
        Path lvt_1_1_ = p_209537_0_.normalize();
        return lvt_1_1_.equals(p_209537_0_);
    }

    public static boolean isPathValidForWindows(Path p_isPathValidForWindows_0_) {
        Iterator<Path> var1 = p_isPathValidForWindows_0_.iterator();

        Path lvt_2_1_;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            lvt_2_1_ = var1.next();
        } while(!RESERVED_WINDOWS_NAMES.matcher(lvt_2_1_.toString()).matches());

        return false;
    }

    public static Path func_209535_a(Path p_209535_0_, String p_209535_1_, String p_209535_2_) {
        String lvt_3_1_ = p_209535_1_ + p_209535_2_;
        Path lvt_4_1_ = Paths.get(lvt_3_1_);
        if (lvt_4_1_.endsWith(p_209535_2_)) {
            throw new InvalidPathException(lvt_3_1_, "empty resource name");
        } else {
            return p_209535_0_.resolve(lvt_4_1_);
        }
    }

    @Nullable
    public static <V> V runTask(FutureTask<V> p_runTask_0_, Logger p_runTask_1_) {
        try {
            p_runTask_0_.run();
            return p_runTask_0_.get();
        } catch (ExecutionException var3) {
            p_runTask_1_.fatal("Error executing task", var3);
        } catch (InterruptedException var4) {
            p_runTask_1_.fatal("Error executing task", var4);
        }

        return null;
    }

    public static <T> T getLastElement(List<T> p_getLastElement_0_) {
        return p_getLastElement_0_.get(p_getLastElement_0_.size() - 1);
    }

    public static <T> T func_195647_a(Iterable<T> p_195647_0_, @Nullable T p_195647_1_) {
        Iterator<T> lvt_2_1_ = p_195647_0_.iterator();
        T lvt_3_1_ = lvt_2_1_.next();
        if (p_195647_1_ != null) {
            T lvt_4_1_ = lvt_3_1_;

            while(lvt_4_1_ != p_195647_1_) {
                if (lvt_2_1_.hasNext()) {
                    lvt_4_1_ = lvt_2_1_.next();
                }
            }

            if (lvt_2_1_.hasNext()) {
                return lvt_2_1_.next();
            }
        }

        return lvt_3_1_;
    }

    public static <T> T func_195648_b(Iterable<T> p_195648_0_, @Nullable T p_195648_1_) {
        Iterator<T> lvt_2_1_ = p_195648_0_.iterator();

        T lvt_3_1_;
        T lvt_4_1_;
        for(lvt_3_1_ = null; lvt_2_1_.hasNext(); lvt_3_1_ = lvt_4_1_) {
            lvt_4_1_ = lvt_2_1_.next();
            if (lvt_4_1_ == p_195648_1_) {
                if (lvt_3_1_ == null) {
                    lvt_3_1_ = lvt_2_1_.hasNext() ? Iterators.getLast(lvt_2_1_) : p_195648_1_;
                }
                break;
            }
        }

        return lvt_3_1_;
    }

    public static <T> T get(Supplier<T> p_get_0_) {
        return p_get_0_.get();
    }

    public static <T> T acceptAndReturn(T p_acceptAndReturn_0_, Consumer<T> p_acceptAndReturn_1_) {
        p_acceptAndReturn_1_.accept(p_acceptAndReturn_0_);
        return p_acceptAndReturn_0_;
    }

    public static enum EnumOS {
        LINUX,
        SOLARIS,
        WINDOWS {
            protected String[] getOpenCommandLine(URL p_getOpenCommandLine_1_) {
                return new String[]{"rundll32", "url.dll,FileProtocolHandler", p_getOpenCommandLine_1_.toString()};
            }
        },
        OSX {
            protected String[] getOpenCommandLine(URL p_getOpenCommandLine_1_) {
                return new String[]{"open", p_getOpenCommandLine_1_.toString()};
            }
        },
        UNKNOWN;

        private EnumOS() {
        }

        /**Warning:(205, 48) java: readLines(java.io.InputStream) in org.apache.commons.io.IOUtils has been deprecated*/
       /* public void openURL(URL p_openURL_1_) {
            try {
                Process lvt_2_1_ = AccessController.doPrivileged((PrivilegedExceptionAction<Process>) () -> Runtime.getRuntime().exec(this.getOpenCommandLine(p_openURL_1_)));
                Iterator<String> var3 = IOUtils.readLines(lvt_2_1_.getErrorStream()).iterator();

                while(var3.hasNext()) {
                    String lvt_4_1_ = var3.next();
                    Util.LOGGER.error(lvt_4_1_);
                }

                lvt_2_1_.getInputStream().close();
                lvt_2_1_.getErrorStream().close();
                lvt_2_1_.getOutputStream().close();
            } catch (IOException | PrivilegedActionException var5) {
                Util.LOGGER.error("Couldn't open url '{}'", p_openURL_1_, var5);
            }

        }*/

        /*public void openURI(URI p_openURI_1_) {
            try {
                this.openURL(p_openURI_1_.toURL());
            } catch (MalformedURLException var3) {
                Util.LOGGER.error("Couldn't open uri '{}'", p_openURI_1_, var3);
            }

        }

        public void openFile(File p_openFile_1_) {
            try {
                this.openURL(p_openFile_1_.toURI().toURL());
            } catch (MalformedURLException var3) {
                Util.LOGGER.error("Couldn't open file '{}'", p_openFile_1_, var3);
            }

        }*/

        protected String[] getOpenCommandLine(URL p_getOpenCommandLine_1_) {
            String lvt_2_1_ = p_getOpenCommandLine_1_.toString();
            if ("file".equals(p_getOpenCommandLine_1_.getProtocol())) {
                lvt_2_1_ = lvt_2_1_.replace("file:", "file://");
            }

            return new String[]{"xdg-open", lvt_2_1_};
        }

        /*public void openUri(String p_openUri_1_) {
            try {
                this.openURL((new URI(p_openUri_1_)).toURL());
            } catch (MalformedURLException | IllegalArgumentException | URISyntaxException var3) {
                Util.LOGGER.error("Couldn't open uri '{}'", p_openUri_1_, var3);
            }

        }*/
    }
}
