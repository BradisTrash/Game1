package com.example.game1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.game1.gamepanel.GameOver;
import com.example.game1.gamepanel.Joystick;
import com.example.game1.gamepanel.Performance;
import com.example.game1.object.Circle;
import com.example.game1.object.Enemy;
import com.example.game1.object.Player;
import com.example.game1.object.Projectile;

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
    private final List<Projectile> projectileList = new ArrayList<Projectile>();
    private int joystickPointerId = 0;
    private int numberOfProjectiles = 0;
    private GameOver gameOver;
    private Performance performance;

    public Game(Context context) {
        super(context);
        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.gameLoop = new GameLoop(this,surfaceHolder);

        //Initialize game panels
        performance = new Performance(getContext(),gameLoop);
        gameOver = new GameOver(getContext());
        joystick = new Joystick(275,500,70,40);
       //Initialize game objects
        player = new Player(getContext(), joystick,500,500,30);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //handle touch event actions
        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if(joystick.getIsPressed()){
                    //joystick was pressed before this event
                    numberOfProjectiles++;
                }
                else if(joystick.isPressed((double)event.getX(),(double)event.getY()))
                {
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                }
                else{
                    //joystick wasn't pressed and isn't being pressed
                    projectileList.add(new Projectile(getContext(),player));

                }
                return true;
            case MotionEvent.ACTION_MOVE:
                //joystick was pressed and moves actuator
                if(joystick.getIsPressed()){
                    joystick.setActuator((double)event.getX(),(double)event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(joystickPointerId == event.getPointerId(event.getActionIndex())){
                    //joystick was let go of and reset actuator
                    joystick.setIsPressed(false);
                    joystick.resetActuator();

                }
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

        //Draw game objects
        player.draw(canvas);

        for(Enemy enemy: enemyList){
            enemy.draw(canvas);
        }
        for(Projectile projectile: projectileList){
            projectile.draw(canvas);
        }
        //Draw game panels
        joystick.draw(canvas);
        performance.draw(canvas);
        //Draw Game Over when player dies
        if(player.getHealthPoints() <= 0){
            gameOver.draw(canvas);
        }

    }


    public void update() {

        //stop updating game if player dies
        if(player.getHealthPoints() <= 0){
            return;
        }


        //update game state
        player.update();
        joystick.update();
        if(Enemy.readyToSpawn()){
            enemyList.add(new Enemy(getContext(),player));
        }
        //update each enemy
        while(numberOfProjectiles > 0)
        {
            projectileList.add(new Projectile(getContext(),player));
            numberOfProjectiles--;
        }
        for(Enemy enemy : enemyList){
            enemy.update();
        }
        //update each projectile
        for(Projectile projectile : projectileList){
            projectile.update();
        }
        //check for collision between enemy and player and projectiles
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while(iteratorEnemy.hasNext()){
            Circle enemy = iteratorEnemy.next();
            if(Circle.isColliding(enemy,player))
            {
                iteratorEnemy.remove();
                player.setHealthPoints(player.getHealthPoints() - 1);
                continue;
            }
            Iterator<Projectile> iteratorProjectile = projectileList.iterator();
            while(iteratorProjectile.hasNext()){
                Circle projectile = iteratorProjectile.next();
                //remove projectile if collision occurs
                if(Circle.isColliding(projectile,enemy)){
                    iteratorProjectile.remove();
                    iteratorEnemy.remove();
                    break;
                }
            }
        }
    }
}
