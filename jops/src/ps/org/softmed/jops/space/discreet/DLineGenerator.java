package org.softmed.jops.space.discreet;

import org.softmed.jops.Particle;
import org.softmed.jops.space.simple.LineGenerator;


public class DLineGenerator extends LineGenerator{

	
	int points = 5;
	float dist = 1.0f/points;
	int choice ;
	int count = 0;
    
    @Override
	public Object getStandaloneCopy() {
		DLineGenerator copy = new DLineGenerator();
		copy.a = a.getStandaloneCopy();
		copy.b = b.getStandaloneCopy();
		copy.setPoints(points);

		return copy;
	}
    
    @Override
	public void reset() {
		count = 0;
		
	}
    
    @Override
	public void generate(Particle part) {
		
    	temp.set(reset);
    	choice = count;

    	temp.scale(dist*choice);
    	temp.add(ta);
    	//part.position.add(temp);
    	//part.position.add(ta,temp,part.position);
    	part.position.set(temp);
    	count++;
    	count %= points;
    	
    }

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
		if(points > 1)
			dist = 1.0f/(points-1);
		else
			dist = 0;
	}
    
    @Override
	public void recompile()
	{
		super.recompile();
		setPoints(points);
	}
	
}
