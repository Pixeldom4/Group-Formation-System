package viewmodel;

import usecase.createverification.CreateVerificationViewModel;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.apache.commons.math3.distribution.*;


public class LoginVerificationViewModel extends ViewModel implements CreateVerificationViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String verifyImageLocation = "";
    private int imageAngle = 0;
    private int sliderAngle = 0;
    private final int imageAngleRange = 20;
    private long startTime = 0;
    private boolean started = false;

    private final double mean = 1345;
    private final double std = 321;
    private final NormalDistribution timeDistribution = new NormalDistribution(mean, std);

    public LoginVerificationViewModel() {
        super("VerificationView");
    }

    public String getVerifyImageLocation() {
        return verifyImageLocation;
    }

    public void setSliderAngle(int angle) {
        this.sliderAngle = angle;
        support.firePropertyChange("imageAngle", null, sliderAngle + imageAngle);
    }

    public void updateTimer() {
        if (!started) {
            this.startTime = System.currentTimeMillis();
            this.started = true;
        }
    }

    public void displayThenVerify() {
        long currTime = System.currentTimeMillis();
        Thread t = new Thread(() -> {
            support.firePropertyChange("displayTime", null, currTime - startTime);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            verifyAngle();
        });
        t.start();
    }

    public void verifyAngle() {
        int absAngle = (sliderAngle + imageAngle) % 360;
        if (absAngle < imageAngleRange || absAngle > 360 - imageAngleRange) {
            support.firePropertyChange("verificationSuccess", null, null);
        } else {
            support.firePropertyChange("verificationFailure", null, null);
        }
    }

    public Color getTimeLabelColor(long time) {
        return getColorGradient(getProb(time));
    }

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
