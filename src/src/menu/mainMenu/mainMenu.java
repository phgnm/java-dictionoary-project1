package menu.mainMenu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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

    private DefaultTableModel model;
    private String[][] searchResult;

    public mainMenu(String title) {
        super(title);

        searchField = new JTextField();
        searchButton = new JButton("Search");
        typeSearch = new JComboBox();
        tableSearch = new JTable();
        mainPanel = new JPanel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        searchField.setMaximumSize(new Dimension(150, 30));
        searchField.setPreferredSize(new Dimension(150, 30));
        typeSearch.setMaximumSize(new Dimension(150, 30));
        typeSearch.setPreferredSize(new Dimension(150, 30));
        searchButton.setMaximumSize(new Dimension(150, 30));
        searchButton.setPreferredSize(new Dimension(150, 30));
        String column[] = {"ID", "Slang", "Definition"};
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
        
        mainPanel.add(searchField);
        mainPanel.add(typeSearch);
        mainPanel.add(searchButton);
        mainPanel.add(new JScrollPane(tableSearch));

        this.add(mainPanel);
        this.pack();
    }
    public static void main(String[] args) {
        mainMenu app = new mainMenu("Dictionary");
        app.setVisible(true);
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
            String key = searchField.getText();
            if (key.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a slang word!");
            }
        }
    }
}