package config;

import view.services.hovervoice.HoverVoiceService;
import view.services.hovervoice.IHoverVoiceService;

public class HoverVoiceServiceConfig {
    private HoverVoiceServiceConfig() {
    }

    public static IHoverVoiceService getHoverVoiceService() {
        return new HoverVoiceService();
    }
}
