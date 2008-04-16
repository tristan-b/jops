/*
 * SphereGenerator.java
 *
 * Created on 26 de Fevereiro de 2006, 0:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.space;

import org.softmed.jops.Particle;
import org.softmed.jops.cloner.StandaloneCloner;
import org.softmed.jops.math.AnglesFastMath;
import org.softmed.jops.random.RandomGenerator;
import org.softmed.jops.valuelists.ValueListf;

//import particle.math.ParticleFastMath;

/**
 * 
 * @author eu
 */
public class SphereGenerator extends GeneratorSpace implements StandaloneCloner{
//	ParticleFastMath math = new ParticleFastMath();

	ValueListf innerRadius = new ValueListf();

	ValueListf outerRadius = new ValueListf();

	float hangle;

	float vangle;

	float radius;

	private float cinnerRadius;

	private float couterRadius;
	
	/** Creates a new instance of SphereGenerator */
	public SphereGenerator() {
		innerRadius.addValue(0.3f,0.0f);
		outerRadius.addValue(0.5f,0.0f);
	}
	
    @Override
	public void generate(Particle part) {
		hangle = generator.getFloat(360.0f);
		vangle = generator.getFloat(360.0f);
		AnglesFastMath.convertSphericalToCartesianRadians(hangle, vangle, part.position);
		

		radius = couterRadius - cinnerRadius;
		radius = generator.getFloat(radius) + cinnerRadius;

		part.position.scale(radius);

	}

	@Override
	public void update(float life) {
		cinnerRadius = innerRadius.getValueAt(life);
		couterRadius = outerRadius.getValueAt(life);

	}
	
    @Override
	public void recompile() {
		
		if(generator == null)
			generator = new RandomGenerator();

//		if(math==null)
//			math = new ParticleFastMath();

		
		innerRadius.compileArray();


		outerRadius.compileArray();
	}

	@Override
	public void setResolution(int resolution) {
		innerRadius.setResolution(resolution);
		outerRadius.setResolution(resolution);
		

	}

	
	public ValueListf getInnerRadius() {
		return innerRadius;
	}

	public ValueListf getOuterRadius() {
		return outerRadius;
	}

	public Object getStandaloneCopy() {
		SphereGenerator copy = new SphereGenerator();
		copy.innerRadius = innerRadius.getStandaloneCopy();
		copy.outerRadius = outerRadius.getStandaloneCopy();
		
		return copy;
	}

	public void setInnerRadius(ValueListf innerRadius) {
		this.innerRadius = innerRadius;
	}

	public void setOuterRadius(ValueListf outerRadius) {
		this.outerRadius = outerRadius;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	

}
