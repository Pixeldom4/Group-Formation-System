package api.texttospeechservice;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class AudioConversionManager implements IAudioConversionManager {

    protected AudioConversionManager() {
    }

    @Override
    public Clip convertTextToAudioClip(byte[] audioData) {
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
            System.err.println("Error converting audio data to Clip: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Clip convertTextReverseClip(byte[] audioData) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
             AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bais)) {

            // Get the audio format and info
            AudioFormat format = audioInputStream.getFormat();
            byte[] reverseAudioData = reverseBytes(audioData, format);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(reverseAudioData);
            AudioInputStream reversedAudioInputStream = new AudioInputStream(byteArrayInputStream, format, reverseAudioData.length / format.getFrameSize());

            Clip clip = AudioSystem.getClip();
            clip.open(reversedAudioInputStream);
            return clip;

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.err.println("Error converting audio data to Clip: " + e.getMessage());
        }
        return null;
    }

    private static byte[] reverseBytes(byte[] audioBytes, AudioFormat format) {
        int frameSize = format.getFrameSize();
        int length = audioBytes.length;
        byte[] reversedBytes = new byte[length];

        for (int i = 0; i < length; i += frameSize) {
            int destPos = length - frameSize - i;
            System.arraycopy(audioBytes, i, reversedBytes, destPos, frameSize);
        }

        return reversedBytes;
    }


}
