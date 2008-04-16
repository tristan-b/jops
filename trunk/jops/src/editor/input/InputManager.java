package input;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import renderer.RendererStatus;
/*
 * InputManager.java
 *
 * Created on 23 de Fevereiro de 2006, 18:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author eu
 */
public class InputManager {

    List<InputHandler> handlers = new ArrayList<InputHandler>();
    RendererStatus status;
    
    /** Creates a new instance of InputManager */
    public InputManager(RendererStatus status) {
        this.status = status;        
    }
    
        
     public void addInputHandler(InputHandler in)
    {
        in.setup(status);
        handlers.add(in);
    }
    
    protected void processInputHandlers()
    {
        for (Iterator it = handlers.iterator(); it.hasNext();) {
            InputHandler elem = (InputHandler) it.next();
            if(elem.isCapture())
            elem.process();
        }
    }

   public void process() {
       processInputHandlers();
    }
    
}
