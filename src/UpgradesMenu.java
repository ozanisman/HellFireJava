import java.awt.*;
import java.awt.geom.Rectangle2D;

public class UpgradesMenu {
	
	private HellFireGame game;
	private int state = 0;
	private Upgrade[] upgrades = new Upgrade[15];
	/*
	blastDamage
	blastFirerate
	missileDamage
	missileFirerate
	missileExplosionRadius
	missileExplosionDamage
	laserDamage
	dualShot
	shieldCooldown
	shieldDuration
	shipHealth
	damageReduction
	powerupDrop
	damageBonus
	moneyBonus

	*/
	
	public UpgradesMenu(HellFireGame game) {
		this.game = game;
		upgrades[0] = game.getPlayer().getUpgrades().getBlastDamage();
		upgrades[1] = game.getPlayer().getUpgrades().getBlastFirerate();
		upgrades[2] = game.getPlayer().getUpgrades().getMissileDamage();
		upgrades[3] = game.getPlayer().getUpgrades().getMissileFirerate();
		upgrades[4] = game.getPlayer().getUpgrades().getMissileExplosionRadius();
		upgrades[5] = game.getPlayer().getUpgrades().getMissileExplosionDamage();
		upgrades[6] = game.getPlayer().getUpgrades().getLaserDamage();
		upgrades[7] = game.getPlayer().getUpgrades().getDualShot();
		upgrades[8] = game.getPlayer().getUpgrades().getShieldCooldown();
		upgrades[9] = game.getPlayer().getUpgrades().getShieldDuration();
		upgrades[10] = game.getPlayer().getUpgrades().getShipHealth();
		upgrades[11] = game.getPlayer().getUpgrades().getDamageReduction();
		upgrades[12] = game.getPlayer().getUpgrades().getPowerupDrop();
		upgrades[13] = game.getPlayer().getUpgrades().getDamageBonus();
		upgrades[14] = game.getPlayer().getUpgrades().getMoneyBonus();
	}
	
	public void tick() {
		upgrades[0] = game.getPlayer().getUpgrades().getBlastDamage();
		upgrades[1] = game.getPlayer().getUpgrades().getBlastFirerate();
		upgrades[2] = game.getPlayer().getUpgrades().getMissileDamage();
		upgrades[3] = game.getPlayer().getUpgrades().getMissileFirerate();
		upgrades[4] = game.getPlayer().getUpgrades().getMissileExplosionDamage();
		upgrades[5] = game.getPlayer().getUpgrades().getMissileExplosionRadius();
		upgrades[6] = game.getPlayer().getUpgrades().getLaserDamage();
		upgrades[7] = game.getPlayer().getUpgrades().getDualShot();
		upgrades[8] = game.getPlayer().getUpgrades().getShieldCooldown();
		upgrades[9] = game.getPlayer().getUpgrades().getShieldDuration();
		upgrades[10] = game.getPlayer().getUpgrades().getShipHealth();
		upgrades[11] = game.getPlayer().getUpgrades().getDamageReduction();
		upgrades[12] = game.getPlayer().getUpgrades().getPowerupDrop();
		upgrades[13] = game.getPlayer().getUpgrades().getDamageBonus();
		upgrades[14] = game.getPlayer().getUpgrades().getMoneyBonus();
	}
	
	public void render(Graphics g) {
		Font f = new Font("SansSerif", Font.BOLD, 50);
		g.setFont(f);
		g.setColor(Color.RED);
		g.drawString("UPGRADES", MainMenu.getCenterX("UPGRADES", g, f), 57);
		Font f2 = new Font("SansSerif", Font.BOLD, 30);
		g.setFont(f2);
		g.drawString("$" + game.getPlayer().getMoney(), MainMenu.getCenterX("$" + game.getPlayer().getMoney(), g, f2), 700);
		g.fillRect(480, 670, 150, 40);
		g.fillRect(10, 670, 110, 40);
		g.setColor(Color.BLACK);
		g.drawString("LAUNCH!", 488, 700);
		g.drawString("MENU", 20, 700);
		
		//Upgrade Buttons
		Font f3 = new Font("SansSerif", Font.BOLD, 20);
		g.setColor(Color.RED);
		g.setFont(f3);

		if(state == 0) { /////////////////////////////////////////// STATE 0 \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
			for(int i = 0; i < 8; i++) {
				g.setColor(Color.DARK_GRAY);
				g.fillRect(10, 150 + 40 * i, (int)(upgrades[i].getCurrentUpgradeNum() / ((0.0) + upgrades[i].getTotalUpgradeNum()) * 610), 30);
				if(upgrades[i].isPurchasable()) {
					g.setColor(Color.GREEN);
				} else {
					g.setColor(Color.RED);
				}
				g.drawRect(10, 150 + 40 * i, 610, 30);
				g.drawString(upgrades[i].getDescription(), 30, 173 + 40 * i);
				if(upgrades[i].getPrice() != 0) {
					g.drawString("$" + upgrades[i].getPrice(), game.getMainMenu().getCenterX("$" + upgrades[i].getPrice(), g, f3) + 257, 173 + 40 * i);
				}
			}
			
		} else if(state == 1) { /////////////////////////////// STATE 1 \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
			for(int i = 8; i < 11; i++) {
				g.setColor(Color.DARK_GRAY);
				g.fillRect(10, 150 + 40 * (i - 8), (int)(upgrades[i].getCurrentUpgradeNum() / ((0.0) + upgrades[i].getTotalUpgradeNum()) * 610), 30);
				if(upgrades[i].isPurchasable()) {
					g.setColor(Color.GREEN);
				} else {
					g.setColor(Color.RED);
				}
				g.drawRect(10, 150 + 40 * (i - 8), 610, 30);
				g.drawString(upgrades[i].getDescription(), 30, 173 + 40 * (i - 8));
				if(upgrades[i].getPrice() != 0) {
					g.drawString("$" + upgrades[i].getPrice(), game.getMainMenu().getCenterX("$" + upgrades[i].getPrice(), g, f3) + 257, 173 + 40 * (i - 8));
				}
			}

		} else if(state == 2) { ////////////////////////////////////////////// STATE 2 \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
			for(int i = 11; i < upgrades.length; i++) {
				g.setColor(Color.DARK_GRAY);
				g.fillRect(10, 150 + 40 * (i - 11), (int)(upgrades[i].getCurrentUpgradeNum() / ((0.0) + upgrades[i].getTotalUpgradeNum()) * 610), 30);
				if(upgrades[i].isPurchasable()) {
					g.setColor(Color.GREEN);
				} else {
					g.setColor(Color.RED);
				}
				g.drawRect(10, 150 + 40 * (i - 11), 610, 30);
				g.drawString(upgrades[i].getDescription(), 30, 173 + 40 * (i - 11));
				if(upgrades[i].getPrice() != 0) {
					g.drawString("$" + upgrades[i].getPrice(), game.getMainMenu().getCenterX("$" + upgrades[i].getPrice(), g, f3) + 257, 173 + 40 * (i - 11));
				}
			}
		}

		Font f4 = new Font("SansSerif", Font.BOLD, 35);
		g.setFont(f4);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(40, 85, 196, 43); //weapons
		g.fillRect(270, 85, 100, 43); //ship
		g.fillRect(430, 85, 120, 43); // misc
		
		g.setColor(checkState(0));
		g.drawString("WEAPONS", 50, 120);
		
		g.setColor(checkState(1));
		g.drawString("SHIP", game.getMainMenu().getCenterX("SHIP", g, f4), 120);
		
		g.setColor(checkState(2));
		g.drawString("MISC", 448, 120);
		
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
	
	public Color checkState(int state) {
		if(this.state == state) {
			return Color.GREEN;
		}
		return Color.RED;
	}
	
	public Upgrade[] getCurrentUpgrades() {
		return upgrades;
	}
}
