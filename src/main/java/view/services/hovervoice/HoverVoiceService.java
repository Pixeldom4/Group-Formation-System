package view.services.hovervoice;

import api.texttospeechservice.TextToSpeechService;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverVoiceService implements IHoverVoiceService {
    @Override
    public void addHoverVoice(JComponent component, String text) {
        byte[] audio = TextToSpeechService.generateSpeech(text);
        Clip audioClip = TextToSpeechService.convertToClip(audio);
        if (audioClip == null) {
            return;
        }
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                try {
                    audioClip.setFramePosition(0);
                    TextToSpeechService.playAudio(audioClip);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}
