package main;

import java.awt.Graphics;

import entities.Player;
import levels.LevelManager;

public class Game implements Runnable {

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	private Player player;

	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.562f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	private LevelManager levelManager;

	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();

	}

	private void initClasses() {
		levelManager = new LevelManager(this);
		player = new Player(200, 200, (int) (99 * SCALE), (int) (64.5 * SCALE));
		player.loadLevelData(levelManager.getCurrentLevel().getLevelData());

	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		player.update();
		levelManager.update();

	}

	public void render(Graphics g) {

		levelManager.draw(g);
		player.render(g);
	}

	@Override
	public void run() {

		double timePerFrame = 1000000000 / FPS_SET;
		double timePerUpdate = 1000000000 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;
		long currentTime;

		while (true) {

			currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;

			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS = " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}

		}

	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}
}
