package org.softmed.jops.space;

import org.softmed.jops.Particle;
import org.softmed.jops.cloner.StandaloneCloner;


public class PointGenerator extends GeneratorSpace implements StandaloneCloner {

	@Override
	public void generate(Particle part) {
		part.position.setZero();

	}

	@Override
	public void update(float life) {
		// TODO Auto-generated method stub

	}

	@Override
	public void recompile() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setResolution(int resolution) {
		// TODO Auto-generated method stub

	}

	public Object getStandaloneCopy() {
		PointGenerator copy = new PointGenerator();

		return copy;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
