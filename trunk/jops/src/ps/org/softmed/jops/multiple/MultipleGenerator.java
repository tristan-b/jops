package org.softmed.jops.multiple;

import java.util.ArrayList;
import java.util.List;

import org.softmed.jops.Particle;
import org.softmed.jops.random.RandomGenerator;
import org.softmed.jops.space.GeneratorSpace;


public class MultipleGenerator extends GeneratorSpace{
List<GeneratorSpace> generators = new ArrayList<GeneratorSpace>();
private int index;
private GeneratorSpace gen;

@Override
public void generate(Particle part) {
	index = generator.getInt(generators.size());
	gen = generators.get(index);
	gen.generate(part);
}

public List<GeneratorSpace> getGenerators() {
	return generators;
}

@Override
public void update(float life) {
	// TODO Auto-generated method stub
	
}

@Override
public void recompile() {
	
	if(generator == null)
		generator = new RandomGenerator();

	for (GeneratorSpace space : generators) {
		space.recompile();
	}
	
}

@Override
public void setResolution(int resolution) {
	for (GeneratorSpace space : generators) {
		space.setResolution(resolution);
	}
	
}

@Override
public void reset() {
	// TODO Auto-generated method stub
	
}
}
