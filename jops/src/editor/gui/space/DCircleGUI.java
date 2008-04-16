package gui.space;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.softmed.jops.space.discreet.DCircleGenerator;


public class DCircleGUI extends CircleGUI implements ChangeListener{
    
    private static final long serialVersionUID = 2456005276966487991L;
    
    SpinnerModel model = new SpinnerNumberModel(3,1,360,1);
	JSpinner points = new JSpinner(model);
	
	DCircleGenerator dcircle;
	
	public DCircleGUI(JComponent destination) {
		super(destination);
		setEasyDimension(points,widthB ,heightB );
		top.add(new JLabel("Points"));
		top.add(points);
		points.addChangeListener(this);
		
	}

	public void stateChanged(ChangeEvent e) {
	 
		dcircle.setPoints((Integer)points.getValue());
		
	}
	
	
	@Override
	public void setObject(Object obj) {
		super.setObject(obj);
		
		dcircle = (DCircleGenerator)obj;
		//System.out.println("Circle with ->" + dcircle.getPoints());
		points.setValue(dcircle.getPoints());

	}
	
}
