package view.services.playvoice;

import api.texttospeechservice.TextToSpeechService;
import config.SpecialSettingConfig;

import javax.sound.sampled.Clip;

public class PlayVoiceService implements IPlayVoiceService {
    private final boolean reverseVoice = SpecialSettingConfig.reverseVoiceSetting();

    @Override
    public void playVoice(String text) {
        byte[] voice = TextToSpeechService.generateSpeech(text);
        Clip audioClip = TextToSpeechService.convertToClip(voice);
        Clip reverseAudioClip = null;

        if (reverseVoice) {
            reverseAudioClip = TextToSpeechService.convertToReverseClip(voice);
        }

        try {
            if (SpecialSettingConfig.useReverseVoice() && reverseAudioClip != null) {
                TextToSpeechService.playAudio(reverseAudioClip);
            }
            else {
                TextToSpeechService.playAudio(audioClip);
            }
        } catch (Exception e) {
            System.err.println("Error playing audio: " + e.getMessage());
        }
    }
}
