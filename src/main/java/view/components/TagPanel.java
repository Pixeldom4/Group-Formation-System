package view.components;

import view.services.hovervoice.HoverVoiceServiceConfig;
import view.services.hovervoice.IHoverVoiceService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashSet;

public class TagPanel extends JPanel {
    private final HashSet<String> tags = new HashSet<>();
    private final IHoverVoiceService hoverVoiceService;

    public TagPanel(){
        this.hoverVoiceService = HoverVoiceServiceConfig.getHoverVoiceService();
    }

    /**
     * Adds a tag to the panel.
     * @param text the tag to add
     */
    public void addTagToPanel(String text) {
        if (text.isEmpty()) {
            return;
        }

        if (tags.contains(text)) {
            return;
        }

        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JButton removeButton = new JButton("x");

        JPanel tag = new JPanel();
        tag.setBorder(new EmptyBorder(0, 10, 0, 10));
        tag.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;

        constraints.gridx = 0;
        constraints.weightx = 0.8;

        hoverVoiceService.addHoverVoice(label, "Tag: " + text);

        tag.add(label, constraints);

        constraints.gridx = 1;
        constraints.weightx = 0.2;

        hoverVoiceService.addHoverVoice(removeButton, "Press to remove " + text + " tag");

        tag.add(removeButton, constraints);

        removeButton.addActionListener(_ -> {
            tags.remove(text);
            this.remove(tag);
            this.revalidate();
            this.repaint();
        });

        this.add(tag);
        this.revalidate();
        this.repaint();
    }

    /**
     * Clears the panel.
     */
    public void clearPanel(){
        tags.clear();
        this.removeAll();
        this.revalidate();
        this.repaint();
    }

    /**
     * Gets the tags.
     * @return the tags
     */
    public HashSet<String> getTags() {
        return tags;
    }
}
