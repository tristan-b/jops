package org.softmed.jops.modifiers;

import org.openmali.vecmath2.Vector3f;

public class InverseDistanceCalculator extends DistanceCalculator{
	
	private DistanceCalculator calculator;
	
	public InverseDistanceCalculator(PointMass pointMass, DistanceCalculator calculator) {
		super(pointMass);
		this.calculator = calculator;
	}
    
    @Override
	public float calculateStrength(Vector3f vector){		
			return 1f/calculator.calculateStrength(vector);
	}
	
}
