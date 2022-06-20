package edu.gmu.c2sim.utils;

import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

import edu.gmu.c2sim.core.distributions.BetaDistributionGenerator;
import edu.gmu.c2sim.core.distributions.ExponentialDistributionGenerator;
import edu.gmu.c2sim.core.distributions.GaussianDistributionGenerator;
import edu.gmu.c2sim.core.distributions.IProbabilityGenerator;
import edu.gmu.c2sim.core.distributions.IProbabilityGenerator.DISTRIBUTION_TYPE;
import edu.gmu.c2sim.core.distributions.PoissonDistributionGenerator;
import edu.gmu.c2sim.core.distributions.WeibullDistributionGenerator;
import edu.gmu.c2sim.core.distributions.exceptions.InvalidValueException;

public class SampleUtils {

	public static float[] generateSamples(IProbabilityGenerator.DISTRIBUTION_TYPE distType, double[] params,
			int number_samples, int number_states) throws InvalidValueException {

		if (number_samples % number_states != 0) {
			throw new InvalidValueException("number of samples needs to be divisible by number of states");
		}

		else {
			
			if (distType==DISTRIBUTION_TYPE.EXPONENTIAL) {
				Random rng = new Random();

				double rate = params[0];
				
				return generateExponentialSamples(rate, number_samples, number_states, rng);
			}
			
			else if (distType==DISTRIBUTION_TYPE.BETA) {
				Random rng = new Random();

				double alpha = params[0];
				double beta = params[1];

				return generateBetaSamples(alpha, beta, number_samples, number_states, rng);
			}
			
			else if (distType==DISTRIBUTION_TYPE.GAUSSIAN) {
				Random rng = new Random();

				double mean = params[0];
				double stdv = params[1];

				return generateGaussianSamples(mean, stdv, number_samples, number_states, rng);
			}
			
			else if (distType==DISTRIBUTION_TYPE.POISSON) {
				Random rng = new Random();

				double p = params[0];
				
				return generatePoissonSamples(p, number_samples, number_states, rng);
			}
			
			else if (distType==DISTRIBUTION_TYPE.WEIBULL) {
				Random rng = new Random();

				double alpha = params[0];
				double beta = params[1];

				return generateWeibullSamples(alpha, beta, number_samples, number_states, rng);
			}
			
			else {
				return null;
			}
		}

		
	}

	private static float[] generateWeibullSamples(double alpha, double beta, int number_samples, int number_states,
			Random rng) {
		float[] samples = new float[number_samples];
		WeibullDistributionGenerator wbGen = null;
		try {
			wbGen = new WeibullDistributionGenerator(alpha, beta);
			List<Double> values = wbGen.generateSamples(number_samples, rng);

			for (int count = 0, i = 0; count < number_samples; i++) {
				float root_sample = new Float(values.get(i)).floatValue();
				float[] intermed_samples = derivate(root_sample, number_states);
				for (int j = 0; j < number_states; j++) {
					samples[count] = intermed_samples[j];
					count++;
				}
			}

		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
		return samples;
	}

	private static float[] generatePoissonSamples(double p, int number_samples, int number_states, Random rng) {
		float[] samples = new float[number_samples];
		PoissonDistributionGenerator poisGen = null;
		try {
			poisGen = new PoissonDistributionGenerator(p);
			List<Double> values = poisGen.generateSamples(number_samples, rng);

			for (int count = 0, i = 0; count < number_samples; i++) {
				float root_sample = new Float(values.get(i)).floatValue();
				float[] intermed_samples = derivate(root_sample, number_states);
				for (int j = 0; j < number_states; j++) {
					samples[count] = intermed_samples[j];
					count++;
				}
			}

		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
		return samples;
	}

	private static float[] generateGaussianSamples(double mean, double stdv, int number_samples, int number_states,
			Random rng) {
		float[] samples = new float[number_samples];
		GaussianDistributionGenerator gausGen = null;
		try {
			gausGen = new GaussianDistributionGenerator(mean, stdv);
			List<Double> values = gausGen.generateSamples(number_samples, rng);

			for (int count = 0, i = 0; count < number_samples; i++) {
				float root_sample = new Float(values.get(i)).floatValue();
				float[] intermed_samples = derivate(root_sample, number_states);
				for (int j = 0; j < number_states; j++) {
					samples[count] = intermed_samples[j];
					count++;
				}
			}

		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
		return samples;
	}

	private static float[] generateBetaSamples(double alpha, double beta,int number_samples, int number_states,
			Random rng) {
		float[] samples = new float[number_samples];
		BetaDistributionGenerator betaGen = null;
		try {
			betaGen = new BetaDistributionGenerator(alpha, beta);
			List<Double> values = betaGen.generateSamples(number_samples, rng);

			for (int count = 0, i = 0; count < number_samples; i++) {
				float root_sample = new Float(values.get(i)).floatValue();
				float[] intermed_samples = derivate(root_sample, number_states);
				for (int j = 0; j < number_states; j++) {
					samples[count] = intermed_samples[j];
					count++;
				}
			}

		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
		return samples;
	}
	
	
	private static float[] generateExponentialSamples(double rate, int sampleNumber, int number_stages,
			Random rng) {
		float[] samples = new float[sampleNumber];
		ExponentialDistributionGenerator expoGen = null;
		try {
			expoGen = new ExponentialDistributionGenerator(rate);
			List<Double> values = expoGen.generateSamples(sampleNumber, rng);

			for (int count = 0, i = 0; count < sampleNumber; i++) {
				float root_sample = new Float(values.get(i)).floatValue();
				float[] intermed_samples = derivate(root_sample, number_stages);
				for (int j = 0; j < number_stages; j++) {
					samples[count] = intermed_samples[j];
					count++;
				}
			}

		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
		return samples;
	}


	private static float[] derivate(float root_sample, int number_samples) {
		float[] samples = new float[number_samples];
		RandomGenerator rngen = new JDKRandomGenerator(new Random().nextInt());

		samples[0] = root_sample;
		float max = 1 - samples[0];
		float min = 0;

		for (int i = 1; i < number_samples - 1; i++) {
			samples[i] = rngen.nextFloat() * (max - min);
			max = max - samples[i];

			if (max < 0) {
				for (int j = i; j < number_samples - 1; j++) {
					samples[j] = 0;
				}
			}
		}

		samples[number_samples - 1] = max;

		return samples;
	}

}
