package com.hlar.games.snake;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_WIDTH) / UNIT_SIZE;
    static final int DELAY = 75;

    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 3;
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(255,165,0));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    
    
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(running){
            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.setColor(Color.orange);
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
            g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for(int i=0;i<bodyParts;i++){
                if(i == 0){
                    g.setColor(new Color(0,150,255));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else{
                    g.setColor(Color.cyan);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(new Color(255,0, 50));
            g.setFont(new Font("Ink Free", Font.BOLD, 25));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("SCORE: " + applesEaten))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for(int i =bodyParts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];   
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;      
            case 'L':
                x[0] = x[0]- UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0]+ UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }
    public void checkCollision(){
        for(int i = bodyParts;i>0;i--){
            if((x[0] == x[i])&&(y[0] == y[i])){
                running = false;
            }
        }

        if(x[0]<0){
            running = false;
        }
        if(x[0]>SCREEN_WIDTH){
            running = false;
        }
        if(y[0]<0){
            running = false;
        }
        if(y[0]>SCREEN_HEIGHT){
            running = false;
        }

        if (!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        g.setColor(new Color(255,0, 50));
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metricsGame = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metricsGame.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);

        g.setColor(new Color(255,0, 50));
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        FontMetrics metricsScore = getFontMetrics(g.getFont());
        g.drawString("SCORE: " + applesEaten, (SCREEN_WIDTH - metricsScore.stringWidth("SCORE: " + applesEaten))/2, g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            switch(event.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){ direction ='L';}
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){ direction ='R';}
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){ direction ='U';}
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){ direction ='D';}
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
            }
        }
    }
    
}
