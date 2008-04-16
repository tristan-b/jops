package gui.generator;

import gui.InfoObjectEditor;
import gui.SimpleEditor;
import gui.value.ValueListGUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.softmed.jops.SpaceAnimator;
import org.softmed.jops.fileloading.converters.AngleRadiansConverter;


public class SpaceAnimatorEditor extends SimpleEditor implements ActionListener {
    
    private static final long serialVersionUID = -7088142754249032771L;
    
    private static final int BUTTON_HEIGHT = 20;

	Dimension dimension = new Dimension(120, BUTTON_HEIGHT);

	SpaceAnimator animator;

	JButton spaceAngle = new JButton("Space Angle");

	JButton spaceSpin = new JButton("Space Spin");

	InfoObjectEditor infoEditor = new InfoObjectEditor();

	private ValueListGUI vgui;

	public SpaceAnimatorEditor() {

		setDimension(spaceAngle, dimension.width, dimension.height);
		setDimension(spaceSpin, dimension.width, dimension.height);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel der3 = new JPanel();
		der3.setLayout(new BoxLayout(der3, BoxLayout.X_AXIS));
		der3.add(spaceAngle);
		der3.add(spaceSpin);
		der3.add(Box.createHorizontalGlue());
		add(der3);

		add(infoEditor);

		setActionListener(this);
	}
	
    @Override
	public void setObject(Object obj) {
		animator = (SpaceAnimator) obj;
		infoEditor.setObject(obj);

	}

	public void setActionListener(ActionListener listener) {
		spaceAngle.addActionListener(listener);
		spaceSpin.addActionListener(listener);
	}

	public void removeActionListener(ActionListener listener) {
		spaceAngle.removeActionListener(listener);
		spaceSpin.removeActionListener(listener);

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == spaceAngle) {
			destination.removeAll();
			vgui = new ValueListGUI("Space Angle");
			vgui.setValueList(new AngleRadiansConverter(),animator.getSpaceAngle(), 1f, -360f, 360f, 1f,
					1f, 0f, 90f, 180f);
			destination.add(vgui);
		} else if (source == spaceSpin) {
			destination.removeAll();
			vgui = new ValueListGUI("Space Spin");
			vgui.setValueList(new AngleRadiansConverter(),animator.getSpaceSpin(), 1f, -360f, 360f, 1f, 1f,
					0f, 90f, 180f);
			destination.add(vgui);
		}

		refreshDestination();
	}

}
