package menu.mainMenu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainMenu extends JFrame implements ListSelectionListener, ActionListener {
    private JPanel mainPanel;
    private JButton historyButton;
    private JButton addButton;
    private JButton resetButton;
    private JButton randomButton;
    private JButton quizButton;
    private JButton searchButton;
    private JTextField searchField;
    private JComboBox typeSearch;
    private JTable tableSearch;
    private JLabel executionTime;

    public static void main(String[] args) {
        ;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            int row = tableSearch.getSelectedRow();
            int col = tableSearch.getSelectedColumn();
            if (row == -1 || col == -1) {
                return;
            }


        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            ;
        }
    }
}