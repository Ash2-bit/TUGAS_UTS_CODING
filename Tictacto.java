package permainan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tictacto extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    public Tictacto() {
        setTitle("Login Game Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 400);
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        //pemberian warna pada
        panel.setBackground(Color.orange);
        JLabel usernameLabel = new  JLabel("   Username:");
        usernameLabel.setFont(new Font("Italic", Font.BOLD, 30));
        usernameField = new JTextField();
        usernameField.setFont(new Font("Italic", Font.BOLD, 20));
        JLabel passwordLabel = new JLabel("   Password:");
        passwordLabel.setFont(new Font("Italic", Font.BOLD, 30));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Italic", Font.BOLD, 20));
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Italic", Font.BOLD, 40));
        loginButton.setBackground(Color.RED);
        loginButton.setForeground(Color.BLUE);
        usernameLabel.setForeground(Color.white);
        passwordLabel.setForeground(Color.white);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("") && password.equals("")) {
                    // pengguna berhasil login, mulai game
                    TicTacToeGame game = new TicTacToeGame();
                    game.setVisible(true);
                    dispose(); // Close the login window
                } else {
                    JOptionPane.showMessageDialog(null, "Username atau Password tidak valid");
                }
            }
        });

        add(panel);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Tictacto loginGame = new Tictacto();
            loginGame.setVisible(true);
        });
    }
}



//bagian game
class TicTacToeGame extends JFrame {

    private JButton[][] boardButtons;
    private boolean isPlayer1Turn;

    private int movesCount;


    public TicTacToeGame() {
        JPanel titlepanel = new JPanel();
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 700);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        boardButtons = new JButton[3][3];
        isPlayer1Turn = true;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();

                button.addActionListener(new ButtonActionListener(row, col));
                button.setBackground(Color.orange);
                button.setFocusPainted(false);
                button.setFont(new Font("ITALIC", Font.BOLD, 200));

                panel.add(button);
                panel.add(button);
                boardButtons[row][col] = button;
            }
        }

        add(panel);
    }

    private class ButtonActionListener implements ActionListener {
        private int row;
        private int col;

        public ButtonActionListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();

            if (button.getText().isEmpty()) {
                if (isPlayer1Turn) {
                    button.setText("X");
                    button.setForeground(Color.RED);
                } else {
                    button.setText("O");
                    button.setForeground(Color.blue);
                }

                isPlayer1Turn = !isPlayer1Turn;
                movesCount++;

                if (movesCount >= 5) {
                    if (checkWin()) {
                        String winner = isPlayer1Turn ? "Player 2" : "Player 1";
                        JOptionPane.showMessageDialog(null, winner + " wins!");
                        resetGame();
                    } else if (movesCount == 9) {
                        JOptionPane.showMessageDialog(null, "SERI!");
                        resetGame();
                    }
                }
            }
        }

        private boolean checkWin() {
            String[][] board = new String[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = boardButtons[i][j].getText();
                }
            }

            // Check rows
            for (int i = 0; i < 3; i++) {
                if (!board[i][0].isEmpty() && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
                    return true;
                }
            }

            // Check columns
            for (int j = 0; j < 3; j++) {
                if (!board[0][j].isEmpty() && board[0][j].equals(board[1][j]) && board[0][j].equals(board[2][j])) {
                    return true;
                }
            }

            // Check diagonals
            if (!board[0][0].isEmpty() && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
                return true;
            }
            if (!board[0][2].isEmpty() && board[0][2].equals(board[1][1]) &&

                    board[0][2].equals(board[2][0])) {
                return true;
            }
            return false;
        }

        private void resetGame() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    boardButtons[i][j].setText("");
                }            }

            isPlayer1Turn = true;
            movesCount = 0;
        }
    }
}