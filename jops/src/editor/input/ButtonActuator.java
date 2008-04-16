/*
 * ActionActuator.java
 *
 * Created on 13 de Julho de 2006, 12:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package input;

import timer.FrameRater;
import view.Camera;
import view.Display;

/**
 *
 * @author gui
 */
public  interface ButtonActuator {
    
       
    public void keyPressed(int key,FrameRater fr, Camera cr, Display d );
    public void keyReleased(int key,FrameRater fr, Camera cr, Display d );
    
}
