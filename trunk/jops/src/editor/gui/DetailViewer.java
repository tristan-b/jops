package gui;

import gui.generator.GeneratorBehaviourEditor;
import gui.generator.ParticleBehaviourEditor;
import gui.generator.PositionAnimatorEditor;
import gui.generator.RenderGUI;
import gui.generator.SpaceAnimatorEditor;
import gui.modifier.ModifierEditor;
import gui.space.Manager;

import java.awt.Container;

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
import org.softmed.jops.modifiers.Modifier;
import org.softmed.jops.space.GeneratorSpace;


public class DetailViewer extends JPanel implements ChoiceChanged {
    
    private static final long serialVersionUID = 7445744960411896046L;
    
    JComponent destination;

	Generator generator;

	Object object;

	SimpleEditor editor;

	DevToolBar bar;

	private ParticleSystem particleSystem;

	public DetailViewer() {
	}

	public void setObject(Object object) {
		this.object = object;

		//bar.setObject(object);

		removeAll();
		editor = null;

		if (object instanceof Generator) {
			editor = new DevGeneratorEditor();
			((DevGeneratorEditor)editor).setPs(particleSystem);
			((DevGeneratorEditor)editor).setDetailViewer(this);
			editor.setObject(object);		
		} else if (object instanceof GeneratorSpace) {
			editor = Manager.getEditor((GeneratorSpace) object,destination);
		}
		else if(object instanceof GeneratorBehaviour){
			GeneratorBehaviourEditor pdetail = new GeneratorBehaviourEditor();
			pdetail.setDestination(destination);
			pdetail.setObject(object);
			editor = pdetail;
		}
		else if(object instanceof ParticleBehaviour){
			ParticleBehaviourEditor pdetail = new ParticleBehaviourEditor();
			pdetail.setDestination(destination);
			pdetail.setObject(object);
			editor = pdetail;
		}else if(object instanceof ParticleRender){
			RenderGUI pdetail = new RenderGUI();
			pdetail.setDestination(destination);
			pdetail.setObject(object);
			editor = pdetail;
		}else if(object instanceof SpaceAnimator){
			SpaceAnimatorEditor pdetail = new SpaceAnimatorEditor();
			pdetail.setDestination(destination);
			pdetail.setObject(object);
			editor = pdetail;
		}else if(object instanceof PositionAnimator){
			PositionAnimatorEditor pdetail = new PositionAnimatorEditor();
			pdetail.setDestination(destination);
			pdetail.setObject(object);
			editor = pdetail;
		}else if(object instanceof ParticleSystem){
			ParticleSystemEditor pdetail = new ParticleSystemEditor();
			pdetail.setDestination(destination);
			pdetail.setObject(object);
			editor = pdetail;
		}else if(object instanceof Modifier){
			ModifierEditor pdetail = new ModifierEditor(particleSystem,this);
			//((DevGeneratorEditor)editor).setPs();
			pdetail.setDestination(destination);
			pdetail.setObject(object);
			editor = pdetail;
		}
		
		
		
		if (editor == null) {
			add(new JLabel("Not Implemented!!!"));
		} else {
			editor.setDestination(destination);
			editor.setObject(object);
			add(editor);
		}

		invalidate();
		revalidate();
		repaint();

		// this is not working
		destination.removeAll();
		destination.invalidate();
		destination.revalidate();
		destination.repaint();

		// destination.
		// destination.add(new JLabel("Not Implemented!!!"));

	}

	public void choosed(Object obj) {
		setObject(obj);
	}

	public Object getObject() {
		return object;
	}

	public Container getDestination() {
		return destination;
	}

	public void setDestination(JComponent destination) {
		this.destination = destination;
	}

	public DevToolBar getBar() {
		return bar;
	}

	public void setBar(DevToolBar bar) {
		this.bar = bar;
	}

	public SimpleEditor getEditor() {
		return editor;
	}

	public void setParticleSystem(ParticleSystem particleSystem) {
		this.particleSystem = particleSystem;
		
	}

}
