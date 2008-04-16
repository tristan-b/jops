package gui.indicators;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.openmali.vecmath2.Vector3f;

public class Vector3fIndicator extends Indicator<Vector3f> implements
		ChangeListener, KeyListener {

	private static final long serialVersionUID = -1362730143318414079L;

	int width = 70;
	int height = 20;

	JSpinner x;

	JSpinner y;

	JSpinner z;

	float multiplier = 10f;

	Integer i;
	float f;

	public Vector3fIndicator() {
		setPreferredSize(new Dimension(160, 30));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		x = getNewSpinner(-1000, 1000, 1, 0);
		y = getNewSpinner(-1000, 1000, 1, 0);
		z = getNewSpinner(-1000, 1000, 1, 0);

		// setKeyboardEditOnSpinner(red, false);
		// setKeyboardEditOnSpinner(green, false);
		// setKeyboardEditOnSpinner(blue, false);

		addKeyListener(x);
		addKeyListener(y);
		addKeyListener(z);

		x.addChangeListener(this);
		y.addChangeListener(this);
		z.addChangeListener(this);

		x.setPreferredSize(new Dimension(width, height));
		y.setPreferredSize(new Dimension(width, height));
		z.setPreferredSize(new Dimension(width, height));

		x.setMaximumSize(new Dimension(width, height));
		y.setMaximumSize(new Dimension(width, height));
		z.setMaximumSize(new Dimension(width, height));

		x.setSize(new Dimension(width, height));
		y.setSize(new Dimension(width, height));
		z.setSize(new Dimension(width, height));

		JLabel label = new JLabel("X");
		add(label);
		add(x);

		label = new JLabel("Y");
		add(label);
		add(y);

		label = new JLabel("Z");
		add(label);
		add(z);

	}

	@Override
	public void myRefresh() {
		x.setValue((int) (value.getX() * multiplier));
		y.setValue((int) (value.getY() * multiplier));
		z.setValue((int) (value.getZ() * multiplier));
	}

	@Override
	public void mySetArgs(Object... args) {
		if (args == null)
			return;

		if (args.length > 1)
			multiplier = (Float) args[0];
		// */

	}

	protected void addKeyListener(JSpinner x) {
		JFormattedTextField tf = ((JSpinner.DefaultEditor) x.getEditor())
				.getTextField();
		tf.addKeyListener(this);
	}

	@Override
	public void setCustomEnabled(boolean enabled) {
		x.setEnabled(enabled);
		y.setEnabled(enabled);
		z.setEnabled(enabled);

	}

	@Override
	public Vector3f constructNew() {

		return new Vector3f();
	}

	public void stateChanged(ChangeEvent e) {

		changeState();

	}

	protected void changeState() {
		if (!signal)
			return;

		signal = false;

		// Object o = null;
		// o = x.getValue();
		i = (Integer) x.getValue();
		f = i.floatValue() / multiplier;
		value.setX(f);

		// o = y.getValue();
		i = (Integer) y.getValue();
		f = i.floatValue() / multiplier;
		value.setY(f);

		// o = z.getValue();
		i = (Integer) z.getValue();
		f = i.floatValue() / multiplier;
		value.setZ(f);

		refresh();

		signal = true;

		signalChange();
	}

	public void keyPressed(KeyEvent arg0) {

		changeState();

	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {

	}

}
