package org.softmed.jops.modifiers.pointmass;

import org.openmali.vecmath2.Vector3f;
import org.softmed.jops.modifiers.DistanceCalculator;
import org.softmed.jops.modifiers.PointMass;


public class constant extends DistanceCalculator{

	public constant(PointMass pointMass) {
		super(pointMass);
	}

	@Override
	public float calculateStrength(Vector3f vector) {
		return 1f;
	}

}
