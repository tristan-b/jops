/*
 * ValueListc.java
 *
 * Created on 12 de Julho de 2006, 18:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.valuelists;

import org.openmali.vecmath2.Colorf;



/**
 * 
 * @author gui
 */
public class SimpleValueListc extends SimpleGenericValueList<Colorf>{

	
	int r,g,b;
	//private int trand;
	//private float tvalue;
	

	public SimpleValueListc() {
		nullValue = new Colorf(1f, 1f, 1f);
		setValue(nullValue,0.0f);
	}

	

	@Override
	public Colorf calculateResult(Colorf previous, Colorf next, float dt,float tt) {

		/*
		Color3b result = new Color3b();
		result.x = ((byte) (previous.x + ((next.x - previous
				.x) * dt/tt)));
		result
				.y=((byte) (previous.y + ((next.y - previous
						.y) * dt/tt)));
		result.z = ((byte) (previous.z + ((next.z - previous
				.z) * dt/tt)));
				//*/
		//System.out.println(result.toString());
		
		Colorf result = new Colorf();
		result.setRed( (previous.getRed() + ((next.getRed() - previous.getRed())
		                * dt/tt)));
		result.setGreen( (previous.getGreen() + ((next.getGreen() - previous.getGreen())
		                * dt/tt)));
		result.setBlue( (previous.getBlue() + ((next.getBlue() - previous.getBlue())
		                * dt/tt)));
		
		return result;
	}


/*
	@Override
	public Color getRandomValue(Color before) {
		trand = generator.getInt(randomness);
		tvalue = (trand*0.01f);
		if(trand< middleValue)
		{
		r = (int)( before.getRed()-before.getRed()*tvalue);
		g = (int)(before.getGreen()-before.getGreen()*tvalue);
		b = (int)(before.getBlue()-before.getBlue()*tvalue);
		}
		else
		{
		r = (int)( before.getRed()+before.getRed()*tvalue);
		g = (int)(before.getGreen()+before.getGreen()*tvalue);
		b = (int)(before.getBlue()+before.getBlue()*tvalue);	
		}
		
		return new Color(r,g,b);
	}
//*/
}
