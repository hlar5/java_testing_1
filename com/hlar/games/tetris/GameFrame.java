package com.hlar.games.tetris;

import com.hlar.games.tetris.blocks.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameFrame {
    final int GAME_WIDTH= 360;
    final int GAME_HEIGHT= 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXT_MINO_X;
    final int NEXT_MINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();


    public static int dropInterval= 30;

    public GameFrame(){
        left_x = (GamePanel.SCREEN_HEIGHT / 2) - (GAME_WIDTH/2);
        right_x = left_x + GAME_WIDTH;
        top_y = 50;
        bottom_y = top_y + GAME_HEIGHT;

        MINO_START_X = left_x + (GAME_WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXT_MINO_X = right_x + 175;
        NEXT_MINO_Y = top_y + 500;

        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);
    }

    private Mino pickMino(){
        Mino mino = null;
        int i = new Random().nextInt(7);
        switch(i){
            case 0: mino = new Mino_L1(); break;
            case 1: mino = new Mino_L2(); break;
            case 2: mino = new Mino_T(); break;
            case 3: mino = new Mino_Z1(); break;
            case 4: mino = new Mino_Z2(); break;
            case 5: mino = new Mino_Bar(); break;
            case 6: mino = new Mino_Square(); break;
        }
        return mino;
    }

    public void update(){
        if(currentMino.active == false) {
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            currentMino.deactivating = false;

            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);

            checkDelete();
        }
        else{
            currentMino.update();}
    }

    private void checkDelete(){
        int x = left_x;
        int y = top_y;
        int blockCount = 0;

        while(x< right_x && y< bottom_y){
            
            for (int i = 0; i < staticBlocks.size(); i++){
                if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y){
                    blockCount++;
                }
            }
            x += Block.SIZE;

            if(x == right_x){

                if(x == right_x){
                    if(blockCount == 12){
                        for(int i = staticBlocks.size()-1; i > -1; i--){
                            if(staticBlocks.get(i).y == y){
                                staticBlocks.remove(i);
                            }
                        }
                        for(int i = 0; i < staticBlocks.size();i++){
                            if(staticBlocks.get(i).y < y){
                                staticBlocks.get(i).y += Block.SIZE;
                            }
                        }
                    }
                }
                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

    }

    public void draw(Graphics2D g){
        // BORDER
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(4f));
        g.drawRect(left_x-4, top_y-4, GAME_WIDTH+8, GAME_HEIGHT+8);
        
        //MINO FRAME
        int x = right_x + 100;
        int y= bottom_y - 200;
        g.drawRect(x, y, 200, 200);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString("NEXT UP", x+40, y+40);

        // DRAW MINO
        if(currentMino != null){
            currentMino.draw(g);
        }

        nextMino.draw(g);

        for (int i = 0; i < staticBlocks.size(); i++) {
            staticBlocks.get(i).draw(g);
        }

        g.setColor(Color.yellow);
        g.setFont(g.getFont().deriveFont(50f));
        if(Movement.pausePressed) {
            x = left_x + 70;
            y = top_y + 320;
            g.drawString("PAUSED", x, y);
        }
    }
}
