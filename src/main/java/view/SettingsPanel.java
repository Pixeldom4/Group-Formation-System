package view;

import api.texttospeechservice.TextToSpeechService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class SettingsPanel extends JPanel {
    private JCheckBox hoverVoiceCheckBox;

    public SettingsPanel() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        hoverVoiceCheckBox = new JCheckBox("Enable Hover Voice", true);

        hoverVoiceCheckBox.addItemListener(e -> {
            TextToSpeechService.setEnablePlayback(e.getStateChange() == ItemEvent.SELECTED);
        });


        add(hoverVoiceCheckBox);
    }
}
