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

import org.softmed.jops.PositionAnimator;
import org.softmed.jops.fileloading.converters.AngleRadiansConverter;


public class PositionAnimatorEditor extends SimpleEditor implements ActionListener {
    
    private static final long serialVersionUID = 980452429381183631L;
    
    private static final int BUTTON_HEIGHT = 20;

	Dimension dimension = new Dimension(120, BUTTON_HEIGHT);

	PositionAnimator animator;

	JButton position = new JButton("Position");

	JButton bias = new JButton("Bias");

	JButton biasAngle = new JButton("Bias Angle");

	JButton biasSpin = new JButton("Bias Spin");

	InfoObjectEditor infoEditor = new InfoObjectEditor();
	
	private ValueListGUI vgui;

	public PositionAnimatorEditor() {

		setDimension(position, dimension.width, dimension.height);
		setDimension(bias, dimension.width, dimension.height);
		setDimension(biasAngle, dimension.width, dimension.height);
		setDimension(biasSpin, dimension.width, dimension.height);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel der = new JPanel();
		der.setLayout(new BoxLayout(der, BoxLayout.X_AXIS));
		der.add(position);
		der.add(bias);
		der.add(Box.createHorizontalGlue());
		add(der);

		JPanel der2 = new JPanel();
		der2.setLayout(new BoxLayout(der2, BoxLayout.X_AXIS));
		der2.add(biasAngle);
		der2.add(biasSpin);
		der2.add(Box.createHorizontalGlue());
		add(der2);


		add(infoEditor);
		
		setActionListener(this);
	}
	
    @Override
	public void setObject(Object obj) {
		animator = (PositionAnimator) obj;
		infoEditor.setObject(obj);

	}

	public void setActionListener(ActionListener listener) {
		bias.addActionListener(listener);
		biasAngle.addActionListener(listener);
		biasSpin.addActionListener(listener);
		position.addActionListener(listener);
	}

	public void removeActionListener(ActionListener listener) {
		position.removeActionListener(listener);

		bias.removeActionListener(listener);
		biasAngle.removeActionListener(listener);
		biasSpin.removeActionListener(listener);

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == position) {
			destination.removeAll();
			vgui = new ValueListGUI("Position");
			vgui.setValueList(null,animator.getPosition());
			destination.add(vgui);
		} else if (source == bias) {
			destination.removeAll();
			vgui = new ValueListGUI("Bias");
			vgui.setValueList(null,animator.getBias());
			destination.add(vgui);
		} else if (source == biasAngle) {
			destination.removeAll();
			vgui = new ValueListGUI("Bias Angle");
			vgui.setValueList(new AngleRadiansConverter(),animator.getBiasAngle(), 1f, -360f, 360f, 1f, 1f,
					0f, 90f, 180f);
			destination.add(vgui);
		} else if (source == biasSpin) {
			destination.removeAll();
			vgui = new ValueListGUI("Bias Spin");
			vgui.setValueList(new AngleRadiansConverter(),animator.getBiasSpin(), 1f, -360f, 360f, 1f, 1f,
					0f, 90f, 180f);
			destination.add(vgui);
		} 
		
		refreshDestination();
	}

}
