package gui.generator;

import gui.Editor;
import gui.SimpleEditor;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.softmed.jops.GeneratorBehaviour;


public class GeneratorBehaviourChooser extends SimpleEditor implements
		ChangeListener {
    
    private static final long serialVersionUID = 2079677575177730752L;
    
    private static final int BUTTON_HEIGHT = 20;

	Dimension dimension = new Dimension(80, BUTTON_HEIGHT);

	JSpinner number;

	GeneratorBehaviour behaviour;

	JButton life = new JButton("Life");

	JButton rate = new JButton("Rate");

	JButton VerticalAngle = new JButton("V Angle");

	JButton HorizontalAngle = new JButton("H Angle");
	
	JButton VerticalAngleSpin = new JButton("V Spin");

	JButton HorizontalAngleSpin = new JButton("H Spin");

	JButton VerticalAngleSpread = new JButton("V A Spread");

	JButton HorizontalAngleSpread = new JButton("H A Spread");

	
	//InfoObjectEditor infoEditor = new InfoObjectEditor();
	

	ParticleBehaviourChooser pchooser = new ParticleBehaviourChooser();

	private boolean signal = true;

	public GeneratorBehaviourChooser() {
		SpinnerModel model = new SpinnerNumberModel(0, 0, 500000, 1);
		number = new JSpinner(model);
		number.addChangeListener(this);

		

		number.setMaximumSize(new Dimension(20, 20));

		
		configureSize(life);
		configureSize(rate);

		

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	

		JPanel header = new JPanel();
		// top.setSize(90,20);
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		header.add(new JLabel("Max. Particles"));
		header.add(number);
		header.add(Box.createHorizontalGlue());
		add(header);

		JPanel second = new JPanel();
		// top.setSize(90,20);
		second.setLayout(new BoxLayout(second, BoxLayout.X_AXIS));
		second.add(life);
		second.add(HorizontalAngleSpread);
		
		second.add(Box.createHorizontalGlue());
		add(second);
		
		
		JPanel middle2 = new JPanel();
		middle2.setLayout(new BoxLayout(middle2, BoxLayout.X_AXIS));
		configureSize(HorizontalAngleSpread, 120, BUTTON_HEIGHT);
		configureSize(VerticalAngleSpread, 120, BUTTON_HEIGHT);
		middle2.add(rate);
		middle2.add(VerticalAngleSpread);
		middle2.add(Box.createHorizontalGlue());
		add(middle2);
		
/*
		JPanel third = new JPanel();
		// top.setSize(90,20);
		third.setLayout(new BoxLayout(third, BoxLayout.X_AXIS));
		// third.add(speed);
		//third.add(absoluteAngle);
		third.add(Box.createHorizontalGlue());
		add(third);
//*/




		JPanel middle = new JPanel();
		middle.setLayout(new BoxLayout(middle, BoxLayout.X_AXIS));
		configureSize(HorizontalAngle, 120, BUTTON_HEIGHT);
		configureSize(VerticalAngle, 120, BUTTON_HEIGHT);
		middle.add(HorizontalAngle);
		middle.add(HorizontalAngleSpin);
		

		middle.add(Box.createHorizontalGlue());
		add(middle);
		
		
		JPanel middle3 = new JPanel();
		middle3.setLayout(new BoxLayout(middle3, BoxLayout.X_AXIS));
		configureSize(VerticalAngleSpin, 120, BUTTON_HEIGHT);
		configureSize(HorizontalAngleSpin, 120, BUTTON_HEIGHT);
		middle3.add(VerticalAngle);
		middle3.add(VerticalAngleSpin);
		

		middle3.add(Box.createHorizontalGlue());
		add(middle3);



		add(pchooser);


		//add(infoEditor);
		
		add(Box.createVerticalGlue());
	}

	@Override
	public void setObject(Object obj) {
		signal  = false;
		behaviour = (GeneratorBehaviour) obj;
		number.setValue(behaviour.getNumber());
		pchooser.setObject(obj);
		signal = true;
	}

	
	public void configureSize(JComponent c) {
		c.setPreferredSize(dimension);
	}

	public void configureSize(JComponent c, int x, int y) {
		c.setPreferredSize(new Dimension(x, y));
	}

	public JButton getHorizontalAngle() {
		return HorizontalAngle;
	}

	public JButton getHorizontalAngleSpread() {
		return HorizontalAngleSpread;
	}

	public JButton getLife() {
		return life;
	}

	public ParticleBehaviourChooser getPchooser() {
		return pchooser;
	}


	public JButton getRate() {
		return rate;
	}

	public JButton getVerticalAngle() {
		return VerticalAngle;
	}

	public JButton getVerticalAngleSpread() {
		return VerticalAngleSpread;
	}

	public void setActionListener(ActionListener listener) {

		
		life.addActionListener(listener);
		rate.addActionListener(listener);

		VerticalAngle.addActionListener(listener);
		HorizontalAngle.addActionListener(listener);
		VerticalAngleSpread.addActionListener(listener);
		HorizontalAngleSpread.addActionListener(listener);
		pchooser.setActionListener(listener);

		
		VerticalAngleSpin.addActionListener(listener);
		HorizontalAngleSpin.addActionListener(listener);


	

	}

	public void removeActionListener(ActionListener listener) {
	
		life.removeActionListener(listener);
		rate.removeActionListener(listener);
		
		VerticalAngle.removeActionListener(listener);
		HorizontalAngle.removeActionListener(listener);
		VerticalAngleSpread.removeActionListener(listener);
		HorizontalAngleSpread.removeActionListener(listener);
	
		pchooser.removeActionListener(listener);


		VerticalAngleSpin.removeActionListener(listener);
		HorizontalAngleSpin.removeActionListener(listener);
	}



	public void stateChanged(ChangeEvent e) {

		if(!signal)
			return;
		
		if (behaviour != null) {

			if (e.getSource() == number){
				Editor.setDirty(true);
				behaviour.setNumber((Integer) number.getValue());
			}

		}
	}

	public JButton getHorizontalAngleSpin() {
		return HorizontalAngleSpin;
	}

	public JButton getVerticalAngleSpin() {
		return VerticalAngleSpin;
	}

	


}
