package org.softmed.jops.modifiers;

import java.util.Map;

import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Vector3f;
import org.openmali.vecmath2.Point3f;
import org.softmed.jops.Particle;
import org.softmed.jops.ParticleSystem;
import org.softmed.jops.PositionAnimator;
import org.softmed.jops.cloner.StandaloneClonerNeedsCopyList;
import org.softmed.jops.modifiers.pointmass.constant;
import org.softmed.jops.modifiers.pointmass.linear;
import org.softmed.jops.modifiers.pointmass.squared;
import org.softmed.jops.valuelists.ValueListf;


public class PointMass extends Modifier implements
		StandaloneClonerNeedsCopyList {

	// constants
	public static final String CONSTANT = "constant";

	public static final String LINEAR = "linear";

	public static final String SQUARED = "squared";

	public static final String DIRECT = "direct";

	public static final String INVERSE = "inverse";

	ParticleSystem reference;

	// Point3f position = new Point3f();

	private Point3f framePosition = new Point3f();

	// attributes
	String distanceVariation = CONSTANT;

	String distanceProportion = DIRECT;

	boolean threshold = false;

	protected PositionAnimator animator;
	int positionAnimatorIndex = -1;

	// the calculator
	DistanceCalculator strengthCalculator = new constant(this);

	// value lists
	ValueListf strength = new ValueListf();

	ValueListf massImportance = new ValueListf();

	ValueListf distanceImportance = new ValueListf();

	ValueListf thresholdValue = new ValueListf();

	// temp variables
	float currentStrenght;

	float currentMassImportance;

	float currentDistanceImportance;

	float currentTreshold;

	float massScaler;

	float strengthScaler;

	Vector3f particleSpeedVector = new Vector3f();

	Vector3f temp = new Vector3f();

	protected void configureCalculator() {

		if (distanceVariation.equals(CONSTANT))
			strengthCalculator = new constant(this);
		else if (distanceVariation.equals(LINEAR))
			strengthCalculator = new linear(this);
		else if (distanceVariation.equals(SQUARED))
			strengthCalculator = new squared(this);

		if (distanceProportion.equals(INVERSE))
			strengthCalculator = new InverseDistanceCalculator(this,
					strengthCalculator);

		// if (threshold)
		// strengthCalculator = new TresholdDistanceCalculator(this,
		// strengthCalculator);
	}

	public PointMass() {
		strength.addValue(1.8f, 0f);
		massImportance.addValue(1f, 0f);
		distanceImportance.addValue(1f, 0f);
		thresholdValue.addValue(1f, 0f);
	}

	/*
	 * public void update(float life, float dt) {
	 * 
	 * if (animator != null) { position.set(0f,0f,0f);
	 * animator.setInitialPosition(position); }
	 * 
	 * currentMassImportance = massImportance.getValueAt(life);
	 * currentDistanceImportance = distanceImportance.getValueAt(life);
	 * currentTreshold = thresholdValue.getValueAt(life); currentStrenght =
	 * strength.getValueAt(life) * dt; } //
	 */

	@Override
	public void update(Point3f position, Matrix4f rotation, float life, float dt) {

		framePosition.set(0f, 0f, 0f);

		if (animator != null) {
			animator.setInitialPosition(framePosition);
		}

		if (reference != null)
			framePosition.add(reference.getPosition());

		if (rotation != null)
			rotation.transform(framePosition);
		// framePosition.add(position);

		// TODO : remove this Vector3f.add(framePosition, , framePosition);

		currentMassImportance = massImportance.getValueAt(life);
		currentDistanceImportance = distanceImportance.getValueAt(life);
		currentTreshold = thresholdValue.getValueAt(life);
		currentStrenght = strength.getValueAt(life) * dt;

	}

	@Override
	public void modify(Particle part) {

		temp.sub(framePosition, part.position);

		if (temp.getX() == 0f && temp.getY() == 0f && temp.getZ() == 0f)
			return;
		// */

		if (threshold && temp.length() >= currentTreshold)
			return;

		strengthScaler = strengthCalculator.calculateStrength(temp);

		massScaler = part.mass * currentMassImportance;

		massScaler = strengthScaler * currentStrenght * (1.0f + massScaler);

		temp.normalize();
		temp.scale(massScaler);

		particleSpeedVector.set(part.direction);
		particleSpeedVector.scale(part.speed);

		particleSpeedVector.add(temp);

		part.speed = particleSpeedVector.length();
		// particleSpeedVector.normalize(part.direction);

		particleSpeedVector.normalize();
		part.direction.set(particleSpeedVector);

	}

	@Override
	public void recompile() {

		massImportance.compileArray();
		strength.compileArray();
		distanceImportance.compileArray();
		thresholdValue.compileArray();

		configureCalculator();

	}

	@Override
	public void setResolution(int resolution) {

		massImportance.setResolution(resolution);
		strength.setResolution(resolution);
		distanceImportance.setResolution(resolution);
		thresholdValue.setResolution(resolution);

	}

	public ValueListf getMassImportance() {
		return massImportance;
	}

	public void setMassImportance(ValueListf massImportance) {
		this.massImportance = massImportance;
	}

	public ValueListf getStrength() {
		return strength;
	}

	public void setStrength(ValueListf strength) {
		this.strength = strength;
	}

	public Point3f getPosition() {
		return framePosition;
	}

	/*
	 * public void setPosition(Point3f position) { this.position = position; } //
	 */
	public PositionAnimator getPositionAnimator() {
		return animator;
	}

	public void setPositionAnimator(PositionAnimator animator) {
		this.animator = animator;
	}

	public String getDistanceProportion() {
		return distanceProportion;
	}

	public void setDistanceProportion(String distanceProportion) {
		this.distanceProportion = distanceProportion;
		configureCalculator();
	}

	public String getDistanceVariation() {
		return distanceVariation;
	}

	public void setDistanceVariation(String distanceVariation) {
		this.distanceVariation = distanceVariation;
		configureCalculator();
	}

	public boolean isThreshold() {
		return threshold;
	}

	public void setThreshold(boolean threshold) {
		this.threshold = threshold;
		configureCalculator();
	}

	public ValueListf getDistanceImportance() {
		return distanceImportance;
	}

	public ValueListf getThresholdValue() {
		return thresholdValue;
	}

	public float getCurrentDistanceImportance() {
		return currentDistanceImportance;
	}

	public float getCurrentMassImportance() {
		return currentMassImportance;
	}

	public float getCurrentStrenght() {
		return currentStrenght;
	}

	public float getCurrentTreshold() {
		return currentTreshold;
	}

	public int getPositionAnimatorIndex() {
		return positionAnimatorIndex;
	}

	public void setPositionAnimatorIndex(int positionAnimatorIndex) {
		this.positionAnimatorIndex = positionAnimatorIndex;
	}

	public ParticleSystem getReference() {
		return reference;
	}

	public void setReference(ParticleSystem reference) {
		this.reference = reference;
	}

	public Object getStandaloneCopy(Map<Object, Object> copies) {
		PointMass copy = new PointMass();
		copy.distanceImportance = distanceImportance.getStandaloneCopy();
		copy.strength = strength.getStandaloneCopy();
		copy.massImportance = massImportance.getStandaloneCopy();
		copy.thresholdValue = thresholdValue.getStandaloneCopy();

		copy.animator = (PositionAnimator) copies.get(animator);
		copy.threshold = threshold;
		copy.setDistanceProportion(distanceProportion);
		copy.setDistanceVariation(distanceVariation);

		return copy;
	}

}
