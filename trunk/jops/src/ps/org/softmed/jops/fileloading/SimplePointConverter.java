package org.softmed.jops.fileloading;

import org.openmali.vecmath2.Point3f;


public class SimplePointConverter implements CustomConverter{

	public Object fromString(String msg) {

		String[] results = msg.split("\\s* \\s*");

		
		float x = Float.parseFloat(results[0]);
		float y = Float.parseFloat(results[1]);
		float z = Float.parseFloat(results[2]);
		
		return new Point3f(x, y, z);
	}

	public String toString(Object obj) {
		Point3f v = (Point3f) obj;
		return v.getX()+" "+v.getY()+ " "+ v.getZ();
	}

}
