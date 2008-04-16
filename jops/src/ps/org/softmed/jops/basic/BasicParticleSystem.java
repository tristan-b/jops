package org.softmed.jops.basic;

import org.softmed.jops.Generator;
import org.softmed.jops.ParticleSystem;

public class BasicParticleSystem extends ParticleSystem{

	
	public BasicParticleSystem()
	{
		//GeneratorSpace space = new BoxGenerator();
		//spaces.add(space);
		Generator gen = new BasicGenerator();
		//gen.setSpace(space);
		generators.add(gen);
		spaces.add(gen.getSpace());
		genBehaviours.add(gen.getGb());
		
		
		/*
		PointMass pm1 = new PointMass();
		pm1.getStrength().addValue(1.8f,0f);
		gen.getModifiers().add(pm1);
		modifiers.add(pm1);
		//*/
		
		/*
		PointMass pm2 = new PointMass();
		SpaceAnimator animator = new SpaceAnimator();
		animator.getBias().addValue(new Vector3f(0f,0f,0.5f), 0f);
		animator.getBiasSpin().addValue(new Vector3f(0f,180f,0.5f), 0f);
		pm2.setAnimator(animator);
		animators.add(animator);
		pm2.getStrength().addValue(1.8f,0f);
		gen.getModifiers().add(pm2);
		modifiers.add(pm2);
		//*/
		
		/*
		PointMass pm3 = new PointMass();
		pm3.getPosition().addValue(new Vector3f(0f,0f,0.5f), 0f);
		pm3.getStrength().addValue(1.8f,0f);
		gen.getModifiers().add(pm3);
		modifiers.add(pm3);
		
	
		
		
	
		 
/*
		AirFriction gf = new AirFriction();
		gen.getModifiers().add(gf);
		modifiers.add(gf);
//*/
		/*
		GenericForce gf2 = new GenericForce();
		gen.getModifiers().add(gf2);
		modifiers.add(gf2);
		//*/
		
		
		
		
		rebuild();
	}
	
}

