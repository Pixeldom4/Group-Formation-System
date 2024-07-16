package view.components;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Used to display buttons in a table
 */
public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
    private final JTable table;
    private final JButton renderButton;
    private final JButton editButton;
    private String text;
    private ArrayList<ButtonAction> actions = new ArrayList<>();

    /**
     * Creates a column of buttons within the table
     * @param table the table
     * @param column the column for the buttons
     */
    public ButtonColumn(JTable table, int column) {
        super();
        this.table = table;
        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        renderButton.setForeground(table.getForeground());
        renderButton.setBackground(table.getBackground());
        renderButton.setText((value == null) ? "" : value.toString());
        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        text = (value == null) ? "" : value.toString();
        editButton.setText(text);
        editButton.setBackground(UIManager.getColor("Button.background"));
        editButton.setForeground(UIManager.getColor("Button.foreground"));
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
        actions.get(table.getSelectedRow()).onClick();
    }

    /**
     * Sets the actions that will be called when the button is clicked
     * @param actions A list of actions where the index corresponds to the row the button is in
     */
    public void setActions(ArrayList<ButtonAction> actions) {
        this.actions = actions;
    }
}
