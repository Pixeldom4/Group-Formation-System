package view.services.hovervoice;

import api.texttospeechservice.TextToSpeechService;
import config.SpecialSettingConfig;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Map;

public class HoverVoiceService implements IHoverVoiceService {
    private final boolean reverseVoice = SpecialSettingConfig.reverseVoiceSetting();

    @Override
    public void addHoverVoice(JComponent component, String text) {
        byte[] audio = TextToSpeechService.generateSpeech(text);
        Clip audioClip = TextToSpeechService.convertToClip(audio);
        Clip reverseAudioClip = null;

        if (reverseVoice) {
            reverseAudioClip = TextToSpeechService.convertToReverseClip(audio);
        }

        if (audioClip == null) {
            return;
        }

        Clip finalReverseAudioClip = reverseAudioClip;
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (SpecialSettingConfig.useReverseVoice() && finalReverseAudioClip != null) {
                    finalReverseAudioClip.setFramePosition(0);
                    TextToSpeechService.playAudio(finalReverseAudioClip);
                } else {
                    audioClip.setFramePosition(0);
                    TextToSpeechService.playAudio(audioClip);
                }
            }
        });
    }

    @Override
    public void addTableHoverVoice(JTable table, Map<Point, String> textMap) {
        Map<Point, Clip> cellAudioMap = new HashMap<>();
        Map<Point, Clip> cellReverseAudioMap = new HashMap<>();

        for (Map.Entry<Point, String> entry : textMap.entrySet()) {
            byte[] audio = TextToSpeechService.generateSpeech(entry.getValue());
            Clip audioClip = TextToSpeechService.convertToClip(audio);
            Clip reverseAudioClip = null;

            if (reverseVoice) {
                reverseAudioClip = TextToSpeechService.convertToReverseClip(audio);
            }

            if (audioClip != null) {
                cellAudioMap.put(entry.getKey(), audioClip);
            }
            if (reverseAudioClip != null) {
                cellReverseAudioMap.put(entry.getKey(), reverseAudioClip);
            }
        }

        table.addMouseMotionListener(new MouseMotionAdapter() {
            int currentRow = -1;
            int currentColumn = -1;
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());

                if (row == currentRow && column == currentColumn){
                    return;
                }

                if (row >= 0 && column >= 0) {
                    Point cell = new Point(row, column);
                    Clip clip = cellAudioMap.get(cell);
                    Clip reverseClip = cellReverseAudioMap.get(cell);

                    if (clip == null) {
                        return;
                    }

                    if (SpecialSettingConfig.useReverseVoice() && reverseClip != null) {
                        reverseClip.setFramePosition(0);
                        TextToSpeechService.playAudio(reverseClip);
                    } else {
                        clip.setFramePosition(0);
                        TextToSpeechService.playAudio(clip);
                    }
                }
                currentRow = row;
                currentColumn = column;
            }
        });
    }

}
