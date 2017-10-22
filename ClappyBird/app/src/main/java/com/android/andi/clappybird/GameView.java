package com.android.andi.clappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.android.andi.clappybird.R;
/**
 * Created by andi on 10/22/17.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Bitmap bmpIcon;

    public GameView(Context context){
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        surfaceHolder = getHolder();
        bmpIcon = BitmapFactory.decodeResource(getResources(), R.drawable.FlappyBird_1.png);
    surfaceHolder.addCallback(new SurfaceHolder.Callback(){
        @Override
        public void surfaceCreated(SurfaceHolder holder){
            Canvas canvas = holder.lockCanvas();
            drawSomething(canvas);
            holder.unlockCanvasAndPost(canvas);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
            //TODO auto generated
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder){
            //TODO auto generated
        }
    });
    }

    protected void drawSomething(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bmpIcon, getWidth()/2, getHeight()/2, null);
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
