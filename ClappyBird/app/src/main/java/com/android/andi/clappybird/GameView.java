package com.android.andi.clappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by andi on 10/22/17.
 */

public class GameView extends SurfaceView {

    private SurfaceHolder surfaceHolder;
    private Bitmap bmpIcon;
    private GameThread gameThread;
    int xPos = 0;
    int yPos = 0;
    int deltaX = 5;
    int deltaY = 5;
    int iconWidth;
    int iconHeight;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context,
                    AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context,
                    AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        gameThread = new GameThread(this);

        surfaceHolder = getHolder();
        bmpIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.flappybird1);
        iconHeight = bmpIcon.getHeight();
        iconWidth = bmpIcon.getWidth();

        surfaceHolder.addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameThread.setRunning(true);
                gameThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width, int height) {
                // TODO Auto-generated method stub

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameThread.setRunning(false);
                while(retry){
                    try{
                        gameThread.join();
                        retry= false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }});
    }

    protected void drawSomething(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bmpIcon,
                getWidth()/2, getHeight()/2, null);
        System.out.println(yPos);
        xPos += deltaX;
        yPos += deltaY;

        if (deltaY >0){
            if (yPos >= getHeight() - iconHeight){
                deltaY *= -1;
            }
        } else {
            if (yPos <= 0){
                deltaY*=-1;
            }
        }
    }

}
