//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package team.hdt.blockadia.game_engine_old.common.chrash;

import com.google.common.collect.Lists;
import team.hdt.blockadia.game_engine.core.util.math.BlockPos;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CrashReportCategory {
    private final CrashReport crashReport;
    private final String name;
    private final List<CrashReportCategory.Entry> children = Lists.newArrayList();
    private StackTraceElement[] stackTrace = new StackTraceElement[0];

    public CrashReportCategory(CrashReport p_i1353_1_, String p_i1353_2_) {
        this.crashReport = p_i1353_1_;
        this.name = p_i1353_2_;
    }

    public static String getCoordinateInfo(double p_getCoordinateInfo_0_, double p_getCoordinateInfo_2_, double p_getCoordinateInfo_4_) {
        return String.format(Locale.ROOT, "%.2f,%.2f,%.2f - %s", p_getCoordinateInfo_0_, p_getCoordinateInfo_2_, p_getCoordinateInfo_4_, getCoordinateInfo(new BlockPos((int) p_getCoordinateInfo_0_, (int) p_getCoordinateInfo_2_, (int) p_getCoordinateInfo_4_)));
    }

    public static String getCoordinateInfo(BlockPos p_getCoordinateInfo_0_) {
        return getCoordinateInfo(p_getCoordinateInfo_0_.getX(), p_getCoordinateInfo_0_.getY(), p_getCoordinateInfo_0_.getZ());
    }

    public static String getCoordinateInfo(int p_getCoordinateInfo_0_, int p_getCoordinateInfo_1_, int p_getCoordinateInfo_2_) {
        StringBuilder lvt_3_1_ = new StringBuilder();

        try {
            lvt_3_1_.append(String.format("World: (%d,%d,%d)", p_getCoordinateInfo_0_, p_getCoordinateInfo_1_, p_getCoordinateInfo_2_));
        } catch (Throwable var16) {
            lvt_3_1_.append("(Error finding world loc)");
        }

        lvt_3_1_.append(", ");

        int lvt_4_4_;
        int lvt_5_2_;
        int lvt_6_2_;
        int lvt_7_2_;
        int lvt_8_2_;
        int lvt_9_2_;
        int lvt_10_2_;
        int lvt_11_2_;
        int lvt_12_2_;
        try {
            lvt_4_4_ = p_getCoordinateInfo_0_ >> 4;
            lvt_5_2_ = p_getCoordinateInfo_2_ >> 4;
            lvt_6_2_ = p_getCoordinateInfo_0_ & 15;
            lvt_7_2_ = p_getCoordinateInfo_1_ >> 4;
            lvt_8_2_ = p_getCoordinateInfo_2_ & 15;
            lvt_9_2_ = lvt_4_4_ << 4;
            lvt_10_2_ = lvt_5_2_ << 4;
            lvt_11_2_ = (lvt_4_4_ + 1 << 4) - 1;
            lvt_12_2_ = (lvt_5_2_ + 1 << 4) - 1;
            lvt_3_1_.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", lvt_6_2_, lvt_7_2_, lvt_8_2_, lvt_4_4_, lvt_5_2_, lvt_9_2_, lvt_10_2_, lvt_11_2_, lvt_12_2_));
        } catch (Throwable var15) {
            lvt_3_1_.append("(Error finding chunk loc)");
        }

        lvt_3_1_.append(", ");

        try {
            lvt_4_4_ = p_getCoordinateInfo_0_ >> 9;
            lvt_5_2_ = p_getCoordinateInfo_2_ >> 9;
            lvt_6_2_ = lvt_4_4_ << 5;
            lvt_7_2_ = lvt_5_2_ << 5;
            lvt_8_2_ = (lvt_4_4_ + 1 << 5) - 1;
            lvt_9_2_ = (lvt_5_2_ + 1 << 5) - 1;
            lvt_10_2_ = lvt_4_4_ << 9;
            lvt_11_2_ = lvt_5_2_ << 9;
            lvt_12_2_ = (lvt_4_4_ + 1 << 9) - 1;
            int lvt_13_1_ = (lvt_5_2_ + 1 << 9) - 1;
            lvt_3_1_.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", lvt_4_4_, lvt_5_2_, lvt_6_2_, lvt_7_2_, lvt_8_2_, lvt_9_2_, lvt_10_2_, lvt_11_2_, lvt_12_2_, lvt_13_1_));
        } catch (Throwable var14) {
            lvt_3_1_.append("(Error finding world loc)");
        }

        return lvt_3_1_.toString();
    }

    public void addDetail(String p_addDetail_1_, ICrashReportDetail<String> p_addDetail_2_) {
        try {
            this.addCrashSection(p_addDetail_1_, p_addDetail_2_.call());
        } catch (Throwable var4) {
            this.addCrashSectionThrowable(p_addDetail_1_, var4);
        }

    }

    public void addCrashSection(String p_addCrashSection_1_, Object p_addCrashSection_2_) {
        this.children.add(new CrashReportCategory.Entry(p_addCrashSection_1_, p_addCrashSection_2_));
    }

    public void addCrashSectionThrowable(String p_addCrashSectionThrowable_1_, Throwable p_addCrashSectionThrowable_2_) {
        this.addCrashSection(p_addCrashSectionThrowable_1_, p_addCrashSectionThrowable_2_);
    }

    public int getPrunedStackTrace(int p_getPrunedStackTrace_1_) {
        StackTraceElement[] lvt_2_1_ = Thread.currentThread().getStackTrace();
        if (lvt_2_1_.length <= 0) {
            return 0;
        } else {
            this.stackTrace = new StackTraceElement[lvt_2_1_.length - 3 - p_getPrunedStackTrace_1_];
            System.arraycopy(lvt_2_1_, 3 + p_getPrunedStackTrace_1_, this.stackTrace, 0, this.stackTrace.length);
            return this.stackTrace.length;
        }
    }

    public boolean firstTwoElementsOfStackTraceMatch(StackTraceElement p_firstTwoElementsOfStackTraceMatch_1_, StackTraceElement p_firstTwoElementsOfStackTraceMatch_2_) {
        if (this.stackTrace.length != 0 && p_firstTwoElementsOfStackTraceMatch_1_ != null) {
            StackTraceElement lvt_3_1_ = this.stackTrace[0];
            if (lvt_3_1_.isNativeMethod() == p_firstTwoElementsOfStackTraceMatch_1_.isNativeMethod() && lvt_3_1_.getClassName().equals(p_firstTwoElementsOfStackTraceMatch_1_.getClassName()) && lvt_3_1_.getFileName().equals(p_firstTwoElementsOfStackTraceMatch_1_.getFileName()) && lvt_3_1_.getMethodName().equals(p_firstTwoElementsOfStackTraceMatch_1_.getMethodName())) {
                if (p_firstTwoElementsOfStackTraceMatch_2_ != null != this.stackTrace.length > 1) {
                    return false;
                } else if (p_firstTwoElementsOfStackTraceMatch_2_ != null && !this.stackTrace[1].equals(p_firstTwoElementsOfStackTraceMatch_2_)) {
                    return false;
                } else {
                    this.stackTrace[0] = p_firstTwoElementsOfStackTraceMatch_1_;
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void trimStackTraceEntriesFromBottom(int p_trimStackTraceEntriesFromBottom_1_) {
        StackTraceElement[] lvt_2_1_ = new StackTraceElement[this.stackTrace.length - p_trimStackTraceEntriesFromBottom_1_];
        System.arraycopy(this.stackTrace, 0, lvt_2_1_, 0, lvt_2_1_.length);
        this.stackTrace = lvt_2_1_;
    }

    public void appendToStringBuilder(StringBuilder p_appendToStringBuilder_1_) {
        p_appendToStringBuilder_1_.append("-- ").append(this.name).append(" --\n");
        p_appendToStringBuilder_1_.append("Details:");
        Iterator var2 = this.children.iterator();

        while(var2.hasNext()) {
            CrashReportCategory.Entry lvt_3_1_ = (CrashReportCategory.Entry)var2.next();
            p_appendToStringBuilder_1_.append("\n\t");
            p_appendToStringBuilder_1_.append(lvt_3_1_.getKey());
            p_appendToStringBuilder_1_.append(": ");
            p_appendToStringBuilder_1_.append(lvt_3_1_.getValue());
        }

        if (this.stackTrace != null && this.stackTrace.length > 0) {
            p_appendToStringBuilder_1_.append("\nStacktrace:");
            StackTraceElement[] var6 = this.stackTrace;
            int var7 = var6.length;

            for(int var4 = 0; var4 < var7; ++var4) {
                StackTraceElement lvt_5_1_ = var6[var4];
                p_appendToStringBuilder_1_.append("\n\tat ");
                p_appendToStringBuilder_1_.append(lvt_5_1_);
            }
        }

    }

    public StackTraceElement[] getStackTrace() {
        return this.stackTrace;
    }

    static class Entry {
        private final String key;
        private final String value;

        public Entry(String p_i1352_1_, Object p_i1352_2_) {
            this.key = p_i1352_1_;
            if (p_i1352_2_ == null) {
                this.value = "~~NULL~~";
            } else if (p_i1352_2_ instanceof Throwable) {
                Throwable lvt_3_1_ = (Throwable)p_i1352_2_;
                this.value = "~~ERROR~~ " + lvt_3_1_.getClass().getSimpleName() + ": " + lvt_3_1_.getMessage();
            } else {
                this.value = p_i1352_2_.toString();
            }

        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }
    }
}
