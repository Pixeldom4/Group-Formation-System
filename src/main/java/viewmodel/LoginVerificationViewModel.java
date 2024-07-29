package viewmodel;

import usecase.createverification.CreateVerificationViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginVerificationViewModel extends ViewModel implements CreateVerificationViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String verifyImageLocation = "";
    private int imageAngle = 0;
    private int sliderAngle = 0;
    private final int imageAngleRange = 15;

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

    public void verifyAngle() {
        int absAngle = (sliderAngle + imageAngle) % 360;
        if (absAngle < imageAngleRange || absAngle > 360 - imageAngleRange) {
            support.firePropertyChange("verificationSuccess", null, null);
        } else {
            support.firePropertyChange("verificationFailure", null, null);
        }
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
        support.firePropertyChange("displayVerify", null, imageLocation);
    }
}
