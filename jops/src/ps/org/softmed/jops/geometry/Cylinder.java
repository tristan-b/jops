/*
 * Cylinder.java
 *
 * Created on 27 de Fevereiro de 2006, 19:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.geometry;

/**
 *
 * @author eu
 */
public class Cylinder {
    
	float radius = 0.2f;
	float height = 1.0f;
	
    /** Creates a new instance of Cylinder */
    public Cylinder() {
    }

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
    
}
