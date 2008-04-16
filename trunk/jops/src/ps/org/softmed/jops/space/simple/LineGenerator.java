/*
 * LineGenerator.java
 *
 * Created on 27 de Fevereiro de 2006, 19:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.space.simple;

import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Vector3f;
import org.softmed.jops.Particle;
import org.softmed.jops.cloner.StandaloneCloner;
import org.softmed.jops.random.RandomGenerator;
import org.softmed.jops.valuelists.ValueListp3f;


/**
 *
 * @author eu
 */
public class LineGenerator extends SimpleGenerator implements StandaloneCloner{
    
   protected ValueListp3f a = new ValueListp3f();
   protected ValueListp3f b = new ValueListp3f();
	
   protected Point3f ta = new Point3f(1.0f,0.0f,1.0f) ;
   protected Point3f tb = new Point3f(0.0f,1.0f,1.0f) ;
   protected Point3f temp = new Point3f() ;
   protected Vector3f reset = new Vector3f() ;
   protected float lenght;
   
   

    /** Creates a new instance of LineGenerator */
    public LineGenerator() {
    	a.addValue(new Point3f(1.0f,0.0f,0.0f),0.0f);
    	b.addValue(new Point3f(0.0f,0.0f,0.0f),0.0f);
    }
    
    @Override
    public void generate(Particle part) {
    	
    	
    	lenght = generator.getFloat(1.0f);
    	temp.set(reset);
    	temp.scale(lenght);
    	//part.position.add(ta,temp,part.position);
    	temp.add(ta);
    	part.position.set(temp);
    }

	@Override
	public void update(float life) {
		 ta = new Point3f(a.getValueAt(life));
		 tb = new Point3f(b.getValueAt(life));
		 //ta.sub(tb,ta,reset);
		 reset.sub(tb,ta);
	}
    
    @Override
	public void recompile() {
		System.out.println("recompiling Line generator space");
		
		if(generator == null)
			generator = new RandomGenerator();

		if(ta == null)
			ta = new Point3f();

		if(tb == null)
			tb = new Point3f();

		if(temp == null)
			temp = new Point3f();

		if(reset == null)
			reset = new Vector3f();
		
		
		
		a.compileArray();
		b.compileArray();
		
	}

	@Override
	public void setResolution(int resolution) {
		a.setResolution(resolution);
		b.setResolution(resolution);


	}

	
	public ValueListp3f getA() {
		return a;
	}

	public ValueListp3f getB() {
		return b;
	}

	public Object getStandaloneCopy() {
		LineGenerator copy = new LineGenerator();
		copy.a = a.getStandaloneCopy();
		copy.b = b.getStandaloneCopy();
		
		return copy;
	}

	public void setA(ValueListp3f a) {
		this.a = a;
	}

	public void setB(ValueListp3f b) {
		this.b = b;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
    
}
