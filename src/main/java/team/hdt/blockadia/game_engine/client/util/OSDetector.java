package team.hdt.blockadia.game_engine.client.util;

public class OSDetector {
    private static String OS;

    public int nonStaticIsOS() {
        OS = System.getProperty("os.name").toLowerCase();
        if (isWindows()) {
            return 1;
        } else if (isMac()) {
            return 2;
        } else if (isUnix()) {
            return 3;
        } else if (isSolaris()) {
            return 4;
        } else {
            return 0;
        }
    }
  	public static int staticIsOS()
  	{
  	  OS = System.getProperty("os.name").toLowerCase();
        if (isWindows()) {
            return 1;
        } else if (isMac()) {
            return 2;
        } else if (isUnix()) {
            return 3;
        } else if (isSolaris()) {
            return 4;
        } else {
            return 0;
        }
  	}
  

    private static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    private static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    private static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

    }

    private static boolean isSolaris() {

        return (OS.indexOf("sunos") >= 0);

    }
}
