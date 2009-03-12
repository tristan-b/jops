package gui;

import events.Event;
import fileloading.FileLoader;
import general.Engine;
import gui.util.QuickFileBrowser;
import input.ButtonListener;
import input.KeyCodes;
import input.MouseMoveListener;
import input.actions.CommandManager;

import javax.swing.JComponent;

import org.lwjgl.LWJGLException;
import org.softmed.jops.ParticleManager;
import org.softmed.jops.basic.BasicParticleSystem;

import renderer.StandardGLRenderer;
import renderer.TriangleParticleRenderer;
import renderer.lwjglbinding.GLCanvas;
import textures.TextureNotFoundException;
import textures.Textures;

public class ParticleEditor extends Editor {

	private static final long serialVersionUID = 4556524331007643575L;

	QuickFileBrowser fileBrowser = new QuickFileBrowser();;

	PSDetail detail;

	private BasicParticleSystem g;

	private GLCanvas canvas;

	// private Texture tex;

	private StandardGLRenderer renderer;

	private Engine engine;

	private ParticleManager manager;

	public static int image;

	public ParticleEditor() {
		setup();
	}

	public void setup() {

		fileBrowser.loadFilesInDirectory(FileLoader.fileStatus.getDirectory());
		center.add(fileBrowser);

		setupGL(topCenter);

		manager = engine.getParticleManager();

		right.add(fileBrowser);
		right.invalidate();
		right.revalidate();

		Textures.setup(canvas);

		detail = new PSDetail(toolbar, renderer,
				(TriangleParticleRenderer) renderer.getClient());
		left.add(detail);
		left.invalidate();
		left.revalidate();
		// topLeft.repaint();
		detail.setDetailDestination(bottomCenter);
		detail.setParticleSystem(g);
		detail.setManager(manager);
		// */
	}

	private void setupGL(JComponent comp) {

		try {

			try {
				Textures.setDefaultDirectory("./media/textures");
			} catch (Throwable t) {
				t.printStackTrace();
			}

			MouseMoveListener mouse = new MouseMoveListener();
			mouse.setCapture(false);

			ButtonListener key = new ButtonListener();
			key.setCapture(false);

			CommandManager cmanager = new CommandManager();
			mouse.setActionListener(key);

			renderer = new StandardGLRenderer();

			engine = new Engine(renderer);
			engine.getInputManager().addInputHandler(key);
			engine.getInputManager().addInputHandler(mouse);

			g = new BasicParticleSystem();
			engine.getParticleManager().getSystems().add(g);

			renderer.getEvents().addEvent(new Event() {

				// to load first texture...
				public void execute() {
					int id = 0;
					String name = "particle.png";
					try {
						id = Textures.requestTexture(name, false);
					} catch (TextureNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					g.getGenerators().get(0).getRender().setTextureId(id);
					g.getGenerators().get(0).getRender().setTextureName(name);

				}

			});

			SwingDealer t = new SwingDealer();
			t.setRstatus(engine.getRenderer().getStatus());
			t.setMouseListener(mouse);
			t.setButtonListener(key);

			key.addActuator(KeyCodes.MOUSE3, t);
			cmanager.setup(key);

			canvas = new GLCanvas(engine);
			canvas.addKeyListener(key);
			canvas.addMouseMotionListener(mouse);
			canvas.addMouseWheelListener(mouse);
			// for in-game presses
			canvas.addMouseListener(key);
			// to detect when i enter in-game mode
			canvas.addMouseListener(t);

			t.setCanvas(canvas);

			comp.addComponentListener(t);

			t.setupDimensions(comp);

			comp.add(canvas);

			// canvas.run();

			new Thread(canvas).start();

			// canvas.setVSyncEnabled(true);

		} catch (LWJGLException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * public void valueChanged(TreeSelectionEvent e) { // TODO Auto-generated
	 * method stub } //
	 */
}
