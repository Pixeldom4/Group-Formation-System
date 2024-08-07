package api.texttospeechservice;

import javax.sound.sampled.Clip;

public interface IAudioConversionManager {
    /**
     * Converts the given text to an audio clip. Used by TextToSpeechService.
     * @param audioData the audio data to be converted
     * @return the audio clip
     */
    Clip convertTextToAudioClip(byte[] audioData);

    /**
     * Converts the given text to an audio clip in reverse. Used by TextToSpeechService.
     * Used for reverse voice setting.
     * @param audioData the audio data to be converted
     * @return the audio clip
     */
    Clip convertTextReverseClip(byte[] audioData);
}
