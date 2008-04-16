package general;

import org.softmed.jops.ParticleManager;

import gui.DevToolBar;
import input.InputManager;
import renderer.ParticleRendererClient;
import renderer.Renderer;

/**
 * 
 * @author eu
 */
public class Engine {
	Renderer renderer;

	protected InputManager inputManager;

	protected ParticleManager particleManager = new ParticleManager();

	boolean setup = false;

	double dt ;

	private int counter;

	private long start;

	private long finish;

	private long diff;

	private int oldcounter;

	private long second;

	private long startTotalTime;
	
	/** Creates a new instance of Engine */
	public Engine(Renderer rend) {
		particleManager.setGameMode(false);
		DevToolBar.PARTICLE_MANAGER = particleManager;
		renderer = rend;
		inputManager = new InputManager(rend.getStatus());
		//particleManager.setup(rend.getStatus());
		renderer.addClient(new ParticleRendererClient(particleManager));
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public void runFrame() {
		
		
		if (!setup) {
			setup = true;
			return;
		}

		
		renderer.render();
		getInputManager().process();
		start = System.nanoTime();
		getParticleManager().process(renderer.getFrameRater().getTimeLapse());
		finish = System.nanoTime();
		diff = finish - start;
		second += System.nanoTime()-startTotalTime;
		startTotalTime = System.nanoTime();
		oldcounter = counter;
		counter++;
		dt = dt*(double)((double)oldcounter/(double)counter)+diff*(double)(1d/(double)counter);
		if(second>1000000000)
		{
		//	System.out.println("particle system performance " + dt / 1000000000.0 + " s / "
		//			+ dt/1000000 + " ms / " + dt + " nanos");
			second = 0;
			dt= 0;
			counter = 0;
		}

	}

	public InputManager getInputManager() {
		return inputManager;
	}

	public ParticleManager getParticleManager() {
		return particleManager;
	}

	public void setParticleManager(ParticleManager particleManager) {
		this.particleManager = particleManager;
	}

}
