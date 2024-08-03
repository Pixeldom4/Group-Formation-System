package config;

import view.services.playvoice.IPlayVoiceService;
import view.services.playvoice.PlayVoiceService;

public class PlayVoiceServiceConfig {
    private PlayVoiceServiceConfig() {
    }

    public static IPlayVoiceService getPlayVoiceService() {
        return new PlayVoiceService();
    }
}
