package view.services.hovervoice;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public interface IHoverVoiceService {
    /**
     * Adds a hover voice to the given component.
     * @param component the component to add the hover voice to
     * @param text the text to be spoken when the component is hovered over
     */
    void addHoverVoice(JComponent component, String text);

    /**
     * Adds a hover voice to the given table.
     * @param table the table to add the hover voice to
     * @param textMap a map of points (row, col) to text to be spoken when the cell at that point is hovered over
     */
    void addTableHoverVoice(JTable table, Map<Point, String> textMap);
}
