package gui;

import input.ButtonListener;
import input.MouseMoveListener;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.softmed.jops.ParticleManager;
import org.softmed.jops.ParticleSystem;

import renderer.StandardGLRenderer;
import renderer.TriangleParticleRenderer;

public class PSDetail extends JPanel {

	private static final long serialVersionUID = -3086040543169716830L;

	ParticleSystem particleSystem;
	ParticleManager manager;

	PSTree tree = new PSTree();

	DevToolBar bar;

	DetailViewer dviewer = new DetailViewer();

	public PSDetail(JToolBar jbar, StandardGLRenderer renderer,
			TriangleParticleRenderer renderClient, MouseMoveListener mouse, ButtonListener key) {
		tree.setChoiceListener(dviewer);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(tree.getPane());
		add(dviewer);

		bar = new DevToolBar(jbar, tree, this, renderer, renderClient,mouse,key);
		dviewer.setBar(bar);
		Editor.EDITOR.setJMenuBar(bar);
	}

	public ParticleSystem getParticleSystem() {
		return particleSystem;
	}

	public void setParticleSystem(ParticleSystem particleSystem) {
		this.particleSystem = particleSystem;
		tree.setPs(particleSystem);
		dviewer.setParticleSystem(particleSystem);
	}

	public void setDetailDestination(JComponent c) {
		dviewer.setDestination(c);
	}

	public ParticleManager getManager() {
		return manager;
	}

	public void setManager(ParticleManager manager) {
		this.manager = manager;
	}

	public DetailViewer getDviewer() {
		return dviewer;
	}

}
