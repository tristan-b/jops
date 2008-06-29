package renderer;

import events.EventList;
import java.util.ArrayList;
import java.util.List;

import timer.FrameRater;
import view.Camera;
import view.Display;

/*
 * GLRenderer.java
 *
 * Created on 22 de Fevereiro de 2006, 23:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 * 
 * @author eu
 */
public abstract class Renderer {

	// protected List<RendererClient> clients = new ArrayList<RendererClient>();
	RendererClient client;
	protected RendererStatus status = new RendererStatus();
	protected EventList events = new EventList();

	public void render() {
		// first process pending GLevents - init, etc
		events.processEvents();

		cleanScene();
		setupScene();
		draw();

		getStatus().getFrameRater().calculateFrameRate();
	}

	protected abstract void cleanScene();

	protected abstract void draw();

	protected abstract void setupScene();

	public Camera getCamera() {
		return getStatus().getCamera();
	}

	public FrameRater getFrameRater() {
		return getStatus().getFrameRater();
	}

	public EventList getEvents() {
		return events;
	}

	public Display getDisplay() {
		return getStatus().getDisplay();
	}

	public RendererStatus getStatus() {
		return status;
	}

	public RendererClient getClient() {
		return client;
	}

	public void setClient(RendererClient client) {
		this.client = client;
		client.setStatus(status);
	}

	// public void addClient(RendererClient client)
	// {
	// clients.add(client);
	// client.setStatus(status);
	// }

}