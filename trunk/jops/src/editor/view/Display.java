package view;
/*
 * Display.java
 *
 * Created on 23 de Fevereiro de 2006, 17:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author eu
 */
public class Display {
    
    private int x;
    private int y;
    float ratio;
    
    /** Creates a new instance of Display */
    public Display() {
    }
    
    public void setup(int x, int y)
    {
        this.x = x;
        this.y = y;
        ratio = ((float)x)/((float)y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
    public float getImageRatio()
    {
    	return ratio;
    }
}
