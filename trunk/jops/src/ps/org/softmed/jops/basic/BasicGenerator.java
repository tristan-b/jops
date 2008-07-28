/*
 * BasicGenerator.java
 *
 * Created on 12 de Julho de 2006, 12:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.basic;

import org.openmali.FastMath;
import org.softmed.jops.Generator;
import org.softmed.jops.ParticleBehaviour;
import org.softmed.jops.valuelists.ValueListf;

/**
 * 
 * @author gui
 */
public class BasicGenerator extends Generator {

	/** Creates a new instance of BasicGenerator */
	public BasicGenerator() {

		// WorldForce gravity = new WorldForce();
		// forces.add(gravity);
		// gravity.addValue(new Vector3f(0.0f,2.0f,0.0f),0.0f);

		// BoxGenerator b = (BoxGenerator) space;
		// b.getBox().setDepth(0.0f);
		// b.getBox().setHeight(0.0f);
		// b.getBox().setWidth(0.0f);

		pb = new ParticleBehaviour();
		/*
		 * pb.setColor(new ValueListc()); pb.getColor().addValue(new
		 * Color(255,0,0),0.0f); pb.getColor().addValue(new
		 * Color(0,0,255),1.0f); pb.getColor().setRepeat(true); //
		 */
		pb.setSize(new ValueListf());
		pb.getSize().addValue(0.1f, 1.0f);
		pb.getSize().addValue(1.8f, 0.0f);
		// pb.getSize().setRepeat(true);

		// pb.setAlpha(new ValueListf());
		// pb.getAlpha().addValue(255f,0.0f);
		// pb.getAlpha().addValue(0f,5.0f);
		// pb.getAlpha().setRepeat(true);

		// compileBehaviour();
		// */

		// blendMode1 = GL11.GL_SRC_ALPHA;
		// blendMode2 = GL11.GL_DST_ALPHA;
		// textureName="media/images/particle.bmp";
		getGb().setNumber(100);
		getGb().getSpeed().addValue(1f, 0.0f);
		// getGb().getSpeed().addValue(0.1f,2.0f);
		// getGb().getSpeed().addValue(3.0f,3.0f);
		// getGb().getSpeed().addValue(1.0f,4.0f);
		// getGb().getPosition().addValue(new Vector3f(0.0f,0.0f,0.0f),0.0f);
		// getGb().getPosition().addValue(new Vector3f(0.0f,0.0f,1.0f),1.0f);
		// getGb().getPosition().addValue(new Vector3f(1.0f,0.0f,0.0f),2.0f);
		// getGb().getPosition().addValue(new Vector3f(0.0f,0.0f,0.0f),3.0f);
		// getGb().getPosition().setRepeat(true);

		// getGb().getColor().addValue(new Color(255,0,0),0.0f);
		// getGb().getColor().addValue(new Color(0,255,0),1.0f);
		// getGb().getColor().addValue(new Color(255,0,0),2.0f);
		// getGb().getColor().setRepeat(true);
		getGb().getLife().addValue(1.0f, 0.0f);
		getGb().getRate().addValue(50.0f, 0.0f);
		getGb().getAlpha().addValue(1.0f, 0.0f);
		// getGb().getAlpha().addValue(150.0f,1.0f);
		// getGb().getAlpha().addValue(50.0f,2.0f);
		// getGb().getAlpha().addValue(150.0f,3.0f);
		// getGb().getAlpha().addValue(255.0f,4.0f);
		// getGb().getAlpha().setRepeat(true);

		getGb().getAngleH().addValue(0.0f, 0.0f);
		getGb().getAngleV().addValue(FastMath.PI * 0.5f, 0.0f);
		// getGb().getAngleV().addValue(-90.0f,1.0f);
		getGb().getAngleSpreadH().addValue(FastMath.PI / 7f, 0.0f);
		getGb().getAngleSpreadV().addValue(FastMath.PI / 7f, 0.0f);
		// getGb().getSize().addValue(0.2f,0.0f);
		getGb().getSize().addValue(0.5f, 0.0f);
		// getGb().getSize().addValue(0.1f,4.0f);
		// getGb().getSize().setRepeat(true);
		// getGb().getSpin().addValue(0.0f,0.0f);
		// getGb().getSpin().setRepeat(true);

		recompileAll();
	}

}
