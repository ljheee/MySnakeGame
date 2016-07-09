package my.games.snake.enums;

/**
 * @author Eric
 * 
 * @desc 游戏的方向枚举类
 */
public enum Direction {
	UP, DOWN, LEFT, RIGHT;

	public boolean isRightDirection() {
		return RIGHT == this;
	}

	public boolean isLeftDirection() {
		return LEFT == this;
	}

	public boolean isUpDirection() {
		return UP == this;
	}

	public boolean isDownDirection() {
		return DOWN == this;
	}
}