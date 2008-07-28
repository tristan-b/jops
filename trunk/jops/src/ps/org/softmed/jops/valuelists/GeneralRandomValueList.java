package org.softmed.jops.valuelists;

import java.util.List;

import org.softmed.jops.random.RandomGenerator;
import org.softmed.jops.values.GenericValue;


public abstract class GeneralRandomValueList<T> {

	RandomGenerator generator = new RandomGenerator();

	SimpleGenericValueList<T> mainValueList ;

	SimpleGenericValueList<T> randomValueList;

	float bias;

	boolean active;

	boolean random;

	private T value;

	private T rvalue;

	//private T finalValue;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public float getBias() {
		return bias;
	}

	public void setBias(float bias) {
		this.bias = bias;
	}

	public T getValueAt(float time) {
		if (random) {
			value = mainValueList.getValueAt(time);
			rvalue = randomValueList.getValueAt(time);
			return calculateRandomValue(value, rvalue);
		} else
			return mainValueList.getValueAt(time);

	}

	public void addValue(T value, float time) {
		mainValueList.setValue(value, time);
	}

	protected abstract T calculateRandomValue(T original, T random);

	public SimpleGenericValueList<T> getMainValueList() {
		return mainValueList;
	}

	public void setMainValueList(SimpleGenericValueList<T> mainValueList) {
		this.mainValueList = mainValueList;
	}

	public boolean isRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		
			if( generator == null)
			generator = new RandomGenerator();
							

		this.random = random;
	}

	public SimpleGenericValueList<T> getRandomValueList() {
		return randomValueList;
	}

	public void setRandomValueList(SimpleGenericValueList<T> randomValueList) {
		this.randomValueList = randomValueList;
	}

	public void setRepeat(boolean b) {
		this.mainValueList.setRepeat(b);
	}

	public void compileArray() {
		if(generator == null)
		generator = new RandomGenerator();
		this.mainValueList.compileArray();
		this.randomValueList.compileArray();
	}

	public void setResolution(int resolution) {
		this.mainValueList.setResolution(resolution);
		this.mainValueList.setResolution(resolution);
	}

	public List<GenericValue> getValues() {
		return this.mainValueList.getValues();
	}
	
}
