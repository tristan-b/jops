package org.softmed.jops.space.discreet;

import org.openmali.FastMath;
import org.softmed.jops.Particle;
import org.softmed.jops.space.simple.CircleGenerator;


public class DCircleGenerator extends CircleGenerator {

	int points = 5;

	float dangle = 2*FastMath.PI / 5;

	int choice;

	int count = 0;
    
    @Override
	public Object getStandaloneCopy() {
		DCircleGenerator copy = new DCircleGenerator();
		copy.setRadius(getRadius().getStandaloneCopy());
		copy.setPoints(points);

		return copy;
	}
    
    @Override
	public void reset() {
		count = 0;
		
	}
    
    @Override
	public void generate(Particle part) {

		choice = count;
		// choice = generator.getInt(points);
		angle = dangle * choice;

		sin = FastMath.sin(angle);
		cos = FastMath.cos(angle);

		part.position.setY( 0f );
		part.position.setX( sin * cinnerRadius );
		part.position.setZ( cos * cinnerRadius );

		count++;
		count %= points;

	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
		if (points > 1)
			dangle = 2*FastMath.PI / points;
	}
    
    @Override
	public void recompile() {
		super.recompile();
		dangle = 2*FastMath.PI / points;
	}

}
