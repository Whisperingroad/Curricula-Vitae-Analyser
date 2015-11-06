package storage;

import java.util.ArrayList;

public class Resume {
	String textFile;
	String candidateName;
	double resumeScore;
	private ArrayList<String> qualificationsFulfilled;
	private ArrayList<String> experienceFulfilled;
	private ArrayList<String> languageFulfilled;
	private ArrayList<String> particularsFulfilled;
	
	public Resume(double score,String name){
		resumeScore = score;
		candidateName = name;
	}
	/*
	public void setResume(double score,String name){
		resumeScore = score;
		candidateName = name;
	}
	*/
	public void setMatchedQualification(ArrayList<String> qualifications){
		qualificationsFulfilled = qualifications;
	}
	
	public void setMatchedExperience(ArrayList<String> experience){
		experienceFulfilled = experience;
	}
	
	public void setMatchedLanguage(ArrayList<String> language){
		languageFulfilled = language;
	}
	
	public void setMatchedParticulars(ArrayList<String> particulars){
		particularsFulfilled = particulars;
	}
	
	public ArrayList<String> getMatchedQualification(){
		return qualificationsFulfilled;
	}
	
	public ArrayList<String> getMatchedExperience(){
		return experienceFulfilled;
	}
	
	public ArrayList<String> getMatchedLanguage(){
		return languageFulfilled;
	}
	
	public ArrayList<String> getMatchedParticulars(){
		return particularsFulfilled;
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
