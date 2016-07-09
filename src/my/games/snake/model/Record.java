package my.games.snake.model;

import java.io.Serializable;

/**
 * 
 * @author Eric
 * 
 * @desc ��¼��ҵ���Ϣ
 */
public class Record implements Serializable {

	private static final long serialVersionUID = -545152110065949242L;

	private String player = null;//�������

	private int score = 0;//��ҵ÷�

	public Record(String player, int score) {
		this.player = player;
		this.score = score;
	}

	/**
	 * @return the player
	 */
	public String getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(String player) {
		this.player = player;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

}
