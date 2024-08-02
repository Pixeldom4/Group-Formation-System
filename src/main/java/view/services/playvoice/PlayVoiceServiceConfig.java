package view.services.playvoice;

public class PlayVoiceServiceConfig {
    private PlayVoiceServiceConfig() {
    }

    public static IPlayVoiceService getPlayVoiceService() {
        return new PlayVoiceService();
    }
}
