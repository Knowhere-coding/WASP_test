package com.wasp.handler;

import com.wasp.data.AppData;

import java.util.Timer;
import java.util.TimerTask;

public class InactivityTimer {
    private static final InactivityTimer INSTANCE = new InactivityTimer();
    private Timer timer = new Timer();
    private final int INACTIVITY_PERIOD = 10*1000;

    private InactivityTimer() {}

    public static InactivityTimer getInstance() {
        return INSTANCE;
    }

    public void reset() {
        AppData.getInstance().setInactive(false);
        timer.cancel();
        timer = new Timer();
        timer.schedule(new LogoutTimerTask(), this.INACTIVITY_PERIOD);
    }

    public void start() {
        timer.schedule(new LogoutTimerTask(), this.INACTIVITY_PERIOD);
    }

    private static class LogoutTimerTask extends TimerTask {
        @Override
        public void run() {
            AppData.getInstance().setInactive(true);
        }

        @Override
        public long scheduledExecutionTime() {
            return super.scheduledExecutionTime();
        }
    }
}

