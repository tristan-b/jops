/*
 * ValueListv3f.java
 *
 * Created on 12 de Julho de 2006, 18:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.valuelists;

import org.openmali.vecmath2.Point3f;

/**
 *
 * @author gui
 */
public class SimpleValueListp3f extends SimpleGenericValueList<Point3f>{
    
	int r,g,b;
//	private int trand;
//	private float tvalue;
	

    public SimpleValueListp3f() {
        nullValue = new Point3f();
        addValue(nullValue,0.0f);
    }
    
  
 /*   
    public void normalizeAll() {
        for (Iterator it = values.iterator(); it.hasNext();) {
            GenericValue elem = (GenericValue) it.next();
            ((Vector3f)elem.value).normalise((Vector3f) elem.value);
        }
    }
//*/
	@Override
	public Point3f calculateResult(Point3f previous, Point3f next, float dt, float tt) {
		Point3f result = new Point3f();
		result.setX( previous.getX() + (next.getX()-previous.getX())*dt/tt );
        result.setY( previous.getY() + (next.getY()-previous.getY())*dt/tt );
        result.setZ( previous.getZ() + (next.getZ()-previous.getZ())*dt/tt );
        //System.out.println(result.toString());
		return result;
	}


/*
	@Override
	public Vector3f getRandomValue(Vector3f before) {
		trand = generator.getInt(randomness);
		tvalue = (trand*0.01f);
		if(trand< middleValue)
		{
		r = (int)( before.getX()-before.getX()*tvalue);
		g = (int)(before.getY()-before.getY()*tvalue);
		b = (int)(before.getZ()-before.getZ()*tvalue);
		}
		else
		{
		r = (int)( before.getX()+before.getX()*tvalue);
		g = (int)(before.getY()+before.getY()*tvalue);
		b = (int)(before.getZ()+before.getZ()*tvalue);	
		}
		
		return new Vector3f(r,g,b);
		
	}
	
	//*/
}
