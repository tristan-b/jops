/*
 * ParticleBehaviourRule.java
 *
 * Created on 27 de Fevereiro de 2006, 13:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops;

import java.util.List;

import org.openmali.FastMath;
import org.openmali.vecmath2.Colorf;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Vector3f;
import org.softmed.jops.math.AnglesFastMath;
import org.softmed.jops.operators.ParticleOperator;
import org.softmed.jops.random.RandomGenerator;
import org.softmed.jops.valuelists.ValueListf;

// import particle.math.ParticleFastMath;

/**
 * 
 * @author eu
 */
public class GeneratorBehaviour extends ParticleBehaviour {

	Vector3f vector = new Vector3f();

	private List<Particle> particles;

	// ParticleFastMath math = new ParticleFastMath();

	RandomGenerator rand = new RandomGenerator();

	private ValueListf life = new ValueListf();

	private ValueListf rate = new ValueListf();

	private ValueListf angleV = new ValueListf();

	private ValueListf angleH = new ValueListf();

	private ValueListf spinV = new ValueListf();

	private ValueListf spinH = new ValueListf();

	private ValueListf angleSpreadV = new ValueListf();

	private ValueListf angleSpreadH = new ValueListf();

	// protected boolean absoluteParticleAngle = false;

	private int number = 1000;

	// temp variables
	ParticleOperator operator = null;

	Particle part = null;

	float vangleH, vangleV, sinH, cosH, sinV, cosV;

	private float currentAlpha;

	private Colorf currentColor;

	private float currentMass;

	private float currentSpin;

	private float currentSize;

	private float currentBounce;

	private float currentSpeed;

	private Vector3f currentPosition;

	private float currentAngleH;

	private float currentAngleV;

	private float currentSpinH;

	private float currentSpinV;

	private float oldSpinH;

	private float oldSpinV;

	private float currentAngleSpreadH;

	private float currentAngleSpreadV;

	private float currentRate;

	private float currentLife;

	private float currentAngle;
	/*
	 * private float currentPAndleH;
	 * 
	 * private float currentPAndleV;
	 * 
	 * private float currentPSpinH;
	 * 
	 * private float currentPSpinV;
	 * 
	 * private float currentWidth, currentHeight;
	 * 
	 * private float currentTexWidth, currentTexHeight; //
	 */

	private float time;

	private Matrix4f rotation;

	@Override
	public void rebuild() {
		// math = new ParticleFastMath();
		rand = new RandomGenerator();

	}

	/** Creates a new instance of ParticleBehaviourRule */
	public GeneratorBehaviour() {

		setNumber(50);
		getSpeed().addValue(1f, 0.0f);
		getLife().addValue(1.0f, 0.0f);
		getRate().addValue(50.0f, 0.0f);
		getAlpha().addValue(1f, 0.0f);
		getAngleH().addValue(0.0f, 0.0f);
		getAngleV().addValue(0.5f, 0.0f);
		getAngleSpreadH().addValue(0.2f, 0.0f);
		getAngleSpreadV().addValue(0.2f, 0.0f);
		getSize().addValue(0.5f, 0.0f);

		recompile();

	}

	@Override
	public void recompile() {
		thisrebuildIfNecessary();

		super.recompile();
		speed.compileArray();
		life.compileArray();
		rate.compileArray();
		angleV.compileArray();
		angleH.compileArray();
		spinV.compileArray();
		spinH.compileArray();
		angleSpreadV.compileArray();
		angleSpreadH.compileArray();

	}

	private void thisrebuildIfNecessary() {
		if (spinV == null)
			spinV = new ValueListf();

		if (spinH == null)
			spinH = new ValueListf();

	}

	public void setInitialState(Particle temp, float diference) {

		temp.speed = speed.getValueAt(time);

		temp.life = life.getValueAt(time);// -diference;
		temp.age = 0.0f;

		temp.alpha = alpha.getValueAt(time);
		temp.color = new Colorf(color.getValueAt(time));

		temp.mass = mass.getValueAt(time);
		temp.spin = spin.getValueAt(time);
		temp.angle = angle.getValueAt(time);
		temp.size = size.getValueAt(time);
		temp.elasticity = elasticity.getValueAt(time);

		vangleH = rand.getFloat(currentAngleSpreadH);
		vangleV = rand.getFloat(currentAngleSpreadV);

		if (currentAngleSpreadH > 0)
			vangleH = vangleH - currentAngleSpreadH / 2;

		if (currentAngleSpreadV > 0)
			vangleV = vangleV - currentAngleSpreadV / 2;

		vangleH += currentAngleH;
		vangleV += currentAngleV;

		cosH = FastMath.cos(vangleH);
		sinH = FastMath.sin(vangleH);
		cosV = FastMath.cos(vangleV);
		sinV = FastMath.sin(vangleV);

		temp.direction.setX(sinH * cosV);
		temp.direction.setY(sinV);
		temp.direction.setZ(cosH * cosV);

		temp.direction.normalize();

		if (rotation != null) {
			rotation.transform(temp.direction);
		}

		temp.angleH = particleAngleH.getValueAt(time);
		temp.angleV = particleAngleV.getValueAt(time);

		if (rotation != null) {
			temp.rotation = new Matrix4f(rotation);
		}

		temp.spinH = particleSpinH.getValueAt(time);
		temp.spinV = particleSpinV.getValueAt(time);

		temp.height = height.getValueAt(time);
		temp.width = width.getValueAt(time);

		temp.texHeight = texHeight.getValueAt(time);
		temp.texWidth = texWidth.getValueAt(time);

	}

	public void update(float time, float timelapse) {

		this.time = time;
		// update current ParticleBehaviour Rules
		currentAlpha = alpha.getValueAt(time);
		currentColor = color.getValueAt(time);
		currentMass = mass.getValueAt(time);
		currentSpin = spin.getValueAt(time);
		currentAngle = angle.getValueAt(time);
		currentSize = size.getValueAt(time);
		currentBounce = elasticity.getValueAt(time);
		currentSpeed = speed.getValueAt(time);

		/*
		 * currentPAndleH = particleAngleH.getValueAt(time); currentPAndleV =
		 * particleAngleV.getValueAt(time);
		 * 
		 * currentPSpinH = particleSpinH.getValueAt(time); currentPSpinV =
		 * particleSpinV.getValueAt(time); //
		 */
		// update aditional generator specific current values
		currentLife = life.getValueAt(time);
		currentRate = rate.getValueAt(time);

		currentAngleH = angleH.getValueAt(time);
		currentAngleV = angleV.getValueAt(time);

		currentSpinH = spinH.getValueAt(time);
		currentSpinV = spinV.getValueAt(time);

		oldSpinV += currentSpinV * timelapse;
		oldSpinH += currentSpinH * timelapse;

		if (oldSpinV >= 360)
			oldSpinV %= 360;
		else if (oldSpinV < -360)
			oldSpinV = -oldSpinV % 360;

		if (oldSpinH >= 360)
			oldSpinH %= 360;
		else if (oldSpinH < -360)
			oldSpinH = -oldSpinH % 360;

		currentAngleH += oldSpinH;
		currentAngleV += oldSpinV;

		currentAngleSpreadH = angleSpreadH.getValueAt(time);
		currentAngleSpreadV = angleSpreadV.getValueAt(time);

		/*
		 * currentWidth = width.getValueAt(time); currentHeight =
		 * height.getValueAt(time);
		 * 
		 * currentTexWidth = texWidth.getValueAt(time); currentTexHeight =
		 * texHeight.getValueAt(time); //
		 */
	}

	@Override
	public void setParticles(List<Particle> particles) {
		this.particles = particles;
	}

	public ValueListf getLife() {
		return life;
	}

	public void setLife(ValueListf life) {
		this.life = life;
	}

	public ValueListf getRate() {
		return rate;
	}

	public void setRate(ValueListf rate) {
		this.rate = rate;
	}

	public List<Particle> getParticles() {
		return particles;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public float getCurrentAlpha() {
		return currentAlpha;
	}

	public Colorf getCurrentColor() {
		return currentColor;
	}

	public float getCurrentMass() {
		return currentMass;
	}

	public float getCurrentSpin() {
		return currentSpin;
	}

	public float getCurrentSize() {
		return currentSize;
	}

	public float getCurrentBounce() {
		return currentBounce;
	}

	public float getCurrentSpeed() {
		return currentSpeed;
	}

	public Vector3f getCurrentPosition() {
		return currentPosition;
	}

	public float getCurrentRate() {
		return currentRate;
	}

	public float getCurrentLife() {
		return currentLife;
	}

	public ValueListf getAngleV() {
		return angleV;
	}

	public ValueListf getAngleH() {
		return angleH;
	}

	public ValueListf getAngleSpreadV() {
		return angleSpreadV;
	}

	public ValueListf getAngleSpreadH() {
		return angleSpreadH;
	}

	@Override
	public ValueListf getSpeed() {
		return speed;
	}

	public void setAngleH(ValueListf angleH) {
		this.angleH = angleH;
	}

	public void setAngleSpreadH(ValueListf angleSpreadH) {
		this.angleSpreadH = angleSpreadH;
	}

	public void setAngleSpreadV(ValueListf angleSpreadV) {
		this.angleSpreadV = angleSpreadV;
	}

	public void setAngleV(ValueListf angleV) {
		this.angleV = angleV;
	}

	public Float getCurrentAngle() {
		return currentAngle;
	}

	/*
	 * public boolean isAbsoluteParticleAngle() { return absoluteParticleAngle; }
	 * 
	 * public void setAbsoluteParticleAngle(boolean absoluteParticleAngle) {
	 * this.absoluteParticleAngle = absoluteParticleAngle; } //
	 */

	@Override
	public void setResolution(int resolution) {
		super.setResolution(resolution);
		speed.setResolution(resolution);
		life.setResolution(resolution);
		rate.setResolution(resolution);
		angleV.setResolution(resolution);
		angleH.setResolution(resolution);
		angleSpreadV.setResolution(resolution);
		angleSpreadH.setResolution(resolution);

	}

	public ValueListf getSpinH() {
		return spinH;
	}

	public void setSpinH(ValueListf spinH) {
		this.spinH = spinH;
	}

	public ValueListf getSpinV() {
		return spinV;
	}

	public void setSpinV(ValueListf spinV) {
		this.spinV = spinV;
	}

	public void reset() {
		oldSpinV = 0f;
		oldSpinH = 0f;
	}

	public void setRotation(Matrix4f rotation) {
		this.rotation = rotation;

	}

}
