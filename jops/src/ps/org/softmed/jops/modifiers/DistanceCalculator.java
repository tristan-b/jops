package org.softmed.jops.modifiers;

import org.openmali.vecmath2.Vector3f;

public abstract class DistanceCalculator {

	protected PointMass pointMass;
	
	public DistanceCalculator(PointMass pointMass)
	{
		this.pointMass = pointMass;
	}
	
	
	public abstract float calculateStrength(Vector3f vector);
	
}
