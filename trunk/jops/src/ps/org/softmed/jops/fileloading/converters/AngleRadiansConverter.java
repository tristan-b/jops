package org.softmed.jops.fileloading.converters;

import org.openmali.FastMath;
import org.openmali.vecmath2.Point3f;

public class AngleRadiansConverter implements IValueConverter {

	public Object fromExternalToInternal(Object external) {
		if (external instanceof Point3f) {
			Point3f p3f = (Point3f) external;
			p3f.mulX( FastMath.PI / 180f );
			p3f.mulY( FastMath.PI / 180f );
			p3f.mulZ( FastMath.PI / 180f );
			return p3f;
		} else {
			float degress = (Float) external;
			float rads =  degress * FastMath.PI / 180f;
			return rads;
		}
	}

	public Object fromInternalToExternal(Object internal) {
		if (internal instanceof Point3f) {
			Point3f p3f = (Point3f) internal;
			p3f.mulX( 180f / FastMath.PI );
			p3f.mulY( 180f / FastMath.PI );
			p3f.mulZ( 180f / FastMath.PI );
			return p3f;
		} else {
			float radians = (Float) internal;
			float degrees =  radians * 180f / FastMath.PI;
			return degrees;
		}
	}
}
