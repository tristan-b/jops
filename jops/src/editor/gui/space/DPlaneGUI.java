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

import org.softmed.jops.space.discreet.DPlaneGenerator;


public class DPlaneGUI extends PlaneGUI implements ChangeListener{
    
    private static final long serialVersionUID = 5771802072561406466L;
    
    SpinnerModel model = new SpinnerNumberModel(3,1,360,1);
	SpinnerModel model2 = new SpinnerNumberModel(3,1,360,1);
	
	JSpinner columns = new JSpinner(model);
	JSpinner lines = new JSpinner(model2); 
	
	DPlaneGenerator dcircle;
	
	public DPlaneGUI(JComponent destination) {
		super(destination);
		
		setEasyDimension(columns,widthB,heightB );
		setEasyDimension(lines,widthB,heightB );
		
		JPanel header = new JPanel();
		// top.setSize(90,20);
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		JLabel label = new JLabel("Lines");
		setEasyDimension(label,50,heightB );
		header.add(label);
		header.add(lines);
		header.add(Box.createHorizontalGlue());
		column.add(header);
		
		
		JPanel header2 = new JPanel();
		// top.setSize(90,20);
		header2.setLayout(new BoxLayout(header2, BoxLayout.X_AXIS));
		//header2.add(new JLabel("Columns"));
		label = new JLabel("Columns");
		setEasyDimension(label,50,heightB );
		header2.add(label);
		header2.add(columns);
		header2.add(Box.createHorizontalGlue());
		column.add(header2);
		
		
		
		
		//column.add(columns);
		//column.add(lines);
		
		
		lines.addChangeListener(this);
		columns.addChangeListener(this);
		
	}

	public void stateChanged(ChangeEvent e) {
	 
		dcircle.setColumns((Integer)columns.getValue());
		dcircle.setLines((Integer)lines.getValue());
		
	}
	
	@Override
	public void setObject(Object obj) {
		super.setObject(obj);
		
		dcircle = (DPlaneGenerator)obj;
		
		columns.setValue(dcircle.getColumns());
		lines.setValue(dcircle.getLines());

	}
	
}
