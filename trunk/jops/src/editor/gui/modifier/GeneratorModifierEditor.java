package gui.modifier;

import gui.CustomListCellRenderer;
import gui.Editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.softmed.jops.Generator;
import org.softmed.jops.ParticleSystem;
import org.softmed.jops.modifiers.Modifier;


public class GeneratorModifierEditor extends JDialog implements ActionListener {
    
    private static final long serialVersionUID = 2645959777535334679L;
    
    ParticleSystem ps;

	Generator gen;

	JButton ok = new JButton("OK");

	JButton cancel = new JButton("CANCEL");

	JButton moveRight = new JButton(">>");

	JButton moveLeft = new JButton("<<");

	JPanel center = new JPanel();

	JPanel top = new JPanel();

	JPanel bottom = new JPanel();

	JList left = new JList();

	JList right = new JList();

	DefaultListModel leftModel = new DefaultListModel();

	DefaultListModel rightModel = new DefaultListModel();

	JScrollPane leftPane;

	JScrollPane rightPane;

	public GeneratorModifierEditor(ParticleSystem ps, Generator gen) {
		//super("Choose Modifiers for This Generator");
		setTitle("Choose Modifiers for This Generator");
		setModal(true);
		
		// We will do this on a reflection way, since it is not possible in Java 1.5 and should be done in Java 1.6!
        //setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        try {
            Object param = Class.forName("ModalExclusionType").getField("APPLICATION_EXCLUDE");
            this.getClass().getMethod("setModalExclusionType", Class.forName("ModalExclusionType")).invoke(null, param);
        } catch (Throwable e) {
            //e.printStackTrace();
        }
		
		this.ps = ps;
		this.gen = gen;
		setLayout(new BorderLayout());

		add(bottom, BorderLayout.SOUTH);
		add(center, BorderLayout.CENTER);
		add(top, BorderLayout.NORTH);

		setEasyDimension(500, 420);

		setEasyDimension(bottom, 500, 50);
		setEasyDimension(center, 500, 350);
		setEasyDimension(top, 500, 20);

		setEasyDimension(ok, 80, 30);
		setEasyDimension(cancel, 80, 30);
		
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		top.add(new JLabel("Generator"));
		top.add(Box.createHorizontalGlue());
		top.add(new JLabel("ParticleSystem"));

		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		bottom.add(Box.createHorizontalGlue());
		bottom.add(ok);
		bottom.add(cancel);
		bottom.add(Box.createHorizontalGlue());

		ok.addActionListener(this);
		cancel.addActionListener(this);

		// list part...
		left.setModel(leftModel);
		right.setModel(rightModel);

		left.setCellRenderer(new CustomListCellRenderer());
		right.setCellRenderer(new CustomListCellRenderer());

		leftPane = new JScrollPane(left);
		rightPane = new JScrollPane(right);

		setEasyDimension(leftPane, 220, 350);
		setEasyDimension(rightPane, 220, 350);

		JPanel move = new JPanel();
		move.setLayout(new BoxLayout(move, BoxLayout.Y_AXIS));
		move.add(Box.createVerticalGlue());
		move.add(moveLeft);
		move.add(moveRight);
		move.add(Box.createVerticalGlue());
		setEasyDimension(move, 50, 350);

		moveRight.addActionListener(this);
		moveLeft.addActionListener(this);

		center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
		center.add(leftPane);
		center.add(move);
		center.add(rightPane);

		List<Modifier> psM = ps.getModifiers();
		List<Modifier> genM = gen.getModifiers();

		for (Modifier modifier : genM) {
			leftModel.addElement(modifier);
		}

		for (Modifier modifier : psM) {
			if (!leftModel.contains(modifier))
				rightModel.addElement(modifier);
		}

		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == ok) {
			Object[] apply = leftModel.toArray();
			gen.getModifiers().clear();
			for (Object object : apply) {
				gen.getModifiers().add((Modifier) object);
			}
			Editor.DIRTY = true;
			closeThis();
		} else if (source == cancel) {

			closeThis();
		} else if (source == moveRight) {
			Object[] values = left.getSelectedValues();
			for (Object object : values) {
				leftModel.removeElement(object);
			}
			for (Object object : values) {
				rightModel.addElement(object);
			}

		} else if (source == moveLeft) {
			Object[] values = right.getSelectedValues();
			for (Object object : values) {
				rightModel.removeElement(object);
			}
			for (Object object : values) {
				leftModel.addElement(object);
			}
		}

	}

	private void closeThis() {
		setVisible(false);
		ps = null;
		gen = null;
		dispose();
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
