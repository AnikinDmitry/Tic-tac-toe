import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe implements ActionListener{
    private final JFrame frame = new JFrame();
    private final JPanel headerPanel = new JPanel();
    private final JPanel playSpacePanel = new JPanel();
    private final JLabel textLabel = new JLabel();
    private final JButton[] sheets = new JButton[9];
    private final JButton newGameButton = new JButton("New Game");
    private boolean playerOneTurn;
    private boolean playerWins = false;
    private int moveCount = 0;

    public TicTacToe() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Tic-tac-toe");
        frame.setSize(600, 800);
        frame.setLocation(100, 100);
        frame.setVisible(true);
        frame.setResizable(false);

        textLabel.setForeground(Color.GREEN);
        textLabel.setFont(new Font(null, Font.BOLD, 75));
        textLabel.setHorizontalAlignment(JLabel.CENTER);

        headerPanel.setBackground(Color.BLACK);

        playSpacePanel.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < sheets.length; i++) {
            sheets[i] = new JButton();
            sheets[i].setFont(new Font(null, Font.BOLD, 120));
            sheets[i].setFocusable(false);
            sheets[i].addActionListener(this);
            playSpacePanel.add(sheets[i]);
        }

        newGameButton.setFont(new Font(null, Font.BOLD, 50));
        newGameButton.setFocusable(false);
        newGameButton.addActionListener(this);

        headerPanel.add(textLabel);
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(playSpacePanel);
        frame.add(newGameButton, BorderLayout.SOUTH);

        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < sheets.length; i++) {
            if (e.getSource() == sheets[i]) {
                if (sheets[i].getText() == "") {
                    moveCount++;
                }

                if (playerOneTurn) {
                    if (sheets[i].getText() == "") {
                        sheets[i].setForeground(Color.BLUE);
                        sheets[i].setText("X");

                        playerOneTurn = false;
                        textLabel.setText("O turn");

                        check();
                    }
                } else {
                    if (sheets[i].getText() == "") {
                        sheets[i].setForeground(Color.RED);
                        sheets[i].setText("O");

                        playerOneTurn = true;
                        textLabel.setText("X turn");

                        check();
                    }
                }
            }
        }

        if (e.getSource() == newGameButton) createNewGame();
    }

    private void firstTurn() {
        if ((new Random()).nextInt(2) == 0) {
            playerOneTurn = true;
            textLabel.setText("X turn");
        } else {
            playerOneTurn = false;
            textLabel.setText("O turn");
        }
    }

    private void check() {
        int[][] winCombinations = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}
        };

        for (int[] winCombination : winCombinations) {
            if (sheets[winCombination[0]].getText() == sheets[winCombination[1]].getText() &&
                    sheets[winCombination[1]].getText() == sheets[winCombination[2]].getText() &&
                    sheets[winCombination[0]].getText() != "") {
                drawWin(
                        sheets[winCombination[0]].getText(),
                        winCombination[0],
                        winCombination[1],
                        winCombination[2]
                );
            }
        }

        if(!playerWins && moveCount == sheets.length) {
            for (int i = 0; i < sheets.length; i++) {
                sheets[i].setEnabled(false);
            }

            textLabel.setText("Draw");
        }
    }

    private void drawWin(String player, int first, int second, int third) {
        sheets[first].setBackground(Color.GREEN);
        sheets[second].setBackground(Color.GREEN);
        sheets[third].setBackground(Color.GREEN);

        for (int i = 0; i < sheets.length; i++) {
            sheets[i].setEnabled(false);
        }

        textLabel.setText(player + " wins");

        playerWins = true;
    }

    private void createNewGame() {
        moveCount = 0;
        playerWins = false;

        for (int i = 0; i < sheets.length; i++) {
            sheets[i].setText("");
            sheets[i].setEnabled(true);
            sheets[i].setBackground(null);

            firstTurn();
        }
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}