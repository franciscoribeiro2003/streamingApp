package com.netflixpp.models;


import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTouchListener implements View.OnTouchListener {
    private final View view;
    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context ctx, View view) {
        gestureDetector = new GestureDetector(ctx, new GestureListener());
        this.view= view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    public final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            onDoubleTouch();
            return super.onDoubleTap(e);
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (view != null) {
                onSingleTouch();
            }
            return true;
        }
    }

    public void onDoubleTouch() {

    }
    public void onSingleTouch() {

    }

}