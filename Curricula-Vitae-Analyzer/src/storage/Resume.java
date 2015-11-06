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
	
	public Resume(double score,String name,ArrayList<String> qualifications, ArrayList<String> experience,
			ArrayList<String> language, ArrayList<String> particulars){
		resumeScore = score;
		candidateName = name;
		qualificationsFulfilled = new ArrayList<String>(qualifications);
		experienceFulfilled = new ArrayList<String>(experience);
		languageFulfilled = new ArrayList<String>(language);
		particularsFulfilled = new ArrayList<String>(particulars);
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
