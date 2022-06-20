

	
DROP TABLE IF EXISTS weapon_type, sensor_type, entity_type, entity_model, exe, entity,order_plan  CASCADE;

--F22_MISSILE
CREATE TABLE IF NOT EXISTS weapon_type (
    weapon_type_id VARCHAR(25) PRIMARY KEY,
	w_type VARCHAR(25),
   	reusable bool,
	wp_range float8,
	domain VARCHAR(25),
	precision float8,
	intensity float8
);

--RQ170-MULTISPECTRAL
CREATE TABLE IF NOT EXISTS sensor_type (
    sensor_type_id VARCHAR(25) PRIMARY KEY,
   --	entity_model_id VARCHAR(25) references entity_model(entity_model_id),
	sens_range float8,
	domain VARCHAR(25),
	precision float8
);

--FIGHTER_5G
CREATE TABLE IF NOT EXISTS entity_type (
	entity_type_id VARCHAR(40) PRIMARY KEY,
	domain VARCHAR(25),
	image_file VARCHAR (70)
	);

-- F22
CREATE TABLE IF NOT EXISTS entity_model (
    entity_model_id VARCHAR(25) PRIMARY KEY,
    type_id VARCHAR(40) references entity_type(entity_type_id),
	pods_positions int,
	speed float8,
	climb_rate float8,
	combat_range_m float8,
	destructionFactor float8,
	neutralizationFactor float8,
	supressionFactor float8,
	vulnerabilityFactor float8
);

CREATE TABLE IF NOT EXISTS exe (
    exe_id VARCHAR(25) PRIMARY KEY,
  	simu_time bigint,
	speed_sim integer
);

-- 
CREATE TABLE IF NOT EXISTS entity (
    entity_id serial PRIMARY KEY,
    exe_id VARCHAR(25) references exe(exe_id),
	alias_name VARCHAR(30),
    model_id VARCHAR(25) references entity_model(entity_model_id),
	behavior VARCHAR(30),
    team VARCHAR(10),
	command VARCHAR(25),
	weapon_list VARCHAR(80),
	sensor VARCHAR(80),
	initial_position VARCHAR(80),
	initial_time INTEGER
);

CREATE TABLE IF NOT EXISTS order_plan (
  	order_id serial PRIMARY KEY,
    uri VARCHAR(25),
  	type VARCHAR(25),
  	actor VARCHAR(25),
  	target VARCHAR(25),
  	effect_time_duration bigint,
  	start_time bigint,
  	time_on_target bigint,
  	initial_position VARCHAR(25),
  	optional bool,
  	exe_id VARCHAR(25)
);


-- INSERT ENTITY TYPE
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('MEO', 'SPACE','meo.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('GEO', 'SPACE','geo.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('LEO', 'SPACE','leo.png');

INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('CRUISER', 'SEA','CRUISER.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('DESTROYER', 'SEA','DESTROYER.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('SMALL_UNMANNED_SURFACE', 'SEA','SMALL_UNMANNED_SURFACE.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('LARGE_UNMANNED_SURFACE', 'SEA','LARGE_UNMANNED_SURFACE.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('SUBMARINE', 'SEA','submarine.png');

INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('HQ', 'LAND','hq.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('HEAVY_COMBAT_PLATFORM', 'LAND','TankPlatoon.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('LIGHT_COMBAT_PLATFORM', 'LAND','InfantryCompany.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('LONGRANGE_PRECISION_FIRES', 'LAND','HEAVY_COMBAT_PLATFORM.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('LONGRANGE_INDIRECT_FIRES', 'LAND','LightFieldArtillery.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('INTEGRATED_AIR_DEFENSE_SYSTEM', 'LAND','ShortRangeAirDefense.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('ANTI_AIRCRAFT_ARTILLERY_VEHICLE', 'LAND','GunTruckmountedMG.png');

INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('FIGHTER_5G', 'AIR','FIGHTER_5G.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('AEW_ATTACK', 'AIR','AEW.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('UAS', 'AIR','AerialEWPlatform.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('HELI_ATTACK', 'AIR','HELI_ATTACK.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('HELI_HEAVY', 'AIR','HELI_HEAVY.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('BOMBER', 'AIR','BOMBER.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('FIGHTER_4G', 'AIR','FIGHTER_4G.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('REFUELING', 'AIR','REFUELING.png');

INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('CYBER_PACKAGE', 'CYBER','CYBER.png');
INSERT INTO entity_type(entity_type_id, domain, image_file) VALUES('C4ISR_NODE', 'CYBER','C4ISR.png');

-- INSERT WEAPON TYPE
INSERT INTO weapon_type(weapon_type_id,w_type, reusable, wp_range,domain,precision,intensity) VALUES('AIR_AIR_MISSILE_T1','MISSILE', false, 18000.0,'AIR',0.8,0.85);
INSERT INTO weapon_type(weapon_type_id, w_type,reusable, wp_range,domain,precision,intensity) VALUES('AIR_AIR_MISSILE_T2', 'MISSILE',false, 18000.0,'AIR',1.0,0.9);
INSERT INTO weapon_type(weapon_type_id,w_type, reusable, wp_range,domain,precision,intensity) VALUES('AIR_AIR_MISSILE_T3', 'MISSILE',false, 20000.0,'AIR',1.0,0.75);
INSERT INTO weapon_type(weapon_type_id,w_type, reusable, wp_range,domain,precision,intensity) VALUES('LAND_AIR_MISSILE_T1','MISSILE', false, 4000.0,'LAND',0.9,1.0);
INSERT INTO weapon_type(weapon_type_id, w_type,reusable, wp_range,domain,precision,intensity) VALUES('LAND_AIR_MISSILE_T2', 'MISSILE',false, 4000.0,'SEA',0.9,0.7);
INSERT INTO weapon_type(weapon_type_id,w_type, reusable, wp_range,domain,precision,intensity) VALUES('ANTISHIPMISSILE_T1', 'MISSILE',false, 250000.0,'AIR',0.9,0.9);
INSERT INTO weapon_type(weapon_type_id,w_type, reusable, wp_range,domain,precision,intensity) VALUES('ANTISHIPMISSILE_T2', 'MISSILE',false, 280000.0,'SEA',1.0,0.9);
INSERT INTO weapon_type(weapon_type_id,w_type, reusable, wp_range,domain,precision,intensity) VALUES('BOMB_T1', 'BOMB',false, 28000.0,'AIR',1.0,0.9);
INSERT INTO weapon_type(weapon_type_id, w_type,reusable, wp_range,domain,precision,intensity) VALUES('BOMB_T2', 'BOMB',false, 26000.0,'AIR',1.0,0.85);
INSERT INTO weapon_type(weapon_type_id,w_type, reusable, wp_range,domain,precision,intensity) VALUES('BOMB_T3', 'BOMB',false, 18000.0,'AIR',1.0,1.0);
INSERT INTO weapon_type(weapon_type_id,w_type, reusable, wp_range,domain,precision,intensity) VALUES('LASERBOMB_T1','BOMB', false, 28000.0,'AIR',1.0,0.9);
INSERT INTO weapon_type(weapon_type_id,w_type, reusable, wp_range,domain,precision,intensity) VALUES('GPSBOMB_T1', 'BOMB',false, 38000.0,'AIR',1.0,0.9);
INSERT INTO weapon_type(weapon_type_id, w_type,reusable, wp_range,domain,precision,intensity) VALUES('AIR_CANNON_T1', 'CANNON',true, 1000.0,'AIR',1.0,0.75);
INSERT INTO weapon_type(weapon_type_id, w_type,reusable, wp_range,domain,precision,intensity) VALUES('AIR_CANNON_T2','CANNON', true, 2000.0,'AIR',0.8,0.8);
INSERT INTO weapon_type(weapon_type_id, w_type,reusable, wp_range,domain,precision,intensity) VALUES('GROUND_AIR_CANNON_T1','CANNON', true, 3000.0,'SEA',0.95,1.0);
INSERT INTO weapon_type(weapon_type_id, w_type,reusable, wp_range,domain,precision,intensity) VALUES('GROUND_AIR_CANNON_T2','CANNON', true, 2500.0,'LAND',1.0,0.9);
INSERT INTO weapon_type(weapon_type_id, w_type,reusable, wp_range,domain,precision,intensity) VALUES('JAMMING_T1', 'EW',true, 75000.0,'AIR',1.0,0.8);
INSERT INTO weapon_type(weapon_type_id, w_type,reusable, wp_range,domain,precision,intensity) VALUES('JAMMING_T2', 'EW',true, 75000.0,'SEA',1.0,0.8);
INSERT INTO weapon_type(weapon_type_id, w_type,reusable, wp_range,domain,precision,intensity) VALUES('JAMMING_T3', 'EW',true, 75000.0,'LAND',1.0,0.8);
INSERT INTO weapon_type(weapon_type_id, w_type,reusable, wp_range,domain,precision,intensity) VALUES('JAMMING_T4','EW', true,0.0,'SPACE',1.0,0.8);


-- INSERT SENSOR TYPE
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('AIR_RADAR_T1', 50000.0,'AIR',0.9);
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('AIR_RADAR_T2', 100000.0,'AIR',1.0);
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('AIR_RADAR_T3', 18000.0,'AIR',0.8);
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('LAND_RADAR_T1', 400000.0,'LAND',0.8);
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('LAND_RADAR_T2', 200000.0,'LAND',1.0);
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('CYBER_T1', 0.0,'CYBER',1.0);
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('CYBER_T2', 0.0,'CYBER',0.9);
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('CYBER_T3', 0.0,'CYBER',0.7);
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('SEA_RADAR_T1', 500000.0,'SEA',0.6);
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('SEA_RADAR_T2', 400000.0,'SEA',0.8);
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('SEA_RADAR_T3', 200000.0,'SEA',1.0);
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('SAT_RADAR_T1', 0.0,'SPACE',1.0);
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('SAT_RADAR_T2', 0.0,'SPACE',0.9);
INSERT INTO sensor_type(sensor_type_id, sens_range, domain, precision) VALUES('SAT_RADAR_T3', 0.0,'SPACE',0.8);

-- INSERT ENTITY MODEL
INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('SAT_MEO', 'MEO',6,7777.8,0.0,-1.0,0.8,0.5,0.2,0.9);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('SAT_GEO', 'GEO',6,7777.8,0.0,-1.0,0.8,0.5,0.2,0.7);
						 
INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('SAT_LEO', 'LEO',6,7777.8,0.0,-1.0,0.8,0.5,0.2,1.0);
						 
INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('CRUISER', 'CRUISER',10,16.8,0.0,-1.0,0.8,0.7,0.8,0.5);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('DESTROYER', 'DESTROYER',10,15.8,0.0,-1.0,0.7,0.5,0.2,0.9);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('SMALL_UNMANED_SHIP', 'SMALL_UNMANNED_SURFACE',5,13.9,0.0,-1.0,0.6,0.3,0.2,0.7);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('LARGE_UNMANED_SHIP', 'LARGE_UNMANNED_SURFACE',5,15.8,0.0,-1.0,0.6,0.3,0.2,0.8);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('SUBMARINE', 'SUBMARINE',5,12.8,0.0,-1.0,0.6,0.4,0.2,0.8);
				
INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('HQ', 'HQ',0,0.0,0.0,0.0,0.0,0.0,0.0,0.9);
				
INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('HEAVY_PLATFORM', 'HEAVY_COMBAT_PLATFORM',5,18.0,0.0,-1.0,0.8,0.5,0.2,0.9);				

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('LIGHT_PLATFORM', 'LIGHT_COMBAT_PLATFORM',5,22.0,0.0,-1.0,0.7,0.5,0.2,0.9);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('LONGRANGE_PREC_FIRES', 'LONGRANGE_PRECISION_FIRES',5,18.0,0.0,-1.0,0.7,0.5,0.2,0.9);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('LONGRANGE_IND_FIRES', 'LONGRANGE_INDIRECT_FIRES',5,5.0,0.0,-1.0,0.7,0.5,0.2,0.9);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('AIR_DEFENS_SYSTEM', 'INTEGRATED_AIR_DEFENSE_SYSTEM',5,5.0,0.0,-1.0,0.5,0.3,0.1,0.6);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('AIRCR_ARTILLERY', 'ANTI_AIRCRAFT_ARTILLERY_VEHICLE',5,13.9,0.0,-1.0,0.6,0.4,0.1,0.8);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('F5G', 'FIGHTER_5G',6,670.5,350.0,850000.0,0.6,0.3,0.2,0.7);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('F4G', 'FIGHTER_4G',6,694.0,208.0,1450000.0,0.0,0.1,0.2,0.8);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('AEW', 'AEW_ATTACK',4,527.48,100.0,722000.0,0.6,0.3,0.2,0.7);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('UAS', 'UAS',4,45.8,100.0,110000.0,0.6,0.3,0.2,0.7);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('HEL_AT', 'HELI_ATTACK',4,78.71,5.01,707000.0,0.8,0.6,0.6,0.6);
						 
INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('HEL_TRANSP', 'HELI_HEAVY',2,77.0,5.01,330000.0,0.4,0.2,0.1,0.5);
						 
INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('BOMBER', 'BOMBER',5,313.0,100.0,5543000.0,0.6,0.3,0.2,0.7);


INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('REFUEL', 'REFUELING',0,259.0,100.0,2500000.0,0.3,0.3,0.2,0.9);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('CYBER', 'CYBER_PACKAGE',0.0,-1.0,0.0,-1.0,0.6,0.4,0.1,0.8);

INSERT INTO entity_model(entity_model_id, type_id, pods_positions,speed,
						 climb_rate,combat_range_m,destructionFactor,neutralizationFactor,supressionFactor,
						 vulnerabilityFactor) VALUES('C4ISR0', 'C4ISR_NODE',0.0,-1.0,0.0,-1.0,0.3,0.2,0.1,0.4);



-- sql CREATE ENTITIES

--INSERT INTO entity(alias_name, model_id,team,command,weapon_list,sensor,initial_position, initial_time) VALUES('FORCE1','F22','BLUE','MARITIMEVL','1','1','1',0);


