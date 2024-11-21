package menu.mainMenu;

import slangs.slangWord;

import menu.addMenu.addMenu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class mainMenu extends JFrame implements ListSelectionListener, ActionListener {
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
        this.setResizable(false);
        searchField.setMaximumSize(new Dimension(150, 30));
        typeSearch.setMaximumSize(new Dimension(100, 30));
        searchButton.setMaximumSize(new Dimension(100, 30));

        searchField.setPreferredSize(new Dimension(150, 30));
        typeSearch.setPreferredSize(new Dimension(100, 30));
        searchButton.setPreferredSize(new Dimension(100, 30));

        tableSearch.setModel(new DefaultTableModel(column, 0));
        tableSearch.setRowHeight(30);
        model = (DefaultTableModel) tableSearch.getModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableSearch.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableSearch.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableSearch.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        ListSelectionModel selectionModel = tableSearch.getSelectionModel();
        selectionModel.addListSelectionListener(this);

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
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
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
        }
        else if (e.getSource() == addButton) {
            addMenu AddMenu = new addMenu("Add a slang", slangs);
            AddMenu.setVisible(true);
        }
    }
}