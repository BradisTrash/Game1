package com.example.game1.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.game1.R;

/**
 * GameOver is a panel that draws Game Over text to screen
 */
public class GameOver {
    private Context context;

    public GameOver(Context context){
        this.context = context;
    }


    public void draw(Canvas canvas) {
        String text = "GAME OVER";
        float x = 800;
        float y = 200;

        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.gameOver);
        paint.setColor(color);
        float textSize = 150;
        paint.setTextSize(textSize);
        canvas.drawText(text,x,y,paint);
    }
}
