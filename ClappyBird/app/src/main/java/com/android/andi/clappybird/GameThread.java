package com.android.andi.clappybird;

import android.graphics.Canvas;

/**
 * Created by andi on 10/22/17.
 */

public class GameThread extends Thread {
    GameView gameView;
    private boolean running = false;

    public GameThread(GameView gameView){
        this.gameView=gameView;
    }

    public void setRunning(boolean run){
        running = run;
    }

    @Override
    public void run(){
        while (running){
            Canvas canvas = gameView.getHolder().lockCanvas();

            if (canvas != null){
                synchronized (gameView.getHolder()){
                    gameView.drawSomething(canvas);
                }
                gameView.getHolder().unlockCanvasAndPost(canvas);
            }

            try{
                sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
