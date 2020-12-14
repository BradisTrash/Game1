package com.example.game1.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.game1.GameDisplay;

//Circle/whatever i change it to, extends the game object class for creating circles
public abstract class Circle extends GameObject {
    protected double radius;
    protected Paint paint;
    public Circle(Context context, int color, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.radius = radius;
        //sets color of circle
        paint = new Paint();
        paint.setColor(color);
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawCircle((float)gameDisplay.gameToDisplayCoordinateX(positionX),(float)gameDisplay.gameToDisplayCoordinateY(positionY),(float)radius,paint);
    }
    public static boolean isColliding(Circle obj1, Circle obj2){
        double distance = getDistanceBetweenObjects(obj1,obj2);
        double distanceToCollision = obj1.getRadius() + obj2.getRadius();
        if(distance < distanceToCollision){
            return true;
        }
        else{
            return false;
        }
    }

    private double getRadius() {
        return radius;
    }
}
