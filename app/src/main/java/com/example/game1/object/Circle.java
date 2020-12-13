package com.example.game1.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

//Circle/whatever i change it to, extends the game object class for creating circles
public abstract class Circle extends GameObject {
    protected double radius;
    protected Paint paint;
    public Circle(Context context,int color, double positionX, double positionY,double radius) {
        super(positionX, positionY);
        this.radius = radius;
        //sets color of circle
        paint = new Paint();
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float)positionX,(float)positionY,(float)radius,paint);
    }
}
