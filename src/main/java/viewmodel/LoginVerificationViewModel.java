package viewmodel;

import config.SpecialSettingConfig;
import org.apache.commons.math3.distribution.NormalDistribution;
import usecase.createverification.CreateVerificationViewModel;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.max;


@SuppressWarnings("FieldCanBeLocal")
public class LoginVerificationViewModel extends ViewModel implements CreateVerificationViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String verifyImageLocation = "";
    private int imageAngle = 0;
    private int sliderAngle = 0;
    private int imageAngleRange = 20;
    private long startTime = 0;
    private boolean started = false;

    private final double mean = 1345;
    private final double std = 321;
    private final NormalDistribution timeDistribution = new NormalDistribution(mean, std);

    private final boolean challengeVerify = SpecialSettingConfig.challengeVerificationSetting();
    private final int verifyLevel = SpecialSettingConfig.getVerificationLevel();
    private Timer timer;
    private TimerTask timerTask;

    public LoginVerificationViewModel() {
        super("VerificationView");
        imageAngleRange = max(imageAngleRange - verifyLevel, 1);
    }

    /**
     * Get the location of the verification image.
     * @return the location of the verification image
     */
    public String getVerifyImageLocation() {
        return verifyImageLocation;
    }

    /**
     * Set the angle of the slider.
     * @param angle the angle of the slider
     */
    public void setSliderAngle(int angle) {
        this.sliderAngle = (int) (angle * (1 + verifyLevel * 0.03));
        support.firePropertyChange("imageAngle", null, sliderAngle + imageAngle);
    }

    /**
     * Update the timer for timing the user.
     */
    public void updateTimer() {
        if (!started) {
            this.startTime = System.currentTimeMillis();
            this.started = true;

            if (challengeVerify) {
                timer = new Timer();
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        double dist = (sliderAngle + imageAngle) % 360;
                        imageAngle += (int) ((180d - dist) / (180d / verifyLevel));
                        support.firePropertyChange("imageAngle", null, sliderAngle + imageAngle);
                    }
                };
                timer.schedule(timerTask, 0, 40);
            }
        }

    }

    /**
     * Display the time and verify the angle.
     */
    public void displayThenVerify() {
        long currTime = System.currentTimeMillis();
        if (challengeVerify && timer != null) {
            timer.cancel();
            timerTask.cancel();
        }
        Thread t = new Thread(() -> {
            support.firePropertyChange("displayTime", null, currTime - startTime);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                System.err.println("Verification thread interrupted");
            }
            verifyAngle();
        });
        t.start();
    }

    /**
     * Verify the angle of the slider.
     */
    private void verifyAngle() {
        int absAngle = (sliderAngle + imageAngle) % 360;
        if (absAngle < imageAngleRange || absAngle > 360 - imageAngleRange) {
            support.firePropertyChange("verificationSuccess", null, null);
        } else {
            support.firePropertyChange("verificationFailure", null, null);
        }
    }

    /**
     * Get the color of the time result label.
     * @param time the time
     * @return the color of the label
     */
    public Color getTimeLabelColor(long time) {
        return getColorGradient(getProb(time));
    }

    /**
     * Get the probability of the time.
     * @param time the time
     * @return the probability of the time
     */
    public double getProb(long time) {
        return timeDistribution.cumulativeProbability(time);
    }

    private Color getColorGradient(double prob) {
        Color startColor = new Color(66, 217, 33);
        Color endColor = new Color(240, 174, 19);
        int red = (int) (startColor.getRed() + prob * (endColor.getRed() - startColor.getRed()));
        int green = (int) (startColor.getGreen() + prob * (endColor.getGreen() - startColor.getGreen()));
        int blue = (int) (startColor.getBlue() + prob * (endColor.getBlue() - startColor.getBlue()));
        return new Color(red, green, blue);
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void displayVerificationImage(String imageLocation) {
        this.verifyImageLocation = imageLocation;
        this.imageAngle = (int) Math.round(Math.random() * 360);
        this.started = false;
        support.firePropertyChange("displayVerify", null, imageLocation);
    }
}
