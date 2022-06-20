package edu.gmu.c2sim.core.physics;

import org.uncommons.maths.random.GaussianGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;


public class WindEffector implements IPhysicsEffector {
	/*
	 * bibTex:@article{ganea2019evaluation, title={An evaluation of the wind and
	 * wave dynamics along the European Coasts}, author={Ganea, Daniel and Mereuta,
	 * Elena and Rusu, Eugen}, journal={Journal of Marine Science and Engineering},
	 * volume={7}, number={2}, pages={43}, year={2019}, publisher={Multidisciplinary
	 * Digital Publishing Institute} }
	 */
	private static final String scale = "Beaufort";
	private String conditionName;
	private int conditionIndex;
	private GaussianGenerator generator;
	private double windSpeed = 0.0;
	private MersenneTwisterRNG rng;

	public static WindEffector instance;

	private WindEffector() {
		rng = new MersenneTwisterRNG();
	}

	public static WindEffector getInstance() {
		if (instance == null)
			instance = new WindEffector();
		return instance;
	}

	@Override
	public void configure(String dist, double[] params) {
		if (dist.equals("Gaussian")) {
			double mean = params[0];
			double stdv = params[1];
			
			generator = new GaussianGenerator(mean, stdv, rng);
		}
		
		update();
		System.out.println("wind speed starts: " + conditionName);
	}

	@Override
	public String getConditionName() {
		return conditionName;
	}

	public String getScale() {
		return scale;
	}

	@Override
	public int getConditionIndex() {
		return conditionIndex;
	}

	/*
	 * source: Figure 12 Bibtex:
	 * 
	 * @article{kim2017estimation, title={Estimation of added resistance and ship
	 * speed loss in a seaway}, author={Kim, Mingyu and Hizir, Olgun and Turan,
	 * Osman and Day, Sandy and Incecik, Atilla}, journal={Ocean Engineering},
	 * volume={141}, pages={465--476}, year={2017}, publisher={Elsevier} }
	 **/
	public double getShipSpeedReduction(String shipType) {
		if (shipType.equals("SUBMARINE")) {
			if (conditionIndex >= 7)
				return 3.5;
			else if (conditionIndex == 6)
				return 3.0;
			else if (conditionIndex == 5)
				return 2.8;
			else if (conditionIndex == 4)
				return 1.5;
			else
				return 0.5;
		}

		else if (shipType.equals("SMALL_UNMANNED_SURFACE")) {
			if (conditionIndex == 6)
				return 3.5;
			else if (conditionIndex == 5)
				return 3.0;
			else if (conditionIndex == 4)
				return 2.8;
			else if (conditionIndex < 4)
				return 1.5;
			else
				return -1;
		}

		else {
			if (conditionIndex == 7)
				return 3.5;
			else if (conditionIndex == 6)
				return 3.0;
			else if (conditionIndex == 5)
				return 2.8;
			else if (conditionIndex == 4)
				return 1.5;
			else if (conditionIndex < 4)
				return 0.5;
			else
				return -1;
		}
	}

	@Override
	public double getFlightSpeedReduction(String type) {
		if (type.equals("FIGHTER_5G") || type.equals("FIGHTER_4G") || type.equals("AEW_ATTACK") || type.equals("BOMBER")) {
			if (conditionIndex == 7)
				return 3.5;
			else if (conditionIndex == 6)
				return 3.0;
			else if (conditionIndex == 5)
				return 2.8;
			else if (conditionIndex == 4)
				return 1.5;
			else if (conditionIndex < 4)
				return 0.5;
	
		} else {
			if (conditionIndex == 6)
				return 3.5;
			else if (conditionIndex == 5)
				return 3.0;
			else if (conditionIndex == 4)
				return 2.8;
			else if (conditionIndex < 4)
				return 1.5;
		}
		return 0;
	}

	@Override
	public double getGroundSpeedReduction() {
		if (conditionIndex > 7 && conditionIndex < 10)
			return 3.9;
		else if (conditionIndex == 7)
			return 3.5;
		else if (conditionIndex == 6)
			return 3.0;
		else if (conditionIndex == 5)
			return 2.8;
		else if (conditionIndex == 4)
			return 1.5;
		else if (conditionIndex < 4)
			return 0.5;
		else
			return -1;
	}

	private void parseConditionValue(double value) {
		if (value <= 0.5) {
			conditionName = "Calm";
			conditionIndex = 0;
		}

		else if (value > 0.5 && value <= 1.5) {
			conditionName = "LightAir";
			conditionIndex = 1;
		}

		else if (value > 1.5 && value <= 3.3) {
			conditionName = "LightBreeze";
			conditionIndex = 2;
		}

		else if (value > 3.3 && value <= 5.5) {
			conditionName = "GentleBreeze";
			conditionIndex = 3;
		}

		else if (value > 5.5 && value <= 7.9) {
			conditionName = "ModerateBreeze";
			conditionIndex = 4;
		}

		else if (value > 7.9 && value <= 10.7) {
			conditionName = "FreshBreeze";
			conditionIndex = 5;
		}

		else if (value > 10.7 && value <= 13.8) {
			conditionName = "StrongBreeze";
			conditionIndex = 6;
		}

		else if (value > 13.8 && value <= 17.1) {
			conditionName = "HighWind";
			conditionIndex = 7;
		}

		else if (value > 17.1 && value <= 20.7) {
			conditionName = "Gale";
			conditionIndex = 8;
		}

		else if (value > 20.7 && value <= 24.4) {
			conditionName = "SevereGale";
			conditionIndex = 9;
		} else if (value > 24.4 && value <= 28.4) {
			conditionName = "Storm";
			conditionIndex = 10;
		}

		else if (value > 28.4 && value <= 32.6) {
			conditionName = "ViolentStorm";
			conditionIndex = 11;
		}

		else {
			conditionName = "Hurricane";
			conditionIndex = 12;
		}
	}

	@Override
	public void update() {
		double value = generator.nextValue();
		int random = rng.nextInt();
		if (random % 2 == 0)
			windSpeed = windSpeed + value / 10;
		else
			windSpeed = windSpeed - value / 10;

		if (windSpeed < 0)
			windSpeed = 0;
		parseConditionValue(windSpeed);
	}

}
