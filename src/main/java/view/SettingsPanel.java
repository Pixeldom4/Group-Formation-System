package view;

import api.texttospeechservice.TextToSpeechService;
import config.SpecialSettingConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

/**
 * A panel for settings.
 */
@SuppressWarnings("FieldCanBeLocal")
public class SettingsPanel extends JPanel {
    private final JCheckBox hoverVoiceCheckBox;
    private final JCheckBox reverseVoiceCheckBox;
    private final boolean reverseVoice = SpecialSettingConfig.reverseVoiceSetting();
    private final boolean enableVoice = TextToSpeechService.isServiceEnabled();

    /**
     * Constructs a SettingsPanel.
     */
    public SettingsPanel() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setLayout(new GridLayout(0, 2));

        hoverVoiceCheckBox = new JCheckBox("Enable Hover Voice", true);
        reverseVoiceCheckBox = new JCheckBox("Enable Reverse Voice", false);

        hoverVoiceCheckBox.addItemListener(e -> TextToSpeechService.setEnablePlayback(e.getStateChange() == ItemEvent.SELECTED));
        reverseVoiceCheckBox.addItemListener(e -> SpecialSettingConfig.setUseReverseVoice(e.getStateChange() == ItemEvent.SELECTED));

        if (enableVoice) {
            add(hoverVoiceCheckBox);
        }

        if (reverseVoice) {
            add(reverseVoiceCheckBox);
        }
    }
}
