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
	private ArrayList<String> importantFulfilled;
	
	
	public Resume(double score,String name,ArrayList<String> qualifications, ArrayList<String> experience,
			ArrayList<String> language, ArrayList<String> particulars, ArrayList<String> important){
		resumeScore = score;
		candidateName = name;
		qualificationsFulfilled = new ArrayList<String>(qualifications);
		experienceFulfilled = new ArrayList<String>(experience);
		languageFulfilled = new ArrayList<String>(language);
		particularsFulfilled = new ArrayList<String>(particulars);
		importantFulfilled = new ArrayList<String>(important);
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
	
	public ArrayList<String> getMatchedImportant(){
		return importantFulfilled;
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
