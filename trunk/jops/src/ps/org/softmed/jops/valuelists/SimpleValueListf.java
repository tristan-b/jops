/*
 * ValueListf.java
 *
 * Created on 12 de Julho de 2006, 18:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.valuelists;


/**
 * 
 * @author gui
 */
public class SimpleValueListf extends SimpleGenericValueList<Float> {

//	private int trand;

//	private float tvalue;

	
	
	public SimpleValueListf() {
		// nullValue = new GenericValue(0.0f,0.0f);
		nullValue = 0.0f;
		setValue(nullValue, 0.0f);
	}

	/*
	 * @Override public Float calculateResult(Float previous, Float next, float
	 * dt) { return previous + (next-previous)*dt; } //
	 */
    @Override
	public Float calculateResult(Float previous, Float next, float dt, float tt) {
		return previous + ((next - previous) / tt) * dt;
	}
/*
	@Override
	public Float getRandomValue(Float before) {
		trand = generator.getInt(randomness);
		tvalue = (trand * 0.01f);
		if (trand < middleValue) {
			return before - before * tvalue;
		} else {
			return before - before * tvalue;
		}

	}
//*/
}
