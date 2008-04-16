package gui.modifier;

import gui.Editor;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.softmed.jops.modifiers.AirFriction;
import org.softmed.jops.modifiers.GenericForce;
import org.softmed.jops.modifiers.Modifier;
import org.softmed.jops.modifiers.PointMass;



public class ModifierCreator extends JDialog implements ActionListener {
    
    private static final long serialVersionUID = 2651897435020964539L;
    
    JCheckBox force = new JCheckBox("Force");

	JCheckBox friction = new JCheckBox("Air Friction");

	JCheckBox pointMass = new JCheckBox("Point Mass");

	
	Container container;

	JButton ok = new JButton("OK");

	JButton cancel = new JButton("CANCEL");

	//private ParticleSystem ps;

	//private DetailViewer viewer;

	//private Generator generator;

	private ModifierCreatedListener listener;



	public ModifierCreator(ModifierCreatedListener listener) {

		setTitle("Create Modifier");
		setModal(true);
		
		setEasyDimension(150,130);
		
		this.listener = listener;

		container = getContentPane();

		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		force.setSelected(true);
	/*	container.add(force);
		container.add(friction);
		container.add(pointMass);
		container.add(Box.createVerticalStrut(10));
		container.add(Box.createVerticalStrut(30));
	//*/	
		//container.add(ok);
		//container.add(cancel);
		JPanel one = new JPanel();
		one.setLayout(new BoxLayout(one, BoxLayout.X_AXIS));
		//one.add(Box.createHorizontalGlue());
		one.add(force);
		one.add(Box.createHorizontalGlue());
		
		JPanel two = new JPanel();
		two.setLayout(new BoxLayout(two, BoxLayout.X_AXIS));
		//two.add(Box.createHorizontalGlue());
		two.add(friction);
		two.add(Box.createHorizontalGlue());
		
		JPanel three = new JPanel();
		three.setLayout(new BoxLayout(three, BoxLayout.X_AXIS));
		//three.add(Box.createHorizontalGlue());
		three.add(pointMass);
		three.add(Box.createHorizontalGlue());
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		bottom.add(Box.createHorizontalGlue());
		bottom.add(ok);
		bottom.add(cancel);
		bottom.add(Box.createHorizontalGlue());
		
		container.add(one);
		container.add(two);
		container.add(three);
		container.add(bottom);
		container.add(Box.createVerticalGlue());
		
		ok.addActionListener(this);
		cancel.addActionListener(this);

		force.addActionListener(this);
		friction.addActionListener(this);
		pointMass.addActionListener(this);

		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JCheckBox) {
			JCheckBox b = (JCheckBox) source;
			boolean s = b.isSelected();
			disconnect();
			b.setSelected(s);

			ok.setEnabled(s);

		} else if (source instanceof JButton) {
			if (source == cancel) {
				this.setVisible(false);
				this.dispose();
			} else if (source == ok) {
				this.setVisible(false);
				this.dispose();
				
				listener.modifierCreated(getSpace());
				Editor.DIRTY = true;
			}

			listener = null;

		}

	}

	protected void disconnect() {
		force.setSelected(false);
		friction.setSelected(false);
		pointMass.setSelected(false);
	}

	protected Modifier getSpace() {
		if (force.isSelected())
			return new GenericForce();

		if (friction.isSelected())
			return new AirFriction();

		if (pointMass.isSelected())
			return new PointMass();

		

		return null;

	}
	

	public void setEasyDimension(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setSize(width, height);
	}

	public void setEasyDimension(JComponent comp, int width, int height) {

		comp.setPreferredSize(new Dimension(width, height));
		comp.setMinimumSize(new Dimension(width, height));
		comp.setMaximumSize(new Dimension(width, height));
		comp.setSize(width, height);
	}

}
