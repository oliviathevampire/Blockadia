package team.hdt.blockadia.game_engine_old.common.util.time;

import team.hdt.blockadia.game_engine_old.common.gameManaging.GameManager;
import team.hdt.blockadia.game_engine_old.common.gameManaging.GameState;
import team.hdt.blockadia.game_engine_old.util.BinaryReader;
import team.hdt.blockadia.game_engine_old.util.BinaryWriter;
import team.hdt.blockadia.game_engine_old.util.languages.GameText;

public class Calendar {

    public static final float DAY_LENGTH_SECONDS = 720;
    public static final float HOURS_IN_DAY = 24;
    public static final float HOUR_LENGTH = DAY_LENGTH_SECONDS / HOURS_IN_DAY;
    public static final int QUARTERS = 1;
    public static final int DAYS_PER_QUARTER = 10;
    private static final String TITLE = GameText.getText(683);
    private static final String MESSAGE = GameText.getText(684);
    private static final String YEAR = GameText.getText(892);
    private static final String YEARS = GameText.getText(893);
    private static final String DAY = GameText.getText(894);
    private static final String DAYS = GameText.getText(895);
    private static final String HR = GameText.getText(896);
    private static final String HRS = GameText.getText(897);
    private static final float NOTIFY_TIME = 7.03f / 24f;
    private static final float START_TIME = 7 / 24f;
    private int day;
    private float time;
    private boolean notified;

    private Calendar(int day, float time) {
        this.day = day;
        this.time = time;
        this.notified = isAfterNotifyTime();
    }

    public static Calendar init() {
        return new Calendar(0, START_TIME);
    }

    public static Calendar load(BinaryReader reader) throws Exception {
        int day = reader.readInt();
        float time = reader.readFloat();
        return new Calendar(day, time);
    }

    public static String formatTimeHours(float timeInHours) {
        return formatTimeDays(timeInHours / HOURS_IN_DAY);
    }

    public static String formatTimeDays(float timeInDays) {
        int daysPerYear = DAYS_PER_QUARTER * QUARTERS;
        float years = timeInDays / daysPerYear;
        if (years > 1) {
            int yearInt = ((int) years);
            String text = yearInt + " " + (yearInt != 1 ? YEARS : YEAR);
            if (years < 10) {
                int days = (int) ((years % 1) * daysPerYear);
                if (days != 0) {
                    text += ", " + days + " " + (days != 1 ? DAYS : DAY);
                }
            }
            return text;
        } else if (timeInDays > 1) {
            int daysInt = ((int) timeInDays);
            String text = daysInt + " " + (daysInt != 1 ? DAYS : DAY);
            if (timeInDays < 10) {
                int hours = (int) ((timeInDays % 1) * HOURS_IN_DAY);
                if (hours != 0) {
                    text += ", " + hours + " " + (hours != 1 ? HRS : HR);
                }
            }
            return text;
        } else {
            float hours = timeInDays * HOURS_IN_DAY;
            if (hours < 10) {
                return String.format("%.1f", hours) + " " + HRS;
            } else {
                return ((int) hours) + " " + HRS;
            }
        }
    }

    public void update(float delta) {
        if (GameManager.getGameState() == GameState.SPLASH_SCREEN) {
            return;
        }
        if (!notified && isAfterNotifyTime()) {
            notifyWelcomeMessage();
        }
        time += delta / DAY_LENGTH_SECONDS;
        if (time >= 1) {
            day++;
            time %= 1;
        }
    }

    private void notifyWelcomeMessage() {
        notified = true;
    }

    /**
     * @return The time as a number between 0 and 1, where 0 is the very start of the day (00:00) and 1 is the very END (24:00).
     */
    public float getRawTime() {
        return time;
        //return 12f/24f;
    }

    private boolean isAfterNotifyTime() {
        return day > 0 || time >= NOTIFY_TIME;
    }

    public boolean isNightTime() {
        //TODO so arbitrary!
        return time > 0.9f || time < 0.25f;
    }

    public int getTimeHours() {
        return (int) (time * HOURS_IN_DAY);
    }

    public int getTimeMinutes() {
        return (int) (((time * HOURS_IN_DAY) % 1) * 60);
    }

    public int getTimeMinutesNearest(int interval) {
        int minutes = getTimeMinutes();
        return (minutes / interval) * interval;
    }

    public int getYear() {
        return (day / (DAYS_PER_QUARTER * QUARTERS)) + 1;
    }

    public int getQuarter() {
        return (day / DAYS_PER_QUARTER) % QUARTERS;
    }

    public int getDay() {
        return ((day % DAYS_PER_QUARTER)) + 1;
    }

    public void save(BinaryWriter writer) {
        writer.writeInt(day);
        writer.writeFloat(time);
    }

}
