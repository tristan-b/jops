package org.softmed.jops.valuelists;

import java.util.List;

import org.softmed.jops.values.GenericValue;


public class ValueListf extends GeneralRandomValueList<Float>{

	public ValueListf()
	{
		mainValueList = new SimpleValueListf();
		randomValueList = new SimpleValueListf();
	}
	
	@Override
	protected Float calculateRandomValue(Float original, Float random) {
		return original + generator.getFloat(random)-random*0.5f;
	}

	
	public ValueListf getStandaloneCopy() {
		ValueListf copy = new ValueListf();

		copy.bias = bias;
		copy.active = active;
		copy.random = random;
		
		List<GenericValue> values = mainValueList.getValues();
		for (GenericValue value : values) {
			copy.mainValueList.addValue(new Float((Float) value.getValue()),value.getTime());
			copy.mainValueList.repeat = mainValueList.repeat;
		}
		
		values = randomValueList.getValues();
		for (GenericValue value : values) {
			copy.randomValueList.addValue(new Float((Float) value.getValue()),value.getTime());
			copy.randomValueList.repeat = randomValueList.repeat;
		}
		
		return copy;
	}


}
