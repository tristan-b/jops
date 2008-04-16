package gui.space;

import gui.value.ValueListGUI;

import javax.swing.JButton;
import javax.swing.JComponent;

import org.softmed.jops.space.CylinderGenerator;


public class CylinderGUI extends BasicSpaceEditor {
    
    private static final long serialVersionUID = -846073051792326329L;
    
    JButton innerRadius = new JButton("In Rad");

	JButton height = new JButton("Width");

	JButton outerRadius = new JButton("Out Rad");

	private CylinderGenerator box;

	private ValueListGUI vgui;

	public CylinderGUI(JComponent destination) {
		super(destination);
		setEasyDimension(innerRadius,widthB ,heightB );
		setEasyDimension(height,widthB ,heightB );
		setEasyDimension(outerRadius,widthB ,heightB );
		
		innerRadius.addActionListener(this);
		height.addActionListener(this);
		outerRadius.addActionListener(this);
		top.add(height);
		top.add(outerRadius);
		top.add(innerRadius);
		
	}

	@Override
	protected void actionDone(Object source) {
		if (source == innerRadius) {
			destination.removeAll();
			vgui = new ValueListGUI("Inner Radius");
			vgui.setValueList(null,box.getInnerRadius(), 100f, 0f, 20f,
					0.01f, 1f, 0f, 2.5f, 5f);
			destination.add(vgui);
		} else if (source == outerRadius) {
			destination.removeAll();
			vgui = new ValueListGUI("Outter Radius");
			vgui.setValueList(null,box.getOuterRadius(), 100f, 0f, 20f,
					0.01f, 1f, 0f, 2.5f, 5f);
			destination.add(vgui);
		} else if (source == height) {
			destination.removeAll();
			vgui = new ValueListGUI("Width");
			vgui.setValueList(null,box.getHeight(), 100f, 0f, 20f,
					0.01f, 1f, 0f, 2.5f, 5f);
			destination.add(vgui);
		}
	}

	@Override
	public void setObjectMine(Object obj) {
		box = (CylinderGenerator) obj;

	}

}
