package view.services.hovervoice;

import javax.swing.*;

public interface IHoverVoiceService {
    /**
     * Adds a hover voice to the given component.
     * @param component the component to add the hover voice to
     * @param text the text to be spoken when the component is hovered over
     */
    void addHoverVoice(JComponent component, String text);
}
