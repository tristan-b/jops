/*
 * CircleGenerator.java
 *
 * Created on 27 de Fevereiro de 2006, 19:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.space.simple;

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
public class CircleGenerator extends SimpleGenerator implements StandaloneCloner{
//	protected ParticleFastMath math = new ParticleFastMath();
	protected float angle;
	protected float sin;
	protected float cos;
	 
	protected ValueListf radius = new ValueListf();
	protected float cinnerRadius;
	
    
    
	
    /** Creates a new instance of CircleGenerator */
    public CircleGenerator() {
    	radius.addValue(0.5f,0.0f);
//    	radius.addValue(1.0f,1.0f);
    	radius.setRepeat(true);
    }
    
    @Override
    public void generate(Particle part) {
    	angle = generator.getFloat(2 * FastMath.PI);
    	sin  = FastMath.sin(angle);
    	cos = FastMath.cos(angle);
    	
    	part.position.setY( 0f );
    	part.position.setX( sin * cinnerRadius );
    	part.position.setZ( cos * cinnerRadius );
    	
    }



	@Override
	public void update(float life) {
		cinnerRadius = radius.getValueAt(life);
		
	}

	public ValueListf getRadius() {
		return radius;
	}

	@Override
	public void recompile() {
		if(generator == null)
			generator = new RandomGenerator();

//		if(math==null)
//			math = new ParticleFastMath();
		
		radius.compileArray();
		
	}

	@Override
	public void setResolution(int resolution) {
		radius.setResolution(resolution);

	}

	public void setRadius(ValueListf radius) {
		this.radius = radius;
	}

	public Object getStandaloneCopy() {
		CircleGenerator copy = new CircleGenerator();
		copy.radius = radius.getStandaloneCopy();
		
		return copy;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
