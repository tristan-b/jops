package org.softmed.jops.fileloading.converters;

import org.openmali.vecmath2.Colorf;

public class ColorConverter implements IValueConverter {

	public Object fromExternalToInternal(Object external) {
		if (external instanceof Colorf) {
			Colorf p3f = (Colorf) external;
			p3f.div( 255f, 255f, 255f );
			return p3f;
		} else if (external instanceof Float){
			float color = (Float) external;
			color = color / 255f;
			return color;
		}else if (external instanceof Integer){
			float color = (Integer) external;
			color = color / 255f;
			return color;
		}else return null;

	}

	public Object fromInternalToExternal(Object internal) {
		if (internal instanceof Colorf) {
			Colorf p3f = (Colorf) internal;
            p3f.mul( 255f, 255f, 255f );
			return p3f;
		} else if (internal instanceof Float){
			float color = (Float) internal;
			color = color * 255f;
			return color;
		}else if (internal instanceof Integer){
			float color = (Integer) internal;
			color = color * 255f;
			return color;
		}else return null;

	}
}
