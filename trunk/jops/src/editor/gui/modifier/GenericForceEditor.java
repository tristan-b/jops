package gui.modifier;

import gui.SimpleEditor;
import gui.value.ValueListGUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.softmed.jops.modifiers.GenericForce;


public class GenericForceEditor extends SimpleEditor implements ActionListener {
    
    private static final long serialVersionUID = 6364993256267116277L;
    
    private static final int BUTTON_HEIGHT = 20;

	Dimension dimension = new Dimension(120, BUTTON_HEIGHT);

	GenericForce force;

	
	JButton direction = new JButton("Direction");

	JButton strength = new JButton("Strength");

	JButton massImportance = new JButton("Mass Relevance");

	
	private ValueListGUI vgui;

	public GenericForceEditor() {
		setDimension(240, 40);
		setDimension(direction, dimension.width, dimension.height);
		setDimension(strength, dimension.width, dimension.height);
		setDimension(massImportance, dimension.width, dimension.height);


		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel der = new JPanel();
		der.setLayout(new BoxLayout(der, BoxLayout.X_AXIS));
		der.add(direction);
		der.add(strength);
		der.add(Box.createHorizontalGlue());
		add(der);


		JPanel der3 = new JPanel();
		der3.setLayout(new BoxLayout(der3, BoxLayout.X_AXIS));
		der3.add(massImportance);
		der3.add(Box.createHorizontalGlue());
		add(der3);

		
		setActionListener(this);
	}
	
    @Override
	public void setObject(Object obj) {
		force =    (GenericForce) obj;
	}

	public void setActionListener(ActionListener listener) {
		direction.addActionListener(listener);
		strength.addActionListener(listener);
		massImportance.addActionListener(listener);
	}

	public void removeActionListener(ActionListener listener) {
		direction.removeActionListener(listener);
		strength.removeActionListener(listener);
		massImportance.removeActionListener(listener);

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == direction) {
			destination.removeAll();
			vgui = new ValueListGUI("Direction");
			vgui.setValueList(null,force.getDirection(), 10f, -20f, 20f, 1f, 1f,
					0f, 90f, 180f);
			destination.add(vgui);
		} else if (source == strength) {
			destination.removeAll();
			vgui = new ValueListGUI("Strength");
			vgui.setValueList(null,force.getStrength(), 10f, -20f, 20f, 1f, 1f,
					0f, 90f, 180f);
			destination.add(vgui);
		} else if (source == massImportance) {
			destination.removeAll();
			vgui = new ValueListGUI("Mass Importance");
			vgui.setValueList(null,force.getMassImportance(), 10f, -20f, 20f, 1f, 1f,
					0f, 90f, 180f);
			destination.add(vgui);
		} 
		refreshDestination();
	}

}
