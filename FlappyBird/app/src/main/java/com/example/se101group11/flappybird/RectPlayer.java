package com.example.se101group11.flappybird;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by jason on 22/10/17.
 */

public class RectPlayer implements GameObject {

    private xPos;
    private yPos;
    private birdHeight;
    
    private deltaY;
    private final
    private Rect rectangle;
    private int color;

    public Rect getRectangle(){
        return rectangle;
    }

    public RectPlayer(Rect rectangle, int color)
    {
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {


    }

    public void update(Point point){
        //l t r b
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x +rectangle.width()/2,
                point.y + rectangle.height()/2);

    }
}
