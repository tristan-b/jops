package org.softmed.jops.valuelists;

import java.util.List;

import org.openmali.vecmath2.Vector3f;
import org.softmed.jops.values.GenericValue;


public class ValueListv3f extends GeneralRandomValueList<Vector3f>{

	Vector3f temp; 
	
	public ValueListv3f()
	{
		mainValueList = new SimpleValueListv3f();
		randomValueList = new SimpleValueListv3f();
	}
	
	@Override
	protected Vector3f calculateRandomValue(Vector3f original, Vector3f random) {

		temp = new Vector3f();
		
		temp.setX( original.getX() + generator.getFloat(random.getX())-random.getX()*0.5f );
		temp.setY( original.getY() + generator.getFloat(random.getY())-random.getY()*0.5f );
		temp.setZ( original.getZ() + generator.getFloat(random.getZ())-random.getZ()*0.5f );
		
		return temp;
	}

	
	public ValueListv3f getStandaloneCopy() {
		ValueListv3f copy = new ValueListv3f();

		copy.bias = bias;
		copy.active = active;
		copy.random = random;
		
		List<GenericValue> values = mainValueList.getValues();
		for (GenericValue value : values) {
			copy.mainValueList.addValue(new Vector3f((Vector3f) value.getValue()),value.getTime());
			copy.mainValueList.repeat = mainValueList.repeat;
		}
		
		values = randomValueList.getValues();
		for (GenericValue value : values) {
			copy.randomValueList.addValue(new Vector3f((Vector3f) value.getValue()),value.getTime());
			copy.randomValueList.repeat = randomValueList.repeat;
		}
		
		return copy;
	}
	
	

}
