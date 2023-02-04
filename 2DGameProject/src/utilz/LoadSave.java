package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {

	public static final String PLAYER_ATLAS = "knight_sprites.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String LEVEL_1_DATA = "level_one_data.png";

	public static BufferedImage GetSpriteAtlas(String filename) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + filename);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

	public static int[][] GetLevelData() {
		int[][] levelData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
		BufferedImage img = GetSpriteAtlas(LEVEL_1_DATA);
		int value = 0;

		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				value = color.getRed();
				if (value >= 48) {
					value = 0;
				}
				levelData[i][j] = value;
			}
		}
		return levelData;
	}
}
