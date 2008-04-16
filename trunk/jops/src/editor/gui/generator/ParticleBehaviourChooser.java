package gui.generator;

import gui.InfoObjectEditor;
import gui.SimpleEditor;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ParticleBehaviourChooser extends SimpleEditor  {
    
    private static final long serialVersionUID = -8215415619196908589L;
    
    private static final int BUTTON_HEIGHT = 20;
	
	Dimension dimension = new Dimension(80, BUTTON_HEIGHT);

	JButton color = new JButton("Color");

	JButton alfa = new JButton("Alpha");

	JButton mass = new JButton("Mass");

	JButton spin = new JButton("Spin");
	
	JButton angle = new JButton("Angle");

	JButton size = new JButton("Size");

	JButton elasticity = new JButton("Elasticity");

	
	JButton speed = new JButton("Speed");
	
	JButton pangleh = new JButton("P. Angle H");
	JButton panglev = new JButton("P. Angle V");
	
	JButton pspinh = new JButton("P. Spin H");
	JButton pspinv = new JButton("P. Spin V");
	
	JButton width = new JButton("Width");
	JButton height = new JButton("Height");
	
	JButton texwidth = new JButton("Tex Width");
	JButton texheight = new JButton("Tex Height");
	
	private BoxLayout grid;

	InfoObjectEditor infoEditor = new InfoObjectEditor();
	
	public ParticleBehaviourChooser() {

		configureSize(color);
		configureSize(alfa);
		configureSize(mass);
		configureSize(size);
		configureSize(elasticity);
		
		configureSize(spin);
		configureSize(speed);
		configureSize(angle);
		
		configureSize(pangleh);
		configureSize(panglev);
		
		configureSize(pspinh);
		configureSize(pspinv);

		configureSize(width);
		configureSize(height);

		grid = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(grid);

		JPanel middle = new JPanel();
		middle.setLayout(new BoxLayout(middle, BoxLayout.X_AXIS));
		middle.add(size);
		middle.add(color);
		middle.add(alfa);		
		middle.add(Box.createHorizontalGlue());
		add(middle);

		
		JPanel middle3 = new JPanel();
		middle3.setLayout(new BoxLayout(middle3, BoxLayout.X_AXIS));
		middle3.add(speed);
		middle3.add(mass);
		middle3.add(elasticity);
		middle3.add(Box.createHorizontalGlue());
		add(middle3);
		
		JPanel middle2 = new JPanel();
		middle2.setLayout(new BoxLayout(middle2, BoxLayout.X_AXIS));
		
		middle2.add(angle);
		middle2.add(spin);
		middle2.add(Box.createHorizontalGlue());
		add(middle2);

		
		
		
		JPanel middle4 = new JPanel();
		middle4.setLayout(new BoxLayout(middle4, BoxLayout.X_AXIS));
		configureSize(pangleh, 120, BUTTON_HEIGHT);
		configureSize(panglev, 120, BUTTON_HEIGHT);

		middle4.add(pangleh);
		middle4.add(pspinh);
		
		middle4.add(Box.createHorizontalGlue());
		add(middle4);
		
		
		JPanel middle5 = new JPanel();
		middle5.setLayout(new BoxLayout(middle5, BoxLayout.X_AXIS));
		configureSize(pspinh, 120, BUTTON_HEIGHT);
		configureSize(pspinv, 120, BUTTON_HEIGHT);
		//middle5.add(pspinh);
		middle5.add(panglev);
		middle5.add(pspinv);
		middle5.add(Box.createHorizontalGlue());
		add(middle5);
		

		
		JPanel middle6 = new JPanel();
		middle6.setLayout(new BoxLayout(middle6, BoxLayout.X_AXIS));
		configureSize(width, 120, BUTTON_HEIGHT);
		configureSize(height, 120, BUTTON_HEIGHT);
		middle6.add(width);
		middle6.add(texwidth);
		middle6.add(Box.createHorizontalGlue());
		add(middle6);
		
		JPanel middle7 = new JPanel();
		middle7.setLayout(new BoxLayout(middle7, BoxLayout.X_AXIS));
		configureSize(texwidth, 120, BUTTON_HEIGHT);
		configureSize(texheight, 120, BUTTON_HEIGHT);
		middle7.add(height);
		
		middle7.add(texheight);
		middle7.add(Box.createHorizontalGlue());
		add(middle7);		
		
		add(infoEditor);
		
		add(Box.createVerticalGlue());

	}

	

	@Override
	public void setObject(Object obj) {
		infoEditor.setObject(obj);
		
	}

	
	
	public void setActionListener(ActionListener listener) {

		color.addActionListener(listener);
		alfa.addActionListener(listener);
		mass.addActionListener(listener);
		spin.addActionListener(listener);
		angle.addActionListener(listener);
		size.addActionListener(listener);
		elasticity.addActionListener(listener);
		speed.addActionListener(listener);
		pangleh.addActionListener(listener);
		panglev.addActionListener(listener);
		pspinh.addActionListener(listener);
		pspinv.addActionListener(listener);
		width.addActionListener(listener);
		height.addActionListener(listener);
		texwidth.addActionListener(listener);
		texheight.addActionListener(listener);		


	}
	
	public void removeActionListener(ActionListener listener) {

		color.removeActionListener(listener);
		alfa.removeActionListener(listener);
		mass.removeActionListener(listener);
		spin.removeActionListener(listener);
		angle.removeActionListener(listener);
		size.removeActionListener(listener);
		elasticity.removeActionListener(listener);
		speed.removeActionListener(listener);
		pangleh.removeActionListener(listener);
		panglev.removeActionListener(listener);
		pspinh.removeActionListener(listener);
		pspinv.removeActionListener(listener);
		width.removeActionListener(listener);
		height.removeActionListener(listener);
		texwidth.removeActionListener(listener);
		texheight.removeActionListener(listener);	
		
	}
	
	public void configureSize(JComponent c, int x, int y) {
		c.setPreferredSize(new Dimension(x, y));
	}


	public void configureSize(JComponent c) {
		c.setPreferredSize(dimension);
	}

	public JButton getSpeed() {
		return speed;
	}
	
	
	public JButton getAlfa() {
		return alfa;
	}

	public JButton getElasticity() {
		return elasticity;
	}

	public JButton getColor() {
		return color;
	}

	public JButton getMass() {
		return mass;
	}

	

	public JButton getSpin() {
		return spin;
	}

	public JButton getSizeButton() {
		return size;
	}



	public JButton getAngle() {
		return angle;
	}



	public JButton getPangleh() {
		return pangleh;
	}



	public JButton getPanglev() {
		return panglev;
	}



	public JButton getPspinh() {
		return pspinh;
	}



	public JButton getPspinv() {
		return pspinv;
	}



	public JButton getHeightButton() {
		return height;
	}



	public JButton getWidthButton() {
		return width;
	}



	public JButton getTexheight() {
		return texheight;
	}



	public JButton getTexwidth() {
		return texwidth;
	}

	

}
