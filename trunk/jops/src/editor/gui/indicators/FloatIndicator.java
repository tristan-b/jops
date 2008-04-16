package gui.indicators;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class FloatIndicator extends Indicator<Float> implements ChangeListener {
    
    private static final long serialVersionUID = -3876204047390402516L;
    
    JSpinner spinner;

	JSlider slider;
	
	float multiplier;

	public FloatIndicator() {
		setMaximumSize(new Dimension(270,50));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		spinner = getNewSpinner(0, 100, 5, 50);
		spinner.setMaximumSize(new Dimension(40, 20));
		setKeyboardEditOnSpinner(spinner, false);
		spinner.addChangeListener(this);
		add(spinner);

		slider = getNewSlider(-100, 100, 50);
		slider.setMaximumSize(new Dimension(270, 50));
		slider.addChangeListener(this);
		add(slider);
		
		//JFormattedTextField t = ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
		//t.setVisible(false);
		//t.setBorder(null);
		//this.setBorder(null);
		//t.remo
		
		
	}

	@Override
	public void myRefresh() {
		signal = false;
		if (value != null) {
			
			int temp = -1;
			if(converter == null )
				temp = (int) (value.floatValue() * multiplier);
			else
				temp = (int) ((Float)converter.fromInternalToExternal(value) * multiplier);
			
			spinner.setValue(temp);
			slider.setValue(temp);
			
			// JFormattedTextField t = ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
			// t.setText("S:"+value.floatValue());
		}
		signal = true;
	}

	@Override
	public void mySetArgs(Object... args) {
		multiplier = (Float)args[0];
		float min = (Float)args[1];
		float max = (Float)args[2];
		float spinnerStep = (Float)args[3];
		float sliderStep = (Float)args[4];

		
		float init = (Float)args[5];
		float minorTick = (Float)args[6];
		float majorTick = (Float)args[7];

		SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
		//model.set
		
		model.setMinimum((Integer)((int)(min*multiplier)));
		model.setMaximum((Integer)((int)(max*multiplier)));
		slider.setMinimum((int) (min*multiplier));
		slider.setMaximum((int) ((max)*multiplier)+1);
		
		model.setStepSize((Integer)((int)(spinnerStep*multiplier)));
		slider.setExtent((int) ((sliderStep)*multiplier));
		
		
		slider.setMinorTickSpacing((int) (minorTick*multiplier));
		slider.setMajorTickSpacing((int) (majorTick*multiplier));
		
		
		model.setValue((Integer)((int)(init*multiplier)));
		slider.setValue((int) (init*multiplier));
		
	
	}

	@SuppressWarnings("static-access")
	public void stateChanged(ChangeEvent e) {
		if (!signal)
			return;

		signal = false;

		if (e.getSource() == slider) {
			int temp = slider.getValue();
			spinner.setValue(temp);
		} else {
			Object temp = spinner.getValue();
			slider.setValue((Integer) temp);
		}

		Integer temp = (Integer) spinner.getValue();
		float result = temp.floatValue() / multiplier;

		if(converter==null)
		value = result;
		else
		value = (Float)converter.fromExternalToInternal(result);

		refresh();

		signal = true;

		signalChange();
	}
	
    @Override
	public void setCustomEnabled(boolean enabled)
	{
		spinner.setEnabled(enabled);
		slider.setEnabled(enabled);
		//super.setEnabled(enabled);
	}

	@Override
	public Float constructNew() {
		
		return new Float(0.0f);
	}

	public JSlider getSlider() {
		return slider;
	}

	public JSpinner getSpinner() {
		return spinner;
	}
	
}
