package gui.indicators;

import gui.value.ChangeListener;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.softmed.jops.fileloading.converters.IValueConverter;

public abstract class Indicator<T> extends JPanel {

	protected ChangeListener changeListener;

	protected T value;
	protected boolean signal = true;
	
	protected IValueConverter converter;
	
	public abstract void myRefresh();
	public abstract void mySetArgs(Object... args);
	public abstract void setCustomEnabled(boolean enabled);
	public abstract T constructNew();
	
	public void setArgs(Object... args)
	{
		signal = false;
		mySetArgs(args);
		signal = true;
	}
	
	public void refresh(){
		signal = false;
		myRefresh();
		signal = true;
	}

	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		signal = false;
		this.value = value;
		refresh();
		signal = true;
	}

	
	public void refreshGUI()
	{
		invalidate();
		revalidate();
		repaint();
	}
	

	public void signalChange() {
		if (changeListener != null)
			changeListener.changed(this);
	}

	public ChangeListener getChangeListener() {
		return changeListener;
	}

	public void setChangeListener(ChangeListener changeListener) {
		this.changeListener = changeListener;
	}

	public JSpinner getNewSpinner(int min, int max, int step, int init) {

		SpinnerModel model = new SpinnerNumberModel(init, min, max, step);
		return new JSpinner(model);
	}
	
	public JSlider getNewSlider(int min, int max, int init) {
		signal = false;
		JSlider s = new JSlider();
		//s.setMinorTickSpacing(10);
		//s.setMajorTickSpacing(1000);
		s.getModel().setMinimum(min);
		s.getModel().setMaximum(max);
		s.getModel().setValue(init);
		s.setPaintLabels(true);
		s.setPaintTicks(true);
		//s.setp
		signal = true;
		return s;
	}
//*/
	
	public void setKeyboardEditOnSpinner(JSpinner spinner, boolean edit)
	{
		JFormattedTextField tf =
	        ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
	    tf.setEditable(edit);
	}
	
	public boolean isSignal() {
		return signal;
	}
	public void setSignal(boolean signal) {
		this.signal = signal;
	}
	public IValueConverter getConverter() {
		return converter;
	}
	public void setConverter(IValueConverter converter) {
		this.converter = converter;
	}
	
}
