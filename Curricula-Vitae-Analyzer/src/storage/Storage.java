package storage;

import utils.Constants;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Storage {
	
	protected ArrayList<String> resumeNames = new ArrayList<String>();
	String path = Constants.SEBASTIAN + "Storage/Database/";
	String postFix = ".txt";

public Storage()
{

}

public ArrayList<String> readData(String fileName) throws FileNotFoundException, IOException
{
	ArrayList<String> resume = new ArrayList<String>();
	FileReader fileReader = new FileReader(Constants.SEBASTIAN + fileName);
	BufferedReader bufferedReader = new BufferedReader(fileReader);
	String line = "";
	// Repeat until all lines are read
	while ((line = bufferedReader.readLine()) != null)
	{
		resume.add(line);
	}
	bufferedReader.close();
	return resume;
			
}

public void writeData(String data, String fileName)
{
	try 
	{
		PrintWriter printWriter = new PrintWriter(path + fileName + postFix);
		printWriter.print(data);
		printWriter.close();
	}
	catch (Exception e)
	{
		System.out.println("An exception occured in writing the pdf text to file.");
		e.printStackTrace();
	}
	System.out.println("Completed");
	
}


}











