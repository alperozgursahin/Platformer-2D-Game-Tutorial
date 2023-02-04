package entities;

import static utilz.Constants.PlayerConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;
import utilz.LoadSave;
import static utilz.HelpMethods.CanMoveHere;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 20;
	private int playerAction = IDLE;
	private boolean left, up, right, down;
	private boolean moving, attacking, jumping;
	private float playerSpeed = 1f;
	private int[][] levelData;
	private float xDrawOffSet = 38 * Game.SCALE;
	private float yDrawOffSet = 17 * Game.SCALE;

	public Player(float x, float y, int height, int width) {
		super(x, y, height, width);
		loadAnimations();
		initHitbox(x, y, 25 * Game.SCALE, 48 * Game.SCALE);

	}

	public void update() {
		updatePosition();
		updateAnimationTick();
		setAnimation();

	}

	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffSet), (int) (hitbox.y - yDrawOffSet),
				width, height, null);
		drawHitbox(g);
	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
				jumping = false;
			}
		}

	}

	private void setAnimation() {
		int startAni = playerAction;

		if (moving) {
			playerAction = RUNNING;
		} else {
			playerAction = IDLE;
		}
		if (attacking) {
			playerAction = ATTACK_2;
		}
		if (jumping) {
			playerAction = JUMP;
		}
		if (startAni != playerAction) {
			resetAni();
		}

	}

	private void resetAni() {
		aniTick = 0;
		aniIndex = 0;

	}

	private void updatePosition() {
		// jumping = false;
		moving = false;
		if (!left && !right && !down && !up)
			return;

		float xSpeed = 0, ySpeed = 0;

		if (left && !right)
			xSpeed = -playerSpeed;

		else if (right && !left)
			xSpeed = playerSpeed;

		if (up && !down)
			ySpeed = -playerSpeed;

		else if (down && !up)
			ySpeed = playerSpeed;

		/*
		 * if (CanMoveHere(x + xSpeed, y + ySpeed, width, height, levelData)) { this.x
		 * += xSpeed; this.y += ySpeed; moving = true; }
		 */
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, levelData)) {
			hitbox.x += xSpeed;
			hitbox.y += ySpeed;
			moving = true;
		}
	}

	private void loadAnimations() {

		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		animations = new BufferedImage[11][7];
		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 132, j * 86, 132, 86);

	}

	public void loadLevelData(int[][] levelData) {
		this.levelData = levelData;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;

	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

}
