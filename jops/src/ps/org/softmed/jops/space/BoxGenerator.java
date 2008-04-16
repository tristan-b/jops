/*
 * BoxGenerator.java
 *
 * Created on 26 de Fevereiro de 2006, 0:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.space;

import org.softmed.jops.Particle;
import org.softmed.jops.cloner.StandaloneCloner;
import org.softmed.jops.random.RandomGenerator;
import org.softmed.jops.valuelists.ValueListf;


/**
 * 
 * @author eu
 */
public class BoxGenerator extends GeneratorSpace implements StandaloneCloner{

	ValueListf depth = new ValueListf();

	ValueListf width = new ValueListf();

	ValueListf height = new ValueListf();

	float pdepth;

	float pwidth;

	float pheight;

	private float cdepth;

	private float cwidth;

	private float chight;
	
	/** Creates a new instance of BoxGenerator */
	public BoxGenerator() {
		depth.addValue(1.0f, 0.0f);
		width.addValue(1.0f, 0.0f);
		height.addValue(1.0f, 0.0f);
	}
    
    @Override
	public void generate(Particle part) {
		pdepth = cdepth - generator.nextFloat() * cdepth;
		pwidth = cwidth - generator.nextFloat() * cwidth;
		pheight = chight - generator.nextFloat() * chight;

		part.position.setX( pwidth );
		part.position.setY( pheight );
		part.position.setZ( pdepth );

	}
	
	public Object getStandaloneCopy() {
		BoxGenerator copy = new BoxGenerator();
		copy.depth = depth.getStandaloneCopy();
		copy.height = height.getStandaloneCopy();
		copy.width = width.getStandaloneCopy();
		
		return copy;
	}


	@Override
	public void update(float life) {
		cdepth = depth.getValueAt(life);
		cwidth = width.getValueAt(life);
		chight = height.getValueAt(life);

	}

	public ValueListf getDepth() {
		return depth;
	}

	public ValueListf getHeight() {
		return height;
	}

	public ValueListf getWidth() {
		return width;
	}
    
    @Override
	public void recompile() {
		if(generator == null)
		generator = new RandomGenerator();
		
		depth.compileArray();
		height.compileArray();
		width.compileArray();
	}

	@Override
	public void setResolution(int resolution) {
		depth.setResolution(resolution);
		width.setResolution(resolution);
		height.setResolution(resolution);

	}

	public void setDepth(ValueListf depth) {
		this.depth = depth;
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

