package input;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import org.openmali.FastMath;
import org.openmali.angle.PolarCoordinate2f;
import org.openmali.vecmath2.Vector2f;
import org.openmali.vecmath2.Vector3f;

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
		MouseMotionListener, MouseWheelListener {

	MouseSmoother ms = new MouseSmoother();

	boolean moving = false;
	int maxStopCount = 20;
	int stopCount = 0;

	int maxIndex = 2;
	int index = 0;
	Vector2f[] mouseSmooth = new Vector2f[maxIndex];
	float speed = 10;
	// polar camera variables
	boolean polar = true;
	final float DEFAULT_DISTANCE_TO_AXIS = 3.0f;
	Vector3f DEFAULT_POLAR_POSITION = new Vector3f(DEFAULT_DISTANCE_TO_AXIS, 0,
			0);

	float verticalAngleBias = FastMath.PI_HALF;
	float horizontalAngleBias = 0;

	float verticalAngle = verticalAngleBias;
	float horizontalAngle = 0f;
	float distanceToAxis = DEFAULT_DISTANCE_TO_AXIS;
	float polarAngleResolution = 0.001f;

	float sphereY = 0f;
	float sphereX = 0f;

	// FPS - style camera variables
	float yt = 0;

	float xt = 0;

	Robot robot;

	private ButtonListener actionListener;

	Point center = new Point();

	Point topLeft = new Point();

	Point result = new Point(0, 0);

	private float sens = 10f;

	// private KeyEvent event;

	// private boolean centering;

	private float mVerticalAngleResolution = 1.0f;

	private float mHorizontalAngleResolution = 1.0f;
	private boolean reseting;

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
			final Point componentAbsLoc = e.getComponent()
					.getLocationOnScreen();
			processInput(componentAbsLoc.x + e.getX(), componentAbsLoc.y
					+ e.getY());
		}
	}

	protected void processInput(int tx, int ty) {
		result.x = tx;
		result.y = ty;

		if (result.y != center.y || result.x != center.x) {

			yt = 0;

			if (result.y != center.y) {
				yt = sens * (result.y - center.y);// / dt;
			}
			xt = 0;

			if (result.x != center.x) {
				xt = sens * (center.x - result.x);// / dt;

			}
			System.out.println("xt: " + xt + " yt: " + yt);
			ms.addMovement(xt, yt);
		}

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

	void moving() {
		// if()
		System.out.print("MOVING --- ");
		// moving2();

	}

	void notMoving() {
		System.out.println("NOT MOVING!!!!");
	}

	public void setCapture(boolean capture) {
		this.capture = capture;
		moving = false;
		stopCount = 0;
		ms.reset();
		// notMoving();
	}

	@Override
	protected void processInput() {

		Vector2f v = ms.getTotalMovement();
		if (v.length() > 0) {
			// movement occourred
			if (!moving) {
				moving = true;
				moving();
			}

		} else {
			// no movement
			if (moving) {
				moving = false;
				notMoving();
			}
		}
		// move

		if (!moving)
			return;
		else
			moving2();

		ms.reset();

		camera.update();

		result.x = center.x;
		result.y = center.y;

		centerMouse();

	}

	private void moving2() {
		Vector2f temp = ms.getAverageMovement();
		xt = temp.getX();
		yt = temp.getY();

		float dt = frameRater.getTimeLapse();
		System.out.println("sxt: " + xt + " syt: " + yt);

		if (polar) {

			float vert = yt * polarAngleResolution;// *
			// frameRater.getTimeLapse()
			// ;
			float horz = xt * polarAngleResolution;// *
			// frameRater.getTimeLapse()
			// ;

			// horizontalAngle += -horz;
			// verticalAngle += vert;

			if (horz != 0)
				if (horz < 0)
					horizontalAngle += dt * speed;
				else
					horizontalAngle -= dt * speed;

			if (vert != 0)
				if (vert < 0)
					verticalAngle -= dt * speed;
				else
					verticalAngle += dt * speed;

			// limits the angles
			if (horizontalAngle < 0f)
				horizontalAngle += 2 * FastMath.PI;
			if (horizontalAngle > 2 * FastMath.PI)
				horizontalAngle -= 2 * FastMath.PI;

			if (verticalAngle < 0)
				verticalAngle = 0;
			if (verticalAngle > FastMath.PI)
				verticalAngle = FastMath.PI;

			float x = distanceToAxis * FastMath.sin(verticalAngle)
					* FastMath.cos(horizontalAngle);
			float z = distanceToAxis * FastMath.sin(verticalAngle)
					* FastMath.sin(horizontalAngle);
			float y = distanceToAxis * FastMath.cos(verticalAngle);

			camera.mPosition.set(x, y, z);
			// camera.setLookingAt(0, 0, 0);
			// System.out.println("H: " + horizontalAngle + "V: " +
			// verticalAngle);
			// System.out.println(camera.mPosition);

			float dv = FastMath.toDeg(verticalAngle);
			float dh = FastMath.toDeg(horizontalAngle);

			// System.out.println("H: " + dh + " V: " + dv);

			camera.mVerticalRotationAngle = 180 - dv;
			camera.mLateralRotationAngle = dh - 180;

			// System.out.println("CH: " + camera.mLateralRotationAngle +
			// " CV: "
			// + camera.mVerticalRotationAngle);

		} else {

			camera.changeVerticalAngle(yt * mVerticalAngleResolution);
			camera.changeLateralAngle(xt * mHorizontalAngleResolution);
			// camera.update();

			// System.out.println("dx->" + t2 + " |dy->" + t1);
			// System.out.println( camera.getStringInfo());

			// centering = true;

		}
	}

	public void centerMouse() {
		reseting = true;
		robot.mouseMove(center.x, center.y);
	}

	public boolean isPolar() {
		return polar;
	}

	public void setPolar(boolean polar) {
		this.polar = polar;
		if (polar) {
			distanceToAxis = DEFAULT_DISTANCE_TO_AXIS;
			horizontalAngle = 0f;
			verticalAngle = 0f;

			camera.mPosition.set(DEFAULT_POLAR_POSITION);
			// camera.setLookingAt(0, 0, 0);
			camera.mLateralRotationAngle = 0;
			camera.mVerticalRotationAngle = 0;
			sphereX = 1.0f;
		} else {
			// TODO reset for FPS style controls
		}
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		System.out.println("Mouse Wheel!!!!");
		if (capture && polar) {
			int rot = e.getWheelRotation();
			distanceToAxis += rot;
			// TODO update camera
			// camera.update();
			processInput();
		}
	}

}
