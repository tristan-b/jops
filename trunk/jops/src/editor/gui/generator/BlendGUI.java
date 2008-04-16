package gui.generator;

import gui.Editor;
import gui.SizeablePanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lwjgl.opengl.GL11;
import org.softmed.jops.ParticleRender;


public class BlendGUI extends SizeablePanel implements ActionListener {
    
    private static final long serialVersionUID = 8686574331058342932L;
    
    String[] modes = { "ONE", "ZERO", "DEST ALPHA", "DEST COLOR", "SRC ALPHA",
			"SRC COLOR", "ONE MINUS DEST ALPHA", "ONE MINUS DEST COLOR",
			"ONE MINUS SRC ALPHA", "ONE MINUS SRC COLOR", "SRC ALPHA SATURATE", };

	String[] set = { "1", "2", "3", "4", "5", "6", "7","8" };

	JComboBox first;

	JComboBox second;

	JComboBox presets;

	ParticleRender render;

	private boolean signal  = true;

	public BlendGUI() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		setPreferredSize(new Dimension(250, 100));
		
		first = new JComboBox(modes);
		second = new JComboBox(modes);
		presets = new JComboBox(set);
		
		setDimension(first, 170, 20);
		setDimension(second, 170, 20);
		setDimension(presets, 170, 20);

		System.out.println("" + GL11.GL_ONE);
		System.out.println("" + GL11.GL_ZERO);
		System.out.println("" + GL11.GL_DST_ALPHA);
		System.out.println("" + GL11.GL_DST_COLOR);
		System.out.println("" + GL11.GL_SRC_ALPHA);
		System.out.println("" + GL11.GL_SRC_COLOR);
		System.out.println("" + GL11.GL_ONE_MINUS_DST_ALPHA);
		System.out.println("" + GL11.GL_ONE_MINUS_DST_COLOR);
		System.out.println("" + GL11.GL_ONE_MINUS_SRC_ALPHA);
		System.out.println("" + GL11.GL_ONE_MINUS_SRC_COLOR);
		System.out.println("" + GL11.GL_SRC_ALPHA_SATURATE);

		first.addActionListener(this);
		second.addActionListener(this);
		presets.addActionListener(this);
		

		JPanel der3 = new JPanel();
		der3.setLayout(new BoxLayout(der3, BoxLayout.X_AXIS));
		JLabel label = new JLabel("Source Factor");
		setDimension(label,80, 20);
		der3.add(label);
		der3.add(first);
		der3.add(Box.createHorizontalGlue());
		add(der3);
		
		der3 = new JPanel();
		der3.setLayout(new BoxLayout(der3, BoxLayout.X_AXIS));
		label = new JLabel("Dest. Factor");
		setDimension(label,80, 20);
		der3.add(label);
		der3.add(second);
		der3.add(Box.createHorizontalGlue());
		add(der3);
		
		der3 = new JPanel();
		der3.setLayout(new BoxLayout(der3, BoxLayout.X_AXIS));
		label = new JLabel("Presets");
		setDimension(label,80, 20);
		der3.add(label);
		der3.add(presets);
		der3.add(Box.createHorizontalGlue());
		add(der3);
		
		//add(first);
		//add(second);
		//add(presets);

	}

	public void actionPerformed(ActionEvent e) {
		
		if(!signal)
			return;
		
		if (e.getSource() == first) {
			Editor.setDirty(true);
			int id = glId(first.getSelectedIndex());
			render.setSourceFactor(id);
		} else if (e.getSource() == second) {
			Editor.setDirty(true);
			int id = glId(second.getSelectedIndex());
			render.setDestinationFactor(id);
		} else {
			applyPresets();
			Editor.setDirty(true);
			reload();
		}

	}

	protected void applyGenMode(int one, int two) {
		render.setSourceFactor(one);
		render.setDestinationFactor(two);
	}

	protected void applyPresets() {
		int index = presets.getSelectedIndex();
		switch (index) {

		case 0:
			applyGenMode(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
			break;

		case 1:
			applyGenMode(GL11.GL_SRC_COLOR, GL11.GL_DST_ALPHA);
			break;

			
		case 2:
			applyGenMode(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
			break;

		case 3:
			applyGenMode(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_COLOR);
			break;

		case 4:
			applyGenMode(GL11.GL_ONE_MINUS_SRC_COLOR, GL11.GL_DST_ALPHA);
			break;

		case 5:
			applyGenMode(GL11.GL_ONE_MINUS_SRC_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
			break;

		case 6:
			applyGenMode(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
			break;

		case 7:
			applyGenMode(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			break;
			
		}

	}

	private int glId(int selectedIndex) {

		switch (selectedIndex) {
		case 0:
			return GL11.GL_ONE;
		case 1:
			return GL11.GL_ZERO;
		case 2:
			return GL11.GL_DST_ALPHA;
		case 3:
			return GL11.GL_DST_COLOR;
		case 4:
			return GL11.GL_SRC_ALPHA;
		case 5:
			return GL11.GL_SRC_COLOR;
		case 6:
			return GL11.GL_ONE_MINUS_DST_ALPHA;
		case 7:
			return GL11.GL_ONE_MINUS_DST_COLOR;
		case 8:
			return GL11.GL_ONE_MINUS_SRC_ALPHA;
		case 9:
			return GL11.GL_ONE_MINUS_SRC_COLOR;
		case 10:
			return GL11.GL_SRC_ALPHA_SATURATE;
		default:
			return 0;
		}
	}

	private int index(int glMode) {

		switch (glMode) {
		case GL11.GL_ONE:
			return 0;
		case GL11.GL_ZERO:
			return 1;
		case GL11.GL_DST_ALPHA:
			return 2;
		case GL11.GL_DST_COLOR:
			return 3;
		case GL11.GL_SRC_ALPHA:
			return 4;
		case GL11.GL_SRC_COLOR:
			return 5;
		case GL11.GL_ONE_MINUS_DST_ALPHA:
			return 6;
		case GL11.GL_ONE_MINUS_DST_COLOR:
			return 7;
		case GL11.GL_ONE_MINUS_SRC_ALPHA:
			return 8;
		case GL11.GL_ONE_MINUS_SRC_COLOR:
			return 9;
		case GL11.GL_SRC_ALPHA_SATURATE:
			return 10;
		default:
			return 0;
		}
	}

	public ParticleRender getRender() {
		return render;
	}

	public void setRender(ParticleRender generator) {
		this.render = generator;
		reload();
	}

	protected void reload() {
		if(render == null)
			return;
		signal = false;
		
		int index = index(render.getSourceFactor());
		first.setSelectedIndex(index);

		index = index(render.getDestinationFactor());
		second.setSelectedIndex(index);
		
		signal = true;
	}

}
