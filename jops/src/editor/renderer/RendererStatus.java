package renderer;

import timer.FrameRater;
import view.Camera;
import view.Display;
/*
 * RendererStatus.java
 *
 * Created on 23 de Fevereiro de 2006, 18:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author eu
 */
public class RendererStatus {
    
    private Camera camera = new Camera();
    private FrameRater frameRater  = new FrameRater();
    private Display display = new Display();
    
    /** Creates a new instance of RendererStatus */
    public RendererStatus() {
    	camera.setDisplay(display);
    }

    public Camera getCamera() {
        return camera;
    }

    public FrameRater getFrameRater() {
        return frameRater;
    }

    public Display getDisplay() {
        return display;
    }
    
}
