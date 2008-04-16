// FrontEnd Plus GUI for JAD
// DeCompiled : FastMath.class

package org.softmed.jops.math;

import org.openmali.FastMath;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Vector3f;

public class AnglesFastMath {

	static int resolution;

	static float cos[];

	static float sin[];

	static float tan[];

	static float arctan[];
	static int maxArcTanValue = (int)(Math.PI * 1000 * 2);

	public AnglesFastMath() {
	}
	
	
	
	public static float atan(float value) {
		return atan((int)value);
	}

	public  static float tan(float angle) {
		return tan((int)angle);
	}

	
	
	public  static float atan(int value) {
		if (value < 0)
			value = -value;

		value %= maxArcTanValue;

		return arctan[value];
	}

	public  static float tan(int angle) {
		angle %= 360;

		if (angle < 0)
			return -tan[-angle];

		return tan[angle];
	}

	public  static float sin(int angle) {
		angle %= 360;

		if (angle < 0)
			return -sin[-angle];

		return sin[angle];
	}

	public  static float cos(int angle) {
		if (angle < 0)
			angle = -angle;

		angle %= 360;

		return cos[angle];
	}

	public  static float sin(float angle) {
		angle %= 360f;

		if (angle < 0)
			return -sin[(int) -angle];

		return sin[(int) angle];
	}

	public  static float cos(float angle) {
		angle %= 360f;

		if (angle < 0)
			angle = -angle;

		return cos[(int) angle];
	}

	protected void write(String mgs) {
		System.out.println(mgs);
	}

	

	public  static void convertSphericalToCartesian(float horizontalAngle,
			float verticalAngle, Point3f destination) {
		destination.setX( cos(horizontalAngle) * sin(verticalAngle) );
		destination.setZ( sin(horizontalAngle) * sin(verticalAngle) );
		destination.setY( cos(verticalAngle) );
	}
	
	public  static  void convertSphericalToCartesianRadians(float horizontalAngle,
			float verticalAngle, Vector3f destination) {
		destination.setX( FastMath.cos(horizontalAngle) * FastMath.sin(verticalAngle) );
		destination.setZ( FastMath.sin(horizontalAngle) * FastMath.sin(verticalAngle) );
		destination.setY( FastMath.cos(verticalAngle) );
	}
	
	public  static void convertSphericalToCartesian(float horizontalAngle,
			float verticalAngle, Vector3f destination) {
		destination.setX( cos(horizontalAngle) * sin(verticalAngle) );
		destination.setZ( sin(horizontalAngle) * sin(verticalAngle) );
		destination.setY( cos(verticalAngle) );
	}
	
	public  static  void convertSphericalToCartesianRadians(float horizontalAngle,
			float verticalAngle, Point3f destination) {
		destination.setX( FastMath.cos(horizontalAngle) * FastMath.sin(verticalAngle) );
		destination.setZ( FastMath.sin(horizontalAngle) * FastMath.sin(verticalAngle) );
		destination.setY( FastMath.cos(verticalAngle) );
	}

	static {
		resolution = 392;
		cos = new float[resolution];
		sin = new float[resolution];
		tan = new float[resolution];
		arctan = new float[maxArcTanValue];
		double value = 0.0D;
		for (int i = 0; i < resolution; i++) {
			value = i;
			cos[i] = (float) Math.cos(Math.toRadians(value));
			sin[i] = (float) Math.sin(Math.toRadians(value));
			tan[i] = (float) Math.tan(Math.toRadians(value));
		}

		
		for (int i = 0; i < maxArcTanValue; i++) {
			arctan[i] = (float) Math.atan((double) ((float) i / 2000.0f));
		}

	}
}
