package gui;

import gui.indicators.FloatIndicator;
import gui.indicators.Indicator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.softmed.jops.ParticleSystem;


public class ParticleSystemEditor extends SimpleEditor implements
		ActionListener,  gui.value.ChangeListener {
    
    private static final long serialVersionUID = 5712390015285372513L;
    
    float resolution = 100f;

	ParticleSystem ps;

	JCheckBox alive = new JCheckBox("Alive");

	JCheckBox noLimit = new JCheckBox("Time Limited");

	JCheckBox repeat = new JCheckBox("Repeat After Limit");

	JCheckBox renderable = new JCheckBox("Renderable");

	FloatIndicator lifeLimit = new FloatIndicator();

	InfoObjectEditor infoEditor = new InfoObjectEditor();

	private boolean signal = true;
	
	
	public ParticleSystemEditor() {

		setDimension( 250, 400);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		setDimension(alive, 250, 20);
		setDimension(noLimit, 250, 20);
		setDimension(repeat, 250, 20);
		setDimension(renderable, 250, 20);

		alive.addActionListener(this);
		noLimit.addActionListener(this);
		repeat.addActionListener(this);
		renderable.addActionListener(this);

		
		add(setToLeft(alive));
		add(setToLeft(renderable));
		add(setToLeft(noLimit));
		add(setToLeft(repeat));
		

		add(new JLabel("Life Limit"));
		lifeLimit.mySetArgs(100f, 0f, 20f, 0.01f, 0.01f, 2f, 5f, 10f);
		lifeLimit.setChangeListener(this);
		add(setToLeft(lifeLimit));
		
		add(infoEditor);
		
	}
	

	protected JPanel setToLeft(JComponent c){
		JPanel header2 = new JPanel();
		header2.setLayout(new BoxLayout(header2, BoxLayout.X_AXIS));
		header2.add(c);
		header2.add(Box.createHorizontalGlue());
		return header2;
	}

	@Override
	public void setObject(Object obj) {
		ps = (ParticleSystem) obj;

		if (ps == null)
			return;

		signal = false;
		
		alive.setSelected(ps.isAlive());
		noLimit.setSelected(ps.isLimited());
		renderable.setSelected(ps.isRenderable());
		repeat.setSelected(ps.isRepeat());

		lifeLimit.setValue(ps.getLimit());
		infoEditor.setObject(obj);
		
		signal = true;
	}

	public void actionPerformed(ActionEvent e) {
		if(!signal)
			return;
		
		Object source = e.getSource();
		if (ps == null)
			return;

		Editor.setDirty(true);
		
		if (source == alive) {
			ps.setAlive(alive.isSelected());
		} else if (source == noLimit) {
			ps.setLimited(noLimit.isSelected());
		} else if (source == renderable) {
			ps.setRenderable(renderable.isSelected());
		} else if (source == repeat) {
			ps.setRepeat(repeat.isSelected());
		}

	}



	public void changed(Indicator indicator) {

		
		if (ps != null){
			Editor.setDirty(true);
			ps.setLimit(lifeLimit.getValue());
		}

	}

}
