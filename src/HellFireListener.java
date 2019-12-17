import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class HellFireListener implements KeyListener, MouseListener, ActionListener, FocusListener, MouseMotionListener {
	private HellFireGame game;
	private Player p;
	private int basicShootKey = KeyEvent.VK_J;
	private int missileShootKey = KeyEvent.VK_K;
	private int shieldKey = KeyEvent.VK_L;

	public HellFireListener(HellFireGame game) {
		this.game = game;
		game.addMouseListener(this);
		game.addKeyListener(this);
		game.addFocusListener(this);
		game.addMouseMotionListener(this);
		p = game.getPlayer();
	}

	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s == null) {
			
		} else if (s.equals("UpgradeMenu")) {
			game.setStateUpgrade();
		}
	}

	public void focusGained(FocusEvent e) {
	}

	public void focusLost(FocusEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		game.requestFocusInWindow();
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		if(game.checkState("menu")) {
			if(checkBounds(235, 405, 250, 300, mouseX, mouseY)) {
				game.setStateGame();
				game.setInvisibleCursor();
			} else if(checkBounds(235, 405, 350, 400, mouseX, mouseY)) {
				game.setStateTutorial();
			} else if(checkBounds(235, 405, 450, 500, mouseX, mouseY)) {
				game.setStateSettings();
			}
		} else if(game.checkState("game")) {
			 if(checkBounds(10, 120, 670, 710, mouseX, mouseY)) {
					if(!game.getPlayer().getPause()) {
						game.getPlayer().setPause(true);
					} else {
						game.getPlayer().setPause(false);
					}
			 }
			 if(game.getPlayer().getPause()) {
				 if(checkBounds(230, 410, 230, 270, mouseX, mouseY)) {
					 game.getPlayer().setPause(false);
				 } else if(checkBounds(230, 410, 300, 340, mouseX, mouseY)) {
					 game.setStateUpgrade();
					 game.restart();
				 } else if(checkBounds(230, 410, 370, 410, mouseX, mouseY)) {
					 game.setStateSettings();
					 game.restart();
				 } else if(checkBounds(230, 410, 440, 480, mouseX, mouseY)) {
					 game.setStateMenu();
					 game.restart();
				 }
			 }
		} else if(game.checkState("tutorial")) {
			if(game.getTutorialMenu().getStage() <= 7) {
				if(game.getTutorialMenu().getStage() == 7 && checkBounds(480, 630, 670, 710, mouseX, mouseY)) {
					game.setStateGame();
				} else if(checkBounds(510, 620, 670, 710, mouseX, mouseY)) {
					game.getTutorialMenu().setStage(game.getTutorialMenu().getStage() + 1);
				}
			} else if(checkBounds(10, 120, 670, 710, mouseX, mouseY)) {
				if(game.getTutorialMenu().getStage() == 0) {
					game.setStateMenu();
				} else {
					game.getTutorialMenu().setStage(game.getTutorialMenu().getStage() - 1);
				}
			}
		} else if(game.checkState("settings")) {
			if(checkBounds(510, 620, 670, 710, mouseX, mouseY)) {
				game.setStateMenu();
			} else if(checkBounds(400, 550, 250, 300, mouseX, mouseY) || (checkBounds(50, 355, 250, 300, mouseX, mouseY))) {
				if(game.getSettingsMenu().mouseControl) {
					game.getPlayer().setMouseControl(false);
					game.getSettingsMenu().mouseControl = false;
				} else {
					game.getPlayer().setMouseControl(true);
					game.getSettingsMenu().mouseControl = true;
				}
			} else if(checkBounds(50, 180, 325, 375, mouseX, mouseY) || checkBounds(400, 550, 325, 375, mouseX, mouseY)) {
				if(game.getSettingsMenu().hacksOn) {
					game.getSettingsMenu().hacksOn = false;
					game.getPlayer().hacks(false);
				} else {
					game.getSettingsMenu().hacksOn = true;
					game.getPlayer().hacks(true);
				}
			} else if(checkBounds(50, 180, 400, 450, mouseX, mouseY)|| checkBounds(400, 550, 400, 450, mouseX, mouseY)) {
				if(game.getSettingsMenu().musicMuted) {
					game.getSettingsMenu().musicMuted = false;
					game.setMusicMuted(false);
					game.getSound().playSound();
				} else {
					game.getSettingsMenu().musicMuted = true;
					game.setMusicMuted(true);
					game.getSound().stopSound();
				}
			}
		} else if(game.checkState("upgrades")) {
			if(checkBounds(10, 120, 670, 710, mouseX, mouseY)) {
				game.setStateMenu();
			} else if(checkBounds(480, 630, 670, 710, mouseX, mouseY)) {
				game.setStateGame();
				game.setInvisibleCursor();
			
			}
			if(game.getUpgradesMenu().getState() == 0) {
				//////////////////////////////////////////////////////STATE 0\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

				if(checkBounds(10, 630, 150, 180, mouseX, mouseY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getBlastDamage().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getBlastDamage().getPrice());
						game.getPlayer().getUpgrades().upgradeBlastDamage();
						game.getPlayer().getUpgrades().upgradeBlastType();
					}
				} else if(checkBounds(10, 630, 190, 220, mouseX, mouseY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getBlastFirerate().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getBlastFirerate().getPrice());
						game.getPlayer().getUpgrades().upgradeBlastFirerate();
					}
				} else if(checkBounds(10, 630, 230, 260, mouseX, mouseY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getMissileDamage().getPrice()) {
						if(game.getPlayer().getUpgrades().getMissileDamage().getUpgradeValue() == 0) {
							game.getPlayer().getUpgrades().upgradeMissileFirerate();
							game.getPlayer().getUpgrades().upgradeMissileExplosionDamage();
							game.getPlayer().getUpgrades().upgradeMissileExplosionRadius();
							if(game.getPlayer().getUpgrades().getDualShot().getUpgradeValue() == 1) {
								game.getPlayer().getUpgrades().upgradeDualShot();
							}
						}
						game.getPlayer().money(-game.getPlayer().getUpgrades().getMissileDamage().getPrice());
						game.getPlayer().getUpgrades().upgradeMissileDamage();
						if(game.getPlayer().getUpgrades().getMissileDamage().getUpgradeValue() != 0) {
							game.getPlayer().getUpgrades().upgradeMissileType();
						}
					}
				} else if(checkBounds(10, 630, 270, 300, mouseX, mouseY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getMissileFirerate().getPrice() && game.getPlayer().getUpgrades().getMissileDamage().getUpgradeValue() != 0) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getMissileFirerate().getPrice());
						game.getPlayer().getUpgrades().upgradeMissileFirerate();
						
					}
				} else if(checkBounds(10, 630, 310, 340, mouseX, mouseY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getMissileExplosionDamage().getPrice() && game.getPlayer().getUpgrades().getMissileDamage().getUpgradeValue() != 0) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getMissileExplosionDamage().getPrice());
						if(game.getPlayer().getUpgrades().getMissileExplosionDamage().getUpgradeValue() == 0) {
							game.getPlayer().getUpgrades().upgradeMissileExplosionRadius();
						}
						game.getPlayer().getUpgrades().upgradeMissileExplosionDamage();
		
					}
				} else if(checkBounds(10, 630, 350, 380, mouseX, mouseY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getMissileExplosionRadius().getPrice() && game.getPlayer().getUpgrades().getMissileExplosionDamage().getUpgradeValue() != 0) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getMissileExplosionRadius().getPrice());
						game.getPlayer().getUpgrades().upgradeMissileExplosionRadius();
					}
				} else if(checkBounds(10, 630, 390, 420, mouseX, mouseY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getLaserDamage().getPrice()) {
						if(game.getPlayer().getUpgrades().getLaserDamage().getUpgradeValue() != 0) {
							game.getPlayer().getUpgrades().upgradeLaserType();
						}
						game.getPlayer().money(-game.getPlayer().getUpgrades().getLaserDamage().getPrice());
						game.getPlayer().getUpgrades().upgradeLaserDamage();
						
					}
				} else if(checkBounds(10, 630, 430, 460, mouseX, mouseY)) {
					if(game.getPlayer().getUpgrades().getDualShot().getUpgradeValue() == 0 && game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getDualShot().getPrice() && game.getPlayer().getUpgrades().getMissileDamage().getUpgradeValue() > 0) {
						game.getPlayer().getUpgrades().upgradeDualShot();
						game.getPlayer().getUpgrades().upgradeDualShot();
					} else if(game.getPlayer().getUpgrades().getDualShot().getUpgradeValue() != 1 && game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getDualShot().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getDualShot().getPrice());
						game.getPlayer().getUpgrades().upgradeDualShot();
					}
				}
			} else if(game.getUpgradesMenu().getState() == 1) {
				//////////////////////////////////////////////////////STATE 1\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
				if(checkBounds(10, 630, 150, 180, mouseX, mouseY)) { 
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getShieldCooldown().getPrice()) {
						if(game.getPlayer().getUpgrades().getShieldCooldown().getUpgradeValue() == 0) {
							game.getPlayer().getUpgrades().upgradeShieldDuration();
						}
						game.getPlayer().money(-game.getPlayer().getUpgrades().getShieldCooldown().getPrice());
						game.getPlayer().getUpgrades().upgradeShieldCooldown();
					}
				} else if(checkBounds(10, 630, 190, 220, mouseX, mouseY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getShieldDuration().getPrice() && game.getPlayer().getUpgrades().getShieldCooldown().getUpgradeValue() != 0) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getShieldDuration().getPrice());
						game.getPlayer().getUpgrades().upgradeShieldDuration();
					}
				} else if(checkBounds(10, 630, 230, 260, mouseX, mouseY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getShipHealth().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getShipHealth().getPrice());
						game.getPlayer().getUpgrades().upgradeShipHealth();
					}
				}
			} else {
				//////////////////////////////////////////////////////STATE 2\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
				 if(checkBounds(10, 630, 150, 180, mouseX, mouseY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getDamageReduction().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getDamageReduction().getPrice());
						game.getPlayer().getUpgrades().upgradeDamageReduction();
					}
				} else if(checkBounds(10, 630, 190, 220, mouseX, mouseY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getPowerupDrop().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getPowerupDrop().getPrice());
						game.getPlayer().getUpgrades().upgradePowerupDrop();
					}
				} else if(checkBounds(10, 630, 230, 260, mouseX, mouseY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getDamageBonus().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getDamageBonus().getPrice());
						game.getPlayer().getUpgrades().upgradeDamageBonus();
					}
				} else if(checkBounds(10, 630, 270, 300, mouseX, mouseY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getMoneyBonus().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getMoneyBonus().getPrice());
						game.getPlayer().getUpgrades().upgradeMoneyBonus();
					}
				}
			}
			if(checkBounds(40, 236, 85, 128, mouseX, mouseY)) {
				game.getUpgradesMenu().setState(0);
			} else if(checkBounds(270, 370, 85, 128, mouseX, mouseY)) {
				game.getUpgradesMenu().setState(1);
			} else if(checkBounds(430, 550, 85, 128, mouseX, mouseY)) {
				game.getUpgradesMenu().setState(2);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {
		if(!game.getPlayer().getPause()) {
			if(!p.getMouseControl() || !p.canMove) {
				return;
			}
			int mouseX = e.getX() - 30;
			int mouseY = e.getY() - 20;
			if(game.checkState("game")) {
				if(mouseY > 600) {
					game.setVisibleCursor();
				} else if(game.getPlayer().getMouseControl()) {
					game.setInvisibleCursor();
				}
			}
			if(mouseY > 600) {
				mouseY = 600;
			}
			if(mouseX > 576) {
				mouseX = 576;
			} else if(mouseX < 0) {
				mouseX = 0;
			}
			p.setX(mouseX);
			p.setY(mouseY);
		}
	}

	public void mouseMoved(MouseEvent e) {
		if(!game.getPlayer().getPause()) {
			if(!p.getMouseControl() || !p.canMove) {
				return;
			}
			int mouseX = e.getX() - 30;
			int mouseY = e.getY() - 20;
			if(game.checkState("game")) {
				if(mouseY > 600) {
					game.setVisibleCursor();
				} else if(game.getPlayer().getMouseControl()) {
					game.setInvisibleCursor();
				}
			}
			if(mouseY > 600) {
				mouseY = 600;
			}
			if(mouseX > 576) {
				mouseX = 576;
			} else if(mouseX < 0) {
				mouseX = 0;
			}
			p.setX(mouseX);
			p.setY(mouseY);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if(!game.getPlayer().getPause()) {
			int code = e.getKeyCode();
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				p.moveUp();
			} else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				p.moveDown();
			} else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
				p.moveLeft();
			} else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
				p.moveRight();
			} else if(code == basicShootKey) {
				game.getPlayer().setBlastShooting(true);
			} else if(code == KeyEvent.VK_M) {
				if(game.getPlayer().getAutoShoot()) {
					game.getPlayer().setAutoShoot(false);
				} else {
					game.getPlayer().setAutoShoot(true);
				}
			} else if(code == missileShootKey) {
				game.getPlayer().setMissileShooting(true);
			} else if(code == shieldKey) {
				if(game.getPlayer().getShieldReady()) {
					game.getPlayer().setShieldActive();
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			p.stopUp();
		} else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			p.stopDown();
		} else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			p.stopLeft();
		} else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			p.stopRight();
		} else if(code == basicShootKey) {
			game.getPlayer().setBlastShooting(false);
		} else if(code == missileShootKey) {
			game.getPlayer().setMissileShooting(false);
		}
	}

	public void keyTyped(KeyEvent e) {}
	
	public boolean checkBounds(int x, int finalX, int y, int finalY, int mouseX, int mouseY) {
		if(mouseX >= x && mouseX <= finalX && mouseY >= y && mouseY <= finalY) {
			return true;
		}
		return false;
	}

}
