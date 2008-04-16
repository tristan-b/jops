package org.softmed.jops.modifiers.pointmass;

import org.openmali.vecmath2.Vector3f;
import org.softmed.jops.modifiers.DistanceCalculator;
import org.softmed.jops.modifiers.PointMass;


public class squared extends DistanceCalculator{

	float value ;
	
	public squared(PointMass pointMass) {
		super(pointMass);
	}

	@Override
	public float calculateStrength(Vector3f vector) {
		value = vector.length();
		return pointMass.getCurrentDistanceImportance()*(value*value);
	}

}
