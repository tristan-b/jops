/*
 * ValueList.java
 *
 * Created on 25 de Fevereiro de 2006, 22:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.valuelists;

import java.util.ArrayList;
import java.util.List;

import org.softmed.jops.values.GenericValue;


class node extends GenericValue {
	node next;

	node previous;
}

/**
 * 
 * @author eu
 */
public abstract class SimpleGenericValueList<T>{

	//TO REMOVE
	//RandomGenerator generator = new RandomGenerator();
	
	int maxIndex;

	//TO REMOVE
	boolean random;
	
	//TO REMOVE
	int randomness;
	
//	TO REMOVE
	boolean active;

	int resolution = 30;

	boolean repeat;

	T nullValue;

	float lastTime = 0.0f;

	float dt;

	protected GenericValue previous = null;

	protected GenericValue next = null;

	protected List<GenericValue> values = new ArrayList<GenericValue>();

	T[] array;

	private int index;

	protected List<GenericValue> temp = new ArrayList<GenericValue>();

	protected List<GenericValue> ordered = new ArrayList<GenericValue>();

	protected List<T> elements = new ArrayList<T>();

	//TO REMOVE
	//protected double middleValue;

	public abstract T calculateResult(T previous, T next, float dt, float tt);

	//TO REMOVE
	//public abstract T getRandomValue(T before) ;
	// public abstract T calculateResult(T previous, T next, int dt);

	
	
	
	public T getValueAt(float time) {

		if (time > lastTime) {
			if (repeat) {
				time %= lastTime;
			} else {
				time = lastTime;
			}
		}
		index = (int) ((float) time * resolution);
		try {
			if (index >= array.length)
				index = array.length - 1;
			//if(random)
			//return getRandomValue(array[index]);	
			//else
			return array[index];
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
		// */
	}

	public void setValue(T firstValue, float t) {
		GenericValue temp;
		for (int i = 0; i < values.size(); i++) {
			temp = values.get(i);
			if (temp.time == t) {
				temp.value = firstValue;
				compileArray();
				return;
			}
		}

		GenericValue v = new GenericValue(firstValue, t);
		values.add(v);

		compileArray();

	}

	public void setValue(GenericValue v) {
		values.add(v);

		compileArray();

	}

	// */

	public void removeValue(float t) {
		GenericValue temp;
		for (int i = 0; i < values.size(); i++) {
			temp = values.get(i);
			if (temp.time == t) {
				values.remove(temp);
				break;
			}
		}

		compileArray();
	}

	public void compileArray() {
		orderList();
		values.clear();
		values.addAll(ordered);
		createArray();
		lastTime = values.get(values.size() - 1).time;
		maxIndex = (int) (lastTime * (float) resolution);
		@SuppressWarnings("unused")
		float gtemp = lastTime;
	}

	@SuppressWarnings("unchecked")
	private void createArray() {
		GenericValue current = null;
		GenericValue next = null;
		elements = new ArrayList<T>();
		for (int i = 0; i < ordered.size(); i++) {
			current = ordered.get(i);
			if (i + 1 < ordered.size()) {
				next = ordered.get(i + 1);
			} else
				next = null;

			createArrayElements(current, next);

		}

		array = (T[]) elements.toArray();

	}

	@SuppressWarnings("unchecked")
	private void createArrayElements(GenericValue current, GenericValue next) {
		if (next == null) {
			// return;
			elements.add((T) current.value);
			return;
		}

		int endTime = 0;
		int startTime = 0;
		float tt = 0;

		if (next != null) {
			tt = next.time - current.time;
			endTime = (int) ((tt) * resolution);
		}

		float time = 0.0f;
		for (int i = startTime; i < endTime; i++) {

			time = (((float) i) / resolution);
			elements.add(calculateResult((T) current.value, (T) next.value,
					time, tt));

		}

	}

	protected List<GenericValue> orderList() {
		if (temp != null)
			temp.clear();
		else
			temp = new ArrayList<GenericValue>();

		if (ordered != null)
			ordered.clear();
		else
			ordered = new ArrayList<GenericValue>();

		temp.addAll(values);

		GenericValue value = null;
		GenericValue min = null;
		//int index = -1;

		while (temp.size() > 0) {

			min = null;

			for (int i = 0; i < temp.size(); i++) {
				value = temp.get(i);
				if (min == null || value.time < min.time) {
					min = value;

				}
			}

			temp.remove(min);
			ordered.add(min);
		}

		return ordered;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public List<GenericValue> getValues() {
		return values;
	}
/*
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
//*/
	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	public T getNullValue() {
		return nullValue;
	}

	public void setValues(List<GenericValue> values) {
		this.values = values;
		values.remove(0);
		compileArray();
	}

	/*
	public boolean isRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}

	public int getRandomness() {
		return randomness;
	}

	public void setRandomness(int randomness) {
		this.randomness = randomness;
		middleValue = randomness*0.5;
	}
	//*/
}
