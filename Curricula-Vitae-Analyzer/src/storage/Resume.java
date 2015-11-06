package storage;

import java.util.ArrayList;
import java.util.Comparator;

public class Resume {
	String textFile;
	String candidateName;
	double resumeScore;
	ArrayList<String> matchedCriteria;
	
	public Resume(String txtname){
		textFile = txtname;
	}
	
	public void setResume(double score,String name){
		resumeScore = score;
		candidateName = name;
	}
	
	public double getScore(){
		return resumeScore;
	}
	
	public String getFileName(){
		return textFile;
	}
	
	public String getName(){
		return candidateName;
	}
	
}
