package my.games.snake.ui;

import java.awt.Container;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;

import my.games.snake.contants.SnakeGameConstant;
import my.games.snake.enums.GameState;
import my.games.snake.model.SnakeGameRecords;
import my.games.snake.model.record.ReadRecord;

public class SnakeGameFrame extends JFrame {

	private static final long serialVersionUID = 998014032682506026L;

	private SnakeGamePanel panel;

	private Container contentPane;

	private JMenuItem startMI = new JMenuItem("开始游戏");

	private JMenuItem pauseMI = new JMenuItem("暂停游戏");

	private JMenu speedMenu = new JMenu("速度");

	private JMenuItem exitMI = new JMenuItem("退出");

	private JMenuItem aboutMI = new JMenuItem("关于本游戏");

	private JMenuItem loadMI = new JMenuItem("打开进度");

	public JMenuItem saveMI = new JMenuItem("保存进度");
	
	private JMenuItem recordMI = new JMenuItem("分数");

	private JRadioButtonMenuItem speedMI1 = new JRadioButtonMenuItem("超慢速",
			true);

	private JRadioButtonMenuItem speedMI2 = new JRadioButtonMenuItem("慢速",
			false);

	private JRadioButtonMenuItem speedMI3 = new JRadioButtonMenuItem("中速",
			false);

	private JRadioButtonMenuItem speedMI4 = new JRadioButtonMenuItem("快速",
			false);

	private JRadioButtonMenuItem speedMI5 = new JRadioButtonMenuItem("超快速",
			false);

	public int speedFlag = 1;

	public SnakeGameFrame() {
		setTitle(SnakeGameConstant.SNAKE_GAME);
		setSize(SnakeGameConstant.SNAKE_GAME_FRAME_WIDTH,
				SnakeGameConstant.SNAKE_GAME_FRAME_HEIGHT);
		setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu setMenu = new JMenu("设置");
		JMenu helpMenu = new JMenu("帮助");

		setMenu.setMnemonic('s');//设置当前模型上的键盘助记符,一个助记符必须对应键盘上的一个键，并且应该使用 java.awt.event.KeyEvent 中定义的 VK_XXX 键代码之一指定。助记符是不区分大小写的
		setMenu.setMnemonic('H');

		menuBar.add(setMenu);
		menuBar.add(helpMenu);

		setMenu.add(startMI);
		setMenu.add(pauseMI);
		setMenu.addSeparator();

		setMenu.addSeparator();
		setMenu.add(speedMenu);
		setMenu.addSeparator();
		setMenu.add(exitMI);

		setMenu.add(loadMI);
		setMenu.add(saveMI);
		
		setMenu.add(recordMI);

		ButtonGroup group = new ButtonGroup();
		group.add(speedMI1);
		group.add(speedMI2);
		group.add(speedMI3);
		group.add(speedMI4);
		group.add(speedMI5);

		speedMenu.add(speedMI1);
		speedMenu.add(speedMI2);
		speedMenu.add(speedMI3);
		speedMenu.add(speedMI4);
		speedMenu.add(speedMI5);

		startMI.addActionListener(new StartAction());
		pauseMI.addActionListener(new PauseAction());
		exitMI.addActionListener(new ExitAction());
		speedMI1.addActionListener(new SpeedAction());
		speedMI2.addActionListener(new SpeedAction());
		speedMI3.addActionListener(new SpeedAction());
		speedMI4.addActionListener(new SpeedAction());
		speedMI5.addActionListener(new SpeedAction());
		
		recordMI.addActionListener(new RecordAction());

		loadMI.addActionListener(new LoadAction());
		saveMI.addActionListener(new SaveAction());

		helpMenu.add(aboutMI);
		aboutMI.addActionListener(new AboutAction());

		contentPane = getContentPane();
		panel = new SnakeGamePanel(this);
		contentPane.add(panel);

		startMI.setEnabled(true);
		pauseMI.setEnabled(false);
		saveMI.setEnabled(false);

		// 设置游戏状态是初始化状态
		panel.setGameState(GameState.INITIALIZE);
	}

	private class StartAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			startMI.setEnabled(false);
			pauseMI.setEnabled(true);
			saveMI.setEnabled(true);
			panel.setGameState(GameState.RUN);
			panel.getTimer().start();
		}
	}

	private class PauseAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			pauseMI.setEnabled(false);
			startMI.setEnabled(true);
			panel.setGameState(GameState.PAUSE);
			if (panel.getTimer().isRunning()) {
				panel.getTimer().stop();
			}

		}
	}

	private class SpeedAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object speed = event.getSource();
			if (speed == speedMI1) {
				speedFlag = 10;
			} else if (speed == speedMI2) {
				speedFlag = 20;
			} else if (speed == speedMI3) {
				speedFlag = 30;
			} else if (speed == speedMI4) {
				speedFlag = 40;
			} else if (speed == speedMI5) {
				speedFlag = 50;
			}

			panel.getTimer().setDelay(1000 - speedFlag*16);
		}
	}

	private class ExitAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int result = JOptionPane.showConfirmDialog(SnakeGameFrame.this,
					SnakeGameConstant.QUIT_GAME, SnakeGameConstant.SNAKE_GAME,
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}

	private class AboutAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String string = SnakeGameConstant.KEYBOARDS_DESCRIPTION;
			JOptionPane.showMessageDialog(SnakeGameFrame.this, string);
		}
	}

	private class LoadAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			FileDialog dialog = new FileDialog(SnakeGameFrame.this, "Open",
					FileDialog.LOAD);
			dialog.setVisible(true);
			String dir = dialog.getDirectory();
			String fileName = dialog.getFile();
			String filePath = dir + fileName;

			if (fileName != null && fileName.trim().length() != 0) {
				File file = new File(filePath);
				panel.loadGameDataFromFile(file);
				startMI.setEnabled(false);
				pauseMI.setEnabled(true);
			} else {
				JOptionPane.showConfirmDialog(SnakeGameFrame.this,
						"文件名为空\n装载游戏进度失败", "贪吃蛇游戏", JOptionPane.DEFAULT_OPTION);
			}

		}
	}

	private class SaveAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (panel.getGameState() == GameState.INITIALIZE) {
				JOptionPane.showConfirmDialog(SnakeGameFrame.this,
								"游戏没有运行\n不能保存游戏进度", "贪吃蛇游戏",
								JOptionPane.DEFAULT_OPTION);
				return;
			}

			FileDialog dialog = new FileDialog(SnakeGameFrame.this, "Save",FileDialog.SAVE);
			dialog.setVisible(true);
			String dir = dialog.getDirectory();
			String fileName = dialog.getFile();//获取到输入框中玩家输入的“要保存的文件名”
			String filePath = dir + fileName;
			if (fileName != null && fileName.trim().length() != 0) {
				File file = new File(filePath);
				panel.saveGameDataToFile(file);
			} else {
				JOptionPane.showConfirmDialog(SnakeGameFrame.this,
						"文件名为空\n保存游戏进度失败", "贪吃蛇游戏", JOptionPane.DEFAULT_OPTION);
			}

		}
	}
	
	private class RecordAction implements ActionListener {

		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent event) {
			File file = new File("file.dat");//记录分数的文件
			SnakeGameRecords records = new ReadRecord()
					.readRecordsFromFile(file);
			records.sortRecords();
			JScrollPane panel = new RecordScrollPane().getReadScrollPane(records,
					file);

			JDialog recordDialog = new JDialog(SnakeGameFrame.this, "贪吃蛇游戏");
			recordDialog.setBounds(300, 300, 300, 219);

			Container container = recordDialog.getContentPane();
			container.add(panel);
			recordDialog.setVisible(true);
		}
	}

}
