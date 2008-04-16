package gui.value;

import gui.Editor;
import gui.SizeablePanel;
import gui.indicators.FloatIndicator;
import gui.indicators.Indicator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.softmed.jops.fileloading.converters.IValueConverter;
import org.softmed.jops.valuelists.GeneralRandomValueList;


public class ValueListGUI extends SizeablePanel implements 
		ActionListener, ChangeListener {
    
    private static final long serialVersionUID = 5183013368870592757L;
    
    JTabbedPane tabbedPane = new JTabbedPane();
	SimpleValueListGUI valueList = new SimpleValueListGUI("Values");
	SimpleValueListGUI randomValueList = new SimpleValueListGUI("Random");
	
	JCheckBox active = new JCheckBox("Valid");
	JCheckBox random = new JCheckBox("Random");
	FloatIndicator bias = new FloatIndicator();
	
	JLabel biasLabel = new JLabel("Bias");
	
	ValueListChangedListener listener;


	boolean viewActiveCheckBox = false;
	
	GeneralRandomValueList gvaluelist;

	private IValueConverter converter;

	//private Object[] args;

	
	public ValueListGUI(String title) {
		setDimension(590, 260);
		
		bias.mySetArgs(100f, 0f, 20f, 0.01f, 1f, 20f, 5f, 10f);
		
		active.setVisible(false);
		bias.setVisible(false);
		biasLabel.setVisible(false);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		tabbedPane.addTab( "Values", valueList );
		tabbedPane.addTab( "Random", randomValueList );
		
		setDimension(tabbedPane,590, 210);
		
		JPanel shor = new JPanel();
		shor.setLayout(new BoxLayout(shor, BoxLayout.LINE_AXIS));

		JLabel label = new JLabel(title);
		shor.add(label);
		shor.add(active);
		shor.add(random);
		shor.add(Box.createHorizontalStrut(50));
		shor.add(biasLabel);
		shor.add(bias);
		
		add(shor);

		add(tabbedPane);
		
		active.addActionListener(this);
		random.addActionListener(this);
		bias.setChangeListener(this);
		
		
		invalidate();
		revalidate();
		repaint();

	}

	public void setValueList(IValueConverter converter, GeneralRandomValueList gvaluelist, Object... args) {
		this.gvaluelist = gvaluelist;
		this.converter = converter;
		//this.args = args;

		
		valueList.setValueList(converter,gvaluelist.getMainValueList(), args);
		randomValueList.setValueList(converter,gvaluelist.getRandomValueList(), args);
		
		valueList.setListener(listener);
		randomValueList.setListener(listener);
		
		bias.setValue(gvaluelist.getBias());
		random.setSelected(gvaluelist.isRandom());
		active.setSelected(gvaluelist.isActive());
		
		invalidate();
		revalidate();
		repaint();
	}
	
	
	




	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		Editor.setDirty(true);
		
		if (source == active){
			gvaluelist.setActive(active.isSelected());
			listener.changed();
		}
		else if (source == random)
			gvaluelist.setRandom(random.isSelected());

	}

	public ValueListChangedListener getListener() {
		return listener;
	}

	public void setListener(ValueListChangedListener listener) {
		this.listener = listener;
	}

	public boolean isActive() {
		return viewActiveCheckBox;
	}

	public void setActive(boolean valid) {
		this.viewActiveCheckBox = valid;
		this.active.setVisible(valid);
		bias.setVisible(valid);
		biasLabel.setVisible(valid);
	}


	
	public void changed(Indicator indicator) {
		Editor.setDirty(true);
		gvaluelist.setBias((Float) indicator.getValue());
	}


}

