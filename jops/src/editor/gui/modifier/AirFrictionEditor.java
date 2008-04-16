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

import org.softmed.jops.modifiers.AirFriction;


public class AirFrictionEditor extends SimpleEditor implements ActionListener {
    
    private static final long serialVersionUID = 6270080975271642887L;
    
    private static final int BUTTON_HEIGHT = 20;

	Dimension dimension = new Dimension(120, BUTTON_HEIGHT);

	AirFriction force;

	JButton strength = new JButton("Strength");

	JButton sizeImportance = new JButton("Size Relevance");

	
	private ValueListGUI vgui;

	public AirFrictionEditor() {
		setDimension(240, 20);
		setDimension(strength, dimension.width, dimension.height);
		setDimension(sizeImportance, dimension.width, dimension.height);


		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel der = new JPanel();
		der.setLayout(new BoxLayout(der, BoxLayout.X_AXIS));
		der.add(strength);
		der.add(sizeImportance);
		der.add(Box.createHorizontalGlue());
		add(der);
	
		setActionListener(this);
	}
	
    @Override
	public void setObject(Object obj) {
		force =    (AirFriction) obj;
	}

	public void setActionListener(ActionListener listener) {
		strength.addActionListener(listener);
		sizeImportance.addActionListener(listener);
	}

	public void removeActionListener(ActionListener listener) {
		strength.removeActionListener(listener);
		sizeImportance.removeActionListener(listener);

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == strength) {
			destination.removeAll();
			vgui = new ValueListGUI("Strength");
			vgui.setValueList(null,force.getStrength(), 10f, -20f, 20f, 1f, 1f,
					0f, 90f, 180f);
			destination.add(vgui);
		} else if (source == sizeImportance) {
			destination.removeAll();
			vgui = new ValueListGUI("Size Importance");
			vgui.setValueList(null,force.getSizeImportance(), 10f, -20f, 20f, 1f, 1f,
					0f, 90f, 180f);
			destination.add(vgui);
		} 
		refreshDestination();
	}

}
