package gui.modifier;

import gui.CustomListCellRenderer;
import gui.DetailViewer;
import gui.Editor;
import gui.SimpleEditor;
import gui.value.ValueListGUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.softmed.jops.ParticleSystem;
import org.softmed.jops.PositionAnimator;
import org.softmed.jops.modifiers.PointMass;


public class PointMassEditor extends SimpleEditor implements ActionListener {
    
    private static final long serialVersionUID = -6931886609132223224L;
    
    private static final int BUTTON_HEIGHT = 20;

	Dimension dimension = new Dimension(120, BUTTON_HEIGHT);

	PointMass force;

	
	
	JButton threshold = new JButton("Threshold");

	JButton strength = new JButton("Strength");

	JButton massImportance = new JButton("Mass Relevance");
	
	JButton distanceImportance = new JButton("Dist. Relevance");

	
	JComboBox spaceAnimator = new JComboBox();
	JButton editAnimator = new JButton("Edit");
	
	String[] variations = { PointMass.CONSTANT, PointMass.LINEAR, PointMass.SQUARED };
	String[] proportions = { PointMass.DIRECT, PointMass.INVERSE };
	
	
	JCheckBox applyThreshold = new JCheckBox("Apply Threshold");
	
	JComboBox distanceVariation = new JComboBox(variations);
	JComboBox distanceProportion = new JComboBox(proportions);
	
	
	private ValueListGUI vgui;

	private boolean dontSet;

	private DetailViewer viewer;

	public PointMassEditor(ParticleSystem ps, DetailViewer viewer) {
		this.viewer = viewer;
		setDimension(240, 150);
		
		setDimension(distanceVariation, 110, 20);
		setDimension(distanceProportion, 110, 20);
		
		
		
		setDimension(spaceAnimator, 180, 20);
		setDimension(editAnimator, 60, 20);
		
		setDimension(threshold, dimension.width, dimension.height);
		setDimension(strength, dimension.width, dimension.height);
		setDimension(massImportance, dimension.width, dimension.height);
		setDimension(distanceImportance, dimension.width, dimension.height);

		spaceAnimator.addActionListener(this);
		spaceAnimator.setRenderer(new CustomListCellRenderer());
		editAnimator.addActionListener(this);
		

		distanceVariation.addActionListener(this);
		distanceProportion.addActionListener(this);
		applyThreshold.addActionListener(this);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		
		JPanel der = new JPanel();
		der.setLayout(new BoxLayout(der, BoxLayout.X_AXIS));
		der.add(strength);
		
		der.add(Box.createHorizontalGlue());
		add(der);


		JPanel der3 = new JPanel();
		der3.setLayout(new BoxLayout(der3, BoxLayout.X_AXIS));
		der3.add(massImportance);
		der3.add(distanceImportance);
		der3.add(Box.createHorizontalGlue());
		add(der3);
		
		
		
		
		
		
		JPanel first = new JPanel();
		first.setLayout(new BoxLayout(first, BoxLayout.X_AXIS));
		first.add(threshold);
		first.add(Box.createHorizontalGlue());
		first.add(applyThreshold);		
		add(first);
		

		JPanel labels = new JPanel();
		labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		JLabel label = new JLabel("Dist. Variation");
		labelPanel.add(label);
		labelPanel.add(Box.createHorizontalGlue());
		
		labels.add(labelPanel);
		labels.add(distanceVariation);
		//labels.add(Box.createHorizontalGlue());
		//add(labels);	
		
		JPanel second = new JPanel();
		second.setLayout(new BoxLayout(second, BoxLayout.Y_AXIS));
		
		labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		label = new JLabel("Dist. Proportion");
		labelPanel.add(label);
		labelPanel.add(Box.createHorizontalGlue());
		
		second.add(labelPanel);
		second.add(distanceProportion);
		//second.add(Box.createHorizontalGlue());
		//add(second);	
		
		JPanel choosers = new JPanel();
		choosers.setLayout(new BoxLayout(choosers, BoxLayout.X_AXIS));
		choosers.add(labels);
		choosers.add(Box.createHorizontalGlue());
		choosers.add(second);
		add(choosers);
		
		
		
		
		add(setToLeft(new JLabel("Position Animator")));
		add(getPanel(spaceAnimator, editAnimator));

		spaceAnimator.addItem("N/A");
		for (PositionAnimator animator : ps.getPanimators()) {
			spaceAnimator.addItem(animator);
		}
		
		
		
		setActionListener(this);
		//setDimension(250, 150);
	}
	
    @Override
	public void setObject(Object obj) {
		force =  (PointMass) obj;
		
		dontSet = true;
		spaceAnimator.setSelectedItem(force.getPositionAnimator());
		applyThreshold.setSelected(force.isThreshold());
		
		
		distanceVariation.setSelectedItem(force.getDistanceVariation());
		distanceProportion.setSelectedItem(force.getDistanceProportion());
		
		dontSet = false;
	}

	public void setActionListener(ActionListener listener) {
		threshold.addActionListener(listener);
		strength.addActionListener(listener);
		massImportance.addActionListener(listener);
		distanceImportance.addActionListener(listener);
	}

	public void removeActionListener(ActionListener listener) {
		threshold.removeActionListener(listener);
		strength.removeActionListener(listener);
		massImportance.removeActionListener(listener);
		distanceImportance.removeActionListener(listener);

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if(force == null || dontSet)
			return;
		
		if (source == applyThreshold) {	Editor.DIRTY = true;
		force.setThreshold(applyThreshold.isSelected());}
		else
		if (source == threshold) {
			destination.removeAll();
			vgui = new ValueListGUI("Treshold");
			vgui.setValueList(null,force.getThresholdValue(), 10f, -10f, 10f, 0.1f, 0.1f,
					0.5f, 5f, 10f);
			destination.add(vgui);
		} else if (source == strength) {
			destination.removeAll();
			vgui = new ValueListGUI("Strength");
			vgui.setValueList(null,force.getStrength(), 10f, -20f, 20f, 0.1f, 0.1f,
					0.5f, 5f, 10f);
			destination.add(vgui);
		} else if (source == massImportance) {
			destination.removeAll();
			vgui = new ValueListGUI("Mass Relevance");
			vgui.setValueList(null,force.getMassImportance(), 10f, -20f, 20f, 0.1f, 0.1f,
					0.5f, 5f, 10f);
			destination.add(vgui);
		} else if (source == distanceImportance) {
			destination.removeAll();
			vgui = new ValueListGUI("Distance Relevance");
			vgui.setValueList(null,force.getDistanceImportance(), 10f, -20f, 20f, 0.1f, 0.1f,
					0.5f, 5f, 10f);
			destination.add(vgui);
		}else if (source == editAnimator) {
			viewer.getBar().getTree().setSelectedPath(
					spaceAnimator.getSelectedItem());
		}else if (source == spaceAnimator) {	Editor.DIRTY = true;
		if (spaceAnimator.getSelectedItem() instanceof String)
			force.setPositionAnimator(null);
		else
			force.setPositionAnimator((PositionAnimator) spaceAnimator
					.getSelectedItem());
		}
		else if (source == distanceVariation) {	Editor.DIRTY = true;
			force.setDistanceVariation( (String) distanceVariation.getSelectedItem());
	} else if (source == distanceProportion) {	Editor.DIRTY = true;
			force.setDistanceProportion( (String) distanceProportion.getSelectedItem());
	} 
		
		
		refreshDestination();
	}

	protected JPanel getPanel(JComponent a, JComponent b) {
		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		header.add(a);
		header.add(b);
		header.add(Box.createHorizontalGlue());
		return header;
	}
	
	

	protected JPanel setToLeft(JComponent c){
		JPanel header2 = new JPanel();
		header2.setLayout(new BoxLayout(header2, BoxLayout.X_AXIS));
		header2.add(c);
		header2.add(Box.createHorizontalGlue());
		return header2;
	}
	
}
