package api.texttospeechservice;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TextToSpeechService {
    private static TextToSpeechClient textToSpeechClient;
    private static VoiceSelectionParams voice;
    private static AudioConfig audioConfig;
    private static SourceDataLine currentLine;
    private static Thread playbackThread;
    private static boolean enablePlayback = true;

    /**
     * Initializes the TextToSpeechService by creating a TextToSpeechClient and setting the voice and audio configuration.
     * @throws IOException if an error occurs while creating the TextToSpeechClient
     */
    public static void initialize() throws IOException {
        textToSpeechClient = TextToSpeechClient.create();

        // Build the voice request, select the language code and the ssml voice gender
        voice = VoiceSelectionParams.newBuilder()
                .setLanguageCode("en-US")
                .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                .build();

        // Select the type of audio file you want returned
        audioConfig = AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.LINEAR16) // Use LINEAR16 for playback
                .build();
    }

    /**
     * Generates speech from the given text.
     * @param text the text to generate speech from
     * @return the generated speech as a ByteString
     */
    public static ByteString generateSpeech(String text) {
        SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();
        SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
        return response.getAudioContent();
    }

    /**
     * Plays the given audio data. Pauses any currently playing audio from this service.
     * @param audioData the audio data to play
     * @throws IOException if an error occurs while playing the audio
     * @throws UnsupportedAudioFileException if the audio file is not supported
     * @throws LineUnavailableException if a line is unavailable
     */
    public static void playAudio(byte[] audioData) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (!enablePlayback) {
            return;
        }

        // Stop any previously playing audio
        stopCurrentAudio();

        // Start a new thread for playback
        playbackThread = new Thread(() -> {
            try {
                // Convert the byte array to an audio input stream
                try (ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
                     AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bais)) {

                    // Get the audio format and info
                    AudioFormat format = audioInputStream.getFormat();
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

                    // Check if the system supports the data line
                    if (!AudioSystem.isLineSupported(info)) {
                        throw new LineUnavailableException("The system does not support the specified audio format.");
                    }

                    // Obtain and open the line
                    currentLine = (SourceDataLine) AudioSystem.getLine(info);
                    currentLine.open(format);

                    // Start playing the audio
                    currentLine.start();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                        currentLine.write(buffer, 0, bytesRead);
                        if (Thread.currentThread().isInterrupted()){
                            break;
                        }
                    }

                    // Drain and close the line
                    currentLine.stop();
                }
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                e.printStackTrace();
            } finally {
                if (currentLine != null) {
                    currentLine.drain();
                    currentLine.close();
                    currentLine = null;
                }
            }
        });

        playbackThread.start();
    }

    /**
     * Sets whether voice playback for this service is enabled.
     * @param enable whether voice playback is enabled
     */
    public static void setEnablePlayback(boolean enable) {
        enablePlayback = enable;
    }

    private static void stopCurrentAudio() {
        if (playbackThread != null && playbackThread.isAlive()) {
            playbackThread.interrupt();
            try {
                playbackThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            playbackThread = null;
        }

    }

}
