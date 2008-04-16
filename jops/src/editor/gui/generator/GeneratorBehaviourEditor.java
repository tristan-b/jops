package gui.generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.SimpleEditor;
import gui.value.ValueListGUI;

import javax.swing.JComponent;

import org.softmed.jops.Generator;
import org.softmed.jops.GeneratorBehaviour;
import org.softmed.jops.fileloading.converters.AngleRadiansConverter;
import org.softmed.jops.fileloading.converters.ColorConverter;


public class GeneratorBehaviourEditor extends SimpleEditor implements ActionListener {
    
    private static final long serialVersionUID = 5589095827923844785L;
    
    GeneratorBehaviourChooser detail = new GeneratorBehaviourChooser();

	Generator generator;

	GeneratorBehaviour generatorBehaviour;

	private ValueListGUI vgui;

	//private Container parent;

	public GeneratorBehaviourEditor() {
		setDimension(250, 600);
		detail.setActionListener(this);
	
		add(detail);

	}

	public void actionPerformed(ActionEvent e) {
	

		Object source = e.getSource();

 if (source == detail.getPchooser().getColor()) {
			destination.removeAll();
			vgui = new ValueListGUI("Color");
			vgui.setValueList(new ColorConverter(),generatorBehaviour.getColor());
			destination.add(vgui);
			// 0, 3000, 10, 100, 0, 100f
		} else if (source == detail.getVerticalAngle()) {
			destination.removeAll();
			vgui = new ValueListGUI("Vertical Angle");
			vgui.setValueList(new AngleRadiansConverter(),generatorBehaviour.getAngleV(), 1f, -180f, 180f,
					1f, 1f, 0f, 90f, 180f);
			destination.add(vgui);
		} else if (source == detail.getHorizontalAngle()) {
			destination.removeAll();
			vgui = new ValueListGUI("Horizontal Angle");
			vgui.setValueList(new AngleRadiansConverter(),generatorBehaviour.getAngleH(), 1f, -360f, 360f,
					1f, 1f, 0f, 90f, 180f);
			destination.add(vgui);
		} else if (source == detail.getVerticalAngleSpread()) {
			destination.removeAll();
			vgui = new ValueListGUI("Vertical Angle Spread");
			vgui.setValueList(new AngleRadiansConverter(),generatorBehaviour.getAngleSpreadV(), 1f, -360f,
					360f, 1f, 1f, 0f, 90f, 180f);
			destination.add(vgui);
		} else if (source == detail.getHorizontalAngleSpread()) {
			destination.removeAll();
			vgui = new ValueListGUI("Horizontal Angle Spread");
			vgui.setValueList(new AngleRadiansConverter(),generatorBehaviour.getAngleSpreadH(), 1f, -360f,
					360f, 1f, 1f, 0f, 90f, 180f);
			destination.add(vgui);
		} else if (source == detail.getLife()) {
			destination.removeAll();
			vgui = new ValueListGUI("Life");
			vgui.setValueList(null,generatorBehaviour.getLife(), 100f, 0f, 20f,
					0.01f, 0.01f, 0f, 5f, 10f);
			destination.add(vgui);
		} else if (source == detail.getRate()) {
			destination.removeAll();
			vgui = new ValueListGUI("Rate");
			vgui.setValueList(null,generatorBehaviour.getRate(), 1f, 0f, 20000f, 1f,
					10f, 0f, 5000f, 10000f);
			destination.add(vgui);
		} 
		
		else if (source == detail.getPchooser().getSizeButton()) {
			destination.removeAll();
			vgui = new ValueListGUI("Size");
			vgui.setValueList(null,generatorBehaviour.getSize(), 100f, 0f, 20f,
					0.01f, 0.01f, 0.1f, 5f, 10f);
			destination.add(vgui);
		} else if (source == detail.getPchooser().getAlfa()) {
			destination.removeAll();
			vgui = new ValueListGUI("Alpha");
			vgui.setValueList(new ColorConverter(),generatorBehaviour.getAlpha(), 1f, 0f, 255f, 1f,
					1f, 255f, 100f, 255f);
			destination.add(vgui);
		} else if (source == detail.getPchooser().getSpin()) {
			destination.removeAll();
			vgui = new ValueListGUI("Spin");
			vgui.setValueList(new AngleRadiansConverter(),generatorBehaviour.getSpin(), 1f, -3600f, 3600f,
					1f, 1f, 0f, 1800f, 3600f);
			destination.add(vgui);
		} else if (source == detail.getPchooser().getAngle()) {
			destination.removeAll();
			vgui = new ValueListGUI("Angle");
			vgui.setValueList(new AngleRadiansConverter(),generatorBehaviour.getAngle(), 1f, -360f, 360f,
					1f, 1f, 0f, 90f, 180f);
			destination.add(vgui);
		} else if (source == detail.getPchooser().getSpeed()) {
			destination.removeAll();
			vgui = new ValueListGUI("Speed");
			vgui.setValueList(null,generatorBehaviour.getSpeed(), 100f, -20f, 20f,
					0.01f, 0.01f, 0f, 5f, 10f);
			destination.add(vgui);
		} else if (source == detail.getPchooser().getPangleh()) {
			destination.removeAll();
			vgui = new ValueListGUI("P. Angle Horz");
			vgui.setValueList(new AngleRadiansConverter(),generatorBehaviour.getParticleAngleH(), 1f,
					-360f, 360f, 1f, 1f, 0f, 90f, 180f);
			destination.add(vgui);
		} else if (source == detail.getPchooser().getPanglev()) {
			destination.removeAll();
			vgui = new ValueListGUI("P. Angle Vert");
			vgui.setValueList(new AngleRadiansConverter(),generatorBehaviour.getParticleAngleV(), 1f,
					-360f, 360f, 1f, 1f, 0f, 90f, 180f);
			destination.add(vgui);
		} else if (source == detail.getPchooser().getPspinh()) {
			destination.removeAll();
			vgui = new ValueListGUI("P. Spin Horz");
			vgui.setValueList(new AngleRadiansConverter(),generatorBehaviour.getParticleSpinH(), 1f,
					-3600f, 3600f, 1f, 1f, 0f, 1800f, 3600f);
			destination.add(vgui);
		} else if (source == detail.getPchooser().getPspinv()) {
			destination.removeAll();
			vgui = new ValueListGUI("P. Spin Vert");
			vgui.setValueList(new AngleRadiansConverter(),generatorBehaviour.getParticleSpinV(), 1f,
					-3600f, 3600f, 1f, 1f, 0f, 1800f, 3600f);
			destination.add(vgui);
		} else if (source == detail.getPchooser().getWidthButton()) {
			destination.removeAll();
			vgui = new ValueListGUI("Width");
			vgui.setValueList(null,generatorBehaviour.getWidth(), 100f, 0f, 20f,
					0.01f, 0.01f, 10f, 2.5f, 5f);
			destination.add(vgui);
		} else if (source == detail.getPchooser().getHeightButton()) {
			destination.removeAll();
			vgui = new ValueListGUI("Height");
			vgui.setValueList(null,generatorBehaviour.getHeight(), 100f, 0f, 20f,
					0.01f, 0.01f, 10f, 2.5f, 5f);
			destination.add(vgui);
		}else if (source == detail.getPchooser().getTexwidth()) {
			destination.removeAll();
			vgui = new ValueListGUI("Texture Width");
			vgui.setValueList(null,generatorBehaviour.getTexWidth(), 100f, 0f, 5f,
					0.01f, 0.01f, 10f, 0.5f, 1f);
			destination.add(vgui);
		} else if (source == detail.getPchooser().getTexheight()) {
			destination.removeAll();
			vgui = new ValueListGUI("Texture Height");
			vgui.setValueList(null,generatorBehaviour.getTexHeight(), 100f, 0f, 5f,
					0.01f, 0.01f, 10f, 0.5f, 1f);
			destination.add(vgui);
		}else if (source == detail.getVerticalAngleSpin()) {
			destination.removeAll();
			vgui = new ValueListGUI("Vertical Spin");
			vgui.setValueList(new AngleRadiansConverter(),generatorBehaviour.getSpinV(), 1f, -3600f,
					3600f, 1f, 1f, 0f, 1800f, 3600f);
			destination.add(vgui);
		} else if (source == detail.getHorizontalAngleSpin()) {
			destination.removeAll();
			vgui = new ValueListGUI("Horizontal Spin");
			vgui.setValueList(new AngleRadiansConverter(),generatorBehaviour.getSpinH(), 1f, -3600f,
					3600f, 1f, 1f, 0f, 1800f, 3600f);
			destination.add(vgui);
		}else if (source == detail.getPchooser().getMass()) {
			destination.removeAll();
			vgui = new ValueListGUI("Mass");
			vgui.setValueList(null,generatorBehaviour.getMass(), 100f, 0f, 20f,
					0.01f, 0.01f, 0.1f, 5f, 10f);
			destination.add(vgui);
		}
	
		

		refreshDestination();

	}
/*
	public void showSpaceEditor() {
		if (parent == null)
			parent = getParent();

		parent.removeAll();

		SimpleEditor gui = Manager.getEditor(generator.getSpace(), destination);
		gui.setOld(this);

		parent.add(gui);

		parent.invalidate();
		parent.validate();
		parent.repaint();

		gui.invalidate();
		gui.validate();
		gui.repaint();
	}
//*/
	@Override
	public void setObject(Object obj) {

		generatorBehaviour =(GeneratorBehaviour) obj;
		detail.setObject(generatorBehaviour);
		
	}
	
    @Override
	public void setDestination(JComponent destination) {
		super.setDestination(destination);
		detail.setDestination(destination);


	}

}
