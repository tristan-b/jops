package renderer.lwjglbinding;

/*
 * GLCamera.java
 *
 * Created on 22 de Fevereiro de 2006, 23:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.glu.Project;

import view.Camera;
import view.Display;

/**
 * 
 * @author eu
 */
public class GLCamera {

	private Camera camera;

	Display display;

	public void placeCam() {
		//testing this
		
		GL11.glViewport(0, 0, display.getX(), display.getY());
		GL11.glMatrixMode(GL11.GL_PROJECTION); // matriz de projec��o
		GL11.glLoadIdentity();
		Project.gluPerspective(camera.mFieldOfViewAngle, display
				.getImageRatio(),
				camera.mClosePlane, camera.mFarPlane);

		GL11.glMatrixMode(GL11.GL_MODELVIEW); // matriz de
												// modela��o/visualiza��o
		GL11.glLoadIdentity();
		
		Project.gluLookAt(camera.mPosition.getX(), camera.mPosition.getY(),
				-camera.mPosition.getZ(), // posi��o da camera
				camera.mPosition.getX() + camera.mLookAt.getX(), // vector
																	// alvo da
																	// camera
				camera.mPosition.getY() + camera.mLookAt.getY(),
				-(camera.mPosition.getZ() + camera.mLookAt.getZ()),
				camera.mUpVector.getX(), camera.mUpVector.getY(),
				-camera.mUpVector.getZ()); // vector cima da camera

		// */
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
		display = camera.getDisplay();
	}

}
