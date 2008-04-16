package gui.indicators;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openmali.vecmath2.Colorf;

public class ColorIndicator extends Indicator<Colorf> implements
		ChangeListener, ActionListener {

	private static final long serialVersionUID = 8858010261896708925L;

	JSpinner red;

	JSpinner green;

	JSpinner blue;

	JButton picker = new JButton("Pick");

	JButton ok = new JButton("Choose");

	JColorChooser chooser;

	private JFrame frame;

//	private IValueConverter converter;

	@Override
	public void setCustomEnabled(boolean enabled) {
		red.setEnabled(enabled);
		green.setEnabled(enabled);
		blue.setEnabled(enabled);
		picker.setEnabled(enabled);

	}

	public ColorIndicator() {
		setPreferredSize(new Dimension(270, 20));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		red = getNewSpinner(0, 255, 10, 255);
		green = getNewSpinner(0, 255, 10, 255);
		blue = getNewSpinner(0, 255, 10, 255);

		setKeyboardEditOnSpinner(red, false);
		setKeyboardEditOnSpinner(green, false);
		setKeyboardEditOnSpinner(blue, false);

		red.addChangeListener(this);
		green.addChangeListener(this);
		blue.addChangeListener(this);

		red.setPreferredSize(new Dimension(60, 20));
		green.setPreferredSize(new Dimension(60, 20));
		blue.setPreferredSize(new Dimension(60, 20));

		red.setMaximumSize(new Dimension(60, 20));
		green.setMaximumSize(new Dimension(60, 20));
		blue.setMaximumSize(new Dimension(60, 20));

		red.setSize(new Dimension(60, 20));
		green.setSize(new Dimension(60, 20));
		blue.setSize(new Dimension(60, 20));

		JLabel label = new JLabel("R");
		add(label);
		add(red);

		label = new JLabel("G");
		add(label);
		add(green);

		label = new JLabel("B");
		add(label);
		add(blue);

		ok.addActionListener(this);
		picker.addActionListener(this);
		picker.setPreferredSize(new Dimension(60, 20));
		picker.setMinimumSize(new Dimension(60, 20));
		picker.setMaximumSize(new Dimension(60, 20));
		picker.setSize(new Dimension(60, 20));
		add(picker);

	}

	@Override
	public void myRefresh() {
		if (converter == null) {
			red.setValue((int) value.getRed());
			green.setValue((int) value.getGreen());
			blue.setValue((int) value.getBlue());

			java.awt.Color color = value.getAWTColor();
			picker.setBackground(color);
		} else {
			Float r = (Float) converter.fromInternalToExternal(value.getRed());
			Float g = (Float) converter.fromInternalToExternal(value.getGreen());
			Float b = (Float) converter.fromInternalToExternal(value.getBlue());
			red.setValue(r.intValue());
			green.setValue(g.intValue());
			blue.setValue(b.intValue());

			java.awt.Color color = new java.awt.Color(r.intValue(),g.intValue(),b.intValue());
			picker.setBackground(color);
		}

	}

	@Override
	public void mySetArgs(Object... args) {
		// TODO Auto-generated method stub

	}

	public void stateChanged(ChangeEvent e) {
		if (!signal)
			return;

		signal = false;
		if (converter == null) {
			value.setRedByte( ((Integer) red.getValue()).byteValue() );
            value.setGreenByte( ((Integer) green.getValue()).byteValue() );
            value.setBlueByte( ((Integer) blue.getValue()).byteValue() );
		} else {
			value.setRed( (Float) converter.fromExternalToInternal(red
					.getValue()));
			value.setGreen( (Float) converter.fromExternalToInternal(green
					.getValue()));
			value.setBlue( (Float) converter.fromExternalToInternal(blue
					.getValue()));
		}

		refresh();

		signal = true;

		signalChange();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == picker) {

			frame = new JFrame("Pick a Color");
			chooser = new JColorChooser();
			chooser.setColor(new java.awt.Color(((Float) converter.fromInternalToExternal(value.getRed())).intValue(),
					((Float) converter.fromInternalToExternal(value.getGreen())).intValue(),((Float) converter.fromInternalToExternal(value.getBlue())).intValue()));
			frame.getContentPane().setLayout(
					new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
			frame.getContentPane().add(chooser);
			frame.getContentPane().add(ok);
			frame.pack();
			frame.setVisible(true);

			// signal = false;
		} else {
			java.awt.Color color = chooser.getColor();

			if (converter == null) {
			    value.set( color );
			} else {
				value.setRed( (Float) converter.fromExternalToInternal(color.getRed()));
				value.setGreen ((Float) converter
						.fromExternalToInternal(color.getGreen()));
				value.setBlue((Float) converter
						.fromExternalToInternal(color.getBlue()));

			}

			frame.setVisible(false);
			frame = null;
			chooser = null;

			refresh();

			// change the values on the
			// signal = true;
			this.signalChange();
		}

	}

	@Override
	public Colorf constructNew() {
		return new Colorf();
	}



}
