package gui.space;

import gui.value.ValueListGUI;

import javax.swing.JButton;
import javax.swing.JComponent;

import org.softmed.jops.space.BoxGenerator;


public class BoxGUI extends BasicSpaceEditor {
    
    private static final long serialVersionUID = -1097935261340043561L;
    
    JButton width = new JButton("width");

	JButton height = new JButton("height");

	JButton depth = new JButton("depth");

	private BoxGenerator box;

	private ValueListGUI vgui;

	public BoxGUI(JComponent destination) {
		super(destination);
		setEasyDimension(width,widthB ,heightB );
		setEasyDimension(height,widthB ,heightB );
		setEasyDimension(depth,widthB ,heightB );
		width.addActionListener(this);
		height.addActionListener(this);
		depth.addActionListener(this);
		top.add(width);
		top.add(depth);
		top.add(height);
	}

	@Override
	protected void actionDone(Object source) {
		if (source == width) {
			destination.removeAll();
			vgui = new ValueListGUI("Width");
			vgui.setValueList(null,box.getWidth(), 100f, 0f, 20f,
					0.01f, 1f, 0f, 2.5f, 5f);
			destination.add(vgui);
		} else if (source == depth) {
			destination.removeAll();
			vgui = new ValueListGUI("Depth");
			vgui.setValueList(null,box.getDepth(), 100f, 0f, 20f,
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
		box = (BoxGenerator) obj;

	}

}
