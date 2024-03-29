package com.example.game1.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.game1.GameDisplay;
import com.example.game1.R;
import com.example.game1.object.Player;

public class HealthBar {
    private Player player;
    private float width, height,margin;
    private Paint borderPaint,healthPaint;

    /**
     * HealthBar displays player health on screen attached to player
     * @param player
     */
    public HealthBar(Context context, Player player){
        this.player = player;
        this.width = 100;
        this.height = 20;
        this.margin = 2;
        this.borderPaint = new Paint();
        int borderColor = ContextCompat.getColor(context, R.color.healthBarBorder);
        borderPaint.setColor(borderColor);
        this.healthPaint = new Paint();
        int healthColor = ContextCompat.getColor(context, R.color.healthBarHealth);
        healthPaint.setColor(healthColor);
    }
    public void draw(Canvas canvas, GameDisplay gameDisplay){
        float x = (float) player.getPositionX();
        float y = (float) player.getPositionY();
        float distanceToPlayer = 50;
        float healthPointsPercentage = (float) player.getHealthPoints()/ Player.MAX_HEALTH_POINTS;
        //Draw border
        float borderLeft,borderRight,borderTop,borderBottom;
        borderLeft = x - width/2;
        borderRight = x + width/2;
        borderBottom = y - distanceToPlayer;
        borderTop = borderBottom - height;
        canvas.drawRect(
                (float) gameDisplay.gameToDisplayCoordinateX(borderLeft),
                (float) gameDisplay.gameToDisplayCoordinateY(borderTop),
                (float) gameDisplay.gameToDisplayCoordinateX(borderRight),
                (float) gameDisplay.gameToDisplayCoordinateY(borderBottom),
                borderPaint);
        //Draw health
        float healthLeft,healthTop,healthRight,healthBottom,healthWidth,healthHeight;
        healthWidth = width - 2*margin;
        healthHeight = height - 2*margin;
        healthLeft = borderLeft + margin;
        healthRight = healthLeft + healthWidth*healthPointsPercentage;
        healthBottom = borderBottom - margin;
        healthTop = healthBottom - healthHeight;
        canvas.drawRect(
                (float) gameDisplay.gameToDisplayCoordinateX(healthLeft),
                (float) gameDisplay.gameToDisplayCoordinateY(healthTop),
                (float)gameDisplay.gameToDisplayCoordinateX(healthRight),
                (float)gameDisplay.gameToDisplayCoordinateY(healthBottom),
                healthPaint);

    }
}
