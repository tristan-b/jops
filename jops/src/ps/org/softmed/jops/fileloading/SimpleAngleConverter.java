package org.softmed.jops.fileloading;

public class SimpleAngleConverter implements CustomConverter{

	public Object fromString(String msg) {
		float angles = Float.parseFloat(msg);


		return angles / 180f;
	}

	public String toString(Object obj) {
		return ""+obj;
	}
}
