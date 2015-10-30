package storage;


import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Storage {

	//protected ArrayList<String> resumeNames = new ArrayList<String>();
	protected ArrayList<Resume> resumeList = new ArrayList<Resume>();
	public Storage()
	{

	}
	
	public ArrayList<Resume> getResumeList(){
		return resumeList;
	}
			
	
	public void addResume(String txtFile){
		Resume resume = new Resume(txtFile);
		resumeList.add(resume);
	}
	
	public Resume getResume(int index){
		return resumeList.get(index);
	}
	
	public ArrayList<String> readData(File fileName) throws FileNotFoundException, IOException
	{
		ArrayList<String> resume = new ArrayList<String>();
		FileReader fileReader = new FileReader(fileName);
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
			PrintWriter printWriter = new PrintWriter(fileName);
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

	public void writeData(ArrayList<String> data, String fileName)
	{
		try
		{
			PrintWriter printWriter = new PrintWriter(fileName);
			for (String line : data)
			{
				String newLine;
				newLine = line.trim();
				printWriter.println(newLine);
			}
			printWriter.close();
		}
		catch (Exception e)
		{
			System.out.println("error in writing data");

		}
	}

	public void moveTxtFile(String destination, File file) throws IOException{
		InputStream inStream = null;
		OutputStream outStream = null;
		inStream = new FileInputStream(file);
		outStream = new FileOutputStream(destination);

		byte[] buffer = new byte[1024];

		int length;
		//copy the file content in bytes 
		while ((length = inStream.read(buffer)) > 0){

			outStream.write(buffer, 0, length);

		}

		inStream.close();
		outStream.close();
	}
	
	public void sortResumeList(){
		double max = 0.0;
		double score = 0.0;
		for (int size = 0; size < resumeList.size() ; size++){
			score = resumeList.get(size).getScore();
			
		}
	}
	
	public static void main(String[] args){
		Storage test = new Storage();
		Resume new1 = new Resume("1");
		new1.setResume(15.5, "nc");
		Resume new2 = new Resume("2");
		new1.setResume(13.5, "nc");
		Resume new3 = new Resume("3");
		new1.setResume(17.5, "nc");
		test.resumeList.add(new1);
		test.resumeList.add(new2);
		test.resumeList.add(new3);
		test.sortResumeList();
	}
}











