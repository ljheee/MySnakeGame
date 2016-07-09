package my.games.snake.contants;

import java.awt.Color;

/**
 * 贪吃蛇常量定义
 * 
 * @author Eric
 */
public class SnakeGameConstant {

	public static final int GRID_SIZE = 20;

	public static final int SNAKE_GAME_PANEL_LEFT = 0;

	public static final int SNAKE_GAME_PANEL_RIGHT = SNAKE_GAME_PANEL_LEFT + 400;

	public static final int SNAKE_GAME_PANEL_TOP = 0;

	public static final int SNAKEGAME_PANEL_BOTTOM = SNAKE_GAME_PANEL_TOP + 600;

	public static final int LEFT = 0;

	public static final int RIGHT = 19;

	public static final int UP = 0;

	public static final int DOWN = 29;

	public static final int GRID_COLUMN_NUMBER = 20;

	public static final int GRID_ROW_NUMBER = 30;

	public static final int SNAKE_GAME_FRAME_WIDTH = 540;

	public static final int SNAKE_GAME_FRAME_HEIGHT = 660;

	public static final Color SNAKE_HEADER_COLOR = Color.RED;

	public static final Color SNAKE_BODY_COLOR = Color.BLUE;

	public static final Color FOOD_COLOR = Color.GREEN;
	
	public static final String GAME_OVER = "游戏结束，重新开始？";
	
	public static final String SNAKE_GAME = "贪吃蛇游戏";
	
	public static final String QUIT_GAME = "确定退出游戏吗？";
	
	public static final String KEYBOARDS_DESCRIPTION ="说明:\n1.按左键向左移动\n2.按右键向右移动\n3.按向上键向上移动\n4.按向下键向下移动";

}
