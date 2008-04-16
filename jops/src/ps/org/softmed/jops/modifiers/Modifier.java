package org.softmed.jops.modifiers;

import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.softmed.jops.InfoObject;
import org.softmed.jops.Particle;


public abstract class Modifier extends InfoObject{

	public abstract void update(Point3f position, Matrix4f rotation, float life, float dt);

	public abstract void modify(Particle part);

	public abstract void setResolution(int resolution);

	public abstract void recompile();
	
}

