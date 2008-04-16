package org.softmed.jops.modifiers;

import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.softmed.jops.Particle;
import org.softmed.jops.cloner.StandaloneCloner;
import org.softmed.jops.valuelists.ValueListf;


public class AirFriction extends Modifier implements StandaloneCloner{

	ValueListf strength = new ValueListf();

	ValueListf sizeImportance = new ValueListf();

	float currentSizeImportance;

	float sizeScaler;

	float currentStrenght;

	public AirFriction() {
		strength.addValue(1.2f, 0f);
		sizeImportance.addValue(1.0f, 0f);
	}
	
    @Override
	public void modify(Particle part) {
		sizeScaler = part.size * currentSizeImportance;
		sizeScaler = currentStrenght * (1.0f + sizeScaler);

		part.speed -= part.speed * sizeScaler;

	}
    
    @Override
	public void update(Point3f position, Matrix4f rotation,float life, float dt) {
		currentSizeImportance = sizeImportance.getValueAt(life);
		currentStrenght = strength.getValueAt(life) * dt;
	}
    
    @Override
	public void recompile() {
		strength.compileArray();
		sizeImportance.compileArray();

	}
    
    @Override
	public void setResolution(int resolution) {
		strength.setResolution(resolution);
		sizeImportance.setResolution(resolution);

	}
    
	public ValueListf getStrength() {
		return strength;
	}

	public ValueListf getSizeImportance() {
		return sizeImportance;
	}
	
	public Object getStandaloneCopy() {
		AirFriction copy = new AirFriction();
		copy.sizeImportance = sizeImportance.getStandaloneCopy();
		copy.strength = strength.getStandaloneCopy();
		return copy;
	}

	public void setSizeImportance(ValueListf sizeImportance) {
		this.sizeImportance = sizeImportance;
	}

	public void setStrength(ValueListf strength) {
		this.strength = strength;
	}

}
