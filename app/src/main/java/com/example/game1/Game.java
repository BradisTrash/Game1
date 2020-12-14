package com.example.game1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.game1.object.Circle;
import com.example.game1.object.Enemy;
import com.example.game1.object.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


//Game renders all objects onto screen and updates the screen with changes
class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    //private final Enemy enemy;
    private final GameLoop gameLoop;
    private final List<Enemy> enemyList = new ArrayList<Enemy>();
    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.gameLoop = new GameLoop(this,surfaceHolder);

       //Initialize game objects
        joystick = new Joystick(275,500,70,40);
        player = new Player(getContext(), joystick,500,500,30);
        //enemy = new Enemy(getContext(), player,600,800,30);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //handle touch event actions
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(joystick.isPressed((double)event.getX(),(double)event.getY()))
                {
                    joystick.setIsPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()){
                    joystick.setActuator((double)event.getX(),(double)event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;
        }



        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);

        player.draw(canvas);
        joystick.draw(canvas);
        for(Enemy enemy: enemyList){
            enemy.draw(canvas);
        }
    }

    public void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(),R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100,200,paint);
    }
    public void drawFPS(Canvas canvas){
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(),R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100,100,paint);
    }

    public void update() {
        player.update();
        joystick.update();
        if(Enemy.readyToSpawn()){
            enemyList.add(new Enemy(getContext(),player));
        }
        //update each enemy
        for(Enemy enemy : enemyList){
            enemy.update();
        }
        //check for collision between enemy and player
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while(iteratorEnemy.hasNext()){
            if(Circle.isColliding(iteratorEnemy.next(),player))
            {
                iteratorEnemy.remove();
            }
        }
    }
}
