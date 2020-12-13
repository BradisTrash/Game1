package com.example.game1.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.game1.GameLoop;
import com.example.game1.Joystick;
import com.example.game1.R;
import com.example.game1.object.Circle;

//Player is the main character. The user controls with touch joystick.
//This class extends Circle which can later be changed to whatever the sprite will be.

public class Player extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private final Joystick joystick;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius){
        super(context,ContextCompat.getColor(context, R.color.player),positionX,positionY,radius);
        this.joystick = joystick;
    }



    public void update() {
        //update velocity based on joystick movement
        velocityX = joystick.getActuatorX() * MAX_SPEED;
        velocityY = joystick.getActuatorY() * MAX_SPEED;
        //update position based on velocity
        positionX += velocityX;
        positionY += velocityY;
    }
}
