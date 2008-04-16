package gui.space;

import javax.swing.JComponent;

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

import gui.SimpleEditor;

public class Manager {

	public synchronized static SimpleEditor getEditor(GeneratorSpace space,
			JComponent destination) {
		BasicSpaceEditor ed = null;

		if (space instanceof PointGenerator)
			ed = new PointGUI(destination);
		else if (space instanceof BoxGenerator)
			ed = new BoxGUI(destination);
		else if (space instanceof CylinderGenerator)
			ed = new CylinderGUI(destination);
		else 
			
			
			
			if (space instanceof DCircleGenerator)
			ed = new DCircleGUI(destination);
		else if (space instanceof DLineGenerator)
			ed = new DLineGUI(destination);
		else if (space instanceof DPlaneGenerator)
			ed = new DPlaneGUI(destination);
		
		
		
		else
		if (space instanceof CircleGenerator)
			ed = new CircleGUI(destination);
		else if (space instanceof LineGenerator)
			ed = new LineGUI(destination);
		else if (space instanceof PlaneGenerator)
			ed = new PlaneGUI(destination);
		
		
		
		else if (space instanceof SphereGenerator)
			ed = new SphereGUI(destination);

		if (ed != null)
			ed.setObject(space);

		return ed;

	}

}
