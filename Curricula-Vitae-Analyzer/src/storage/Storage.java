package storage;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

	protected ArrayList<Resume> resumeList = new ArrayList<Resume>();
	public Storage()
	{

	}
	
	public ArrayList<Resume> getResumeList(){
		return resumeList;
	}
			
	public void clearList(){
		resumeList.clear();
	}
	public void addResume(double score,String name, ArrayList<String> qualifications, ArrayList<String> experience,
			ArrayList<String> language, ArrayList<String> particulars){
		Resume resume = new Resume(score,name,qualifications,experience,language,particulars);
		resumeList.add(resume);
	}
	
	public Resume getResume(int index){
		return resumeList.get(index);
	}
	
	public ArrayList<String> readData(String fileName) throws FileNotFoundException, IOException
	{
		ArrayList<String> resume = new ArrayList<String>();
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = "";
		// Repeat until all lines are read
		while ((line = bufferedReader.readLine()) != null)
		{
			resume.add(line.toLowerCase());
		}
		bufferedReader.close();
		return resume;
	}

	public void writeData(String data, String fileName) throws FileNotFoundException, IOException
	{
		//try 
		//{
		PrintWriter printWriter = new PrintWriter(fileName);
		printWriter.print(data);
		printWriter.close();
		//}
		//catch (Exception e)
		//{
		//	System.out.println("An exception occured in writing the pdf text to file.");
		//	e.printStackTrace();
		//}
		System.out.println("Completed");
	}

	public void writeData(ArrayList<String> data, String fileName) throws FileNotFoundException, IOException
	{
		//try
		//{
		PrintWriter printWriter = new PrintWriter(fileName);
		for (String line : data)
		{
			String newLine;
			newLine = line.trim();
			printWriter.println(newLine);
		}
		printWriter.close();
		//}
		//catch (Exception e)
		//{
		//	System.out.println("error in writing data");

		//}
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
		Collections.sort(resumeList,new Comparator<Resume>(){
            public int compare(Resume r1,Resume r2){
                return (int) (r2.resumeScore - r1.resumeScore);
          }});
	}

}

