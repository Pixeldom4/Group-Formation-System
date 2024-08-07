package api.texttospeechservice;

import javax.sound.sampled.Clip;

public interface IPlayAudioManager {
    /**
     * Plays the given clip. Used by TextToSpeechService.
     * @param clip the clip to play
     */
    void playClip(Clip clip);
}
