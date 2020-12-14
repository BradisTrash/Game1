package com.example.game1.gamepanel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.game1.Utils;


public class Joystick {

    private final int outerCircleCenterPositionX;
    private final int outerCircleCenterPositionY;
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;
    private final int outerCircleRadius;
    private final int innerCircleRadius;
    private final Paint outerCirclePaint;
    private final Paint innerCirclePaint;
    private double joystickCenterToTouchDistance;
    private boolean isPressed;
    private double actuatorX;
    private double actuatorY;


    public Joystick(int centerPositionX,int centerPositionY,int outerCircleRadius, int innerCircleRadius){
        //outer and inner circles of joystick
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

        //radii of circles
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        //paint of circles
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.WHITE);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    public void draw(Canvas canvas) {
        canvas.drawCircle(
                outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                outerCircleRadius,
                outerCirclePaint
        );

        canvas.drawCircle(
                innerCircleCenterPositionX,
                innerCircleCenterPositionY,
                innerCircleRadius,
                innerCirclePaint
        );
    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius);

    }

    public boolean isPressed(double x, double y) {
        joystickCenterToTouchDistance = Utils.getDistanceBetweenPoints(outerCircleCenterPositionX,
                outerCircleCenterPositionY,x,y
        );
        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }
    public boolean getIsPressed(){
        return isPressed;
    }

    public void setActuator(double x, double y) {
        double deltaX = x - outerCircleCenterPositionX;
        double deltaY = y - outerCircleCenterPositionY;
        double deltaDistance = Utils.getDistanceBetweenPoints(0,0,deltaX,deltaY);

        if(deltaDistance < outerCircleRadius){
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        }
        else{
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }
    }

    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }
}
