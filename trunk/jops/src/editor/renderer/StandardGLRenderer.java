package renderer;
/*
 * StandardGLRender.java
 *
 * Created on 22 de Fevereiro de 2006, 23:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.glu.Disk;
import org.lwjgl.opengl.glu.Quadric;
import org.lwjgl.opengl.glu.Sphere;
import org.lwjgl.opengl.glu.Util;

import renderer.lwjglbinding.GLCamera;
import renderer.lwjglbinding.GLCanvas;
import renderer.lwjglbinding.GLInitEvent;
/**
 *
 * @author eu
 */
public class StandardGLRenderer extends  Renderer {
	
	public static boolean showGrid  = true;
	public static boolean showAxis  = true;

	
    GLCanvas canvas;
    GLCamera glcamera = new GLCamera();
    boolean red=false;
    Sphere sphere = new Sphere();
//    Disk q = new Disk();

    Color background = new Color(0,0,0,255);
    Color gridColor = new Color(100,100,100,255);
    float[] back = new float[4];
    float[] grid = new float[4];
    
    /** Creates a new instance of StandardGLRender */
    public StandardGLRenderer() {
        //add a GLINitEvent
         glcamera.setCamera(getStatus().getCamera());
         getEvents().addEvent(new GLInitEvent());
         buildColors();
         
         /*
     	try {
			DisplayMode modes[] = Display.getAvailableDisplayModes();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
         //*/
    }

    public void buildColors()
    {
    	background.getComponents(back);
    	gridColor.getComponents(grid);
    	
    }
    
    @Override
    protected void setupScene() {
	//	if(canvas == null)
	//		return;

    	GL11.glClearColor(back[0],back[1],back[2],back[3]);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearDepth(1.0);
        glcamera.placeCam();
    }
    
    @Override
    protected void cleanScene() {
    	
    }
    
    @Override
    protected void draw() {
       
    	GL11.glDepthMask(true);
    	
        if(showGrid){
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK,GL11.GL_LINE);
        GL11.glColor3f(grid[0],grid[1],grid[2]);
        sphere.draw(5.0f,15,15);
//        q.draw(0f, 5f, 15, 15);

        }
        //*/
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK,GL11.GL_FILL);
        
      //  GL11.glColor4f(grid[0],grid[1],grid[2],grid[3]);
       /* GL11.glColor3f(grid[0],grid[1],grid[2]);
        sphere.draw(0.2f,15,15);
      //*/  
    	
        if(showAxis){
        GL11.glColor3f(1f,0f,0f);
        GL11.glBegin(GL11.GL_LINES);	
        GL11.glVertex3f(0.0f, 0.0f,0.0f);
        GL11.glVertex3f(1.0f, 0.0f,0.0f);
        GL11.glEnd();
               
        GL11.glColor3f(0f,1f,0f);
        GL11.glBegin(GL11.GL_LINES);	
        GL11.glVertex3f(0.0f, 0.0f,0.0f);
        GL11.glVertex3f(0.0f, 1.0f,0.0f);
        GL11.glEnd();
        
        GL11.glColor3f(0f,0f,1f);
        GL11.glBegin(GL11.GL_LINES);	
        GL11.glVertex3f(0.0f, 0.0f,0.0f);
        GL11.glVertex3f(0.0f, 0.0f,-1.0f);
        GL11.glEnd();
        }
        
        GL11.glColor3f(1f,1f,1f);
        
        //RendererClient elem = null;
        for (int i = 0; i < clients.size(); i++) {
           clients.get(i).render();
        }
    
    }

	public Color getBackground() {
		return background;
	}

	public Color getGridColor() {
		return gridColor;
	}

	public GLCanvas getCanvas() {
		return canvas;
	}

	public void setCanvas(GLCanvas canvas) {
		this.canvas = canvas;
	}

    

    
    
    
}
