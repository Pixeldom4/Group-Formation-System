package view;

import api.texttospeechservice.TextToSpeechService;
import config.DataAccessConfig;
import config.SpecialSettingConfig;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

/**
 * A view for setting the settings of the app before starting it.
 */
@SuppressWarnings("FieldCanBeLocal")
public class AppLaunchSettingView extends JFrame {
    private JLabel commonSettingsLabel;
    private JCheckBox enableVoiceCheckBox;
    private JLabel repositoryImplementationLabel;
    private JComboBox<String> repositoryImplementationComboBox;
    private JLabel specialSettingsLabel;
    private JCheckBox reverseVoiceCheckBox;
    private JCheckBox challengeVerifyCheckBox;
    private JSpinner verificationLevelSpinner;

    private JButton startAppButton;

    private final CountDownLatch latch;

    public AppLaunchSettingView(CountDownLatch latch) {
        this.latch = latch;

        this.setLayout(new GridBagLayout());
        initComponents();
        this.setSize(550, 380);
        this.setTitle("App Launch Settings");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initComponents() {
        commonSettingsLabel = new JLabel("Common Settings");
        GridBagConstraints commonSettingsLabelGbc = createGbc(0, 0);
        this.add(commonSettingsLabel, commonSettingsLabelGbc);

        enableVoiceCheckBox = new JCheckBox("Enable voice over (will cause longer loading time)");
        GridBagConstraints enableVoiceCheckBoxGbc = createGbc(0, 1);
        this.add(enableVoiceCheckBox, enableVoiceCheckBoxGbc);

        repositoryImplementationLabel = new JLabel("Repository Implementation");
        GridBagConstraints repositoryImplementationLabelGbc = createGbc(0, 2);
        this.add(repositoryImplementationLabel, repositoryImplementationLabelGbc);

        repositoryImplementationComboBox = new JComboBox<>(new String[]{"Local", "Database"});
        GridBagConstraints repositoryImplementationComboBoxGbc = createGbc(1, 2);
        this.add(repositoryImplementationComboBox, repositoryImplementationComboBoxGbc);

        specialSettingsLabel = new JLabel("Special Settings");
        GridBagConstraints specialSettingsLabelGbc = createGbc(0, 3);
        this.add(specialSettingsLabel, specialSettingsLabelGbc);

        reverseVoiceCheckBox = new JCheckBox("Reverse voice");
        GridBagConstraints reverseVoiceCheckBoxGbc = createGbc(0, 4);
        this.add(reverseVoiceCheckBox, reverseVoiceCheckBoxGbc);

        challengeVerifyCheckBox = new JCheckBox("Challenge verify system");
        GridBagConstraints challengeVerifyCheckBoxGbc = createGbc(0, 5);
        this.add(challengeVerifyCheckBox, challengeVerifyCheckBoxGbc);

        verificationLevelSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        GridBagConstraints verificationLevelSpinnerGbc = createGbc(1, 5);
        this.add(verificationLevelSpinner, verificationLevelSpinnerGbc);

        startAppButton = new JButton("Start App");
        startAppButton.addActionListener(_ -> {
            boolean enableVoice = enableVoiceCheckBox.isSelected();
            boolean reverseVoice = reverseVoiceCheckBox.isSelected();
            boolean challengeVerify = challengeVerifyCheckBox.isSelected();
            int verificationLevel = (int) verificationLevelSpinner.getValue();
            String repositoryImplementation = (String) repositoryImplementationComboBox.getSelectedItem();

            TextToSpeechService.setEnableService(enableVoice);
            SpecialSettingConfig.setReverseVoiceSetting(reverseVoice);
            SpecialSettingConfig.setChallengeVerificationSetting(challengeVerify);
            if (challengeVerify) {
                SpecialSettingConfig.setVerificationLevel(verificationLevel);
            }

            if (repositoryImplementation.equals("Local")) {
                DataAccessConfig.USE_LOCAL = 1;
            } else {
                DataAccessConfig.USE_LOCAL = 0;
            }

            latch.countDown();
            this.setVisible(false);
        });
        GridBagConstraints startAppButtonGbc = createGbc(0, 6);
        this.add(startAppButton, startAppButtonGbc);
    }

    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = (x == 0) ? new Insets(20, 10, 0, 0) : new Insets(20, 10, 0, 10);
        return gbc;
    }

}
