package edu.gmu.c2sim.utils;

//import static org.junit.Assert.assertThrows;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import edu.gmu.c2sim.core.distributions.IProbabilityGenerator.DISTRIBUTION_TYPE;
import edu.gmu.c2sim.core.distributions.exceptions.InvalidValueException;

class SampleUtilsTestCases {
	@Test
	void testWeibullGenerator()  {
		double alpha =4.0;
		double beta = 4.0;
		double [] params = new double [2];
		params[0]=alpha;
		params[1]=beta;
		int number_samples = 120;
		int num_states = 3;
		float[] values;
		try {
			values = SampleUtils.generateSamples(DISTRIBUTION_TYPE.WEIBULL, params, number_samples, num_states);
			
			for (int i=0; i<number_samples;) {
				float total = 0;
				for (int j=0; j<num_states;j++) {
					float value = values[i];
					total = total+value;
					i++;
				}
				Assert.assertEquals(total, 1.0,0.0001);
			}
		
		} catch (InvalidValueException e) {
			Assert.fail(e.getMessage());
		}	
	}
	
	@Test
	void testWeibullGeneratorException()  {
		double alpha =4.0;
		double beta = 4.0;
		double [] params = new double [2];
		params[0]=alpha;
		params[1]=beta;
		int number_samples2 = 100;
		int num_states2 = 3;
	//	assertThrows(InvalidValueException.class, () -> SampleUtils.generateSamples(DISTRIBUTION_TYPE.WEIBULL, params, number_samples2, num_states2));
		
	}
	
	@Test
	void testPoissonGenerator()  {
		double p =4.0;
		double [] params = new double [1];
		params[0]=p;
		int number_samples = 120;
		int num_states = 3;
		float[] values;
		try {
			values = SampleUtils.generateSamples(DISTRIBUTION_TYPE.POISSON, params, number_samples, num_states);
			
			for (int i=0; i<number_samples;) {
				float total = 0;
				for (int j=0; j<num_states;j++) {
					float value = values[i];
					total = total+value;
					i++;
				}
				Assert.assertEquals(total, 1.0,0.0001);
			}
		
		} catch (InvalidValueException e) {
			Assert.fail(e.getMessage());
		}	
	}
	
	@Test
	void testPoissonGeneratorException()  {
		double p =4.0;
		double [] params = new double [1];
		params[0]=p;
		int number_samples2 = 100;
		int num_states2 = 3;
		//assertThrows(InvalidValueException.class, () -> SampleUtils.generateSamples(DISTRIBUTION_TYPE.POISSON, params, number_samples2, num_states2));
		
	}
	
	@Test
	void testGaussianGenerator()  {
		double mean =4.0;
		double std = 4.0;
		double [] params = new double [2];
		params[0]=mean;
		params[1]=std;
		int number_samples = 120;
		int num_states = 3;
		float[] values;
		try {
			values = SampleUtils.generateSamples(DISTRIBUTION_TYPE.GAUSSIAN, params, number_samples, num_states);
			
			for (int i=0; i<number_samples;) {
				float total = 0;
				for (int j=0; j<num_states;j++) {
					float value = values[i];
					total = total+value;
					i++;
				}
				Assert.assertEquals(total, 1.0,0.0001);
			}
		
		} catch (InvalidValueException e) {
			Assert.fail(e.getMessage());
		}	
	}
	
	@Test
	void testGaussianGeneratorException()  {
		double mean =4.0;
		double std = 4.0;
		double [] params = new double [2];
		params[0]=mean;
		params[1]=std;
		int number_samples2 = 100;
		int num_states2 = 3;
	//	assertThrows(InvalidValueException.class, () -> SampleUtils.generateSamples(DISTRIBUTION_TYPE.GAUSSIAN, params, number_samples2, num_states2));
		
	}
	
	@Test
	void testExponentialGenerator()  {
		double rate =4.0;
		double [] params = new double [1];
		params[0]=rate;
		int number_samples = 120;
		int num_states = 3;
		float[] values;
		try {
			values = SampleUtils.generateSamples(DISTRIBUTION_TYPE.EXPONENTIAL, params, number_samples, num_states);
			
			for (int i=0; i<number_samples;) {
				float total = 0;
				for (int j=0; j<num_states;j++) {
					float value = values[i];
					total = total+value;
					i++;
				}
				Assert.assertEquals(total, 1.0,0.0001);
			}
		
		} catch (InvalidValueException e) {
			Assert.fail(e.getMessage());
		}	
	}
	
	@Test
	void testExponentialGeneratorException()  {
		double rate =4.0;
		double [] params = new double [21];
		params[0]=rate;
		int number_samples2 = 100;
		int num_states2 = 3;
//		assertThrows(InvalidValueException.class, () -> SampleUtils.generateSamples(DISTRIBUTION_TYPE.EXPONENTIAL, params, number_samples2, num_states2));
		
	}
	

	@Test
	void testBetaGenerator()  {
		double alpha =5.0;
		double beta = 7.0;
		double [] params = new double [2];
		params[0]=alpha;
		params[1]=beta;
		int number_samples = 120;
		int num_states = 3;
		float[] values;
		try {
			values = SampleUtils.generateSamples(DISTRIBUTION_TYPE.BETA, params, number_samples, num_states);
			
			for (int i=0; i<number_samples;) {
				float total = 0;
				for (int j=0; j<num_states;j++) {
					float value = values[i];
					total = total+value;
					i++;
				}
				Assert.assertEquals(total, 1.0,0.0001);
			}
		
		} catch (InvalidValueException e) {
			Assert.fail(e.getMessage());
		}	
	}
	
	@Test
	void testBetaGeneratorException()  {
		double alpha =5.0;
		double beta = 7.0;
		double [] params = new double [2];
		params[0]=alpha;
		params[1]=beta;
		int number_samples2 = 100;
		int num_states2 = 3;
//		assertThrows(InvalidValueException.class, () -> SampleUtils.generateSamples(DISTRIBUTION_TYPE.BETA, params, number_samples2, num_states2));
	}

}
