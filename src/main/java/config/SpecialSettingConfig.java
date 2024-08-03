package config;

public class SpecialSettingConfig {
    // These are special settings that add extra features to the program (for fun lol).
    // These features may change the behavior of the program.
    private static final boolean reverseVoice = true;
    private static final boolean challengeVerfication = true;

    // Variables that can turn some special settings on or off in the app.
    // These variables can be changed by the user in the app's settings.
    // Only works if the respective special setting is enabled.
    private static boolean useReverse;

    // Static block to initialize default values for settings.
    static {
        useReverse = false;
    }

    /**
     * Returns whether the reverse voice setting is enabled.
     * @return whether the reverse voice setting is enabled
     */
    public static boolean reverseVoiceSetting() {
        return reverseVoice;
    }

    /**
     * Returns whether the challenge verification setting is enabled.
     * @return whether the challenge verification setting is enabled
     */
    public static boolean challengeVerificationSetting() {
        return challengeVerfication;
    }

    /**
     * Returns whether to use reverse voice.
     * @return whether to use reverse voice
     */
    public static boolean useReverseVoice() {
        return useReverse;
    }

    /**
     * Sets whether to use reverse voice.
     * @param useReverseVoice whether to use reverse voice
     */
    public static void setUseReverseVoice(boolean useReverseVoice) {
        useReverse = useReverseVoice;
    }
}
