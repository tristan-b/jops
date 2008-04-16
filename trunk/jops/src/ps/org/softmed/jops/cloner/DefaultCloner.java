package org.softmed.jops.cloner;

import java.util.HashMap;
import java.util.Map;

import org.softmed.jops.Generator;
import org.softmed.jops.GeneratorBehaviour;
import org.softmed.jops.ParticleBehaviour;
import org.softmed.jops.ParticleRender;
import org.softmed.jops.ParticleSystem;
import org.softmed.jops.PositionAnimator;
import org.softmed.jops.SpaceAnimator;
import org.softmed.jops.modifiers.Modifier;
import org.softmed.jops.modifiers.PointMass;
import org.softmed.jops.space.GeneratorSpace;


public class DefaultCloner implements Cloner {

	public ParticleSystem getStandaloneCopy(ParticleSystem original) {
		ParticleSystem copy = new ParticleSystem();

		Map<Object, Object> copies = new HashMap<Object, Object>();
		// basic variables
		copy.setDescription(original.getDescription());
		// ps.setLife(life);
		copy.setLimit(original.getLimit());
		copy.setLimited(original.isLimited());
		copy.setName(original.getName());
		copy.setRepeat(original.isRepeat());
		copy.setResolution(original.getResolution());
		// complicated variables
		Object copiedObject = null;
		for (ParticleRender renderer : original.getRenders()) {
			copiedObject = getGeneralStandAloneCopy(renderer, copies);
			copies.put(renderer, copiedObject);
			copy.getRenders().add((ParticleRender) copiedObject);
		}

		for (GeneratorBehaviour renderer : original.getGenBehaviours()) {
			copiedObject = getGeneralStandAloneCopy(renderer, copies);
			copies.put(renderer, copiedObject);
			copy.getGenBehaviours().add((GeneratorBehaviour) copiedObject);
		}

		for (ParticleBehaviour renderer : original.getBehaviours()) {
			copiedObject = getGeneralStandAloneCopy(renderer, copies);
			copies.put(renderer, copiedObject);
			copy.getBehaviours().add((ParticleBehaviour) copiedObject);
		}

		for (GeneratorSpace renderer : original.getSpaces()) {
			copiedObject = getGeneralStandAloneCopy(renderer, copies);
			copies.put(renderer, copiedObject);
			copy.getSpaces().add((GeneratorSpace) copiedObject);
		}

		for (SpaceAnimator renderer : original.getAnimators()) {
			copiedObject = getGeneralStandAloneCopy(renderer, copies);
			copies.put(renderer, copiedObject);
			copy.getAnimators().add((SpaceAnimator) copiedObject);
		}

		for (PositionAnimator renderer : original.getPanimators()) {
			copiedObject = getGeneralStandAloneCopy(renderer, copies);
			copies.put(renderer, copiedObject);
			copy.getPanimators().add((PositionAnimator) copiedObject);
		}

		for (Modifier renderer : original.getModifiers()) {
			copiedObject = getGeneralStandAloneCopy(renderer, copies);
			copies.put(renderer, copiedObject);
			copy.getModifiers().add((Modifier) copiedObject);
			if (copiedObject instanceof PointMass) {
				PointMass pm = (PointMass) copiedObject;
				pm.setReference(copy);
			}

		}

		for (Generator gen : original.getGenerators()) {
			copy.getGenerators().add(
					(Generator) getGeneralStandAloneCopy(gen, copies));
		}

		return copy;
	}

	protected Object getGeneralStandAloneCopy(Object original,
			Map<Object, Object> copies) {
		if (original instanceof StandaloneCloner)
			return ((StandaloneCloner) original).getStandaloneCopy();
		else if (original instanceof StandaloneClonerNeedsCopyList)
			return ((StandaloneClonerNeedsCopyList) original)
					.getStandaloneCopy(copies);

		return getAdequateStandAloneCopy(original, copies);
	}

	private Object getAdequateStandAloneCopy(Object original,
			Map<Object, Object> copies) {

		if (original instanceof PositionAnimator)
			return getStandaloneCopy((PositionAnimator) original, copies);

		if (original instanceof GeneratorSpace)
			return getStandaloneCopy((GeneratorSpace) original, copies);

		if (original instanceof SpaceAnimator)
			return getStandaloneCopy((SpaceAnimator) original, copies);

		if (original instanceof Modifier)
			return getStandaloneCopy((Modifier) original, copies);

		if (original instanceof ParticleRender)
			return getStandaloneCopy((ParticleRender) original, copies);

		if (original instanceof GeneratorBehaviour)
			return getStandaloneCopy((GeneratorBehaviour) original, copies);

		if (original instanceof ParticleBehaviour)
			return getStandaloneCopy((ParticleBehaviour) original, copies);

		if (original instanceof Generator)
			return getStandaloneCopy((Generator) original, copies);

		throw new RuntimeException(
				"No default cloning procedure for this object : " + original);
	}

	private GeneratorSpace getStandaloneCopy(GeneratorSpace original,
			Map<Object, Object> copies) {

		throw new RuntimeException(
				"No default cloning procedure for GeneratorSpace!!!!");
	}

	private SpaceAnimator getStandaloneCopy(SpaceAnimator original,
			Map<Object, Object> copies) {
		SpaceAnimator copy = new SpaceAnimator();
		copy.setSpaceAngle(original.getSpaceAngle().getStandaloneCopy());
		copy.setSpaceSpin(original.getSpaceSpin().getStandaloneCopy());

		return copy;
	}

	private PositionAnimator getStandaloneCopy(PositionAnimator original,
			Map<Object, Object> copies) {
		PositionAnimator copy = new PositionAnimator();
		copy.setBias(original.getBias().getStandaloneCopy());
		copy.setBiasAngle(original.getBiasAngle().getStandaloneCopy());
		copy.setBiasSpin(original.getBiasSpin().getStandaloneCopy());
		copy.setPosition(original.getPosition().getStandaloneCopy());

		return copy;
	}

	private Modifier getStandaloneCopy(Modifier original,
			Map<Object, Object> copies) {

		throw new RuntimeException(
				"No default cloning procedure for Modifiers!!!!");
	}

	private ParticleRender getStandaloneCopy(ParticleRender original,
			Map<Object, Object> copies) {

		ParticleRender copy = new ParticleRender();
		copy.setName(original.getName());
		copy.setDescription(original.getDescription());
		copy.setDestinationFactor(original.getDestinationFactor());
		copy.setSourceFactor(original.getSourceFactor());
		copy.setTextureId(original.getTextureId());
		copy.setTextureName(original.getTextureName());

		return copy;
	}

	private ParticleBehaviour getStandaloneCopy(ParticleBehaviour original,
			Map<Object, Object> copies) {

		ParticleBehaviour copy = new ParticleBehaviour();
		copy.setName(original.getName());
		copy.setDescription(original.getDescription());

		copy.setResolution(original.getResolution());

		copy.setAlpha(original.getAlpha().getStandaloneCopy());
		copy.setColor(original.getColor().getStandaloneCopy());
		copy.setMass(original.getMass().getStandaloneCopy());
		copy.setSpin(original.getSpin().getStandaloneCopy());
		copy.setAngle(original.getAngle().getStandaloneCopy());
		copy.setSize(original.getSize().getStandaloneCopy());
		copy.setElasticity(original.getElasticity().getStandaloneCopy());
		copy
				.setParticleAngleV(original.getParticleAngleV()
						.getStandaloneCopy());
		copy
				.setParticleAngleH(original.getParticleAngleH()
						.getStandaloneCopy());
		copy.setParticleSpinV(original.getParticleSpinV().getStandaloneCopy());

		copy.setParticleSpinH(original.getParticleSpinH().getStandaloneCopy());
		copy.setWidth(original.getWidth().getStandaloneCopy());
		copy.setHeight(original.getHeight().getStandaloneCopy());
		copy.setTexWidth(original.getTexWidth().getStandaloneCopy());
		copy.setTexHeight(original.getTexHeight().getStandaloneCopy());
		copy.setSpeed(original.getSpeed().getStandaloneCopy());

		return copy;
	}

	private Generator getStandaloneCopy(Generator original,
			Map<Object, Object> copies) {

		Generator gen = new Generator();
		gen.setName(original.getName());
		gen.setDescription(original.getDescription());

		gen.setAbsoluteParticleAngle(original.isAbsoluteParticleAngle());
		gen.setAlive(original.isAlive());
		gen.setRegenerateParticles(original.isRegenerateParticles());
		gen.setKillParticles(original.isKillParticles());
		// gen.setResolution(resolution);

		gen.setPositionAnimator((PositionAnimator) copies.get(original
				.getPositionAnimator()));
		gen.setAnimator((SpaceAnimator) copies.get(original.getAnimator()));
		gen.setGb((GeneratorBehaviour) copies.get(original.getGb()));
		gen.setPb((ParticleBehaviour) copies.get(original.getPb()));
		gen.setRender((ParticleRender) copies.get(original.getRender()));
		gen.setSpace((GeneratorSpace) copies.get(original.getSpace()));
		for (Modifier modifier : original.getModifiers()) {
			gen.getModifiers().add((Modifier) copies.get(modifier));
		}

		return gen;
	}

	private GeneratorBehaviour getStandaloneCopy(GeneratorBehaviour original,
			Map<Object, Object> copies) {

		GeneratorBehaviour copy = new GeneratorBehaviour();
		copy.setName(original.getName());
		copy.setDescription(original.getDescription());

		copy.setResolution(original.getResolution());

		copy.setAlpha(original.getAlpha().getStandaloneCopy());
		copy.setColor(original.getColor().getStandaloneCopy());
		copy.setMass(original.getMass().getStandaloneCopy());
		copy.setSpin(original.getSpin().getStandaloneCopy());
		copy.setAngle(original.getAngle().getStandaloneCopy());
		copy.setSize(original.getSize().getStandaloneCopy());
		copy.setElasticity(original.getElasticity().getStandaloneCopy());
		copy
				.setParticleAngleV(original.getParticleAngleV()
						.getStandaloneCopy());
		copy
				.setParticleAngleH(original.getParticleAngleH()
						.getStandaloneCopy());
		copy.setParticleSpinV(original.getParticleSpinV().getStandaloneCopy());

		copy.setParticleSpinH(original.getParticleSpinH().getStandaloneCopy());
		copy.setWidth(original.getWidth().getStandaloneCopy());
		copy.setHeight(original.getHeight().getStandaloneCopy());
		copy.setTexWidth(original.getTexWidth().getStandaloneCopy());
		copy.setTexHeight(original.getTexHeight().getStandaloneCopy());
		copy.setSpeed(original.getSpeed().getStandaloneCopy());

		copy.setLife(original.getLife().getStandaloneCopy());
		copy.setRate(original.getRate().getStandaloneCopy());
		copy.setAngleV(original.getAngleV().getStandaloneCopy());
		copy.setAngleH(original.getAngleH().getStandaloneCopy());
		copy.setSpinV(original.getSpinV().getStandaloneCopy());
		copy.setSpinH(original.getSpinH().getStandaloneCopy());
		copy.setAngleSpreadV(original.getAngleSpreadV().getStandaloneCopy());
		copy.setAngleSpreadH(original.getAngleSpreadH().getStandaloneCopy());
		copy.setNumber(original.getNumber());
		// copy.time = original.getT;

		return copy;
	}

}
