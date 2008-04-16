package input;

import renderer.RendererStatus;
import timer.FrameRater;
import view.Camera;
import view.Display;
/*
 * InputHandler.java
 *
 * Created on 23 de Fevereiro de 2006, 0:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author eu
 */
public abstract class InputHandler {
    
    protected boolean capture = true;
    protected FrameRater frameRater;
    protected Camera camera;
    protected Display display;
            
  public  InputHandler ()
    {
     
    }
    
  
  public void process()
  {
      if(capture  && frameRater !=null & camera != null)
         processInput(); 
  }
  
    protected abstract void processInput();
    
    public abstract void destroy();

    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

   public void setup(RendererStatus status) {
       this.display = status.getDisplay();
       this.camera = status.getCamera();
       this.frameRater = status.getFrameRater();
    }
    
}
