package my.games.snake.enums;

/**
 * @author Eric
 * 
 * @desc ��Ϸ�ķ���ö����
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