package input.actions;

import timer.FrameRater;
import view.Camera;
import view.Display;
import input.ButtonActuator;

public class PressedButton implements ButtonActuator {

	boolean pressed;

	public void keyPressed(int key, FrameRater fr, Camera cr, Display d) {
		pressed = true;

	}

	public void keyReleased(int key, FrameRater fr, Camera cr, Display d) {
		pressed = false;

	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

}
