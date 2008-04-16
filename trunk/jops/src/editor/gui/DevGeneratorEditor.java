package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.modifier.GeneratorModifierEditor;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.softmed.jops.Generator;
import org.softmed.jops.GeneratorBehaviour;
import org.softmed.jops.ParticleBehaviour;
import org.softmed.jops.ParticleRender;
import org.softmed.jops.ParticleSystem;
import org.softmed.jops.PositionAnimator;
import org.softmed.jops.SpaceAnimator;
import org.softmed.jops.space.GeneratorSpace;


public class DevGeneratorEditor extends SimpleEditor implements ActionListener {
    
    private static final long serialVersionUID = 8531210098830509235L;
    
    ParticleSystem ps;

	Generator generator;

	// RenderGUI textureGUI = new RenderGUI();

	JButton modifiers = new JButton("Modifiers");

	JCheckBox killParticles = new JCheckBox("Kill Particles");

	JCheckBox alive = new JCheckBox("Alive");

	JCheckBox regenerate = new JCheckBox("Regenerate");

	JCheckBox faceCamera = new JCheckBox("Particles Face Camera");

	// JCheckBox repeat = new JCheckBox("Repeat");

	JComboBox render = new JComboBox();

	JComboBox generatorBehaviour = new JComboBox();

	JComboBox particleBehaviour = new JComboBox();

	JComboBox generatorSpace = new JComboBox();

	JComboBox spaceAnimator = new JComboBox();
	
	JComboBox positionAnimator = new JComboBox();

	boolean dontSet = false;

	InfoObjectEditor infoEditor = new InfoObjectEditor();

	JButton editGenB = new JButton("Edit");

	JButton editParB = new JButton("Edit");

	JButton editSpace = new JButton("Edit");

	JButton editAnimator = new JButton("Edit");
	
	JButton editPAnimator = new JButton("Edit");

	JButton editRender = new JButton("Edit");

	private DetailViewer viewer;

	public DevGeneratorEditor() {

		// setLayout(new GridLayout(12,1));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		setDimension(modifiers, 90, 20);
		setDimension(alive, 60, 20);
		setDimension(killParticles, 100, 20);

		setDimension(regenerate, 100, 20);
		setDimension(faceCamera, 155, 20);
		// setDimension(repeat, 100, 20);

		setDimension(render, 180, 20);
		setDimension(generatorBehaviour, 180, 20);
		setDimension(particleBehaviour, 180, 20);
		setDimension(generatorSpace, 180, 20);
		setDimension(spaceAnimator, 180, 20);
		setDimension(positionAnimator, 180, 20);
		

		setDimension(editGenB, 60, 20);
		setDimension(editParB, 60, 20);
		setDimension(editSpace, 60, 20);
		setDimension(editAnimator, 60, 20);
		setDimension(editPAnimator, 60, 20);
		setDimension(editRender, 60, 20);

		modifiers.addActionListener(this);
		alive.addActionListener(this);
		killParticles.addActionListener(this);
		regenerate.addActionListener(this);
		faceCamera.addActionListener(this);

		render.addActionListener(this);
		generatorBehaviour.addActionListener(this);
		particleBehaviour.addActionListener(this);
		generatorSpace.addActionListener(this);
		spaceAnimator.addActionListener(this);
		positionAnimator.addActionListener(this);

		render.setRenderer(new CustomListCellRenderer());
		generatorBehaviour.setRenderer(new CustomListCellRenderer());
		particleBehaviour.setRenderer(new CustomListCellRenderer());
		generatorSpace.setRenderer(new CustomListCellRenderer());
		spaceAnimator.setRenderer(new CustomListCellRenderer());
		positionAnimator.setRenderer(new CustomListCellRenderer());

		editRender.addActionListener(this);
		editGenB.addActionListener(this);
		editParB.addActionListener(this);
		editSpace.addActionListener(this);
		editAnimator.addActionListener(this);
		editPAnimator.addActionListener(this);

		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		header.add(alive);
		header.add(regenerate);
		header.add(modifiers);
		header.add(Box.createHorizontalGlue());
		add(header);

		// add(alive);
		// add(regenerate);
		JPanel header2 = new JPanel();
		header2.setLayout(new BoxLayout(header2, BoxLayout.X_AXIS));
		header2.add(faceCamera);
		header2.add(killParticles);
		header2.add(Box.createHorizontalGlue());
		add(header2);

		// add(faceCamera);

		add(setToLeft(new JLabel("Particle Render")));
		add(getPanel(render, editRender));
		add(setToLeft(new JLabel("Generator Behaviour")));
		add(getPanel(generatorBehaviour, editGenB));
		add(setToLeft(new JLabel("Particle Behaviour")));
		add(getPanel(particleBehaviour, editParB));
		add(setToLeft(new JLabel("Space")));
		add(getPanel(generatorSpace, editSpace));
		add(setToLeft(new JLabel("Space Animator")));
		add(getPanel(spaceAnimator, editAnimator));
		add(setToLeft(new JLabel("Position Animator")));
		add(getPanel(positionAnimator, editPAnimator));

		// add(textureGUI);

		add(infoEditor);

		setDimension(250, 600);

	}

	protected JPanel setToLeft(JComponent c) {
		JPanel header2 = new JPanel();
		header2.setLayout(new BoxLayout(header2, BoxLayout.X_AXIS));
		header2.add(c);
		header2.add(Box.createHorizontalGlue());
		return header2;
	}

	public Generator getGenerator() {
		return generator;
	}

	public void setGenerator(Generator generator) {

		this.generator = generator;

		if (generator == null)
			return;

		dontSet = true;

		render.setSelectedItem(generator.getRender());
		generatorBehaviour.setSelectedItem(generator.getGb());
		particleBehaviour.setSelectedItem(generator.getPb());
		generatorSpace.setSelectedItem(generator.getSpace());
		spaceAnimator.setSelectedItem(generator.getAnimator());
		positionAnimator.setSelectedItem(generator.getPositionAnimator());

		killParticles.setSelected(generator.isKillParticles());
		alive.setSelected(generator.isAlive());
		regenerate.setSelected(generator.isRegenerateParticles());
		faceCamera.setSelected(!generator.isAbsoluteParticleAngle());

		infoEditor.setObject(generator);

		dontSet = false;
	}

	public void refresh() {

	}

	public ParticleSystem getPs() {
		return ps;
	}

	public void setPs(ParticleSystem ps) {
		this.ps = ps;

		// render.remo
		render.removeAllItems();
		generatorBehaviour.removeAllItems();
		particleBehaviour.removeAllItems();
		generatorSpace.removeAllItems();
		spaceAnimator.removeAllItems();
		positionAnimator.removeAllItems();

		render.addItem("N/A");
		for (ParticleRender gb : ps.getRenders()) {
			render.addItem(gb);
		}

		generatorBehaviour.addItem("N/A");
		for (GeneratorBehaviour gb : ps.getGenBehaviours()) {
			generatorBehaviour.addItem(gb);
		}

		particleBehaviour.addItem("N/A");
		for (ParticleBehaviour pb : ps.getBehaviours()) {
			particleBehaviour.addItem(pb);
		}

		generatorSpace.addItem("N/A");
		for (GeneratorSpace space : ps.getSpaces()) {
			generatorSpace.addItem(space);
		}

		spaceAnimator.addItem("N/A");
		for (SpaceAnimator animator : ps.getAnimators()) {
			spaceAnimator.addItem(animator);
		}
		
		positionAnimator.addItem("N/A");
		for (PositionAnimator animator : ps.getPanimators()) {
			positionAnimator.addItem(animator);
		}

		// setGenerator(generator);
	}

	@Override
	public void setObject(Object obj) {
		setGenerator((Generator) obj);

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (generator == null || dontSet)
			return;

		if (source == killParticles) {
			Editor.setDirty(true);
			generator.setKillParticles(killParticles.isSelected());
		} else if (source == modifiers) {
			/*GeneratorModifierEditor chooser = */new GeneratorModifierEditor(ps,
					generator);
		} else if (source == faceCamera) {
			Editor.setDirty(true);
			generator.setAbsoluteParticleAngle(!faceCamera.isSelected());
		} else if (source == alive) {
			Editor.setDirty(true);
			generator.setAlive(alive.isSelected());
		} else if (source == regenerate) {
			Editor.setDirty(true);
			generator.setRegenerateParticles(regenerate.isSelected());
		} else if (source == generatorBehaviour) {
			Editor.setDirty(true);
			if (generatorBehaviour.getSelectedItem() instanceof String)
				generator.setGb(null);
			else
				generator.setGb((GeneratorBehaviour) generatorBehaviour
						.getSelectedItem());
		} else if (source == particleBehaviour) {
			Editor.setDirty(true);
			if (particleBehaviour.getSelectedItem() instanceof String)
				generator.setPb(null);
			else
				generator.setPb((ParticleBehaviour) particleBehaviour
						.getSelectedItem());
		} else if (source == generatorSpace) {
			Editor.setDirty(true);
			if (generatorSpace.getSelectedItem() instanceof String)
				generator.setSpace(null);
			else
				generator.setSpace((GeneratorSpace) generatorSpace
						.getSelectedItem());
		} else if (source == spaceAnimator) {
			Editor.setDirty(true);
			if (spaceAnimator.getSelectedItem() instanceof String)
				generator.setAnimator(null);
			else
				generator.setAnimator((SpaceAnimator) spaceAnimator
						.getSelectedItem());
		} else if (source == positionAnimator) {
			Editor.setDirty(true);
			if (positionAnimator.getSelectedItem() instanceof String)
				generator.setPositionAnimator(null);
			else
				generator.setPositionAnimator((PositionAnimator) positionAnimator
						.getSelectedItem());
		}else if (source == render) {
			Editor.setDirty(true);
			if (render.getSelectedItem() instanceof String)
				generator.setRender(null);
			else
				generator.setRender((ParticleRender) render.getSelectedItem());
		} else if (source == editGenB) {
			// if (generatorBehaviour.getSelectedItem() != null)
			// viewer.setObject(generatorBehaviour.getSelectedItem());
			viewer.getBar().getTree().setSelectedPath(
					generatorBehaviour.getSelectedItem());
		} else if (source == editParB) {
			// if (particleBehaviour.getSelectedItem() != null)
			// viewer.setObject(particleBehaviour.getSelectedItem());
			viewer.getBar().getTree().setSelectedPath(
					particleBehaviour.getSelectedItem());
		} else if (source == editSpace) {
			// if (generatorSpace.getSelectedItem() != null)
			// viewer.setObject(generatorSpace.getSelectedItem());
			viewer.getBar().getTree().setSelectedPath(
					generatorSpace.getSelectedItem());
		} else if (source == editAnimator) {
			// if (spaceAnimator.getSelectedItem() != null)
			// viewer.setObject(spaceAnimator.getSelectedItem());
			viewer.getBar().getTree().setSelectedPath(
					spaceAnimator.getSelectedItem());
		} else if (source == editPAnimator) {
			// if (spaceAnimator.getSelectedItem() != null)
			// viewer.setObject(spaceAnimator.getSelectedItem());
			viewer.getBar().getTree().setSelectedPath(
					positionAnimator.getSelectedItem());
		}else if (source == editRender) {
			// if (render.getSelectedItem() != null)
			// viewer.setObject(render.getSelectedItem());
			viewer.getBar().getTree().setSelectedPath(render.getSelectedItem());

		}

	}

	protected JPanel getPanel(JComponent a, JComponent b) {
		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		header.add(a);
		header.add(b);
		header.add(Box.createHorizontalGlue());
		return header;
	}

	public void setDetailViewer(DetailViewer viewer) {
		this.viewer = viewer;

	}

}
