package org.softmed.jops.modifiers.pointmass;

import org.openmali.vecmath2.Vector3f;
import org.softmed.jops.modifiers.DistanceCalculator;
import org.softmed.jops.modifiers.PointMass;


public class linear extends DistanceCalculator{

	public linear(PointMass pointMass) {
		super(pointMass);
	}

	@Override
	public float calculateStrength(Vector3f vector) {
		return vector.length()*pointMass.getCurrentDistanceImportance();
	}

}
