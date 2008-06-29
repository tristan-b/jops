package gui;

import fileloading.FileLoader;
import gui.modifier.ModifierCreatedListener;
import gui.modifier.ModifierCreator;
import gui.space.SpaceCreator;
import gui.space.SpaceSelectionListener;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import org.softmed.jops.Generator;
import org.softmed.jops.ParticleManager;
import org.softmed.jops.ParticleSystem;
import org.softmed.jops.modifiers.Modifier;
import org.softmed.jops.space.GeneratorSpace;

import renderer.ParticleRendererClient;
import renderer.StandardGLRenderer;
import renderer.TriangleParticleRenderer;

public class DevToolBar extends JMenuBar implements ActionListener,
		SpaceSelectionListener, ModifierCreatedListener {

	private static final long serialVersionUID = -9049562565758490119L;

	private final int ITEM_PLAIN = 0; // Item types

	private final int ITEM_CHECK = 1;

	private final int ITEM_RADIO = 2;

	public static ParticleManager PARTICLE_MANAGER;

	// file control
	public static FileLoader fileLoader = new FileLoader();

	// particle system control
	JMenuItem emptyScene;

	JMenuItem createDefaultGen;

	JMenuItem createEmptyGen;

	JMenuItem createGenBehaviour;

	JMenuItem createParBehaviour;

	JMenuItem createSpace;

	JMenuItem createAnimator;

	JMenuItem createPositionAnimator;

	JMenuItem createRender;

	JMenuItem createModifier;

	JMenuItem about;

	JButton save;

	JButton saveAs;

	JButton load;

	JButton convertDirectory;

	JButton remove;

	// particleManager control
	JButton shutAllMinusGensThisOne;

	JButton startAllGens;

	JButton pauseAll;

	JButton resetAll;

	JButton create10SharedClones;

	JButton create10Clones;

	JButton killClones;

	JToolBar bar;

	JCheckBoxMenuItem showGrid;

	JCheckBoxMenuItem showAxis;

	JCheckBoxMenuItem showGenerators;

	JCheckBoxMenuItem showPointMasses;

	JCheckBoxMenuItem showPointMassesTreshold;

	private PSTree tree;

	private PSDetail detail;

	StandardGLRenderer renderer;
	TriangleParticleRenderer renderClient;

	public DevToolBar(JToolBar bar, PSTree tree, PSDetail detail,
			StandardGLRenderer renderer, TriangleParticleRenderer renderClient) {
		this.bar = bar;
		this.tree = tree;
		this.detail = detail;

		this.renderer = renderer;
		this.renderClient = renderClient;

		fileLoader.setDetail(detail);

		JMenu file = new JMenu("File");

		fileLoader.save = CreateMenuItem("Save");
		fileLoader.saveAs = CreateMenuItem("Save As");
		fileLoader.load = CreateMenuItem("Load");
		fileLoader.convertDirectory = CreateMenuItem("Convert Directory");

		file.add(fileLoader.save);
		file.add(fileLoader.saveAs);
		file.add(fileLoader.load);
		file.add(fileLoader.convertDirectory);

		add(file);

		JMenu create = new JMenu("Create");

		// particle system control
		emptyScene = CreateMenuItem("Empty");
		createDefaultGen = CreateMenuItem("Default Generator");
		createEmptyGen = CreateMenuItem("Empty Generator");
		createGenBehaviour = CreateMenuItem("Generator Behaviour");
		createParBehaviour = CreateMenuItem("Particle Behaviour");
		createSpace = CreateMenuItem("Space");
		createAnimator = CreateMenuItem("Space Animator");
		createPositionAnimator = CreateMenuItem("Position Animator");
		createRender = CreateMenuItem("Render");
		createModifier = CreateMenuItem("Modifier");

		create.add(createDefaultGen);
		create.add(createEmptyGen);
		create.add(createGenBehaviour);
		create.add(createParBehaviour);
		create.add(createSpace);
		create.add(createAnimator);
		create.add(createPositionAnimator);

		create.add(createRender);
		create.add(createModifier);
		create.add(new JSeparator());
		create.add(emptyScene);

		add(create);

		JMenu preferences = new JMenu("View Preferences");

		showGrid = (JCheckBoxMenuItem) CreateMenuCheckItem("3D Grid");
		showAxis = (JCheckBoxMenuItem) CreateMenuCheckItem("3D Axis");
		showGenerators = (JCheckBoxMenuItem) CreateMenuCheckItem("Generators");
		showPointMasses = (JCheckBoxMenuItem) CreateMenuCheckItem("Point Masses");
		showPointMassesTreshold = (JCheckBoxMenuItem) CreateMenuCheckItem("Point Masses Treshold Radius");

		showGrid.setSelected(renderer.showGrid);
		showAxis.setSelected(renderer.showAxis);
		showGenerators.setSelected(renderClient.showGenerators);
		showPointMasses.setSelected(renderClient.showPointMasses);
		showPointMassesTreshold
				.setSelected(renderClient.showPointMasseTreshold);

		preferences.add(showGrid);
		preferences.add(showAxis);
		preferences.add(showGenerators);
		preferences.add(showPointMasses);
		preferences.add(showPointMassesTreshold);

		add(preferences);

		// TODO
		JMenu help = new JMenu("Help");
		about = CreateMenuItem("About");
		help.add(about);
		add(help);

		save = createButton("Save");
		saveAs = createButton("Save As");
		load = createButton("Load");

		remove = createButton("Remove");

		shutAllMinusGensThisOne = createButton("Stop Other Gens");
		startAllGens = createButton("Start Gens");
		pauseAll = createButton("Pause");
		resetAll = createButton("Reset");

		create10SharedClones = createButton("10 Shared Clones");

		create10Clones = createButton("10 clones");
		killClones = createButton("Kill clones");

		// particleManager control
		/*
		 * shutAllMinusGensThisOne = createButton("Shut Other Gens");
		 * startAllGens = createButton("Start All Gens"); pauseAll =
		 * createButton("Pause"); resetAll = createButton("Reset"); /*
		 * bar.add(emptyScene); bar.add(createDefaultGen);
		 * bar.add(createEmptyGen);
		 * 
		 * bar.add(createGenBehaviour); bar.add(createParBehaviour);
		 * bar.add(createSpace);
		 * 
		 * bar.add(createAnimator); bar.add(createRender); //
		 */
		bar.add(save);
		bar.add(saveAs);
		bar.add(load);
		bar.addSeparator();
		bar.add(remove);
		bar.addSeparator();
		bar.add(shutAllMinusGensThisOne);
		bar.add(startAllGens);
		bar.add(pauseAll);
		bar.add(resetAll);
		bar.addSeparator();

		// bar.add(create10SharedClones);
		bar.add(create10Clones);
		bar.add(killClones);
		// */
	}

	/*
	 * public void setObject(Object obj) { } //
	 */
	protected JButton createButton(String name) {
		JButton c1 = new JButton();
		c1.setText(name);
		c1.setMargin(new Insets(0, 0, 0, 0));
		c1.addActionListener(this);
		return c1;
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == fileLoader.save || source == fileLoader.saveAs
				|| source == fileLoader.load
				|| source == fileLoader.convertDirectory || source == save
				|| source == saveAs || source == load) {
			if (source == save)
				e.setSource(fileLoader.save);
			else if (source == saveAs)
				e.setSource(fileLoader.saveAs);
			else if (source == load)
				e.setSource(fileLoader.load);

			fileLoader.actionPerformed(e);
			return;
		} else if (source == createDefaultGen) {
			// detail.getDviewer().setObject(tree.addNewDefaultGenerator());
			tree.addNewDefaultGenerator();
		} else if (source == createEmptyGen) {
			tree.addNewEmpyGenerator();
		} else if (source == createSpace) {
			new SpaceCreator(this);
		} else if (source == createGenBehaviour) {
			tree.addGenBehaviour();
		} else if (source == createParBehaviour) {
			tree.addParBehaviour();
		} else if (source == createAnimator) {
			tree.addAnimator();
		} else if (source == createPositionAnimator) {
			tree.addPositionAnimator();
		} else if (source == createRender) {
			tree.addRender();
		} else if (source == createModifier) {
			new ModifierCreator(this);
		} else if (source == remove) {
			tree.removeNode(tree.getSelectedObject());
		} else if (source == shutAllMinusGensThisOne) {

			tree.getPs().stopAllGens();
			if (tree.getSelectedObject() instanceof Generator) {
				Generator gen = (Generator) tree.getSelectedObject();
				gen.setAlive(true);
			}
		} else if (source == startAllGens) {
			tree.getPs().startAllGens();
			tree.getPs().reset();
		} else if (source == pauseAll) {

			if (PARTICLE_MANAGER == null)
				return;

			if (PARTICLE_MANAGER.isAlive()) {
				PARTICLE_MANAGER.setAlive(false);
				pauseAll.setText("PLAY");
			} else {
				PARTICLE_MANAGER.setAlive(true);
				pauseAll.setText("PAUSE");
			}

		} else if (source == resetAll) {
			// tree.getPs().reset();
			if (PARTICLE_MANAGER != null)
				PARTICLE_MANAGER.reset();
		} else if (source == emptyScene) {
			if (PARTICLE_MANAGER == null)
				return;

			if (Editor.DIRTY) {
				int duh = JOptionPane
						.showConfirmDialog(
								Editor.EDITOR,
								"You have unsaved changes on this scene.\n"
										+ "Would you like to cancel loading so that you can save this scene ???\n"
										+ "If you click yes, you will cancel loading a file, and you will have\n the option of saving this scene."
										+ "\nIf you click no you will lose all changes applied to this scene.",
								"test2", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (duh == JOptionPane.YES_OPTION)
					return;

			}

			// detail.setParticleSystem(null);
			PARTICLE_MANAGER.purge();
			tree.cleanAll();

			ParticleSystem ps = new ParticleSystem();
			PARTICLE_MANAGER.getSystems().add(ps);
			detail.setParticleSystem(ps);

			fileLoader.getFileStatus().reset();

			Editor.EDITOR.setTitle("untitled");
			Editor.DIRTY = true;
		} else if (source == showGrid) {
			renderer.showGrid = showGrid.isSelected();
		} else if (source == showAxis) {
			renderer.showAxis = showAxis.isSelected();
		} else if (source == showGenerators) {
			renderClient.showGenerators = showGenerators.isSelected();
		} else if (source == showPointMasses) {
			renderClient.showPointMasses = showPointMasses.isSelected();
		} else if (source == showPointMassesTreshold) {
			renderClient.showPointMasseTreshold = showPointMassesTreshold
					.isSelected();
		} else if (source == about) {
			JOptionPane.showMessageDialog(Editor.EDITOR,
					"Open Particle Editor V0.5 \n"
							+ "Created by Guilherme Gomes \n"
							+ "guilhermegrg@gmail.com\n" + "LGPL License");
		} else if (source == create10Clones) {
			// PARTICLE_MANAGER
			if (PARTICLE_MANAGER.getSystems().size() == 0)
				return;

			for (int i = 0; i < 10; i++) {

				ParticleSystem original = PARTICLE_MANAGER.getSystems().get(0);

				ParticleSystem copy = PARTICLE_MANAGER.getParticleLibrary()
						.getStandaloneCopy(original);
				copy.getPosition().set((float) (5f - Math.random() * 10f), 0f,
						(float) (5f - Math.random() * 10f));
				PARTICLE_MANAGER.getSystems().add(copy);

			}

		} else if (source == killClones) {
			int size = PARTICLE_MANAGER.getSystems().size();
			if (size <= 1)
				return;
			for (int i = 1; i < size; i++) {
				PARTICLE_MANAGER.getSystems().remove(1);
			}

			System.gc();

		} else if (source == create10SharedClones) {
			// PARTICLE_MANAGER
			if (PARTICLE_MANAGER.getSystems().size() == 0)
				return;

			for (int i = 0; i < 10; i++) {

				ParticleSystem original = PARTICLE_MANAGER.getSystems().get(0);

				ParticleSystem copy = PARTICLE_MANAGER.getParticleLibrary()
						.getStandaloneCopy(original);
				copy.getPosition().set((float) (5f - Math.random() * 10f), 0f,
						(float) (5f - Math.random() * 10f));
				PARTICLE_MANAGER.getSystems().add(copy);

			}

		}

	}

	public void selected(GeneratorSpace space) {
		tree.addSpace(space);
		detail.getDviewer().setObject(space);

	}

	public void modifierCreated(Modifier modifier) {
		// TODO make changes to these 2 classes...
		tree.addModifier(modifier);
		detail.getDviewer().setObject(modifier);

	}

	public PSTree getTree() {
		return tree;
	}

	public JMenuItem CreateMenuItem(String sText) {
		return CreateMenuItem(null, ITEM_PLAIN, sText, null, 0, null);
	}

	public JMenuItem CreateMenuCheckItem(String sText) {
		return CreateMenuItem(null, ITEM_CHECK, sText, null, 0, null);
	}

	public JMenuItem CreateMenuItem(JMenu menu, int iType, String sText,
			ImageIcon image, int acceleratorKey, String sToolTip) {
		// Create the item
		JMenuItem menuItem;

		switch (iType) {
		case ITEM_RADIO:
			menuItem = new JRadioButtonMenuItem();
			break;

		case ITEM_CHECK:
			menuItem = new JCheckBoxMenuItem();
			break;

		default:
			menuItem = new JMenuItem();
			break;
		}

		// Add the item test
		menuItem.setText(sText);

		// Add the optional icon
		if (image != null)
			menuItem.setIcon(image);

		// Add the accelerator key
		if (acceleratorKey > 0)
			menuItem.setMnemonic(acceleratorKey);

		// Add the optional tool tip text
		if (sToolTip != null)
			menuItem.setToolTipText(sToolTip);

		// Add an action handler to this menu item
		menuItem.addActionListener(this);

		if (menu != null)
			menu.add(menuItem);

		return menuItem;
	}

}
