/*
 * ParticleManager.java
 *
 * Created on 27 de Fevereiro de 2006, 23:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.softmed.jops.fileloading.DataFormatException;

/**
 * 
 * @author eu
 */
public class ParticleManager {

	protected ParticleLibrary particleLibrary = new ParticleLibrary();

	protected boolean alive = true;

	protected List<ParticleSystem> systems = new ArrayList<ParticleSystem>();

	protected List<ParticleSystem> toRemove = new ArrayList<ParticleSystem>();

	ParticleSystem ps;

	protected boolean gameMode = true;

	protected boolean resetDeadParticleSystemsInEditMode = true;

	private float dt;

	protected int calculationsPerSecond = 50;

	float tt = 1.0f / calculationsPerSecond;

	float ft;

	float ddt = 0f;

	/** Creates a new instance of ParticleManager */
	public ParticleManager() {
	}

	public String load(String filepath) throws IOException, DataFormatException {
		return particleLibrary.setOriginalFromFile(filepath);
	}

	public String load(File file) throws IOException, DataFormatException {
		return particleLibrary.setOriginalFromFile(file);
	}

	public String load(InputStream inputStream) throws IOException,
			DataFormatException {
		return particleLibrary.setOriginalFromStream(inputStream);
	}

	public String load(URL url) throws IOException, DataFormatException {
		return particleLibrary.setOriginalFromURL(url);
	}

	public ParticleSystem getCopyDetached(String filename) {
		return particleLibrary.getStandaloneCopy(filename);
	}

	public ParticleSystem getCopyAttached(String filename) {
		ParticleSystem tps = particleLibrary.getStandaloneCopy(filename);
		if (tps != null)
			systems.add(tps);

		return tps;
	}

	public void process(float timelapse) {
		if (!alive)
			return;

		toRemove.clear();

		dt = timelapse;
		ddt += dt;

		if (ddt <= tt)
			return;

		while (true) {
			ddt -= tt;
			// ft = ddt;

			dt = tt;
			// ddt = 0;
			for (int j = 0; j < systems.size(); j++) {
				ps = systems.get(j);
				ps.processFrame(dt);

				if (ps.isRemove()) {
					if (gameMode)
						toRemove.add(ps);
					else if (resetDeadParticleSystemsInEditMode) {
						ps.setRemove(false);
						ps.reset();
						ps.setAlive(true);

					}
				}

			}

			systems.removeAll(toRemove);
			for (ParticleSystem ps : toRemove) {
				ps.signalRemoved();
			}

			if (ddt < tt) {
				return;
			}

		}
	}

	public List<ParticleSystem> getSystems() {
		return systems;
	}

	public boolean isGameMode() {
		return gameMode;
	}

	public void setGameMode(boolean editorMode) {
		this.gameMode = editorMode;
	}

	public boolean isResetDeadParticleSystemsInEditMode() {
		return resetDeadParticleSystemsInEditMode;
	}

	public void setResetDeadParticleSystemsInEditMode(
			boolean resetDeadParticleSystems) {
		this.resetDeadParticleSystemsInEditMode = resetDeadParticleSystems;
	}

	public void purge() {
		for (ParticleSystem system : systems) {
			system.setRenderable(false);
			system.setAlive(false);
			system.setRemove(true);
		}

		systems.clear();
	}

	public void reset() {
		for (ParticleSystem system : systems) {
			system.reset();
		}

		ddt = 0f;
		dt = 0f;
		ft = 0f;
	}

	public int getCalculationsPerSecond() {
		return calculationsPerSecond;
	}

	public void setCalculationsPerSecond(int calculationsPerSecond) {
		this.calculationsPerSecond = calculationsPerSecond;
		if (calculationsPerSecond == 0)
			calculationsPerSecond = 45;
		tt = 1f / calculationsPerSecond;

	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public ParticleLibrary getParticleLibrary() {
		return particleLibrary;
	}

	public void setParticleLibrary(ParticleLibrary particleLibrary) {
		this.particleLibrary = particleLibrary;
	}

}
