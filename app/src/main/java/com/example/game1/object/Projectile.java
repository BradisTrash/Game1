package com.example.game1.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.game1.GameLoop;
import com.example.game1.R;

public class Projectile extends Circle{
    public static final double SPEED_PIXELS_PER_SECOND = 800.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    public Projectile(Context context, Player player) {
        super(context,
                ContextCompat.getColor(context, R.color.projectile),
                player.getPositionX(),
                player.getPositionY(),
                20
        );
        velocityX = player.getDirectionX()*MAX_SPEED;
        velocityY = player.getDirectionY()*MAX_SPEED;

    }

    @Override
    public void update() {
        positionX += velocityX;
        positionY += velocityY;
    }
}
