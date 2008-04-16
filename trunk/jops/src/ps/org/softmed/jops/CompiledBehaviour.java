/*
 * CompiledBehaviour.java
 *
 * Created on 12 de Julho de 2006, 11:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops;

import java.util.ArrayList;
import java.util.List;

import org.softmed.jops.operators.AlphaOperator;
import org.softmed.jops.operators.AngleOperator;
import org.softmed.jops.operators.BounceOperator;
import org.softmed.jops.operators.ColorOperator;
import org.softmed.jops.operators.HeightOperator;
import org.softmed.jops.operators.MassOperator;
import org.softmed.jops.operators.PAngleHOperator;
import org.softmed.jops.operators.PAngleVOperator;
import org.softmed.jops.operators.PSpinHOperator;
import org.softmed.jops.operators.PSpinVOperator;
import org.softmed.jops.operators.ParticleOperator;
import org.softmed.jops.operators.SizeOperator;
import org.softmed.jops.operators.SpeedOperator;
import org.softmed.jops.operators.SpinOperator;
import org.softmed.jops.operators.TexHeightOperator;
import org.softmed.jops.operators.TexWidthOperator;
import org.softmed.jops.operators.WidthOperator;



/**
 * 
 * @author gui
 */
public class CompiledBehaviour {
	private List<Particle> particles;
	private List<ParticleOperator> operators = new ArrayList<ParticleOperator>();

	private ParticleOperator operator;

	private Particle part;

	/** Creates a new instance of CompiledBehaviour */
	public CompiledBehaviour(List<Particle> particles) {
		this.particles = particles;
	}

	public CompiledBehaviour() {
		// TODO Auto-generated constructor stub
	}

	public void updateParticleBehaviour(boolean killParticles) {
		if (particles != null) {
			if (killParticles) {
				for (int i = 0; i < operators.size(); i++) {
					operator = operators.get(i);
					for (int j = 0; j < particles.size(); j++) {
						part = particles.get(j);
						if (part.life >= 0f)
							operator.myupdate(part);
					}

				}
			} else {
				for (int i = 0; i < operators.size(); i++) {
					operator = operators.get(i);
					for (int j = 0; j < particles.size(); j++) {
						part = particles.get(j);
						operator.myupdate(part);
					}

				}
			}
		}

	}

	private void compileBehaviour(ParticleBehaviour behaviour) {
		if (operators != null)
			getOperators().clear();
		else
			operators = new ArrayList<ParticleOperator>();
		;

		if (behaviour.getAlpha().isActive())
			getOperators().add(new AlphaOperator(behaviour));

		if (behaviour.getElasticity() != null
				&& behaviour.getElasticity().isActive())
			getOperators().add(new BounceOperator(behaviour));

		if (behaviour.getColor().isActive())
			getOperators().add(new ColorOperator(behaviour));

		if (behaviour.getMass().isActive())
			getOperators().add(new MassOperator(behaviour));

		if (behaviour.getSize().isActive())
			getOperators().add(new SizeOperator(behaviour));

		if (behaviour.getSpin().isActive())
			getOperators().add(new SpinOperator(behaviour));

		if (behaviour.getAngle().isActive())
			getOperators().add(new AngleOperator(behaviour));

		if (behaviour.getSpeed().isActive())
			getOperators().add(new SpeedOperator(behaviour));

		if (behaviour.particleAngleH.isActive())
			getOperators().add(new PAngleHOperator(behaviour));

		if (behaviour.particleAngleV.isActive())
			getOperators().add(new PAngleVOperator(behaviour));

		if (behaviour.particleSpinV.isActive())
			getOperators().add(new PSpinVOperator(behaviour));

		if (behaviour.particleSpinH.isActive())
			getOperators().add(new PSpinHOperator(behaviour));

		if (behaviour.width.isActive())
			getOperators().add(new WidthOperator(behaviour));

		if (behaviour.height.isActive())
			getOperators().add(new HeightOperator(behaviour));

		if (behaviour.texWidth.isActive())
			getOperators().add(new TexWidthOperator(behaviour));

		if (behaviour.texHeight.isActive())
			getOperators().add(new TexHeightOperator(behaviour));

	}

	public List<Particle> getParticles() {
		return particles;
	}

	public List<ParticleOperator> getOperators() {
		return operators;
	}

	public void setBehaviour(ParticleBehaviour behaviour) {
		if (behaviour == null) {
			particles = null;
			if (operators != null)
				operators.clear();
			operators = null;
		} else {
			compileBehaviour(behaviour);
		}
	}

	protected void setParticles(List<Particle> particles) {
		this.particles = particles;
	}

}
