package team.hdt.blockadia.bml.interfaces;

/*
 * This class is made by HuskyTheArtist
 * the 21.08.2018 at 09.07
 */
public interface IMod {

    String getDisplayName();

    String getModID();

    String getIdentifier();

    String getVersion();

    default String getContentLocation() {
        return "";
    }

    String getDescription();

    default String[] getCreators() {
        return new String[0];
    }

    default int getSortingPriority() {
        return 0;
    }

    default boolean isDisableable() {
        return true;
    }

    default boolean isRequiredOnClient() {
        return true;
    }

    default boolean isRequiredOnServer() {
        return false;
    }

    default boolean isCompatibleWithModVersion(String version) {
        return version.equals(this.getVersion());
    }

}
