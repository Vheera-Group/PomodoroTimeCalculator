package src.models;

public class PomodoroTime {
    int pomodoro;
    int rest;
    int longRest;
    
    public PomodoroTime(int pomodoro, int rest, int longRest) {
        this.pomodoro = pomodoro;
        this.rest = rest;
        this.longRest = longRest;
    }

    public int getPomodoro() {
        return pomodoro;
    }

    public void setPomodoro(int pomodoro) {
        this.pomodoro = pomodoro;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public int getLongRest() {
        return longRest;
    }

    public void setLongRest(int longRest) {
        this.longRest = longRest;
    }
    
}
