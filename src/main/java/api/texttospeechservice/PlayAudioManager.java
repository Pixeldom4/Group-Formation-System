package api.texttospeechservice;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayAudioManager implements IPlayAudioManager {
    private final ConcurrentHashMap<Integer, Future<?>> playbackTasks = new ConcurrentHashMap<>();
    private final ExecutorService playbackExecutor;
    private final AtomicInteger threadIdCounter = new AtomicInteger();
    private final ArrayList<Integer> threadIds = new ArrayList<>();

    protected PlayAudioManager(ExecutorService executor) {
        playbackExecutor = executor;
    }

    @Override
    public void playClip(Clip clip) {
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

    private int playAudioHelper(Clip clip) {
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

    private void stopAudio(int threadId) {
        Future<?> playbackTask = playbackTasks.get(threadId);
        if (playbackTask != null) {
            playbackTask.cancel(true);
            playbackTasks.remove(threadId);
        }
    }
}
