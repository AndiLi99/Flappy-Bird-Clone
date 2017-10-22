package com.example.se101group11.flappybird;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by jason on 22/10/17.
 */

public class RectPlayer implements GameObject {

    private int xPos = Constants.SCREEN_WIDTH/2;
    private float yPos;
    private int birdHeight;
    private int birdWidth;
    private final int accelY = 3;
    private int deltaY;
    private boolean jump = false;

    private Bitmap btmp;
    private Rect rectangle;
    private int color;

    public Rect getRectangle(){
        return rectangle;
    }

    public RectPlayer(Rect rectangle, int color, Bitmap btmp)
    {
        this.rectangle = rectangle;
        this.color = color;
        this.btmp = btmp;
        birdHeight=btmp.getHeight();
        birdWidth=btmp.getWidth();
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawBitmap(btmp, rectangle.centerX(), rectangle.centerY(), null);

    }

    @Override
    public void update() {


    }

    public void jump(){
        deltaY=-25;
    }

    public void update(Point point){
        yPos+= deltaY;
        deltaY+= accelY;

        rectangle.set(xPos -birdWidth/2,(int)( yPos-birdHeight/2), xPos+birdWidth/2, (int)(yPos+birdHeight/2));


    }
}
