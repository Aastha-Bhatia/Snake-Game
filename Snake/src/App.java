// Snake Game
// Developed by: Aastha Bhatia
// Description: A simple Snake game implementation using Java Swing.
// Feel free to modify and distribute, but please retain this attribution.

// importing the library
import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        // dimensions of our frame
        int boardWidth=600;
        int boardHeight=boardWidth;

        // creating our frame
        JFrame frame=new JFrame("Snake");
        // makes the frame visible
        frame.setVisible(true);
        // we have set the size of our frame
        frame.setSize(boardWidth,boardHeight);
        // keeps the frame at the centerX
        frame.setLocationRelativeTo(null);
        // we cannot resize it
        frame.setResizable(false);
        // closes when we click on X
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame=new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        // the title takes some of the boardheight and the boardheight is not exactly 600px, to resolve this issue, we can write frame.pack()
        frame.pack();
        snakeGame.requestFocus();
    }
}
