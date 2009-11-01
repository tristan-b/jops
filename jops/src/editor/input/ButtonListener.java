package input;

import input.actions.CommandManager;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * KeyListener.java
 *
 * Created on 23 de Fevereiro de 2006, 17:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
class ActData {
	public ActData(ButtonActuator a, boolean p) {
		act = a;
		pressed = p;
	}

	ButtonActuator act;

	boolean pressed;
}

/**
 * 
 * @author eu
 */
public class ButtonListener extends InputHandler implements KeyListener,
		java.awt.event.MouseListener {

	CommandManager cmanager;

	boolean pressed;

	int key;

	ActData temp;

	protected Map<Integer, ButtonActuator> map = new HashMap<Integer, ButtonActuator>();

	List<ActData> actuators = new ArrayList<ActData>();

	private ButtonActuator actuator;



	public void reset() {
		actuators.clear();
		if (cmanager != null)
			cmanager.reset();
	}

	public void addActuator(int key, ButtonActuator act) {
		map.put(key, act);

	}

	public void removeActuator(int key) {
		map.remove(key);
	}

	/** Creates a new instance of KeyListener */
	public ButtonListener() {
	}

	public void keyPressed(KeyEvent e) {
		if (!capture )
			return;

		key = e.getKeyCode();
		actuator = map.get(e.getKeyCode());
		if (actuator != null)
			actuators.add(new ActData(actuator, true));

	}

	public void keyReleased(KeyEvent e) {
		if (!capture )
			return;

		key = e.getKeyCode();
		actuator = map.get(e.getKeyCode());
		if (actuator != null)
			actuators.add(new ActData(actuator, false));

	}

	@Override
	protected void processInput() {

		for (int i = 0; i < actuators.size(); i++) {
			temp = actuators.get(i);

			// TODO bug here, i'm, not getting the key from what
			// was pressed at the time, just the last pressed key
			if (temp.pressed)
				temp.act.keyPressed(key, frameRater, camera, display);
			else
				temp.act.keyReleased(key, frameRater, camera, display);
		}

		actuators.clear();

		if (cmanager != null)
			cmanager.process(frameRater, camera, display);

	}

	public void mousePressed(MouseEvent e) {

		if (!capture)
			return;

		createEvent(e);
		keyPressed(createEvent(e));
	}

	public void mouseReleased(MouseEvent e) {

		if (!capture)
			return;

		keyReleased(createEvent(e));
	}

	@SuppressWarnings("deprecation")
	protected KeyEvent createEvent(final MouseEvent e) {
		int code = 0;
		if (e.getButton() == MouseEvent.BUTTON1) {
			code = KeyCodes.MOUSE1;
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			code = KeyCodes.MOUSE2;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			code = KeyCodes.MOUSE3;
		} else
		// not working here man
		if (e.getButton() == MouseEvent.MOUSE_WHEEL) {
			if (e.getClickCount() < 0)
				code = KeyCodes.MOUSE_WHEEL_DOWN;
			else
				code = KeyCodes.MOUSE_WHEEL_UP;
		}

		KeyEvent event = new KeyEvent((Component) e.getSource(), 0, 0, 0, code);
		return event;
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
	}

	public void keyTyped(KeyEvent e) {
	}

	public CommandManager getCmanager() {
		return cmanager;
	}

	public void setCmanager(CommandManager cmanager) {
		this.cmanager = cmanager;
	}



}
