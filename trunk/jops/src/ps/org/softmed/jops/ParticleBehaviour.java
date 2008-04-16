/*
 * ParticleData.java
 *
 * Created on 27 de Fevereiro de 2006, 0:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops;

import java.util.List;

import org.softmed.jops.valuelists.ValueListc;
import org.softmed.jops.valuelists.ValueListf;


/**
 * 
 * @author eu
 */
public class ParticleBehaviour extends InfoObject{

	
	private List<Particle> particles;

	CompiledBehaviour cb = new CompiledBehaviour();

	private int resolution;

	protected ValueListf alpha = new ValueListf();

	protected ValueListc color = new ValueListc();

	protected ValueListf mass = new ValueListf();

	protected ValueListf spin = new ValueListf();

	protected ValueListf angle = new ValueListf();

	protected ValueListf size = new ValueListf();

	protected ValueListf elasticity = new ValueListf();

	protected ValueListf particleAngleV = new ValueListf();

	protected ValueListf particleAngleH = new ValueListf();

	protected ValueListf particleSpinV = new ValueListf();

	protected ValueListf particleSpinH = new ValueListf();

	protected ValueListf width = new ValueListf();

	protected ValueListf height = new ValueListf();

	protected ValueListf texWidth = new ValueListf();

	protected ValueListf texHeight = new ValueListf();

	protected ValueListf speed = new ValueListf();

	
	public void recompile() {
		rebuildIfNecessary();

		alpha.compileArray();
		color.compileArray();
		mass.compileArray();
		spin.compileArray();
		angle.compileArray();
		size.compileArray();
		elasticity.compileArray();
		particleAngleV.compileArray();
		particleAngleH.compileArray();
		particleSpinV.compileArray();
		particleSpinH.compileArray();
		width.compileArray();
		height.compileArray();
		speed.compileArray();
		texWidth.compileArray();
		texHeight.compileArray();

		cb.setBehaviour(this);
		cb.setParticles(particles);
	}
	
	
	public void updateParticleBehaviour(boolean killParticles) {
		cb.updateParticleBehaviour(killParticles);
	}
	
	public void rebuild() {
		recompile();
	}


	private void rebuildIfNecessary() {
		// to ensure compatibility with previous XML file formats

		if (cb == null)
			cb = new CompiledBehaviour();

		if (angle == null)
			angle = new ValueListf();

		if (speed == null)
			speed = new ValueListf();

		if (particleAngleV == null)
			particleAngleV = new ValueListf();

		if (particleAngleH == null)
			particleAngleH = new ValueListf();

		if (particleSpinV == null)
			particleSpinV = new ValueListf();

		if (particleSpinH == null)
			particleSpinH = new ValueListf();

		if (width == null) {
			width = new ValueListf();
			width.addValue(1.0f, 0f);
		}

		if (height == null) {
			height = new ValueListf();
			height.addValue(1.0f, 0f);
		}

		if (texWidth == null) {
			texWidth = new ValueListf();
			texWidth.addValue(1.0f, 0f);
		}

		if (texHeight == null) {
			texHeight = new ValueListf();
			texHeight.addValue(1.0f, 0f);
		}
		
		if (elasticity == null)
			elasticity = new ValueListf();

	}

	/** Creates a new instance of ParticleData */
	public ParticleBehaviour() {
		height.addValue(1.0f, 0f);
		width.addValue(1.0f, 0f);
		texHeight.addValue(1.0f, 0f);
		texWidth.addValue(1.0f, 0f);
	}

	public ValueListf getAlpha() {
		return alpha;
	}

	public ValueListc getColor() {
		return color;
	}

	public ValueListf getMass() {
		return mass;
	}

	public ValueListf getSpin() {
		return spin;
	}

	public ValueListf getSize() {
		return size;
	}

	public ValueListf getElasticity() {
		return elasticity;
	}

	public void setAlpha(ValueListf alpha) {
		this.alpha = alpha;
	}

	public void setColor(ValueListc color) {
		this.color = color;
	}

	public void setMass(ValueListf mass) {
		this.mass = mass;
	}

	public void setSpin(ValueListf spin) {
		this.spin = spin;
	}

	public void setSize(ValueListf size) {
		this.size = size;
	}

	public void setElasticity(ValueListf bounce) {
		this.elasticity = bounce;
	}

	public ValueListf getAngle() {
		return angle;
	}

	public void setAngle(ValueListf angle) {
		this.angle = angle;
	}

	public ValueListf getHeight() {
		return height;
	}

	public void setHeight(ValueListf height) {
		this.height = height;
	}

	public ValueListf getParticleAngleH() {
		return particleAngleH;
	}

	public void setParticleAngleH(ValueListf particleAngleH) {
		this.particleAngleH = particleAngleH;
	}

	public ValueListf getParticleAngleV() {
		return particleAngleV;
	}

	public void setParticleAngleV(ValueListf particleAngleV) {
		this.particleAngleV = particleAngleV;
	}

	public ValueListf getSpeed() {
		return speed;
	}

	public void setSpeed(ValueListf speed) {
		this.speed = speed;
	}

	public ValueListf getWidth() {
		return width;
	}

	public void setWidth(ValueListf width) {
		this.width = width;
	}

	public ValueListf getParticleSpinH() {
		return particleSpinH;
	}

	public void setParticleSpinH(ValueListf particleSpinH) {
		this.particleSpinH = particleSpinH;
	}

	public ValueListf getParticleSpinV() {
		return particleSpinV;
	}

	public void setParticleSpinV(ValueListf particleSpinV) {
		this.particleSpinV = particleSpinV;
	}

	public ValueListf getTexHeight() {
		return texHeight;
	}

	public void setTexHeight(ValueListf texHeight) {
		this.texHeight = texHeight;
	}

	public ValueListf getTexWidth() {
		return texWidth;
	}

	public void setTexWidth(ValueListf texWidth) {
		this.texWidth = texWidth;
	}

	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
		alpha.setResolution(resolution);
		color.setResolution(resolution);
		mass.setResolution(resolution);
		spin.setResolution(resolution);
		angle.setResolution(resolution);
		size.setResolution(resolution);
		elasticity.setResolution(resolution);
		particleAngleV.setResolution(resolution);
		particleAngleH.setResolution(resolution);
		particleSpinV.setResolution(resolution);
		particleSpinH.setResolution(resolution);
		width.setResolution(resolution);
		height.setResolution(resolution);
		speed.setResolution(resolution);
		texWidth.setResolution(resolution);
		texHeight.setResolution(resolution);

	}

	public void setParticles(List<Particle> particles) {
		this.particles = particles;
		if(cb == null)
		{
			cb = new CompiledBehaviour(particles);
			cb.setBehaviour(this);

		}
		else
		cb.setParticles(particles);
	}





	
}
