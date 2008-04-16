/*
 * CylinderGenerator.java
 *
 * Created on 27 de Fevereiro de 2006, 19:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.space;

import org.openmali.FastMath;
import org.softmed.jops.Particle;
import org.softmed.jops.cloner.StandaloneCloner;
import org.softmed.jops.random.RandomGenerator;
import org.softmed.jops.valuelists.ValueListf;

//import particle.math.ParticleFastMath;

/**
 * 
 * @author eu
 */
public class CylinderGenerator extends GeneratorSpace implements StandaloneCloner{
//	ParticleFastMath math = new ParticleFastMath();

	ValueListf height = new ValueListf();

	ValueListf innerRadius = new ValueListf();

	ValueListf outerRadius = new ValueListf();

	float pheight;

	float pangle;

	float pradius;

	private float cos;

	private float sin;

	private float cheight;

	private float cinnerRadius;

	private float couterRadius;

	
	/** Creates a new instance of CylinderGenerator */
	public CylinderGenerator() {
		height.addValue(1.0f,0.0f);
		innerRadius.addValue(0.3f,0.0f);
		outerRadius.addValue(0.5f,0.0f);
		
		
	}
	public Object getStandaloneCopy() {
		CylinderGenerator copy = new CylinderGenerator();
		copy.innerRadius = innerRadius.getStandaloneCopy();
		copy.height = height.getStandaloneCopy();
		copy.outerRadius = outerRadius.getStandaloneCopy();
		
		return copy;
	}
    
    @Override
	public void generate(Particle part) {
		pheight = generator.getFloat(cheight);
		pangle = generator.getFloat(2*FastMath.PI);
		pradius = generator.getFloat(1.0f);
		sin = FastMath.sin(pangle);
		cos = FastMath.cos(pangle);

		pradius = pradius * (couterRadius - cinnerRadius) + cinnerRadius;

		part.position.setY( pheight );
		part.position.setX( sin * pradius );
		part.position.setZ( cos * pradius );

	}

	@Override
	public void update(float life) {
		cheight = height.getValueAt(life);
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
		height.compileArray();
		outerRadius.compileArray();
	}
	

	@Override
	public void setResolution(int resolution) {
		innerRadius.setResolution(resolution);
		outerRadius.setResolution(resolution);
		height.setResolution(resolution);

	}
	
	public ValueListf getHeight() {
		return height;
	}

	public ValueListf getInnerRadius() {
		return innerRadius;
	}

	public ValueListf getOuterRadius() {
		return outerRadius;
	}
	public void setHeight(ValueListf height) {
		this.height = height;
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
