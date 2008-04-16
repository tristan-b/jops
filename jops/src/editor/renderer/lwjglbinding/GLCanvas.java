package renderer.lwjglbinding;

import general.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;

/*
 * GLCanvas.java
 *
 * Created on 17 de Fevereiro de 2006, 23:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 * 
 * @author eu
 */
public class GLCanvas extends AWTGLCanvas implements Runnable {
    
    private static final long serialVersionUID = -5919075037839576244L;
    
    boolean alive = true;

	Engine engine;

	/** Creates a new instance of GLCanvas */
	public GLCanvas(Engine eng) throws LWJGLException {
		engine = eng;

		// GL11.glMatrixMode(GL11.GL_PROJECTION);
	}
	
    @Override
	protected synchronized void paintGL() // Overrides �paintGL()
	{

		try {
			//makeCurrent();
			engine.runFrame();
			swapBuffers(); // show what you were drawing
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

	}

	public void run() {
	
		while (alive) {

			synchronized (this) {
				repaint();
				try {
					this.wait(1);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
					break;
				}
			}

		}
	}

}
