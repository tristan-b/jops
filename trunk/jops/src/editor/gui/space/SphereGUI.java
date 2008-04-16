package gui.space;

import gui.value.ValueListGUI;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;

import org.softmed.jops.space.SphereGenerator;


public class SphereGUI extends BasicSpaceEditor {
    
    private static final long serialVersionUID = 7934397310381097154L;
    
    JButton innerRadius = new JButton("Inner Radius");

	JButton outerRadius = new JButton("Outter Radius");

	private SphereGenerator box;

	private ValueListGUI vgui;

	public SphereGUI(JComponent destination) {
		super(destination);
		setEasyDimension(innerRadius,widthB +40,heightB );
		setEasyDimension(outerRadius,widthB +40,heightB );
		innerRadius.addActionListener(this);
		outerRadius.addActionListener(this);
		
		top.add(outerRadius);
		top.add(innerRadius);
		top.add(Box.createHorizontalGlue());
		
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
		} 
	}

	@Override
	public void setObjectMine(Object obj) {
		box = (SphereGenerator) obj;

	}

}
