/*
 * generator.java
 *
 * Created on 11 de Julho de 2006, 21:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops;

import java.util.ArrayList;
import java.util.List;

import org.openmali.vecmath2.Matrix3f;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.softmed.jops.modifiers.Modifier;
import org.softmed.jops.space.GeneratorSpace;
import org.softmed.jops.space.PointGenerator;

/**
 * 
 * @author gui
 */
public class Generator extends InfoObject {

	protected boolean alive = true;

	protected boolean regenerateParticles = true;

	protected boolean killParticles = true;

	protected boolean absoluteParticleAngle = false;

	float age = 0.0f;

	protected List<Particle> particles = new ArrayList<Particle>();

	// here are the 4 possible components for a generator
	protected ParticleBehaviour pb = new ParticleBehaviour();

	private GeneratorBehaviour gb = new GeneratorBehaviour();

	protected GeneratorSpace space = new PointGenerator();

	protected SpaceAnimator animator = new SpaceAnimator();

	protected ParticleRender render = new ParticleRender();

	protected PositionAnimator positionAnimator = new PositionAnimator();

	protected List<Modifier> modifiers = new ArrayList<Modifier>();

	// temp variable
	protected Particle temp = null;

	private Modifier modifier;

	// private Point3f ctf;

	private Point3f position = new Point3f();

	// private Particle tp;

	private float totalGenerated;

	private int resolution;

	private float betweenGenerateCycles;

	// protected Vector3f translation;

	// protected Matrix4f rotation;

	// private float betweenGenerateCycles = 0f;

	// variables for JIXB loading, saving - object references
	int particleBehaviourIndex;
	int generatorBehaviourIndex;
	int spaceIndex;
	int spaceAnimatorIndex;
	int renderIndex;
	int positionAnimatorIndex;
	List<Integer> modifierIndexes;
	// String modifierIndexes;

	private Matrix3f tempRotation = new Matrix3f();;

	public Generator getCopy() {
		Generator gen = (Generator) super.clone();
		gen.setAbsoluteParticleAngle(absoluteParticleAngle);
		gen.setAlive(alive);
		gen.setRegenerateParticles(regenerateParticles);
		gen.setResolution(resolution);

		gen.setPositionAnimator(positionAnimator);
		gen.setAnimator(animator);
		gen.setGb(gb);
		gen.setPb(pb);
		gen.setRender(render);
		gen.setSpace(space);
		gen.modifiers.addAll(modifiers);

		return gen;
	}

	/** Creates a new instance of generator */
	public Generator() {
		pb.setParticles(particles);
	}

	public void recompileAll() {
		if (space != null)
			space.recompile();

		if (gb != null)
			gb.recompile();

		if (pb != null) {
			pb.setParticles(particles);
			pb.recompile();
		}
		if (animator != null)
			animator.recompile();

		if (positionAnimator != null)
			positionAnimator.recompile();

		if (modifiers == null)
			modifiers = new ArrayList<Modifier>();

		if (position == null)
			position = new Point3f();

		for (Modifier modifier : modifiers) {
			modifier.recompile();
		}
	}

	public void rebuildParticles() {
		particles = new ArrayList<Particle>();
		if (pb != null)
			pb.setParticles(particles);
	}

	public void update(Point3f position2, Matrix4f rotation, float systemAge,
			float timelapse) {
		if (!alive)
			return;
		age = systemAge;

		generate(position2, rotation, timelapse);

		if (pb != null) {
			pb.setParticles(particles);
			pb.updateParticleBehaviour(killParticles);
		}
		applyModifiers();
		calculateAllParticles(timelapse);
		// boundaries
		// killzones

	}

	protected void applyModifiers() {
		for (int m = 0; m < modifiers.size(); m++) {
			modifier = modifiers.get(m);
			for (int i = 0; i < particles.size(); i++) {
				modifier.modify(particles.get(i));
			}
		}

	}

	/*
	 * protected void calculateForces(float dt) { if (forces == null
	 * ||forces.size() == 0) return;
	 * 
	 * tforce.scale(0.0f);
	 * 
	 * for (int i = 0; i < forces.size(); i++) { tw = forces.get(i); ctf =
	 * tw.getValueAt(age); tforce.add(tforce, ctf, tforce); }
	 * 
	 * tforce.scale(dt);
	 * 
	 * for (int i = 0; i < particles.size(); i++) { tp = particles.get(i);
	 * tp.direction.x += tforce.x; tp.direction.y += tforce.y; tp.direction.z +=
	 * tforce.z; } } //
	 */
	protected void calculateAllParticles(float dt) {
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).calculate(dt);
		}
	}

	protected void generate(Point3f position2, Matrix4f rotation, float dt) {

		// if(dt>0.2f)
		// System.out.println("dt->"+dt);
		if (gb == null)
			return;

		position.set(0f, 0f, 0f);
		if (animator != null)
			animator.setInitialPosition(position);
		// position.add(position2);
		if (rotation != null) {
			rotation.transform(position);

			rotation.getRotationScale(tempRotation);
		}

		float x = gb.getCurrentRate();

		gb.setRotation(rotation);
		x *= dt;
		// x*=betweenGenerateCycles;
		totalGenerated += x;
		betweenGenerateCycles += dt;
		if (totalGenerated < 1f) {
			// System.out.println("totalGenerated->"+totalGenerated);

			return;
		}
		// float count = 0.0f;
		int frameCount = (int) totalGenerated;
		int recycledCount = 0;
		float rest = totalGenerated - frameCount;
		rest *= dt;

		// betweenGenerateCycles /= frameCount;
		// float particleDiference = dt / frameCount;
		// recycles dead particles
		if (killParticles) {
			if (regenerateParticles && recycledCount < frameCount) {
				int count = -1;
				for (int i = 0; i < particles.size(); i++) {
					temp = particles.get(i);
					if (temp.life < 0.0f) {
						count++;
						if (space != null)
							space.generate(temp);

						if (animator != null)
							animator.setInitialPosition(temp.position);

						if (positionAnimator != null)
							positionAnimator.setInitialPosition(temp.position);

						// Vector3f.add(temp.position, position, temp.position);
						// Vector3f.add(temp.position, psPosition,
						// temp.position);

						temp.position.add(position);
						temp.position.add(position2);

						if (rotation != null)
							tempRotation.transform(temp.position);

						if (space != null)
							gb.setInitialState(temp, 0.0f);

						// temp.calculate(-particleDiference*recycledCount);
						// temp.calculate(-rest);
						// temp.life-=rest;

						recycledCount += 1;
						if (recycledCount >= frameCount)
							break;
					}

				}

			}
		}
		// generates new particles
		int max = getGb().getNumber();
		int size = particles.size();

		if (size < max) {
			int count = -1;
			for (int i = (int) recycledCount; i < frameCount; i++) {
				if (particles.size() >= max)
					break;

				count++;
				temp = new Particle();

				if (space != null)
					space.generate(temp);

				if (animator != null)
					animator.setInitialPosition(temp.position);

				if (positionAnimator != null)
					positionAnimator.setInitialPosition(temp.position);

				// Vector3f.add(temp.position, position, temp.position);
				temp.position.add(position);
				temp.position.add(position2);
				// Vector3f.add(temp.position, psPosition, temp.position);

				if (rotation != null)
					tempRotation.transform(temp.position);

				if (gb != null)
					gb.setInitialState(temp, 0.0f);

				// temp.calculate(-particleDiference*i);
				// temp.calculate(-rest);
				// temp.calculate(-rest);

				particles.add(temp);
			}

		}
		totalGenerated -= frameCount;
		betweenGenerateCycles = 0f;
		// frameCount = (int) totalGenerated;
		// totalGenerated = frameCount;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isRegenerateParticles() {
		return regenerateParticles;
	}

	public void setRegenerateParticles(boolean regenerateParticles) {
		this.regenerateParticles = regenerateParticles;
	}

	public List<Particle> getParticles() {
		return particles;
	}

	public GeneratorBehaviour getGb() {
		return gb;
	}

	public GeneratorSpace getSpace() {
		return space;
	}

	public void setSpace(GeneratorSpace space) {
		this.space = space;
	}

	public void reset() {
		age = 0.0f;
		particles.clear();

	}

	/*
	 * public List<WorldForce> getForces() { return forces; } //
	 */
	public ParticleBehaviour getPb() {
		return pb;
	}

	public void setPb(ParticleBehaviour pb) {
		this.pb = pb;
		if (pb != null) {
			// pb.setParticles(particles);
			pb.recompile();
		}
	}

	public void rebuild() {
		if (gb != null)
			gb.rebuild();

		if (space != null)
			space.rebuild();

		if (pb != null)
			pb.rebuild();

		if (animator != null)
			animator.rebuild();

		if (positionAnimator != null)
			positionAnimator.rebuild();

	}

	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;

		if (gb != null)
			gb.setResolution(resolution);

		if (space != null)
			space.setResolution(resolution);

		if (pb != null)
			pb.setResolution(resolution);

		if (animator != null)
			animator.setResolution(resolution);

		if (positionAnimator != null)
			positionAnimator.setResolution(resolution);
	}

	public SpaceAnimator getAnimator() {
		return animator;
	}

	public void setAnimator(SpaceAnimator animator) {
		this.animator = animator;
	}

	public void setGb(GeneratorBehaviour gb) {
		this.gb = gb;
	}

	public boolean isAbsoluteParticleAngle() {
		return absoluteParticleAngle;
	}

	public void setAbsoluteParticleAngle(boolean absoluteParticleAngle) {
		this.absoluteParticleAngle = absoluteParticleAngle;
	}

	public ParticleRender getRender() {
		return render;
	}

	public void setRender(ParticleRender render) {
		this.render = render;
	}

	public List<Modifier> getModifiers() {
		return modifiers;
	}

	public void setModifiers(List<Modifier> modifiers) {
		this.modifiers = modifiers;
	}

	public Point3f getPosition() {
		return position;
	}

	public boolean isKillParticles() {
		return killParticles;
	}

	public void setKillParticles(boolean particlesDie) {
		this.killParticles = particlesDie;
	}

	public PositionAnimator getPositionAnimator() {
		return positionAnimator;
	}

	public void setPositionAnimator(PositionAnimator positionAnimator) {
		this.positionAnimator = positionAnimator;
	}

	public int getGeneratorBehaviourIndex() {
		return generatorBehaviourIndex;
	}

	public void setGeneratorBehaviourIndex(int generatorBehaviourIndex) {
		this.generatorBehaviourIndex = generatorBehaviourIndex;
	}

	/*
	 *  //
	 */
	public int getParticleBehaviourIndex() {
		return particleBehaviourIndex;
	}

	public void setParticleBehaviourIndex(int particleBehaviourIndex) {
		this.particleBehaviourIndex = particleBehaviourIndex;
	}

	public int getPositionAnimatorIndex() {
		return positionAnimatorIndex;
	}

	public void setPositionAnimatorIndex(int positionAnimatorIndex) {
		this.positionAnimatorIndex = positionAnimatorIndex;
	}

	public int getRenderIndex() {
		return renderIndex;
	}

	public void setRenderIndex(int renderIndex) {
		this.renderIndex = renderIndex;
	}

	public int getSpaceAnimatorIndex() {
		return spaceAnimatorIndex;
	}

	public void setSpaceAnimatorIndex(int spaceAnimatorIndex) {
		this.spaceAnimatorIndex = spaceAnimatorIndex;
	}

	public int getSpaceIndex() {
		return spaceIndex;
	}

	public void setSpaceIndex(int spaceIndex) {
		this.spaceIndex = spaceIndex;
	}

	/*
	 * public String getModifierIndexes() { return modifierIndexes; }
	 * 
	 * public void setModifierIndexes(String modifierIndexes) {
	 * this.modifierIndexes = modifierIndexes; } //
	 */
	public List<Integer> getModifierIndexes() {
		return modifierIndexes;
	}

	public void setModifierIndexes(List<Integer> modifierIndexes) {
		this.modifierIndexes = modifierIndexes;
	}

	// public Matrix4f getRotation() {
	// return rotation;
	// }
	//
	// public void setRotation(Matrix4f rotation) {
	// this.rotation = rotation;
	// }

}
