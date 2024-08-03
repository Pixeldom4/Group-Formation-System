package view.services.playvoice;

import api.texttospeechservice.TextToSpeechService;

import javax.sound.sampled.Clip;

public class PlayVoiceService implements IPlayVoiceService {
    @Override
    public void playVoice(String text) {
        byte[] voice = TextToSpeechService.generateSpeech(text);
        Clip audioClip = TextToSpeechService.convertToClip(voice);
        try {
            TextToSpeechService.playAudio(audioClip);
        } catch (Exception e) {
            System.err.println("Error playing audio: " + e.getMessage());
        }
    }
}
