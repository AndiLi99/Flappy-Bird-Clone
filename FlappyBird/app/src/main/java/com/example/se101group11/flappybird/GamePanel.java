package com.example.se101group11.flappybird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.StringRes;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by jason on 22/10/17.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;

    private Rect r = new Rect();

    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private boolean gameOver = false;

    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        player = new RectPlayer(new Rect(50, 50, 100, 100), Color.rgb(255, 0, 0));

        playerPoint = new Point(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*3/4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(100, 150,75, Color.BLACK);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    public void reset(){
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*3/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(200, 250, 75, Color.BLACK);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;

        while(true)
        {
            try{
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver)
                    playerPoint.set((int)event.getX(), (int)event.getY());
                if(gameOver)
                {
                    reset();
                    gameOver = false;
                }
        }

        return true;
        //return super.onTouchEvent(event);
    }

    public void update(){
        if (!gameOver){
            player.update(playerPoint);
            obstacleManager.update();
            if (obstacleManager.playerCollide(player))
                gameOver = true;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        obstacleManager.draw(canvas);

        if (gameOver){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.BLUE);
            drawCentreText(canvas, paint, "GAME OVER");
        }
    }

    private void drawCentreText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}