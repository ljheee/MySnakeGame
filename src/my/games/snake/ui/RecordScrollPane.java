package my.games.snake.ui;

import java.io.File;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import my.games.snake.model.Record;
import my.games.snake.model.SnakeGameRecords;

public class RecordScrollPane{

	private static final long serialVersionUID = -3552642981292000951L;

	public JScrollPane getReadScrollPane(SnakeGameRecords records,
			File recordFile) {
		Object[][] data = new Object[records.getNumberInRecord()][3];
		Record [] availableRecords = records.getSortedAvailableRecords();
		
		for (int i = 0; i < availableRecords.length; i++) {
			Record record = availableRecords[i];
			data[i][0] = String.valueOf(i + 1);
			data[i][1] = record.getPlayer();
			data[i][2] = String.valueOf(record.getScore());
		}

		Object[] columnNames = new Object[3];
		columnNames[0] = "ID";
		columnNames[1] = "Name";
		columnNames[2] = "Score";
		JTable table = new JTable(data, columnNames);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane pane = new JScrollPane(table);
		return pane;
	}
}
