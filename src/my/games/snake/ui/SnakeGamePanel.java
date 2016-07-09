package my.games.snake.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import my.games.snake.contants.SnakeGameConstant;
import my.games.snake.enums.Direction;
import my.games.snake.enums.GameOverType;
import my.games.snake.enums.GameState;
import my.games.snake.model.Barriers;
import my.games.snake.model.Grid;
import my.games.snake.model.Record;
import my.games.snake.model.Snake;
import my.games.snake.model.SnakeGameRecords;
import my.games.snake.model.record.ReadRecord;
import my.games.snake.model.record.WriteRecord;

public class SnakeGamePanel extends JPanel {

	private static final long serialVersionUID = -4173775119881265176L;

	private int flag[][] = new int[SnakeGameConstant.GRID_COLUMN_NUMBER][SnakeGameConstant.GRID_ROW_NUMBER];// ��һ��20*30�Ľ����У�����ÿ�������flag

	private Color color[][] = new Color[SnakeGameConstant.GRID_COLUMN_NUMBER][SnakeGameConstant.GRID_ROW_NUMBER];// ��һ��20*30�Ľ����У�����ÿ���������ɫ

	private Snake snake;

	private Grid food;

	private TimerAction timerAction;

	private int score;

	private SnakeGameFrame frame;

	private Timer timer;

	private Grid grid;

	private GameState gameState = GameState.INITIALIZE;

	private GameOverType gameOverType = GameOverType.TOUCH_EDGE;

	private boolean needToGenerateFood = false;

	private Barriers barrier = new Barriers();

	private boolean needToGenerateBarrie = false;

	public SnakeGamePanel(SnakeGameFrame frame) {
		for (int i = SnakeGameConstant.LEFT; i <= SnakeGameConstant.RIGHT; i++) {
			for (int j = SnakeGameConstant.UP; j <= SnakeGameConstant.DOWN; j++) {
				flag[i][j] = 0;
			}
		}
		addKeyListener(new KeyHandler());
		setFocusable(true);
		init();

		timerAction = new TimerAction();
		timer = new Timer(500, timerAction);
		score = 0;
		this.frame = frame;
		grid = new Grid();
	}

	private void init() {
		initSnake();
		initFood();
		initBarrier();
	}

	private void initSnake() {
		snake = new Snake();
		List<Grid> list = new LinkedList<Grid>();
		list.add(new Grid(4, 1, SnakeGameConstant.SNAKE_BODY_COLOR));
		list.add(0, new Grid(5, 1, SnakeGameConstant.SNAKE_HEADER_COLOR));
		
		snake.setList(list);
	}

	private void initFood() {
		food = new Grid();
		needToGenerateFood = true;
		this.generateFoodByRandom();
	}

	private void initBarrier() {
		barrier = new Barriers();
	}

	public void setGameState(GameState state) {
		gameState = state;
	}

	
	private void judgeGameOver() {
		if (isSnakeHeadTouchEdge() || isSnakeHeadTouchBody()
				|| isSnakeHeadTouchBarrier()) {
			gameState = GameState.OVER;
			writeScore();
			JOptionPane.showMessageDialog(frame, "Game Over");
			int result = JOptionPane.showConfirmDialog(frame,
					SnakeGameConstant.GAME_OVER, SnakeGameConstant.SNAKE_GAME,
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				for (int i = SnakeGameConstant.LEFT; i <= SnakeGameConstant.RIGHT; i++) {
					for (int j = SnakeGameConstant.UP; j <= SnakeGameConstant.DOWN; j++) {
						flag[i][j] = 0;
					}
				}

				gameState = GameState.RUN;
				score = 0;
				init();
				timer.start();
			} else {
				System.exit(0);
			}
		}
	}

	public void drawGameFrame(Graphics2D g2) {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.draw(new Rectangle2D.Double(SnakeGameConstant.SNAKE_GAME_PANEL_LEFT,
				SnakeGameConstant.SNAKE_GAME_PANEL_TOP,
				SnakeGameConstant.SNAKE_GAME_PANEL_RIGHT,
				SnakeGameConstant.SNAKEGAME_PANEL_BOTTOM));

		if (gameState.isInitializedState()) {
			return;
		}

		draw(g2);
		drawScore(g);
	}

	private void draw(Graphics2D g2) {
		drawSnake(g2);
		drawFood(g2);

		drawBarrier(g2);

		for (int i = SnakeGameConstant.LEFT; i <= SnakeGameConstant.RIGHT; i++) {
			for (int j = SnakeGameConstant.UP; j <= SnakeGameConstant.DOWN; j++) {
				if (flag[i][j] == 1) {
					grid.setX(i);
					grid.setY(j);
					grid.setColor(color[i][j]);
					grid.draw(g2);
				}
			}
		}
	}

	private void drawScore(Graphics g) {
		g.drawString("�֔�: " + score,
				SnakeGameConstant.SNAKE_GAME_PANEL_RIGHT + 20, 200);
	}

	private void drawSnake(Graphics2D g2) {
		snake.draw(g2);
	}

	private void drawFood(Graphics2D g2) {
		food.draw(g2);
	}

	private void drawBarrier(Graphics2D g2) {
		barrier.draw(g2);
	}

	private class KeyHandler implements KeyListener {
		public void keyPressed(KeyEvent event) {
			if (!gameState.isRunState()) {
				return;
			}
			int keyCode = event.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_LEFT:
				snake.changeDirection(Direction.LEFT);
				break;

			case KeyEvent.VK_RIGHT:
				snake.changeDirection(Direction.RIGHT);
				break;

			case KeyEvent.VK_UP:
				snake.changeDirection(Direction.UP);
				break;

			case KeyEvent.VK_DOWN:
				snake.changeDirection(Direction.DOWN);
				break;
			default:
				break;
			}
			repaint();
		}

		public void keyReleased(KeyEvent event) {
		}

		public void keyTyped(KeyEvent event) {
		}
	}

	private class TimerAction implements ActionListener, Serializable {

		private static final long serialVersionUID = 749074368549207272L;

		public void actionPerformed(ActionEvent e) {
			if (!gameState.isRunState()) {
				return;
			}

			generateFoodByRandom();
			snake.move();
			eatFood();
			judgeGameOver();

			if (isNeedToGenerateNewBarrier()) {
				generateBarrierByRandom();
			}

			repaint();
		}
	}

	private boolean isFoodAvailable(int x, int y) {
		for (Grid grid : snake.getList()) {
			if (x == grid.getX() && y == grid.getY()) {
				return false;
			}
		}
		return true;
	}

	private void generateFoodByRandom() {

		if (needToGenerateFood) {
			Random r = new Random();
			int randomX = r.nextInt(SnakeGameConstant.GRID_COLUMN_NUMBER);
			int randomY = r.nextInt(SnakeGameConstant.GRID_ROW_NUMBER);

			if (isFoodAvailable(randomX, randomX)) {
				food = new Grid(randomX, randomY, SnakeGameConstant.FOOD_COLOR);

				needToGenerateFood = false;
			} else {
				generateFoodByRandom();
			}
		}
	}

	/**
	 * �������Ǵﵽ50�ı�����ʱ���������һ���ϰ���.
	 */
	private boolean isNeedToGenerateNewBarrier() {
		return score != 0 && score % 50 == 0 && needToGenerateBarrie;
	}

	/**
	 * �ж����������λ���Ƿ��������ʳ���ͻ�� ����г�ͻ����false,���λ��û�г��򷵻�true
	 */
	private boolean isBarrierAvailable(int x, int y) {
		for (Grid grid : snake.getList()) {
			if (x == grid.getX() && y == grid.getY()) {
				return false;
			}
		}
		if (food.getX() == x && food.getY() == y) {
			return false;
		}
		return true;
	}

	/**
	 * �������һ���ϰ���
	 */
	private void generateBarrierByRandom() {
		Random r = new Random();
		int randomX = r.nextInt(SnakeGameConstant.GRID_COLUMN_NUMBER);
		int randomY = r.nextInt(SnakeGameConstant.GRID_ROW_NUMBER);

		if (isBarrierAvailable(randomX, randomY)) {
			barrier.getBarriersList().add(
					new Grid(randomX, randomY, Color.YELLOW));
			// �ɹ�����һ���ϰ���󣬽�needToGenerateBarrie��ʶΪfalse
			needToGenerateBarrie = false;
		} else {
			generateBarrierByRandom();
		}
	}

	private boolean isSnakeHeadTouchBarrier() {
		Grid head = snake.getList().get(0);
		for (Grid grid : barrier.getBarriersList()) {
			if (head.getX() == grid.getX() && head.getY() == grid.getY()) {
				this.gameOverType = GameOverType.TOUCH_BARRIER;
				return true;
			}
		}

		return false;
	}

	private boolean isSnakeHeadTouchEdge() {
		Grid head = this.snake.getList().get(0);
		if ((head.getX() >= SnakeGameConstant.GRID_COLUMN_NUMBER)
				|| (head.getX() < 0)) {
			this.gameOverType = GameOverType.TOUCH_EDGE;
			return true;
		}
		if ((head.getY() >= SnakeGameConstant.GRID_ROW_NUMBER)
				|| (head.getY() < 0)) {
			this.gameOverType = GameOverType.TOUCH_EDGE;
			return true;
		}

		return false;
	}

	private boolean isSnakeHeadTouchBody() {
		Grid head = this.snake.getList().get(0);
		int length = snake.getList().size();

		for (int i = 1; i < length; i++) {
			if (head.getX() == snake.getList().get(i).getX()
					&& head.getY() == snake.getList().get(i).getY()) {
				// this.gameOverType = GameOverType.TOUCH_BODY;
				return true;
			}
		}

		return false;
	}

	private boolean isFoodTouched() {
		Grid head = snake.getList().get(0);
		return head.getX() == food.getX() && head.getY() == food.getY();
	}

	private void eatFood() {
		if (isFoodTouched()) {
			Grid tail = snake.getList().get(snake.getList().size() - 1);
			snake.getList().add(tail);
			this.needToGenerateFood = true;
			this.score += 10;

			if (score % 50 != 0) {
				needToGenerateBarrie = false;
			} else {
				needToGenerateBarrie = true;
			}

		}
	}

	/**
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * @param timer
	 *            the timer to set
	 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	/**
	 * @return the gameState
	 */
	public GameState getGameState() {
		return gameState;
	}

	public void saveGameDataToFile(File file) {

		try {
			FileOutputStream fileStream = new FileOutputStream(file);
			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

			objectStream.writeObject(flag);
			objectStream.writeObject(color);
			objectStream.writeObject(snake);
			objectStream.writeObject(food);
			objectStream.writeObject(new Integer(score));

			objectStream.writeObject(barrier);
			objectStream.writeObject(new Boolean(needToGenerateFood));
			objectStream.writeObject(new Boolean(needToGenerateBarrie));

			objectStream.close();
			fileStream.close();

			JOptionPane.showConfirmDialog(frame, "������Ϸ���ȳɹ�", "̰������Ϸ",
					JOptionPane.DEFAULT_OPTION);
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(frame, e.toString() + "\n������Ϸ����ʧ��",
					"̰������Ϸ", JOptionPane.DEFAULT_OPTION);
		}
	}

	public void loadGameDataFromFile(File file) {

		try {
			int[][] svaedFlag;
			Snake savedSnake;
			Grid savedFood;
			Integer savedScore;
			Color[][] savedColor;
			Barriers savedBarriers;

			Boolean savedNeedToGenerateFood;
			Boolean savedNeedToGenerateBarrie;

			FileInputStream fileStream = new FileInputStream(file);
			ObjectInputStream objectStream = new ObjectInputStream(fileStream);

			svaedFlag = (int[][]) objectStream.readObject();
			savedColor = (Color[][]) objectStream.readObject();
			savedSnake = (Snake) objectStream.readObject();
			savedFood = (Grid) objectStream.readObject();
			savedScore = (Integer) objectStream.readObject();

			savedBarriers = (Barriers) objectStream.readObject();
			savedNeedToGenerateFood = (Boolean) objectStream.readObject();
			savedNeedToGenerateBarrie = (Boolean) objectStream.readObject();

			objectStream.close();
			fileStream.close();

			if (svaedFlag != null && savedColor != null && savedSnake != null
					&& savedFood != null && savedScore != null
					&& savedBarriers != null && savedNeedToGenerateFood != null
					&& savedNeedToGenerateBarrie != null) {
				flag = svaedFlag;
				color = savedColor;
				score = savedScore.intValue();
				gameState = GameState.RUN;

				snake = savedSnake;
				food = savedFood;

				barrier = savedBarriers;
				needToGenerateFood = savedNeedToGenerateFood.booleanValue();
				needToGenerateBarrie = savedNeedToGenerateBarrie.booleanValue();

				frame.saveMI.setEnabled(true);

				if (!timer.isRunning()) {
					timer.start();
				}

				repaint();

				JOptionPane.showConfirmDialog(frame, "װ����Ϸ���ȳɹ�", "̰������Ϸ",
						JOptionPane.DEFAULT_OPTION);
			}
		} catch (Exception e) {

			JOptionPane.showConfirmDialog(frame, e.toString() + "\nװ����Ϸ����ʧ��",
					"̰������Ϸ", JOptionPane.DEFAULT_OPTION);
		}
	}

	private void writeScore() {
		if (score == 0) {
			return;
		}
		File file = new File("file.dat");//����Ŀ¼��
		SnakeGameRecords records = new ReadRecord().readRecordsFromFile(file);
		if (records == null
				|| records.isEmpty()
				|| !records.isFull()
				|| (records.getLastAvailableRecord().getScore() < score && records
						.isFull())) {
			String playerName = JOptionPane
					.showInputDialog("Please input your name");
			if (playerName == null || playerName.length() == 0
					|| playerName.trim().equals("")) {
				playerName = "����Ӣ��";
			}
			Record record = new Record(playerName, score);
			records.addRecordToTopTen(record);
			new WriteRecord().writeRecordToFile(records, file);
		}

	}

}
