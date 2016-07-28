package my.games.snake.appilication;

import javax.swing.JFrame;
import my.games.snake.ui.SnakeGameFrame;


public class MySnakeGameApplication {
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		SnakeGameFrame frame = new SnakeGameFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocation(400,30);
	}
}
