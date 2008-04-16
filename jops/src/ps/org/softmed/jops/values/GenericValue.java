/*
 * Value.java
 *
 * Created on 25 de Fevereiro de 2006, 22:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.values;

/**
 * USE GENERICS
 * @author eu
 */
public class GenericValue{
	
    public float time;
    public Object value;

    
    
    /** Creates a new instance of Value */
    public GenericValue() {
    }

        /** Creates a new instance of Value */
    public GenericValue(Object v, float t) {
        setValue(v);
        setTime(t);
        
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

     public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }


    
}
