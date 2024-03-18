package com.hlar.games.tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Movement implements KeyListener{
    public static boolean upPressed,downPressed,leftPressed,rightPressed, pausePressed;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_W){
            upPressed = true;
        }
        if(keyCode == KeyEvent.VK_S){
            downPressed = true;
        }
        if(keyCode == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(keyCode == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(keyCode == KeyEvent.VK_SPACE){
            if(pausePressed){
                pausePressed = false;
            }
            else{
                pausePressed = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
