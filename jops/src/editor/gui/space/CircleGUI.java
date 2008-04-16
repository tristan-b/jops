package gui.space;

import gui.value.ValueListGUI;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;

import org.softmed.jops.space.simple.CircleGenerator;


public class CircleGUI extends BasicSpaceEditor {
    
    private static final long serialVersionUID = 6274521048408038829L;
    
    JButton radius = new JButton("Radius");

	private CircleGenerator box;

	private ValueListGUI vgui;

	public CircleGUI(JComponent destination) {
		super(destination);
		setEasyDimension(radius,widthB ,heightB );
		radius.addActionListener(this);
		top.add(radius);
		top.add(Box.createHorizontalGlue());

	}

	@Override
	protected void actionDone(Object source) {
		if (source == radius) {
			destination.removeAll();
			vgui = new ValueListGUI("Radius");
			vgui.setValueList(null,box.getRadius(), 100f, 0f, 20f,
					0.01f, 1f, 0f, 2.5f, 5f);
			destination.add(vgui);
		}
	}

	@Override
	public void setObjectMine(Object obj) {
		box = (CircleGenerator) obj;

	}

}
