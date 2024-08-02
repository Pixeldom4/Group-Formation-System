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
            e.printStackTrace();
        }
        return null;
    }
}
