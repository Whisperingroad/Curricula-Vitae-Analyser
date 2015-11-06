package controller;

import Parser.JobDescriptionAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import extractor.TextExtractor;
import Parser.CvAnalyzer;
import Parser.Lemmatise;
import storage.Resume;
import storage.Storage;
import utils.Constants;


public class Controller 
{
	protected Lemmatise textLemmatiser = new Lemmatise();
//	protected JobDescriptionAnalyzer jobDescriptionAnalyzer = new JobDescriptionAnalyzer();
	protected CvAnalyzer cvAnalyzer = new CvAnalyzer();
	protected Storage storage = new Storage(); 

	String resumePath = Constants.NICHOLAS + "Input\\";
	String textResumePath = Constants.NICHOLAS + "Storage\\TextResumes\\";
	String lemmatisedResumePath = Constants.NICHOLAS + "Storage\\LemmatisedResumes\\";
	String libraryPath = Constants.NICHOLAS + "Library\\";

	ArrayList<String> language = new ArrayList<String>();
	ArrayList<String> qualification = new ArrayList<String>();
	ArrayList<String> experience = new ArrayList<String>();
	ArrayList<String> nationality = new ArrayList<String>();
	ArrayList<String> reqYearExp = new ArrayList<String>();
	ArrayList<String> VVVIPList = new ArrayList<String>();

	HashMap<String,Double> nameScorePairsHash =  new HashMap<String,Double>();

	// default constructor
	public Controller()
	{
		
	}

	
	
	public String extractCV(File CV) throws IOException
	{
		String fileName = CV.getName();
		storage.addResume(fileName);
		//System.out.println("File Name is " + fileName);
		Boolean extractComplete = TextExtractor.execute(CV);
		if(extractComplete == true)
			return TextExtractor.getFileContent();
		else
			return null;

	}

	private void clearLists(){
		language.clear();
		qualification.clear();
		experience.clear();
		nationality.clear();
		reqYearExp.clear();
		VVVIPList.clear();
	}

	private void updateLists(JobDescriptionAnalyzer jobDescriptionAnalyzer){	
		language  = jobDescriptionAnalyzer.getLanguageReq();
		qualification = jobDescriptionAnalyzer.getQualificationReq();
		experience = jobDescriptionAnalyzer.getExperienceReq();
		nationality = jobDescriptionAnalyzer.getNationalityReq();
		reqYearExp = jobDescriptionAnalyzer.getreqYearExp();
		VVVIPList = jobDescriptionAnalyzer.getVVVIPList();
	}
	
	public void startJobProcess(String jobDescription){
		JobDescriptionAnalyzer jobDescriptionAnalyzer = new JobDescriptionAnalyzer();
		
		ArrayList<String> jobReq = new ArrayList<String>(Arrays.asList(jobDescription.split("\\r?\\n")));
		jobReq = textLemmatiser.lemmatiser(jobReq);
		System.out.println(jobReq.toString());
		jobDescriptionAnalyzer.setJobRequirement(jobReq);
		jobDescriptionAnalyzer.execute(libraryPath);
		
		clearLists();
		updateLists(jobDescriptionAnalyzer);
		
		// for testing purposes
		System.out.println("language");
		for (int i=0;i<language.size();i++)
			System.out.println(language.get(i));
		System.out.println("qualification");
		for (int i=0;i<qualification.size();i++)
			System.out.println(qualification.get(i));
		System.out.println("experience");
		for (int i=0;i<experience.size();i++)
			System.out.println(experience.get(i));
		System.out.println("nationality");
		for (int i=0;i<nationality.size();i++)
			System.out.println(nationality.get(i));
		System.out.println("reqYearExp");
		for (int i=0;i<reqYearExp.size();i++)
			System.out.println(reqYearExp.get(i));
		System.out.println("VVVIPList");
		for (int i=0;i<VVVIPList.size();i++)
			System.out.println(VVVIPList.get(i));
	}
	
	public void setExperienceList(ArrayList<String> input){
		experience.addAll(input);
	}
	
	public void setLanguageList(ArrayList<String> input){
		language.addAll(input);
	}
	
	public void setNationalityList(ArrayList<String> input){
		nationality.addAll(input);
	}
	
	public void setqualificationList(ArrayList<String> input){
		qualification.addAll(input);
	}
	
	public ArrayList<String> getExperience(){
		return experience;
	}
	
	public ArrayList<String> getLanguage(){
		return language;
	}
	
	public ArrayList<String> getNationality(){
		return nationality;
	}
	
	public ArrayList<String> getQualification(){
		return qualification;
	}

	public HashMap<String,Double> startProcessing(File resumePath) throws IOException, FileNotFoundException
	{

		System.out.println("check");
		System.out.println(experience.size());
		System.out.println(language.size());
		System.out.println(nationality.size());
		System.out.println(qualification.size());
		File[] listOfCVs = resumePath.listFiles();
		for (int i=0;i< listOfCVs.length ; i++)
		{
			String txtCV = extractCV(listOfCVs[i]);
			ArrayList<String> resume = new ArrayList<String>(Arrays.asList(txtCV.split("\n")));	
			ArrayList<String> lemmatisedResume = new ArrayList<String>(textLemmatiser.lemmatiser(resume));
			cvAnalyzer.inputCV(lemmatisedResume);
			double score = cvAnalyzer.execute(libraryPath, language, qualification, experience, nationality);
			String candidateName = (listOfCVs[i].getName()).replace(lemmatisedResumePath, "");
			candidateName = candidateName.replace(Constants.txtPostFix, "");
			nameScorePairsHash.put(candidateName, score);
			storage.getResume(i).setResume(score, candidateName);
		}
		ArrayList<Resume> resumeList = storage.getResumeList();
		return nameScorePairsHash;

	}
}
