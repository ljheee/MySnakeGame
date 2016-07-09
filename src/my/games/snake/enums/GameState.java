package my.games.snake.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eric
 * 
 * @desc 游戏的状态枚举类
 */
public enum GameState {

	INITIALIZE("I", "Initial Game State"), RUN("R", "Run State"), PAUSE("P",
			"Pause State"), OVER("O", "Over State"), UNKNOWN("U", "UNKNOWN");

	private String gameStateCode = null;

	private String gameStateValue = null;

	private static Map<String, GameState> MAP = new HashMap<String, GameState>();

	static {
		for (GameState gameState : GameState.values()) {
			MAP.put(gameState.getGameStateCode(), gameState);
		}
	}

	private GameState(String gameStateCode, String gameStateValue) {
		this.gameStateCode = gameStateCode;
		this.gameStateValue = gameStateValue;
	}

	public static GameState getGameStateByCode(String gameStateCode) {
		return MAP.containsKey(gameStateCode) ? MAP.get(gameStateCode)
				: UNKNOWN;
	}

	/**
	 * @return the gameStateCode
	 */
	public String getGameStateCode() {
		return gameStateCode;
	}

	/**
	 * @param gameStateCode
	 *            the gameStateCode to set
	 */
	public void setGameStateCode(String gameStateCode) {
		this.gameStateCode = gameStateCode;
	}

	/**
	 * @return the gameStateValue
	 */
	public String getGameStateValue() {
		return gameStateValue;
	}

	/**
	 * @param gameStateValue
	 *            the gameStateValue to set
	 */
	public void setGameStateValue(String gameStateValue) {
		this.gameStateValue = gameStateValue;
	}

	public boolean isInitializedState() {
		return this == INITIALIZE;
	}

	public boolean isRunState() {
		return this == RUN;
	}

	public boolean isPausedState() {
		return this == PAUSE;
	}

	public boolean isOverState() {
		return this == OVER;
	}
}
