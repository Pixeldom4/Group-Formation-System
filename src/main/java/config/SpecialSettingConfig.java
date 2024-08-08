package config;

public class SpecialSettingConfig {
    // These are special settings that add extra features to the program (for fun lol).
    // These features may change the behavior of the program.
    private static boolean reverseVoice = true;
    private static boolean challengeVerfication = true;

    // Variables that can turn some special settings on or off in the app.
    // These variables can be changed by the user in the app's settings.
    // Only works if the respective special setting is enabled.
    private static boolean useReverse;
    private static int verificationLevel;

    // Static block to initialize default values for settings.
    static {
        useReverse = false;
        verificationLevel = 0;
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

    /**
     * Sets whether the reverse voice setting is enabled.
     * @param reverseVoice whether the reverse voice setting is enabled
     */
    public static void setReverseVoiceSetting(boolean reverseVoice) {
        SpecialSettingConfig.reverseVoice = reverseVoice;
    }

    /**
     * Sets whether the challenge verification setting is enabled.
     * @param challengeVerification whether the challenge verification setting is enabled
     */
    public static void setChallengeVerificationSetting(boolean challengeVerification) {
        SpecialSettingConfig.challengeVerfication = challengeVerification;
    }

    /**
     * Returns the verification level.
     * @return the verification level
     */
    public static int getVerificationLevel() {
        return verificationLevel;
    }

    /**
     * Sets the verification level.
     * @param level the verification level
     */
    public static void setVerificationLevel(int level) {
        verificationLevel = level;
    }
}
