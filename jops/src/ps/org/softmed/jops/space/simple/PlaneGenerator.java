/*
 * PlaneGenerator.java
 *
 * Created on 27 de Fevereiro de 2006, 19:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.space.simple;


import org.softmed.jops.Particle;
import org.softmed.jops.cloner.StandaloneCloner;
import org.softmed.jops.random.RandomGenerator;
import org.softmed.jops.valuelists.ValueListf;


/**
 * 
 * @author eu
 */
public class PlaneGenerator extends SimpleGenerator implements StandaloneCloner{

	protected ValueListf width = new ValueListf();

	protected ValueListf height = new ValueListf();

	protected float cwidth;

	protected  float chight;


	
	/** Creates a new instance of PlaneGenerator */
	public PlaneGenerator() {
		width.addValue(1.0f,0.0f);
	    height.addValue(1.0f,0.0f);
	}
    
    @Override
	public void generate(Particle part) {
		part.position.setX( generator.getFloat(cwidth)-cwidth*0.5f );
		part.position.setZ( generator.getFloat(chight)-chight*0.5f );
		part.position.setY( 0f );

	}

	

	@Override
	public void update(float life) {
		cwidth = width.getValueAt(life);
		chight = height.getValueAt(life);

	}
    
    @Override
	public void recompile() {
		if(generator == null)
			generator = new RandomGenerator();

		
		width.compileArray();
		height.compileArray();
	}

	@Override
	public void setResolution(int resolution) {
		width.setResolution(resolution);
		height.setResolution(resolution);


	}

	
	public ValueListf getHeight() {
		return height;
	}

	public ValueListf getWidth() {
		return width;
	}

	public Object getStandaloneCopy() {
		PlaneGenerator copy = new PlaneGenerator();
		copy.width = width.getStandaloneCopy();
		copy.height = height.getStandaloneCopy();
		
		return copy;
	}

	public void setHeight(ValueListf height) {
		this.height = height;
	}

	public void setWidth(ValueListf width) {
		this.width = width;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
