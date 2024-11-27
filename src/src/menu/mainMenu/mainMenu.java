package menu.mainMenu;

import menu.historyMenu.historyMenu;
import slangs.slangWord;

import menu.addMenu.addMenu;
import menu.editMenu.editMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class mainMenu extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JButton historyButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton resetButton;
    private JButton randomButton;
    private JButton quizButton;
    private JButton searchButton;
    private JTextField searchField;
    private JComboBox typeSearch;
    private JTable tableSearch;
    private JLabel executionTime;

    private DefaultTableModel model;
    private String[][] searchResult;
    private slangWord slangs = new slangWord();
    String[] column = {"ID", "Slang", "Definition"};

    public mainMenu(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        searchField.setMaximumSize(new Dimension(150, 30));
        typeSearch.setMaximumSize(new Dimension(100, 30));
        searchButton.setMaximumSize(new Dimension(100, 30));

        searchField.setPreferredSize(new Dimension(150, 30));
        typeSearch.setPreferredSize(new Dimension(100, 30));
        searchButton.setPreferredSize(new Dimension(100, 30));

        tableSearch.setModel(new DefaultTableModel(column, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tableSearch.setRowHeight(30);
        model = (DefaultTableModel) tableSearch.getModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableSearch.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableSearch.getColumnModel().getColumn(0).setResizable(false);
        tableSearch.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableSearch.getColumnModel().getColumn(1).setResizable(false);
        tableSearch.getColumnModel().getColumn(2).setPreferredWidth(300);
        tableSearch.getColumnModel().getColumn(2).setResizable(false);

        searchButton.addActionListener(this);
        historyButton.addActionListener(this);
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        resetButton.addActionListener(this);
        randomButton.addActionListener(this);
        quizButton.addActionListener(this);

        this.setContentPane(mainPanel);
        this.pack();
    }

    void clearTable() {
        int row = model.getRowCount() - 1;
        for (int i = row; i >= 0; i--) {
            model.removeRow(i);
        }
    }
    public static void main(String[] args) {
        mainMenu app = new mainMenu("Dictionary");
        app.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String key = searchField.getText();
            if (key.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter something to search!", "Searching error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.clearTable();
            long timeElapsed = 0;

            if (Objects.equals(typeSearch.getSelectedItem(), "Search a slang")) {
                long startTime = System.currentTimeMillis();
                searchResult = slangs.getMeaning(key);
                long endTime = System.currentTimeMillis();
                timeElapsed = endTime - startTime;
            }
            else {
                long startTime = System.currentTimeMillis();
                searchResult = slangs.findDefinition(key);
                long endTime = System.currentTimeMillis();
                timeElapsed = endTime - startTime;
            }
            if (searchResult != null)
                executionTime.setText("Search time: " + timeElapsed + "ms");
            else {
                executionTime.setText("Word not found");
                return;
            }
            for (String[] ss : searchResult) {
                model.addRow(ss);
            }

            slangs.addToHistory(searchResult);
        }
        else if (e.getSource() == historyButton) {
            historyMenu historymenu = new historyMenu("Search history");
            historymenu.setVisible(true);
        }
        else if (e.getSource() == addButton) {
            addMenu AddMenu = new addMenu("Add a slang", slangs);
            AddMenu.setVisible(true);
        }
        else if (e.getSource() == editButton) {
            int selectedRow = tableSearch.getSelectedRow();
            int selectedCol = tableSearch.getSelectedColumn();
            if (selectedRow == -1 || selectedCol == -1) {
                JOptionPane.showMessageDialog(this, "Please select something to edit!", "Edit error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                String selectedWord = (String) tableSearch.getValueAt(selectedRow, 1);
                String selectedDefinition = (String) tableSearch.getValueAt(selectedRow, 2);
                editMenu editmenu = new editMenu("Edit a slang", slangs, selectedWord, selectedDefinition);
                editmenu.setVisible(true);
            }
        }
        else if (e.getSource() == deleteButton) {
            int selectedRow = tableSearch.getSelectedRow();
            int selectedCol = tableSearch.getSelectedColumn();
            if (selectedRow == -1 || selectedCol == -1) {
                JOptionPane.showMessageDialog(this, "Please select something to delete!", "Delete error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                String selectedWord = (String) tableSearch.getValueAt(selectedRow, 1);
                String selectedDefinition = (String) tableSearch.getValueAt(selectedRow, 2);
                int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this slang word?", "Delete a slang word", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.NO_OPTION) {
                    return;
                }
                slangs.deleteSlang(selectedWord, selectedDefinition);
                JOptionPane.showMessageDialog(this, "Successfully deleted " + selectedDefinition + " from the word " + selectedWord, "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if (e.getSource() == resetButton) {
            slangs.reset();
            JOptionPane.showMessageDialog(this, "Successfully reset!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (e.getSource() == randomButton) {
            JOptionPane.showMessageDialog(this, slangs.randomizeASlang(), "Randomize a slang", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}