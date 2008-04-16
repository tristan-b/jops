package org.softmed.jops;

import org.openmali.FastMath;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Tuple3f;
import org.softmed.jops.valuelists.ValueListp3f;

//import particle.math.ParticleFastMath;

public class SpaceAnimator extends InfoObject {

	private ValueListp3f spaceAngle = new ValueListp3f();

	private ValueListp3f spaceSpin = new ValueListp3f();

	private Point3f currentSpaceAngle = new Point3f(),
			currentSpaceSpin = new Point3f();

	public void rebuild() {

	}

	public void recompile() {
		thisrebuildIfNecessary();

		spaceAngle.compileArray();
		spaceSpin.compileArray();

	}

	private void thisrebuildIfNecessary() {

		currentSpaceAngle = new Point3f();
		currentSpaceSpin = new Point3f();
	}

	public void update(float time, float timelapse) {

		currentSpaceAngle = new Point3f();
		currentSpaceSpin = new Point3f();

		currentSpaceAngle.set((Tuple3f) spaceAngle.getValueAt(time));
		currentSpaceSpin.set((Tuple3f) spaceSpin.getValueAt(time));
		currentSpaceSpin.scale(time);

		currentSpaceAngle.add(currentSpaceSpin);
	}

	public void setInitialPosition(Point3f position) {

		// first - rotate 3 axis on origin
		rotateOnAngleVector(position, currentSpaceAngle);

	}

	protected void rotateOnAngleVector(Point3f temp, Point3f angles) {
		rotateOnX(temp, angles.getX());
		rotateOnY(temp, angles.getY());
		rotateOnZ(temp, angles.getZ());
	}

	protected void rotateOnX(Point3f temp, float angle) {

		float y = FastMath.cos(angle) * temp.getY() - FastMath.sin(angle) * temp.getZ();

		float z = FastMath.sin(angle) * temp.getY() + FastMath.cos(angle) * temp.getZ();

		temp.setY( y );
		temp.setZ( z );
	}

	protected void rotateOnY(Point3f temp, float angle) {
		float x = FastMath.cos(angle) * temp.getX() + FastMath.sin(angle) * temp.getZ();

		float z = -FastMath.sin(angle) * temp.getX() + FastMath.cos(angle) * temp.getZ();

		temp.setX( x );
		temp.setZ( z );

	}

	protected void rotateOnZ(Point3f temp, float angle) {
		float x = FastMath.cos(angle) * temp.getX() - FastMath.sin(angle) * temp.getY();

		float y = FastMath.sin(angle) * temp.getX() + FastMath.cos(angle) * temp.getY();

		temp.setY( y );
		temp.setX( x );

	}

	public void setResolution(int resolution) {
		spaceAngle.setResolution(resolution);
		spaceSpin.setResolution(resolution);
	}

	public ValueListp3f getSpaceAngle() {
		return spaceAngle;
	}

	public void setSpaceAngle(ValueListp3f spaceAngle) {
		this.spaceAngle = spaceAngle;
	}

	public ValueListp3f getSpaceSpin() {
		return spaceSpin;
	}

	public void setSpaceSpin(ValueListp3f spaceSpin) {
		this.spaceSpin = spaceSpin;
	}

}
