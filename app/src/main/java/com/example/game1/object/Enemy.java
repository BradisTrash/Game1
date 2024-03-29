package com.example.game1.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.game1.GameLoop;
import com.example.game1.R;

public class Enemy extends Circle {
    private static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND * 0.6;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SPAWNS_PER_MINUTE = 20;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/(SPAWNS_PER_MINUTE/60);
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;
    private final Player player;
    public Enemy(Context context,Player player, double positionX, double positionY, double radius){
        super(context, ContextCompat.getColor(context, R.color.enemy),positionX,positionY,radius);
        this.player = player;
    }

    public Enemy(Context context, Player player) {
        super(context,
                ContextCompat.getColor(context,R.color.enemy),
                Math.random()*1000,
                Math.random()*1000,
                30
        );
        this.player = player;
    }

    public static boolean readyToSpawn() {
        //Checks if enemy is ready to spawn
        if(updatesUntilNextSpawn <= 0){
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        }
        else{
            updatesUntilNextSpawn --;
            return false;
        }
    }

    @Override
    public void update() {
        //Send enemy in a velocity in direction of player
        //calculate vector from enemy to player
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;
        //calculate absolute distance
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this,player);
        //calculate direction
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        //set velocity
        if(distanceToPlayer > 0){
            velocityX = directionX*MAX_SPEED;
            velocityY = directionY*MAX_SPEED;
        }
        else{
            velocityX = 0;
            velocityY = 0;
        }
        //update position of enemy
        positionX += velocityX;
        positionY += velocityY;
    }
}
