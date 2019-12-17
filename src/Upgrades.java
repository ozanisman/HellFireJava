import java.awt.image.BufferedImage;

public class Upgrades {
	
	private HellFireGame game;
	private Textures texture;
	private static int upgradesBought = 0;
	
	private Upgrade[] blastDamage;
	private Upgrade[] blastFirerate;
	private Upgrade[] missileDamage;
	private Upgrade[] missileFirerate;
	private Upgrade[] missileExplosionRadius;
	private Upgrade[] missileExplosionDamage;
	private Upgrade[] laserDamage;
	private Upgrade[] dualShot;
	private Upgrade[] shieldCooldown;
	private Upgrade[] shieldDuration;
	private Upgrade[] shipHealth;
	private Upgrade[] damageReduction;
	private Upgrade[] powerupDrop;
	private Upgrade[] moneyBonus;
	private Upgrade[] damageBonus;
	
	private BufferedImage[] blastType;
	private BufferedImage[] missileType;
	private BufferedImage[][] laserType;
	
	public Upgrades(HellFireGame game, Textures texture) {
		this.game = game;
		this.texture = texture;
		blastDamage = new Upgrade[] {new Upgrade(game, 10, 200, "Increases Blast Damage", 0, 4), new Upgrade(game, 15, 500, "Increases Blast Damage", 1, 4), new Upgrade(game, 20, 1000, "Increases Blast Damage", 2, 4), new Upgrade(game, 25, 1500, "Increases Blast Damage", 3, 4), new Upgrade(game, 30, 0, "Blast Damage Maxed", 4, 4)};
		blastFirerate = new Upgrade[] {new Upgrade(game, 40, 200, "Increases Blast Firerate", 0, 4), new Upgrade(game, 35, 500, "Increases Blast Firerate", 1, 4), new Upgrade(game, 30, 1500, "Increases Blast Firerate", 2, 4), new Upgrade(game, 25, 2000, "Increases Blast Firerate", 3, 4), new Upgrade(game, 20, 0, "Blast Firerate Maxed", 4, 4)};
		missileDamage = new Upgrade[] {new Upgrade(game, 0, 350, "Unlocks A Slow Firing Missile With Heavy Damage", 0, 5), new Upgrade(game, 70, 500, "Increases Missile Damage", 1, 5), new Upgrade(game, 100, 1000, "Increases Missile Damage", 2, 5), new Upgrade(game, 120, 1000, "Increases Missile Damage", 3, 5), new Upgrade(game, 140, 2000, "Increases Missile Damage", 4, 5), new Upgrade(game, 160, 0, "Missile Damage Maxed", 5, 5)};
		missileFirerate = new Upgrade[] {new Upgrade(game, 0, 0, "Purchase The Missile To Unlock This Upgrade", 0, 4), new Upgrade(game, 120, 300, "Increases Missile Firerate", 0, 4), new Upgrade(game, 110, 500, "Increases Missile Firerate", 1, 4), new Upgrade(game, 100, 1000, "Increases Missile Firerate", 2, 4), new Upgrade(game, 90, 1500, "Increases Missile Firerate", 3, 4), new Upgrade(game, 80, 0, "Missile Firerate Maxed", 4, 4)};
		missileExplosionDamage = new Upgrade[] {new Upgrade(game, 0, 0, "Purchase The Missile To Unlock This Upgrade", 0, 3), new Upgrade(game, 0, 500, "Missile Exploads On Impact Damaging Enemies", 0, 3), new Upgrade(game, 10, 500, "Increases Missile Explosion Damage", 1, 3), new Upgrade(game, 15, 1000, "Increases Missile Explosion Damage", 2, 3), new Upgrade(game, 20, 0, "Missile Explosion Damage Maxed", 3, 3)};
		missileExplosionRadius = new Upgrade[] {new Upgrade(game, 0, 0, "Purchase The Missile To Unlock This Upgrade", 0, 3), new Upgrade(game, -1, 0, "Purchase Missile Explosion to Unlock This Upgrade", 0, 3), new Upgrade(game, 100, 500, "Increases Missile Explosion Radius", 0, 3), new Upgrade(game, 125, 750, "Increases Missile Explosion Radius", 1, 3), new Upgrade(game, 150, 1000, "Increases Missile Explosion Radius", 2, 3), new Upgrade(game, 175, 0, "Missile Explosion Radius Maxed", 3, 3)};
		laserDamage = new Upgrade[] {new Upgrade(game, 0, 500, "Unlocks Laser That Damages Anything In Its Path", 0, 5), new Upgrade (game, 5, 750, "Increases Laser Damage", 1, 5), new Upgrade(game, 7, 1000, "Increases Laser Damage", 2, 5), new Upgrade(game, 10, 1500, "Increases Laser Damage", 3, 5), new Upgrade(game, 15, 2500, "Increases Laser Damage", 4, 5), new Upgrade(game, 20, 0, "Laser Damage Maxed", 5, 5)};
		dualShot = new Upgrade[] {new Upgrade(game, 0, 1000, "Shoot Two Blasts At A Time Instead Of One", 0, 2), new Upgrade(game, 1, 0, "Purchase The Missile To Unlock This Upgrade", 1, 2), new Upgrade(game, 2, 2000, "Shoot Two Missiles At A Time Instead of One", 1, 2), new Upgrade(game, 3, 0, "Dual Fire Maxed", 2, 2)};
		shieldCooldown = new Upgrade[] {new Upgrade(game, 0, 200, "Unlocks A Shield That Blocks All Damage Periodically", 0, 7), new Upgrade(game, 60, 250, "Decreases Shield Cooldown", 1, 7), new Upgrade(game, 55, 300, "Decreases Shield Cooldown", 2, 7), new Upgrade(game, 50, 400, "Decreases Shield Cooldown", 3, 7), new Upgrade(game, 45, 500, "Decreases Shield Cooldown", 4, 7), new Upgrade(game, 40, 600, "Decreases Shield Cooldown", 5, 7), new Upgrade(game, 25, 750, "Decreases Shield Cooldown", 6, 7), new Upgrade(game, 30, 0, "Shield Cooldown Maxed", 7, 7)};
		shieldDuration = new Upgrade[] {new Upgrade(game, 0, 0, "Purchase The Shield To Unlock This Upgrade", 0, 5), new Upgrade(game, 5, 200, "Increases Shield Duration", 0, 5), new Upgrade(game, 6, 350, "Increases Shield Duration", 1, 5), new Upgrade(game, 7, 500, "Increases Shield Duration", 2, 5), new Upgrade(game, 8, 650, "Increases Shield Duration", 3, 5), new Upgrade(game, 9, 800, "Increases Shield Duration", 4, 5), new Upgrade(game, 10, 0, "Shield Duration Maxed", 5, 5)};
		shipHealth = new Upgrade[] {new Upgrade(game, 100, 300, "Increases Ship's Armor", 0, 6), new Upgrade(game, 200, 500, "Increases Ship's Armor", 1, 6), new Upgrade(game, 300, 700, "Increases Ship's Armor", 2, 6), new Upgrade(game, 400, 1000, "Increases Ship's Armor", 3, 6), new Upgrade(game, 500, 1200, "Increases Ship's Armor", 4, 6), new Upgrade(game, 600, 1500, "Increases Ship's Armor", 5, 6), new Upgrade(game, 700, 0, "Ship Armor Maxed", 6, 6)};
		damageReduction = new Upgrade[] {new Upgrade(game, 0, 700, "Reduces A Percentage Of Damage Taken", 0, 3), new Upgrade(game, 0.05, 1000, "Increases Percent Reduction Of Damage Taken", 1, 3), new Upgrade(game, 0.1, 1500, "Increases Percent Reduction Of Damage Taken", 2, 3), new Upgrade(game, 0.15, 0, "Damage Reduction Maxed", 3, 3)};
		powerupDrop = new Upgrade[] {new Upgrade(game, 0, 200, "Enemies Have A Chance Of Dropping Powerups", 0, 6), new Upgrade(game, 0.01, 300, "Increases Powerup Drop Rate", 1, 6), new Upgrade(game, 0.02, 400, "Increases Powerup Drop Rate", 2, 6), new Upgrade(game, 0.04, 500, "Increases Powerup Drop Rate", 3, 6), new Upgrade(game, 0.06, 600, "Increases Powerup Drop Rate", 4, 6), new Upgrade(game, 0.08, 700, "Increases Powerup Drop Rate", 5, 6), new Upgrade(game, 0.1, 0, "Powerup Drop Rate Maxed", 6, 6)};
		moneyBonus = new Upgrade[] {new Upgrade(game, 1, 200, "Increases Money Gained By A Percentage", 0, 5), new Upgrade(game, 1.2, 300, "Increases Money Gained By A Percentage", 1, 5), new Upgrade(game, 1.4, 400, "Increases Money Gained By A Percentage", 2, 5), new Upgrade(game, 1.6, 500, "Increases Money Gained By A Percentage", 3, 5), new Upgrade(game, 1.8, 600, "Increases Money Gained By A Percentage", 4, 5), new Upgrade(game, 2, 0, "Money Bonus Maxed", 5, 5)};
		damageBonus = new Upgrade[] {new Upgrade(game, 1, 300, "Increases All Damage Dealt By A Percentage", 0, 5), new Upgrade(game, 1.1, 500, "Increases All Damage Dealt By A Percentage", 1, 5), new Upgrade(game, 1.2, 700, "Increases All Damage Dealt By A Percentage", 2, 5), new Upgrade(game, 1.3, 1000, "Increases All Damage Dealt By A Percentage", 3, 5), new Upgrade(game, 1.4, 1200, "Increases All Damage Dealt By A Percentage", 4, 5), new Upgrade(game, 1.5, 0, "Damage Bonus Maxed", 5, 5)};
	
		blastType = new BufferedImage[] {texture.blast1, texture.blast2, texture.blast3, texture.blast4, texture.blast5};
		missileType = new BufferedImage[] {texture.missile1, texture.missile2, texture.missile3, texture.missile4}; 
		laserType = new BufferedImage[][] {texture.laser1, texture.laser2, texture.laser3, texture.laser4, texture.laser5};
	}
	
	public void upgradeBlastDamage() {
		if(blastDamage.length > 1) {
			Upgrade[] newBlastDamage = new Upgrade[blastDamage.length - 1];
			for(int i = 1; i < blastDamage.length; i++) {
				newBlastDamage[i - 1] = blastDamage[i];
			}
			blastDamage = newBlastDamage;
		}
		upgradesBought++;
	}
	
	public void upgradeBlastFirerate() {
		if(blastFirerate.length > 1) {
			Upgrade[] newBlastFirerate = new Upgrade[blastFirerate.length - 1];
			for(int i = 1; i < blastFirerate.length; i++) {
				newBlastFirerate[i - 1] = blastFirerate[i];
			}
			blastFirerate = newBlastFirerate;
		}
		upgradesBought++;
	}
	
	public void upgradeBlastType() {
		if(blastType.length > 1) {
			BufferedImage[] newBlastType = new BufferedImage[blastType.length - 1];
			for(int i = 1; i < blastType.length; i++) {
				newBlastType[i - 1] = blastType[i];
			}
			blastType = newBlastType;
		}
	}
	
	public void upgradeMissileDamage() {
		if(missileDamage.length > 1) {
			Upgrade[] newMissileDamage = new Upgrade[missileDamage.length - 1];
			for(int i = 1; i < missileDamage.length; i++) {
				newMissileDamage[i - 1] = missileDamage[i];
			}
			missileDamage = newMissileDamage;
		}
		upgradesBought++;
	}
	
	public void upgradeMissileFirerate() {
		if(missileFirerate.length > 1) {
			Upgrade[] newMissileFirerate = new Upgrade[missileFirerate.length - 1];
			for(int i = 1; i < missileFirerate.length; i++) {
				newMissileFirerate[i - 1] = missileFirerate[i];
			}
			missileFirerate = newMissileFirerate;
		}
		upgradesBought++;
	}
	
	public void upgradeMissileType() {
		if(missileType.length > 1) {
			BufferedImage[] newMissileType = new BufferedImage[missileType.length - 1];
			for(int i = 1; i < missileType.length; i++) {
				newMissileType[i - 1] = missileType[i];
			}
			missileType = newMissileType;
		}
	}
	
	public void upgradeMissileExplosionRadius() {
		if(missileExplosionRadius.length > 1) {
			Upgrade[] newMissileExplosionRadius = new Upgrade[missileExplosionRadius.length - 1];
			for(int i = 1; i < missileExplosionRadius.length; i++) {
				newMissileExplosionRadius[i - 1] = missileExplosionRadius[i];
			}
			missileExplosionRadius = newMissileExplosionRadius;
		}
		upgradesBought++;
	}
	
	public void upgradeMissileExplosionDamage() {
		if(missileExplosionDamage.length > 1) {
			Upgrade[] newMissileExplosionDamage = new Upgrade[missileExplosionDamage.length - 1];
			for(int i = 1; i < missileExplosionDamage.length; i++) {
				newMissileExplosionDamage[i - 1] = missileExplosionDamage[i];
			}
			missileExplosionDamage = newMissileExplosionDamage;
		}
		upgradesBought++;
	}
	
	public void upgradeLaserDamage() {
		if(laserDamage.length > 1) {
			Upgrade[] newLaserDamage = new Upgrade[laserDamage.length - 1];
			for(int i = 1; i < laserDamage.length; i++) {
				newLaserDamage[i - 1] = laserDamage[i];
			}
			laserDamage = newLaserDamage;
		}
		upgradesBought++;
	}
	
	public void upgradeLaserType() {
		if(laserType.length > 1) {
			BufferedImage[][] newlaserType = new BufferedImage[laserType.length - 1][laserType[0].length];
			for(int i = 1; i < laserType.length; i++) {
				newlaserType[i - 1] = laserType[i];
			}
			laserType = newlaserType;
		}
	}
	
	public void upgradeDualShot() {
		if(dualShot.length > 1) {
			Upgrade[] newDualShot = new Upgrade[dualShot.length - 1];
			for(int i = 1; i < dualShot.length; i++) {
				newDualShot[i - 1] = dualShot[i];
			}
			dualShot = newDualShot;
		}
		upgradesBought++;
	}
	
	public void upgradeShieldCooldown() {
		if(shieldCooldown.length > 1) {
			Upgrade[] newShieldCooldown = new Upgrade[shieldCooldown.length - 1];
			for(int i = 1; i < shieldCooldown.length; i++) {
				newShieldCooldown[i - 1] = shieldCooldown[i];
			}
			shieldCooldown = newShieldCooldown;
		}
		upgradesBought++;
	}
	
	public void upgradeShieldDuration() {
		if(shieldDuration.length > 1) {
			Upgrade[] newShieldDuration = new Upgrade[shieldDuration.length - 1];
			for(int i = 1; i < shieldDuration.length; i++) {
				newShieldDuration[i - 1] = shieldDuration[i];
			}
			shieldDuration = newShieldDuration;
		}
		upgradesBought++;
	}
	
	public void upgradeShipHealth() {
		if(shipHealth.length > 1) {
			Upgrade[] newshipHealth = new Upgrade[shipHealth.length - 1];
			for(int i = 1; i < shipHealth.length; i++) {
				newshipHealth[i - 1] = shipHealth[i];
			}
			shipHealth = newshipHealth;
		}
		upgradesBought++;
	}
	
	public void upgradeDamageReduction() {
		if(damageReduction.length > 1) {
			Upgrade[] newDamageReduction = new Upgrade[damageReduction.length - 1];
			for(int i = 1; i < damageReduction.length; i++) {
				newDamageReduction[i - 1] = damageReduction[i];
			}
			damageReduction = newDamageReduction;
		}
		upgradesBought++;
	}

	public void upgradeMoneyBonus() {
		if(moneyBonus.length > 1) {
			Upgrade[] newMoneyBonus = new Upgrade[moneyBonus.length - 1];
			for(int i = 1; i < moneyBonus.length; i++) {
				newMoneyBonus[i - 1] = moneyBonus[i];
			}
			moneyBonus = newMoneyBonus;
		}
		upgradesBought++;
	}
	
	public void upgradeDamageBonus() {
		if(damageBonus.length > 1) {
			Upgrade[] newDamageBonus = new Upgrade[damageBonus.length - 1];
			for(int i = 1; i < damageBonus.length; i++) {
				newDamageBonus[i - 1] = damageBonus[i];
			}
			damageBonus = newDamageBonus;
		}
		upgradesBought++;
	}
	
	public void upgradePowerupDrop() {
		if(powerupDrop.length > 1) {
			Upgrade[] newpowerupDrop = new Upgrade[powerupDrop.length - 1];
			for(int i = 1; i < powerupDrop.length; i++) {
				newpowerupDrop[i - 1] = powerupDrop[i];
			}
			powerupDrop = newpowerupDrop;
		}
		upgradesBought++;
	}
	
	/////////////////////////////////////////////////////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public Upgrade getBlastDamage() {
		return blastDamage[0];
	}

	public Upgrade getBlastFirerate() {
		return blastFirerate[0];
	}

	public BufferedImage getBlastType() {
		return blastType[0];
	}

	public Upgrade getMissileDamage() {
		return missileDamage[0];
	}

	public Upgrade getMissileFirerate() {
		return missileFirerate[0];
	}
	
	public Upgrade getMissileExplosionRadius() {
		return missileExplosionRadius[0];
	}
	
	public Upgrade getMissileExplosionDamage() {
		return missileExplosionDamage[0];
	}

	public BufferedImage getMissileType() {
		return missileType[0];
	}

	public Upgrade getLaserDamage() {
		return laserDamage[0];
	}

	public BufferedImage[] getLaserType() {
		return laserType[0];
	}
	
	public Upgrade getDualShot() {
		return dualShot[0];
	}

	public Upgrade getShieldCooldown() {
		return shieldCooldown[0];
	}

	public Upgrade getShieldDuration() {
		return shieldDuration[0];
	}

	public Upgrade getShipHealth() {
		return shipHealth[0];
	}

	public Upgrade getDamageReduction() {
		return damageReduction[0];
	}

	public Upgrade getPowerupDrop() {
		return powerupDrop[0];
	}
	
	public Upgrade getMoneyBonus() {
		return moneyBonus[0];
	}
	
	public Upgrade getDamageBonus() {
		return damageBonus[0];
	}
	
	public int getNumUpgradesBought() {
		return upgradesBought;
	}
}
