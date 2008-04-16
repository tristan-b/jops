package org.softmed.jops.random;

import java.util.Random;

public class PseudoRandomGenerator {

	int amount;

	float floats[];

	Random gen;

	int count;

	public PseudoRandomGenerator() {
		count = 0;
		setup(0);
		count = gen.nextInt(amount);
	}

	public PseudoRandomGenerator(long seed) {
		count = 0;
		setup(seed);
		count = gen.nextInt(amount);
	}

	protected void setup(long seed) {

		amount = 0xf4240;
		floats = new float[amount];

		gen = new Random(seed);

		for (int i = 0; i < floats.length; i++)
			floats[i] = gen.nextFloat();

	}

	
	public float nextFloat() {
		count++;
		if (count == amount)
			count = gen.nextInt(amount);
		return floats[count];
	}

	public int getInt(int x) {
		float y = nextFloat();
		y *= x;
		return (int) y;
	}

	public float getFloat(float x) {
		return nextFloat() * x;
	}

	public boolean nextBoolean() {
		return (double) (nextFloat() * 1.0F) > 0.5D;
	}

}
