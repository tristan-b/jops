package org.softmed.jops;


import org.openmali.FastMath;
import org.openmali.vecmath2.Point3f;
import org.softmed.jops.valuelists.ValueListp3f;

//import particle.math.ParticleFastMath;

public class PositionAnimator extends InfoObject{
	
//	ParticleFastMath math = new ParticleFastMath();
	
	private ValueListp3f position = new ValueListp3f();

	private ValueListp3f bias = new ValueListp3f();

	private ValueListp3f biasAngle = new ValueListp3f();

	private ValueListp3f biasSpin = new ValueListp3f();

	
	
	private Point3f currentBias;

	private Point3f currentBiasAngle;

	private Point3f currentBiasSpin;

	private Point3f currentPosition;
	

	

	public void rebuild() {
//		math = new ParticleFastMath();

	}

	public void recompile() {
		thisrebuildIfNecessary();

		position.compileArray();
		bias.compileArray();
		biasAngle.compileArray();
		biasSpin.compileArray();

	}

	private void thisrebuildIfNecessary() {
//		if (math == null)
//			math = new ParticleFastMath();

		if (bias == null)
			bias = new ValueListp3f();
		if (biasAngle == null)
			biasAngle = new ValueListp3f();
		if (biasSpin == null)
			biasSpin = new ValueListp3f();

		currentBias = new Point3f();
		currentBiasAngle = new Point3f();
		currentBiasSpin = new Point3f();

		currentPosition = new Point3f();
		
	}

	public void update(float time, float timelapse) {

		currentPosition = position.getValueAt(time);

		currentBias = bias.getValueAt(time);

			currentBiasAngle = new Point3f();
			currentBiasSpin = new Point3f();

//			if (math == null)
//				math = new ParticleFastMath();

		
		currentBiasAngle.set(biasAngle.getValueAt(time));
		currentBiasSpin.set(biasSpin.getValueAt(time));
		currentBiasSpin.scale(time);

		//Vector3f.add(currentBiasAngle, currentBiasSpin, currentBiasAngle);
		currentBiasAngle.add(currentBiasSpin);

	}

	
	
	public void setInitialPosition(Point3f position) {

		// second - add bias
		//Vector3f.add(position, currentBias, position);
		position.add(currentBias);

		// third - rotate 3 axis on origin with bias
		rotateOnAngleVector(position, currentBiasAngle);

		// forth - add position
		position.addX( currentPosition.getX() );
		position.addY( currentPosition.getY() );
		position.addZ( currentPosition.getZ() );

	}

	protected void rotateOnAngleVector(Point3f temp, Point3f angles) {
		// System.out.println("Angle Vector->" + angles.toString());
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

		position.setResolution(resolution);

		bias.setResolution(resolution);
		biasAngle.setResolution(resolution);
		biasSpin.setResolution(resolution);

	}

	public ValueListp3f getBias() {
		return bias;
	}

	public void setBias(ValueListp3f bias) {
		this.bias = bias;
	}

	public ValueListp3f getBiasAngle() {
		return biasAngle;
	}

	public void setBiasAngle(ValueListp3f biasAngle) {
		this.biasAngle = biasAngle;
	}

	public ValueListp3f getBiasSpin() {
		return biasSpin;
	}

	public void setBiasSpin(ValueListp3f biasSpin) {
		this.biasSpin = biasSpin;
	}

	public ValueListp3f getPosition() {
		return position;
	}

	public void setPosition(ValueListp3f position) {
		this.position = position;
	}


}
