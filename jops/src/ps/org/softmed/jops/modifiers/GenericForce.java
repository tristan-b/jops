package org.softmed.jops.modifiers;

import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Vector3f;
import org.softmed.jops.Particle;
import org.softmed.jops.cloner.StandaloneCloner;
import org.softmed.jops.valuelists.ValueListf;
import org.softmed.jops.valuelists.ValueListv3f;


public class GenericForce extends Modifier implements StandaloneCloner{

	ValueListv3f direction = new ValueListv3f();
	
	ValueListf strength = new ValueListf();
	
	ValueListf massImportance = new ValueListf();
	
	Vector3f currentDirection;
	float currentStrenght;
	float currentMassImportance;
	float massScaler;
	Vector3f particleSpeedVector = new Vector3f();
	
	Vector3f temp = new Vector3f();
	
	public GenericForce()
	{
		direction.addValue(new Vector3f(0f,-1f,0f), 0.0f);
		strength.addValue(9.8f,0f);
		massImportance.addValue(1f,0f);
	}
    
    @Override
	public void modify(Particle part) {
		massScaler = part.mass*currentMassImportance;
		massScaler = currentStrenght*(1.0f+massScaler);
		
		temp.set(currentDirection);
		temp.scale(massScaler);
		
		particleSpeedVector.set(part.direction);
		particleSpeedVector.scale(part.speed);
		
		//Vector3f.add(particleSpeedVector,temp,particleSpeedVector);
		particleSpeedVector.add(temp);
		
		part.speed = particleSpeedVector.length();
		
		
		particleSpeedVector.normalize();	
		part.direction.set(particleSpeedVector);
		
		//TODO change the speed & include the mass...
		//part.direction.add(temp);
	//	Vector3f.add(part.direction,temp,part.direction);
	//	part.speed = part.speed + currentStrenght;
		
	}
    
    @Override
	public void update(Point3f position, Matrix4f rotation,float life, float dt) {
		currentDirection = direction.getValueAt(life);
		currentMassImportance = massImportance.getValueAt(life);
		currentStrenght = strength.getValueAt(life)*dt;
		
		//temp.set(currentDirection);
		//temp.scale(currentStrenght);
		
	}

	
	
	
	
	public ValueListv3f getDirection() {
		return direction;
	}

	public void setDirection(ValueListv3f direction) {
		this.direction = direction;
	}

	public ValueListf getMassImportance() {
		return massImportance;
	}

	public void setMassImportance(ValueListf mass) {
		this.massImportance = mass;
	}

	public ValueListf getStrength() {
		return strength;
	}

	public void setStrength(ValueListf strength) {
		this.strength = strength;
	}
    
    @Override
	public void setResolution(int resolution) {
		direction.setResolution(resolution);
		strength.setResolution(resolution);
		massImportance.setResolution(resolution);
		
	}
    
    @Override
	public void recompile() {
		direction.compileArray();
		strength.compileArray();
		massImportance.compileArray();
	}
    
	public Object getStandaloneCopy() {
		GenericForce copy = new GenericForce();
		copy.direction = direction.getStandaloneCopy();
		copy.strength = strength.getStandaloneCopy();
		copy.massImportance = massImportance.getStandaloneCopy();
		return copy;
	}
}
