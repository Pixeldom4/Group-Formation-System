package view.services.hovervoice;

import api.texttospeechservice.TextToSpeechService;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverVoiceService implements IHoverVoiceService {
    @Override
    public void addHoverVoice(JComponent component, String text) {
        byte[] audio = TextToSpeechService.generateSpeech(text);
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                try {
                    TextToSpeechService.playAudio(audio);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
