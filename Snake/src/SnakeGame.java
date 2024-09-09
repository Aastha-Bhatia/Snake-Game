// importing all the libraries
import java.awt.*;
import java.awt.event.*;
// for storing the body of snake
import java.util.ArrayList;
// for random positions in which we will place our food
import java.util.Random;
// for our GUI
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{ 

    // we created our private class Tile
    private class Tile {
        int x;
        int y;

        Tile(int x,int y){
            this.x=x;
            this.y=y;
        }
        
    }

    int boardWidth;
    int boardHeight;

    int tileSize=25;

    // this is our snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    // this is our food
    Tile food;

    // to place food in random places everytime
    Random random;

    // we need a game loop to constantly redraw the frames when we move the snake
    // game logic
    Timer gameLoop;

    // initialising a velocity x and a velocity y
    int velocityX;
    int velocityY;

    // game over
    boolean gameOver=false;

    SnakeGame(int boardWidth,int boardHeight){
        this.boardWidth=boardWidth;
        this.boardHeight=boardHeight;
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.decode("#072D1C"));
        addKeyListener(this);
        setFocusable(true);

        // starting position of our snake head.
        snakeHead=new Tile(5, 5);
        snakeBody=new ArrayList<Tile>();

        // starting position of our food
        food=new Tile(10, 10);
        
        random=new Random();
        placeFood();

        // as velocity x is 0 and velocity y is 1, it will move in +ve y deirection
        velocityX=0;
        velocityY=1;

        // milliseconds 
        gameLoop= new Timer(100,this);
        gameLoop.start();
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        // drawing grids for better understanding

        // for(int i=0;i<boardWidth/tileSize;i++){
        //     // (x1,y1,x2,y2)
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
        //     g.drawLine(0,i*tileSize, boardWidth,i*tileSize);
        // }

        // drawing the food
        g.setColor(Color.decode("#800000"));
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize,true);

        // // we are drawing our snake now
        g.setColor(Color.decode("#4F7942"));
        // fills the rectangle
        // x,y, width, height
        // we need to multiply it by the tilesize for it to go 5 units down and 5 units right.
        g.fill3DRect(snakeHead.x*tileSize,snakeHead.y*tileSize,tileSize,tileSize,true);

        // drawing our snake body
        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart=snakeBody.get(i);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize,true);
        }

        // drawing our game score
        g.setFont(new Font("Arial",Font.PLAIN,16));
        if(gameOver){
            g.setColor(Color.WHITE);
            g.drawString("Game Over: "+ String.valueOf(snakeBody.size()), tileSize-16, tileSize);
            // Draw the creator's name
            String creatorMessage = "CREATED BY AASTHA BHATIA";
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int lineHeight = metrics.getHeight(); // Height of a single line of text
            g.drawString(creatorMessage, tileSize - 16, tileSize + lineHeight+1);
        }
        else{
            g.setColor(Color.WHITE);
            g.drawString("Score: "+ String.valueOf(snakeBody.size()), tileSize-16, tileSize);
            // Draw the creator's name
            String creatorMessage = "CREATED BY AASTHA BHATIA";
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int lineHeight = metrics.getHeight(); // Height of a single line of text
            g.drawString(creatorMessage, tileSize - 16, tileSize + lineHeight +1);
        }
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x==tile2.x && tile1.y==tile2.y;
    }

    public void placeFood(){
        food.x=random.nextInt(boardWidth/tileSize); //random no. from 0-24
        food.y=random.nextInt(boardHeight/tileSize); //random no. from 0-24 
    }

    public void move(){
        // lets check the collision
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // to move the snake body, each part has to follow the path of previous part
        // snake body
        for(int i=snakeBody.size()-1;i>=0;i--){
            Tile snakePart=snakeBody.get(i);
            if(i==0){
                snakePart.x=snakeHead.x;
                snakePart.y=snakeHead.y;
            }
            else{
                Tile prevSnakePart=snakeBody.get(i-1);
                snakePart.x=prevSnakePart.x;
                snakePart.y=prevSnakePart.y;

            }
        }

        // snake head
        snakeHead.x+=velocityX;
        snakeHead.y+=velocityY;

        // game over condition
        // if snake collides with its own body, then game gets over
        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart= snakeBody.get(i);
            // collide with snake head
            if(collision(snakeHead, snakePart)){
                gameOver=true;
            }
        }
        if(snakeHead.x*tileSize<0|| snakeHead.x*tileSize>boardWidth|| snakeHead.y*tileSize<0||snakeHead.y*tileSize>boardHeight){
            gameOver=true;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }

    }

    // we have one problem, if the snake is moving forward, it should'nt be able to move backwards. and if left, it should'nt be able to move right, otherwise it will eat itself
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP && velocityY!=1){
            velocityX=0;
            velocityY=-1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY!=-1){
            velocityX=0;
            velocityY=1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX!=1){
            velocityX=-1;
            velocityY=0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX!=-1){
            velocityX=1;
            velocityY=0;
        }
    }

    // we just need keypressed in our game, we just need to define the others, but we will work only on key pressed.
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
    
}
