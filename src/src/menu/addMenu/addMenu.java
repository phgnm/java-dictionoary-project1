package menu.addMenu;

import slangs.slangWord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addMenu extends JFrame implements ActionListener {
    private JButton backButton;
    private JButton addButton;
    private JTextField slang;
    private JTextField definition;
    private JPanel mainPanel;
    private JPanel slangPanel;
    private JPanel definitionPanel;

    private slangWord slangs;

    public addMenu(String title, slangWord slangList) {
        super(title);

        this.setResizable(false);
        this.slangs = slangList;

        slangPanel.setMaximumSize(new Dimension(150, 50));
        definitionPanel.setMaximumSize(new Dimension(250, 50));
        addButton.setMaximumSize(new Dimension(100, 30));
        backButton.setMaximumSize(new Dimension(100, 30));
        slang.setMaximumSize(new Dimension(250, 30));
        definition.setMaximumSize(new Dimension(250, 30));

        slangPanel.setPreferredSize(new Dimension(150, 50));
        definitionPanel.setPreferredSize(new Dimension(250, 50));
        addButton.setPreferredSize(new Dimension(100, 30));
        backButton.setPreferredSize(new Dimension(100, 30));
        slang.setPreferredSize(new Dimension(250, 50));
        definition.setPreferredSize(new Dimension(250, 30));

        backButton.addActionListener(this);
        addButton.addActionListener(this);

        this.setContentPane(mainPanel);
        this.pack();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String word = slang.getText();
            String def = definition.getText();
            if (word.equals("") || def.equals("")) {
                JOptionPane.showMessageDialog(mainPanel, "Please enter both word and definition", "Add error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (slangs.checkCoincidence(word)) {
                int n = JOptionPane.showConfirmDialog(this, "This word exists in the dictionary, would you like to overwrite it? \n Choose No will add another word into the dictionary!", "Coincidence found!", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    slangs.Overwrite(word, def);
                }
                else {
                    slangs.Duplicate(word, def);
                }
            }
            else {
                slangs.Overwrite(word, def);
            }
            JOptionPane.showMessageDialog(this, "Successfully added " + word + " to the dictionary!", "Finish", JOptionPane.INFORMATION_MESSAGE);
        }
        this.dispose();
    }
}
