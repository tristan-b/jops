package gui.space;

import gui.value.ValueListGUI;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;

import org.softmed.jops.space.simple.PlaneGenerator;


public class PlaneGUI extends BasicSpaceEditor {
    
    private static final long serialVersionUID = -1128120414641263984L;
    
    protected JButton width = new JButton("Width");

	protected JButton height = new JButton("Height");


	protected  PlaneGenerator box;

	protected  ValueListGUI vgui;

	public PlaneGUI(JComponent destination) {
		super(destination);
		setEasyDimension(width,widthB +40,heightB );
		setEasyDimension(height,widthB +40,heightB );
		width.addActionListener(this);
		height.addActionListener(this);
	
		top.add(width);
		top.add(height);
		top.add(Box.createHorizontalGlue());
		
		
	}

	@Override
	protected void actionDone(Object source) {
		if (source == width) {
			destination.removeAll();
			vgui = new ValueListGUI("Width");
			vgui.setValueList(null,box.getWidth(), 100f, 0f, 20f,
					0.01f, 1f, 0f, 2.5f, 5f);
			destination.add(vgui);
		} else if (source == height) {
			destination.removeAll();
			vgui = new ValueListGUI("Height");
			vgui.setValueList(null,box.getHeight(), 100f, 0f, 20f,
					0.01f, 1f, 0f, 2.5f, 5f);
			destination.add(vgui);
		}
	}

	@Override
	public void setObjectMine(Object obj) {
		box = (PlaneGenerator) obj;

	}

}
