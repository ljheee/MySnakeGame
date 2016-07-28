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

	private JMenuItem startMI = new JMenuItem("��ʼ��Ϸ");

	private JMenuItem pauseMI = new JMenuItem("��ͣ��Ϸ");

	private JMenu speedMenu = new JMenu("�ٶ�");

	private JMenuItem exitMI = new JMenuItem("�˳�");

	private JMenuItem aboutMI = new JMenuItem("���ڱ���Ϸ");

	private JMenuItem loadMI = new JMenuItem("�򿪽���");

	public JMenuItem saveMI = new JMenuItem("�������");
	
	private JMenuItem recordMI = new JMenuItem("����");

	private JRadioButtonMenuItem speedMI1 = new JRadioButtonMenuItem("������",
			true);

	private JRadioButtonMenuItem speedMI2 = new JRadioButtonMenuItem("����",
			false);

	private JRadioButtonMenuItem speedMI3 = new JRadioButtonMenuItem("����",
			false);

	private JRadioButtonMenuItem speedMI4 = new JRadioButtonMenuItem("����",
			false);

	private JRadioButtonMenuItem speedMI5 = new JRadioButtonMenuItem("������",
			false);

	public int speedFlag = 1;

	public SnakeGameFrame() {
		setTitle(SnakeGameConstant.SNAKE_GAME);
		setSize(SnakeGameConstant.SNAKE_GAME_FRAME_WIDTH,
				SnakeGameConstant.SNAKE_GAME_FRAME_HEIGHT);
		setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu setMenu = new JMenu("����");
		JMenu helpMenu = new JMenu("����");

		setMenu.setMnemonic('s');//���õ�ǰģ���ϵļ������Ƿ�,һ�����Ƿ������Ӧ�����ϵ�һ����������Ӧ��ʹ�� java.awt.event.KeyEvent �ж���� VK_XXX ������֮һָ�������Ƿ��ǲ����ִ�Сд��
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

		// ������Ϸ״̬�ǳ�ʼ��״̬
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
						"�ļ���Ϊ��\nװ����Ϸ����ʧ��", "̰������Ϸ", JOptionPane.DEFAULT_OPTION);
			}

		}
	}

	private class SaveAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (panel.getGameState() == GameState.INITIALIZE) {
				JOptionPane.showConfirmDialog(SnakeGameFrame.this,
								"��Ϸû������\n���ܱ�����Ϸ����", "̰������Ϸ",
								JOptionPane.DEFAULT_OPTION);
				return;
			}

			FileDialog dialog = new FileDialog(SnakeGameFrame.this, "Save",FileDialog.SAVE);
			dialog.setVisible(true);
			String dir = dialog.getDirectory();
			String fileName = dialog.getFile();//��ȡ����������������ġ�Ҫ������ļ�����
			String filePath = dir + fileName;
			if (fileName != null && fileName.trim().length() != 0) {
				File file = new File(filePath);
				panel.saveGameDataToFile(file);
			} else {
				JOptionPane.showConfirmDialog(SnakeGameFrame.this,
						"�ļ���Ϊ��\n������Ϸ����ʧ��", "̰������Ϸ", JOptionPane.DEFAULT_OPTION);
			}

		}
	}
	
	private class RecordAction implements ActionListener {

		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent event) {
			File file = new File("file.dat");//��¼�������ļ�
			SnakeGameRecords records = new ReadRecord()
					.readRecordsFromFile(file);
			records.sortRecords();
			JScrollPane panel = new RecordScrollPane().getReadScrollPane(records,
					file);

			JDialog recordDialog = new JDialog(SnakeGameFrame.this, "̰������Ϸ");
			recordDialog.setBounds(300, 300, 300, 219);

			Container container = recordDialog.getContentPane();
			container.add(panel);
			recordDialog.setVisible(true);
		}
	}

}
