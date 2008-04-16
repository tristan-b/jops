/*
 * Sphere.java
 *
 * Created on 26 de Fevereiro de 2006, 0:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.geometry;

/**
 *
 * @author eu
 */
public class Sphere {
    
     public float innerRadius=0.0f;
     public float outerRadius=0.5f;
     
    // public float angle = 360f;

     
    /** Creates a new instance of Sphere */
    public Sphere() {
    }

	public float getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(float innerRadius) {
		this.innerRadius = innerRadius;
	}

	public float getOuterRadius() {
		return outerRadius;
	}

	public void setOuterRadius(float outerRadius) {
		this.outerRadius = outerRadius;
	}
    
    
  
}
