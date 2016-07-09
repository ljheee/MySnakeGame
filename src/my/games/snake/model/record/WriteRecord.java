package my.games.snake.model.record;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import my.games.snake.model.SnakeGameRecords;

/**
 * @author Eric
 * @vesion 1.0
 * @desc Write records
 */
public class WriteRecord {

	public void writeRecordToFile(SnakeGameRecords records, File recordFile) {
		FileOutputStream fileOutput = null;
		try {
			fileOutput = new FileOutputStream(recordFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectOutputStream objectOutput = null;
		try {
			objectOutput = new ObjectOutputStream(fileOutput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectOutput.writeObject(records);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
