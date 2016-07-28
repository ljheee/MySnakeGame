package my.games.snake.model;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import my.games.snake.contants.SnakeGameConstant;
import my.games.snake.enums.Direction;

public class Snake implements Serializable {

	private static final long serialVersionUID = -1064622816984550631L;

	private List<Grid> list = null;

	private Direction direction = Direction.RIGHT;

	public Snake() {
		this.list = new LinkedList<Grid>();
	}

	public void changeDirection(Direction direction) {
		if (direction.isUpDirection()) {
			if (!this.direction.isUpDirection()
					&& !this.direction.isDownDirection()) {
				this.direction = direction;
			}
		} else if (direction.isRightDirection()) {
			if (!this.direction.isRightDirection()
					&& !this.direction.isLeftDirection()) {
				this.direction = direction;
			}
		} else if (direction.isDownDirection()) {
			if (!this.direction.isUpDirection()
					&& !this.direction.isDownDirection()) {
				this.direction = direction;
			}
		} else if (direction.isLeftDirection()) {
			if (!this.direction.isRightDirection()
					&& !this.direction.isLeftDirection()) {
				this.direction = direction;
			}
		}
	}

	public void draw(Graphics2D g2) {
		for (Grid grid : list) {
			grid.draw(g2);
		}
	}

	/**
	 * @return the list
	 */
	public List<Grid> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<Grid> list) {
		this.list = list;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 * the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void move() {
		Grid currentHead = list.get(0);
		int headX = currentHead.getX();
		int headY = currentHead.getY();
		currentHead.setColor(SnakeGameConstant.SNAKE_BODY_COLOR);

		if (direction.isDownDirection()) {
			list.add(0, new Grid(headX, headY + 1,
					SnakeGameConstant.SNAKE_HEADER_COLOR));
			list.remove(list.size() - 1);
		} else if (direction.isUpDirection()) {
			list.add(0, new Grid(headX, headY - 1,
					SnakeGameConstant.SNAKE_HEADER_COLOR));
			list.remove(list.size() - 1);
		} else if (direction.isRightDirection()) {
			list.add(0, new Grid(headX + 1, headY,
					SnakeGameConstant.SNAKE_HEADER_COLOR));
			list.remove(list.size() - 1);
		} else if (direction.isLeftDirection()) {
			list.add(0, new Grid(headX - 1, headY,
					SnakeGameConstant.SNAKE_HEADER_COLOR));
			list.remove(list.size() - 1);
		}

	}

}
