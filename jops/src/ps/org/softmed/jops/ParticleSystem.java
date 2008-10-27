/*
 * NewClass.java
 *
 * Created on 11 de Julho de 2006, 21:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops;

import java.util.ArrayList;
import java.util.List;

import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.softmed.jops.modifiers.Modifier;
import org.softmed.jops.space.GeneratorSpace;

/**
 * 
 * @author gui
 */
public class ParticleSystem extends InfoObject {

	public boolean printRotMatrix = false;

	public static final int DEFAULT_RESOLUTION = 30;

	public static final String VERSION = "0.5";

	String version = VERSION;

	boolean repeat = false;

	boolean limited = false;

	// boolean nolimit = false;

	boolean alive = true;

	boolean renderable = true;

	boolean remove = false;

	float limit = 2.0f;

	int resolution = 30;

	protected Point3f position = new Point3f();

	protected Matrix4f rotation;

	protected List<ParticleRender> renders = new ArrayList<ParticleRender>();

	protected List<ParticleBehaviour> behaviours = new ArrayList<ParticleBehaviour>();

	protected List<GeneratorBehaviour> genBehaviours = new ArrayList<GeneratorBehaviour>();

	protected List<GeneratorSpace> spaces = new ArrayList<GeneratorSpace>();

	protected List<SpaceAnimator> animators = new ArrayList<SpaceAnimator>();

	protected List<PositionAnimator> panimators = new ArrayList<PositionAnimator>();

	protected List<Modifier> modifiers = new ArrayList<Modifier>();

	protected List<Modifier> externalModifiers = new ArrayList<Modifier>();

	protected List<Generator> generators = new ArrayList<Generator>();

	float life = 0.0f;

	private Generator gen;

	// private ParticleRender render;

	private GeneratorBehaviour genBehaviour;

	private GeneratorSpace space;

	// private ParticleBehaviour behaviour;

	private SpaceAnimator animator;

	protected PositionAnimator panimator;

	private Modifier modifier;

	List<RemovalListener> removalListeners = new ArrayList<RemovalListener>();

	/** Creates a new instance of NewClass */
	public ParticleSystem() {
	}

	public void setCurrentVersion() {
		version = VERSION;
	}

	public void reset() {
		// remove = false;
		// alive = true;
		for (Generator gen : generators) {
			gen.reset();
		}

		for (GeneratorBehaviour genBehaviour : genBehaviours) {
			genBehaviour.reset();
		}

		for (GeneratorSpace space : spaces) {
			space.reset();
		}

		life = 0.0f;
	}

	public void setAliveAllGens(boolean alive) {
		for (Generator gen : generators) {
			gen.setAlive(alive);
			if (!alive)
				gen.reset();
			else
				gen.setAlive(true);
		}
	}

	public void stopAllGens() {
		setAliveAllGens(false);
	}

	public void startAllGens() {
		setAliveAllGens(true);
	}

	public List<GeneratorSpace> getSpaces() {
		return spaces;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public float getLife() {
		return life;
	}

	public void setLife(float life) {
		this.life = life;
	}

	public float getLimit() {
		return limit;
	}

	public void setLimit(float limit) {
		this.limit = limit;
	}

	public void updateLife(float dt) {
		life += dt;

		if (life > limit) {

			if (!limited) {
				if (repeat) {
					life = life % limit;
					for (Generator gen : generators) {
						gen.reset();
					}
				}

			} else {
				alive = false;
				remove = true;
			}

		}
	}

	public boolean isRenderable() {
		return renderable;
	}

	public void setRenderable(boolean renderable) {
		this.renderable = renderable;
	}

	public int getResolution() {
		if (this.resolution == 0)
			this.resolution = DEFAULT_RESOLUTION;

		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
		if (this.resolution == 0)
			this.resolution = DEFAULT_RESOLUTION;

		for (Generator gen : generators) {
			gen.setResolution(resolution);
		}

		for (GeneratorSpace space : spaces) {
			space.setResolution(resolution);
		}

		for (SpaceAnimator animator : animators) {
			animator.setResolution(resolution);
		}

		for (PositionAnimator panimator : panimators) {
			panimator.setResolution(resolution);
		}

		for (ParticleBehaviour behaviour : behaviours) {
			behaviour.setResolution(resolution);
		}

		for (GeneratorBehaviour genBehaviour : genBehaviours) {
			genBehaviour.setResolution(resolution);
		}

		for (Modifier modifier : modifiers) {
			modifier.setResolution(resolution);
		}

	}

	public List<GeneratorBehaviour> getGenBehaviours() {
		return genBehaviours;
	}

	public List<ParticleBehaviour> getBehaviours() {
		return behaviours;
	}

	public List<SpaceAnimator> getAnimators() {
		return animators;
	}

	public void processFrame(float dt) {
		if (!alive)
			return;

		if (rotation != null && printRotMatrix) {
			// System.out.println("rot matrix");
			// System.out.println(rotation.m00() + "," + rotation.m10() + ","
			// + rotation.m20() + "," + rotation.m30());
			// System.out.println(rotation.m01() + "," + rotation.m11() + ","
			// + rotation.m21() + "," + rotation.m31());
			// System.out.println(rotation.m02() + "," + rotation.m12() + ","
			// + rotation.m22() + "," + rotation.m32());
			// System.out.println(rotation.m03() + "," + rotation.m13() + ","
			// + rotation.m23() + "," + rotation.m33());
		}

		updateLife(dt);

		for (int i = 0; i < spaces.size(); i++) {
			space = spaces.get(i);
			space.update(life);
		}

		for (int i = 0; i < animators.size(); i++) {
			animator = animators.get(i);
			animator.update(life, dt);
		}

		for (int i = 0; i < panimators.size(); i++) {
			panimator = panimators.get(i);
			panimator.update(life, dt);
		}

		for (int i = 0; i < genBehaviours.size(); i++) {
			genBehaviour = genBehaviours.get(i);
			genBehaviour.update(life, dt);
		}

		for (int i = 0; i < modifiers.size(); i++) {
			modifier = modifiers.get(i);
			modifier.update(position, rotation, life, dt);
		}

		for (int i = 0; i < generators.size(); i++) {
			gen = generators.get(i);
			gen.update(position, rotation, life, dt);
		}

	}

	public void rebuild() {

		if (genBehaviours == null)
			genBehaviours = new ArrayList<GeneratorBehaviour>();

		if (animators == null)
			animators = new ArrayList<SpaceAnimator>();

		if (panimators == null)
			panimators = new ArrayList<PositionAnimator>();

		if (behaviours == null)
			behaviours = new ArrayList<ParticleBehaviour>();

		if (renders == null)
			renders = new ArrayList<ParticleRender>();

		if (modifiers == null)
			modifiers = new ArrayList<Modifier>();

		for (Generator generator : generators) {
			generator.rebuildParticles();
			generator.recompileAll();
			generator.setResolution(resolution);
			generator.rebuild();
			if (generator.getGb() != null
					&& !genBehaviours.contains(generator.getGb()))
				genBehaviours.add(generator.getGb());
			if (generator.getSpace() != null
					&& !spaces.contains(generator.getSpace()))
				spaces.add(generator.getSpace());
			if (generator.getAnimator() != null
					&& !animators.contains(generator.getAnimator()))
				animators.add(generator.getAnimator());
			if (generator.getPositionAnimator() != null
					&& !panimators.contains(generator.getPositionAnimator()))
				panimators.add(generator.getPositionAnimator());
			if (generator.getPb() != null
					&& !behaviours.contains(generator.getPb()))
				behaviours.add(generator.getPb());
			if (generator.getRender() != null
					&& !renders.contains(generator.getRender()))
				renders.add(generator.getRender());

			// TODO generators can have several modifiers applied to them...
			// TODO this may return duplicates...debuuuggg
			if (generator.getModifiers() != null
					&& !modifiers.containsAll(generator.getModifiers()))
				modifiers.addAll(generator.getModifiers());
		}

		for (GeneratorSpace space : spaces)
			space.recompile();

	}

	public List<Generator> getGenerators() {
		return generators;
	}

	// */
	public List<ParticleRender> getRenders() {
		return renders;
	}

	public boolean isLimited() {
		return limited;
	}

	public void setLimited(boolean repeat) {
		this.limited = repeat;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public void addGenerator(Generator generator) {
		generators.add(generator);

		if (generator.getGb() != null
				&& !genBehaviours.contains(generator.getGb()))
			genBehaviours.add(generator.getGb());
		if (generator.getSpace() != null
				&& !spaces.contains(generator.getSpace()))
			spaces.add(generator.getSpace());
		if (generator.getAnimator() != null
				&& !animators.contains(generator.getAnimator()))
			animators.add(generator.getAnimator());
		if (generator.getPositionAnimator() != null
				&& !panimators.contains(generator.getPositionAnimator()))
			panimators.add(generator.getPositionAnimator());
		if (generator.getPb() != null
				&& !behaviours.contains(generator.getPb()))
			behaviours.add(generator.getPb());
		if (generator.getRender() != null
				&& !renders.contains(generator.getRender()))
			renders.add(generator.getRender());
		// TODO this may force duplicates.. TEST && DEBUG
		if (generator.getModifiers() != null
				&& !modifiers.containsAll(generator.getModifiers()))
			modifiers.addAll(generator.getModifiers());
	}

	public void removeGenB(GeneratorBehaviour gen) {
		genBehaviours.remove(gen);
		for (Generator generator : generators) {
			if (generator.getGb() == gen)
				generator.setGb(null);
		}
	}

	public void removeParB(ParticleBehaviour gen) {
		behaviours.remove(gen);
		for (Generator generator : generators) {
			if (generator.getPb() == gen)
				generator.setPb(null);
		}
	}

	public void removeSpace(GeneratorSpace gen) {
		spaces.remove(gen);
		for (Generator generator : generators) {
			if (generator.getSpace() == gen)
				generator.setSpace(null);
		}
	}

	public void removeRender(ParticleRender gen) {
		renders.remove(gen);
		for (Generator generator : generators) {
			if (generator.getRender() == gen)
				generator.setRender(null);
		}
	}

	public void removeAnimator(SpaceAnimator gen) {
		animators.remove(gen);
		for (Generator generator : generators) {
			if (generator.getAnimator() == gen)
				generator.setAnimator(null);
		}
	}

	public void removePositionAnimator(PositionAnimator gen) {
		panimators.remove(gen);
		for (Generator generator : generators) {
			if (generator.getPositionAnimator() == gen)
				generator.setPositionAnimator(null);
		}
	}

	public void removeModifier(Modifier gen) {
		modifiers.remove(gen);
		for (Generator generator : generators) {
			if (generator.getModifiers().contains(gen))
				generator.getModifiers().remove(gen);
		}
	}

	public List<Modifier> getModifiers() {
		return modifiers;
	}

	public List<PositionAnimator> getPanimators() {
		return panimators;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setAnimators(List<SpaceAnimator> animators) {
		this.animators = animators;
	}

	public void setBehaviours(List<ParticleBehaviour> behaviours) {
		this.behaviours = behaviours;
	}

	public void setGenBehaviours(List<GeneratorBehaviour> genBehaviours) {
		this.genBehaviours = genBehaviours;
	}

	public void setGenerators(List<Generator> generators) {
		this.generators = generators;
	}

	public void setModifiers(List<Modifier> modifiers) {
		this.modifiers = modifiers;
	}

	public void setPanimators(List<PositionAnimator> panimators) {
		this.panimators = panimators;
	}

	public void setRenders(List<ParticleRender> renders) {
		this.renders = renders;
	}

	public void setSpaces(List<GeneratorSpace> spaces) {
		this.spaces = spaces;
	}

	public Point3f getPosition() {
		return position;
	}

	public void setPosition(Point3f position) {
		this.position = position;
	}

	public Matrix4f getRotation() {
		return rotation;
	}

	public void setRotation(Matrix4f rotation) {
		this.rotation = rotation;
	}

	public void addExternalModifier(Modifier externalModifier) {
		externalModifiers.add(externalModifier);
		for (int i = 0; i < generators.size(); i++) {
			gen = generators.get(i);
			gen.getModifiers().add(externalModifier);
		}

	}

	public void removeExternalModifier(Modifier externalModifier) {
		externalModifiers.remove(externalModifier);
		for (int i = 0; i < generators.size(); i++) {
			gen = generators.get(i);
			gen.getModifiers().remove(externalModifier);
		}

	}

	public void removeAllExternalModifiers() {
		for (int i = 0; i < generators.size(); i++) {
			gen = generators.get(i);
			gen.getModifiers().removeAll(externalModifiers);
		}
		externalModifiers.clear();
	}

	public void addInternalModifiersAsExternal(ParticleSystem ps) {
		List<Modifier> extModifiers = ps.getModifiers();
		externalModifiers.addAll(extModifiers);
		for (int i = 0; i < generators.size(); i++) {
			gen = generators.get(i);
			gen.getModifiers().addAll(extModifiers);
		}

	}

	public List<RemovalListener> getRemovalListeners() {
		return removalListeners;
	}

	public void addRemovalListener(RemovalListener listener) {
		removalListeners.add(listener);
	}

	public void removeRemovalListener(RemovalListener listener) {
		removalListeners.remove(listener);
	}

	public void signalRemoved() {
		for (RemovalListener listener : removalListeners) {
			listener.wasRemoved(this);
		}
	}

}
