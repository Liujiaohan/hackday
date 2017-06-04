package com.hackday.anonymousmeet.Entity;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hackday.anonymousmeet.OnMoveUp;

/**
 * Created by Liu jiaohan on 2017-06-03.
 */

public class Animal extends ImageView {
    private int animationResource;
    private AnimationDrawable animationDrawable;
    private int lastX,lastY;
    private int screenWidth,screenHeight;
    private OnMoveUp iOnMoveUp;

    public Animal(Context context) {
        super(context);
        init();
    }

    public Animal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Animal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setAnimationResource(int animationResource){
        this.animationResource=animationResource;
        this.setImageResource(animationResource);
    }

    public void setOnMoveUp(OnMoveUp onMoveUp){
        this.iOnMoveUp=onMoveUp;
    }
    public void runAnimation(){
        animationDrawable=(AnimationDrawable) this.getDrawable();
        animationDrawable.run();
    }

    public void stopAnimation(){
        animationDrawable.stop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;

                int top = this.getTop() + dy;


                int left = this.getLeft() + dx;


                if (top <= 0) {
                    top = 0;
                }
                if (top >= screenHeight - this.getHeight() ) {
                    top = screenHeight - this.getHeight();
                }
                if (left >= screenWidth - this.getWidth()) {
                    left = screenWidth - this.getWidth();
                }

                if (left <= 0) {
                    left = 0;
                }
                this.layout(left, top, left+this.getWidth(), top+this.getHeight());

                this.postInvalidate();
                this.runAnimation();

                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();

                break;
            case MotionEvent.ACTION_UP:
                this.stopAnimation();
                iOnMoveUp.OnMoveEnd();
                break;

        }
        return true;
    }


    private void init() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();

    }
}
