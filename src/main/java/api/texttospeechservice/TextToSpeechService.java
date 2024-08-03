package api.texttospeechservice;

import com.google.cloud.texttospeech.v1.*;

import javax.sound.sampled.Clip;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TextToSpeechService {
    private static TextToSpeechClient textToSpeechClient;
    private static VoiceSelectionParams voice;
    private static AudioConfig audioConfig;
    private static boolean enablePlayback = true;
    private static boolean initialized = false;
    private static final ExecutorService playbackExecutor = Executors.newCachedThreadPool();

    private static IPlayAudioManager playAudioManager;
    private static IAudioConversionManager audioConversionManager;

    /**
     * Initializes the TextToSpeechService by creating a TextToSpeechClient and setting the voice and audio configuration.
     */
    public static void initialize() {
        try {
            textToSpeechClient = TextToSpeechClient.create();
        } catch (IOException e) {
            System.out.println("Could not create TextToSpeechClient.");
            return;
        }

        // Build the voice request, select the language code and the ssml voice gender
        voice = VoiceSelectionParams.newBuilder()
                .setLanguageCode("en-US")
                .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                .build();

        // Select the type of audio file you want returned
        audioConfig = AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.LINEAR16) // Use LINEAR16 for playback
                .build();

        playAudioManager = new PlayAudioManager(playbackExecutor);
        audioConversionManager = new AudioConversionManager();

        initialized = true;
        System.out.println("TextToSpeechService initialized.");
    }

    /**
     * Shuts down the TextToSpeechService by closing the TextToSpeechClient.
     */
    public static void shutdown() {
        if (initialized) {
            textToSpeechClient.close();
            initialized = false;
            playbackExecutor.shutdown();
            System.out.println("TextToSpeechService shut down.");
        }
    }

    /**
     * Generates speech from the given text.
     * @param text the text to generate speech from
     * @return the generated speech as a ByteString
     */
    public static byte[] generateSpeech(String text) {
        if (!initialized) {
            return new byte[0];
        }
        SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();
        SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
        return response.getAudioContent().toByteArray();
    }

    /**
     * Plays the given audio data. Pauses any currently playing audio from this service.
     * @param clip the audio data to play
     */
    public static void playAudio(Clip clip) {
        if (!enablePlayback) {
            return;
        }

        playAudioManager.playClip(clip);
    }

    /**
     * Sets whether voice playback for this service is enabled.
     * @param enable whether voice playback is enabled
     */
    public static void setEnablePlayback(boolean enable) {
        enablePlayback = enable;
    }

    /**
     * Converts the given audio data to a Clip object.
     * @param audioData the audio data to convert
     * @return the Clip object
     */
    public static Clip convertToClip(byte[] audioData) {
        return audioConversionManager.convertTextToAudioClip(audioData);
    }

    /**
     * Converts the given audio data to a Clip object in reverse.
     * Used for reverse voice setting.
     * @param audioData the audio data to convert
     * @return the Clip object
     */
    public static Clip convertToReverseClip(byte[] audioData) {
        return audioConversionManager.convertTextReverseClip(audioData);
    }

}
