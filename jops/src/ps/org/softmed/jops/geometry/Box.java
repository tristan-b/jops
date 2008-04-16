/*
 * Box.java
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
public class Box {
    
    public float height=0.1f;
    public float width=0.1f;
    public float depth=0.1f;
    
    /** Creates a new instance of Box */
    public Box() {
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }
    
}
