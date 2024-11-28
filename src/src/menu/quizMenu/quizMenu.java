package menu.quizMenu;

import slangs.slangWord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class quizMenu extends JFrame implements ActionListener, ItemListener {
    private JButton backButton;
    private JButton startButton;
    private JButton answerButton;
    private JComboBox quizType;
    private JRadioButton A;
    private JRadioButton B;
    private JRadioButton C;
    private JRadioButton D;
    private JPanel quizPanel;
    private JPanel mainPanel;
    private JPanel questionPanel;
    private JPanel answerPanel;
    private JLabel question;
    private JLabel accuracy;
    ButtonGroup bg = new ButtonGroup();

    private slangWord slangword;
    private String correctAnswer = "";
    private int chosenAnswer = 0;
    private int correctNum = 0;

    public quizMenu(String title, slangWord slangword) {
        super(title);

        this.setResizable(false);
        this.slangword = slangword;

        quizPanel.setMaximumSize(new Dimension(600, 200));
        questionPanel.setMaximumSize(new Dimension(600, 50));
        answerPanel.setMaximumSize(new Dimension(600, 80));
        question.setMaximumSize(new Dimension(350, 40));
        A.setMaximumSize(new Dimension(140, 80));
        B.setMaximumSize(new Dimension(140, 80));
        C.setMaximumSize(new Dimension(140, 80));
        D.setMaximumSize(new Dimension(140, 80));

        quizPanel.setPreferredSize(new Dimension(600, 200));
        questionPanel.setPreferredSize(new Dimension(600, 50));
        answerPanel.setPreferredSize(new Dimension(600, 80));
        question.setPreferredSize(new Dimension(350, 40));
        A.setPreferredSize(new Dimension(140, 80));
        B.setPreferredSize(new Dimension(140, 80));
        C.setPreferredSize(new Dimension(140, 80));
        D.setPreferredSize(new Dimension(140, 80));

        bg.add(A);
        bg.add(B);
        bg.add(C);
        bg.add(D);

        A.addItemListener(this);
        B.addItemListener(this);
        C.addItemListener(this);
        D.addItemListener(this);

        backButton.addActionListener(this);
        startButton.addActionListener(this);
        answerButton.addActionListener(this);

        this.setContentPane(mainPanel);
        this.pack();

        accuracy.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            answerButton.setEnabled(true);
            bg.clearSelection();
            A.setForeground(new Color(0,0,0));
            B.setForeground(new Color(0,0,0));
            C.setForeground(new Color(0,0,0));
            D.setForeground(new Color(0,0,0));
            accuracy.setForeground(new Color(0,0,0));

            int rd = slangword.randomizeInt(0, 3);
            correctNum = rd + 1;
            String[][] quiz = slangword.getQuiz();

            if (Objects.equals(quizType.getSelectedItem(), "Slang quiz")) {
                question.setText("<html>What is the meaning of \"" + quiz[rd][0] + "\"?</html>");
                correctAnswer = quiz[rd][1];
                A.setText("<html>" + quiz[0][1] + "</html>");
                B.setText("<html>" + quiz[1][1] + "</html>");
                C.setText("<html>" + quiz[2][1] + "</html>");
                D.setText("<html>" + quiz[3][1] + "</html>");
            }
            else {
                question.setText("<html>What is the slang for \"" + quiz[rd][1] + "\"?</html>");
                correctAnswer = quiz[rd][0];
                A.setText("<html>" + quiz[0][0] + "</html>");
                B.setText("<html>" + quiz[1][0] + "</html>");
                C.setText("<html>" + quiz[2][0] + "</html>");
                D.setText("<html>" + quiz[3][0] + "</html>");
            }
        }
        else if (e.getSource() == answerButton) {
            if (chosenAnswer == 0) {
                JOptionPane.showMessageDialog(this, "Please pick an answer!", "Answer error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (chosenAnswer == correctNum) {
                switch (chosenAnswer) {
                    case 1:
                        A.setForeground(new Color(24, 180, 19));
                        break;
                    case 2:
                        B.setForeground(new Color(24, 180, 19));
                        break;
                    case 3:
                        C.setForeground(new Color(24, 180, 19));
                        break;
                    case 4:
                        D.setForeground(new Color(24, 180, 19));
                        break;
                }
                accuracy.setText("Well done! That is correct!");
            }
            else {
                switch (chosenAnswer) {
                    case 1:
                        A.setForeground(new Color(180, 12, 27));
                        break;
                    case 2:
                        B.setForeground(new Color(180, 12, 27));
                        break;
                    case 3:
                        C.setForeground(new Color(180, 12, 27));
                        break;
                    case 4:
                        D.setForeground(new Color(180, 12, 27));
                        break;
                }
                switch (correctNum) {
                    case 1:
                        A.setForeground(new Color(24, 180, 19));
                        break;
                    case 2:
                        B.setForeground(new Color(24, 180, 19));
                        break;
                    case 3:
                        C.setForeground(new Color(24, 180, 19));
                        break;
                    case 4:
                        D.setForeground(new Color(24, 180, 19));
                        break;
                }
                accuracy.setText("Incorrect! The answer should be \"" + correctAnswer + "\".");
            }
            answerButton.setEnabled(false);
        }
        else {
            this.dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == A) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                chosenAnswer = 1;
            }
        }
        if (e.getSource() == B) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                chosenAnswer = 2;
            }
        }
        if (e.getSource() == C) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                chosenAnswer = 3;
            }
        }
        if (e.getSource() == D) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                chosenAnswer = 4;
            }
        }
    }
}
