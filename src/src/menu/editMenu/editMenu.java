package menu.editMenu;

import slangs.slangWord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class editMenu extends JFrame implements ActionListener{
    private JButton backButton;
    private JButton editButton;
    private JTextField definition;
    private JPanel mainPanel;
    private JPanel definitionPanel;
    private JPanel slangPanel;
    private JLabel textSlang;

    private String curValue;
    private slangWord slangword;
    public editMenu(String title, slangWord slangword, String slang, String def) {
        super(title);

        this.setResizable(false);

        textSlang.setText(slang);
        definition.setText(def);
        curValue = def;
        this.slangword = slangword;
        slangPanel.setMaximumSize(new Dimension(120, 50));
        textSlang.setMaximumSize(new Dimension(100, 30));
        definitionPanel.setMaximumSize(new Dimension(280, 50));
        definition.setMaximumSize(new Dimension(280, 30));
        editButton.setMaximumSize(new Dimension(100, 30));
        backButton.setMaximumSize(new Dimension(100, 30));

        slangPanel.setPreferredSize(new Dimension(120, 50));
        textSlang.setPreferredSize(new Dimension(100, 30));
        definitionPanel.setPreferredSize(new Dimension(280, 50));
        definition.setPreferredSize(new Dimension(280, 30));
        editButton.setPreferredSize(new Dimension(100, 30));
        backButton.setPreferredSize(new Dimension(100, 30));

        backButton.addActionListener(this);
        editButton.addActionListener(this);

        this.setContentPane(mainPanel);
        this.pack();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editButton) {
            if (definition.getText().equals("")) {
                JOptionPane.showMessageDialog(mainPanel, "Please enter a definition", "Editing error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (definition.getText().equals(curValue)) {
                JOptionPane.showMessageDialog(mainPanel, "The definition is not different!", "Editing error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                slangword.Set(textSlang.getText(), curValue, definition.getText());
                JOptionPane.showMessageDialog(this, "Successfully edited " + textSlang.getText() + " to the dictionary!", "Finish", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        this.dispose();
    }
}
