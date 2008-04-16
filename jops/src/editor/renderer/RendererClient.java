/*
 * RendererClient.java
 *
 * Created on 28 de Fevereiro de 2006, 1:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package renderer;

/**
 * 
 * @author eu
 */
public abstract class RendererClient {

	protected RendererStatus status;

	public abstract void render();

	public RendererStatus getStatus() {
		return status;
	}

	public void setStatus(RendererStatus status) {
		this.status = status;
	}

}
