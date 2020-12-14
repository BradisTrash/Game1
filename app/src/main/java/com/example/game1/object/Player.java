package com.example.game1.object;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.game1.GameLoop;
import com.example.game1.gamepanel.HealthBar;
import com.example.game1.gamepanel.Joystick;
import com.example.game1.R;
import com.example.game1.Utils;

//Player is the main character. The user controls with touch joystick.
//This class extends Circle which can later be changed to whatever the sprite will be.

public class Player extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final int MAX_HEALTH_POINTS = 10;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private final Joystick joystick;
    private HealthBar healthBar;
    private int healthPoints;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius){
        super(context,ContextCompat.getColor(context, R.color.player),positionX,positionY,radius);
        this.joystick = joystick;
        this.healthBar = new HealthBar(context,this);
        this.healthPoints = MAX_HEALTH_POINTS;
    }



    public void update() {
        //update velocity based on joystick movement
        velocityX = joystick.getActuatorX() * MAX_SPEED;
        velocityY = joystick.getActuatorY() * MAX_SPEED;
        //update position based on velocity
        positionX += velocityX;
        positionY += velocityY;

        //update direction
        if(velocityX != 0 || velocityY != 0)
        {
            //normalize velocity to get direction
            double distance = Utils.getDistanceBetweenPoints(0,0,velocityX,velocityY);
            directionX = velocityX/distance;
            directionY = velocityY/distance;

        }

    }
    public void draw(Canvas canvas) {
        super.draw(canvas);
        healthBar.draw(canvas);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        if(this.healthPoints > 0){
            this.healthPoints = healthPoints;
        }

    }
}
