//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package team.hdt.blockadia.game_engine_old.common.chrash;

public class ReportedException extends RuntimeException {
    private final CrashReport crashReport;

    public ReportedException(CrashReport p_i1356_1_) {
        this.crashReport = p_i1356_1_;
    }

    public CrashReport getCrashReport() {
        return this.crashReport;
    }

    public Throwable getCause() {
        return this.crashReport.getCrashCause();
    }

    public String getMessage() {
        return this.crashReport.getDescription();
    }
}
