package edu.gmu.c2sim.utils;

import org.uncommons.maths.random.GaussianGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;

public class RandomUtils {
	private MersenneTwisterRNG rng;
	private GaussianGenerator generator;
	
	public RandomUtils(double precision) {
		this.rng = new MersenneTwisterRNG();
		generator = new GaussianGenerator(precision, 0.9,rng);
	}
	
	public double nextValue() {
		double random = generator.nextValue();
		return random;
	}
	
}
