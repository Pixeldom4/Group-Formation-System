package api.texttospeechservice;

import com.google.cloud.texttospeech.v1.*;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class TextToSpeechService {
    private static TextToSpeechClient textToSpeechClient;
    private static VoiceSelectionParams voice;
    private static AudioConfig audioConfig;
    private static boolean enablePlayback = true;
    private static boolean initialized = false;
    private static final ConcurrentHashMap<Integer, Future<?>> playbackTasks = new ConcurrentHashMap<>();
    private static final ExecutorService playbackExecutor = Executors.newCachedThreadPool();
    private static final AtomicInteger threadIdCounter = new AtomicInteger();
    private static ArrayList<Integer> threadIds = new ArrayList<>();

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

        final int prevThreadId = threadIdCounter.get();
        Thread clearThread = new Thread(() -> {
            if (threadIds.contains(prevThreadId)) {
                threadIds.remove(Integer.valueOf(prevThreadId));
                stopAudio(prevThreadId);
            }
        });
        clearThread.start();

        int threadId = playAudioHelper(clip);
        threadIds.add(threadId);

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
        try (ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
             AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bais)) {

            // Get the audio format and info
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            // Check if the system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                throw new LineUnavailableException("The system does not support the specified audio format.");
            }

            // Obtain and open the clip
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);
            return clip;

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int playAudioHelper(Clip clip) {
        // Generate a unique thread ID
        int threadId = threadIdCounter.incrementAndGet();

        // Submit a new playback task to the executor
        Future<?> playbackTask = playbackExecutor.submit(() -> {
            try {
                // Add a listener to notify when playback is done
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        synchronized (clip) {
                            clip.notify();
                        }
                    }
                });

                // Start playing the audio
                clip.start();

                // Wait for the playback to complete
                synchronized (clip) {
                    clip.wait();
                }
            } catch (InterruptedException e) {
                clip.stop();
                Thread.currentThread().interrupt();
            } finally {
                playbackTasks.remove(threadId);
            }
        });

        // Store the playback task in the map
        playbackTasks.put(threadId, playbackTask);

        return threadId;
    }

    private static void stopAudio(int threadId) {
        Future<?> playbackTask = playbackTasks.get(threadId);
        if (playbackTask != null) {
            playbackTask.cancel(true);
            playbackTasks.remove(threadId);
        }
    }

}
