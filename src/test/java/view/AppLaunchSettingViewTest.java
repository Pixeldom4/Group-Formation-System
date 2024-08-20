package view;

import api.texttospeechservice.TextToSpeechService;
import config.DataAccessConfig;
import config.SpecialSettingConfig;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class AppLaunchSettingViewTest {

    @Test
    public void testAppLaunchSettingViewComponents() {
        CountDownLatch latch = new CountDownLatch(1);
        AppLaunchSettingView appLaunchSettingView = new AppLaunchSettingView(latch);

        // Verify window title
        Assertions.assertEquals("App Launch Settings", appLaunchSettingView.getTitle());

        // Verify that the main components are not null
        Assertions.assertNotNull(appLaunchSettingView.getContentPane());
        Assertions.assertTrue(appLaunchSettingView.getContentPane() instanceof JPanel);

        // Verify commonSettingsLabel
        JLabel commonSettingsLabel = findComponent(appLaunchSettingView, JLabel.class, "Common Settings");
        Assertions.assertNotNull(commonSettingsLabel);

        // Verify enableVoiceCheckBox
        JCheckBox enableVoiceCheckBox = findComponent(appLaunchSettingView, JCheckBox.class, "Enable voice over (will cause longer loading time)");
        Assertions.assertNotNull(enableVoiceCheckBox);

        // Verify repositoryImplementationComboBox
        JComboBox<String> repositoryImplementationComboBox = findComponent(appLaunchSettingView, JComboBox.class);
        Assertions.assertNotNull(repositoryImplementationComboBox);
        Assertions.assertEquals(2, repositoryImplementationComboBox.getItemCount());

        // Verify specialSettingsLabel
        JLabel specialSettingsLabel = findComponent(appLaunchSettingView, JLabel.class, "Special Settings");
        Assertions.assertNotNull(specialSettingsLabel);

        // Verify reverseVoiceCheckBox
        JCheckBox reverseVoiceCheckBox = findComponent(appLaunchSettingView, JCheckBox.class, "Reverse voice");
        Assertions.assertNotNull(reverseVoiceCheckBox);

        // Verify challengeVerifyCheckBox
        JCheckBox challengeVerifyCheckBox = findComponent(appLaunchSettingView, JCheckBox.class, "Challenge verify system");
        Assertions.assertNotNull(challengeVerifyCheckBox);

        // Verify verificationLevelSpinner
        JSpinner verificationLevelSpinner = findComponent(appLaunchSettingView, JSpinner.class);
        Assertions.assertNotNull(verificationLevelSpinner);

        // Verify startAppButton
        JButton startAppButton = findComponent(appLaunchSettingView, JButton.class, "Start App");
        Assertions.assertNotNull(startAppButton);

        appLaunchSettingView.setVisible(false); // Hide the frame after test
    }

    @Test
    public void testStartAppButtonAction() {
        CountDownLatch latch = new CountDownLatch(1);
        AppLaunchSettingView appLaunchSettingView = new AppLaunchSettingView(latch);

        // Find and set up components
        JCheckBox enableVoiceCheckBox = findComponent(appLaunchSettingView, JCheckBox.class, "Enable voice over (will cause longer loading time)");
        enableVoiceCheckBox.setSelected(true);

        JCheckBox reverseVoiceCheckBox = findComponent(appLaunchSettingView, JCheckBox.class, "Reverse voice");
        reverseVoiceCheckBox.setSelected(true);

        JCheckBox challengeVerifyCheckBox = findComponent(appLaunchSettingView, JCheckBox.class, "Challenge verify system");
        challengeVerifyCheckBox.setSelected(true);

        JSpinner verificationLevelSpinner = findComponent(appLaunchSettingView, JSpinner.class);
        verificationLevelSpinner.setValue(50);

        JComboBox<String> repositoryImplementationComboBox = findComponent(appLaunchSettingView, JComboBox.class);
        repositoryImplementationComboBox.setSelectedItem("Database");

        JButton startAppButton = findComponent(appLaunchSettingView, JButton.class, "Start App");
        startAppButton.doClick();

        // Wait for latch countdown
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify configurations
        Assertions.assertTrue(TextToSpeechService.isServiceEnabled());
        Assertions.assertEquals(50, SpecialSettingConfig.getVerificationLevel());
        Assertions.assertEquals(0, DataAccessConfig.USE_LOCAL); // 0 for Database

        appLaunchSettingView.setVisible(false); // Hide the frame after test
    }

    @SuppressWarnings("unchecked")
    private <T extends Component> T findComponent(Container container, Class<T> clazz, String text) {
        for (Component component : container.getComponents()) {
            if (clazz.isInstance(component)) {
                if (component instanceof JLabel && text.equals(((JLabel) component).getText())) {
                    return clazz.cast(component);
                }
                if (component instanceof JCheckBox && text.equals(((JCheckBox) component).getText())) {
                    return clazz.cast(component);
                }
                if (component instanceof JButton && text.equals(((JButton) component).getText())) {
                    return clazz.cast(component);
                }
            }
            if (component instanceof Container) {
                T found = findComponent((Container) component, clazz, text);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T extends Component> T findComponent(Container container, Class<T> clazz) {
        for (Component component : container.getComponents()) {
            if (clazz.isInstance(component)) {
                return clazz.cast(component);
            }
            if (component instanceof Container) {
                T found = findComponent((Container) component, clazz);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
