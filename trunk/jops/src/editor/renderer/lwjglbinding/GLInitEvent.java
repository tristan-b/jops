package renderer.lwjglbinding;
/*
 * GLInitEvent.java
 *
 * Created on 22 de Fevereiro de 2006, 23:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import events.Event;

import org.lwjgl.opengl.GL11;
/**
 *
 * @author eu
 */
public class GLInitEvent implements Event {
    
    public void execute() {

        /* Culling. */
    GL11.glCullFace(GL11.GL_BACK);
    GL11.glFrontFace(GL11.GL_CCW);
    GL11.glDisable(GL11.GL_CULL_FACE);

    /* Setup our viewport. */
   // gl.glViewport(0, 0, width, height);
        
    GL11.glShadeModel(GL11.GL_SMOOTH);
    GL11.glClearColor(0.0f,0.0f,0.0f,0.0f);         // black background
    GL11.glClearDepth(1.0f);                        // depth of 0 to 1
    GL11.glEnable(GL11.GL_DEPTH_TEST);              // enable depth testing       
    GL11.glDepthFunc(GL11.GL_LEQUAL);       
    GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT,GL11.GL_NICEST);
        
    
    
    }
    
    
    
}
