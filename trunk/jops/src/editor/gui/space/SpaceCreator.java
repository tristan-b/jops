package gui.space;

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

import org.softmed.jops.space.BoxGenerator;
import org.softmed.jops.space.CylinderGenerator;
import org.softmed.jops.space.GeneratorSpace;
import org.softmed.jops.space.PointGenerator;
import org.softmed.jops.space.SphereGenerator;
import org.softmed.jops.space.discreet.DCircleGenerator;
import org.softmed.jops.space.discreet.DLineGenerator;
import org.softmed.jops.space.discreet.DPlaneGenerator;
import org.softmed.jops.space.simple.CircleGenerator;
import org.softmed.jops.space.simple.LineGenerator;
import org.softmed.jops.space.simple.PlaneGenerator;


public class SpaceCreator extends JDialog implements ActionListener {
    
    private static final long serialVersionUID = -7146688017138002751L;
    
    JCheckBox point = new JCheckBox("Point");

	JCheckBox box = new JCheckBox("Box");

	JCheckBox cylinder = new JCheckBox("Cylinder");

	JCheckBox sphere = new JCheckBox("Sphere");

	JCheckBox circle = new JCheckBox("Circle");

	JCheckBox line = new JCheckBox("Line");

	JCheckBox plane = new JCheckBox("Plane");

	JCheckBox dcircle = new JCheckBox("Points in Circle");

	JCheckBox dline = new JCheckBox("Points in Line");

	JCheckBox dplane = new JCheckBox("Lines and Columns in Plane");

	Container container;

	JButton ok = new JButton("OK");

	JButton cancel = new JButton("CANCEL");

	//private ParticleSystem ps;

	//private DetailViewer viewer;

	//private Generator generator;

	private SpaceSelectionListener listener;



	public SpaceCreator(SpaceSelectionListener listener) {

		setTitle("Create Space");
		setModal(true);
		setEasyDimension(300, 360);
		
		this.listener = listener;

		container = getContentPane();

		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		point.setSelected(true);
		container.add(point);
		container.add(box);
		container.add(cylinder);
		container.add(sphere);

		container.add(Box.createVerticalStrut(10));
		container.add(circle);
		container.add(line);
		container.add(plane);

		container.add(Box.createVerticalStrut(10));
		container.add(dcircle);
		container.add(dline);
		container.add(dplane);

		container.add(Box.createVerticalStrut(30));
		//container.add(ok);
		//container.add(cancel);

		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		bottom.add(Box.createHorizontalGlue());
		bottom.add(ok);
		bottom.add(cancel);
		bottom.add(Box.createHorizontalGlue());
		
		container.add(bottom);
		
		
		ok.addActionListener(this);
		cancel.addActionListener(this);

		point.addActionListener(this);
		box.addActionListener(this);
		cylinder.addActionListener(this);
		sphere.addActionListener(this);
		circle.addActionListener(this);
		line.addActionListener(this);
		point.addActionListener(this);
		plane.addActionListener(this);
		dcircle.addActionListener(this);
		dline.addActionListener(this);
		dplane.addActionListener(this);

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
				
				listener.selected(getSpace());
				Editor.setDirty(true);

			}

			listener = null;

		}

	}

	protected void disconnect() {
		point.setSelected(false);
		box.setSelected(false);
		cylinder.setSelected(false);
		sphere.setSelected(false);

		circle.setSelected(false);
		line.setSelected(false);
		plane.setSelected(false);

		dcircle.setSelected(false);
		dline.setSelected(false);
		dplane.setSelected(false);
	}

	protected GeneratorSpace getSpace() {
		if (point.isSelected())
			return new PointGenerator();

		if (box.isSelected())
			return new BoxGenerator();

		if (cylinder.isSelected())
			return new CylinderGenerator();

		if (sphere.isSelected())
			return new SphereGenerator();

		if (circle.isSelected())
			return new CircleGenerator();

		if (line.isSelected())
			return new LineGenerator();

		if (plane.isSelected())
			return new PlaneGenerator();

		if (dcircle.isSelected())
			return new DCircleGenerator();

		if (dline.isSelected())
			return new DLineGenerator();

		if (dplane.isSelected())
			return new DPlaneGenerator();

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
