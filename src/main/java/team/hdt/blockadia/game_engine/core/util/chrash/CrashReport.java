package team.hdt.blockadia.game_engine.core.util.chrash;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.hdt.blockadia.game_engine_old.common.util.Util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CrashReport {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String description;
    private final Throwable cause;
    private final CrashReportCategory systemDetailsCategory = new CrashReportCategory(this, "System Details");
    private final List<CrashReportCategory> crashReportSections = Lists.newArrayList();
    private File crashReportFile;
    private boolean firstCategoryInCrashReport = true;
    private StackTraceElement[] stacktrace = new StackTraceElement[0];

    public CrashReport(String p_i1348_1_, Throwable p_i1348_2_) {
        this.description = p_i1348_1_;
        this.cause = p_i1348_2_;
        this.populateEnvironment();
    }

    private void populateEnvironment() {
        this.systemDetailsCategory.addDetail("Blockadia Version", () -> "0.0.1");
        this.systemDetailsCategory.addDetail("Operating System", () -> System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
        this.systemDetailsCategory.addDetail("Java Version", () -> System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
        this.systemDetailsCategory.addDetail("Java VM Version", () -> System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
        this.systemDetailsCategory.addDetail("Memory", () -> {
            Runtime lvt_0_1_ = Runtime.getRuntime();
            long lvt_1_1_ = lvt_0_1_.maxMemory();
            long lvt_3_1_ = lvt_0_1_.totalMemory();
            long lvt_5_1_ = lvt_0_1_.freeMemory();
            long lvt_7_1_ = lvt_1_1_ / 1024L / 1024L;
            long lvt_9_1_ = lvt_3_1_ / 1024L / 1024L;
            long lvt_11_1_ = lvt_5_1_ / 1024L / 1024L;
            return lvt_5_1_ + " bytes (" + lvt_11_1_ + " MB) / " + lvt_3_1_ + " bytes (" + lvt_9_1_ + " MB) up to " + lvt_1_1_ + " bytes (" + lvt_7_1_ + " MB)";
        });
        this.systemDetailsCategory.addDetail("JVM Flags", () -> {
            List<String> lvt_0_1_ = Util.getJvmFlags().collect(Collectors.toList());
            return String.format("%d total; %s", lvt_0_1_.size(), String.join(" ", lvt_0_1_));
        });
    }

    public String getDescription() {
        return this.description;
    }

    public Throwable getCrashCause() {
        return this.cause;
    }

    public void getSectionsInStringBuilder(StringBuilder p_getSectionsInStringBuilder_1_) {
        if ((this.stacktrace == null || this.stacktrace.length <= 0) && !this.crashReportSections.isEmpty()) {
            this.stacktrace = (StackTraceElement[])ArrayUtils.subarray(((CrashReportCategory)this.crashReportSections.get(0)).getStackTrace(), 0, 1);
        }

        if (this.stacktrace != null && this.stacktrace.length > 0) {
            p_getSectionsInStringBuilder_1_.append("-- Head --\n");
            p_getSectionsInStringBuilder_1_.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
            p_getSectionsInStringBuilder_1_.append("Stacktrace:\n");
            StackTraceElement[] var2 = this.stacktrace;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                StackTraceElement lvt_5_1_ = var2[var4];
                p_getSectionsInStringBuilder_1_.append("\t").append("at ").append(lvt_5_1_);
                p_getSectionsInStringBuilder_1_.append("\n");
            }

            p_getSectionsInStringBuilder_1_.append("\n");
        }

        Iterator var6 = this.crashReportSections.iterator();

        while(var6.hasNext()) {
            CrashReportCategory lvt_3_1_ = (CrashReportCategory)var6.next();
            lvt_3_1_.appendToStringBuilder(p_getSectionsInStringBuilder_1_);
            p_getSectionsInStringBuilder_1_.append("\n\n");
        }

        this.systemDetailsCategory.appendToStringBuilder(p_getSectionsInStringBuilder_1_);
    }

    public String getCauseStackTraceOrString() {
        StringWriter lvt_1_1_ = null;
        PrintWriter lvt_2_1_ = null;
        Throwable lvt_3_1_ = this.cause;
        if (lvt_3_1_.getMessage() == null) {
            if (lvt_3_1_ instanceof NullPointerException) {
                lvt_3_1_ = new NullPointerException(this.description);
            } else if (lvt_3_1_ instanceof StackOverflowError) {
                lvt_3_1_ = new StackOverflowError(this.description);
            } else if (lvt_3_1_ instanceof OutOfMemoryError) {
                lvt_3_1_ = new OutOfMemoryError(this.description);
            }

            lvt_3_1_.setStackTrace(this.cause.getStackTrace());
        }

        String var4;
        try {
            lvt_1_1_ = new StringWriter();
            lvt_2_1_ = new PrintWriter(lvt_1_1_);
            lvt_3_1_.printStackTrace(lvt_2_1_);
            var4 = lvt_1_1_.toString();
        } finally {
            IOUtils.closeQuietly(lvt_1_1_);
            IOUtils.closeQuietly(lvt_2_1_);
        }

        return var4;
    }

    public String getCompleteReport() {
        StringBuilder lvt_1_1_ = new StringBuilder();
        lvt_1_1_.append("---- Blockadia Crash Report ----\n");
        lvt_1_1_.append("// ");
        lvt_1_1_.append(getWittyComment());
        lvt_1_1_.append("\n\n");
        lvt_1_1_.append("Time: ");
        lvt_1_1_.append((new SimpleDateFormat()).format(new Date()));
        lvt_1_1_.append("\n");
        lvt_1_1_.append("Description: ");
        lvt_1_1_.append(this.description);
        lvt_1_1_.append("\n\n");
        lvt_1_1_.append(this.getCauseStackTraceOrString());
        lvt_1_1_.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

        for(int lvt_2_1_ = 0; lvt_2_1_ < 87; ++lvt_2_1_) {
            lvt_1_1_.append("-");
        }

        lvt_1_1_.append("\n\n");
        this.getSectionsInStringBuilder(lvt_1_1_);
        return lvt_1_1_.toString();
    }

    public File getFile() {
        return this.crashReportFile;
    }

    public boolean saveToFile(File p_saveToFile_1_) {
        if (this.crashReportFile != null) {
            return false;
        } else {
            if (p_saveToFile_1_.getParentFile() != null) {
                p_saveToFile_1_.getParentFile().mkdirs();
            }

            OutputStreamWriter lvt_2_1_ = null;

            boolean var4;
            try {
                lvt_2_1_ = new OutputStreamWriter(new FileOutputStream(p_saveToFile_1_), StandardCharsets.UTF_8);
                lvt_2_1_.write(this.getCompleteReport());
                this.crashReportFile = p_saveToFile_1_;
                boolean var3 = true;
                return var3;
            } catch (Throwable var8) {
                LOGGER.error("Could not save crash report to {}", p_saveToFile_1_, var8);
                var4 = false;
            } finally {
                IOUtils.closeQuietly(lvt_2_1_);
            }

            return var4;
        }
    }

    public CrashReportCategory getCategory() {
        return this.systemDetailsCategory;
    }

    public CrashReportCategory makeCategory(String p_makeCategory_1_) {
        return this.makeCategoryDepth(p_makeCategory_1_, 1);
    }

    public CrashReportCategory makeCategoryDepth(String p_makeCategoryDepth_1_, int p_makeCategoryDepth_2_) {
        CrashReportCategory lvt_3_1_ = new CrashReportCategory(this, p_makeCategoryDepth_1_);
        if (this.firstCategoryInCrashReport) {
            int lvt_4_1_ = lvt_3_1_.getPrunedStackTrace(p_makeCategoryDepth_2_);
            StackTraceElement[] lvt_5_1_ = this.cause.getStackTrace();
            StackTraceElement lvt_6_1_ = null;
            StackTraceElement lvt_7_1_ = null;
            int lvt_8_1_ = lvt_5_1_.length - lvt_4_1_;
            if (lvt_8_1_ < 0) {
                System.out.println("Negative index in crash report handler (" + lvt_5_1_.length + "/" + lvt_4_1_ + ")");
            }

            if (lvt_5_1_ != null && 0 <= lvt_8_1_ && lvt_8_1_ < lvt_5_1_.length) {
                lvt_6_1_ = lvt_5_1_[lvt_8_1_];
                if (lvt_5_1_.length + 1 - lvt_4_1_ < lvt_5_1_.length) {
                    lvt_7_1_ = lvt_5_1_[lvt_5_1_.length + 1 - lvt_4_1_];
                }
            }

            this.firstCategoryInCrashReport = lvt_3_1_.firstTwoElementsOfStackTraceMatch(lvt_6_1_, lvt_7_1_);
            if (lvt_4_1_ > 0 && !this.crashReportSections.isEmpty()) {
                CrashReportCategory lvt_9_1_ = this.crashReportSections.get(this.crashReportSections.size() - 1);
                lvt_9_1_.trimStackTraceEntriesFromBottom(lvt_4_1_);
            } else if (lvt_5_1_ != null && lvt_5_1_.length >= lvt_4_1_ && 0 <= lvt_8_1_ && lvt_8_1_ < lvt_5_1_.length) {
                this.stacktrace = new StackTraceElement[lvt_8_1_];
                System.arraycopy(lvt_5_1_, 0, this.stacktrace, 0, this.stacktrace.length);
            } else {
                this.firstCategoryInCrashReport = false;
            }
        }

        this.crashReportSections.add(lvt_3_1_);
        return lvt_3_1_;
    }

    private static String getWittyComment() {
        String[] lvt_0_1_ = new String[]{"Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine."};

        try {
            return lvt_0_1_[(int)(Util.nanoTime() % (long)lvt_0_1_.length)];
        } catch (Throwable var2) {
            return "Witty comment unavailable :(";
        }
    }

    public static CrashReport makeCrashReport(Throwable p_makeCrashReport_0_, String p_makeCrashReport_1_) {
        CrashReport lvt_2_2_;
        if (p_makeCrashReport_0_ instanceof ReportedException) {
            lvt_2_2_ = ((ReportedException)p_makeCrashReport_0_).getCrashReport();
        } else {
            lvt_2_2_ = new CrashReport(p_makeCrashReport_1_, p_makeCrashReport_0_);
        }

        return lvt_2_2_;
    }
}
