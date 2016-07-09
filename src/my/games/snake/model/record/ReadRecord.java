package my.games.snake.model.record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import my.games.snake.model.SnakeGameRecords;

/**
 * 
 * @author Eric
 * @vesion 1.0
 * @desc Read Record
 */
public class ReadRecord {

	public SnakeGameRecords readRecordsFromFile(File recordFile) {
		SnakeGameRecords records = new SnakeGameRecords();
		FileInputStream fileInput = null;
		ObjectInputStream objectInput = null;

		if (!recordFile.exists()) {
			return records;
		}

		try {
			fileInput = new FileInputStream(recordFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			objectInput = new ObjectInputStream(fileInput);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object o = null;
		try {
			o = objectInput.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			objectInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		records = (SnakeGameRecords) o;
		records.sortRecords();
		return records;
	}

}
