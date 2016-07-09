package my.games.snake.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import my.games.snake.contants.SnakeGameConstant;

/**
 * 
 * 贪吃蛇游戏用到的格子类
 * 
 * @author Eric
 * 
 */
public class Grid implements Serializable {

	private static final long serialVersionUID = 5105993927776028563L;

	private int x; // x location

	private int y; // y location

	private Color color; // color for square

	public Grid() {
	}

	public Grid(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	/**
	 * Draw Grid
	 * 
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		int clientX = SnakeGameConstant.SNAKE_GAME_PANEL_LEFT + x
				* SnakeGameConstant.GRID_SIZE;
		int clientY = SnakeGameConstant.SNAKE_GAME_PANEL_TOP + y
				* SnakeGameConstant.GRID_SIZE;
		Rectangle2D.Double rect = new Rectangle2D.Double(clientX, clientY,
				SnakeGameConstant.GRID_SIZE, SnakeGameConstant.GRID_SIZE);
		g2.setPaint(color);
		g2.fill(rect);
		g2.setPaint(Color.BLACK);
		g2.draw(rect);
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

}
