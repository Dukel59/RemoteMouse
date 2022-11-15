package com.example.remotemouse;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView {

    int mouseSensitivity = 1;
    ClientThread clientThread;
    float currentX, currentY;
    float previousX, previousY;


    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addClientThread(ClientThread thread)
    {
        this.clientThread = thread;
        if(clientThread.getState() != Thread.State.RUNNABLE)
            clientThread.start();
    }

    public void setSensitivity(int sensitivity)
    {
        mouseSensitivity = sensitivity;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            previousX = event.getX();
            previousY = event.getY();
            clientThread.update(previousX + " " + previousY + " " + mouseSensitivity);
        } else if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            if(event.getX() > previousX) {
                currentX = event.getX() - previousX;
            } else {
                currentX = -(previousX - event.getX());
            }

            if(event.getY() > previousY) {
                currentY = event.getY() - previousY;
            } else {
                currentY = -(previousY - event.getY());
            }

            if(clientThread != null) {
                int icurrentX = Math.round(currentX);
                int icurrentY = Math.round(currentY);
                clientThread.update(icurrentX + " " + icurrentY + " " + mouseSensitivity + "/");
            }
        }
        return true;
    }
}
