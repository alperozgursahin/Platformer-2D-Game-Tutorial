package utilz;

public class Constants {

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {

		public static final int IDLE = 0;
		public static final int ATTACK_1 = 1;
		public static final int ATTACK_2 = 2;
		public static final int ATTACK_3 = 3;
		public static final int DEAD = 4;
		public static final int DEFEND = 5;
		public static final int HURT = 6;
		public static final int JUMP = 7;
		public static final int PROTECT = 8;
		public static final int RUNNING = 9;
		public static final int RUN_ATTACK = 10;

		public static int GetSpriteAmount(int playerAction) {
			switch (playerAction) {
			case RUNNING:
				return 7;
			case RUN_ATTACK:
			case JUMP:
			case DEAD:
				return 6;
			case ATTACK_1:
			case DEFEND:
				return 5;
			case IDLE:
			case ATTACK_2:
			case ATTACK_3:
				return 4;
			case HURT:
				return 2;
			case PROTECT:
				return 1;
			default:
				return 1;

			}
		}

	}

}
