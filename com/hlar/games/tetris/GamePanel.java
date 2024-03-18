package com.hlar.games.tetris;

import java.awt.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final Color BLACK = new Color(0,0,25);
    final int FPS = 60;
    Thread gameThread;
    GameFrame gf;

    public GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(BLACK);
        this.setLayout(null);
        this.addKeyListener(new Movement());
        this.setFocusable(true);   
        
        gf = new GameFrame();

    }

    public void lauchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;

            if (delta >- 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update(){
        if(Movement.pausePressed ==false) {gf.update();}
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D )g;
        gf.draw(g2);
    }
}