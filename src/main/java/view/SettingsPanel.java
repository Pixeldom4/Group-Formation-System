package view;

import api.texttospeechservice.TextToSpeechService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

/**
 * A panel for settings.
 */
@SuppressWarnings("FieldCanBeLocal")
public class SettingsPanel extends JPanel {
    private final JCheckBox hoverVoiceCheckBox;

    /**
     * Constructs a SettingsPanel.
     */
    public SettingsPanel() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        hoverVoiceCheckBox = new JCheckBox("Enable Hover Voice", true);

        hoverVoiceCheckBox.addItemListener(e -> {
            TextToSpeechService.setEnablePlayback(e.getStateChange() == ItemEvent.SELECTED);
        });


        add(hoverVoiceCheckBox);
    }
}
