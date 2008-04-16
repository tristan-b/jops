package org.softmed.jops.valuelists;

import java.util.List;

import org.openmali.vecmath2.Colorf;
import org.softmed.jops.values.GenericValue;


public class ValueListc extends GeneralRandomValueList<Colorf>{

	private Colorf temp;
	
	public ValueListc()
	{
		mainValueList = new SimpleValueListc();
		randomValueList = new SimpleValueListc();
	}
	@Override
	protected Colorf calculateRandomValue(Colorf original, Colorf random) {
		temp = new Colorf();
		
	//	if(original == null || random == null || generator == null)
	//		System.out.println("I received RANDOM colors !!!!");
		
		temp.setRed( (original.getRed() + generator.getFloat(random.getRed())-random.getRed()*0.5f));
		temp.setGreen(  (original.getGreen() + generator.getFloat(random.getGreen())-random.getGreen()*0.5f));
		temp.setBlue(   (original.getBlue() + generator.getFloat(random.getBlue())-random.getBlue()*0.5f));
		
		return temp;
	}

	
	public ValueListc getStandaloneCopy() {
		ValueListc copy = new ValueListc();

		copy.bias = bias;
		copy.active = active;
		copy.random = random;

		List<GenericValue> values = mainValueList.getValues();
		for (GenericValue value : values) {
			copy.mainValueList.addValue(new Colorf((Colorf) value.getValue()),
					value.getTime());
			copy.mainValueList.repeat = mainValueList.repeat;
		}

		values = randomValueList.getValues();
		for (GenericValue value : values) {
			copy.randomValueList.addValue(new Colorf((Colorf) value.getValue()),
					value.getTime());
			copy.randomValueList.repeat = randomValueList.repeat;
		}

		return copy;
	}
	

}
