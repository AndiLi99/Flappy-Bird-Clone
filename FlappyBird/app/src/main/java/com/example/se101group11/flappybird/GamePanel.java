package com.example.se101group11.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public static int score;
    public static int highScore;

    private Rect r = new Rect();
    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private Bitmap background= BitmapFactory.decodeResource(getResources(), R.drawable.bg);

    private Bitmap zeroPNG = BitmapFactory.decodeResource(getResources(), R.drawable.zero);
    private Bitmap onePNG = BitmapFactory.decodeResource(getResources(), R.drawable.one);
    private Bitmap twoPNG = BitmapFactory.decodeResource(getResources(), R.drawable.two);
    private Bitmap threePNG = BitmapFactory.decodeResource(getResources(), R.drawable.three);
    private Bitmap fourPNG = BitmapFactory.decodeResource(getResources(), R.drawable.four);
    private Bitmap fivePNG = BitmapFactory.decodeResource(getResources(), R.drawable.five);
    private Bitmap sixPNG = BitmapFactory.decodeResource(getResources(), R.drawable.six);
    private Bitmap sevenPNG = BitmapFactory.decodeResource(getResources(), R.drawable.seven);
    private Bitmap eightPNG = BitmapFactory.decodeResource(getResources(), R.drawable.eight);
    private Bitmap ninePNG = BitmapFactory.decodeResource(getResources(), R.drawable.nine);



    private Bitmap playAgain = BitmapFactory.decodeResource(getResources(), R.drawable.button);
    private Bitmap scoreBoard = BitmapFactory.decodeResource(getResources(), R.drawable.menuscore);
    private Bitmap gameOverPic = BitmapFactory.decodeResource(getResources(), R.drawable.gameover);

    private boolean gameStarted = true;
    private boolean gameOver = false;

    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        score = 0;
        highScore = 0;

        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0), BitmapFactory.decodeResource(getResources(), R.drawable.bird2));

        playerPoint = new Point(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*3/4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(100, 150,100, Color.BLACK);
        Obstacle.bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.bottomtube);
        Obstacle.bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.toptube);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

private boolean withinBitmap(int x, int y, Bitmap bitmap, int leftX, int topY){
    return x < leftX + bitmap.getWidth() && x > leftX && y < topY + bitmap.getHeight() && y > topY;
}

    public void reset(){
        player.init();
        obstacleManager = new ObstacleManager(200, 250, 75, Color.BLACK);
        score = 15;

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

        while(retry)
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
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                if(!gameOver)
                {
                    player.jump();
                }

                if(gameOver)
                {
                    if (withinBitmap(x, y, playAgain,Constants.SCREEN_WIDTH/2 - playAgain.getWidth()/2, Constants.SCREEN_HEIGHT/2 + 2*playAgain.getHeight()/2)) {
                        reset();
                        gameOver = false;
                    }
                }


        }

        return true;
        //return super.onTouchEvent(event);
    }

    public void update(){
        if (!gameOver && gameStarted){
            player.update(playerPoint);
            obstacleManager.update();
            if (obstacleManager.playerCollide(player)||player.getRectangle().bottom > Constants.SCREEN_HEIGHT)
                gameOver = true;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        canvas.drawBitmap(background, 0, 0, null);

        player.draw(canvas);
        obstacleManager.draw(canvas);

        drawScore(canvas);

        if (gameOver){
            Paint paint = new Paint();
            paint.setTextSize(50);
            paint.setColor(Color.BLUE);
            drawCentreText(canvas, paint, "GAME OVER");
            canvas.drawBitmap(gameOverPic, Constants.SCREEN_WIDTH/2 - gameOverPic.getWidth()/2, Constants.SCREEN_HEIGHT/2 - 50 -scoreBoard.getHeight(), paint);
            canvas.drawBitmap(playAgain, Constants.SCREEN_WIDTH/2 - playAgain.getWidth()/2, Constants.SCREEN_HEIGHT/2 + 2*playAgain.getHeight()/2, paint);
            canvas.drawBitmap(scoreBoard, Constants.SCREEN_WIDTH/2 - scoreBoard.getWidth()/2, Constants.SCREEN_HEIGHT/2 - scoreBoard.getHeight()/2 - playAgain.getHeight()/2, paint);
        } else if(!gameStarted){
            Paint paint = new Paint();
            paint.setTextSize(50);
            paint.setColor(Color.BLUE);
            drawCentreText(canvas, paint, "Start Game!");
        }
    }

    private void drawScore(Canvas canvas){


        Paint paint = new Paint();

        int tempScore = score;
        int scoreLength = 0;
        do
        {
            tempScore /= 10;
            scoreLength++;
        } while (tempScore != 0);


        int digitWidth = 20;
        tempScore = score;

        for(int i = scoreLength; i > 0; i--)
        {
            int thisDigit = tempScore % 10;
            tempScore /= 10;
            switch (thisDigit)
            {
                case 0:
                    canvas.drawBitmap(zeroPNG, digitWidth*i, 25, paint);
                    break;
                case 1:
                    canvas.drawBitmap(onePNG, digitWidth*i, 25, paint);
                    break;
                case 2:
                    canvas.drawBitmap(twoPNG, digitWidth*i, 25, paint);
                    break;
                case 3:
                    canvas.drawBitmap(threePNG, digitWidth*i, 25, paint);
                    break;
                case 4:
                    canvas.drawBitmap(fourPNG, digitWidth*i, 25, paint);
                    break;
                case 5:
                    canvas.drawBitmap(fivePNG, digitWidth*i, 25, paint);
                    break;
                case 6:
                    canvas.drawBitmap(sixPNG, digitWidth*i, 25, paint);
                    break;
                case 7:
                    canvas.drawBitmap(sevenPNG, digitWidth*i, 25, paint);
                    break;
                case 8:
                    canvas.drawBitmap(eightPNG, digitWidth*i, 25, paint);
                    break;
                case 9:
                    canvas.drawBitmap(ninePNG, digitWidth*i, 25, paint);
                    break;
            }
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
