package my.games.snake.enums;

/**
 * @author Eric
 * @desc 游戏的结束类型的枚举类
 */
public enum GameOverType {
	TOUCH_BARRIER, TOUCH_EDGE, TOUCH_BODY;

	public boolean isBarrierTouched() {
		return TOUCH_BARRIER == this;
	}

	public boolean isEdgeTouched() {
		return TOUCH_EDGE == this;
	}

	public boolean isBodyTouched() {
		return TOUCH_BODY == this;
	}
}