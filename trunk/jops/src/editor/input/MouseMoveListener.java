package input;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/*
 * MouseListener.java
 *
 * Created on 23 de Fevereiro de 2006, 17:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 * 
 * @author eu
 */
public class MouseMoveListener extends InputHandler implements
		MouseMotionListener {
	float t1 = 0;

	float t2 = 0;

	Robot robot;

	private ButtonListener actionListener;

	Point center = new Point();

	Point topLeft = new Point();

	Point result = new Point(0, 0);

	private float sens = 10f;

	//private KeyEvent event;

	// private boolean centering;

	private float mVerticalAngleResolution = 1.0f;

	private float mHorizontalAngleResolution = 1.0f;

	/** Creates a new instance of MouseListener */
	public MouseMoveListener() {
		try {
			robot = new Robot();
		} catch (AWTException ex) {
			ex.printStackTrace();
		}
	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		if (capture) {
		    // This is the Java 1.5 way!
		    final Point componentAbsLoc = e.getComponent().getLocationOnScreen();
			processInput(componentAbsLoc.x + e.getX(), componentAbsLoc.y + e.getY());
		}
	}

	protected void processInput(int tx, int ty) {
		if (tx != center.x)
			result.x = tx;
		// result.x /= 2;
		if (ty != center.y)
			result.y = ty;
		// result.y /= 2;

	}
	
    @Override
	public void destroy() {
	}

	public void configureMouse(int x, int y, int mX, int mY) {
		topLeft.x = x;
		topLeft.y = y;
		center.x = x + mX;
		center.y = y + mY;

		result.x = center.x;
		result.y = center.y;

		// re-center
		// result.x = x;
		// result.x = y;
		// centering = true;
		// robot.mouseMove(x, y); // this is not letting the window be
		// resizeddddd...
	}

	public ButtonListener getActionListener() {
		return actionListener;
	}

	public void setActionListener(ButtonListener actionListener) {
		this.actionListener = actionListener;
	}
	
    @Override
	protected void processInput() {

		// System.out.println("rx->" + result.x + " |ry->" + result.y);
		// System.out.println("cx->" + center.x + " |cy->" + center.y);

		t1 = 0;

		if (result.y != center.y) {
			t1 = sens * frameRater.getTimeLapse() * (result.y - center.y);
			// System.out.println("V->" + t1);
		}
		t2 = 0;

		if (result.x != center.x) {
			t2 = sens * frameRater.getTimeLapse() * (center.x - result.x);
			// System.out.println("H->" + t2);

		}

		camera.changeVerticalAngle(t1 * mVerticalAngleResolution);
		camera.changeLateralAngle(t2 * mHorizontalAngleResolution);
		camera.update();
		// System.out.println("dx->" + t2 + " |dy->" + t1);
		// System.out.println( camera.getStringInfo());

		// centering = true;
		result.x = center.x;
		result.y = center.y;
		centerMouse();
	}

	public void centerMouse() {
		robot.mouseMove(center.x, center.y);
	}

}
