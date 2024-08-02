package view.services.hovervoice;

public class HoverVoiceServiceConfig {
    private HoverVoiceServiceConfig() {
    }

    public static IHoverVoiceService getHoverVoiceService() {
        return new HoverVoiceService();
    }
}
