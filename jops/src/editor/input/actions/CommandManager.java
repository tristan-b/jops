package input.actions;

import java.awt.event.KeyEvent;

import input.ButtonListener;
import input.KeyCodes;
import timer.FrameRater;
import view.Camera;
import view.Display;

public class CommandManager {

	boolean active = false;

	PressedButton forward = new PressedButton();

	PressedButton back = new PressedButton();

	PressedButton left = new PressedButton();

	PressedButton right = new PressedButton();

	PressedButton up = new PressedButton();

	PressedButton down = new PressedButton();

	// private ButtonListener al;

	public void setup(ButtonListener al) {

		// this.al = al;

		al.addActuator(KeyEvent.VK_W, forward);
		al.addActuator(KeyEvent.VK_S, back);
		al.addActuator(KeyEvent.VK_A, left);
		al.addActuator(KeyEvent.VK_D, right);

		al.addActuator(KeyEvent.VK_SPACE, up);
		al.addActuator(KeyEvent.VK_CONTROL, down);
		al.addActuator(KeyCodes.MOUSE_WHEEL_UP, up);
		al.addActuator(KeyCodes.MOUSE_WHEEL_DOWN, down);
		al.setCmanager(this);

	}

	public void process(FrameRater fr, Camera cr, Display d) {

		if (!active)
			return;

		if (forward.isPressed()) {
			cr.moveForward(fr.getTimeLapse());
		}
		if (back.isPressed()) {
			cr.moveBackward(fr.getTimeLapse());
		}
		if (left.isPressed()) {
			cr.leftStraff(fr.getTimeLapse());
		}
		if (right.isPressed()) {
			cr.rightStraff(fr.getTimeLapse());
		}
		if (up.isPressed()) {
			cr.moveUp(fr.getTimeLapse());
		}
		if (down.isPressed()) {
			cr.moveDown(fr.getTimeLapse());
		}

	}

	public void reset() {
		forward.setPressed(false);
		back.setPressed(false);
		left.setPressed(false);
		right.setPressed(false);
		up.setPressed(false);
		down.setPressed(false);
		// forward.setPressed(false);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
