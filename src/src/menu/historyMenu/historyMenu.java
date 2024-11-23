package menu.historyMenu;

import slangs.slangWord;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class historyMenu extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JButton backButton;
    private JTable table;

    DefaultTableModel model;

    public historyMenu(String title) {
        super(title);

        this.setResizable(false);

        scrollPane.setMaximumSize(new Dimension(500, 300));
        scrollPane.setPreferredSize(new Dimension(500, 300));

        String[] column = {"ID", "Slang", "Definition", "Time"};

        table.setModel(new DefaultTableModel(column, 0));
        table.setRowHeight(30);

        model = (DefaultTableModel) table.getModel();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setResizable(false);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setResizable(false);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setResizable(false);

        String[][] history = slangWord.readHistory();

        for (String[] s : history) {
            model.addRow(s);
        }

        backButton.addActionListener(this);

        this.setContentPane(mainPanel);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
