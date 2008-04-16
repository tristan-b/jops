package org.softmed.jops.space.discreet;

import org.softmed.jops.Particle;
import org.softmed.jops.space.simple.PlaneGenerator;


public class DPlaneGenerator extends PlaneGenerator {

	int lines =3;
	int columns = 3;
	float dline ;
	float dcol ;

	int l;
	int c;
	
	int countL;
	int countC;
	

	
	public DPlaneGenerator()
	{
		update(0.0f);
		setColumns(columns);
		setLines(lines);
	}
	
	@Override
	public void reset() {
		countL = 0;
		countC = 0;
	}
    
    @Override
	public Object getStandaloneCopy() {
		DPlaneGenerator copy = new DPlaneGenerator();
		copy.width = width.getStandaloneCopy();
		copy.height = height.getStandaloneCopy();
		
		copy.setLines(lines);
		copy.setColumns(columns);
		
		return copy;
	}
    
    @Override
    public void generate(Particle part) {

    	c = countC;
		l = countL;
		

    	part.position.setX( c*dcol-cwidth*0.5f );
    	part.position.setZ( l*dline-chight*0.5f );
    	part.position.setY( 0f );
    	
    	countC++;
    	if(countC >= columns)
    	{
    		countL++;
    		countC=0;
    		countL%=lines;
    	}
    	
    }

	public int getColumns() {
		return columns;
	}

	public int getLines() {
		return lines;
	}

	public void setColumns(int columns) {
		this.columns = columns;
		if(columns > 1)
			dcol = cwidth / (columns-1); 
		else
			dcol = 0;
	}

	public void setLines(int lines) {
		this.lines = lines;
		if(lines > 1)
			dline = chight / (lines-1); 
		else
			dline = 0;
	}
    
    @Override
	public void recompile()
	{
		super.recompile();
		update(0.0f);
		setColumns(columns);
		setLines(lines);
	}
}
