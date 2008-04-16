package gui.space;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.softmed.jops.space.discreet.DLineGenerator;


public class DLineGUI extends LineGUI implements ChangeListener{
    
    private static final long serialVersionUID = 8351826799533959562L;
    
    SpinnerModel model = new SpinnerNumberModel(3,1,360,1);
	JSpinner points = new JSpinner(model);
	
	DLineGenerator dcircle;
	
	public DLineGUI(JComponent destination) {
		super(destination);
		
		setEasyDimension(points,widthB ,heightB );
		
		
		
		JPanel header = new JPanel();
		// top.setSize(90,20);
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		header.add(new JLabel("Points"));
		header.add(points);
		header.add(Box.createHorizontalGlue());
		column.add(header);
		
		
		points.addChangeListener(this);
		
	}

	public void stateChanged(ChangeEvent e) {
	 
		dcircle.setPoints((Integer)points.getValue());
		
	}
	
	@Override
	public void setObject(Object obj) {
		super.setObject(obj);
		
		dcircle = (DLineGenerator)obj;
		points.setValue(dcircle.getPoints());
	}
	
}
