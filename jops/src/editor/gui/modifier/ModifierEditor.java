package gui.modifier;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.softmed.jops.ParticleSystem;
import org.softmed.jops.modifiers.AirFriction;
import org.softmed.jops.modifiers.GenericForce;
import org.softmed.jops.modifiers.Modifier;
import org.softmed.jops.modifiers.PointMass;

import gui.DetailViewer;
import gui.InfoObjectEditor;
import gui.SimpleEditor;

public class ModifierEditor extends SimpleEditor {
    
    private static final long serialVersionUID = -3636201663542974056L;
    
    Modifier modifier;

	SimpleEditor editor;

	JPanel proxy = new JPanel();

	InfoObjectEditor infoEditor = new InfoObjectEditor();

	private ParticleSystem ps;

	private DetailViewer viewer;

	public ModifierEditor(ParticleSystem particleSystem,DetailViewer viewer) {
		this.ps = particleSystem;
		this.viewer = viewer;
		
		//setDimension(250, 250);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(proxy);
		add(infoEditor);

	}

	@Override
	public void setObject(Object obj) {
		if (obj == null)
			return;

		modifier = (Modifier) obj;
		infoEditor.setObject(obj);
		if (modifier instanceof GenericForce)
			editor = new GenericForceEditor();
		else if (modifier instanceof AirFriction)
			editor = new AirFrictionEditor();
		else if (modifier instanceof PointMass)
			editor = new PointMassEditor(ps,viewer);
		
			
		proxy.removeAll();
		if (editor != null) {
			editor.setDestination(destination);
			editor.setObject(obj);
			proxy.add(editor);
		} else
			proxy.add(new JLabel("No Editor found for:\n" + obj.getClass()));

	}

}
