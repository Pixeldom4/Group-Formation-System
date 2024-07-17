package view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
    private final JButton renderButton;
    private final JButton editButton;
    private String text;

    public ButtonEditorRenderer() {
        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        renderButton.setText((value == null) ? "Edit" : value.toString());
        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        text = (value == null) ? "Edit" : value.toString();
        editButton.setText(text);
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
        // Handle button click here
        JOptionPane.showMessageDialog(null, "Edit Button Clicked");
    }
}
