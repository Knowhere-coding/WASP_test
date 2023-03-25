package com.wasp.handler;

import com.wasp.data.AppData;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class InactivityEventHandler implements EventHandler<MouseEvent> {
    private final Parent root;

    public InactivityEventHandler(Parent root) {
        this.root = root;
    }

    public void start() {
        AppData.getInstance().getInactivityTimer().reset();
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, this);
        root.addEventFilter(KeyEvent.KEY_PRESSED, keyEventHandler);
    }

    public void resetTimer() {
        AppData.getInstance().getInactivityTimer().reset();
    }


    @Override
    public void handle(MouseEvent event) {
        resetTimer();
    }

    private final EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            resetTimer();
        }
    };
}

