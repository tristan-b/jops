package org.softmed.jops.fileloading;

import org.openmali.vecmath2.Colorf;

public class SimpleColorConverter implements CustomConverter {

	public Object fromString(String msg) {
		String[] results = msg.split("\\s* \\s*");
//		try {
//			int r = Integer.parseInt(results[0]);
//			int g = Integer.parseInt(results[1]);
//			int b = Integer.parseInt(results[2]);
//			return new Color3f(r / 255f, g / 255f, b / 255f);
//		} catch (Throwable t) {
//			t.printStackTrace();
//		}

		float r = Float.parseFloat(results[0]);
		float g = Float.parseFloat(results[1]);
		float b = Float.parseFloat(results[2]);
		return new Colorf(r, g, b);
	}

	public String toString(Object obj) {
		Colorf c = (Colorf) obj;
		return c.getRed() + " " + c.getGreen() + " " + c.getBlue();
	}

}
