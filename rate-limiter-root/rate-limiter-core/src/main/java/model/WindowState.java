package model;

import java.time.Instant;

public class WindowState {
    private Instant time;
    private int count;

    public WindowState(Instant time ,int count){
        this.time = time;
        this.count = count;
    }

    public Instant getTime(){
        return time;
    }
    public int getCount(){
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public void setTime(Instant time){
        this.time = time;
    }
}
